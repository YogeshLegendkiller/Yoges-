package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 4264 on 10/03/2017.
 */

public class PostMethodToServer extends AsyncTask<String, Void, String> {

    private int Server_response;
    public      PostMethod getResponse=null;
    private String JsonResponse;
    private String URLs;
//    private Context mcontext;
    public PostMethodToServer(String URLs, Context context) {
        this.URLs = URLs;
       // this.mcontext=context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(final String ...params) {
        HttpURLConnection urlConnection=null;
        String  jsonObject= params[0];
        byte[] post = jsonObject.getBytes();
        int request_post = post.length;
        BufferedReader reader = null;
        try {
            Thread.sleep(2000);
            URL url = new URL(URLs);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setConnectTimeout(30000);
           urlConnection.setRequestProperty("Content-Length", Integer.toString(request_post));
            urlConnection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(jsonObject);
            writer.close();
            Server_response = urlConnection.getResponseCode();
            if (Server_response >= 400 && Server_response <= 499) {
                Log.e("", "HTTPx Response: " + Server_response + " - " + urlConnection.getResponseMessage());
            }
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                return null;
            }
            JsonResponse = buffer.toString();
            Log.i("response", JsonResponse);
            return JsonResponse;
        } catch (java.net.SocketTimeoutException e) {
            return null;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("", "Error closing stream", e);
                }
            }
        }

    }
    @Override
    protected void onPostExecute(String  aVoid){
        super.onPostExecute(aVoid);
        if(aVoid==null ){
            getResponse.PostDataToServer("no value");
        }
        else {
            if(Server_response==200){
                getResponse.PostDataToServer(JsonResponse);
            }else{
                Log.d("unabletoreachserver","unabletoreachserver");
            }
        }
    }
}

