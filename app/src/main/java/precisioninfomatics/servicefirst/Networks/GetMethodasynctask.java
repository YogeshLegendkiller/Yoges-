package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import precisioninfomatics.servicefirst.Activities.sfapp;

/**
 * Created by 4264 on 30-01-2017.
 */

public class GetMethodasynctask  extends AsyncTask<String, Void, Void> {
    public GetMethod getMethod;



    @Override
    protected Void doInBackground(String... params) {
    //    RequestQueue mQueue= Volley.newRequestQueue(Context,new HurlStack());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, params[0], new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String server_response = response.toString();
                        try {
                            getMethod.getDataFromServer(server_response);
                        }
                        catch (Exception e) {
                      //      getMethod.getDataFromServer("no value");
                             e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getMethod.getDataFromServer("no value");
                        Log.e("error", error.toString());
                    }
                });

     //   int socketTimeout = 30000;//30 seconds - change to what you want
   //     RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
      //  jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    //    mQueue.add(jsonArrayRequest);

        int socketTimeout = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonArrayRequest.setRetryPolicy(policy);
         sfapp.getInstance().addToRequestQueue(jsonArrayRequest);

        return null;
    }

}