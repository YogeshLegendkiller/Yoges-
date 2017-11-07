package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DAO.GeneralClaimDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.GeneralClaimDocument;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 12/04/2017.
 */

public class GeneralClaimFile implements FileData {
    private Context context;
    private Integer IncidentID;
    private GeneralClaimDAO generalClaimDAO;

    public void PostFile(Context context,int incidentID) {
        this.context=context;
        this.IncidentID=incidentID;
        generalClaimDAO = new GeneralClaimDAO(context);
        int isFileExist = generalClaimDAO.IsClaimFileExist();
        if (isFileExist > 0) {
            List<GeneralClaimModel> filelist = generalClaimDAO.FileList();
            for (int i = 0; i < filelist.size(); i++) {
                FileUploadAsync fileUploadAsync = new FileUploadAsync();
                fileUploadAsync.fileData = GeneralClaimFile.this;
                fileUploadAsync.execute(URL.SF_URL+"/uploadfiles", String.valueOf(4), String.valueOf(filelist.get(i).getIncidentID()), String.valueOf(filelist.get(i).getID()), String.valueOf(filelist.get(i).getUserID()), filelist.get(i).getDocs(),String.valueOf(0));
            }
        }
        else {
            GeneralClaimData generalClaimData=new GeneralClaimData();
            generalClaimData.SendClaimData(context,IncidentID);
        }
    }

    @Override
    public void getValue(String obj) {
        try {
            generalClaimDAO.db.beginTransaction();
            Gson gson=new Gson();
            JSONObject jsonObject = new JSONObject(obj);
                GeneralClaimModel generalClaimModel=new GeneralClaimModel();
               JSONArray atach=jsonObject.getJSONArray("attachment");
               List<GeneralClaimDocument>generalClaimDocuments=new ArrayList<>();
               for (int i=0;i<atach.length();i++){
                   JSONObject file=atach.getJSONObject(i);
                   GeneralClaimDocument generalClaimDocument=new GeneralClaimDocument();
                   generalClaimDocument.setFileName(file.getString("FileName"));
                   generalClaimDocument.setFilePath(file.getString("FilePath"));
                   generalClaimDocuments.add(generalClaimDocument);
               }
                String docs=gson.toJson(generalClaimDocuments);
                Log.d("docs",docs);
                generalClaimModel.setDocs(docs);
                generalClaimModel.setIsFileExist(0);
                generalClaimModel.setID(jsonObject.getInt("AppId"));
                generalClaimDAO.UpdateDocument(generalClaimModel);

            //}
            generalClaimDAO.db.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally{
            if(generalClaimDAO.db!=null&&generalClaimDAO.db.inTransaction()){
            generalClaimDAO.db.endTransaction();
         //       Log.d("inside_finally","end_db");
            }
            GeneralClaimData generalClaimData=new GeneralClaimData();
            generalClaimData.SendClaimData(context,IncidentID);
        }
    }
}