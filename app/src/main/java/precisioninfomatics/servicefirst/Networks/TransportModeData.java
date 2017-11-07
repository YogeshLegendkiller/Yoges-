package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import precisioninfomatics.servicefirst.DAO.TransportModeDAO;
import precisioninfomatics.servicefirst.DAO.VisitStatusDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.Model.TransportModeModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

/**
 * Created by 4264 on 17-02-2017.
 */

public class TransportModeData {
    private Context mcontext;
    public TransportModeData(Context context) {
        this.mcontext=context;
        String url= URL.SF_URL+"/gettransportmode/";
        VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {
            @Override
            public void getDataFromServer(String obj) {
                try {
                     TransportModeDAO  transportModeDAO = new TransportModeDAO(mcontext);
                    JSONArray jsonArray=new JSONArray(obj);
                    TransportModeModel transportModeModel;
                    transportModeDAO = new TransportModeDAO(mcontext);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        transportModeModel = new TransportModeModel();
                        transportModeModel.setID(jsonObject.getInt("PairID"));
                        transportModeModel.setName(jsonObject.getString("Name"));
                        transportModeDAO.TransportModeInsertorUpdate(transportModeModel);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }

}

