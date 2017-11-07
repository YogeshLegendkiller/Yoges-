package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.VisitDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class DetailVisitLoaders extends CursorLoader {
    private VisitDAO visitDAO;
    private Integer id;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    public DetailVisitLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,int id) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.visitDAO=new VisitDAO(context);
        this.id=id;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c;
        c = visitDAO.visitView(id);
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }
}
