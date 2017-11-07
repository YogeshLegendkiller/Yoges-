package precisioninfomatics.servicefirst.BackgroundServices;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import precisioninfomatics.servicefirst.Activities.VisitTravel;
import precisioninfomatics.servicefirst.DAO.GPSDAO;
import precisioninfomatics.servicefirst.Model.GpsModel;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static android.os.SystemClock.elapsedRealtime;

/**
 * Created by 4264 on 20/03/2017.
 */

public class  TravelService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final long INTERVAL = 1000 *30;
    private static final long FASTEST_INTERVAL = 1000 * 30;
    private GPSDAO gpsdao;
    private LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    public TravelService() {

    }

    /*
     Called befor service  onStart method is called.All Initialization part goes here
    */
    @Override
    public void onCreate() {
        connectionopen();
        gpsdao=new GPSDAO(this);
    }

    public void connectionopen() {
        if(mLocationRequest==null) {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        int check=0;
       if(intent!=null) {
          check = intent.getIntExtra("startservice", 0);
       }
            int servicestart=SharedPreferenceManager.getServiceInteger("serviceStart",0,this);
              if(servicestart==1||check==1){
                mGoogleApiClient.connect();
        }
            else {
                Log.d("no","don't start");
            }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


 /*
  Overriden method of the interface GooglePlayServicesClient.OnConnectionFailedListener .
  called when connection to the Google Play Service are not able to connect
 */

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
  /*
   * Google Play services can resolve some errors it detects. If the error
   * has a resolution, try sending an Intent to start a Google Play
   * services activity that can resolve error.
   */
        Log.d("GPS", "Connection failed: " + connectionResult.toString());
    }

    /*
     This is overriden method of interface GooglePlayServicesClient.ConnectionCallbacks which is called
     when locationClient is connecte to google service.
     You can receive GPS reading only when this method is called.So request for location updates from this
     method rather than onStart()

    */
    @Override
    public void onConnected(Bundle arg0) {
        Log.i("info", "Location Client is Connected");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
          LocationServices.FusedLocationApi.requestLocationUpdates(
               mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
    //    Intent in = new Intent("latlong");
    //    in.putExtra("lat",latitude);
    //    in.putExtra("long",longitude);
        GpsModel gpsModel=new GpsModel();
        gpsModel.setLongtitude(longitude);
        gpsModel.setLatitude(latitude);
        gpsModel.setFlag(1);
        gpsdao.InsertGps(gpsModel);
        Log.i("travel", "Latitude :: " + latitude);
        Log.i("travel", "Longitude :: " + longitude);
  //    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e("TravelServicetaskremove", "TravelServicetaskremove");
        mGoogleApiClient.disconnect();
        Intent intent = new Intent("restartTravel");
        sendBroadcast(intent);
        Intent restartServiceIntent = new Intent(this, this.getClass());
        PendingIntent restartServicePendingIntent = PendingIntent.getService(
                getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmService.set(ELAPSED_REALTIME, elapsedRealtime() + 1000,
                restartServicePendingIntent);
    }

    @Override
    public void onDestroy() {
        Log.e("destroy", "onDestroy");
        super.onDestroy();
        if(mGoogleApiClient.isConnected()){
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mGoogleApiClient.disconnect();}
        gpsdao.deleteGpsRecords();
        NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();
        //Intent intent = new Intent("restartTravel");
        //sendBroadcast(intent);

    }

}
