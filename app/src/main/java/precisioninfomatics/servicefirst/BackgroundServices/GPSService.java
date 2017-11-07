package precisioninfomatics.servicefirst.BackgroundServices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.app.AlarmManager;
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
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import precisioninfomatics.servicefirst.Networks.LocationUpdate;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static android.os.SystemClock.elapsedRealtime;


public class GPSService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final long INTERVAL =  7000 *60;
    private static final long FASTEST_INTERVAL =  6000 *60;
    private LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    public GPSService() {

    }

    /*
     Called befor service  onStart method is called.All Initialization part goes here
    */
    @Override
    public void onCreate() {
        connectionopen();
    }

    public void connectionopen() {
        if(mLocationRequest==null) {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }
 /*
  You need to write the code to be executed on service start. Sometime due to memory congestion DVM kill the
  running service but it can be restarted when the memory is enough to run the service again.
 */

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("start", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        //    connectionopen();
        mGoogleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
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

        Log.d("GPS", "Location update started ..............: ");

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    /*
     Overrriden method of interface LocationListener called when location of gps device is changed.
     Location Object is received as a parameter.
     This method is called when location of GPS device is changed
    */
    @Override
    public void onLocationChanged(Location location) {
     double latitude = location.getLatitude();
     double longitude = location.getLongitude();
     LocationUpdate locationUpdate=new LocationUpdate();
     locationUpdate.LocationUpdate(this,latitude,longitude);
          Log.i("info", "Latitude :: " + latitude);

       Log.i("info", "Longitude :: " + longitude);
       /* Intent intent=new Intent("gps");
        Bundle bundle=new Bundle();
        bundle.putDouble("lat",latitude);
        bundle.putDouble("long",longitude);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);*/

    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e("taskremove", "taskremove");
        mGoogleApiClient.disconnect();
//        LocationServices.FusedLocationApi.removeLocationUpdates(
        //              mGoogleApiClient, this);
        Intent intent = new Intent("restartApps");
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
        mGoogleApiClient.disconnect();
    }
        Intent intent = new Intent("restartApps");
        sendBroadcast(intent);

    }

}
