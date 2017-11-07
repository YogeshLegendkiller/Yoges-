package precisioninfomatics.servicefirst.Networks;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import precisioninfomatics.servicefirst.Activities.Dashboard;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

/**
 * Created by 4264 on 15/04/2017.
 */

public class FireBaseInstanceID  extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        //Get hold of the registration token
       // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
   //     SharedPreferenceManager.init(this);
     //   Log.d("id", "Refreshed token: " + refreshedToken);
       // SharedPreferenceManager.puString("FCMRegID",refreshedToken);
        FCMTokeRegistration fcmTokeRegistration=new FCMTokeRegistration();
        fcmTokeRegistration.SendTokenToServer(this);

    }

}
