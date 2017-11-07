package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.FileDAO;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class IncidentViewLoaders extends CursorLoader {
    private IncidentDAO incidentDAO;
    private FileDAO fileDAO;
    private Integer LoaderID,id;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    public IncidentViewLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,int LoaderID,int id) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.incidentDAO=new IncidentDAO(context);
        this.LoaderID=LoaderID;
        this.id=id;
        this.fileDAO=new FileDAO(context);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c=null;
        switch (LoaderID) {
            case 0:
                c = incidentDAO.Incidentview(id);
                break;
            case 1:
                c = fileDAO.FileList(id);
                break;
        }
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }
}
