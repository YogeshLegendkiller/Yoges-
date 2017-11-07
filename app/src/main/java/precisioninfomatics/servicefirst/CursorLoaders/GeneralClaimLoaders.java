package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.GeneralClaimDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class GeneralClaimLoaders extends CursorLoader{
     private GeneralClaimDAO generalClaimDAO;
     private Integer IncidentID;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    public GeneralClaimLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,int IncidentID) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.generalClaimDAO=new GeneralClaimDAO(context);
        this.IncidentID=IncidentID;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c =generalClaimDAO.GeneralClaimList(IncidentID);
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }
}
