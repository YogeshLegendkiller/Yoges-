package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.PendingClassificationDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class PendingClassificationLoaders extends CursorLoader {

  private PendingClassificationDAO pendingClassificationDAO;
  private String filterText;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    public PendingClassificationLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,String filterText) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.pendingClassificationDAO=new PendingClassificationDAO(context);
        this.filterText=filterText;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c;
        if(filterText!=null){
            c=pendingClassificationDAO.pendingclassificationlist_search(filterText);
        }
        else {
            c = pendingClassificationDAO.pendingclassificationlist();
        }
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }

}

