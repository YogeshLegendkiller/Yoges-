package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.SpareStatusDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class SpareStatusLoaders extends CursorLoader{
   private SpareStatusDAO spareStatusDAO;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    private Integer sequenceID;
    public SpareStatusLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,int id) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        spareStatusDAO=new SpareStatusDAO(context);
        this.sequenceID=id;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c;
        c = spareStatusDAO.SpareStatus(sequenceID);
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }
}

