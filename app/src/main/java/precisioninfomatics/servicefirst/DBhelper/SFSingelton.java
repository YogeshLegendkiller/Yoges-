package precisioninfomatics.servicefirst.DBhelper;

/**
 * Created by 4264 on 08-12-2016.
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SFSingelton {

    public SQLiteDatabase db;
    private SFTables dbHelper;
    private Context mContext;

    public SFSingelton(Context context) {
        this.mContext = context;
        open();
    }


    private void open() throws SQLException {
        if (dbHelper == null)
            dbHelper = SFTables.getHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }



}