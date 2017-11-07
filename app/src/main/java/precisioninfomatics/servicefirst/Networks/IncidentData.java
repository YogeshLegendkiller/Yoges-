package precisioninfomatics.servicefirst.Networks;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.List;

import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.LastModifiedDateDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.LastModifiedModel;


public class IncidentData  {
    private   IncidentDAO incidentDAO;
    private LastModifiedDateDAO lastModifiedDateDAO;
    private   ProgressDialog pd;
     public IncidentData(final Context mcontext, final SwipeRefreshLayout swipeRefreshLayout, final int flags) {
        LoginDAO loginobj=new LoginDAO(mcontext);
        int   userid=loginobj.getUserID();
        lastModifiedDateDAO=new LastModifiedDateDAO(mcontext);
        incidentDAO=new IncidentDAO(mcontext);
        final int count=incidentDAO.IncidentCheck();
        if(count==0){
             pd=new ProgressDialog(mcontext);
             pd.setMessage("Please wait");
             pd.setCancelable(false);
             pd.show();
        }
        String lastmodifieddate=lastModifiedDateDAO.getMaxDate();
         String  url;

        if(lastmodifieddate==null){
         url= URL.SF_URL+"/getail/"+userid+"/29/";
    }
    else {
         url = URL.SF_URL + "/getail/" + userid + "/29/" + lastmodifieddate;
     }
        Log.d("inc_url",url);
           VolleyJsonArray.makeJsonArraytRequest(url, new GetMethod() {

            @Override
            public void getDataFromServer(String obj) {
                try {
                    if (flags == 2 && obj == null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    incidentDAO.db.beginTransaction();
                    JSONArray jsonArray = new JSONArray(obj);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        IncidentModel incidentModel = new IncidentModel();
                        LastModifiedModel lastModifiedModel=new LastModifiedModel();
                        incidentModel.setCompanyName(jsonObject.getString("CompanyName"));
                        incidentModel.setIncidentID(jsonObject.getInt("RegistrationID"));
                        incidentModel.setIncidentCode(jsonObject.getString("IncidentCode"));
                        incidentModel.setProblemCategory(jsonObject.getString("ServiceClassification"));
                        incidentModel.setStatus(jsonObject.getString("Status"));
                        incidentModel.setLatitude(jsonObject.getString("Latitude"));
                        incidentModel.setLongtitude(jsonObject.getString("Longitude"));
                        incidentModel.setCustomerAddress(jsonObject.getString("CustomerAddress"));
                        boolean is_installationcall = jsonObject.getBoolean("IsInstallationCall");
                        if (is_installationcall) {
                            incidentModel.setIsInstallationCall(1);
                        } else {
                            incidentModel.setIsInstallationCall(0);
                        }
                        boolean is_claimrequired = jsonObject.getBoolean("IsGeneralClaim");
                        if (is_claimrequired) {
                            incidentModel.setIsGeneralClaim(1);
                        } else {
                            incidentModel.setIsGeneralClaim(0);
                        }
                        //  incidentModel.setIsGeneralClaim(1);
                        boolean is_IsPartRequired = jsonObject.getBoolean("IsPartRequired");
                        if (is_IsPartRequired) {
                            incidentModel.setIsPartRequired(1);
                        } else {
                            incidentModel.setIsPartRequired(0);
                        }
                        boolean is_LV = jsonObject.getBoolean("IsvendorInvoice");
                        if (is_LV) {
                            incidentModel.setLocalVendor(1);
                        } else {
                            incidentModel.setLocalVendor(0);
                        }
                        incidentModel.setVisitStatus(jsonObject.getString("VisitStatus"));
                        incidentModel.setStatusID(jsonObject.getInt("StatusID"));
                        incidentModel.setLastmodifedat(jsonObject.getString("LastModifiedDateTime"));
                        incidentModel.setProblemDescription(jsonObject.getString("ProblemDescription"));
                        incidentModel.setCreatedat(jsonObject.getString("CreatedDateTime"));
                        lastModifiedModel.setIncidentID(jsonObject.getInt("RegistrationID"));
                        lastModifiedModel.setLastModifiedDateTime(jsonObject.getString("LastModifiedDateTime"));
                        lastModifiedDateDAO.InsertOrUpdate(lastModifiedModel);
                        incidentDAO.InsertOrUpdate(incidentModel);
                    }
                    incidentDAO.db.setTransactionSuccessful();
                    if (flags == 2) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    if (flags == 2 && obj == null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    Toast.makeText(mcontext,"Please Try After Some Time",Toast.LENGTH_SHORT).show();
                }
                finally {
                    if(incidentDAO.db.inTransaction()){
                        if(count==0){
                            pd.cancel();
                        }
                        Log.d("end","end");
                        incidentDAO.db.endTransaction();
                    }
            }
        }
    });




}}
