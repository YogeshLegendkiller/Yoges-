package precisioninfomatics.servicefirst.BackgroundServices;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.Networks.IncidentSummaryData;
import precisioninfomatics.servicefirst.Networks.PieData;
import precisioninfomatics.servicefirst.Networks.VisitData;
import precisioninfomatics.servicefirst.Networks.VisitViewData;

public class Background extends IntentService {

    public Background() {
        super("Background");
    }

    // TODO: Rename actions, choose action names that describe tasks that this




    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("start", "onStartCommand");
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String fromdate=null;
        String todate=null;
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.MONTH,0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date yearStartDate = cal.getTime();
        fromdate=dateFormat.format(yearStartDate);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date yearEndDate = cal.getTime();
        todate=dateFormat.format(yearEndDate);
        IncidentDAO incidentDAO = new IncidentDAO(this);
        incidentDAO.DeleteOldIncident();
        new PieData(this,fromdate,todate);
        new IncidentSummaryData(this,fromdate,todate);
    }



}






