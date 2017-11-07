package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.CustomerBranchModel;
import precisioninfomatics.servicefirst.Model.SpareStatusModel;

/**
 * Created by 4264 on 21/06/2017.
 */

public class CustomerBranchDAO extends SFSingelton {

    public static final Uri DB_SF_CustomerBranch = Uri
            .parse("sqlite://" + "sf_spareStatus" + "/" + "db");
    private Context mcontext;
    public CustomerBranchDAO(Context context) {
        super(context);
        this.mcontext=context;
    }
    private void InsertCustomerBranch(CustomerBranchModel customerBranchModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(CustomerBranchModel.CustomerBranchid,customerBranchModel.getCustomerBranchID());
        contentValues.put(CustomerBranchModel.CustomerBranchname,customerBranchModel.getCustomerBranchName());
        contentValues.put(CustomerBranchModel.RegID,customerBranchModel.getIncidentID());
        db.insert(CustomerBranchModel.CustomerBranchTable,null,contentValues);
        mcontext.getContentResolver().notifyChange(DB_SF_CustomerBranch, null);
    }
    private void UpdateCustomerBranch(CustomerBranchModel customerBranchModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(customerBranchModel.getCustomerBranchID())};
        contentValues.put(CustomerBranchModel.RegID,customerBranchModel.getIncidentID());
        contentValues.put(CustomerBranchModel.CustomerBranchid,customerBranchModel.getCustomerBranchID());
        contentValues.put(CustomerBranchModel.CustomerBranchname,customerBranchModel.getCustomerBranchName());
        db.update(CustomerBranchModel.CustomerBranchTable,contentValues, CustomerBranchModel.CustomerBranchid + "= ?", id);
        mcontext.getContentResolver().notifyChange(DB_SF_CustomerBranch, null);
    }
    public void CustomerBranchInsertorUpdate(CustomerBranchModel customerBranchModel) {
        String query = " SELECT  * FROM " +  CustomerBranchModel.CustomerBranchTable + " WHERE " +  CustomerBranchModel.CustomerBranchid   + "  =" + customerBranchModel.getCustomerBranchID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateCustomerBranch(customerBranchModel);
        } else {
            InsertCustomerBranch(customerBranchModel);
        }
        c.close();
    }
    public Cursor CustomerBranchList(int id) {
        Cursor cursor = db.rawQuery(" SELECT * FROM  " + CustomerBranchModel.CustomerBranchTable  +" WHERE " + CustomerBranchModel.RegID    + "  = " + id + "" , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), DB_SF_CustomerBranch);
        return cursor;
    }
}





