package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.Activities.LocalVendor;
import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.Model.InvoiceItemsModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Model.LoginModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 02/03/2017.
 */

public class LocalVendorDAO extends SFSingelton {

    private Context mcontext;
    private static String DATABASE_NAME = "SFdatabasename";
    public static final Uri LocalVendor_URI = Uri
            .parse("sqlite://" + "sf_localvendor" + "/" + DATABASE_NAME);
    public LocalVendorDAO(Context context) {
        super(context);
        this.mcontext=context;
    }
    public void InsertLocalVendor(LocalVendorModel obj){
        ContentValues contentValues=new ContentValues();
        contentValues.put(LocalVendorModel.userorvendorid,obj.getUserOrVendorID());
        contentValues.put(LocalVendorModel.webid,obj.getWebID());
        contentValues.put(LocalVendorModel.attachname,obj.getAttachmentName());
        contentValues.put(LocalVendorModel.attachpath,obj.getAttachmentPath());
        contentValues.put(LocalVendorModel.incID,obj.getIncidentID());
        contentValues.put(LocalVendorModel.invoicedate,obj.getInvoiceDate());
        contentValues.put(LocalVendorModel.invoiceno,obj.getInvoiceNumber());
        contentValues.put(LocalVendorModel.sparecharge,obj.getSpareCharge());
        contentValues.put(LocalVendorModel.cb,obj.getCreatedBy());
        contentValues.put(LocalVendorModel.Resourcename,obj.getResourceName());
        contentValues.put(LocalVendorModel.lmb,obj.getLastModifiedBy());
        contentValues.put(LocalVendorModel.cd,obj.getCreatedDateTime());
        contentValues.put(LocalVendorModel.issent,obj.getIsSent());
        contentValues.put(LocalVendorModel.is_fileexist,obj.getIsFileExist());
        contentValues.put(LocalVendorModel.LV_Total,obj.getTotal());
        contentValues.put(LocalVendorModel.servicecharge,obj.getServiceCharge());
        contentValues.put(LocalVendorModel.localfilename,obj.getLocalFileName());
        contentValues.put(LocalVendorModel.targettype,obj.getTargetType());
        contentValues.put(LocalVendorModel.chargetype,obj.getChargeType());
        contentValues.put(LocalVendorModel.localfilepath,obj.getLocalFilePath());
        contentValues.put(LocalVendorModel.ld,obj.getLastModifiedDateTime());
        mcontext.getContentResolver().notifyChange(LocalVendor_URI, null);
        db.insert(LocalVendorModel.table, null, contentValues);
    }
    private void UpdateLocalVendor(LocalVendorModel obj){
        ContentValues contentValues=new ContentValues();
        contentValues.put(LocalVendorModel.Resourcename,obj.getResourceName());
        contentValues.put(LocalVendorModel.userorvendorid,obj.getUserOrVendorID());
        contentValues.put(LocalVendorModel.webid,obj.getWebID());
        contentValues.put(LocalVendorModel.attachname,obj.getAttachmentName());
        contentValues.put(LocalVendorModel.attachpath,obj.getAttachmentPath());
        contentValues.put(LocalVendorModel.LV_Total,obj.getTotal());
        contentValues.put(LocalVendorModel.servicecharge,obj.getServiceCharge());
        contentValues.put(LocalVendorModel.incID,obj.getIncidentID());
        contentValues.put(LocalVendorModel.invoicedate,obj.getInvoiceDate());
        contentValues.put(LocalVendorModel.invoiceno,obj.getInvoiceNumber());
        contentValues.put(LocalVendorModel.targettype,obj.getTargetType());
        contentValues.put(LocalVendorModel.sparecharge,obj.getSpareCharge());
        contentValues.put(LocalVendorModel.is_fileexist,obj.getIsFileExist());
        contentValues.put(LocalVendorModel.cb,obj.getCreatedBy());
        contentValues.put(LocalVendorModel.lmb,obj.getLastModifiedBy());
        contentValues.put(LocalVendorModel.cd,obj.getCreatedDateTime());
        contentValues.put(LocalVendorModel.issent,obj.getIsSent());
        contentValues.put(LocalVendorModel.localfilename,obj.getLocalFileName());
        contentValues.put(LocalVendorModel.chargetype,obj.getChargeType());
        contentValues.put(LocalVendorModel.localfilepath,obj.getLocalFilePath());
        contentValues.put(LocalVendorModel.ld,obj.getLastModifiedDateTime());
        mcontext.getContentResolver().notifyChange(LocalVendor_URI, null);
        db.update(LocalVendorModel.table, contentValues, LocalVendorModel.webid + "= ?", new String[]{String.valueOf(obj.getWebID())});
    }
    public Cursor LocalVendorList(int id) {
        Cursor cursor = db.rawQuery("select * from " + LocalVendorModel.table + " where " + LocalVendorModel.incID + " =" + id, null);
        cursor.setNotificationUri(mcontext.getContentResolver(), LocalVendor_URI);
        return cursor;
    }
    public List<LocalVendorModel> LocalVendorData() {
        String query = " SELECT "
                + LocalVendorModel.id + " ,"
                + LocalVendorModel.webid + ","
                + LocalVendorModel.incID + ","
                + LocalVendorModel.invoiceno + " ,"
                + LocalVendorModel.invoicedate + " ,"
                + LocalVendorModel.userorvendorid  + " ,"
                + LocalVendorModel.sparecharge + " ,"
                + LocalVendorModel.attachname + " ,"
                + LocalVendorModel.chargetype + " ,"
                + LocalVendorModel.targettype + " ,"
                + LocalVendorModel.servicecharge + " ,"
                + LocalVendorModel.attachpath + " ,"
                + LocalVendorModel.cb + " ,"
                + LocalVendorModel.lmb + " ,"
                + LocalVendorModel.cd + "  ,"
                + LocalVendorModel.ld  + "  FROM " + LocalVendorModel.table +
                " WHERE " + LocalVendorModel.issent  + " =" + 1  + "";

    //    String query= " SELECT * FROM " + LocalVendorModel.table +    " WHERE " + LocalVendorModel.issent  + " =" + 1  + "";

         Cursor cursor = db.rawQuery(query, null);
         List<LocalVendorModel> listobj = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    LocalVendorModel localVendorModel=new LocalVendorModel();
                    InvoiceItemsModel invoiceItemsModel=new InvoiceItemsModel();
                    invoiceItemsModel.setIncidentID(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.incID)));
                    invoiceItemsModel.setChargeType(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.chargetype)));
                    invoiceItemsModel.setServiceCharge(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.servicecharge)));
                    invoiceItemsModel.setSpareCharge(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.sparecharge)));
                    int total=cursor.getInt(cursor.getColumnIndex(LocalVendorModel.servicecharge)) + cursor.getInt(cursor.getColumnIndex(LocalVendorModel.sparecharge));
                  //  localVendorModel.setTotal(total);
                    invoiceItemsModel.setTotal(total);
                    List<InvoiceItemsModel>lvlist=new ArrayList<>();
                    lvlist.add(invoiceItemsModel);
                    localVendorModel.setInvoiceItems(lvlist);
                     //lvlist.add();
                  //  lvlist.add();
            //        localVendorModel.setIncidentID(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.incID)));
                  //  localVendorModel.setChargeType(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.chargetype)));
                  //  localVendorModel.setServiceCharge(cursor.getString(cursor.getColumnIndex(LocalVendorModel.servicecharge)));
                 //   localVendorModel.setSpareCharge(cursor.getString(cursor.getColumnIndex(LocalVendorModel.sparecharge)));
                   // lvlist.add(localVendorModel);
                  //  localVendorModel.setInvoiceItems(lvlist);
                    localVendorModel.setTargetType(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.targettype)));
                    localVendorModel.setPrimaryID(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.id)));
                    localVendorModel.setWebID(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.webid)));
                    localVendorModel.setInvoiceNumber(cursor.getString(cursor.getColumnIndex(LocalVendorModel.invoiceno)));
                    localVendorModel.setInvoiceDate(cursor.getString(cursor.getColumnIndex(LocalVendorModel.invoicedate)));
                    localVendorModel.setUserOrVendorID(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.userorvendorid)));
                    localVendorModel.setAttachmentName(cursor.getString(cursor.getColumnIndex(LocalVendorModel.attachname)));
                    localVendorModel.setAttachmentPath(cursor.getString(cursor.getColumnIndex(LocalVendorModel.attachpath)));
                    localVendorModel.setCreatedDateTime(cursor.getString(cursor.getColumnIndex(LocalVendorModel.cd)));
                    localVendorModel.setLastModifiedDateTime(cursor.getString(cursor.getColumnIndex(LocalVendorModel.ld)));
                    localVendorModel.setCreatedBy(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.cb)));
                    localVendorModel.setLastModifiedBy(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.lmb)));
                    listobj.add(localVendorModel);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null || !cursor.isClosed()) {
            cursor.close();
        }
        cursor.setNotificationUri(mcontext.getContentResolver(), LocalVendor_URI);
        return listobj;
    }
    public void InsertOrUpdate(LocalVendorModel localVendorModel) {
        String query = " SELECT  * FROM " + LocalVendorModel.table + " WHERE " + LocalVendorModel.webid  + "  =" + localVendorModel.getWebID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateLocalVendor(localVendorModel);
        } else {
            InsertLocalVendor(localVendorModel);
        }
        mcontext.getContentResolver().notifyChange(LocalVendor_URI, null);
        c.close();
    }
    public List<LocalVendorModel> Document() {
        String query = " SELECT "
                + LocalVendorModel.id + " ,"
                + LocalVendorModel.localfilepath + " ,"
                + LocalVendorModel.incID + " "
                + "  FROM " + LocalVendorModel.table +
                " WHERE " + LocalVendorModel.issent  + " =" + 1  + " and "  + LocalVendorModel.is_fileexist  + " =" + 1;


        Cursor cursor = db.rawQuery(query, null);
        List<LocalVendorModel> listobj = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    LocalVendorModel localVendorModel=new LocalVendorModel();
                    localVendorModel.setIncidentID(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.incID)));
                    localVendorModel.setPrimaryID(cursor.getInt(cursor.getColumnIndex(LocalVendorModel.id)));
                    localVendorModel.setLocalFilePath(cursor.getString(cursor.getColumnIndex(LocalVendorModel.localfilepath)));
                    listobj.add(localVendorModel);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null || !cursor.isClosed()) {
            cursor.close();
        }
        cursor.setNotificationUri(mcontext.getContentResolver(), LocalVendor_URI);
        return listobj;
    }
    public int IsVendorExist() {
        String countQuery = "SELECT  * FROM " + LocalVendorModel.table + " where " + LocalVendorModel.issent  + " = " + 1 + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public void UpdateWebID(LocalVendorModel  localVendorModel) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(localVendorModel.getPrimaryID())};
         contentValues.put(LocalVendorModel.issent,0);
        contentValues.put(LocalVendorModel.webid, localVendorModel.getWebID());
        contentValues.put(LocalVendorModel.LV_Total,localVendorModel.getTotal());
        mcontext.getContentResolver().notifyChange(LocalVendor_URI, null);
        db.update(LocalVendorModel.table, contentValues, LocalVendorModel.id + "= ?", args);
    }
    public void UpdateFileID(LocalVendorModel  obj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(obj.getPrimaryID())};
        //contentValues.put(LocalVendorModel.webid, obj.getWebID());
        contentValues.put(LocalVendorModel.is_fileexist,0);
        contentValues.put(LocalVendorModel.attachpath, obj.getAttachmentPath());
        contentValues.put(LocalVendorModel.attachname, obj.getAttachmentName());
        db.update(LocalVendorModel.table, contentValues, LocalVendorModel.id + "= ?", args);
        mcontext.getContentResolver().notifyChange(LocalVendor_URI, null);
    }
    public void Delete(int id){
        db.delete(LocalVendorModel.table,LocalVendorModel.incID  + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(LocalVendor_URI, null);
    }
    public void DeleteSingle(int id){
        db.delete(LocalVendorModel.table,LocalVendorModel.id  + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(LocalVendor_URI, null);
    }
    public int IsLocalFileExist() {
        String countQuery = "SELECT  * FROM " + LocalVendorModel.table  + " where " + LocalVendorModel.is_fileexist    + " = " + 1 + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
}
