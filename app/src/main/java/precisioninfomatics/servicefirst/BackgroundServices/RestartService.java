package precisioninfomatics.servicefirst.BackgroundServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartService extends BroadcastReceiver {
    public RestartService() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("restart","restartservice");
        context.startService(new Intent(context, GPSService.class));
      //  context.startService(new Intent(context,TravelService.class));
    }
}
