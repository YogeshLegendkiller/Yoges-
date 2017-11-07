package precisioninfomatics.servicefirst.Networks;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.GeneralClaimDocument;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 11/04/2017.
 */

public class FileUploadAsync extends AsyncTask<String, Void, String>  {
    private String JsonResponse;
    public  FileData fileData=null;

    @Override
    protected String doInBackground(String... strings) {
        try {
            FileUpload multipart = new FileUpload(strings[0], "UTF-8");
            multipart.addFormField("flag",strings[1]);
            multipart.addFormField("WebID",strings[6]);
            multipart.addFormField("RegistrationID",strings[2]);
            multipart.addFormField("AppID", strings[3]);
            multipart.addFormField("UserID",strings[4]);
            switch (strings[1]) {
                case "3":
                    multipart.addFormField("TravelMode", strings[7]);
                    if (strings[7].equals("1")) {

                        multipart.addFilePart("OrginMapFileName", new File(strings[5].trim()));
                        multipart.addFilePart("DestinationMapFileName", new File(strings[8].trim()));
                    } else {
                        multipart.addFilePart("uploaded_file", new File(strings[5]));
                    }
                    break;
                case "4":
                      List<GeneralClaimDocument> list = Utility.GenericList(GeneralClaimDocument.class,strings[5]);
                    for (int i = 0; i < list.size(); i++) {
                        Log.d("filename", list.get(i).getFileName());
                        multipart.addFilePart(list.get(i).getFileName(), new File(list.get(i).getFilePath()));
                    }
                    break;
                default:
                    multipart.addFilePart("uploaded_file", new File(strings[5]));
                    break;
            }
              JsonResponse = multipart.finish();
        } catch (IOException ex) {
         ex.printStackTrace();
        }
        return JsonResponse;
    }
    @Override
    protected void onPostExecute(String  aVoid){
        super.onPostExecute(aVoid);
        if(aVoid==null ){
         ///   Toast.makeText(context,"Please Try after sometime",Toast.LENGTH_SHORT).show();
            fileData.getValue("no value");

        }
        else {
                fileData.getValue(JsonResponse);
        }
    }
}

