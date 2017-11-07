package precisioninfomatics.servicefirst.BackgroundServices;

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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import precisioninfomatics.servicefirst.Networks.LocationUpdate;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static android.os.SystemClock.elapsedRealtime;

public class GetLocationService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
private static final long INTERVAL =  1000 *60;
private static final long FASTEST_INTERVAL =  1000 *60;
private LocationRequest mLocationRequest;
   private      GoogleApiClient mGoogleApiClient;

     public GetLocationService() {

        }

    /*
     Called befor service  onStart method is called.All Initialization part goes here
    */
@Override
public void onCreate() {
    Log.e("oncreate", "oncreate");
        connectionopen();

}

public void connectionopen() {
        if(mLocationRequest==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        }
        }
 /*
  You need to write the code to be executed on service start. Sometime due to memory congestion DVM kill the
  running service but it can be restarted when the memory is enough to run the service again.
 */

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("locreq", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
            mGoogleApiClient.connect();
           return START_NOT_STICKY;
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

    Location mLastLocations = LocationServices.FusedLocationApi.getLastLocation(
            mGoogleApiClient);
    Log.d("locservice","ready");

    if(mLastLocations!=null){
        Intent intent=new Intent("gps");
        Bundle bundle=new Bundle();
        bundle.putDouble("lat",mLastLocations.getLatitude());
        bundle.putDouble("long",mLastLocations.getLongitude());
        intent.putExtras(bundle);
        //  intent.putExtras(bundle);
        Log.d("locservice","last");
        // intent.setAction("gps");
        Log.i("locservice", "Latitude :: " + mLastLocations.getLatitude());
        Log.i("locservice", "Longitude :: " + mLastLocations.getLongitude());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
    else {
        Log.i("locservice", "req");
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }




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
        Intent intent=new Intent("gps");
        Bundle bundle=new Bundle();
        bundle.putDouble("lat",latitude);
        bundle.putDouble("long",longitude);
        intent.putExtras(bundle);
        //  intent.putExtras(bundle);
        // intent.setAction("gps");
        Log.i("locservice", "Latitude :: " + latitude);
        Log.i("locservice", "Longitude :: " + longitude);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
}



    @Override
    public void onDestroy() {
         super.onDestroy();
        if(mGoogleApiClient.isConnected()){
            Log.e("destroygeo", "onDestroy");

            LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
    }}
 }
