package precisioninfomatics.servicefirst.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import precisioninfomatics.servicefirst.Activities.*;

import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;

import precisioninfomatics.servicefirst.Adapters.VisitListAdapter;
import precisioninfomatics.servicefirst.BackgroundServices.GetLocationService;
import precisioninfomatics.servicefirst.BackgroundServices.VisitService;
import precisioninfomatics.servicefirst.CursorLoaders.VisitLoaders;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Networks.VisitData;
import precisioninfomatics.servicefirst.Networks.VisitSpareData;
import precisioninfomatics.servicefirst.Networks.VisitViewData;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

import static precisioninfomatics.servicefirst.Activities.VisitTravel.MY_PERMISSIONS_REQUEST_LOCATION;

public class Visit extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> ,SwipeRefreshLayout.OnRefreshListener{
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private int  id, userid, travelcheck,GeneralClaimID,ispartrequired,installationcall,incistatusID;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private RecyclerView recyclerView;
    private VisitDAO visitDAO;
    private String   latitude,longtitude,customeradress;
    private Double lat,longt;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragvisit, container, false);
        fab = view.findViewById(R.id.fab);
        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        swipeRefreshLayout = view. findViewById(R.id.swipe_frag);
        swipeRefreshLayout.setOnRefreshListener(this);
    //    IncidentView.SectionsPagerAdapter mSectionsPagerAdapter = new IncidentView.SectionsPagerAdapter(getFragmentManager());
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        recyclerView = view.findViewById(R.id.recyclerview);
        visitDAO = new VisitDAO(getActivity());
        LoginDAO loginobj = new LoginDAO(getActivity());
        userid = loginobj.getUserID();
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        getActivity().startService(new Intent(getActivity(), GetLocationService.class));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver, new IntentFilter("gps"));
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt("id");
            installationcall=bundle.getInt("installationcall");
            customeradress=bundle.getString("customeraddress");
            ispartrequired=bundle.getInt("partrequired");
            latitude=bundle.getString("lat");
            GeneralClaimID=bundle.getInt("generalclaim");
            incistatusID=bundle.getInt("status");
            longtitude=bundle.getString("long");
        }
         travelcheck = SharedPreferenceManager.getServiceInteger("two_wheeler_check", 0,getActivity());
     if (travelcheck == 2) {
         fab2.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_1481892667_vespa));
     } else {
         fab2.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_gps));
     }
        fab1.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_incoming_call));
        final boolean internetcheck = Utility.isNetworkAvailable(getActivity());
        if(internetcheck) {
            Intent serviceintent=new Intent(getActivity(), VisitService.class);
            serviceintent.putExtra("id",id);
            getActivity().startService(serviceintent);
        }
        int   iscompleted=visitDAO.IsVisitCompleted(id,userid);
        if(incistatusID==8){
            fab.setVisibility(View.GONE);
            fab1.setVisibility(View.GONE);
            fab2.setVisibility(View.GONE);
        }
        else if(incistatusID==7||iscompleted>0){
            fab.setVisibility(View.VISIBLE);
            fab1.setVisibility(View.GONE);
            fab2.setVisibility(View.INVISIBLE);
        }
        getActivity().getSupportLoaderManager().initLoader(1, null, this);
        return view;
    }

    public void animateFAB() {
        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setVisibility(View.INVISIBLE);
            fab2.setVisibility(View.INVISIBLE);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }

    }
    public  void animateFABForCompleted() {
        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            fab2.startAnimation(fab_close);
            fab1.setVisibility(View.GONE);
            fab2.setVisibility(View.INVISIBLE);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab2.startAnimation(fab_open);
            fab1.setVisibility(View.GONE);
            fab2.setVisibility(View.VISIBLE);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        int viewid = view.getId();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        VisitModel visitModel = new VisitModel();
        String mstarttime = dateFormat.format(new Date());
        int      iscompleted=visitDAO.IsVisitAssigned(userid,id);
        if(iscompleted>0){
        switch (viewid) {
            case R.id.fab:
                int     visit_iscomplete = visitDAO.Visit_IsCompleted_Count();
                int  travel_iscomplete=visitDAO.Travel_IsCompleted_Count();
                int   visitCompleted=visitDAO.IsVisitCompleted(id,userid);
                if (visit_iscomplete == 0&& travel_iscomplete==0) {
                    Boolean internetcheck=Utility.isNetworkAvailable(getActivity());
                    if(incistatusID==7||visitCompleted>0){
                        if(internetcheck){
                            new IsVisitClosed(getActivity()).execute(precisioninfomatics.servicefirst.HelperClass.URL.SF_URL+"/getinccheck"+"/"+id+"/"+userid);
                        }
                        else {
                            animateFABForCompleted();
                        }}
                    else {
                    animateFAB();
                }} else {
                    //fab.setClickable(false);
                    Toast.makeText(getActivity(), "Please Finish The Pending Visit", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fab1:
                Intent intent;
                visitModel.setCheckInDate(mstarttime);
                visitModel.setIncidentID(id);
                visitModel.setTravelrVisitFlag(1);
                visitModel.setCreatedat(mstarttime);
                visitModel.setCreatedby(userid);
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                visitModel.setUserID(userid);
                fab2.startAnimation(fab_close);
                fab1.setVisibility(View.INVISIBLE);
                fab2.setVisibility(View.INVISIBLE);
                fab1.setClickable(false);
                fab2.setClickable(false);
                visitModel.setIsCallCompleted(1);
                visitDAO.InsertVisitUpdate(visitModel);
                break;
            case R.id.fab2:
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                //  fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                //   fab1.setVisibility(View.INVISIBLE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
                //      fab1.setClickable(false);
                fab1.setClickable(false);
                fab2.setClickable(false);
                if (travelcheck ==1) {
                if(!checkPermission()){
                    requestPermission();
                }
                else {
                    intent = new Intent(getActivity(), VisitTravel.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("destination_lat", latitude);
                    bundle.putString("destination_long", longtitude);
                    bundle.putString("customeraddress", customeradress);
                    bundle.putInt("incidentid", id);
                    bundle.putInt("flag", 1);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                } } else {
                    if(lat!=null&& longt!=null){
                        Log.d("lat",String.valueOf(lat));
                        Log.d("long",String.valueOf(longt));
                        visitModel.setCheckInDate(mstarttime);
                        visitModel.setIncidentID(id);
                        visitModel.setTravelrVisitFlag(2);
                        visitModel.setCreatedat(mstarttime);
                        visitModel.setCreatedby(userid);
                        visitModel.setStartAddress("");
                        visitModel.setUserID(userid);
                        visitModel.setMaporOther(2);
                        visitModel.setStartLat(String.valueOf(lat));
                        visitModel.setStartLong(String.valueOf(longt));
                        visitModel.setIsSent(1);
                        visitModel.setIsCallCompleted(1);
                        visitDAO.InsertTravel(visitModel);
                    }
                    else {
                        Toast.makeText(getActivity(),"Please Turn on your GPS and Try Again",Toast.LENGTH_SHORT).show();
                    }
                       }
                break;
        }}///}
        else {
        //  Toast.makeText(getActivity(),"Please Wait Looking for Resource Allocation or This may not be your call  ",Toast.LENGTH_SHORT).show();
          //  IncidentDAO incidentDAO=new IncidentDAO(getActivity());
          //  incidentDAO.DeleteIncident(id);
       //     Intent intent=new Intent(getActivity(),Incident.class);
        //     startActivity(intent);
            Toast.makeText(getActivity(),"Please wait till Resource Allocation",Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_LOCATION:
                boolean location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0) {
                    if (location) {
                        Toast.makeText(getActivity(),"Permission Granted",Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getActivity(),"Please Enable Location Permission",Toast.LENGTH_LONG).show();
                    }
                }

                break;
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
    return new VisitLoaders(getContext(), VisitDAO.VisitDAO_URI, null, null, null, null,id);
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
          Bundle bundle=intent.getExtras();
            if(bundle!=null){
            lat=bundle.getDouble("lat");
            longt=bundle.getDouble("long");
                if(lat != null){
                    Log.d("lat",String.valueOf(lat));
                    Log.d("long",String.valueOf(longt));
                }
                else {
                    Toast.makeText(getActivity(),"Please Trying Again SomeTime Cannot Fetch Location",Toast.LENGTH_SHORT).show();
                }
            }
            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        VisitListAdapter visitListAdapter = new VisitListAdapter(getActivity(), cursor);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(visitListAdapter);
        visitListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndex(VisitModel.TempID));
                Log.d("id", String.valueOf(id));
                int userid=cursor.getInt(cursor.getColumnIndex(VisitModel.userid));
                String estimatedKilometer=cursor.getString(cursor.getColumnIndex(VisitModel.TempEstimatedKilometer));
                String startaddress=cursor.getString(cursor.getColumnIndex(VisitModel.TempStartAddress));
                int incidentid = cursor.getInt(cursor.getColumnIndex(VisitModel.incid));
                int travelorvisit = cursor.getInt(cursor.getColumnIndex(VisitModel.travelorcallflag));
                String checkout = cursor.getString(cursor.getColumnIndex(VisitModel.TempCheckout));
                String checkInDate = cursor.getString(cursor.getColumnIndex(VisitModel.TempCheckin));
                //maporother=errorcode
                int maporother = cursor.getInt(cursor.getColumnIndex(VisitModel.TempMaporother));

                int webid=cursor.getInt(cursor.getColumnIndex(VisitModel.TempWebID));
                //int errorcode=cursor.getInt(cursor.getColumnIndex(VisitModel.errorcode));
                if (travelorvisit == 1) {
               if(maporother==0){
                   if (checkout == null) {
                        Intent intent = new Intent(getActivity(), VisitUpdation.class);
                        intent.putExtra("checkin", checkInDate);
                        intent.putExtra("id", id);
                        intent.putExtra("ispartrequired",ispartrequired);
                        intent.putExtra("claim",GeneralClaimID) ;
                        intent.putExtra("installationcall",installationcall);
                        intent.putExtra("incidentid", incidentid);
                        startActivityForResult(intent, 1);
                    } else {
                        Intent intent = new Intent(getActivity(), VisitView.class);
                        intent.putExtra("incidentid",incidentid);
                        intent.putExtra("id", id);
                       intent.putExtra("userid",userid);
                       intent.putExtra("webid", webid);
                        intent.putExtra("installationcall",installationcall);
                        intent.putExtra("ispartrequired",ispartrequired);
                        startActivityForResult(intent, 1);
                    }
                }
                else {
                   Intent intent=new Intent(getActivity(), VisitUpdation.class);
                   intent.putExtra("id",id);
                   intent.putExtra("editflag",3);
                   intent.putExtra("incidentid",incidentid);
                   intent.putExtra("ispartrequired",ispartrequired);
                   intent.putExtra("claim",GeneralClaimID) ;
                   intent.putExtra("installationcall",installationcall);
                   startActivityForResult(intent,1);
                    }
                } else {
                    String lat = cursor.getString(cursor.getColumnIndex(VisitModel.TempStartLat));
                    String longt = cursor.getString(cursor.getColumnIndex(VisitModel.TempStartLong));
                    if (maporother == 1) {
                        String duration=cursor.getString(cursor.getColumnIndex(VisitModel.Tempduration));
                        if (checkout == null) {
                            Intent intent = new Intent(getActivity(), VisitTravel.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", id);
                            bundle.putString("destination_lat", latitude);
                            bundle.putString("customeraddress",customeradress);
                            bundle.putString("destination_long",longtitude);
                            bundle.putInt("incidentid", incidentid);
                            bundle.putString("duration",duration);
                            bundle.putString("startaddress",startaddress);
                            bundle.putString("estimateddistance",estimatedKilometer);
                            bundle.putDouble("lat", Double.valueOf(lat));
                            bundle.putDouble("long", Double.valueOf(longt));
                            bundle.putString("checkin", checkInDate);
                            bundle.putInt("flag", 2);
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(intent, 2);

                        } else {
                            Intent intent = new Intent(getActivity(), TravelView.class);
                            intent.putExtra("id", id);
                            intent.putExtra("status",incistatusID);
                            intent.putExtra("flag",maporother);
                            intent.putExtra("userid",userid);
                             startActivityForResult(intent, 2);
                        }
                    } else {
                        if (checkout == null) {
                            Intent intent = new Intent(getActivity(), TravelUpdation.class);
                            intent.putExtra("checkin", checkInDate);
                            intent.putExtra("id", id);
                            intent.putExtra("webid", 0);
                            intent.putExtra("lat", lat);
                            intent.putExtra("long", longt);
                            intent.putExtra("incidentid", incidentid);
                            startActivityForResult(intent, 2);
                        } else {
                            Intent intent = new Intent(getActivity(), TravelView.class);
                            intent.putExtra("id", id);
                            intent.putExtra("status",incistatusID);
                            intent.putExtra("flag",maporother);
                            intent.putExtra("userid",userid);
                            startActivityForResult(intent, 2);
                        }
                    }
                      }
            }


        });
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onresume","onresume");
    //    getActivity().startService(new Intent(getActivity(), GetLocationService.class));
           getActivity().startService(new Intent(getActivity(), GetLocationService.class));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
        mMessageReceiver, new IntentFilter("gps"));

    }

    @Override
    public void onRefresh() {
        boolean isnetworkavailable=Utility.isNetworkAvailable(getActivity());
        if(isnetworkavailable){
       // VisitData visitData=new VisitData();
       // visitData.PostVisitData(getActivity(),id,2,swipeRefreshLayout);
     //   VisitViewData visitViewData=new VisitViewData();
      //  visitViewData.VisitView(getActivity(),id,0,2,swipeRefreshLayout);
            VisitSpareData visitSpareData=new VisitSpareData();
            visitSpareData.VisitSpareData(getActivity(),id,0,2,swipeRefreshLayout);

        }
    else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(),"Please Check Your Network Connectivity",Toast.LENGTH_SHORT).show();
        }
    }
    public  class IsVisitClosed extends AsyncTask<String, Void, String> {
        private static final String REQUEST_METHOD = "GET";
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECTION_TIMEOUT = 15000;
        private ProgressDialog pd;
        private Context context;
        private IsVisitClosed(Context c) {
            this.context=c;
            pd=new ProgressDialog(context);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
        }
        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL url = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        url.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                return  null;
             //   e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            pd.dismiss();
            if (value != null){

                try {
                    JSONObject jsonObject=new JSONObject(value);
                    int incidentstatusid=jsonObject.getInt("StatusID");
                 //   int visitstatusid=jsonObject.getInt("VisitStatusID");
                    if(incidentstatusid==8){
                        Toast.makeText(context,"Incident Is Closed",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        animateFABForCompleted();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(context,"Please Check Your Network",Toast.LENGTH_SHORT).show();
            }
        }}

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onpause","onpause");
     //   recyclerView.setAdapter(null);
        if(mMessageReceiver!=null){
              LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
       //        mMessageReceiver = null;
           }
      getActivity().stopService(new Intent(getActivity(),GetLocationService.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ondestroyview","ondestroyview");
        recyclerView.setAdapter(null);
        if(mMessageReceiver!=null){
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
            //       mMessageReceiver = null;
        }
        getActivity().stopService(new Intent(getActivity(),GetLocationService.class));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ondestroy","ondestroy");
        recyclerView.setAdapter(null);
        if(mMessageReceiver!=null){
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
            //       mMessageReceiver = null;
        }
        getActivity().stopService(new Intent(getActivity(),GetLocationService.class));

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onstop","onstop");
        if(mMessageReceiver!=null){
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
     //       mMessageReceiver = null;
        }
         getActivity().stopService(new Intent(getActivity(),GetLocationService.class));
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {

        int Location = ContextCompat.checkSelfPermission(getActivity(),  Manifest.permission.ACCESS_FINE_LOCATION);
        return
                Location == PackageManager.PERMISSION_GRANTED;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, MY_PERMISSIONS_REQUEST_LOCATION);

    }
}
