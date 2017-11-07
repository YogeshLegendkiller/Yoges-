package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.Model.ResourceModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 15-02-2017.
 */

public class ResourceDAO extends SFSingelton {
    private Context mcontext;
    public static final Uri DB_SF_Resource = Uri
            .parse("sqlite://" + "sf_resource" + "/" + "db");


    public ResourceDAO(Context context) {
        super(context);
        this.mcontext=context;
    }
    public void InsertResource(ResourceModel resourceModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ResourceModel.resourceid,resourceModel.getResourceID());
        contentValues.put(ResourceModel.resourcename,resourceModel.getEmployeeName());
        contentValues.put(ResourceModel.telephone,resourceModel.getTelephone());
        contentValues.put(ResourceModel.email,resourceModel.getEmail());
        contentValues.put(ResourceModel.statusid,resourceModel.getStatus());
        contentValues.put(ResourceModel.resourcestatus,resourceModel.getResourceStatus());
        contentValues.put(ResourceModel.status,resourceModel.getStatusText());
        contentValues.put(ResourceModel.resourcealocid,resourceModel.getResourceAllocationID());
        contentValues.put(ResourceModel.instruction,resourceModel.getInstruction());
        contentValues.put(ResourceModel.assignedate,resourceModel.getAssignedDate());
        contentValues.put(ResourceModel.incid,resourceModel.getIncidentID());
        contentValues.put(ResourceModel.photourl,resourceModel.getPhotourl());
        contentValues.put(ResourceModel.localpath,resourceModel.getLocalPath());
        contentValues.put(ResourceModel.designation,resourceModel.getDesignation());
        mcontext.getContentResolver().notifyChange(DB_SF_Resource, null);
        db.insert(ResourceModel.table,null,contentValues);
    }
    public void UpdateResource(ResourceModel resourceModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ResourceModel.resourceid,resourceModel.getResourceID());
        contentValues.put(ResourceModel.resourcename,resourceModel.getEmployeeName());
        contentValues.put(ResourceModel.telephone,resourceModel.getTelephone());
        contentValues.put(ResourceModel.email,resourceModel.getEmail());
        contentValues.put(ResourceModel.statusid,resourceModel.getStatus());
        contentValues.put(ResourceModel.resourcestatus,resourceModel.getResourceStatus());
        contentValues.put(ResourceModel.status,resourceModel.getStatusText());
        contentValues.put(ResourceModel.resourcealocid,resourceModel.getResourceAllocationID());
        contentValues.put(ResourceModel.instruction,resourceModel.getInstruction());
        contentValues.put(ResourceModel.assignedate,resourceModel.getAssignedDate());
        contentValues.put(ResourceModel.incid,resourceModel.getIncidentID());
        contentValues.put(ResourceModel.photourl,resourceModel.getPhotourl());
        contentValues.put(ResourceModel.localpath,resourceModel.getLocalPath());
        contentValues.put(ResourceModel.designation,resourceModel.getDesignation());
        mcontext.getContentResolver().notifyChange(DB_SF_Resource, null);
        db.update(ResourceModel.table, contentValues, ResourceModel.resourceid + "= ?", new String[]{String.valueOf(resourceModel.getResourceID())});

    }
    public Cursor Resource (int id) {
        Cursor cursor = db.rawQuery("select * from " + ResourceModel.table +  " where " + ResourceModel.incid + " ="+ id , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_Resource);
        return cursor;
    }
    public void DeleteResource(int id){
        db.delete(ResourceModel.table ,ResourceModel.incid + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(DB_SF_Resource, null);
    }
   /* public void FileReplace(int id) {

        String query = " SELECT "
                +  ResourceModel.localpath  + " from "
                +  ResourceModel.table  + " where " + ResourceModel.resourceid  + " =" + id;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    File filedelete=new File(cursor.getString(cursor.getColumnIndex(ResourceModel.localpath)));
                    boolean result= Utility.Filedelete(filedelete);
                    Log.d("result",String.valueOf(result));

                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }

    }

    public void FileReplaceByIncident(int id) {

        String query = " SELECT "
                +  ResourceModel.localpath  + " from "
                +  ResourceModel.table  + " where " + ResourceModel.incid  + " =" + id;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    File filedelete=new File(cursor.getString(cursor.getColumnIndex(ResourceModel.localpath)));
                    boolean result= Utility.Filedelete(filedelete);
                    Log.d("result",String.valueOf(result));

                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }

    }
*/
   /* public boolean ResourceCheck(int id){
        String sql= "select " + ResourceModel.resourceid  + " from  " + ResourceModel.table  + " where "+ ResourceModel.resourceid  + " =" +id ;
        Cursor c=db.rawQuery(sql,null);
        try {         if (c.moveToFirst()) {
            return true;
        } else {
            return false;
        }
        }
        finally{
            c.close();
        }
    }*/
    public void InsertOrUpdateResourceView(ResourceModel resourceModel) {
        String query = " SELECT  * FROM " + ResourceModel.table  + " WHERE " + ResourceModel.resourceid + "  =" + resourceModel.getResourceID() + "";
        Cursor c = db.rawQuery(query, null);
        Log.d("insideinsert","insideinsert");
        if (c.moveToFirst()) {
            UpdateResource(resourceModel);
        } else {
            InsertResource(resourceModel);
        }
        mcontext.getContentResolver().notifyChange(DB_SF_Resource, null);
        c.close();
    }
}
