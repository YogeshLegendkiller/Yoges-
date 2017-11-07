package precisioninfomatics.servicefirst.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import precisioninfomatics.servicefirst.Adapters.LocalVendorAdapter;
import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.CursorLoaders.LocalVendorLoaders;
import precisioninfomatics.servicefirst.DAO.DeleteDAO;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.LocalVendorDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.Model.DeleteModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Networks.DeleteRecords;
import precisioninfomatics.servicefirst.Networks.LocalVendorData;
import precisioninfomatics.servicefirst.Networks.LocalVendorFile;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 01/03/2017.
 */

public class LocalVendor  extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
    private Integer IncidentID,incistatusID;
    private RecyclerView localvendorRecyclerview;
    private LocalVendorDAO localVendorDAO;
    private TextView emptyview;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_vendor, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle!=null){
            incistatusID=bundle.getInt("status");
            IncidentID=bundle.getInt("id");
        }
        if(incistatusID==8){
            fab.setVisibility(View.INVISIBLE);
        }

        localVendorDAO=new LocalVendorDAO(getActivity());
         emptyview= view.findViewById(R.id.emptyview);
        localvendorRecyclerview= view.findViewById(R.id.local_vendor_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        localvendorRecyclerview.setLayoutManager(layoutManager);
        LoginDAO loginobj=new LoginDAO(getActivity());
        int   userid=loginobj.getUserID();
        LocalVendorFile localVendorData=new LocalVendorFile();
        localVendorData.PostFile(getActivity(),IncidentID,userid);
         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), precisioninfomatics.servicefirst.Activities.LocalVendor.class);
                Bundle bundle=new Bundle();
                bundle.putInt("incidentID",IncidentID);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
     /*   return new CursorLoader(getActivity(), LocalVendorDAO.LocalVendor_URI, null, null, null, null) {
            private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                Cursor c;
                c = localVendorDAO.LocalVendorList(IncidentID);
                if (c != null) {
                    c.getCount();
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }

        };
*/
    return new LocalVendorLoaders(getActivity(), LocalVendorDAO.LocalVendor_URI, null, null, null, null,IncidentID);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        LocalVendorAdapter localVendorAdapter=new LocalVendorAdapter(getActivity(),data);
        localvendorRecyclerview.setAdapter(localVendorAdapter);
         if(localVendorAdapter.getItemCount()==0){
            emptyview.setVisibility(View.VISIBLE);
             emptyview.setText("No Data Available");
        }
        else {
             emptyview.setVisibility(View.INVISIBLE);

         }
        localVendorAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(final Cursor cursor) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                alertDialog.setTitle("Confirm Delete...");

                alertDialog.setMessage("Are you sure you want delete this?");

                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        int id=cursor.getInt(cursor.getColumnIndex(LocalVendorModel.id));
                        int webid=cursor.getInt(cursor.getColumnIndex(LocalVendorModel.webid));
                        if(webid>0){
                            DeleteDAO deleteDAO=new DeleteDAO(getActivity());
                            DeleteModel deleteModel=new DeleteModel();
                            deleteModel.setWebID(webid);
                            deleteModel.setFlag(2);
                            deleteDAO.InsertRecords(deleteModel);
                        }
                           localVendorDAO.DeleteSingle(id);
                           DeleteRecords deleteRecords=new DeleteRecords();
                           deleteRecords.DeleteRecord(getActivity());
                     }
                });

                 alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}