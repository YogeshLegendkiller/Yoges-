package precisioninfomatics.servicefirst.Activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import precisioninfomatics.servicefirst.BackgroundServices.GetLocationService;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.Fragments.TransportModeFragment;
import precisioninfomatics.servicefirst.HelperClass.FileChoosers;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Networks.FileTravel;
import precisioninfomatics.servicefirst.Networks.FileVisit;
import precisioninfomatics.servicefirst.Networks.TravelData;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;


public class TravelUpdation extends AppCompatActivity implements TransportModeFragment.Transportmoderesult {
    private SimpleDateFormat dateview, dateFormat;
    private EditText Checkout, TransportMode, Amount, Attachment,Kilometer,FromLocation,ToLocation;
    private Integer ID, IncidentID, userid;
    private String  CheckinTime;
    private String filepath, filename;
    private ImageView btn_checkout;
    private Double destination_lat,destination_longt,source_lat,source_longt;
    private BottomSheetBehavior bottomSheetBehavior;
    private String userChoosenTask;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelupdation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText Checkin = findViewById(R.id.checkin);
        Checkout = findViewById(R.id.checkout);
        Attachment = findViewById(R.id.attachment);
        TransportMode = findViewById(R.id.transportmode);
        FromLocation= findViewById(R.id.fromloc);
      //  KilometerText=(TextView)findViewById(R.id.km_text) ;
        ToLocation= findViewById(R.id.toloc);
        Kilometer= findViewById(R.id.km);
        Amount = findViewById(R.id.amount);
        Intent intent = getIntent();
        dateview = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Attachment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do something, e.g. set your TextView here via .setText()
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        Kilometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TravelUpdation.this,"You Cannot Enter This",Toast.LENGTH_SHORT).show();
            }
        });
        LoginDAO loginobj = new LoginDAO(this);
        userid = loginobj.getUserID();
        if (intent != null) {
            ID = intent.getIntExtra("id", 0);
             IncidentID = intent.getIntExtra("incidentid", 0);
            CheckinTime = intent.getStringExtra("checkin");
            source_lat=Double.valueOf(intent.getStringExtra("lat"));
            source_longt=Double.valueOf(intent.getStringExtra("long"));
            String checkin_view = ViewDateTimeFormat.DateView(CheckinTime, 1);
            Checkin.setText(checkin_view);
        }
        startService(new Intent(this, GetLocationService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("gps"));
        View bottomSheet = findViewById(R.id.intentbottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        ImageView cancel_bottomsheet = findViewById(R.id.cancel);
        final boolean result = Utility.checkPermission(TravelUpdation.this);
        cancel_bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        RelativeLayout file = findViewById(R.id.file);
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result) {
                    userChoosenTask = "Choose from Library";
                    FileChoosers.galleryIntent(TravelUpdation.this);
                }
            }
        });
        RelativeLayout camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result) {
                    userChoosenTask = "Take Photo";
                    FileChoosers.cameraIntent(TravelUpdation.this);
                }
            }
        });
        Attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  selectImage();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        TransportMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                TransportModeFragment transportModeFragment = new TransportModeFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("flag",2);
                transportModeFragment.setArguments(bundle);
                transportModeFragment.setRetainInstance(true);
                transportModeFragment.show(fm, "fm");
            }
        });
       btn_checkout = findViewById(R.id.btn_checkout);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean internetcheck = Utility.isNetworkAvailable(TravelUpdation.this);
                if(internetcheck){
                if( destination_lat!=null &&  destination_longt!=null){
                     new GetDistance().execute(source_lat,source_longt,destination_lat,destination_longt);
            }else {
                      Toast.makeText(TravelUpdation.this,"Location Not Available Please Try After SomeTime",Toast.LENGTH_SHORT).show();
                      startService(new Intent(TravelUpdation.this, GetLocationService.class));
                      LocalBroadcastManager.getInstance(TravelUpdation.this).registerReceiver(
                        mMessageReceiver, new IntentFilter("gps"));
                }
                } else{
                  Toast.makeText(TravelUpdation.this,"Please Check Your Internet Connectivity",Toast.LENGTH_SHORT).show();
                }}

        });
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Bundle bundle=intent.getExtras();
            if(bundle!=null){
                destination_lat=bundle.getDouble("lat");
                destination_longt=bundle.getDouble("long");
                if( destination_lat != null){
                    Log.d("lat",String.valueOf( destination_lat));
                    Log.d("long",String.valueOf( destination_longt));
                }
                else {
                    Toast.makeText(TravelUpdation.this,"Please Trying Again SomeTime Cannot Fetch Location",Toast.LENGTH_SHORT).show();
                }
            }
            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save:
                Log.d("savekm",SharedPreferenceManager.getServiceString("travel_km","",this));
                if (Checkout.getText().toString().isEmpty() ) {
                    Toast.makeText(TravelUpdation.this, "Please Checkout ", Toast.LENGTH_SHORT).show();
                } else if (TransportMode.getText().toString().isEmpty()) {
                    Toast.makeText(TravelUpdation.this, "Please Select the Mode of Transport", Toast.LENGTH_SHORT).show();
                }
                else if(FromLocation.getText().toString().isEmpty()){
                    Toast.makeText(TravelUpdation.this, "From Location is Missing", Toast.LENGTH_SHORT).show();

                }
                else if(ToLocation.getText().toString().isEmpty()){
                    Toast.makeText(TravelUpdation.this, "To Location is Missing", Toast.LENGTH_SHORT).show();
                }
                else if(TransportMode.getText().toString().equals("TWO WHEELER")&&Kilometer.getText().toString().isEmpty()){
                    Toast.makeText(TravelUpdation.this, "Kilometer is Missing", Toast.LENGTH_SHORT).show();
                }
                else if(!TransportMode.getText().toString().equals("TWO WHEELER")&&Amount.getText().toString().isEmpty()){
                    Toast.makeText(TravelUpdation.this, "Amount is Missing", Toast.LENGTH_SHORT).show();

                }
                else {
                    Handler handler = new Handler();
                    VisitModel visitobj = new VisitModel();
                    visitobj.setID(ID);
                    Date checkoutdate= null;
                    try {
                        checkoutdate = dateview.parse(Checkout.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    visitobj.setCheckOutDate(dateFormat.format(checkoutdate));
                    visitobj.setCheckInDate(CheckinTime);
                    visitobj.setIncidentID(IncidentID);
                    visitobj.setTravelrVisitFlag(2);
                    visitobj.setAmount(Amount.getText().toString());
                    visitobj.setLastmodifiedby(userid);
                    visitobj.setMaporOther(2);
                    visitobj.setIsCallCompleted(0);
                    visitobj.setTransportModeText(TransportMode.getText().toString());
                    visitobj.setTransportMode(SharedPreferenceManager.getServiceInteger("transportid",0,this));
                    visitobj.setLastModifiedat(dateFormat.format(new Date()));
                    visitobj.setDocumentName(filename);
                    visitobj.setIsSent(1);
                    visitobj.setStartAddress(FromLocation.getText().toString());
                    visitobj.setEndAddress(ToLocation.getText().toString());
                    visitobj.setKilometer(SharedPreferenceManager.getServiceString("travel_km","",this));
                    visitobj.setDocumentPath(filepath);
                 //   visitobj.setKilometer(Kilometer.getText().toString());
                    if (filepath != null) {
                        visitobj.setIsFileExist(1);
                    } else {
                        visitobj.setIsFileExist(0);
                    }
                    VisitDAO visitdao = new VisitDAO(TravelUpdation.this);
                    visitdao.UpdateTravel(visitobj);
                    SharedPreferenceManager.RemoveValue("transportid");
                    boolean internetcheck = Utility.isNetworkAvailable(TravelUpdation.this);
                    if(internetcheck){
                        FileTravel fileTravel = new FileTravel();
                        fileTravel.PostFile(TravelUpdation.this);
                    }
                    final Intent intent = new Intent(TravelUpdation.this, IncidentView.class);
                    final ProgressDialog progressDialog=Utility.showProgressDialog(this,"Please wait");
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }, 6000);

                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResultRecieve(int id, String text) {
        TransportMode.setText(text);
        SharedPreferenceManager.putInteger("transportid",id);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        FileChoosers.cameraIntent(TravelUpdation.this);
                    else if (userChoosenTask.equals("Choose from Library"))
                        FileChoosers.galleryIntent(TravelUpdation.this);
                }
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if(data!=null){
            Uri uri = data.getData();
            filepath = FileChoosers.getPath(this, uri);
            filename = FileChoosers.getFileName(uri, this);
            Log.d("file", filepath);}
        } else if (requestCode == 1) {
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String uri=  preferences.getString("uri", "");
            filepath = FileChoosers.getPath(this, Uri.parse(uri));
            filename = FileChoosers.getFileName(Uri.parse(uri), this);
            Log.d("file", filepath);
            preferences.edit().remove("uri").apply();
        }
        if (filepath != null) {
            File isexist = new File(filepath);
            if (isexist.exists()) {
                Attachment.setText(filename);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {
                filepath=null;
            }
        }
        else {
            Log.d("tryagain", "try");

        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        if(!Checkout.getText().toString().isEmpty()){
            btn_checkout.setVisibility(View.GONE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("onresume","onresume");
    startService(new Intent(this, GetLocationService.class));
        //       getActivity().startService(new Intent(getActivity(), GetLocationService.class));
      LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver, new IntentFilter("gps"));

    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("onpause","onpause");
        if(mMessageReceiver!=null){
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
            //        mMessageReceiver = null;
        }
      stopService(new Intent(this,GetLocationService.class));
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d("onstop","onstop");
        if(mMessageReceiver!=null){
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
            //       mMessageReceiver = null;
        }
        stopService(new Intent(this,GetLocationService.class));
    }
    public class GetDistance extends AsyncTask<Double, Void, String> {
        private ProgressDialog pd;
        private static final int READ_TIMEOUT = 6000;
        private static final int CONNECTION_TIMEOUT = 6000;
        private String JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(TravelUpdation.this);
            pd.setMessage("Please wait");
            pd.show();
        }



        @Override
        protected String doInBackground(Double... strings) {
            URL url;
            try {
                url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + strings[0] + "," + strings[1] + "&destination=" + strings[2] + "," + strings[3] + "&sensor=false&units=metric&mode=driving&alternatives=true");
                //Log.d("url","http://maps.googleapis.com/maps/api/directions/json?origin=" + strings[0] + "," + strings[1] + "&destination=" + strings[2] + "," + strings[3] + "&sensor=false&units=metric&mode=driving&alternatives=true");
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                InputStream in;
                in = new BufferedInputStream(conn.getInputStream());
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(in));
                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine).append("\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    Log.e("empty", "empty");
                }
                JsonResponse = buffer.toString();
                Log.d("response", JsonResponse);


            } catch (IOException e1) {
                e1.printStackTrace();
            }


            return JsonResponse;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.dismiss();
            if(result!=null){
               String km=   getKilometer(result,1);
               if(km!=null) {
                   String[] view_km = km.split(",,");
                   Log.d("km", view_km[0]);
                   String KM = view_km[0];
                   String CheckoutTime = dateview.format(new Date());
                   Checkout.setText(CheckoutTime);
                   btn_checkout.setVisibility(View.INVISIBLE);
                   Kilometer.setText(KM);
                   SharedPreferenceManager.puString("travel_km", KM);
               }
            else {
                   Toast.makeText(TravelUpdation.this,"Please Try After SomeTime Cannot Fetch Location",Toast.LENGTH_SHORT).show();
               }
            }
            else {
                Toast.makeText(TravelUpdation.this,"Please Try After SomeTime Cannot Fetch Location",Toast.LENGTH_SHORT).show();
            }
    }
}

    public   String getKilometer(String jsonResponse,int flag) {
        String parsedDistance = null;
        String start_adrs = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray array = jsonObject.getJSONArray("routes");
            JSONObject routes = array.getJSONObject(0);
            Log.d("routelength", String.valueOf(routes.length()));
            JSONArray legs = routes.getJSONArray("legs");
            JSONObject steps = legs.getJSONObject(0);
            JSONObject distances = steps.getJSONObject("distance");
            parsedDistance = distances.getString("text");
            if (flag == 1) {
                start_adrs = steps.getString("start_address");
            } else if (flag == 2) {
                start_adrs = steps.getString("end_address");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"Please Try After Sometime",Toast.LENGTH_SHORT).show();
            return null;
        }
        //  Log.d("addrss",start_adrs);
        return parsedDistance + " ,," + start_adrs;
    }
}
