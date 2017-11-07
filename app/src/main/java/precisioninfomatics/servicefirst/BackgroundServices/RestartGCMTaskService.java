package precisioninfomatics.servicefirst.BackgroundServices;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import precisioninfomatics.servicefirst.Activities.Dashboard;
import precisioninfomatics.servicefirst.DAO.GPSDAO;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static android.os.SystemClock.elapsedRealtime;

/**
 * Created by 4264 on 27/02/2017.
 */

public class RestartGCMTaskService extends GcmTaskService {
    private static final String TAG = RestartGCMTaskService.class.getSimpleName();

    public static final String TASK_GET_LOCATION_ONCE = "location_oneoff_task";
    public static final String TASK_GET_LOCATION_PERIODIC = "location_periodic_task";
     public RestartGCMTaskService() {

    }


    @Override

    public void onInitializeTasks() {
        super.onInitializeTasks();
        GcmNetworkManager.getInstance(this).schedule(startPeriodicLocationTask());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("oncreate", "onc");
        GcmNetworkManager.getInstance(this).schedule(startPeriodicLocationTask());
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        return super.onStartCommand(intent, i, i1);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("removed", "remove");
        Intent restartServiceIntent = new Intent(this, this.getClass());
        PendingIntent restartServicePendingIntent = PendingIntent.getService(
                getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmService.set(ELAPSED_REALTIME, elapsedRealtime() + 1000,
                restartServicePendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public PeriodicTask startPeriodicLocationTask() {
        Log.d("periodictaskservice", "startPeriodicLocationTask");
        GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(getApplicationContext());
        PeriodicTask taskBuilder = new PeriodicTask.Builder()
                .setService(RestartGCMTaskService.class)
                .setTag(TASK_GET_LOCATION_PERIODIC)
                .setPeriod(18000).setFlex(18000)
                .setPersisted(true).build();
        mGcmNetworkManager.schedule(taskBuilder);
        return taskBuilder;
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        Log.d(TAG, "onRunTask: " + taskParams.getTag());
        String tag = taskParams.getTag();
        int result = GcmNetworkManager.RESULT_SUCCESS;

        switch (tag) {
            case TASK_GET_LOCATION_ONCE:
          //      getLastKnownLocation();
                break;

            case TASK_GET_LOCATION_PERIODIC:
                Log.d("periodic", "period");
                startService(new Intent(this, GPSService.class));
            //    GPSDAO gpsdao=new GPSDAO(this);
            //    boolean flag=gpsdao.gpsCheck();
            //    Log.d("flagforservice",String.valueOf(flag));
                int servicecheck= SharedPreferenceManager.getServiceInteger("serviceStart",0,this);
                Log.d("servicecheckforservice",String.valueOf(servicecheck));
                if(servicecheck==1){
                    startService(new Intent(this,TravelService.class));
                }
                else {
                    Log.d("no","no travel");

                }
                break;
        }



        return result;
    }

}
