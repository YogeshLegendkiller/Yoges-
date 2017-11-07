package precisioninfomatics.servicefirst.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

import precisioninfomatics.servicefirst.Adapters.IncidentSummaryAdapter;
import precisioninfomatics.servicefirst.Adapters.InsetDivider;
import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.BackgroundServices.Background;
import precisioninfomatics.servicefirst.BackgroundServices.GPSService;
import precisioninfomatics.servicefirst.BackgroundServices.RestartGCMTaskService;
import precisioninfomatics.servicefirst.CursorLoaders.DashBoardLoaders;
import precisioninfomatics.servicefirst.DAO.ChartDAO;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.Fragments.DatePickerFragment;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.ChartModel;
import precisioninfomatics.servicefirst.Networks.FCMTokeRegistration;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;


public class Dashboard extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>, DatePickerFragment.OnCompleteListener {

    public static final String TASK_GET_LOCATION_PERIODIC = "location_periodic_task";
    public static final int RequestPermissionCode = 1;
    private RecyclerView mrecyclerview;
    private ChartDAO chartDAO;
    private PieChart pieChart;
    private ArrayList<PieEntry> entries;
    private TableLayout pielist;

    public static String getLatLongByURL(String requestURL) {
        URL url;
        HttpURLConnection conn=null;
        StringBuilder response = new StringBuilder();
        try {
            url = new URL(requestURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            } else {
                response = new StringBuilder();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }}
        return response.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (!checkPermission()) {
            requestPermission();
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        pielist = findViewById(R.id.child_layout);
        Integer fcmcheck = SharedPreferenceManager.getServiceInteger("fcmcheck", 0,this);
        if (fcmcheck != 1) {
            FCMTokeRegistration fcmTokeRegistration = new FCMTokeRegistration();
            fcmTokeRegistration.SendTokenToServer(this);
        } else {
            Log.e("already sent", "already sent");
        }
        boolean internetcheck = Utility.isNetworkAvailable(this);
        Integer addresscheck = SharedPreferenceManager.getServiceInteger("addressflag", 0, this);
        if (addresscheck != 1) {
            Address(1);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView textView_name = view.findViewById(R.id.name);
        TextView textView_role = view.findViewById(R.id.rolename);
        String name = SharedPreferenceManager.getServiceString("Name", null, this);
        String role = SharedPreferenceManager.getServiceString("Role", null, this);
        textView_name.setText(name);
        if (internetcheck) {
            startService(new Intent(this, Background.class));
        }
        pieChart = findViewById(R.id.pie);
        entries = new ArrayList<>();
        mrecyclerview = findViewById(R.id.inc_summary);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration divider = new InsetDivider.Builder(this)
                .orientation(InsetDivider.VERTICAL_LIST)
                .dividerHeight(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .color(getResources().getColor(R.color.divider))
                .insets(getResources().getDimensionPixelSize(R.dimen.divider_height), 0)
                .overlay(true)
                .build();
        mrecyclerview.addItemDecoration(divider);
        chartDAO = new ChartDAO(this);
        textView_role.setText(role);
        startService(new Intent(this, GPSService.class));
        startPeriodicLocationTask();
        getLoaderManager().initLoader(1, null, this);
   }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("onrestart","onrestart");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean location = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean externalstorage = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean readcontacts = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0) {
                    if (CameraPermission && location && externalstorage && readcontacts) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Please Enable Some Permission", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public void startPeriodicLocationTask() {
        Log.d("periodictask", "startPeriodicLocationTask");
        GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(getApplicationContext());
        PeriodicTask taskBuilder = new PeriodicTask.Builder()
                .setService(RestartGCMTaskService.class)
                .setTag(TASK_GET_LOCATION_PERIODIC)
                .setPeriod(60).setFlex(30)
                .setPersisted(true).build();
        mGcmNetworkManager.schedule(taskBuilder);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        int Camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int Location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int ReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int Contact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        return Camera == PackageManager.PERMISSION_GRANTED &&
                Location == PackageManager.PERMISSION_GRANTED &&
                ReadStorage == PackageManager.PERMISSION_GRANTED &&
                Contact == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS
                }, RequestPermissionCode);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            DatePickerFragment visitStatus = new DatePickerFragment();
            visitStatus.setRetainInstance(true);
            visitStatus.show(fm, "fragment_name");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.my_profile) {
            Address(2);
        } else if (id == R.id.call) {
            Intent intent = new Intent(Dashboard.this, Incident.class);
            startActivity(intent);
        } else if (id == R.id.vehicle_details) {
            Intent intent = new Intent(Dashboard.this, RegistrationView.class);
            startActivity(intent);
        }
        //   else if(id==R.id.attendence){
        // Intent intent=new Intent(Dashboard.this ,Attendence.class);
        //startActivity(intent);
        //  }
        else if (id == R.id.logout) {
            // SharedPreferences sharedPreferences = getSharedPreferences("loginpref", 0);
            //sharedPreferences.edit().remove("flag").apply();
            SharedPreferenceManager.RemoveValue("flag");
            SharedPreferenceManager.RemoveValue("isslideexist");
            SharedPreferenceManager.RemoveValue("Name");
            SharedPreferenceManager.RemoveValue("fcmcheck");
            SharedPreferenceManager.RemoveValue("vehicle_flag");
            SharedPreferenceManager.RemoveValue("isslideexist");
            SharedPreferenceManager.RemoveValue("Role");
            SharedPreferenceManager.RemoveValue("adress");
            SharedPreferenceManager.RemoveValue("addresslat");
            SharedPreferenceManager.RemoveValue("addresslong");
            SharedPreferenceManager.RemoveValue("addressflag");
            LoginDAO loginobj = new LoginDAO(this);
            loginobj.logout();
            IncidentDAO incidentDAO = new IncidentDAO(this);
            incidentDAO.deleteincident();
            incidentDAO.deleteVisit();
            incidentDAO.deleteTravel();
            chartDAO.PieDelete();
            chartDAO.SummaryDelete();
            incidentDAO.deleteLastmodified();
            incidentDAO.deleteSpareRequest();
            incidentDAO.deleteResource();
            incidentDAO.deleteIncidentView();
            incidentDAO.deleteClaimDelete();
            incidentDAO.deleteVehicleRegistration();
            incidentDAO.deleteLocalVendor();
            incidentDAO.deleteSpareIssue();
            Intent intent = new Intent(Dashboard.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int i, Bundle bundle) {

      return new DashBoardLoaders(this, ChartDAO.Chart_URI, null, null, null, null,i);
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (loader.getId()) {
                    case 0:
                        entries.clear();
                        pielist.removeAllViews();
                        int total = 0;
                        if (cursor.moveToFirst()) {
                        do {
                            total += cursor.getInt(cursor.getColumnIndex(ChartModel.Data));
                            entries.add(new PieEntry(cursor.getInt(cursor.getColumnIndex(ChartModel.Data)), cursor.getString(cursor.getColumnIndex(ChartModel.Label))));
                        } while (cursor.moveToNext());
                    }
                        PieDataSet pieDataSet = new PieDataSet(entries, "");
                        com.github.mikephil.charting.data.PieData pieData = new com.github.mikephil.charting.data.PieData(pieDataSet);
                        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                        pieChart.setData(pieData);
                        pieChart.animateY(3000);
                        Legend l = pieChart.getLegend();
                        int colorcodes[] = l.getColors();
                        for (int i = 0; i < entries.size()-1; i++) {
                            LayoutInflater inflater = getLayoutInflater();
                            TableRow tr = (TableRow) inflater.inflate(R.layout.table_row_legend,
                                    pielist, false);
                            pielist.addView(tr);
                            LinearLayout linearLayoutColorContainer = (LinearLayout) tr.getChildAt(0);
                            LinearLayout linearLayoutColor = (LinearLayout) linearLayoutColorContainer.getChildAt(0);
                            TextView textlable = (TextView) tr.getChildAt(1);
                            TextView textdata = (TextView) tr.getChildAt(2);
                            linearLayoutColor.setBackgroundColor(colorcodes[i]);
                            textlable.setText(entries.get(i).getLabel());
                            textdata.setText(String.valueOf(entries.get(i).getValue()));
                        }
                        String str2 = "Total <br />" + String.valueOf(total);
                        pieChart.setCenterText(Html.fromHtml(str2));
                        pieChart.getLegend().setWordWrapEnabled(true);
                        pieChart.getLegend().setEnabled(false);
                        pieChart.setDrawSliceText(false);
                        pieDataSet.setDrawValues(false);
                        Description des = pieChart.getDescription();
                        des.setEnabled(false);
                        pieDataSet.notifyDataSetChanged();
                        pieChart.invalidate();
                        getLoaderManager().destroyLoader(0);
                        break;
                    case 1:
                        IncidentSummaryAdapter  incidentSummaryAdapter = new IncidentSummaryAdapter(cursor);
                        mrecyclerview.setAdapter(incidentSummaryAdapter);
                        incidentSummaryAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClicked(Cursor cursor) {
                                String label = cursor.getString(cursor.getColumnIndex(ChartModel.Label));
                                Bundle bundle = new Bundle();
                                bundle.putInt("flag", 1);
                                bundle.putString("label", label);
                                Intent intent = new Intent(Dashboard.this, Incident.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        break;
                }
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ondestroy", "ondestroy");
        pieChart=null;
         pielist=null;
        entries=null;
        mrecyclerview.setAdapter(null);
        mrecyclerview=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onresume", "onresume_dash");
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onpause", "onpause_dash");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onstop", "onstop_dash");
        stopService((new Intent(this, Background.class)));
     }

    @Override
    public void onComplete() {
        getLoaderManager().restartLoader(0, null, this);
        Log.d("oncomplete", "onstart_dash");
    }

    public void Address(final int flag) {
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alterdialog_editext, null);
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(dialogView);
        final EditText edt = dialogView.findViewById(R.id.geolocation);
        dialogBuilder.setTitle("Please Enter Your Present Address");
        dialogBuilder.setMessage("Enter Address");
        if (flag == 2) {
            edt.setText(SharedPreferenceManager.getServiceString("adress", "", this));
        }
        dialogBuilder.setPositiveButton("Get Location", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });
        final android.app.AlertDialog b = dialogBuilder.create();
        b.show();
        b.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean internetcheck = Utility.isNetworkAvailable(Dashboard.this);
                if (internetcheck) {
                    if(edt.getText().toString().isEmpty()){
                        Toast.makeText(Dashboard.this, "Please Enter Your Present Address", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        new LatLngAsyncTask(edt.getText().toString(), b, Dashboard.this).execute();
                    }
                } else {
                    Toast.makeText(Dashboard.this, "Please Check Your Network Connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                } else {
                    b.dismiss();
                }
            }
        });
    }




    public static class LatLngAsyncTask extends AsyncTask<String, Void, String[]> {
        private ProgressDialog dialog;
        private String location;
        private String address;
        private Context context;
        private android.app.AlertDialog alertDialog;
        LatLngAsyncTask(String address, android.app.AlertDialog alertDialog, Context context) {
            this.address = address;
             dialog = new ProgressDialog(context);
             this.alertDialog=alertDialog;
             this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            String response;
            try {
                String url_json = "https://maps.googleapis.com/maps/api/geocode/json?";
                //Log.d("url","http://maps.google.com/maps/api/geocode/json?address="+address+"&sensor=false");
                try {
                    // encoding special characters like space in the user input place
                    location = URLEncoder.encode(address, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String addresss = "address=" + location;
                String sensor = "sensor=false";
                url_json = url_json + addresss + "&" + sensor;
                Log.d("url", url_json);
                response = getLatLongByURL(url_json);
                //     Log.d("response",""+response);
                return new String[]{response};
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String... result) {
            if (result != null) {
                try {

                 //   Log.d("result", result[0]);
                    JSONObject jsonObject = new JSONObject(result[0]);

                    double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lng");

                    double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lat");

                 //   Log.d("latitude", "" + lat);
                //    Log.d("longitude", "" + lng);
                    SharedPreferenceManager.puString("adress", address);
                    SharedPreferenceManager.puString("addresslat", String.valueOf(lat));
                    SharedPreferenceManager.puString("addresslong", String.valueOf(lng));
                    SharedPreferenceManager.putInteger("addressflag", 1);
                    alertDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                 Toast.makeText(context, "Please Try Again Later", Toast.LENGTH_SHORT).show();

                }
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

}
