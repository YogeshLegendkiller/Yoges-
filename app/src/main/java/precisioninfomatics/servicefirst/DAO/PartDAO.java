package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.PartModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 24-01-2017.
 */

public class PartDAO extends SFSingelton {
    private static String DATABASE_NAME = "SFdatabasename";
    public static final Uri PartDAO_URI = Uri
            .parse("sqlite://" + "sf_partRequest" + "/" + DATABASE_NAME);
   private Context mcontext;
    public PartDAO(Context context) {
        super(context);
        this.mcontext=context;
    }
    public void InsertPart(PartModel partModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(PartModel.webid,partModel.getPartID());
        contentValues.put(PartModel.partno,partModel.getPartno());
        contentValues.put(PartModel.specification,partModel.getPartSpecification());
        contentValues.put(PartModel.productno,partModel.getProductno());
        contentValues.put(PartModel.brand,partModel.getBrand());
        contentValues.put(PartModel.model,partModel.getModel());
     //   contentValues.put(PartModel.incid,partModel.getIncidentID());
        contentValues.put(PartModel.category,partModel.getCategory());
        mcontext.getContentResolver().notifyChange(PartDAO_URI, null);
        db.insert(PartModel.table,null,contentValues);
    }
    public void UpdatePart(PartModel partModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(PartModel.webid,partModel.getPartID());
        contentValues.put(PartModel.partno,partModel.getPartno());
        contentValues.put(PartModel.specification,partModel.getPartSpecification());
        contentValues.put(PartModel.productno,partModel.getProductno());
        contentValues.put(PartModel.brand,partModel.getBrand());
        contentValues.put(PartModel.model,partModel.getModel());
       // contentValues.put(PartModel.incid,partModel.getIncidentID());
        contentValues.put(PartModel.category,partModel.getCategory());
        mcontext.getContentResolver().notifyChange(PartDAO_URI, null);
        db.update(PartModel.table, contentValues, PartModel.webid + "= ?", new String[]{String.valueOf(partModel.getPartID())});

    }

    public void InsertOrUpdate(PartModel partModel){
        String query = " SELECT  * FROM " + PartModel.table  + " WHERE " + PartModel.webid  + "  =" + partModel.getPartID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdatePart(partModel);
        } else {
            InsertPart(partModel);
        }
        c.close();
    }
}
