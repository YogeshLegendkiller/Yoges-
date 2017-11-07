package precisioninfomatics.servicefirst.CursorLoaders;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.SerialNumberDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class SerialNumberMapLoaders extends CursorLoader {
  private Integer id,WebID;
  private SerialNumberDAO serialNumberDAO;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    public SerialNumberMapLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,int id,int webID) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.id=id;
        this.WebID=webID;
        this.serialNumberDAO=new SerialNumberDAO(context);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c =serialNumberDAO.SerialnumbermapView(id,WebID);
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }
}

