package precisioninfomatics.servicefirst.HelperClass;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.Activities.VisitUpdation;
import precisioninfomatics.servicefirst.Fragments.IncidentView;
import precisioninfomatics.servicefirst.Model.ListParameterizedType;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static precisioninfomatics.servicefirst.Activities.Dashboard.RequestPermissionCode;

/**
 * Created by 4264 on 20-12-2016.
 */
public class  Utility {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_Camera = 1234;


  /*  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }*/
  public static ProgressDialog showProgressDialog(Context context, String message){
      ProgressDialog m_Dialog = new ProgressDialog(context);
      m_Dialog.setMessage(message);
      m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      m_Dialog.setCancelable(false);
      m_Dialog.show();
      return m_Dialog;
  }
    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<android.location.Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            if(address.size()>=1) {
                android.location.Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                p1 = new LatLng(location.getLatitude(), location.getLongitude() );
            }
        } catch (IOException ex) {

            ex.printStackTrace();
            return null;
        }

        return p1;
    }
    public static void  permission(final Context context){
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
    }
            if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("Camera permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_Camera);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();

                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_Camera);
                    }

}
    }
}}
    public static  <T> List<T> GenericList(Class<T> clazz, String value)
    {
        List<T> lst=null;
        try {
            Gson gson=new Gson();
            Type type = new ListParameterizedType(clazz);
            lst = gson.fromJson(value, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }
 public static  <T> String ConvertListToString(List<T>list){
     Gson gson=new Gson();
     return gson.toJson(list);
 }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(Context context) {

        int camera = ContextCompat.checkSelfPermission(context,  Manifest.permission.CAMERA);
        int ReadStorage = ContextCompat.checkSelfPermission(context ,  Manifest.permission.READ_EXTERNAL_STORAGE);
        return
                camera == PackageManager.PERMISSION_GRANTED &&
                        ReadStorage == PackageManager.PERMISSION_GRANTED;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void requestPermission(Context context) {

        ActivityCompat.requestPermissions(((Activity) context), new String[]
                {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, RequestPermissionCode);

    }
    public static  boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }
 public static File GetFileName(Context context,String foldername,String filename){
     File mFileTemp;
     String state = Environment.getExternalStorageState();
     if (Environment.MEDIA_MOUNTED.equals(state)) {
          mFileTemp = new File(Environment.getExternalStorageDirectory() + File.separator + foldername);
         mFileTemp.mkdirs();
         try {
             mFileTemp.createNewFile();
         } catch (IOException e) {
             e.printStackTrace();
         }
     } else {
         mFileTemp = new File(context.getFilesDir() + foldername);

         mFileTemp.mkdirs();

         try {
             mFileTemp.createNewFile();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
      Log.d("file", String.valueOf(mFileTemp));
     //String mLastUpdateTime = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
     return new File(mFileTemp, filename);
 }
    public static boolean Filedelete(File path) {
        boolean result = true;
        if (path.exists()) {
            if (path.isDirectory()) {
                for (File child : path.listFiles()) {
                    result &= Filedelete(child);
                }
                result &= path.delete(); // Delete empty directory.
            } else if (path.isFile()) {
                result = path.delete();
            }
            return result;
        } else {
            return false;
        }
    }
}