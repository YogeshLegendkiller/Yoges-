package precisioninfomatics.servicefirst.CursorLoaders;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import precisioninfomatics.servicefirst.DAO.CustomerListDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public class CustomerBranchLoader extends CursorLoader {
    private CustomerListDAO customerListDAO;
    private Integer incidentid,flag;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    public CustomerBranchLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,int incidentid,int flag) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.customerListDAO=new CustomerListDAO(context);
        this.incidentid=incidentid;
        this.flag=flag;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c;
        c= customerListDAO.customerlist(incidentid,flag);
        if (c != null) {
            c.getCount();
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }
}
