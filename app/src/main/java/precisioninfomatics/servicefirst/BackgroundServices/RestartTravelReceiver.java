package precisioninfomatics.servicefirst.BackgroundServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 4264 on 21/03/2017.
 */

public class RestartTravelReceiver  extends BroadcastReceiver  {
    public RestartTravelReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        context.startService(new Intent(context, TravelService.class));
      }
}
