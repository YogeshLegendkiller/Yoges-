package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.Activities.sfapp;

/**
 * Created by 4264 on 07-02-2017.
 */

public class GetJsonObjectResponse  extends AsyncTask<String, Void, Void> {
     public GetMethod getMethod;

    @Override
    protected Void doInBackground(String... params) {
        JsonObjectRequest jsonObjRequestss = new JsonObjectRequest(Request.Method.GET, params[0], new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String server_response = response.toString();
                        try {
                            getMethod.getDataFromServer(server_response);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "");
                    }
                });

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjRequestss.setRetryPolicy(policy);
        sfapp.getInstance().addToRequestQueue(jsonObjRequestss, "jsonobj");
           return null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        getMethod=null;
    }
}