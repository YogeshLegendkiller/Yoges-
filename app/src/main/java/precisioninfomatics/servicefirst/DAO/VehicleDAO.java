package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.LoginModel;
import precisioninfomatics.servicefirst.Model.PartModel;
import precisioninfomatics.servicefirst.Model.VehicleModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

/**
 * Created by 4264 on 18-01-2017.
 */

public class VehicleDAO extends SFSingelton {

    public VehicleDAO(Context context) {
        super(context);

    }
    public void InsertVehicleDetails(VehicleModel vehicleModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(VehicleModel.regno,vehicleModel.getRegNo());
        contentValues.put(VehicleModel.licno,vehicleModel.getLicenseNo());
        contentValues.put(VehicleModel.lictype,vehicleModel.getLicenseType());
        contentValues.put(VehicleModel.licexpdate,vehicleModel.getExpiryDate());
        contentValues.put(VehicleModel.vehicletype,vehicleModel.getVehicleType());
        contentValues.put(VehicleModel.ins_expdate,vehicleModel.getInsuranceExpiryDate());
        contentValues.put(VehicleModel.userid,vehicleModel.getUserID());
        contentValues.put(VehicleModel.lictypeID,vehicleModel.getLicenseTypeID());
        db.insert(VehicleModel.table,null,contentValues);
    }
   public void UpdateVehicleDetails(VehicleModel vehicleModel){
       ContentValues contentValues=new ContentValues();
       String[] args = {String.valueOf(1)};
       contentValues.put(VehicleModel.regno,vehicleModel.getRegNo());
       contentValues.put(VehicleModel.vehicletype,vehicleModel.getVehicleType());
       contentValues.put(VehicleModel.licno,vehicleModel.getLicenseNo());
       contentValues.put(VehicleModel.lictype,vehicleModel.getLicenseType());
       contentValues.put(VehicleModel.licexpdate,vehicleModel.getExpiryDate());
       contentValues.put(VehicleModel.ins_expdate,vehicleModel.getInsuranceExpiryDate());
       contentValues.put(VehicleModel.userid,vehicleModel.getUserID());
       contentValues.put(VehicleModel.lictypeID,vehicleModel.getLicenseTypeID());
       db.update(VehicleModel.table , contentValues, VehicleModel.id  + "= ?", args);
   }
    public List<VehicleModel> list(){
        String query=" Select * from "+ VehicleModel.table ;
        Cursor cursor = db.rawQuery(query ,null);
        List<VehicleModel> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                  VehicleModel vehicleModel=new VehicleModel();
                    vehicleModel.setVehicleType(cursor.getInt(cursor.getColumnIndex(VehicleModel.vehicletype)));
                  vehicleModel.setExpiryDate(cursor.getString(cursor.getColumnIndex(VehicleModel.licexpdate)));
                  vehicleModel.setInsuranceExpiryDate(cursor.getString(cursor.getColumnIndex(VehicleModel.ins_expdate)));
                  vehicleModel.setLicenseType(cursor.getString(cursor.getColumnIndex(VehicleModel.lictype)));
                  vehicleModel.setRegNo(cursor.getString(cursor.getColumnIndex(VehicleModel.regno)));
                  vehicleModel.setLicenseNo(cursor.getString(cursor.getColumnIndex(VehicleModel.licno)));
                  list.add(vehicleModel);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return list;
    }
    public int verifybike() {
        String countQuery = "SELECT " + VehicleModel.id  + " from " + VehicleModel.table   + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public Cursor vehicleedit(){
        String vedhicleedit= " Select  * FROM "  + VehicleModel.table +"";
        Cursor cursor = db.rawQuery(vedhicleedit ,null);
        return cursor;
    }
    public VehicleModel getDetails(){
        String vehicleDetails= " Select  * FROM "  + VehicleModel.table +"";
        Cursor cursor = db.rawQuery(vehicleDetails, null);
        cursor.moveToFirst();
        VehicleModel vehicleModel=new VehicleModel();
        vehicleModel.setVehicleType(cursor.getInt(cursor.getColumnIndex(VehicleModel.vehicletype)));
        vehicleModel.setLicenseNo(cursor.getString(cursor.getColumnIndex(VehicleModel.licno)));
        vehicleModel.setRegNo(cursor.getString(cursor.getColumnIndex(VehicleModel.regno)));
        vehicleModel.setLicenseType(cursor.getString(cursor.getColumnIndex(VehicleModel.lictype)));
        vehicleModel.setInsuranceExpiryDate(cursor.getString(cursor.getColumnIndex(VehicleModel.ins_expdate)));
        vehicleModel.setExpiryDate(cursor.getString(cursor.getColumnIndex((VehicleModel.licexpdate))));
        vehicleModel.setUserID(cursor.getInt(cursor.getColumnIndex(VehicleModel.userid)));
        vehicleModel.setLicenseTypeID(cursor.getInt(cursor.getColumnIndex(VehicleModel.lictypeID)));
        if(!cursor.isClosed()){
            cursor.close();
        }
        return vehicleModel;

    }
}
