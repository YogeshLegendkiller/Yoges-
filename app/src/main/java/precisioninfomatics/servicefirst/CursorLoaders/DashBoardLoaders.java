package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import precisioninfomatics.servicefirst.DAO.ChartDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public  class DashBoardLoaders extends CursorLoader {

    private ChartDAO chartDAO;
    private Integer flag;
    private Cursor c;
    private    ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
   public DashBoardLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, int flag) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        chartDAO=new ChartDAO(context);
        this.flag=flag;
    }



    @Override
    public Cursor loadInBackground() {
        switch (flag) {
            case 0:
                c = chartDAO.PieData();
                break;
            case 1:
                c = chartDAO.SummaryData();
                break;
        }
        if (c != null) {
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }

        return c;
    }


}