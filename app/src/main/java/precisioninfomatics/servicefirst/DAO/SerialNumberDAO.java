package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.CustomerListModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.Model.SpareModel;


/**
 * Created by 4264 on 28/03/2017.
 */

public class SerialNumberDAO extends SFSingelton {
    private Context mContext;
    public static final Uri SerialNumber = Uri
            .parse("sqlite://" + "sf_serialnumber" + "/" + "SFdatabasename");

    public SerialNumberDAO(Context context) {
        super(context);
        this.mContext=context;
    }
    public void InsertSerialNumber(SerialNumberMapModel serialNumberMapModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(SerialNumberMapModel.incid,serialNumberMapModel.getIncidentID());
        contentValues.put(SerialNumberMapModel.serialno,serialNumberMapModel.getSerialNumber());
 //       contentValues.put(SerialNumberMapModel.shiptoserialno,serialNumberMapModel.getShipToSerialNumber());
        contentValues.put(SerialNumberMapModel.checklistlineid,serialNumberMapModel.getSerialNumberID());
        db.insert(SerialNumberMapModel.table,null,contentValues);
        mContext.getContentResolver().notifyChange(SerialNumber, null);
    }
    private void UpdateSerialNumber(SerialNumberMapModel serialNumberMapModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(serialNumberMapModel.getSerialNumberID())};
        contentValues.put(SerialNumberMapModel.incid,serialNumberMapModel.getIncidentID());
        contentValues.put(SerialNumberMapModel.serialno,serialNumberMapModel.getSerialNumber());
    //    contentValues.put(SerialNumberMapModel.shiptoserialno,serialNumberMapModel.getShipToSerialNumber());
        contentValues.put(SerialNumberMapModel.checklistlineid,serialNumberMapModel.getSerialNumberID());
        db.update(SerialNumberMapModel.table,contentValues, SerialNumberMapModel.checklistlineid  + "= ?", id);
        mContext.getContentResolver().notifyChange(SerialNumber, null);
    }
    public void InsertOrUpdate(SerialNumberMapModel serialNumberMapModel){
        String query = " SELECT  * FROM " + SerialNumberMapModel.table  + " WHERE " + SerialNumberMapModel.checklistlineid  + "  =" + serialNumberMapModel.getSerialNumberID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateSerialNumber(serialNumberMapModel);
        } else {
            InsertSerialNumber(serialNumberMapModel);
        }
        c.close();
    }

    public List<SerialNumberMapModel>listobj(int id){
        String query=" SELECT " + SerialNumberMapModel.serialno+ " ," +SerialNumberMapModel.checklistlineid   +" from "+ SerialNumberMapModel.table  + " WHERE " + SerialNumberMapModel.incid   + "  =" +id + "";
        Cursor c=db.rawQuery(query,null);
        List<SerialNumberMapModel>listobj=new ArrayList<>();
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    SerialNumberMapModel serialNumberMapModel=new SerialNumberMapModel();
                    serialNumberMapModel.setSerialNumber(c.getString(c.getColumnIndex(SerialNumberMapModel.serialno)));
                    serialNumberMapModel.setSerialNumberID(c.getInt(c.getColumnIndex(SerialNumberMapModel.checklistlineid)));
         //           serialNumberMapModel.setShipToSerialNumber(c.getString(c.getColumnIndex(SerialNumberMapModel.shiptoserialno)));
                    listobj.add(serialNumberMapModel);
                }while (c.moveToNext());
            }
        }
        if(c!=null||!c.isClosed()){
            c.close();
        }
     return listobj;
    }
    public void InsertSerialNumberMap(SerialNumberMapModel serialNumberMapModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(SerialNumberMapModel.visitid,serialNumberMapModel.getVisitID());
        contentValues.put(SerialNumberMapModel.visitwebid,serialNumberMapModel.getVisitWebID());
        contentValues.put(SerialNumberMapModel.incid,serialNumberMapModel.getIncidentID());
       // contentValues.put(SerialNumberMapModel.updatedid,serialNumberMapModel.getUpdatedID());
        contentValues.put(SerialNumberMapModel.serialno,serialNumberMapModel.getSerialNumber());
        contentValues.put(SerialNumberMapModel.shiptoserialno,serialNumberMapModel.getShipToSerialNumber());
        contentValues.put(SerialNumberMapModel.checklistlineid,serialNumberMapModel.getSerialNumberID());
        db.insert(SerialNumberMapModel.SerialMapTable,null,contentValues);
        mContext.getContentResolver().notifyChange(SpareDAO.DB_SF_Spare, null);
    }
    public Cursor Serialnumbermap(int id,int webID) {
        Cursor cursor = db.rawQuery(" select " + SerialNumberMapModel.serialno + " , "+ SerialNumberMapModel.id  + " , "+  SerialNumberMapModel.shiptoserialno  +   " from "+ SerialNumberMapModel.SerialMapTable +  " where " + SerialNumberMapModel.visitid  + " =" +id +  " or " + SerialNumberMapModel.visitwebid + " =" + webID  , null);
        cursor.setNotificationUri(mContext.getContentResolver(), SpareDAO.DB_SF_Spare);
        return cursor;
    }
    public List<SerialNumberMapModel>serialnumberlist(int id){
        String query=" SELECT " + SerialNumberMapModel.serialno+ " ," +SerialNumberMapModel.shiptoserialno + " , " +  SerialNumberMapModel.checklistlineid   +" from "+ SerialNumberMapModel.SerialMapTable  + " WHERE " + SerialNumberMapModel.visitid   + "  =" +id + "";
        Cursor c=db.rawQuery(query,null);
        List<SerialNumberMapModel>listobj=new ArrayList<>();
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    SerialNumberMapModel serialNumberMapModel=new SerialNumberMapModel();
                    serialNumberMapModel.setSerialNumber(c.getString(c.getColumnIndex(SerialNumberMapModel.serialno)));
                    serialNumberMapModel.setSerialNumberID(c.getInt(c.getColumnIndex(SerialNumberMapModel.checklistlineid)));
                          serialNumberMapModel.setShipToSerialNumber(c.getString(c.getColumnIndex(SerialNumberMapModel.shiptoserialno)));
                    listobj.add(serialNumberMapModel);
                }while (c.moveToNext());
            }
        }
        if(c!=null||!c.isClosed()){
            c.close();
        }
        return listobj;
    }

    public boolean SerialNumberDuplicate(int id) {
        String sql = "select " + SerialNumberMapModel.checklistlineid + " from  " + SerialNumberMapModel.SerialMapTable  + " where " + SerialNumberMapModel.checklistlineid  + " =" + id;
        Cursor c = db.rawQuery(sql, null);
        try {
            return c.moveToFirst();
        } finally {
            c.close();
        }
    }
    public void Delete(int id){
        db.delete(SerialNumberMapModel.SerialMapTable ,SerialNumberMapModel.id + " ="+id+"",null);
        mContext.getContentResolver().notifyChange(SpareDAO.DB_SF_Spare, null);
    }
    public void DeleteSerialNumNotUpdated(int visitid){
        db.delete(SerialNumberMapModel.SerialMapTable,SerialNumberMapModel.visitid + " ="+visitid  + " and " + SerialNumberMapModel.updatedid  + " =2",null);
        mContext.getContentResolver().notifyChange(SpareDAO.DB_SF_Spare, null);
    }
    public void UpdateSerialNumUpdte(int id){
        ContentValues contentValues=new ContentValues();
        String[] visitid = {String.valueOf(id)};
        contentValues.put(SerialNumberMapModel.updatedid,0);
        mContext.getContentResolver().notifyChange(SpareDAO.DB_SF_Spare, null);
        db.update(SerialNumberMapModel.SerialMapTable,contentValues, SerialNumberMapModel.visitid + "= ?", visitid);

    }
    public Cursor SerialnumbermapView(int id,int webID) {
        Cursor cursor = db.rawQuery(" select " + SerialNumberMapModel.serialno + " , "+ SerialNumberMapModel.id  + " , "+  SerialNumberMapModel.shiptoserialno  +   " from "+ SerialNumberMapModel.SerialMapTable +  " where " + SerialNumberMapModel.visitid  + " =" +id +  " or " + SerialNumberMapModel.visitwebid + " =" + webID  , null);
        cursor.setNotificationUri(mContext.getContentResolver(), SpareDAO.DB_SF_Spare);
        return cursor;
    }
}
