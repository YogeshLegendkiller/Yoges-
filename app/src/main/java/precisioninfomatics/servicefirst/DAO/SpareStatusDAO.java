package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.SpareStatusModel;

/**
 * Created by 4264 on 18/03/2017.
 */

public class SpareStatusDAO extends SFSingelton {
    public static final Uri DB_SF_SpareStatus = Uri
            .parse("sqlite://" + "sf_spareStatus" + "/" + "db");
    private Context mcontext;

    public SpareStatusDAO(Context context) {
        super(context);
        this.mcontext=context;
    }

    private void InsertSpareStatus(SpareStatusModel spareStatusModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(SpareStatusModel.statusid,spareStatusModel.getStatusID());
        contentValues.put(SpareStatusModel.statusname,spareStatusModel.getStatus());
        contentValues.put(SpareStatusModel.sequence,spareStatusModel.getSequence());
        db.insert(SpareStatusModel.table,null,contentValues);
        mcontext.getContentResolver().notifyChange(DB_SF_SpareStatus, null);
    }
    private void UpdateSpareStatus(SpareStatusModel spareStatusModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(spareStatusModel.getStatusID())};
        contentValues.put(SpareStatusModel.statusid,spareStatusModel.getStatusID());
        contentValues.put(SpareStatusModel.statusname,spareStatusModel.getStatus());
        contentValues.put(SpareStatusModel.sequence,spareStatusModel.getSequence());
        db.update(SpareStatusModel.table,contentValues, SpareStatusModel.statusid + "= ?", id);
        mcontext.getContentResolver().notifyChange(DB_SF_SpareStatus, null);
    }
    public void SpareInsertorUpdate(SpareStatusModel modlobj) {
        String query = " SELECT  * FROM " +  SpareStatusModel.table  + " WHERE " +  SpareStatusModel.statusid    + "  =" + modlobj.getStatusID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateSpareStatus(modlobj);
        } else {
            InsertSpareStatus(modlobj);
        }
        c.close();
    }
    public Cursor SpareStatus(int id) {
        Cursor cursor = db.rawQuery("select * from " + SpareStatusModel.table  +" WHERE " +  SpareStatusModel.sequence     + "  =" + id + "" , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_SpareStatus);
        return cursor;
    }
}
