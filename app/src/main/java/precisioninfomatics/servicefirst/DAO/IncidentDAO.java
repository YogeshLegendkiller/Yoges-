package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import precisioninfomatics.servicefirst.Activities.Incident;
import precisioninfomatics.servicefirst.Activities.LocalVendor;
import precisioninfomatics.servicefirst.Activities.VehicleRegistration;
import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.Model.LastModifiedModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Model.LoginModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.ResourceModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VehicleModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

import static precisioninfomatics.servicefirst.DAO.VisitDAO.VisitDAO_URI;

/**
 * Created by 4264 on 30-01-2017.
 */

public class IncidentDAO extends SFSingelton {
    private Context mcontext;
    public static final Uri DB_SF_Incident = Uri
            .parse("sqlite://" + "sf_incident" + "/" + "db");

    public IncidentDAO(Context context) {
        super(context);
        this.mcontext = context;
    }

    private void InsertIncident(IncidentModel incidentModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IncidentModel.inc_regID, incidentModel.getIncidentID());
        contentValues.put(IncidentModel.inc_customeraddrs,incidentModel.getCustomerAddress());
        contentValues.put(IncidentModel.inc_companyname, incidentModel.getCompanyName());
        contentValues.put(IncidentModel.inc_incCode, incidentModel.getIncidentCode());
        contentValues.put(IncidentModel.inc_probcategory, incidentModel.getProblemCategory());
        contentValues.put(IncidentModel.inc_status, incidentModel.getStatus());
        contentValues.put(IncidentModel.inc_localvendor,incidentModel.getLocalVendor());
        contentValues.put(IncidentModel.probdescription,incidentModel.getProblemDescription());
        contentValues.put(IncidentModel.inc_partreq,incidentModel.getIsPartRequired());
        contentValues.put(IncidentModel.inc_lmd,incidentModel.getLastmodifedat());
        contentValues.put(IncidentModel.inc_gc,incidentModel.getIsGeneralClaim());
        contentValues.put(IncidentModel.inc_instalcall,incidentModel.getIsInstallationCall());
        contentValues.put(IncidentModel.inc_statusID, incidentModel.getStatusID());
        contentValues.put(IncidentModel.inc_cd, incidentModel.getCreatedat());
        contentValues.put(IncidentModel.inc_visitstatus,incidentModel.getVisitStatus());
        contentValues.put(IncidentModel.inc_lat,incidentModel.getLatitude());
        contentValues.put(IncidentModel.inc_long,incidentModel.getLongtitude());
        db.insert(IncidentModel.inc_table, null, contentValues);
    }

    private void UpdateIncident(IncidentModel incidentModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IncidentModel.inc_customeraddrs,incidentModel.getCustomerAddress());
        contentValues.put(IncidentModel.inc_lat,incidentModel.getLatitude());
        contentValues.put(IncidentModel.inc_long,incidentModel.getLongtitude());
        contentValues.put(IncidentModel.inc_localvendor,incidentModel.getLocalVendor());
        contentValues.put(IncidentModel.inc_partreq,incidentModel.getIsPartRequired());
        contentValues.put(IncidentModel.inc_lmd,incidentModel.getLastmodifedat());
        contentValues.put(IncidentModel.inc_gc,incidentModel.getIsGeneralClaim());
        contentValues.put(IncidentModel.probdescription,incidentModel.getProblemDescription());
        contentValues.put(IncidentModel.inc_instalcall,incidentModel.getIsInstallationCall());
        contentValues.put(IncidentModel.inc_regID, incidentModel.getIncidentID());
        contentValues.put(IncidentModel.inc_companyname, incidentModel.getCompanyName());
        contentValues.put(IncidentModel.inc_incCode, incidentModel.getIncidentCode());
        contentValues.put(IncidentModel.inc_probcategory, incidentModel.getProblemCategory());
        contentValues.put(IncidentModel.inc_status, incidentModel.getStatus());
        contentValues.put(IncidentModel.inc_visitstatus,incidentModel.getVisitStatus());
        contentValues.put(IncidentModel.inc_statusID, incidentModel.getStatusID());
        contentValues.put(IncidentModel.inc_cd, incidentModel.getCreatedat());
        db.update(IncidentModel.inc_table, contentValues, IncidentModel.inc_regID + "= ?", new String[]{String.valueOf(incidentModel.getIncidentID())});
    }

    public void InsertOrUpdate(IncidentModel incidentModel) {
        String query = " SELECT  * FROM " + IncidentModel.inc_table + " WHERE " + IncidentModel.inc_regID + "  =" + incidentModel.getIncidentID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateIncident(incidentModel);
        } else {
            InsertIncident(incidentModel);
        }
        mcontext.getContentResolver().notifyChange(DB_SF_Incident, null);
        c.close();
    }
    public void DeleteIncident(int id){
        db.delete(IncidentModel.inc_table,IncidentModel.inc_regID  + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(DB_SF_Incident, null);
    }
    public Cursor IncidentList() {
        Cursor cursor = db.rawQuery("select * from " + IncidentModel.inc_table +" order  by         datetime("+IncidentModel.inc_cd+") DESC ", null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Incident);
        return cursor;
    }

 public void DeleteOldIncident(){
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     Calendar cal = Calendar.getInstance();
     cal.add(Calendar.DAY_OF_YEAR, -6);
     cal.set(Calendar.HOUR_OF_DAY, 0);
     cal.set(Calendar.MINUTE, 0);
     cal.set(Calendar.SECOND, 0);
     cal.set(Calendar.MILLISECOND, 0);
     //String query = "  DELETE  FROM   " + IncidentModel.inc_table  + " where  " + IncidentModel.inc_statusID + " = 8  and  " + IncidentModel.inc_cd  +"  <= " + "'" + dateFormat.format(cal.getTime()) + "'";
     String query = "  SELECT  " + IncidentModel.inc_regID   + "   FROM   " + IncidentModel.inc_table  + " where  " + IncidentModel.inc_statusID + " = 8  and  " + IncidentModel.inc_cd  +"  <= " + "'" + dateFormat.format(cal.getTime()) + "'";
    Cursor cursor= db.rawQuery(query,null);
     if (cursor != null && cursor.getCount() > 0) {
         if (cursor.moveToFirst()) {
             do {
                 DeleteClosedIncident(cursor.getInt(cursor.getColumnIndex(IncidentModel.inc_regID)));
                 DeleteClosedVisit(cursor.getInt(cursor.getColumnIndex(IncidentModel.inc_regID)));
             } while (cursor.moveToNext());
         }
         mcontext.getContentResolver().notifyChange(DB_SF_Incident, null);
         cursor.close();
     }
    }
    public Cursor IncidentListBasedOnLabel(String label) {
        Cursor cursor = db.rawQuery("select * from " + IncidentModel.inc_table + " where " + IncidentModel.inc_visitstatus +  " LIKE " + "'%" + label + "%'", null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Incident);
        return cursor;
    }
    public Cursor IncidentListBasedOnLabelWithFilter(String label,String companyname) {
        Cursor cursor = db.rawQuery("select * from " + IncidentModel.inc_table + " where " + IncidentModel.inc_visitstatus  +  " LIKE " + "'%" + label  + "%'" +   " and (" + IncidentModel.inc_companyname  +  " LIKE " + "'%" + companyname  + "%'" +   " or " + IncidentModel.inc_incCode   +  " LIKE " + "'%" + companyname  + "%')", null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Incident);
        return cursor;
    }
    public Cursor FilterList(String txt){
        String visitquery= " Select * FROM  " + IncidentModel.inc_table  + " where " +  IncidentModel.inc_incCode  + " LIKE " + "'%" + txt + "%'" +   " or " + IncidentModel.inc_companyname   +  " LIKE " + "'%" + txt  + "%'";
        Cursor cursor = db.rawQuery(visitquery ,null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Incident);
        return cursor;
    }
    public void InsertIncidentView(IncidentViewModel incidentModel) {
        ContentValues contentValues = new ContentValues();
        Log.d("insertview","view");
        contentValues.put(IncidentModel.inc_callorgin, incidentModel.getCallOrigin());
        contentValues.put(IncidentModel.serviceclassification, incidentModel.getServiceClassification());
        contentValues.put(IncidentModel.sbu, incidentModel.getSBU());
        contentValues.put(IncidentViewModel.checklistid,incidentModel.getCheckListID());
        contentValues.put(IncidentModel.servicecategory, incidentModel.getServiceCategory());
        contentValues.put(IncidentModel.bu, incidentModel.getBU());
        contentValues.put(IncidentModel.servicesubcategory, incidentModel.getServiceSubCategory());
        contentValues.put(IncidentModel.subBu, incidentModel.getSubBU());
        contentValues.put(IncidentModel.inc_companyname, incidentModel.getCompanyName());
        contentValues.put(IncidentModel.probdescription, incidentModel.getProblemDescription());
        contentValues.put(IncidentViewModel.inc_probcategory, incidentModel.getProblemCategory());
        contentValues.put(IncidentModel.priority, incidentModel.getPriority());
        contentValues.put(IncidentModel.remarks, incidentModel.getRemarks());
        contentValues.put(IncidentModel.inc_regID, incidentModel.getIncidentID());
        contentValues.put(IncidentModel.inc_cb, incidentModel.getCreatedby());
        contentValues.put(IncidentViewModel.contractcode,incidentModel.getContractCode());
        contentValues.put(IncidentViewModel.contaract_sdate,incidentModel.getContractStartDate());
        contentValues.put(IncidentViewModel.contract_enddate,incidentModel.getContractEndDate());
        contentValues.put(IncidentViewModel.acm,incidentModel.getAccountManager());
        contentValues.put(IncidentViewModel.sbu,incidentModel.getSBU());
        contentValues.put(IncidentViewModel.bu,incidentModel.getBU());
        contentValues.put(IncidentViewModel.conatractstatus,incidentModel.getContractStatus());
        contentValues.put(IncidentViewModel.subBu,incidentModel.getSubBU());
        contentValues.put(IncidentViewModel.serviceprovider, incidentModel.getServiceProvider());
        contentValues.put(IncidentModel.inc_lmb, incidentModel.getLastModifiedby());
        contentValues.put(IncidentModel.inc_lmd, incidentModel.getLastmodifedat());
        contentValues.put(IncidentViewModel.customercode, incidentModel.getCustomerCode());
        contentValues.put(IncidentViewModel.customerbranchaddress, incidentModel.getCustomerBranchAddress());
        contentValues.put(IncidentViewModel.contactname, incidentModel.getContactName());
        contentValues.put(IncidentViewModel.designation, incidentModel.getDesignation());
        contentValues.put(IncidentViewModel.mailid, incidentModel.getEmailID());
        contentValues.put(IncidentViewModel.phno, incidentModel.getPhoneNo());
        contentValues.put(IncidentViewModel.assetcategoryname, incidentModel.getAssetCategoryName());
        contentValues.put(IncidentViewModel.serialno, incidentModel.getSerialNumber());
        contentValues.put(IncidentViewModel.baseconfig, incidentModel.getBaseConfiguration());
        contentValues.put(IncidentViewModel.currentconfig, incidentModel.getCurrentConfiguration());
        contentValues.put(IncidentViewModel.partno, incidentModel.getPartNo());
        contentValues.put(IncidentViewModel.periodtype, incidentModel.getPeriodType());
        contentValues.put(IncidentViewModel.periodfrom, incidentModel.getPeriodFrom());
        contentValues.put(IncidentViewModel.periodto, incidentModel.getPeriodTo());
         db.insert(IncidentViewModel.table, null, contentValues);
        mcontext.getContentResolver().notifyChange(DB_SF_Incident, null);
    }
    public void UpdateIncidentView(IncidentViewModel incidentModel) {
        ContentValues contentValues = new ContentValues();
        Log.d("insertview","view");
        contentValues.put(IncidentModel.inc_callorgin, incidentModel.getCallOrigin());
        contentValues.put(IncidentModel.serviceclassification, incidentModel.getServiceClassification());
        contentValues.put(IncidentModel.sbu, incidentModel.getSBU());
        contentValues.put(IncidentModel.servicecategory, incidentModel.getServiceCategory());
        contentValues.put(IncidentModel.bu, incidentModel.getBU());
        contentValues.put(IncidentModel.servicesubcategory, incidentModel.getServiceSubCategory());
        contentValues.put(IncidentModel.subBu, incidentModel.getSubBU());
        contentValues.put(IncidentModel.inc_companyname, incidentModel.getCompanyName());
        contentValues.put(IncidentModel.probdescription, incidentModel.getProblemDescription());
        contentValues.put(IncidentViewModel.inc_probcategory, incidentModel.getProblemCategory());
        contentValues.put(IncidentModel.priority, incidentModel.getPriority());
        contentValues.put(IncidentModel.remarks, incidentModel.getRemarks());
        contentValues.put(IncidentViewModel.checklistid,incidentModel.getCheckListID());
        contentValues.put(IncidentModel.inc_regID, incidentModel.getIncidentID());
        contentValues.put(IncidentModel.inc_cb, incidentModel.getCreatedby());
        contentValues.put(IncidentViewModel.contractcode,incidentModel.getContractCode());
        contentValues.put(IncidentViewModel.contaract_sdate,incidentModel.getContractStartDate());
        contentValues.put(IncidentViewModel.contract_enddate,incidentModel.getContractEndDate());
        contentValues.put(IncidentViewModel.acm,incidentModel.getAccountManager());
        contentValues.put(IncidentViewModel.sbu,incidentModel.getSBU());
        contentValues.put(IncidentViewModel.bu,incidentModel.getBU());
        contentValues.put(IncidentViewModel.conatractstatus,incidentModel.getContractStatus());
        contentValues.put(IncidentViewModel.subBu,incidentModel.getSubBU());
        contentValues.put(IncidentViewModel.serviceprovider, incidentModel.getServiceProvider());
        contentValues.put(IncidentModel.inc_lmb, incidentModel.getLastModifiedby());
        contentValues.put(IncidentModel.inc_lmd, incidentModel.getLastmodifedat());
        contentValues.put(IncidentViewModel.customercode, incidentModel.getCustomerCode());
        contentValues.put(IncidentViewModel.customerbranchaddress, incidentModel.getCustomerBranchAddress());
        contentValues.put(IncidentViewModel.contactname, incidentModel.getContactName());
        contentValues.put(IncidentViewModel.designation, incidentModel.getDesignation());
        contentValues.put(IncidentViewModel.mailid, incidentModel.getEmailID());
        contentValues.put(IncidentViewModel.phno, incidentModel.getPhoneNo());
        contentValues.put(IncidentViewModel.assetcategoryname, incidentModel.getAssetCategoryName());
        contentValues.put(IncidentViewModel.serialno, incidentModel.getSerialNumber());
        contentValues.put(IncidentViewModel.baseconfig, incidentModel.getBaseConfiguration());
        contentValues.put(IncidentViewModel.currentconfig, incidentModel.getCurrentConfiguration());
        contentValues.put(IncidentViewModel.partno, incidentModel.getPartNo());
        contentValues.put(IncidentViewModel.periodtype, incidentModel.getPeriodType());
        contentValues.put(IncidentViewModel.periodfrom, incidentModel.getPeriodFrom());
        contentValues.put(IncidentViewModel.periodto, incidentModel.getPeriodTo());
        db.update(IncidentViewModel.table, contentValues, IncidentModel.inc_regID + "= ?", new String[]{String.valueOf(incidentModel.getIncidentID())});
    }
    public Cursor Incidentview(int id) {
        Cursor cursor = db.rawQuery("select * from " + IncidentViewModel.table + " where " + IncidentViewModel.inc_regID + " =" + id, null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Incident);
        return cursor;
    }

    public boolean incdentviewcheck(int id) {
        String sql = "select " + IncidentModel.inc_regID + " from  " + IncidentViewModel.table + " where " + IncidentModel.inc_regID + " =" + id;
        Cursor c = db.rawQuery(sql, null);
        try {
            return c.moveToFirst();
        } finally {
            c.close();
        }
    }
    public void InsertOrUpdateIncView(IncidentViewModel incidentModel) {
        String query = " SELECT  * FROM " + IncidentViewModel.table + " WHERE " + IncidentModel.inc_regID + "  =" + incidentModel.getIncidentID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateIncidentView(incidentModel);
        } else {
            InsertIncidentView(incidentModel);
        }
        if(!c.isClosed()){
            c.close();
        }

    }
     public void UpdateIncidentLocalVendor(IncidentModel incidentobj,int id){
         ContentValues contentValues=new ContentValues();
         contentValues.put(IncidentModel.inc_localvendor,incidentobj.getLocalVendor());
         mcontext.getContentResolver().notifyChange(DB_SF_Incident, null);
         db.update(IncidentModel.inc_table, contentValues, IncidentModel.inc_regID + "= ?", new String[]{String.valueOf(id)});
     }

    public void deleteincident() {
        db.execSQL("delete from " + IncidentModel.inc_table);
    }
    public void deleteLastmodified() {
        db.execSQL("delete from " + LastModifiedModel.lmd_table);
    }
    public void deleteResource() {
        db.execSQL("delete from " + ResourceModel.table);
    }
    public void deleteIncidentView() {
        db.execSQL("delete from " + IncidentViewModel.table);
    }
    public void deleteClaimDelete() {
        db.execSQL("delete from " + GeneralClaimModel.GC_Table);
    }
    public void deleteLocalVendor() {
        db.execSQL("delete from " + LocalVendorModel.table);
    }
    public void deleteVehicleRegistration() {
        db.execSQL("delete from " + VehicleModel.table);
    }
    public void deleteVisit() {
        db.execSQL("delete from " + VisitModel.table);
    }
    public void deleteTravel() {
        db.execSQL("delete from " + VisitModel.t_table);
    }
    public void deleteSpareRequest() {
        db.execSQL("delete from " +SpareModel.table);
    }
    public void deleteSpareIssue() {
        db.execSQL("delete from " + PartIssueModel.table);
    }

    public String  getMaxDate() {
        String countQuery = "SELECT   MAX ("+ IncidentModel.inc_lmd   +")  FROM " + IncidentModel.inc_table  + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        String date=null;
        if(cursor.moveToFirst()) {
            date = cursor.getString(0);
        } cursor.close();
        return date;
    }
      public Integer getCheckListID(int id) {
        int checklistid = 0;
        String query = " select * from " + IncidentViewModel.table  + "  where  " + IncidentModel.inc_regID + " = " +id  ;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                checklistid = cursor.getInt(cursor.getColumnIndex(IncidentViewModel.checklistid));
            }}
        if (cursor != null) {
            cursor.close();
        }
        return checklistid;
    }
    private void DeleteClosedIncident(int id){
        db.delete(IncidentModel.inc_table ,IncidentModel.inc_regID + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(DB_SF_Incident, null);
    }
    private void DeleteClosedVisit(int id){
        db.delete(VisitModel.table,VisitModel.incid  + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
    }
    public int IncidentCheck() {
        String countQuery = "SELECT  " + IncidentModel.inc_regID   + " from " + IncidentModel.inc_table;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
}

