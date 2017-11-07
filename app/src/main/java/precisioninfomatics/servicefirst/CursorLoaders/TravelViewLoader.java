package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class TravelViewLoader  extends CursorLoader {

    private VisitDAO visitDAO;
    private Integer id;
    private Loader.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver();
    public TravelViewLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, int id) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        visitDAO=new VisitDAO(context);
        this.id=id;
    }



    @Override
    public Cursor loadInBackground() {
        Cursor c;
        c = visitDAO.TravelView(id);
        if (c != null) {
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());

        }
        return c;
    }


}