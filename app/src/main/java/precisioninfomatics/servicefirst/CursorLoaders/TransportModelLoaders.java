package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.TransportModeDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class TransportModelLoaders extends CursorLoader {
    private TransportModeDAO transportModeDAO;
    private Integer flag;
    private ForceLoadContentObserver mObserver = new  ForceLoadContentObserver();
    public TransportModelLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, int  flag) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        transportModeDAO=new TransportModeDAO(context);
        this.flag=flag;

    }



    @Override
    public Cursor loadInBackground() {
        Cursor c;
        if(flag==1){
            c= transportModeDAO.VehicleRegistration();
        }
        else {
            c = transportModeDAO.transportlist();
        }

        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }
}