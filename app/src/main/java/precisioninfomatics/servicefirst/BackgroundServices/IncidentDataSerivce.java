package precisioninfomatics.servicefirst.BackgroundServices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import precisioninfomatics.servicefirst.Networks.ClaimFieldNetwork;
import precisioninfomatics.servicefirst.Networks.IncidentData;
import precisioninfomatics.servicefirst.Networks.IncidentSummaryData;
import precisioninfomatics.servicefirst.Networks.PendingClassification;
import precisioninfomatics.servicefirst.Networks.PieData;
import precisioninfomatics.servicefirst.Networks.VisitStatus;

public class IncidentDataSerivce extends Service {
    public IncidentDataSerivce() {
    }
    @Override
    public void onCreate() {
        Log.e("oncreate", "dash");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("start", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        new ClaimFieldNetwork(this);
        new PendingClassification(this);
        new VisitStatus(this);
        return START_NOT_STICKY;
    }


}
