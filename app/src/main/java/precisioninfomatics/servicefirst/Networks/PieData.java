package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import precisioninfomatics.servicefirst.DAO.ChartDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.ChartModel;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;

/**
 * Created by 4264 on 23/05/2017.
 */

public class PieData   {
     public PieData(Context context,String FromDate,String ToDate) {
      final   ChartDAO chartDAO = new ChartDAO(context);
        LoginDAO loginobj=new LoginDAO(context);
        int   userid=loginobj.getUserID();
        String url = URL.SF_URL + "/dash/2/"+userid+"/"+FromDate+"/"+ToDate;;
        Log.d("url",url);
        VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {
            @Override
            public void getDataFromServer(String obj) {
                ChartModel chartModel;
                try {
                    chartDAO.PieDelete();
                    JSONArray jsonArray=new JSONArray(obj);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        chartModel=new ChartModel();
                        chartModel.setLabel(jsonObject.getString("label"));
                        chartModel.setData(jsonObject.getInt("data"));
                        chartDAO.PieInsert(chartModel);
                    }
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        });

    }

}