package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 14/03/2017.
 */

public class PartIssueDAO extends SFSingelton {
    private Context mContext;

    public PartIssueDAO(Context context) {
        super(context);
        this.mContext=context;
    }

    public void InsertPartIssue(PartIssueModel partIssueModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(PartIssueModel.curentpartstatusname,partIssueModel.getCurrentPartStatusName());
        contentValues.put(PartIssueModel.issuedpartno,partIssueModel.getIssuedPartNo());
        contentValues.put(PartIssueModel.serialno,partIssueModel.getSerialNo());
        contentValues.put(PartIssueModel.statusname,partIssueModel.getStatusName());
        contentValues.put(PartIssueModel.reqstatusID,partIssueModel.getRequestedStatusID());
        contentValues.put(PartIssueModel.currentpartstatusID,partIssueModel.getCurrentPartStatusID());
        contentValues.put(PartIssueModel.reqid,partIssueModel.getRequestID());
        contentValues.put(PartIssueModel.partstatusid,partIssueModel.getPartStatusID());
        contentValues.put(PartIssueModel.issuedpartid,partIssueModel.getIssuedPartID());
        contentValues.put(PartIssueModel.squence,partIssueModel.getSequence());
        contentValues.put(PartIssueModel.issuedwarehouseid,partIssueModel.getIssuedWarehouseID());
        contentValues.put(PartIssueModel.cb,partIssueModel.getCreatedBy());
        contentValues.put(PartIssueModel.lmb,partIssueModel.getLastModifiedBy());
        contentValues.put(PartIssueModel.cd,partIssueModel.getCreatedDateTime());
        contentValues.put(PartIssueModel.ld,partIssueModel.getLastModifiedDateTime());
        contentValues.put(PartIssueModel.updatedid,partIssueModel.getUpdatedID());
        contentValues.put(PartIssueModel.incid,partIssueModel.getIncidentID());
        contentValues.put(PartIssueModel.webid,partIssueModel.getVisitWebID());
        db.insert(PartIssueModel.table,null,contentValues);
        mContext.getContentResolver().notifyChange(SpareDAO.DB_SF_Spare, null);
    }
    public void UpdatePartIssue(PartIssueModel partIssueModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(partIssueModel.getUpdatedID())};
        contentValues.put(PartIssueModel.curentpartstatusname,partIssueModel.getCurrentPartStatusName());
        contentValues.put(PartIssueModel.issuedpartno,partIssueModel.getIssuedPartNo());
        contentValues.put(PartIssueModel.serialno,partIssueModel.getSerialNo());
        contentValues.put(PartIssueModel.statusname,partIssueModel.getStatusName());
        contentValues.put(PartIssueModel.reqstatusID,partIssueModel.getRequestedStatusID());
        contentValues.put(PartIssueModel.currentpartstatusID,partIssueModel.getCurrentPartStatusID());
        contentValues.put(PartIssueModel.reqid,partIssueModel.getRequestID());
        contentValues.put(PartIssueModel.issuedpartid,partIssueModel.getIssuedPartID());
        contentValues.put(PartIssueModel.squence,partIssueModel.getSequence());
        contentValues.put(PartIssueModel.issuedwarehouseid,partIssueModel.getIssuedWarehouseID());
        contentValues.put(PartIssueModel.cb,partIssueModel.getCreatedBy());
        contentValues.put(PartIssueModel.lmb,partIssueModel.getLastModifiedBy());
        contentValues.put(PartIssueModel.cd,partIssueModel.getCreatedDateTime());
        contentValues.put(PartIssueModel.ld,partIssueModel.getLastModifiedDateTime());
        contentValues.put(PartIssueModel.partstatusid,partIssueModel.getPartStatusID());
        contentValues.put(PartIssueModel.updatedid,partIssueModel.getUpdatedID());
        contentValues.put(PartIssueModel.incid,partIssueModel.getIncidentID());
        contentValues.put(PartIssueModel.webid,partIssueModel.getVisitWebID());
        db.update(PartIssueModel.table,contentValues, PartIssueModel.updatedid  + "= ?", id);
        mContext.getContentResolver().notifyChange(SpareDAO.DB_SF_Spare, null);
    }
    public void InsertOrUpdate(PartIssueModel partIssueModel){
        String query = " SELECT  * FROM " + PartIssueModel.table  + " WHERE " + PartIssueModel.updatedid  + "  =" + partIssueModel.getUpdatedID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdatePartIssue(partIssueModel);
        } else {
            InsertPartIssue(partIssueModel);
        }
        c.close();
    }
    public List<PartIssueModel>partIssueModelList(int  id) {
        String query = " SELECT  * FROM " + PartIssueModel.table + " WHERE " + PartIssueModel.incid + "  =" + id + "";
        List<PartIssueModel>partobj=new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
            do {
              PartIssueModel partIssueModel=new PartIssueModel();
              partIssueModel.setCreatedDateTime(c.getString(c.getColumnIndex(PartIssueModel.cd)));
              partIssueModel.setLastModifiedDateTime(c.getString(c.getColumnIndex(PartIssueModel.ld)));
              partIssueModel.setCreatedBy(c.getInt(c.getColumnIndex(PartIssueModel.cb)));
              partIssueModel.setIssuedPartNo(c.getString(c.getColumnIndex(PartIssueModel.issuedpartno)));
              partIssueModel.setIssuedWarehouseID(c.getInt(c.getColumnIndex(PartIssueModel.issuedwarehouseid)));
              partIssueModel.setLastModifiedBy(c.getInt(c.getColumnIndex(PartIssueModel.lmb)));
              partIssueModel.setStatusName(c.getString(c.getColumnIndex(PartIssueModel.statusname)));
              partIssueModel.setRequestID(c.getInt(c.getColumnIndex(PartIssueModel.reqid)));
              partIssueModel.setRequestedStatusID(c.getInt(c.getColumnIndex(PartIssueModel.reqstatusID)));
              partIssueModel.setSequence(c.getInt(c.getColumnIndex(PartIssueModel.squence)));
              partIssueModel.setSerialNo(c.getString(c.getColumnIndex(PartIssueModel.serialno)));
              partIssueModel.setCurrentPartStatusID(c.getInt(c.getColumnIndex(PartIssueModel.currentpartstatusID)));
              partIssueModel.setPartStatusID(c.getInt(c.getColumnIndex(PartIssueModel.partstatusid)));
              partIssueModel.setUpdatedID(c.getInt(c.getColumnIndex(PartIssueModel.updatedid)));
              partobj.add(partIssueModel);
            } while (c.moveToNext());

        }
        }
        if(c!=null||!c.isClosed()){
            c.close();
        }
        return partobj;
    }
    public Cursor partIssueList(int  id) {
        String query = " SELECT  * FROM " + PartIssueModel.table + " WHERE " + PartIssueModel.incid + "  =" + id + "";
         Cursor c = db.rawQuery(query, null);
        return c;
    }
    public void UpdatePart(PartIssueModel partIssueModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(partIssueModel.getUpdatedID())};
        contentValues.put(PartIssueModel.lmb,partIssueModel.getLastModifiedBy());
        contentValues.put(PartIssueModel.ld,partIssueModel.getLastModifiedDateTime());
        contentValues.put(PartIssueModel.statusname,partIssueModel.getStatusName());
        contentValues.put(PartIssueModel.partstatusid,partIssueModel.getPartStatusID());
      //  contentValues.put(PartIssueModel.currentpartstatusID,partIssueModel.getCurrentPartStatusID());
        db.update(PartIssueModel.table,contentValues, PartIssueModel.updatedid  + "= ?", id);
        mContext.getContentResolver().notifyChange(SpareDAO.DB_SF_Spare, null);
    }
    public boolean ispartIssued(int id) {
        String sql = "select " + PartIssueModel.reqid + " from  " + PartIssueModel.table + " where " + PartIssueModel.reqid  + " =" + id;
        Cursor c = db.rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } finally {
            c.close();
        }
    }
    public void DeleteSpareIssue(int id){
        db.delete(PartIssueModel.table,PartIssueModel.incid + " ="+id+"",null);
        mContext.getContentResolver().notifyChange(SpareDAO.DB_SF_Spare, null);
    }
}