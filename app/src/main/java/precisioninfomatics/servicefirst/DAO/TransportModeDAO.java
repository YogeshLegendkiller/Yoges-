package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.TransportModeModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

/**
 * Created by 4264 on 17-02-2017.
 */

public class TransportModeDAO extends SFSingelton {
    private Context mcontext;
    public TransportModeDAO(Context context) {
        super(context);
        this.mcontext = context;

    }

    private static String DATABASE_NAME = "SFdatabasename";
    public static final Uri TransportDAO_URI = Uri
            .parse("sqlite://" + "sf_visitstatus" + "/" + DATABASE_NAME);


    private void Insert_TransportMode(TransportModeModel transportModeModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TransportModeModel.name, transportModeModel.getName());
        contentValues.put(TransportModeModel.webid, transportModeModel.getID());
        mcontext.getContentResolver().notifyChange(TransportDAO_URI, null);
        db.insert(TransportModeModel.table, null, contentValues);
    }
    public Cursor transportlist(){
        String visitquery= " Select * FROM  " + TransportModeModel.table + " order by " + TransportModeModel.webid +  " DESC LIMIT 3 ";
        Cursor cursor = db.rawQuery(visitquery ,null);
        cursor.setNotificationUri(mcontext.getContentResolver(), TransportDAO_URI);
        return cursor;
    }
    public Cursor VehicleRegistration(){
        String visitquery= " Select * FROM  " + TransportModeModel.table + " LIMIT 2";
        Cursor cursor = db.rawQuery(visitquery ,null);
        cursor.setNotificationUri(mcontext.getContentResolver(), TransportDAO_URI);
        return cursor;
    }
  /*  public List<VisitStatusModel> list(Cursor cursor) {
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
    }*/
    public void TransportModeInsertorUpdate(TransportModeModel  modlobj) {
        String query = " SELECT  * FROM " + TransportModeModel.table  + " WHERE " + TransportModeModel.webid  + "  =" + modlobj.getID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            TransportModeUpdate(modlobj);
        } else {
            Insert_TransportMode(modlobj);
        }
        c.close();
    }

    private void TransportModeUpdate(TransportModeModel modlobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(modlobj.getID())};
        contentValues.put(TransportModeModel.name,modlobj.getName());
        contentValues.put(TransportModeModel.webid,modlobj.getID());
        db.update(TransportModeModel.table, contentValues, TransportModeModel.webid + "= ?", args);
    }
}
