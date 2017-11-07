package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.SpareStatusModel;

/**
 * Created by 4264 on 24/05/2017.
 */

public class ClaimFieldDAO extends SFSingelton {

    public ClaimFieldDAO(Context context) {
        super(context);
    }

    private void InsertFieldValue(ClaimFieldModel claimFieldModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClaimFieldModel.fieldname, claimFieldModel.getName());
        contentValues.put(ClaimFieldModel.fieldpairid, claimFieldModel.getPairID());
        db.insert(ClaimFieldModel.ClaimFieldTable, null, contentValues);
    }

    private void UpdateFieldValue(ClaimFieldModel claimFieldModel) {
        ContentValues contentValues = new ContentValues();
        String[] id = {String.valueOf(claimFieldModel.getPairID())};
        contentValues.put(ClaimFieldModel.fieldname, claimFieldModel.getName());
        contentValues.put(ClaimFieldModel.fieldpairid, claimFieldModel.getPairID());
        db.update(ClaimFieldModel.ClaimFieldTable, contentValues, ClaimFieldModel.fieldpairid + "= ?", id);
    }

    public void FieldInsertOrUpdate(ClaimFieldModel claimFieldModel) {
        String query = " SELECT  * FROM " + ClaimFieldModel.ClaimFieldTable + " WHERE " + ClaimFieldModel.fieldpairid + "  =" + claimFieldModel.getPairID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateFieldValue(claimFieldModel);
        } else {
            InsertFieldValue(claimFieldModel);
        }
        c.close();
    }

    public List<ClaimFieldModel> fieldlist() {
        Cursor cursor = db.rawQuery("select * from " + ClaimFieldModel.ClaimFieldTable , null);
        List<ClaimFieldModel> listobj = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    ClaimFieldModel claimFieldModel=new ClaimFieldModel();
                    claimFieldModel.setName(cursor.getString(cursor.getColumnIndex(ClaimFieldModel.fieldname)));
                    claimFieldModel.setPairID(cursor.getInt(cursor.getColumnIndex(ClaimFieldModel.fieldpairid)));
                    listobj.add(claimFieldModel);
                }while (cursor.moveToNext());
            }
        }
        if(cursor!=null||!cursor.isClosed()){
            cursor.close();
        }
        return listobj;

    }
}