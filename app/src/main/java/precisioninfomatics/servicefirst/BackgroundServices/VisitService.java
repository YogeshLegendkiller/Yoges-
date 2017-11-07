package precisioninfomatics.servicefirst.BackgroundServices;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import precisioninfomatics.servicefirst.Networks.FileTravel;
import precisioninfomatics.servicefirst.Networks.VisitData;
import precisioninfomatics.servicefirst.Networks.VisitViewData;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class VisitService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this

    public VisitService() {
        super("VisitService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
           if(intent!=null){
               int id=intent.getIntExtra("id",0);
               VisitViewData visitViewData=new VisitViewData();
               visitViewData.VisitView(this,id,0,1,null);
               FileTravel fileTravel=new FileTravel();
               fileTravel.PostFile(this);

           }
    }


}
