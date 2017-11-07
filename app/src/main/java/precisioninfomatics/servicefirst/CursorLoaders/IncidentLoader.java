package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import precisioninfomatics.servicefirst.DAO.IncidentDAO;

/**
 * Created by 4264 on 23/08/2017.
 */

public  class IncidentLoader extends CursorLoader {

    private IncidentDAO incidentDAO;
    private Integer flag;
    private String filterText,label;
    private   ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    public IncidentLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, int flag, String filtertext, String label) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        incidentDAO=new IncidentDAO(context);
        this.flag=flag;
        this.filterText=filtertext;
        this.label=label;
}



    @Override
    public Cursor loadInBackground() {
        Cursor c;;
        if(flag==1){
            if(filterText!=null){
                c = incidentDAO.IncidentListBasedOnLabelWithFilter(label,filterText);
            }
            else {
                c = incidentDAO.IncidentListBasedOnLabel(label);
            }
        }
        else {
            if(filterText!=null){
                c = incidentDAO.FilterList(filterText);
            }
            else {
                c = incidentDAO.IncidentList();
            }
        }
        if (c != null) {
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return c;
    }


}