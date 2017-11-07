package precisioninfomatics.servicefirst.Networks;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import precisioninfomatics.servicefirst.DAO.PendingClassificationDAO;
import precisioninfomatics.servicefirst.DAO.VisitStatusDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

/**
 * Created by 4264 on 09-02-2017.
 */

public class PendingClassification{
     public  PendingClassification(Context context){
         String url= URL.SF_URL+"/getpendingclassification/";
        final PendingClassificationDAO pendingClassificationDAO = new PendingClassificationDAO(context);
         VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {
             @Override
             public void getDataFromServer(String obj) {
                 try {
                     JSONArray jsonArray = new JSONArray(obj);
                  /*   List<PendingClassificationModel> list = Utility.GenericList(PendingClassificationModel.class, obj);
                     for (int i = 0; i < list.size(); i++) {
                         PendingClassificationModel pendingClassificationModel = new PendingClassificationModel();
                         Integer id = list.get(i).getPendingClassificationID();
                         pendingClassificationModel.setPendingClassificationID(id);
                         String name = list.get(i).getPendingClassification();
                         pendingClassificationModel.setPendingClassification(name);
                         pendingClassificationDAO.PendingClassificationInsertorUpdate(pendingClassificationModel);
                     }*/
                     for (int i = 0; i < jsonArray.length(); i++) {
                         JSONObject jsonObject = jsonArray.getJSONObject(i);
                         PendingClassificationModel pendingClassificationModel = new PendingClassificationModel();
                         pendingClassificationModel.setPendingClassificationID(jsonObject.getInt("PairID"));
                         pendingClassificationModel.setPendingClassification(jsonObject.getString("Name"));
                         pendingClassificationDAO.PendingClassificationInsertorUpdate(pendingClassificationModel);
                     }
                 }catch (Exception ex){
                     ex.printStackTrace();
                 }
             }
         });
     }

    }

