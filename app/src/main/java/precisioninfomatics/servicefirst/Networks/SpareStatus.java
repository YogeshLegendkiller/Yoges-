package precisioninfomatics.servicefirst.Networks;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.DAO.SpareStatusDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.SpareStatusModel;

public class SpareStatus implements GetMethod {
    private SpareStatusDAO spareStatusDAO;

    public SpareStatus(Context context) {
        spareStatusDAO = new SpareStatusDAO(context);
        String SpareStatus = URL.SF_URL + "/getsparestatus";
        GetMethodasynctask obj = new GetMethodasynctask();
        obj.getMethod = SpareStatus.this;
        obj.execute(SpareStatus);
    }

    @Override
    public void getDataFromServer(String obj) {
        try {
            JSONArray jsonArray = new JSONArray(obj);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SpareStatusModel spareStatusModel = new SpareStatusModel();
                spareStatusModel.setStatus(jsonObject.getString("StatusName"));
                spareStatusModel.setStatusID(jsonObject.getInt("StatusID"));
                spareStatusModel.setSequence(jsonObject.getInt("Sequence"));
                spareStatusDAO.SpareInsertorUpdate(spareStatusModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
