package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;



import precisioninfomatics.servicefirst.DAO.VisitStatusDAO;


/**
 * Created by 4264 on 23/08/2017.
 */

public class VisitStatusLoader  extends CursorLoader {
    private VisitStatusDAO visitStatusDAO;
    private String filterText;
    private ForceLoadContentObserver mObserver = new  ForceLoadContentObserver();
    public VisitStatusLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,String filtertext) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        visitStatusDAO=new VisitStatusDAO(context);
        this.filterText=filtertext;

    }



    @Override
    public Cursor loadInBackground() {
        Cursor c;
        if(filterText!=null){
            c=visitStatusDAO.FilterStatus(filterText);
        }
        else {
            c = visitStatusDAO.visitStatusList();

        }
        if (c != null) {
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());

        }
        return c;
    }

}
