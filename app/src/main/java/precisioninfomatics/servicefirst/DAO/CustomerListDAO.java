package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.CustomerListModel;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.Model.TransportModeModel;

/**
 * Created by 4264 on 16/06/2017.
 */

public class CustomerListDAO  extends SFSingelton {
    private Context mContext;
    public static final Uri customerlisturi = Uri
            .parse("sqlite://" + "sf_customerlist" + "/" + "SFdatabasename");

    public CustomerListDAO(Context context) {
        super(context);
        this.mContext=context;
    }
    public void InsertCustomerLIst(CustomerListModel customerListModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(CustomerListModel.incidentid,customerListModel.getIncidentID());
        contentValues.put(CustomerListModel.flag,customerListModel.getFlag());
        contentValues.put(CustomerListModel.customerbranchid,customerListModel.getCustomerBranchID());
        contentValues.put(CustomerListModel.customerbranchname,customerListModel.getCustomerBranchName());
        db.insert(CustomerListModel.table,null,contentValues);
        mContext.getContentResolver().notifyChange(customerlisturi, null);
    }
    private void UpdateCustomerList(CustomerListModel customerListModel){
        ContentValues contentValues=new ContentValues();
        String[] id = {String.valueOf(customerListModel.getCustomerBranchID())};
        contentValues.put(CustomerListModel.flag,customerListModel.getFlag());
        contentValues.put(CustomerListModel.incidentid,customerListModel.getIncidentID());
        contentValues.put(CustomerListModel.customerbranchid,customerListModel.getCustomerBranchID());
        contentValues.put(CustomerListModel.customerbranchname,customerListModel.getCustomerBranchName());
        db.update(CustomerListModel.table,contentValues, CustomerListModel.customerbranchid  + "= ?", id);
        mContext.getContentResolver().notifyChange(customerlisturi, null);
    }
    public void InsertOrUpdate(CustomerListModel customerListModel){
        String query = " SELECT  * FROM " + CustomerListModel.table  + " WHERE " + CustomerListModel.customerbranchid  + "  =" + customerListModel.getCustomerBranchID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateCustomerList(customerListModel);
        } else {
            InsertCustomerLIst(customerListModel);
        }
        c.close();
    }
    public List<CustomerListModel> listobj(int id){
        String query=" SELECT " + CustomerListModel.customerbranchid + " ," +CustomerListModel.customerbranchname   +" from "+ CustomerListModel.table  + " WHERE " + CustomerListModel.incidentid  + "  =" +id + "";
        Cursor c=db.rawQuery(query,null);
        List<CustomerListModel>listobj=new ArrayList<>();
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    CustomerListModel customerListModel=new CustomerListModel();
                    customerListModel.setFlag(c.getInt(c.getColumnIndex(CustomerListModel.flag)));
                    customerListModel.setCustomerBranchID(c.getInt(c.getColumnIndex(CustomerListModel.customerbranchid)));
                    customerListModel.setCustomerBranchName(c.getString(c.getColumnIndex(CustomerListModel.customerbranchname)));
                    listobj.add(customerListModel);
                }while (c.moveToNext());
            }
        }
        if(c!=null||!c.isClosed()){
            c.close();
        }
        return listobj;
    }
    public Cursor customerlist(int id,int flag){
        String query=" SELECT " + CustomerListModel.customerbranchname  + " ," +  CustomerListModel.customerbranchid  + " ," + CustomerListModel.flag   +" from "+ CustomerListModel.table  + "  WHERE  " + CustomerListModel.incidentid  + "  = " +id + " and " + CustomerListModel.flag +  "  = "  +flag;
        Cursor cursor = db.rawQuery(query ,null);
        cursor.setNotificationUri(mContext.getContentResolver(), customerlisturi);
        return cursor;
    }
}

