package precisioninfomatics.servicefirst.Networks;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import precisioninfomatics.servicefirst.Activities.sfapp;

/**
 * Created by 4264 on 14/08/2017.
 */

public class VolleyJsonArray {

    public  static  void makeJsonArraytRequest(String url, final  GetMethod getMethod) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        getMethod.getDataFromServer(response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getMethod.getDataFromServer(error.toString());
                    }
                }) ;


        int socketTimeout = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

          jsonArrayRequest.setRetryPolicy(policy);
            sfapp.getInstance().addToRequestQueue(jsonArrayRequest);



    }}