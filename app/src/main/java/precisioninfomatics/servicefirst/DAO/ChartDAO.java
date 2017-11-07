package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.ChartModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 23/05/2017.
 */

public class ChartDAO extends SFSingelton {
    private Context mcontext;
    public static final Uri Chart_URI = Uri
            .parse("sqlite://" + "sf_visitstatus" + "/" + "sf_db");


    public ChartDAO(Context context) {
        super(context);
        this.mcontext=context;
    }

    public void PieInsert (ChartModel chartobj){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ChartModel.Label,chartobj.getLabel());
        contentValues.put(ChartModel.Data,chartobj.getData());
        db.insert(ChartModel.Pie_Table,null,contentValues);
        mcontext.getContentResolver().notifyChange(Chart_URI, null);
    }
    public void BarInsert (ChartModel chartobj){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ChartModel.Label,chartobj.getLabel());
        contentValues.put(ChartModel.Data,chartobj.getData());
        db.insert(ChartModel.Bar_Table,null,contentValues);
        mcontext.getContentResolver().notifyChange(Chart_URI, null);
    }
    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + ChartModel.Pie_Table;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public void PieDelete(){
        db.execSQL("delete from "+ ChartModel.Pie_Table);
        mcontext.getContentResolver().notifyChange(Chart_URI, null);
    }
    public void SummaryInsert (ChartModel chartobj){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ChartModel.Label,chartobj.getLabel());
        contentValues.put(ChartModel.Data,chartobj.getData());
        mcontext.getContentResolver().notifyChange(Chart_URI, null);
        db.insert(ChartModel.Summary_Table,null,contentValues);
     }
    public void SummaryDelete(){
        db.execSQL("delete from "+ ChartModel.Summary_Table);
        mcontext.getContentResolver().notifyChange(Chart_URI, null);
    }
    public void BarDelete(){
        db.execSQL("delete from "+ ChartModel.Bar_Table);
        mcontext.getContentResolver().notifyChange(Chart_URI, null);
    }
    public Cursor PieData() {
        Cursor cursor = db.rawQuery("select * from " + ChartModel.Pie_Table  , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), Chart_URI);
        return cursor;
    }
    public Cursor BarData() {
        Cursor cursor = db.rawQuery("select * from " + ChartModel.Bar_Table  , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), Chart_URI);
        return cursor;
    }
    public Cursor SummaryData() {
        Cursor cursor = db.rawQuery("select * from " + ChartModel.Summary_Table  , null);
        cursor.setNotificationUri(mcontext.getContentResolver(), Chart_URI);
        return cursor;
    }
    public List<ChartModel>CharSummary(Cursor cursor) {
        List<ChartModel> chartlist = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                   ChartModel chartModel=new ChartModel();
                   chartModel.setLabel(cursor.getString(cursor.getColumnIndex(ChartModel.Label)));
                    chartModel.setData(cursor.getInt(cursor.getColumnIndex(ChartModel.Data)));
                   chartlist.add(chartModel);
                } while (cursor.moveToNext());
            }
        }

        return chartlist;
    }
}