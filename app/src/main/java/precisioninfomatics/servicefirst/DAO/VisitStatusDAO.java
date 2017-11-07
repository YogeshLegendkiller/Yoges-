package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Fragments.VisitStatus;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

/**
 * Created by 4264 on 28-12-2016.
 */
public class VisitStatusDAO extends SFSingelton {
    private Context mcontext;
    private static String DATABASE_NAME = "SFdatabasename";
    public static final Uri VisitDAO_URI = Uri
            .parse("sqlite://" + "sf_visitstatus" + "/" + DATABASE_NAME);

    public VisitStatusDAO(Context context) {
        super(context);
        this.mcontext = context;
    }
    private void InsertVisit_Status(VisitStatusModel visitStatusModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VisitStatusModel.status, visitStatusModel.getStatus());
        contentValues.put(VisitStatusModel.statusid, visitStatusModel.getStatusID());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.insert(VisitStatusModel.table, null, contentValues);
    }
    public Cursor visitStatusList(){
        String visitquery= " Select * FROM  " + VisitStatusModel.table + " ";
        Cursor cursor = db.rawQuery(visitquery ,null);
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
        return cursor;
    }
    public Cursor FilterStatus(String txt){
        String visitquery= " Select * FROM  " + VisitStatusModel.table + " where " + VisitStatusModel.status + " LIKE " + "'%" + txt + "%'";
        Cursor cursor = db.rawQuery(visitquery ,null);
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
        return cursor;
    }
    public List<VisitStatusModel> list(Cursor cursor) {
        List<VisitStatusModel> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                  VisitStatusModel visitStatusModel=new VisitStatusModel();
                    visitStatusModel.setStatus(cursor.getString(cursor.getColumnIndex(VisitStatusModel.status)));
                    visitStatusModel.setStatusID(cursor.getInt(cursor.getColumnIndex(VisitStatusModel.statusid)));
                    list.add(visitStatusModel);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return list;
    }
    public void VisitStatusInsertorUpdate(VisitStatusModel modlobj) {
        String query = " SELECT  * FROM " + VisitStatusModel.table  + " WHERE " + VisitStatusModel.statusid  + "  =" + modlobj.getStatusID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            VisitStatusUpdate(modlobj);
        } else {
            InsertVisit_Status(modlobj);
        }
        c.close();
    }

    private void VisitStatusUpdate(VisitStatusModel modlobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(modlobj.getStatusID())};
         contentValues.put(VisitStatusModel.status,modlobj.getStatus());
        contentValues.put(VisitStatusModel.statusid,modlobj.getStatusID());
        db.update(VisitStatusModel.table, contentValues, VisitStatusModel.statusid + "= ?", args);
    }
}