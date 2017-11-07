package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 25-01-2017.
 */

public class SpareDAO extends SFSingelton {
    public static final Uri DB_SF_Spare = Uri
            .parse("sqlite://" + "sf_spare" + "/" + "db");
    private Context mcontext;
    public SpareDAO(Context context) {
        super(context);
        this.mcontext=context;
    }


public void Insert(SpareModel spareModel){
    ContentValues contentValues=new ContentValues();
    contentValues.put(SpareModel.webid,spareModel.getWebID());
    contentValues.put(SpareModel.advreturn,spareModel.getAdvanceReturn());
    contentValues.put(SpareModel.issued,spareModel.getIssue());
    contentValues.put(SpareModel.visitprimaryID,spareModel.getVisitPrimaryID());
    contentValues.put(SpareModel.requestedspec,spareModel.getRequestedPartSpecification());
    contentValues.put(SpareModel.partstatus,spareModel.getPartStatus());
    contentValues.put(SpareModel.partno,spareModel.getPartno());
    contentValues.put(SpareModel.partid,spareModel.getPartID());
    contentValues.put(SpareModel.updateid,spareModel.getUpdateID());
    contentValues.put(SpareModel.incid,spareModel.getIncidentID());
    contentValues.put(SpareModel.editflag,spareModel.getEditFlag());
    contentValues.put(SpareModel.samepart,spareModel.getSamePart());
    contentValues.put(SpareModel.defectiveserialno,spareModel.getDefectiveSerialno());
    contentValues.put(SpareModel.remarks,spareModel.getRemarks());
    contentValues.put(SpareModel.statusid,spareModel.getStatusID());
    contentValues.put(SpareModel.visitwebid,spareModel.getVisitWebID());
    contentValues.put(SpareModel.brandname,spareModel.getBrandName());
    contentValues.put(SpareModel.modelname,spareModel.getModelName());
    contentValues.put(SpareModel.catname,spareModel.getCategoryName());
    contentValues.put(SpareModel.specification,spareModel.getPartSpecification());
    mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
    db.insert(SpareModel.table,null,contentValues);
 }
    public void WebInsert(SpareModel spareModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(SpareModel.webid,spareModel.getWebID());
        contentValues.put(SpareModel.advreturn,spareModel.getAdvanceReturn());
        contentValues.put(SpareModel.issued,spareModel.getIssue());
        contentValues.put(SpareModel.visitprimaryID,spareModel.getVisitPrimaryID());
        contentValues.put(SpareModel.requestedspec,spareModel.getRequestedPartSpecification());
        contentValues.put(SpareModel.partstatus,spareModel.getPartStatus());
        contentValues.put(SpareModel.partno,spareModel.getPartno());
        contentValues.put(SpareModel.partid,spareModel.getPartID());
        contentValues.put(SpareModel.updateid,spareModel.getUpdateID());
        contentValues.put(SpareModel.incid,spareModel.getIncidentID());
        contentValues.put(SpareModel.editflag,3);
        contentValues.put(SpareModel.samepart,spareModel.getSamePart());
        contentValues.put(SpareModel.defectiveserialno,spareModel.getDefectiveSerialno());
        contentValues.put(SpareModel.remarks,spareModel.getRemarks());
        contentValues.put(SpareModel.statusid,spareModel.getStatusID());
        contentValues.put(SpareModel.visitwebid,spareModel.getVisitWebID());
        contentValues.put(SpareModel.brandname,spareModel.getBrandName());
        contentValues.put(SpareModel.modelname,spareModel.getModelName());
        contentValues.put(SpareModel.catname,spareModel.getCategoryName());
        contentValues.put(SpareModel.specification,spareModel.getPartSpecification());
        mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
        db.insert(SpareModel.table,null,contentValues);
    }
    public int  Update(SpareModel spareModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(spareModel.getID())};
        contentValues.put(SpareModel.editflag,spareModel.getEditFlag());
        contentValues.put(SpareModel.partid,spareModel.getPartID());
        contentValues.put(SpareModel.updateid,spareModel.getUpdateID());
        contentValues.put(SpareModel.webid,spareModel.getWebID());
        contentValues.put(SpareModel.statusid,spareModel.getStatusID());
        contentValues.put(SpareModel.advreturn,spareModel.getAdvanceReturn());
        contentValues.put(SpareModel.issued,spareModel.getIssue());
        contentValues.put(SpareModel.visitprimaryID,spareModel.getVisitPrimaryID());
        contentValues.put(SpareModel.partstatus,spareModel.getPartStatus());
        contentValues.put(SpareModel.partno,spareModel.getPartno());
        contentValues.put(SpareModel.incid,spareModel.getIncidentID());
        contentValues.put(SpareModel.requestedspec,spareModel.getRequestedPartSpecification());
        contentValues.put(SpareModel.samepart,spareModel.getSamePart());
        contentValues.put(SpareModel.visitwebid,spareModel.getVisitWebID());
        contentValues.put(SpareModel.brandname,spareModel.getBrandName());
        contentValues.put(SpareModel.modelname,spareModel.getModelName());
        contentValues.put(SpareModel.catname,spareModel.getCategoryName());
        contentValues.put(SpareModel.specification,spareModel.getPartSpecification());
        contentValues.put(SpareModel.defectiveserialno,spareModel.getDefectiveSerialno());
        contentValues.put(SpareModel.remarks,spareModel.getRemarks());
        mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
     int  ids=   db.update(SpareModel.table,contentValues, SpareModel.id + "= ?", id);
       return ids;
}
    public void   DeleteUpdate(SpareModel spareModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(spareModel.getID())};
        contentValues.put(SpareModel.editflag,spareModel.getEditFlag());
        contentValues.put(SpareModel.webid,spareModel.getWebID());
        contentValues.put(SpareModel.incid,spareModel.getIncidentID());
        mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
        db.update(SpareModel.table,contentValues, SpareModel.id + "= ?", id);
    }

    public Cursor SpareRequest(int id) {
        Cursor cursor = db.rawQuery("select * from " + SpareModel.table + " where " + SpareModel.incid + " ="+ id + " and " + SpareModel.editflag + " != 2 "  , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Spare);
        return cursor;
    }
    public Cursor SpareUpdate(int id) {
      //  Cursor cursor = db.rawQuery(" select  " + SpareModel.partno + " , "+ SpareModel.partstatus + " ," +  SpareModel.remarks + " ," + SpareModel.advreturn + " ," + SpareModel.samepart + " ," + SpareModel.defectiveserialno  + " from " + SpareModel.table +  " where " + SpareModel.id + " =" +id, null);
        Cursor cursor=db.rawQuery(" select * from " + SpareModel.table +   " where " + SpareModel.id + " =" +id, null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Spare);
        return cursor;
    }
    public Cursor SpareViewByID(int id,int webID) {
        Cursor cursor = db.rawQuery(" select " + SpareModel.partno + " , "+ SpareModel.partstatus  + " ," + SpareModel.advreturn + " ," + SpareModel.samepart  +   " from "+ SpareModel.table +  " where " + SpareModel.visitprimaryID  + " =" +id +  " or " + SpareModel.visitwebid + " =" + webID  , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Spare);
        return cursor;
    }
    public void Delete(int id){
        db.delete(SpareModel.table,SpareModel.id + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
    }

    public void DeleteNotUpdate(int incid){
        db.delete(SpareModel.table,SpareModel.incid  + " ="+incid + " and " + SpareModel.updateid  + " =2",null);
        mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
    }
    public int getCount(int id) {
        String countQuery = "SELECT  * FROM  "  + SpareModel.table  + " where  " + SpareModel.incid + " ="+ id +  "  and " + SpareModel.editflag + " = 3 ";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public List<SpareModel> spareRequestList(int id){
        Cursor cursor = db.rawQuery("select * from " + SpareModel.table + " where " + SpareModel.incid + " ="+id + " and " + SpareModel.editflag + " != 2", null);
        List<SpareModel>listobj=new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    SpareModel spareModel=new SpareModel();
                    spareModel.setQuantity(1);
                    String is_issued=cursor.getString(cursor.getColumnIndex(SpareModel.issued));
                    if(is_issued.equals("Yes")){
                        spareModel.setIsIssued(1);
                    }
                    else {
                        spareModel.setIsIssued(0);
                    }
                    //webid=requestedid
                    spareModel.setWebID(cursor.getInt(cursor.getColumnIndex(SpareModel.webid)));
                    spareModel.setID(cursor.getInt(cursor.getColumnIndex(SpareModel.id)));
                    spareModel.setRequestedPartSpecification(cursor.getString(cursor.getColumnIndex(SpareModel.requestedspec)));
                    spareModel.setPartID(cursor.getInt(cursor.getColumnIndex(SpareModel.partid)));
                    spareModel.setPartno(cursor.getString(cursor.getColumnIndex(SpareModel.partno)));
                    spareModel.setStatusID(cursor.getInt(cursor.getColumnIndex(SpareModel.statusid)));
                    spareModel.setRemarks(cursor.getString(cursor.getColumnIndex(SpareModel.remarks)));
                    spareModel.setAdvanceReturn(cursor.getInt(cursor.getColumnIndex(SpareModel.advreturn)));
                    spareModel.setSamePart(cursor.getInt(cursor.getColumnIndex(SpareModel.samepart)));
                    spareModel.setDefectiveSerialno(cursor.getString(cursor.getColumnIndex(SpareModel.defectiveserialno)));
                    listobj.add(spareModel);
                } while (cursor.moveToNext());
            }
        }
        if(cursor!=null||!cursor.isClosed()){
            cursor.close();
        }
        return listobj;
    }
    public void SpareInsertorUpdate(SpareModel modlobj) {
        String query = " SELECT  * FROM " + SpareModel.table    + " WHERE " + SpareModel.webid    + "  =" + modlobj.getWebID();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
           // WebUpdate(modlobj);
         Log.e("already exist","already exist");
        } else {

            WebInsert(modlobj);
        }
        c.close();
    }

    public void DeleteSpareReq(int id){
        db.delete(SpareModel.table,SpareModel.incid + " ="+ id +  "  and " + SpareModel.editflag + " = 3", null);
        mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
    }
    public void DeleteAllSpareReq(int id){
        db.delete(SpareModel.table,SpareModel.incid + " ="+ id ,null);
        mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
    }
    public void UpdateIDUpdte(int id){
        ContentValues contentValues=new ContentValues();
        String[] incid = {String.valueOf(id)};
        contentValues.put(SpareModel.updateid,0);
        mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
        db.update(SpareModel.table,contentValues, SpareModel.incid + "= ?", incid);

    }
}