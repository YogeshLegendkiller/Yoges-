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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Activities.GeneralClaimUpdation;
import precisioninfomatics.servicefirst.Adapters.GeneralClaimAdapter;
import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.CursorLoaders.GeneralClaimLoaders;
import precisioninfomatics.servicefirst.DAO.DeleteDAO;
import precisioninfomatics.servicefirst.DAO.GeneralClaimDAO;
import precisioninfomatics.servicefirst.Model.DeleteModel;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Networks.DeleteRecords;
import precisioninfomatics.servicefirst.Networks.GeneralClaimFile;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 21-02-2017.
 */

public class GeneralClaim  extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
   private Integer IncidentID,incistatusID;
    private RecyclerView mrecyclerview;
    private  TextView EmptyView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generalclaim, container, false);
        FloatingActionButton fab= view.findViewById(R.id.fab);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            incistatusID=bundle.getInt("status");
            IncidentID = bundle.getInt("id");
        }
        mrecyclerview= view.findViewById(R.id.detail_visit);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mrecyclerview.setLayoutManager(layoutManager);
        EmptyView= view.findViewById(R.id.emptyview);
        if(incistatusID==8){
           fab.setVisibility(View.INVISIBLE);
        }
            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), GeneralClaimUpdation.class);
                intent.putExtra("incidentID",IncidentID);
                intent.putExtra("flag",1);
                startActivityForResult(intent, 1);

            }
        });
        GeneralClaimFile generalClaimFile=new GeneralClaimFile();
        generalClaimFile.PostFile(getActivity(),IncidentID);
        getLoaderManager().initLoader(1, null, this);
        return view;
    }
        @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
         /*   return new CursorLoader(getActivity(), GeneralClaimDAO.DB_SF_GeneralClaim,null,null, null, null) {
                private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
                @Override
                public Cursor loadInBackground() {
                    Cursor c =generalClaimDAO.GeneralClaimList(IncidentID);
                    if (c != null) {
                        c.getCount();
                        c.registerContentObserver(mObserver);
                        c.setNotificationUri(getContext().getContentResolver(), getUri());
                    }
                    return c;
                }

            };*/
         return new GeneralClaimLoaders(getActivity(), GeneralClaimDAO.DB_SF_GeneralClaim,null,null, null, null,IncidentID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        GeneralClaimAdapter generalClaimAdapter=new GeneralClaimAdapter(getActivity(),data);
        mrecyclerview.setAdapter(generalClaimAdapter);

        if(generalClaimAdapter.getItemCount() == 0){
            EmptyView.setText("No Data Available");
            EmptyView.setVisibility(View.VISIBLE);
        }
        else {
            EmptyView.setVisibility(View.INVISIBLE);
        }
        generalClaimAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(final  Cursor cursor) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                alertDialog.setTitle("Confirm Delete...");

                alertDialog.setMessage("Are you sure you want delete this?");

                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        int id=cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_ID));
                        int webid=cursor.getInt(cursor.getColumnIndex(GeneralClaimModel.GC_ClaimID));
                        if(webid>0){
                            DeleteDAO deleteDAO=new DeleteDAO(getActivity());
                            DeleteModel deleteModel=new DeleteModel();
                            deleteModel.setWebID(webid);
                            deleteModel.setFlag(1);
                            //   deleteModel.setIsSent(1);
                            deleteDAO.InsertRecords(deleteModel);
///
                        }
                        GeneralClaimDAO generalClaimDAO=new GeneralClaimDAO(getActivity());
                        generalClaimDAO.DeleteGC(id);
                        DeleteRecords deleteRecords=new DeleteRecords();
                        deleteRecords.DeleteRecord(getActivity());
                        //localVendorDAO.DeleteSingle(id);
                    }
                });

                // Setting Negative "NO" Button
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ondestroyview","ondestroyview");
        mrecyclerview.setAdapter(null);
        mrecyclerview=null;
    }
}
