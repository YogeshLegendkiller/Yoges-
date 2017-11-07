package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.CustomerBranchDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class CustomerBranchLoaders extends CursorLoader {
    private CustomerBranchDAO customerBranchDAO;
    private Integer IncidentID;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    public CustomerBranchLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,int id) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.customerBranchDAO=new CustomerBranchDAO(context);
        this.IncidentID=id;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor  c= customerBranchDAO.CustomerBranchList(IncidentID);
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }
}
