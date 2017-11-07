package precisioninfomatics.servicefirst.Activities;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class sfapp extends Application {
    public static final String TAG = sfapp.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    private static sfapp mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }


    @Override public void onCreate() {
        super.onCreate();
         mInstance = this;

       StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
     StrictMode.setVmPolicy(builder.build());
   //     LeakCanary.install(this);

    }
    public static synchronized sfapp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


}
