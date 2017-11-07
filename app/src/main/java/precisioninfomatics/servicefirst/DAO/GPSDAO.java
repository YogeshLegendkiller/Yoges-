package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.GpsModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.Model.SpareModel;

/**
 * Created by 4264 on 21/03/2017.
 */

public class GPSDAO extends SFSingelton{

    private Context mContext;
    public static final Uri GPS_URI = Uri
            .parse("sqlite://" + "sf_gps" + "/" + "SFdatabasename");

    public GPSDAO(Context context) {
        super(context);
        this.mContext=context;
    }
    public void InsertGps(GpsModel gpsModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(GpsModel.LAT,gpsModel.getLatitude());
        contentValues.put(GpsModel.Long,gpsModel.getLongtitude());
        contentValues.put(GpsModel.flag,gpsModel.getFlag());
        db.insert(GpsModel.Table,null,contentValues);
        mContext.getContentResolver().notifyChange(GPS_URI, null);
    }
    public Cursor Gps(){
        String visitquery= " Select * FROM  " + GpsModel.Table + " ";
        Cursor cursor = db.rawQuery(visitquery ,null);
        cursor.setNotificationUri(mContext.getContentResolver(), GPS_URI);
        return cursor;
    }
    public void deleteGpsRecords() {
        db.delete(GpsModel.Table ,null,null);
        mContext.getContentResolver().notifyChange(GPS_URI, null);
    }

    public List<LatLng>listobj(){
        String visitquery= " Select * FROM  " + GpsModel.Table + " ";
        Cursor cursor = db.rawQuery(visitquery ,null);
        List<LatLng>listobj=new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    LatLng latLng=new LatLng(cursor.getDouble(cursor.getColumnIndex(GpsModel.LAT)),cursor.getDouble(cursor.getColumnIndex(GpsModel.Long)));
                    listobj.add(latLng);
                } while (cursor.moveToNext());
            }
        }
        if(cursor!=null||!cursor.isClosed()){
            cursor.close();
        }
        return listobj;
    }

    public List<GpsModel>listobj(Cursor cursor){
         List<GpsModel>listobj=new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    GpsModel gpsModel=new GpsModel();
                    gpsModel.setLatitude(cursor.getDouble(cursor.getColumnIndex(GpsModel.LAT)));
                    gpsModel.setLongtitude(cursor.getDouble(cursor.getColumnIndex(GpsModel.Long)));
                    listobj.add(gpsModel);
                } while (cursor.moveToNext());
            }
        }
        if(cursor!=null||!cursor.isClosed()){
            cursor.close();
        }
        return listobj;
    }
    public int getCount() {
        String countQuery = "SELECT  * FROM " + GpsModel.Table;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public boolean gpsCheck() {
        String sql = "select " + GpsModel.flag + " from  " + GpsModel.Table + " where " + GpsModel.flag + " =" + 1;
        Cursor c = db.rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } finally {
            c.close();
        }
    }
}
