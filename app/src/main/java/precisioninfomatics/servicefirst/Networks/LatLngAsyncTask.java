package precisioninfomatics.servicefirst.Networks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

/**
 * Created by 4264 on 23/08/2017.
 */

public  class LatLngAsyncTask extends AsyncTask<String, Void, String[]> {
    private ProgressDialog dialog;
    private String location;
    private String address;
    LatLngAsyncTask(String address, Context context) {
        this.address = address;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected String[] doInBackground(String... params) {
        String response;
        try {
            String url_json = "https://maps.googleapis.com/maps/api/geocode/json?";
            //Log.d("url","http://maps.google.com/maps/api/geocode/json?address="+address+"&sensor=false");
            try {
                // encoding special characters like space in the user input place
                location = URLEncoder.encode(address, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String addresss = "address=" + location;
            String sensor = "sensor=false";
            url_json = url_json + addresss + "&" + sensor;
            Log.d("url", url_json);
            response = getLatLongByURL(url_json);
            //     Log.d("response",""+response);
            return new String[]{response};
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String... result) {
        if (result != null) {
            try {

                //   Log.d("result", result[0]);
                JSONObject jsonObject = new JSONObject(result[0]);

                double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");

                //   Log.d("latitude", "" + lat);
                //    Log.d("longitude", "" + lng);
                SharedPreferenceManager.puString("adress", address);
                SharedPreferenceManager.puString("addresslat", String.valueOf(lat));
                SharedPreferenceManager.puString("addresslong", String.valueOf(lng));
                SharedPreferenceManager.putInteger("addressflag", 1);
                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    private   String getLatLongByURL(String requestURL) {
        URL url;
        HttpURLConnection conn=null;
        String response = "";
        try {
            url = new URL(requestURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }}
        return response;
    }

}