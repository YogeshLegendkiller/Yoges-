package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.DBhelper.SFTables;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.LoginModel;

/**
 * Created by 4264 on 11-02-2017.
 */

public class LoginDAO extends SFSingelton {
    private Context mcontext;
    private static String DATABASE_NAME = "SFdatabasename";
    public static final Uri Login_URI = Uri
            .parse("sqlite://" + "sf_visitstatus" + "/" + DATABASE_NAME);

    public LoginDAO(Context context) {
        super(context);
        this.mcontext = context;
    }

    public void InsertLoginDetails(LoginModel loginobj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoginModel.empcode, loginobj.getEmployeeCode());
        contentValues.put(LoginModel.userid, loginobj.getUserID());
        contentValues.put(LoginModel.moduleID, loginobj.getModuleID());
        contentValues.put(LoginModel.modulename, loginobj.getModuleName());
        contentValues.put(LoginModel.roleid, loginobj.getRoleID());
        contentValues.put(LoginModel.empname,loginobj.getEmployeeName());
        contentValues.put(LoginModel.rolename, loginobj.getRoleName());
        contentValues.put(LoginModel.userloginid, loginobj.getUserLoginID());
        mcontext.getContentResolver().notifyChange(Login_URI, null);
        db.insert(LoginModel.table, null, contentValues);
    }

    public Integer getUserID() {
        int userid = 0;
        String query = " select * from " + LoginModel.table + " ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                userid = cursor.getInt(cursor.getColumnIndex(LoginModel.userid));
        }}
        if (cursor != null) {
            cursor.close();
        }
        return userid;
    }
    public void logout(){
        db.execSQL("delete from "+ LoginModel.table);
    }

}
