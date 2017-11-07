package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import precisioninfomatics.servicefirst.Activities.IncidentView;
import precisioninfomatics.servicefirst.DAO.SpareDAO;
import precisioninfomatics.servicefirst.DAO.TransportModeDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.TransportModeModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 10/03/2017.
 */

public class VisitData implements PostMethod{
    private VisitDAO visitDAO;
    private Context context;
    private Integer incidentID;
     private PostMethodToServer postMethodToServer;
    public void PostVisitData(Context context, final int id,final  int flag,final SwipeRefreshLayout swipeRefreshLayout) {
        visitDAO = new VisitDAO(context);
        this.context=context;
        this.incidentID=id;
        int isVisitAvailable=visitDAO.IsVisitExist();
        if(isVisitAvailable>0){
            Log.d("visitsave",Utility.ConvertListToString(visitDAO.VisitData()));
            SendValueToServer(context);
            postMethodToServer.execute(Utility.ConvertListToString(visitDAO.VisitData()));
        }
        String url = URL.SF_URL+"/visitlist/" + id;
        Log.d("visitlist",url);
        VolleyJsonArray.makeJsonArraytRequest( url,new GetMethod() {
            @Override
            public void getDataFromServer(String obj) {
                if (obj != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(obj);
                        visitDAO.db.beginTransaction();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            VisitModel visitModel = new VisitModel();
                            String visitType = jsonObject.getString("VisitType");
                            visitModel.setIsServer(1);
                            visitModel.setCreatedat(jsonObject.getString("CreatedDateTime"));
                            visitModel.setCreatedby(jsonObject.getInt("CreatedBy"));
                            visitModel.setLastModifiedat(jsonObject.getString("LastModifiedDateTime"));
                            visitModel.setLastmodifiedby(jsonObject.getInt("LastModifiedBy"));
                            visitModel.setVisitCode(jsonObject.getString("VisitCode"));
                            String checkin = jsonObject.getString("CheckInDate");
                            visitModel.setUserID(jsonObject.getInt("UserID"));
                            visitModel.setIncidentID(id);
                            visitModel.setCheckInDate(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss", checkin));
                            String checkout = jsonObject.getString("CheckOutDate");
                            visitModel.setCheckOutDate(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss", checkout));
                            if (visitType.equals("VISIT")) {
                                visitModel.setVisitStatus(jsonObject.getString("VisitStatusName"));
                                visitModel.setPendingClassificationText(jsonObject.getString("PendingClassificationName"));
                                visitModel.setFindingsAtSite(jsonObject.getString("FindingsAtSite"));
                                visitModel.setActionTaken(jsonObject.getString("ActionTaken"));
                                visitModel.setCallSlipNo(jsonObject.getString("CallSlipNo"));
                                visitModel.setWebID(jsonObject.getInt("VisitID"));
                                visitModel.setNextVisitDate(jsonObject.getString("NextVisitDate"));
                                visitModel.setCutOffDate(jsonObject.getString("CutOffDate"));
                                visitModel.setErrorCode(0);
                                visitModel.setErrorMessage("");
                                visitModel.setRemarks(jsonObject.getString("Remarks"));
                                visitModel.setStatus(jsonObject.getInt("VisitStatusID"));
                                visitModel.setPendingClassification(jsonObject.getInt("PendingClassificationID"));
                                boolean isLocalVendor = jsonObject.getBoolean("IsLocalVendorSupport");
                                if (isLocalVendor) {
                                    visitModel.setIsLocalVendor(1);
                                } else {
                                    visitModel.setIsLocalVendor(0);
                                }
                                visitModel.setTravelrVisitFlag(1);
                                visitDAO.VisitInsertorUpdate(visitModel);
                            } else {
                                visitModel.setTravelrVisitFlag(2);
                                visitModel.setMaporOther(2);
                                visitModel.setTravelID(jsonObject.getInt("VisitID"));
                                visitModel.setStartAddress(jsonObject.getString("FromLocation"));
                                visitModel.setEndAddress(jsonObject.getString("ToLocation"));
                                int transportmodeID = jsonObject.getInt("TransportModeID");
                                visitModel.setTransportMode(transportmodeID);
                                visitModel.setTransportModeText(jsonObject.getString("TransportMode"));
                                visitModel.setAmount(jsonObject.getString("ClaimAmount"));
                                visitModel.setKilometer(jsonObject.getString("Kilometer"));
                                visitDAO.TravelInsertorUpdate(visitModel);
                            }
                        }
                        visitDAO.db.setTransactionSuccessful();
                        if (flag == 2) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finally {
                        if(visitDAO!=null &&visitDAO.db.inTransaction()) {
                            visitDAO.db.endTransaction();
                        }
                    }

                }
            }});
        }
    private void SendValueToServer(Context context) {
        postMethodToServer = new PostMethodToServer(URL.SF_URL+"/visitsave",context);
        postMethodToServer.getResponse = this;
    }



    @Override
    public void PostDataToServer(String obj) {
        if(obj!=null){
          Log.d("obj",obj);
        try {
            VisitModel visitobj=new VisitModel();
            JSONArray jsonArray = new JSONArray(obj);
            SpareDAO spareDAO=new SpareDAO(context);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getInt("Status") == 0) {
                    visitobj.setVisitCode(jsonObject.getString("TargetCode"));
                    visitobj.setWebID(jsonObject.getInt("TargetID"));
                    visitobj.setID(jsonObject.getInt("AppID"));
                    visitDAO.UpdateWebID(visitobj);
                    JSONObject partobj=jsonObject.getJSONObject("Args");
                    JSONArray  partarray=partobj.getJSONArray("Part");
                    Log.d("arraylength",String.valueOf(partarray.length()));
                  if(partarray.length()>0){
                      spareDAO.DeleteAllSpareReq(incidentID);
                  }
                  else {
                      Log.e("no value","no value");
                  }
                       }
                else {
                    visitobj.setID(jsonObject.getInt("AppID"));
                    visitobj.setErrorMessage(jsonObject.getString("Message"));
                    visitobj.setErrorCode(jsonObject.getInt("Status"));
                    visitDAO.UpdateErrorMessage(visitobj);
                 //   visitobj.setErrorMessage(jsonObject.getString("updatemessage"));
                }

            }}
        catch (JSONException e) {
            e.printStackTrace();
        }finally {
            new SpareRequest(context,incidentID,0);
            new IncidentData(context, null,1);
            postMethodToServer.cancel(true);
            postMethodToServer.getResponse=null;
            postMethodToServer=null;
        }}
    }
}
