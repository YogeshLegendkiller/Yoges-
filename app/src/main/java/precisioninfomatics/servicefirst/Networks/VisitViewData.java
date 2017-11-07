package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.Activities.VisitUpdation;
import precisioninfomatics.servicefirst.DAO.CustomerListDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.PartIssueDAO;
import precisioninfomatics.servicefirst.DAO.SerialNumberDAO;
import precisioninfomatics.servicefirst.DAO.SpareDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.Fragments.Visit;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.CustomerListModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 13/03/2017.
 */

public class  VisitViewData implements GetMethod {
    private SpareDAO spareDAO;
    private PartIssueDAO partIssueDAO;
    private Integer IncidentID,VisitID,flag;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomerListDAO customerListDAO;
    private Context context;
    private GetJsonObjectResponse getJsonObjectResponse;
    private SerialNumberDAO serialNumberDAO;
    public void VisitView(Context context, int incidentID, int visitID, int flag, SwipeRefreshLayout swipeRefreshLayout){
        this.IncidentID=incidentID;
        this.VisitID=visitID;
        this.context=context;
        spareDAO=new SpareDAO(context);
        this.flag=flag;
        if(flag==2){
            this.swipeRefreshLayout=swipeRefreshLayout;
        }
        partIssueDAO=new PartIssueDAO(context);
        LoginDAO loginobj=new LoginDAO(context);
        customerListDAO=new CustomerListDAO(context);
        serialNumberDAO=new SerialNumberDAO(context);
        int   userid=loginobj.getUserID();
        String visitview = URL.SF_URL+"/visitcreate/"+incidentID+"/"+VisitID+"/"+userid;
    //    Log.d("visitget",visitview);
         getJsonObjectResponse = new GetJsonObjectResponse();
        getJsonObjectResponse.getMethod =this;
        getJsonObjectResponse.execute(visitview);

    }
    @Override
    public void getDataFromServer(String obj) {
        try {
            JSONObject jsonObject=new JSONObject(obj);
            Log.d("spare",obj);
        //    spareDAO.db.beginTransaction();
         //   partIssueDAO.db.beginTransaction();
         /*
            visitobj.db.beginTransaction();
            VisitModel visitModel=new VisitModel();
            visitModel.setVisitCode(jsonObject.getString("VisitCode"));
            visitModel.setFindingsAtSite(jsonObject.getString("FindingsAtSite"));
            String checkin=jsonObject.getString("CheckInDate");
            visitModel.setCheckInDate(ViewDateTimeFormat.DateViewforServer(checkin,2));
            String checkout=jsonObject.getString("CheckOutDate");
            visitModel.setCheckOutDate(ViewDateTimeFormat.DateViewforServer(checkout,2));
            visitModel.setActionTaken(jsonObject.getString("ActionTaken"));
            visitModel.setWebID(jsonObject.getInt("VisitID"));
            visitModel.setNextVisitDate(jsonObject.getString("NextVisitDate"));
            visitModel.setCutOffDate(jsonObject.getString("CutOffDate"));
            visitModel.setRemarks(jsonObject.getString("Remarks"));
            visitModel.setCreatedby(jsonObject.getInt("CreatedBy"));
            visitModel.setCreatedat(ViewDateTimeFormat.DateViewforServer(jsonObject.getString("CreatedDateTime"),2));
            visitModel.setLastmodifiedby(jsonObject.getInt("LastModifiedBy"));
           visitModel.setPart(jsonObject.getString("Part"));
            visitModel.setSpareRequest(jsonObject.getString("SparePart"));
            visitModel.setStatus(jsonObject.getInt("VisitStatusID"));
            visitModel.setPendingClassification(jsonObject.getInt("PendingClassificationID"));
            boolean isLocalVendor=jsonObject.getBoolean("IsLocalVendorSupport");
            if(isLocalVendor){
                visitModel.setLocalVendorID(1);
            }
            else {
                visitModel.setLocalVendorID(0);
            }*/
            JSONArray PartRequestedJson=jsonObject.getJSONArray("Part");
           /// if(PartRequestedJson.length()>0){
                spareDAO.DeleteSpareReq(IncidentID);
           /// }
            for (int i=0;i<PartRequestedJson.length();i++){
                 JSONObject jsonobj=PartRequestedJson.getJSONObject(i);
                 SpareModel spareModel = new SpareModel();
                 spareModel.setPartStatus(jsonobj.getString("RequestedStatusName"));
                 spareModel.setPartno(jsonobj.getString("RequestedPartNo"));
                 spareModel.setWebID(jsonobj.getInt("RequestID"));
                 spareModel.setIncidentID(jsonobj.getInt("RegistrationID"));
                 spareModel.setVisitWebID(jsonobj.getInt("VisitID"));
                 spareModel.setPartID(jsonobj.getInt("RequestedPartID"));
                 spareModel.setBrandName(jsonobj.getString("BrandName"));
                 spareModel.setCategoryName(jsonobj.getString("CategoryName"));
                 spareModel.setStatusID(jsonobj.getInt("RequestedStatusID"));
                 spareModel.setPartSpecification(jsonobj.getString("PartSpecification"));
                 spareModel.setModelName(jsonobj.getString("ModelName"));
                 spareModel.setRequestedPartSpecification(jsonobj.getString("RequestedPartSpecification"));
                 boolean isIssued=jsonobj.getBoolean("IsIssued");
                 if(isIssued){
                     spareModel.setIssue("Yes");
                 }
                else {
                     spareModel.setIssue("No");
                 }
                boolean isReturned=jsonobj.getBoolean("IsAdvanceReturn");
                if(isReturned){
                    spareModel.setAdvanceReturn(1);
                }
                else {
                    spareModel.setAdvanceReturn(0);
                }
                boolean isSamePart=jsonobj.getBoolean("IsSamePart");
                if(isSamePart){
                    spareModel.setSamePart(1);
                }
                else {
                    spareModel.setSamePart(0);
                }
                spareModel.setEditFlag(3);
                spareModel.setRemarks(jsonobj.getString("PartRemarks"));
                spareModel.setDefectiveSerialno(jsonobj.getString("DefectiveSerialNo"));
                spareDAO.SpareInsertorUpdate(spareModel);
            }
            JSONArray PartIssueJsonArray=jsonObject.getJSONArray("SparePart");
                 partIssueDAO.DeleteSpareIssue(IncidentID);
            for(int i=0;i<PartIssueJsonArray.length();i++){
                JSONObject partIssueJsonobj=PartIssueJsonArray.getJSONObject(i);
                PartIssueModel partIssueModel=new PartIssueModel();
                partIssueModel.setIssuedPartNo(partIssueJsonobj.getString("IssuedPartNo"));
                partIssueModel.setIncidentID(IncidentID);
                partIssueModel.setVisitWebID(VisitID);
                partIssueModel.setSerialNo(partIssueJsonobj.getString("SerialNo"));
                partIssueModel.setStatusName(partIssueJsonobj.getString("PartStatusName"));
                partIssueModel.setRequestedStatusID(partIssueJsonobj.getInt("RequestedStatusID"));
                partIssueModel.setCurrentPartStatusID(partIssueJsonobj.getInt("CurrentPartStatusID"));
                partIssueModel.setRequestID(partIssueJsonobj.getInt("RequestID"));
                partIssueModel.setIssuedPartID(partIssueJsonobj.getInt("IssuedPartID"));
                partIssueModel.setSequence(partIssueJsonobj.getInt("Sequence"));
                partIssueModel.setUpdatedID(partIssueJsonobj.getInt("UpdateID"));
                partIssueModel.setCreatedBy(partIssueJsonobj.getInt("CreatedBy"));
                partIssueModel.setCreatedDateTime(partIssueJsonobj.getString("CreatedDateTime"));
                partIssueModel.setLastModifiedBy(partIssueJsonobj.getInt("LastModifiedBy"));
                partIssueModel.setLastModifiedDateTime(partIssueJsonobj.getString("LastModifiedDateTime"));
                partIssueModel.setIssuedWarehouseID(partIssueJsonobj.getInt("IssuedWarehouseID"));
                partIssueModel.setPartStatusID(partIssueJsonobj.getInt("PartStatusID"));
                partIssueDAO.InsertOrUpdate(partIssueModel);
            }
            JSONArray customerlistarray=jsonObject.getJSONArray("CustomerList");
            //  }
            for(int i=0;i<customerlistarray.length();i++){
                JSONObject customerlistobj=customerlistarray.getJSONObject(i);
                CustomerListModel customerListModel=new CustomerListModel();
                customerListModel.setFlag(1);
                customerListModel.setCustomerBranchName(customerlistobj.getString("Name"));
                customerListModel.setCustomerBranchID(customerlistobj.getInt("Value"));
                customerListModel.setIncidentID(IncidentID);
                 customerListDAO.InsertOrUpdate(customerListModel);
            }
            JSONArray installableserialnoarray=jsonObject.getJSONArray("InstallableSerialNumber");
           serialNumberDAO.db.beginTransaction();
            //  }
            for(int i=0;i<installableserialnoarray.length();i++){
                JSONObject installableobj=installableserialnoarray.getJSONObject(i);
                SerialNumberMapModel serialNumberMapModel=new SerialNumberMapModel();
                serialNumberMapModel.setSerialNumber(installableobj.getString("Name"));
                serialNumberMapModel.setIncidentID(IncidentID);
                serialNumberMapModel.setSerialNumberID(installableobj.getInt("Value"));
                serialNumberDAO.InsertOrUpdate(serialNumberMapModel);
            }
           serialNumberDAO.db.setTransactionSuccessful();



        /*   visitModel.setIncidentID(jsonObject.getInt("RegistrationID"));
            visitModel.setTravelrVisitFlag(1);
            visitModel.setUserID(jsonObject.getInt("UserID"));
            visitobj.VisitInsertorUpdate(visitModel);

*/
//            visitobj.db.setTransactionSuccessful();
//            spareDAO.db.setTransactionSuccessful();
  //          partIssueDAO.db.setTransactionSuccessful();

        }
       catch (JSONException e) {
            e.printStackTrace();
           getJsonObjectResponse.cancel(true);
           getJsonObjectResponse.getMethod=null;
           getJsonObjectResponse=null;
        }
        finally {
            serialNumberDAO.db.endTransaction();
            FileVisit fileVisit = new FileVisit();
            fileVisit.PostFile(context, IncidentID);
        //   VisitData visitData =new VisitData();
          // visitData.PostVisitData(context,IncidentID,flag,swipeRefreshLayout);
        }

    }
}
