package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;

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

public class BarData  implements GetMethod {
    private ChartDAO chartDAO;

    public BarData(Context context) {
        chartDAO = new ChartDAO(context);
        LoginDAO loginobj=new LoginDAO(context);
        int   userid=loginobj.getUserID();
        String url = URL.SF_URL + "/dash/3/"+userid;
        Log.d("urldash",url);
        GetMethodasynctask obj = new GetMethodasynctask();
        obj.getMethod = BarData.this;
        obj.execute(url);
    }

    @Override
    public void getDataFromServer(String obj) {
        try {
            chartDAO.BarDelete();

            JSONArray jsonArray=new JSONArray(obj);

            for (int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChartModel chartModel=new ChartModel();
                chartModel.setLabel(jsonObject.getString("label"));
                chartModel.setData(jsonObject.getInt("data"));
                chartDAO.BarInsert(chartModel);
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }
}