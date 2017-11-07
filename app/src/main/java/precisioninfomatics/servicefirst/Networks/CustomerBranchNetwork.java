package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.DAO.CustomerBranchDAO;
import precisioninfomatics.servicefirst.DAO.CustomerListDAO;
import precisioninfomatics.servicefirst.DAO.SpareStatusDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.CustomerBranchModel;
import precisioninfomatics.servicefirst.Model.CustomerListModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.LastModifiedModel;

/**
 * Created by 4264 on 21/06/2017.
 */

public class CustomerBranchNetwork   {
    private Integer IncidentID;
    private CustomerBranchDAO customerBranchDAO;
    public CustomerBranchNetwork(Context context,int id) {
        this.IncidentID=id;
        customerBranchDAO=new CustomerBranchDAO(context);
        String url = URL.SF_URL + "/getcompletedbranchlistbyregistrationid/"+id;
        VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {

            @Override
            public void getDataFromServer(String obj) {
                try {
                    JSONArray customerlistarray=new JSONArray(obj);
                    for(int i=0;i<customerlistarray.length();i++){
                        JSONObject customerlistobj=customerlistarray.getJSONObject(i);
                        CustomerBranchModel customerListModel=new CustomerBranchModel();
                        customerListModel.setCustomerBranchName(customerlistobj.getString("Name"));
                        customerListModel.setCustomerBranchID(customerlistobj.getString("Value"));
                        customerListModel.setIncidentID(IncidentID);
                        customerBranchDAO.CustomerBranchInsertorUpdate(customerListModel);

                    }}
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
