package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DAO.GeneralClaimDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.CustomerBranchModel;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;

/**
 * Created by 4264 on 12/04/2017.
 */

public class GeneralClaimData implements PostMethod  {
  private GeneralClaimDAO generalClaimDAO;
    private PostMethodToServer postMethodToServer;
    private Integer incidentID,userID;
    public void SendClaimData(Context context,final int id){
       generalClaimDAO=new GeneralClaimDAO(context);
       int isClaimExist=generalClaimDAO.IsClaimExist();
        LoginDAO loginobj=new LoginDAO(context);
        this.incidentID=id;
        userID=loginobj.getUserID();
        if(isClaimExist>0){
           SendValueToServer(context);
          // Log.d("value",Utility.ConvertListToString(generalClaimDAO.GeneralClaimData()));
           postMethodToServer.execute(Utility.ConvertListToString(generalClaimDAO.GeneralClaimData()));
       }
       else {
           String url = URL.SF_URL+"/claimlist/" + incidentID+"/"+userID;
           Log.d("url",url);
           VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {

               @Override
               public void getDataFromServer(String obj) {
                   try {
                       JSONArray jsonArray = new JSONArray(obj);
                    //   if(jsonArray.length()>0){
                           generalClaimDAO.Delete(id);

                    //   }
                       //    generalClaimDAO.db.beginTransaction();
                       for (int i = 0; i < jsonArray.length(); i++) {
                           JSONObject jsonObject = jsonArray.getJSONObject(i);
                           GeneralClaimModel generalClaimModel=new GeneralClaimModel();
                           generalClaimModel.setClaimID(jsonObject.getInt("ClaimID"));
                           JSONArray claimcost=jsonObject.getJSONArray("ClaimCost");
                           List<ClaimFieldModel>listobj=new ArrayList<>();
                           for(int j=0;j<claimcost.length();j++){
                               JSONObject claimobj = claimcost.getJSONObject(j);
                               ClaimFieldModel claimFieldModel=new ClaimFieldModel();
                               claimFieldModel.setName(claimobj.getString("CostTypeName"));
                               claimFieldModel.setClaimAmount(claimobj.getInt("ClaimAmount"));
                               listobj.add(claimFieldModel);
                           }
                           Gson listtest=new Gson();
                           String claimcostvalue=listtest.toJson(listobj);
                           generalClaimModel.setClaimCost(claimcostvalue);
                           generalClaimModel.setFromLoc(jsonObject.getString("FromLocation"));
                           generalClaimModel.setToLoc(jsonObject.getString("ToLocation"));
                           generalClaimModel.setIncidentID(jsonObject.getInt("RegistrationID"));
                           generalClaimModel.setUserID(jsonObject.getInt("UserID"));
                           generalClaimModel.setCode(jsonObject.getString("ClaimCode"));
                           generalClaimModel.setFromDate(jsonObject.getString("FromDate"));
                           generalClaimModel.setToDate(jsonObject.getString("ToDate"));
                           generalClaimModel.setIsServer(1);
                           generalClaimModel.setTotal(jsonObject.getDouble("Total"));
                           generalClaimModel.setCreatedBy(jsonObject.getInt("CreatedBy"));
                           generalClaimModel.setCreatedAt(jsonObject.getString("CreatedDateTime"));
                           generalClaimModel.setLastModifiedBy(jsonObject.getInt("LastModifiedBy"));
                           generalClaimModel.setLastModifiedDateTime(jsonObject.getString("LastModifiedDateTime"));
                           generalClaimDAO.ClaimInsertorUpdate(generalClaimModel);

                       }
                       //   generalClaimDAO.db.setTransactionSuccessful();
                   }catch (JSONException ex){
                       ex.printStackTrace();
                   }
                   //   finally{

                   //   if(generalClaimDAO.db!=null&&generalClaimDAO.db.inTransaction()){
                   //       Log.d("inside_finally","end_db");
                   //      generalClaimDAO.db.endTransaction();
                   //  }

                   //   }
               }
           });
       }

    }
    private void SendValueToServer(Context context) {
        postMethodToServer = new PostMethodToServer(URL.SF_URL+"/Claimsave",context);
        postMethodToServer.getResponse = this;
    }

    @Override
    public void PostDataToServer(String obj) {
        try {
            //   JSONArray jsonArray = new JSONArray(obj);
            JSONArray jsonArray = new JSONArray(obj);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GeneralClaimModel generalClaimModel=new GeneralClaimModel();
                generalClaimModel.setID(jsonObject.getInt("AppID"));
                generalClaimModel.setClaimID(jsonObject.getInt("TargetID"));
                generalClaimModel.setCode(jsonObject.getString("TargetCode"));
                generalClaimModel.setIsSent(0);
                generalClaimModel.setIsFileExist(0);
                generalClaimDAO.UpdateClaim(generalClaimModel);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        String url = URL.SF_URL+"/claimlist/" + incidentID+"/"+userID;
        VolleyJsonArray.makeJsonArraytRequest( url, new GetMethod() {

            @Override
            public void getDataFromServer(String obj) {
                try {
                    JSONArray jsonArray = new JSONArray(obj);
                   // if(jsonArray.length()>0){
                        generalClaimDAO.Delete(incidentID);

                   // }
                    //    generalClaimDAO.db.beginTransaction();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GeneralClaimModel generalClaimModel=new GeneralClaimModel();
                        generalClaimModel.setClaimID(jsonObject.getInt("ClaimID"));
                        JSONArray claimcost=jsonObject.getJSONArray("ClaimCost");
                        List<ClaimFieldModel>listobj=new ArrayList<>();
                        for(int j=0;j<claimcost.length();j++){
                            JSONObject claimobj = claimcost.getJSONObject(j);
                            ClaimFieldModel claimFieldModel=new ClaimFieldModel();
                            claimFieldModel.setName(claimobj.getString("CostTypeName"));
                            claimFieldModel.setClaimAmount(claimobj.getInt("ClaimAmount"));
                            listobj.add(claimFieldModel);
                        }
                        Gson listtest=new Gson();
                        String claimcostvalue=listtest.toJson(listobj);
                        generalClaimModel.setClaimCost(claimcostvalue);
                        generalClaimModel.setFromLoc(jsonObject.getString("FromLocation"));
                        generalClaimModel.setToLoc(jsonObject.getString("ToLocation"));
                        generalClaimModel.setIncidentID(jsonObject.getInt("RegistrationID"));
                        generalClaimModel.setUserID(jsonObject.getInt("UserID"));
                        generalClaimModel.setCode(jsonObject.getString("ClaimCode"));
                        generalClaimModel.setFromDate(jsonObject.getString("FromDate"));
                        generalClaimModel.setToDate(jsonObject.getString("ToDate"));
                        generalClaimModel.setIsServer(1);
                        generalClaimModel.setTotal(jsonObject.getDouble("Total"));
                        generalClaimModel.setCreatedBy(jsonObject.getInt("CreatedBy"));
                        generalClaimModel.setCreatedAt(jsonObject.getString("CreatedDateTime"));
                        generalClaimModel.setLastModifiedBy(jsonObject.getInt("LastModifiedBy"));
                        generalClaimModel.setLastModifiedDateTime(jsonObject.getString("LastModifiedDateTime"));
                        generalClaimDAO.ClaimInsertorUpdate(generalClaimModel);

                    }
                    //   generalClaimDAO.db.setTransactionSuccessful();
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
                //   finally{

                //   if(generalClaimDAO.db!=null&&generalClaimDAO.db.inTransaction()){
                //       Log.d("inside_finally","end_db");
                //      generalClaimDAO.db.endTransaction();
                //  }

                //   }
            }
        });
    }
}
