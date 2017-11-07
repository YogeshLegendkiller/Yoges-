package precisioninfomatics.servicefirst.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import precisioninfomatics.servicefirst.BackgroundServices.TravelService;
import precisioninfomatics.servicefirst.CursorLoaders.VisitTravelLoader;
import precisioninfomatics.servicefirst.DAO.GPSDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.Fragments.Visit;
import precisioninfomatics.servicefirst.Fragments.VisitStatus;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.GpsModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Networks.MapFile;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

import static precisioninfomatics.servicefirst.Activities.Dashboard.getLatLongByURL;

public class VisitTravel extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,VisitStatus.OnCompleteListener,
        LocationListener, LoaderManager.LoaderCallbacks<Cursor>,GoogleMap.OnPolylineClickListener{
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private SimpleDateFormat dateFormat;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private GPSDAO gpsdao;
    private ProgressDialog progressDialog;
    private String  customeraddress,bottomtext,duration, JsonResponse, address,jsonresponse,destination_lat, destination_long;
    private VisitDAO visitDAO;
    private ProgressBar progressDialogTravel;
    private Long primaryID;
    private PolylineOptions polylineOptions;
    private List<Polyline> PlottedPloyline;
    private EditText edt;
    private Integer id, flag, incidentid, userid,test;
    private Button starttravel,stoptravel;
    private   AlertDialog alertDialog1,addressdialog;
    private TextView heading,durationtextview;
    private PowerManager.WakeLock wakeLock;
    private BottomSheetBehavior bottomSheetBehavior;
    private List<GpsModel> list;
   // final CharSequence[] maproute = {"Office", "Customer", "Home"};
   final CharSequence[] maproute = {"Customer", "Home"};
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_travel);
        gpsdao = new GPSDAO(VisitTravel.this);
        boolean   check= Utility.isNetworkAvailable(VisitTravel.this);
        if (check) {
            if(!checkPermission()){
                Toast.makeText(this,"Please Enable Location Permission From Setting",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VisitTravel.this, IncidentView.class);
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                createLocationRequest();
                buildGoogleApiClient();
                SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFrag.getMapAsync(this);
                Settingsapi();
                progressDialog = new ProgressDialog(VisitTravel.this);
                heading = findViewById(R.id.destinationaddrs);
                durationtextview = findViewById(R.id.duration);
                starttravel = findViewById(R.id.start_travel);
                stoptravel = findViewById(R.id.stop_travel);
                polylineOptions = new PolylineOptions();
                progressDialogTravel = findViewById(R.id.progresstravel);
                progressDialog.setMessage("Fetching Location Updates");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                View bottomSheet = findViewById(R.id.bottom_sheet);
                LoginDAO loginobj = new LoginDAO(this);
                userid = loginobj.getUserID();
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                list = new ArrayList<>();
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    flag = bundle.getInt("flag");
                    destination_lat = bundle.getString("destination_lat");
                    destination_long = bundle.getString("destination_long");
                    stoptravel.setVisibility(View.GONE);
                    progressDialogTravel.setVisibility(View.GONE);
                    customeraddress = bundle.getString("customeraddress");
                    incidentid = bundle.getInt("incidentid");
                    if (flag != 1) {
                        id = bundle.getInt("id");
                        incidentid = bundle.getInt("incidentid");
                        starttravel.setVisibility(View.VISIBLE);
                        stoptravel.setVisibility(View.VISIBLE);
                        //Checkin = bundle.getString("checkin");
                      //  Start_lat = bundle.getDouble("lat");
                      //  Start_long = bundle.getDouble("long");
                        address = bundle.getString("startaddress");
                        String distance = bundle.getString("estimateddistance");
                        buildGoogleApiClient();
                        createLocationRequest();
                        Settingsapi();
                        setBottomSheet(distance + " " + bundle.getString("duration"), "22, Habibullah Road, 1st Floor, T Nagar, Chennai, Tamil Nadu 600017", 2);
                    }
                }
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                if (powerManager != null) {
                    wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                            "WakelockTag");
                }
                wakeLock.acquire(10*60*1000L /*10 minutes*/);
                visitDAO = new VisitDAO(VisitTravel.this);
                starttravel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mLastLocation != null) {
                                    final String checkin_to_server = dateFormat.format(new Date());
                                    final String checkin_view = ViewDateTimeFormat.DateView(checkin_to_server, 1);
                                    final double startinglat = mLastLocation.getLatitude();
                                    final double startinglong = mLastLocation.getLongitude();
                                    if (jsonresponse != null) {
                                        final GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
                                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                            @Override
                                            public void onSnapshotReady(Bitmap snapshot) {
                                                try {
                                                    progressDialogTravel.setVisibility(View.VISIBLE);
                                                    String[] file = CreateFile(snapshot).split(",,");
                                                    SharedPreferenceManager.puString("startpath", file[0]);
                                                    String address_kilometer = getKilometer(jsonresponse, 1);
                                                    String[] view_date = new String[0];
                                                    if (address_kilometer != null) {
                                                        view_date = address_kilometer.split(",,");
                                                    }
                                                    SharedPreferenceManager.puString("startlat", String.valueOf(startinglat));
                                                    SharedPreferenceManager.puString("startlong", String.valueOf(startinglong));
                                                    VisitModel visitModel = new VisitModel();
                                                    visitModel.setStartLat(String.valueOf(startinglat));
                                                    visitModel.setStartLong(String.valueOf(startinglong));
                                                    visitModel.setCheckInDate(checkin_to_server);
                                                    visitModel.setEstimatedDistance(view_date[0]);
                                                    visitModel.setTravelrVisitFlag(2);
                                                    visitModel.setIsCallCompleted(1);
                                                    visitModel.setMapOrginFilepath(file[0]);
                                                    visitModel.setIncidentID(incidentid);
                                                    visitModel.setDuration(duration);
                                                    visitModel.setMaporOther(1);
                                                    visitModel.setUserID(userid);
                                                    visitModel.setCreatedat(dateFormat.format(new Date()));
                                                    visitModel.setCreatedby(userid);
                                                    visitModel.setStartAddress(view_date[1]);
                                                    map_marker_start(startinglat, startinglong, checkin_view);
                                                    primaryID = visitDAO.InsertTravel(visitModel);
                                                    id = primaryID.intValue();
                                                    SharedPreferenceManager.puString("service_address", view_date[1]);
                                                    SharedPreferenceManager.putInteger("serviceStart", 1);
                                                    SharedPreferenceManager.puString("ServiceCheckin", checkin_to_server);
                                                    ForeGroundNotification(view_date[0], duration, startinglat, startinglong, checkin_to_server, view_date[1], incidentid, id, view_date[0]);
                                                    Intent intent = new Intent(VisitTravel.this, TravelService.class);
                                                    intent.putExtra("startservice", 1);
                                                    startService(intent);
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            progressDialogTravel.setVisibility(View.GONE);
                                                            stoptravel.setVisibility(View.VISIBLE);
                                                        }
                                                    }, 2000);

                                                } catch (Exception e) {
                                                    Log.e("err", e.toString());
                                                    Toast.makeText(VisitTravel.this, "Map Insert"+e.toString(), Toast.LENGTH_SHORT).show();

                                                }

                                                Log.e("screen", "screen");
                                            }
                                        };
                                        GoogleMap.OnMapLoadedCallback mapLoadedCallback = new GoogleMap.OnMapLoadedCallback() {
                                            @Override
                                            public void onMapLoaded() {
                                                starttravel.setVisibility(View.GONE);
                                                mGoogleMap.snapshot(callback);
                                                Log.e("inside", "load");

                                            }
                                        };
                                        mGoogleMap.setOnMapLoadedCallback(mapLoadedCallback);
                                    } else {
                                        starttravel.setVisibility(View.VISIBLE);
                                        Toast.makeText(VisitTravel.this, "Please Try After SomeTime", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    starttravel.setVisibility(View.VISIBLE);
                                    Toast.makeText(VisitTravel.this, "Please Wait Till We Receive Location Updates", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                stoptravel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mLastLocation != null) {
                                    final VisitModel visitModel = new VisitModel();
                                    mGoogleMap.clear();
                                    progressDialogTravel.setVisibility(View.VISIBLE);
                                    starttravel.setVisibility(View.GONE);
                                    stoptravel.setVisibility(View.GONE);
                                    GpsCordinates();
                                    final GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                        @Override
                                        public void onSnapshotReady(Bitmap snapshot) {
                                            try {
                                                Handler handler = new Handler();
                                                String[] file = CreateFile(snapshot).split(",,");
                                                double lat = mLastLocation.getLatitude();
                                                double longt = mLastLocation.getLongitude();
                                                address = SharedPreferenceManager.getServiceString("service_address", "", VisitTravel.this);
                                                String startlat = SharedPreferenceManager.getServiceString("startlat", "", VisitTravel.this);
                                                String startlong = SharedPreferenceManager.getServiceString("startlong", "", VisitTravel.this);
                                                try {
                                                    String jsonresponse = new GetDistance(2, 4,"",null).execute(Double.valueOf(startlat), Double.valueOf(startlong), lat, longt).get();
                                                    String address_kilometer = getKilometer(jsonresponse, 2);
                                                    String[] view_date = address_kilometer.split(",,");
                                                    visitModel.setEndLat(String.valueOf(lat));
                                                    visitModel.setEndLong(String.valueOf(longt));
                                                    visitModel.setID(id);
                                                    visitModel.setCheckInDate(SharedPreferenceManager.getServiceString("ServiceCheckin", "", VisitTravel.this));
                                                    visitModel.setLastModifiedat(dateFormat.format(new Date()));
                                                    visitModel.setLastmodifiedby(userid);
                                                    visitModel.setIncidentID(incidentid);
                                                    visitModel.setCheckOutDate(dateFormat.format(new Date()));
                                                    visitModel.setIsCallCompleted(0);
                                                    visitModel.setKilometer(view_date[0]);
                                                    visitModel.setEndAddress(view_date[1]);
                                                    SharedPreferenceManager.putInteger("serviceStart", 0);
                                                    visitModel.setTransportModeText("TWO WHEELER");
                                                    stopService(new Intent(VisitTravel.this, TravelService.class));
                                                    gpsdao.deleteGpsRecords();
                                                    visitModel.setStartAddress(address);
                                                    visitModel.setIsFileExist(1);
                                                    visitModel.setIsSent(1);
                                                    visitModel.setMaporOther(1);
                                                    visitModel.setTransportMode(SharedPreferenceManager.getInteger("vehicletype", 0));
                                                    visitModel.setDestiFilePath(file[0]);
                                                    visitModel.setDocumentName(file[1]);
                                                    visitModel.setDocumentPath(file[0]);
                                                    SharedPreferences settings = VisitTravel.this.getSharedPreferences("lat_long", Context.MODE_PRIVATE);
                                                    settings.edit().clear().apply();
                                                } catch (InterruptedException | ExecutionException e) {
                                                    e.printStackTrace();
                                                }
                                                visitDAO.UpdateTravel(visitModel);
                                                MapFile mapFile = new MapFile();
                                                mapFile.PostFile(VisitTravel.this);
                                                NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                if (notifManager != null) {
                                                    notifManager.cancelAll();
                                                }
                                                final ProgressDialog progressDialog=Utility.showProgressDialog(VisitTravel.this,"Please wait");
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(VisitTravel.this, "Travel Completed", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(VisitTravel.this, IncidentView.class);
                                                        setResult(RESULT_OK, intent);
                                                        finish();
                                                    }
                                                }, 6000);

                                            } catch (Exception e) {
                                                Log.e("err", e.toString());
                                                starttravel.setVisibility(View.GONE);
                                                progressDialogTravel.setVisibility(View.GONE);
                                                stoptravel.setVisibility(View.VISIBLE);
                                                Toast.makeText(VisitTravel.this, "Map Send"+e.toString(), Toast.LENGTH_SHORT).show();

                                            }
                                }
                                    };
                                    GoogleMap.OnMapLoadedCallback mapLoadedCallback = new GoogleMap.OnMapLoadedCallback() {
                                        @Override
                                        public void onMapLoaded() {
                                            mGoogleMap.snapshot(callback);
                                            Log.e("inside", "load");
                                        }
                                    };
                                    mGoogleMap.setOnMapLoadedCallback(mapLoadedCallback);
                                } else {
                                    Toast.makeText(VisitTravel.this, "Please Wait Fetching Location", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });

                getLoaderManager().initLoader(1, null, VisitTravel.this);
            }
        } else {
            showAlertDialog();
        }

    }

    public void ForeGroundNotification(String Kilometer,String duration,Double startlat,Double startlong,String checkin,String startaddress,int incidentid,int visitid,String estimated_distance){
        android.support.v4.app.NotificationCompat.Builder   builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_1481892667_vespa)
                        .setOngoing(true)
                        .setContentTitle("Started at:"+ " " +startaddress)
                        .setContentText("Estimated Distance: " + " " +Kilometer);
        Intent notificationIntent = new Intent(this, VisitTravel.class);
        Bundle bundle=new Bundle();
        bundle.putString("duration",duration);
        bundle.putInt("flag",2);
        bundle.putDouble("lat",startlat);
        bundle.putDouble("long",startlong);
        bundle.putString("checkin",checkin);
        bundle.putString("startaddress",startaddress);
        bundle.putInt("incidentid",incidentid);
        bundle.putInt("id",visitid);
        bundle.putString("estimateddistance",estimated_distance);
        notificationIntent.putExtras(bundle);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(0, builder.build());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onresume", "on resume");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("onstart","on start");
        if(mGoogleApiClient!=null){
            mGoogleApiClient.connect();
        }
       }
    public void Settingsapi() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                       startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(
                                    VisitTravel.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VisitTravel.this);
        builder.setTitle("Network Connectivity")
                .setMessage("Please Check Your Network Connectivity")
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(VisitTravel.this, IncidentView.class);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onPause() {
        super.onPause();
        //  progressDialog.dismiss();
        Log.d("onpause","onpause");
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        //       LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        if(alertDialog1!=null ){
            alertDialog1.dismiss();
           }
           if(addressdialog!=null){
               addressdialog.dismiss();
           }
        /*if(alertDialog1!=null){
            alertDialog1.dismiss();
            Log.d("insidedismiss","insidedismiss");
        }
        else {
            Log.e("null","alertnull");
        }
*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Log.d("mapready","ready");
        mGoogleMap.setMyLocationEnabled(true);
        //Initialize Google Play Services
       if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                createLocationRequest();
                mGoogleMap.setMyLocationEnabled(true);
            }
        } else {
           buildGoogleApiClient();
           createLocationRequest();
           mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(15 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocations = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.d("insideready","ready");
        if (mLastLocations != null) {
            LatLng latLng = new LatLng(mLastLocations.getLatitude(), mLastLocations.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    latLng, 12);
            mGoogleMap.animateCamera(cameraUpdate);
            progressDialog.dismiss();
            mLastLocation=mLastLocations;
            final double startinglat = mLastLocation.getLatitude();
            final double startinglong = mLastLocation.getLongitude();
            if(polylineOptions!=null&&alertDialog1!=null){
                alertDialog1.dismiss();
            }
            else {
                TravelDetail(startinglat,startinglong);
            }
       }
        else {
            if(mGoogleApiClient!=null){
                startLocationUpdates();
            }
        }}


    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Toast.makeText(this, "LocationNotUpdated", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(VisitTravel.this,
                    new String[]{android.Manifest.permission
                            .ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    20);
        } else {
            if (mGoogleApiClient.isConnected()) {
                Log.e("locupdate","connected");
                Location mLastLocations = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocations != null) {
                    LatLng latLng = new LatLng(mLastLocations.getLatitude(), mLastLocations.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                            latLng, 12);
                    mGoogleMap.animateCamera(cameraUpdate);
                }else {
                     LocationServices.FusedLocationApi.requestLocationUpdates(
                            mGoogleApiClient, mLocationRequest, this);
                }
            }
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

        public void map_marker_start(Double lat, Double longt, String title) {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(lat, longt);
        Log.d("lat", String.valueOf(latLng.longitude));
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mGoogleMap.addMarker(markerOptions).showInfoWindow();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
       if (mLastLocation != null) {
            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    latLng, 12);
            mGoogleMap.animateCamera(cameraUpdate);
               if(test!=null&&test==1){
                   if(polylineOptions!=null&&alertDialog1!=null){
                       alertDialog1.dismiss();
                       Log.e("value","value");
                   }
                   else {
                       TravelDetail(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                   }

               }
        }
  /*     polylineOptions = new PolylineOptions();
        mGoogleMap.setMyLocationEnabled(true);
        progressDialog.dismiss();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        routeObj.add(latLng);
        polylineOptions.addAll(routeObj);
        polylineOptions.width(5);
        Log.e("onchangecount",String.valueOf(routeObj));
        mGoogleMap.addPolyline(polylineOptions);
        progressDialog.dismiss();
    */
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    /*    return new CursorLoader(VisitTravel.this, GPSDAO.GPS_URI, null, null, null, null) {
            ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

            @Override
            public Cursor loadInBackground() {
                Cursor c;
                c = gpsdao.Gps();
                if (c != null) {
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());

                }
                return c;
            }
        };
    */
    return new VisitTravelLoader(VisitTravel.this,GPSDAO.GPS_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        list = gpsdao.listobj(data);
        List<LatLng> routeObj = new ArrayList<>();
        polylineOptions=new PolylineOptions();
        for (int i = 0; i < list.size(); i++) {
            LatLng latLng = new LatLng(list.get(i).getLatitude(), list.get(i).getLongtitude());
            progressDialog.dismiss();
            routeObj.add(latLng);
        }
        polylineOptions.addAll(routeObj);
        polylineOptions.width(9);
         polylineOptions.color(Color.parseColor("#2196f3"));
        mGoogleMap.addPolyline(polylineOptions);
        if(list.size()>0) {
            CameraAnimation(polylineOptions);
        }
        mGoogleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                try {
                String tag=polyline.getTag().toString();
                String [] tagarray=tag.split(",");
                String address_kilometer = getKilometerPolyline(jsonresponse,Integer.valueOf(tagarray[1]));
                String[] view_date = address_kilometer.split(",,");
                polyline.setTag(tagarray[0]+ "," +tagarray[1]);
                for (int i = 0;i< PlottedPloyline.size();i++)
                {
                    Polyline map_polyline = PlottedPloyline.get(i);
                    map_polyline.setColor(Color.parseColor("#9e9e9e"));
                    map_polyline.setZIndex(1);
                }
                polyline.setZIndex(2);
                polyline.setColor(Color.parseColor("#9c27b0"));
                setBottomSheet(view_date[0] + " " + tagarray[0],bottomtext,3);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        test=1;
                        progressDialog.dismiss();
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Intent intent = new Intent(VisitTravel.this, IncidentView.class);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onstop","on stop");
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
        if(alertDialog1!=null){
            alertDialog1.dismiss();
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    public void setBottomSheet(String jsonResponses,String addrs,int flag) {
        if(jsonResponses!=null) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            if (flag == 1) {
                String address_kilometer = getKilometer(jsonResponses, 1);
                if (address_kilometer != null) {
                    String[] view_date = address_kilometer.split(",,");
                    heading.setText(addrs);
                    durationtextview.setText(view_date[0] + " " + duration);
                }
            } else if (flag == 2) {
                heading.setText(addrs);
                durationtextview.setText(jsonResponses);
            } else {
                heading.setText(addrs);
                durationtextview.setText(jsonResponses);
            }
        }
    }

    public   String getKilometer(String jsonResponse,int flag) {
            String parsedDistance ;
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
                if(flag==1) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    if(alertDialog1!=null){
                        alertDialog1.dismiss();
                    }
                    if(mLastLocation!=null) {
                        TravelDetail(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    }
                }
                Toast.makeText(this,"Please Try After Sometime",Toast.LENGTH_SHORT).show();
                return null;
             }
            //  Log.d("addrss",start_adrs);
            return parsedDistance + " ,," + start_adrs;
          }
    public String getKilometerPolyline(String jsonResponse,int index) {
        String parsedDistance = null;
        String start_adrs = null;

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray array ;
        try {
            if (jsonObject != null) {
                array = jsonObject.getJSONArray("routes");
                JSONObject routes = array.getJSONObject(index);
                JSONArray legs = routes.getJSONArray("legs");
                JSONObject steps = legs.getJSONObject(0);
                JSONObject distances = steps.getJSONObject("distance");
                parsedDistance = distances.getString("text");
                start_adrs = steps.getString("start_address");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parsedDistance + " ,," + start_adrs;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void CameraAnimation(PolylineOptions polylineOptions) {
        try {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        List<LatLng> latLngs;
        if (polylineOptions.getPoints() != null) {
            latLngs = polylineOptions.getPoints();
            for (LatLng latLng : latLngs) {
                builder.include(latLng);
            }
            LatLngBounds bounds = builder.build();
            int padding = 130;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mGoogleMap.moveCamera(cameraUpdate);
            mGoogleMap.animateCamera(cameraUpdate);
        }}catch (Exception ex){
            Log.d("errcam",ex.toString());
        }
    }

    @Override
    public void onComplete(int id, String text) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        polyline.setColor(Color.BLUE);
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private ArrayList<LatLng> points;
        private int flag;
        public ParserTask(int flag){
            this.flag=flag;
        }
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionJSONParser parser = new DirectionJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
         //   Log.d("size of list",String.valueOf(result.size()));
            PlottedPloyline=new ArrayList<>();
            for (int i = result.size()-1; i >= 0; i--) {
           //     Log.d("loop",String.valueOf(i));
                points = new ArrayList<>();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    if (j == 0) {
                        duration = point.get("duration");
                        Log.d("duration", duration);
                        continue;
                    }
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                polylineOptions=new PolylineOptions();
                polylineOptions.addAll(points);
                polylineOptions.width(15);

                if(i==0) {
                    polylineOptions.color(Color.parseColor("#9c27b0"));
                }
                else {
                    polylineOptions.color(Color.parseColor("#9e9e9e"));
                }
                Polyline polyline=  mGoogleMap.addPolyline(polylineOptions);
                PlottedPloyline.add(polyline);
                polyline.setTag(duration + ","  +i);
                polyline.setClickable(true);
            }
            if(flag==1){
                bottomtext="Precision Infomatic Habibullah Road";
            }
            else if(flag==2){
                if(!destination_lat.equals("")&&!destination_long.equals("")) {
                    bottomtext=customeraddress;
                }
                else {
                    bottomtext=edt.getText().toString();
                }
            }
            else if(flag==3){
                bottomtext= SharedPreferenceManager.getServiceString("adress",null,VisitTravel.this);
            }
            setBottomSheet(jsonresponse, bottomtext,1);
            CameraAnimation(polylineOptions);
        }
    }

       private class GetDistance extends AsyncTask<Double, Void, String> {
        private ProgressDialog pd;
        private static final int READ_TIMEOUT = 6000;
        private static final int CONNECTION_TIMEOUT = 6000;
        private int flag,type;
        private String Customeraddress;
        private AlertDialog alterdialog;
        public GetDistance(int flag,int type,String customeradd,AlertDialog alert) {
            this.flag=flag;
            this.alterdialog=alert;
            this.type=type;
            this.Customeraddress=customeradd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(VisitTravel.this);
            pd.setMessage("Please wait");
            pd.show();
        }



        @Override
        protected String doInBackground(Double... strings) {
            URL url;
            try {
                url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + strings[0] + "," + strings[1] + "&destination=" + strings[2] + "," + strings[3] + "&sensor=false&units=metric&mode=driving&alternatives=true");
                Log.d("url","http://maps.googleapis.com/maps/api/directions/json?origin=" + strings[0] + "," + strings[1] + "&destination=" + strings[2] + "," + strings[3] + "&sensor=false&units=metric&mode=driving&alternatives=true");
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
            jsonresponse=result;
            if(flag==1) {
               alterdialog.dismiss();
                if(type==2){
                    setBottomSheet(jsonresponse,Customeraddress,1);
                }
                else if(type==3){
                    setBottomSheet(jsonresponse,"Loading...",1);
                }
                    new ParserTask(type).execute(result);
                }}
    }

    public String  CreateFile(Bitmap snapshot){
        File   screenshotfile=null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            snapshot.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Calendar c = Calendar.getInstance();
            screenshotfile=Utility.GetFileName(this, "ServiceFirst/Map",dateFormat.format(c.getTime())+ ".jpeg");
            SharedPreferenceManager.puString("startpath",screenshotfile.getPath());
            FileOutputStream outputStream = new FileOutputStream(screenshotfile);
            outputStream.write(stream.toByteArray());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return screenshotfile.getPath() + " ,," + screenshotfile.getName() ;
    }


    public void GpsCordinates(){
        getLoaderManager().restartLoader(1, null, VisitTravel.this);
        List<LatLng>list = gpsdao.listobj();
        PolylineOptions  polylineOptions=new PolylineOptions();
        polylineOptions.addAll(list);
        polylineOptions.width(9);
        polylineOptions.color(Color.parseColor("#2196f3"));
        mGoogleMap.addPolyline(polylineOptions);
        if(list.size()>0) {
            CameraAnimation(polylineOptions);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {

        int Location = ContextCompat.checkSelfPermission(VisitTravel.this,  Manifest.permission.ACCESS_FINE_LOCATION);
        return
                Location == PackageManager.PERMISSION_GRANTED;
    }
  public void TravelDetail(final double startinglat,final double startinglong){
        if(test!=null && test==1){
            test=0;
        }
      int servicecheck= SharedPreferenceManager.getServiceInteger("serviceStart",0,this);
      if(servicecheck==0){
      if(flag==1){
          final AlertDialog.Builder builders = new AlertDialog.Builder(VisitTravel.this);
          builders.setTitle("Choose Your Location");
          builders.setSingleChoiceItems(maproute, -1, new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int item) {
                  switch(item)
                  {
                      case 0:
                           if(flag!=2){
                              if(!destination_lat.equals("")&&!destination_long.equals("")){
                                  new GetDistance(1,2,customeraddress,alertDialog1).execute(startinglat,startinglong, Double.valueOf(destination_lat), Double.valueOf(destination_long));
                              }
                              else {
                                  if(destination_lat.equals("") &&destination_long.equals("")) {
                                      LayoutInflater inflater = VisitTravel.this.getLayoutInflater();
                                      final View dialogView = inflater.inflate(R.layout.alterdialog_editext, null);
                                      AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VisitTravel.this);
                                      dialogBuilder.setCancelable(false);
                                      dialogBuilder.setView(dialogView);
                                      edt = dialogView.findViewById(R.id.geolocation);
                                      dialogBuilder.setTitle("Location Provider");
                                      dialogBuilder.setMessage("Enter Address");
                                      edt.setText(customeraddress);
                                      //        Log.d("customeraddress",customeraddress);
                                      dialogBuilder.setPositiveButton("Get Location", new DialogInterface.OnClickListener() {
                                          public void onClick(DialogInterface dialog, int whichButton) {

                                          }
                                      });
                                      dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                          public void onClick(DialogInterface dialog, int whichButton) {
                                              //pass
                                          }
                                      });
                                      addressdialog = dialogBuilder.create();
                                      addressdialog.show();
                                      addressdialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              try {
                                                  if(!edt.getText().toString().isEmpty()) {
                                                  LatLng destinationlatlng = Utility.getLocationFromAddress(VisitTravel.this, edt.getText().toString());
                                               if (destinationlatlng != null) {
                                                      addressdialog.dismiss();
                                                      final double startinglat = mLastLocation.getLatitude();
                                                      final double startinglong = mLastLocation.getLongitude();
                                                      new GetDistance(1, 2, edt.getText().toString(), alertDialog1).execute(startinglat, startinglong, destinationlatlng.latitude, destinationlatlng.longitude);
                                                      map_marker_start(destinationlatlng.latitude, destinationlatlng.longitude, edt.getText().toString());
                                                      // setBottomSheet(jsonresponse, edt.getText().toString(),1);

                                              } else {
                                                  String jsonvalue=   new LatLngAsyncTask(edt.getText().toString()).execute().get();
                                                  LatLng destinationlatlngs=GpsPosition(jsonvalue);
                                                  if (destinationlatlngs != null||!edt.getText().toString().isEmpty()) {
                                                      addressdialog.dismiss();
                                                      final double startinglat = mLastLocation.getLatitude();
                                                      final double startinglong = mLastLocation.getLongitude();
                                                      new GetDistance(1,2,edt.getText().toString(),alertDialog1).execute(startinglat, startinglong, destinationlatlngs.latitude, destinationlatlngs.longitude);
                                                      map_marker_start(destinationlatlngs.latitude,destinationlatlngs.longitude,edt.getText().toString());
                                                      // setBottomSheet(jsonresponse, edt.getText().toString(),1);
                                                  }
                                                  Toast.makeText(VisitTravel.this, "Please Enter the Valid Address", Toast.LENGTH_SHORT).show();
                                              }
                                              //     new  LatLngAsyncTask(edt.getText().toString(),b,VisitTravel.this,mLastLocation.getLatitude(),mLastLocation.getLongitude()).execute();
                                                  }
                                                  else {
                                                      Toast.makeText(VisitTravel.this, "Please Enter the Valid Address", Toast.LENGTH_SHORT).show();
                                                  }
                                              }catch (Exception ex){
                                                  Toast.makeText(VisitTravel.this, "Please Enter the Valid Address", Toast.LENGTH_SHORT).show();

                                              }
                                          }

                                      });
                                  }
                              }
                          }
                          break;
                      case 1:
                          new GetDistance(1,3,"",alertDialog1).execute(startinglat,startinglong, Double.valueOf(SharedPreferenceManager.getServiceString("addresslat",null,VisitTravel.this)), Double.valueOf(SharedPreferenceManager.getServiceString("addresslong",null,VisitTravel.this)));
                        //  alertDialog1.dismiss();
                          break;
                  }
              }
          });
            alertDialog1 = builders.show();
         //   alertDialog1.show();

      }}else {
            Log.e("already exist","already exist");
        }
  }
    public static class LatLngAsyncTask extends AsyncTask<String, Void, String> {
         private String location;
         private String address;

        LatLngAsyncTask(String address) {
            this.address = address;
          }



        @Override
        protected String doInBackground(String... params) {
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
                return response;
            } catch (Exception e) {
                return null;
            }
        }
    }
  public LatLng GpsPosition(String value){
      LatLng latLng;
      try {

          JSONObject jsonObject = new JSONObject(value);
          double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                  .getJSONObject("geometry").getJSONObject("location")
                  .getDouble("lng");

          double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                  .getJSONObject("geometry").getJSONObject("location")
                  .getDouble("lat");
       latLng=new LatLng(lat,lng);
      } catch (JSONException e) {
          e.printStackTrace();
   //       Toast.makeText(context, "Please Try Again Later", Toast.LENGTH_SHORT).show();
           return null;
      }
      return latLng;
  }
}
