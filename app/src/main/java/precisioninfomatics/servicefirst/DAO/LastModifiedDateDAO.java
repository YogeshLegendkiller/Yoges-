package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.File;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.FileModel;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.LastModifiedModel;

/**
 * Created by 4264 on 25/07/2017.
 */

public class LastModifiedDateDAO extends SFSingelton {

    public LastModifiedDateDAO(Context context) {
        super(context);
    }


    private void InsertLMD(LastModifiedModel lastModifiedModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LastModifiedModel.lmd_incid, lastModifiedModel.getIncidentID());
        contentValues.put(LastModifiedModel.lmd_lmd, lastModifiedModel.getLastModifiedDateTime());
        db.insert(LastModifiedModel.lmd_table, null, contentValues);
    }

    private void UpdateLMD(LastModifiedModel lastModifiedModel) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(lastModifiedModel.getIncidentID())};
        contentValues.put(LastModifiedModel.lmd_incid, lastModifiedModel.getIncidentID());
        contentValues.put(LastModifiedModel.lmd_lmd, lastModifiedModel.getLastModifiedDateTime());
        db.update(LastModifiedModel.lmd_table, contentValues, LastModifiedModel.lmd_incid + "= ?", args);
    }

    public void InsertOrUpdate(LastModifiedModel lastModifiedModel) {
        String query = " SELECT  * FROM " + LastModifiedModel.lmd_table + " WHERE " + LastModifiedModel.lmd_incid + "  =" + lastModifiedModel.getIncidentID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateLMD(lastModifiedModel);
        } else {
            InsertLMD(lastModifiedModel);
        }
        c.close();
    }

    public String getMaxDate() {
        String countQuery = "SELECT   MAX (" + LastModifiedModel.lmd_lmd + ")  FROM " + LastModifiedModel.lmd_table + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        String date = null;
        if (cursor.moveToFirst()) {
            date = cursor.getString(0);
        }
        cursor.close();
        return date;
    }
}


