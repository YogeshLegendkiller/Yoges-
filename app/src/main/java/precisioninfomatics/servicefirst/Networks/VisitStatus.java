package precisioninfomatics.servicefirst.Networks;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import precisioninfomatics.servicefirst.DAO.VisitStatusDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

/**
 * Created by 4264 on 09-02-2017.
 */

public class VisitStatus {
    public VisitStatus(Context context) {
        String url= URL.SF_URL+"/getincstatus";
       final  VisitStatusDAO visitStatusDAO;
          visitStatusDAO=new VisitStatusDAO(context);
        VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {
            @Override
            public void getDataFromServer(String obj) {
                try {
                    /*
                    List<VisitStatusModel> list = Utility.GenericList(VisitStatusModel.class,obj);
                    for(int i=0;i<list.size();i++){
                        VisitStatusModel visitStatusModel=new VisitStatusModel();
                        Integer id=list.get(i).getStatusID();
                        visitStatusModel.setStatusID(id);
                        String name=list.get(i).getStatus();
                        visitStatusModel.setStatus(name);
                        visitStatusDAO.VisitStatusInsertorUpdate(visitStatusModel);
                    }}*/
                    JSONArray jsonArray=new JSONArray(obj);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        VisitStatusModel visitStatusModel = new VisitStatusModel();
                        visitStatusModel.setStatusID(jsonObject.getInt("Value"));
                        visitStatusModel.setStatus(jsonObject.getString("Name"));
                       // pendingClassificationDAO.PendingClassificationInsertorUpdate(pendingClassificationModel);
                        visitStatusDAO.VisitStatusInsertorUpdate(visitStatusModel);
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
    }

}
