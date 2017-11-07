package precisioninfomatics.servicefirst.CursorLoaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import precisioninfomatics.servicefirst.DAO.PartIssueDAO;
import precisioninfomatics.servicefirst.DAO.SerialNumberDAO;
import precisioninfomatics.servicefirst.DAO.SpareDAO;

import android.content.CursorLoader;

/**
 * Created by 4264 on 23/08/2017.
 */

public class VisitUpdateLoaders extends CursorLoader {
  private Integer IncidentID,ID,WebID,LoaderID;
  private SpareDAO spareDAO;
  private PartIssueDAO partIssueDAO;
  private SerialNumberDAO serialNumberDAO;
    private    ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

    public VisitUpdateLoaders(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder,int IncId,int WebID,int ID, int loaderID) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
       this.spareDAO=new SpareDAO(context);
       this.partIssueDAO=new PartIssueDAO(context);
       this.serialNumberDAO= new SerialNumberDAO(context);
       this.LoaderID=loaderID;
       this.IncidentID=IncId;
       this.ID=ID;
       this.WebID=WebID;
    }
    @Override
    public Cursor loadInBackground() {
        Cursor c=null;
        switch (LoaderID){
            case 1:
                c = spareDAO.SpareRequest(IncidentID);
                break;
            case 2:
                c=partIssueDAO.partIssueList(IncidentID);
                break;
            case 3:
                c=serialNumberDAO.Serialnumbermap(ID,WebID);
        }
        if (c != null) {
            c.registerContentObserver(mObserver);
            c.setNotificationUri(getContext().getContentResolver(), getUri());

        }
        return c;
    }
}
