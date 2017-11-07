package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.File;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.CustomerBranchModel;
import precisioninfomatics.servicefirst.Model.FileModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.ResourceModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 21/06/2017.
 */

public class FileDAO extends SFSingelton{
  private Context mcontext;
    public FileDAO(Context context) {
        super(context);
        this.mcontext=context;
    }


    private void InsertFile(FileModel fileModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(FileModel.Filename,fileModel.getFileName());
        contentValues.put(FileModel.Filepath,fileModel.getFilePath());
        contentValues.put(FileModel.Filetype,fileModel.getFileType());
        contentValues.put(FileModel.Incidentid,fileModel.getIncidentID());
        db.insert(FileModel.FileTable,null,contentValues);
        mcontext.getContentResolver().notifyChange(IncidentDAO.DB_SF_Incident, null);
    }

    private void UpdateFile(FileModel fileModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FileModel.Filename,fileModel.getFileName());
        contentValues.put(FileModel.Filepath,fileModel.getFilePath());
        contentValues.put(FileModel.Filetype,fileModel.getFileType());
        contentValues.put(FileModel.Incidentid,fileModel.getIncidentID());
        db.update(FileModel.FileTable, contentValues,   FileModel.Incidentid + " = ? AND " + FileModel.Filetype+ " = ?", new String[]{String.valueOf(fileModel.getIncidentID()),String.valueOf(fileModel.getFileType())});
    }
    public Cursor FileList(int id) {
        Cursor cursor = db.rawQuery(" SELECT * FROM  " + FileModel.FileTable   +" WHERE " + FileModel.Incidentid     + "  = " + id + "" , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), IncidentDAO.DB_SF_Incident);
        return cursor;
    }
    public Integer FileCountCheck(int id) {
        String countQuery = "SELECT  * FROM " + FileModel.FileTable  + " where " + FileModel.Incidentid  + " = " + id + " ";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public void DeleteFile(int id,int incid) {
        String query = " SELECT "
                +   FileModel.Filepath + " from "
                +  FileModel.FileTable  + " where " +FileModel.Filetype  + " =" + id + " and " +FileModel.Incidentid  + " =" +incid ;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    File filedelete=new File(cursor.getString(cursor.getColumnIndex(FileModel.Filepath )));
                    Log.d("filepath",cursor.getString(cursor.getColumnIndex(FileModel.Filepath )));
                    boolean result= Utility.Filedelete(filedelete);
                    Log.d("result",String.valueOf(result));
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }

    }

    public void InsertOrUpdate(FileModel fileModel) {
        String query = " SELECT  * FROM " +  FileModel.FileTable  + " WHERE " +  FileModel.Incidentid  + "  =" + fileModel.getIncidentID() + " and "+  FileModel.Filetype  + "  =" + fileModel.getFileType();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateFile(fileModel);
        } else {
            InsertFile(fileModel);
        }
        mcontext.getContentResolver().notifyChange(IncidentDAO.DB_SF_Incident, null);
        c.close();
    }
}
