package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import precisioninfomatics.servicefirst.DAO.ChartDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.ChartModel;

/**
 * Created by 4264 on 23/05/2017.
 */

public class IncidentSummaryData  {
    private ChartDAO chartDAO;
    public IncidentSummaryData(Context context,String FromDate,String ToDate) {
        chartDAO = new ChartDAO(context);
        LoginDAO loginobj=new LoginDAO(context);
        int   userid=loginobj.getUserID();
        String url = URL.SF_URL + "/dash/1/"+userid+"/"+FromDate+"/"+ToDate;; //  String url = "http://172.16.6.187/sf/api/inc/dash/1/182/"+FromDate+"/"+ToDate;
        VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {
            @Override
            public void getDataFromServer(String obj) {
                ChartModel chartModel;
                try {
                    chartDAO.SummaryDelete();
                    JSONArray jsonArray=new JSONArray(obj);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        chartModel=new ChartModel();
                        chartModel.setLabel(jsonObject.getString("label"));
                        chartModel.setData(jsonObject.getInt("data"));
                        chartDAO.SummaryInsert(chartModel);
                     }
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}