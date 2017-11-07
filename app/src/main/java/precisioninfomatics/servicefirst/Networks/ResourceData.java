package precisioninfomatics.servicefirst.Networks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import precisioninfomatics.servicefirst.Activities.Incident;
import precisioninfomatics.servicefirst.Activities.VisitTravel;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.PendingClassificationDAO;
import precisioninfomatics.servicefirst.DAO.ResourceDAO;
import precisioninfomatics.servicefirst.Fragments.IncidentView;
import precisioninfomatics.servicefirst.HelperClass.FileChoosers;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.Model.ResourceModel;

/**
 * Created by 4264 on 15-02-2017.
 */

public class ResourceData   {
    private ResourceDAO resourceDAO;
    public ResourceData(final  Context mcontext,final  int id){
         resourceDAO=new ResourceDAO(mcontext);
        String url= URL.SF_URL+"/getresourcelist/"+id;
    //    Log.d("resource_url",url);
        VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void getDataFromServer(String obj) {
                try {
                    Log.d("resource",obj);
                    JSONArray jsonArray=new JSONArray(obj);
                    int length=jsonArray.length();
                    if(length==0){
                        IncidentDAO incidentDAO=new IncidentDAO(mcontext);
                        incidentDAO.DeleteIncident(id);
                        Intent intent=new Intent(mcontext,Incident.class);
                        mcontext.startActivity(intent);
                        Toast.makeText(mcontext,"This Incident May Unassigned",Toast.LENGTH_SHORT).show();
                    }
                    resourceDAO.DeleteResource(id);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String url=URL.SF_FotoURL +jsonObject.getString("PhotoUrl");
                        boolean result = Utility.checkPermission(mcontext);
                        File downloadfile=Utility.GetFileName(mcontext, "ServiceFirst/SF_IMG",jsonObject.getInt("ResourceID")+"_inc"+ ".jpeg");
                        if (result) {
                            if(downloadfile.exists()){
                                Log.d("isexist", " exist");
                                ResourceValue(jsonObject,downloadfile,mcontext,id);
                            }
                            else {
                                Log.d("isexist", "not exist");
                                DownloadTask downloadTask = new  DownloadTask(downloadfile,jsonObject,id,mcontext);
                                downloadTask.execute(url);
                                Log.d("path", downloadfile.getPath());
                            }
                        }
                        else {
                            ResourceValue(jsonObject,downloadfile,mcontext,id);
                        }
                    }}catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
       }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private  class DownloadTask extends AsyncTask<String, Integer, String> {
       private File mTargetFile;
        private JSONObject jsonObject;
        private Integer id;
        private Context mcontext;
        public DownloadTask( File targetFile,JSONObject jsonobj,int id,Context context) {
            this.mTargetFile = targetFile;
            this.mcontext=context;
            this.jsonObject=jsonobj;
            this.id=id;
            Log.i("DownloadTask", "Constructor done");
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
               java.net.URL url=new java.net.URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Encoding", "identity");
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                Log.i("DownloadTask", "Response " + connection.getResponseCode());

                int fileLength = connection.getContentLength();
                input = connection.getInputStream();
                output = new FileOutputStream(mTargetFile, false);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        Log.i("DownloadTask", "Cancelled");
                        input.close();
                        return null;
                    }
                     // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                   //     publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ResourceValue(jsonObject,mTargetFile,mcontext,id);
          }
    }
    private  void ResourceValue(JSONObject jsonObject,File mTargetFile,Context mcontext,int id){
        try {
            ResourceModel resourceModel=new ResourceModel();
            ResourceDAO resourceDAO=new ResourceDAO(mcontext);
            resourceModel.setResourceID(jsonObject.getInt("ResourceID"));
            resourceModel.setEmployeeName(jsonObject.getString("ResourceName"));
            resourceModel.setTelephone(jsonObject.getString("Telephone"));
            resourceModel.setEmail(jsonObject.getString("Email"));
            resourceModel.setStatus(jsonObject.getInt("Status"));
            resourceModel.setDesignation(jsonObject.getString("Designation"));
            String path = mTargetFile.getPath();
            Log.d("path", path);
            resourceModel.setLocalPath(path);
            resourceModel.setStatusText(jsonObject.getString("VisitStatusName"));
            resourceModel.setResourceAllocationID(jsonObject.getInt("ResourceAllocationID"));
            resourceModel.setInstruction(jsonObject.getString("Instruction"));
            resourceModel.setAssignedDate(jsonObject.getString("AssignedDateTime"));
            resourceModel.setIncidentID(id);
            resourceModel.setResourceStatus(jsonObject.getString("StatusName"));
            resourceModel.setPhotourl(jsonObject.getString("AssignedDateTime"));
            resourceDAO.InsertOrUpdateResourceView(resourceModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
