package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Fragments.GeneralClaim;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.GeneralClaimDocument;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.ListParameterizedType;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 04/04/2017.
 */

public class GeneralClaimDAO extends SFSingelton {
    private Context mcontext;
    public static final Uri DB_SF_GeneralClaim = Uri
            .parse("sqlite://" + "sf_incident" + "/" + "db");
    public GeneralClaimDAO(Context context) {
        super(context);
        this.mcontext = context;
    }
    public void InsertGeneralClaim(GeneralClaimModel generalClaimobj){
        ContentValues contentValues=new ContentValues();
        contentValues.put(GeneralClaimModel.GC_claimcost,generalClaimobj.getClaimCost());
        contentValues.put(GeneralClaimModel.GC_Total,generalClaimobj.getTotal());
        contentValues.put(GeneralClaimModel.GC_FromDate,generalClaimobj.getFromDate());
        contentValues.put(GeneralClaimModel.GC_ToDate,generalClaimobj.getToDate());
        contentValues.put(GeneralClaimModel.GC_ca,generalClaimobj.getCreatedAt());
        contentValues.put(GeneralClaimModel.GC_cb,generalClaimobj.getCreatedBy());
        contentValues.put(GeneralClaimModel.GC_cbName,generalClaimobj.getCreatedbyName());
        contentValues.put(GeneralClaimModel.GC_filename,generalClaimobj.getFileName());
        contentValues.put(GeneralClaimModel.GC_ClaimID,generalClaimobj.getClaimID());
        contentValues.put(GeneralClaimModel.Gc_IncidentID,generalClaimobj.getIncidentID());
       contentValues.put(GeneralClaimModel.GC_ServerPath,generalClaimobj.getDocs());
       contentValues.put(GeneralClaimModel.GC_FromLoc,generalClaimobj.getFromLoc());
        contentValues.put(GeneralClaimModel.Gc_ToLoc,generalClaimobj.getToLoc());
        contentValues.put(GeneralClaimModel.GC_lma,generalClaimobj.getLastModifiedDateTime());
        contentValues.put(GeneralClaimModel.GC_lmb,generalClaimobj.getLastModifiedBy());
       contentValues.put(GeneralClaimModel.GC_Total,generalClaimobj.getTotal());
        contentValues.put(GeneralClaimModel.GC_isServer,generalClaimobj.getIsServer());
         contentValues.put(GeneralClaimModel.GC_Filepath,generalClaimobj.getSelectSliptoUpload());
        contentValues.put(GeneralClaimModel.GC_isexist,generalClaimobj.getIsFileExist());
        contentValues.put(GeneralClaimModel.GC_issent,generalClaimobj.getIsSent());
        db.insert(GeneralClaimModel.GC_Table,null,contentValues);
        mcontext.getContentResolver().notifyChange(DB_SF_GeneralClaim, null);
    }
    public void UpdateLocalGeneralClaim(GeneralClaimModel generalClaimobj){
        ContentValues contentValues=new ContentValues();
        String[] args = {String.valueOf(generalClaimobj.getID())};
        contentValues.put(GeneralClaimModel.GC_claimcost,generalClaimobj.getClaimCost());
        contentValues.put(GeneralClaimModel.GC_Total,generalClaimobj.getTotal());
        contentValues.put(GeneralClaimModel.GC_FromDate,generalClaimobj.getFromDate());
        contentValues.put(GeneralClaimModel.GC_ToDate,generalClaimobj.getToDate());
        contentValues.put(GeneralClaimModel.GC_ca,generalClaimobj.getCreatedAt());
        contentValues.put(GeneralClaimModel.GC_cb,generalClaimobj.getCreatedBy());
        contentValues.put(GeneralClaimModel.GC_isServer,generalClaimobj.getIsServer());
        contentValues.put(GeneralClaimModel.GC_cbName,generalClaimobj.getCreatedbyName());
        contentValues.put(GeneralClaimModel.GC_filename,generalClaimobj.getFileName());
        contentValues.put(GeneralClaimModel.GC_lma,generalClaimobj.getLastModifiedDateTime());
        contentValues.put(GeneralClaimModel.GC_lmb,generalClaimobj.getLastModifiedBy());
        contentValues.put(GeneralClaimModel.GC_ClaimID,generalClaimobj.getClaimID());
        contentValues.put(GeneralClaimModel.Gc_IncidentID,generalClaimobj.getIncidentID());
        contentValues.put(GeneralClaimModel.GC_ServerPath,generalClaimobj.getDocs());
        contentValues.put(GeneralClaimModel.GC_Total,generalClaimobj.getTotal());
        contentValues.put(GeneralClaimModel.GC_FromLoc,generalClaimobj.getFromLoc());
        contentValues.put(GeneralClaimModel.Gc_ToLoc,generalClaimobj.getToLoc());
         contentValues.put(GeneralClaimModel.GC_Filepath,generalClaimobj.getSelectSliptoUpload());
        contentValues.put(GeneralClaimModel.GC_isexist,generalClaimobj.getIsFileExist());
        contentValues.put(GeneralClaimModel.GC_issent,generalClaimobj.getIsSent());
        db.update(GeneralClaimModel.GC_Table, contentValues, GeneralClaimModel.GC_ID  + "= ?", args);
        mcontext.getContentResolver().notifyChange(DB_SF_GeneralClaim, null);
    }
    public void UpdateGeneralClaim(GeneralClaimModel generalClaimobj){
        ContentValues contentValues=new ContentValues();
        String[] args = {String.valueOf(generalClaimobj.getClaimID())};
        contentValues.put(GeneralClaimModel.GC_claimcost,generalClaimobj.getClaimCost());
        contentValues.put(GeneralClaimModel.GC_Total,generalClaimobj.getTotal());
        contentValues.put(GeneralClaimModel.GC_FromDate,generalClaimobj.getFromDate());
        contentValues.put(GeneralClaimModel.GC_ToDate,generalClaimobj.getToDate());
        contentValues.put(GeneralClaimModel.GC_ca,generalClaimobj.getCreatedAt());
        contentValues.put(GeneralClaimModel.GC_cb,generalClaimobj.getCreatedBy());
        contentValues.put(GeneralClaimModel.GC_isServer,generalClaimobj.getIsServer());
        contentValues.put(GeneralClaimModel.GC_cbName,generalClaimobj.getCreatedbyName());
        contentValues.put(GeneralClaimModel.GC_filename,generalClaimobj.getFileName());
        contentValues.put(GeneralClaimModel.GC_lma,generalClaimobj.getLastModifiedDateTime());
        contentValues.put(GeneralClaimModel.GC_lmb,generalClaimobj.getLastModifiedBy());
        contentValues.put(GeneralClaimModel.GC_ClaimID,generalClaimobj.getClaimID());
        contentValues.put(GeneralClaimModel.Gc_IncidentID,generalClaimobj.getIncidentID());
        contentValues.put(GeneralClaimModel.GC_ServerPath,generalClaimobj.getDocs());
        contentValues.put(GeneralClaimModel.GC_Total,generalClaimobj.getTotal());
        contentValues.put(GeneralClaimModel.GC_FromLoc,generalClaimobj.getFromLoc());
        contentValues.put(GeneralClaimModel.Gc_ToLoc,generalClaimobj.getToLoc());
         contentValues.put(GeneralClaimModel.GC_Filepath,generalClaimobj.getSelectSliptoUpload());
        contentValues.put(GeneralClaimModel.GC_isexist,generalClaimobj.getIsFileExist());
        contentValues.put(GeneralClaimModel.GC_issent,generalClaimobj.getIsSent());
        db.update(GeneralClaimModel.GC_Table, contentValues, GeneralClaimModel.GC_ClaimID  + "= ?", args);
        mcontext.getContentResolver().notifyChange(DB_SF_GeneralClaim, null);
    }
    public Cursor GeneralClaimList(int id){
        Cursor cursor = db.rawQuery(" SELECT  * FROM " + GeneralClaimModel.GC_Table + " WHERE " + GeneralClaimModel.Gc_IncidentID + " ="+id , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_GeneralClaim);
        return cursor;
    }
    public Cursor GeneralClaimLUpdate(int id){
        Cursor cursor = db.rawQuery(" SELECT  * FROM " + GeneralClaimModel.GC_Table + " WHERE " + GeneralClaimModel.GC_ID  + " ="+id , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_GeneralClaim);
        return cursor;
    }
    public Cursor GeneralClaimView(int id){
        Cursor cursor = db.rawQuery(" SELECT  * FROM " + GeneralClaimModel.GC_Table + " WHERE " + GeneralClaimModel.GC_ID  + " ="+id , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_GeneralClaim);
        return cursor;
    }
    public int IsClaimExist() {
        String countQuery = "SELECT  * FROM " + GeneralClaimModel.GC_Table  + " where " + GeneralClaimModel.GC_issent  + " = " + 1 + " and " + GeneralClaimModel.GC_isexist  + " =" + 0 + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


   public List<GeneralClaimModel>GeneralClaimData(){
        String query=" Select  " + GeneralClaimModel.Gc_IncidentID + " ,"
        + GeneralClaimModel.GC_cb + " ,"
        + GeneralClaimModel.GC_ca + " ,"
        + GeneralClaimModel.GC_FromDate + " ,"
        + GeneralClaimModel.GC_ToDate + " ,"
        + GeneralClaimModel.GC_ID + " ,"
        + GeneralClaimModel.GC_FromLoc + " ,"
        + GeneralClaimModel.Gc_ToLoc + " ,"
        + GeneralClaimModel.GC_claimcost + " ,"
         + GeneralClaimModel.GC_ServerPath + " ,"
        + GeneralClaimModel.GC_Total + " ,"
        + GeneralClaimModel.GC_ClaimID  + "  FROM " + GeneralClaimModel.GC_Table +
                " WHERE " + GeneralClaimModel.GC_issent  + " =" + 1 + " and " + GeneralClaimModel.GC_isexist  + " =" + 0 + "";

       Cursor cursor = db.rawQuery(query, null);
       List<GeneralClaimModel> claimobj = new ArrayList<>();
       if (cursor != null && cursor.getCount() > 0) {
           if (cursor.moveToFirst()) {
               do {
                   GeneralClaimModel generalClaimModel=new GeneralClaimModel();
                    List<ClaimFieldModel> claimcosts= Utility.GenericList(ClaimFieldModel.class,cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_claimcost)));
                    generalClaimModel.setClaimCost(claimcosts);
                   generalClaimModel.setID(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_ID)));
                   List<GeneralClaimDocument> list=Utility.GenericList(GeneralClaimDocument.class,cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_ServerPath)));
                   generalClaimModel.setDocs(list);
                   generalClaimModel.setFromLoc(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_FromLoc)));
                   generalClaimModel.setToLoc(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.Gc_ToLoc)));
                   generalClaimModel.setIncidentID(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.Gc_IncidentID)));
                   generalClaimModel.setCreatedBy(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_cb)));
                   generalClaimModel.setCreatedAt(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_ca)));
                   generalClaimModel.setFromDate(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_FromDate)));
                   generalClaimModel.setToDate(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_ToDate)));
                    generalClaimModel.setClaimID(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_ClaimID)));
                   generalClaimModel.setTotal(cursor.getDouble(cursor.getColumnIndex(GeneralClaimModel.GC_Total)));
                   generalClaimModel.setLastModifiedBy(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_cb)));
                   generalClaimModel.setLastModifiedDateTime((cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_ca))));
                   generalClaimModel.setUserID(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_cb)));
                   claimobj.add(generalClaimModel);

               }
               while (cursor.moveToNext());

           }
       }
       if (cursor != null || !cursor.isClosed()) {
           cursor.close();
       }
       cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_GeneralClaim);
   return claimobj;
   }
    public int IsClaimFileExist() {
        String countQuery = "SELECT  * FROM " +GeneralClaimModel.GC_Table  + " where " + GeneralClaimModel.GC_isexist   + " = " + 1 + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public List<GeneralClaimModel> FileList() {

        String query = " SELECT " + GeneralClaimModel.GC_ID   + " ,"
                + GeneralClaimModel.Gc_IncidentID  + " ,"
                + GeneralClaimModel.GC_cb  + " ,"
                + GeneralClaimModel.GC_ServerPath  + " from "
                + GeneralClaimModel.GC_Table  + " where " + GeneralClaimModel.GC_isexist   + " =" + 1;

        Cursor cursor = db.rawQuery(query, null);
        List<GeneralClaimModel> visitlist = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    GeneralClaimModel generalClaimModel = new GeneralClaimModel();
                    generalClaimModel.setDocs(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_ServerPath)));
                    generalClaimModel.setUserID(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_cb)));
                    generalClaimModel.setIncidentID(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.Gc_IncidentID)));
                    generalClaimModel.setID(cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_ID)));
                    visitlist.add(generalClaimModel);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return visitlist;
    }
    public void UpdateDocument(GeneralClaimModel generalClaimModel) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(generalClaimModel.getID())};
        contentValues.put(GeneralClaimModel.GC_ServerPath, generalClaimModel.getDocs());
        contentValues.put(GeneralClaimModel.GC_isexist, generalClaimModel.getIsFileExist());
        mcontext.getContentResolver().notifyChange(DB_SF_GeneralClaim, null);
        db.update(GeneralClaimModel.GC_Table, contentValues, GeneralClaimModel.GC_ID + "= ?", args);
    }
    public void UpdateClaim(GeneralClaimModel generalClaimModel) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(generalClaimModel.getID())};
        contentValues.put(GeneralClaimModel.GC_Code, generalClaimModel.getCode());
        contentValues.put(GeneralClaimModel.GC_issent, generalClaimModel.getIsSent());
        contentValues.put(GeneralClaimModel.GC_isexist, generalClaimModel.getIsFileExist());
        contentValues.put(GeneralClaimModel.GC_ClaimID,generalClaimModel.getClaimID());
        mcontext.getContentResolver().notifyChange(DB_SF_GeneralClaim, null);
        db.update(GeneralClaimModel.GC_Table, contentValues, GeneralClaimModel.GC_ID + "= ?", args);
    }
    public void ClaimInsertorUpdate(GeneralClaimModel  modlobj) {

        String query = " SELECT  * FROM " + GeneralClaimModel.GC_Table   + " WHERE " + GeneralClaimModel.GC_ClaimID  + "  =" + modlobj.getClaimID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateGeneralClaim(modlobj);
        } else {
            InsertGeneralClaim(modlobj);
        }
        c.close();
    } public String  getMaxDate() {
        String countQuery = "SELECT   MAX ("+ GeneralClaimModel.GC_lma   +")  FROM " + GeneralClaimModel.GC_Table  + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        String date=null;
        if(cursor.moveToFirst()) {
            date = cursor.getString(0);
        } cursor.close();
        return date;
    }
    public void DeleteGC(int id){
        db.delete(GeneralClaimModel.GC_Table,GeneralClaimModel.GC_ID  + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(DB_SF_GeneralClaim, null);
    }
    public void Delete(int id){
        db.delete(GeneralClaimModel.GC_Table,GeneralClaimModel.Gc_IncidentID  + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(DB_SF_GeneralClaim, null);
    }


}