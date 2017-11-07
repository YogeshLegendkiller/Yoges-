package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

/**
 * Created by 4264 on 09-02-2017.
 */

public class PendingClassificationDAO  extends SFSingelton {
    private Context mcontext;
    private static String DATABASE_NAME = "SFdatabasename";
    public static final Uri pendinclassification_URI = Uri
            .parse("sqlite://" + "sf_visitstatus" + "/" + DATABASE_NAME);

    public PendingClassificationDAO(Context context) {
        super(context);
        this.mcontext = context;
    }
    private void Insertpendingclassification(PendingClassificationModel pendingobj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PendingClassificationModel.text, pendingobj.getPendingClassification());
        contentValues.put(PendingClassificationModel.webid, pendingobj.getPendingClassificationID());
        db.insert(PendingClassificationModel.table, null, contentValues);
    }
    public Cursor pendingclassificationlist(){
        String visitquery= " Select * FROM  " + PendingClassificationModel.table + " ";
        Cursor cursor = db.rawQuery(visitquery ,null);
        cursor.setNotificationUri(mcontext.getContentResolver(), pendinclassification_URI);
        return cursor;
    }
    public Cursor pendingclassificationlist_search(String txt){
        String visitquery= " Select * FROM  " + PendingClassificationModel.table +  " where " +PendingClassificationModel.text + " LIKE " + "'%" + txt + "%'";;;
        Cursor cursor = db.rawQuery(visitquery ,null);
        cursor.setNotificationUri(mcontext.getContentResolver(), pendinclassification_URI);
        return cursor;
    }
    public void PendingClassificationInsertorUpdate(PendingClassificationModel modlobj) {
        String query = " SELECT  * FROM " + PendingClassificationModel.table  + " WHERE " + PendingClassificationModel.webid  + "  =" + modlobj.getPendingClassificationID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            updatependingclassification(modlobj);
        } else {
            Insertpendingclassification(modlobj);
        }
        c.setNotificationUri(mcontext.getContentResolver(), pendinclassification_URI);
     //   modlobj=null;
        c.close();

    }

    private void updatependingclassification(PendingClassificationModel pendingobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(pendingobj.getPendingClassificationID())};
        contentValues.put(PendingClassificationModel.text, pendingobj.getPendingClassification());
        contentValues.put(PendingClassificationModel.webid, pendingobj.getPendingClassificationID());
        db.update(PendingClassificationModel.table, contentValues, PendingClassificationModel.webid + "= ?", args);
    }
}