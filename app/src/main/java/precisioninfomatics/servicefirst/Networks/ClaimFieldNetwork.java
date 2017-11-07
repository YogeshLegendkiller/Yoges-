package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.DAO.ChartDAO;
import precisioninfomatics.servicefirst.DAO.ClaimFieldDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.ChartModel;
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.LastModifiedModel;

/**
 * Created by 4264 on 24/05/2017.
 */

public class ClaimFieldNetwork   {
    public ClaimFieldNetwork(Context context) {
       final ClaimFieldDAO   claimFieldDAO = new ClaimFieldDAO(context);
        String url = URL.SF_URL + "/getclaimdynamicfields";
        VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {

            @Override
            public void getDataFromServer(String obj) {
                try {
                    JSONArray jsonArray = new JSONArray(obj);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ClaimFieldModel claimFieldModel = new ClaimFieldModel();
                        claimFieldModel.setName(jsonObject.getString("Name"));
                        claimFieldModel.setPairID(jsonObject.getInt("PairID"));
                        claimFieldDAO.FieldInsertOrUpdate(claimFieldModel);
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        });
      }


}