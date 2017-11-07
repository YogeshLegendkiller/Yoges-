package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.GPSDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;

/**
 * Created by 4264 on 01/09/2017.
 */

public class VisitTravelLoader  extends CursorLoader {
    private GPSDAO gpsdao;
    private Integer id;
    private ForceLoadContentObserver mObserver = new  ForceLoadContentObserver();
    public VisitTravelLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        gpsdao=new GPSDAO(context);
        this.id=id;

    }



    @Override
    public Cursor loadInBackground() {
    Cursor  c = gpsdao.Gps();
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }

}
