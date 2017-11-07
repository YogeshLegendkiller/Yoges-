package precisioninfomatics.servicefirst.Fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.io.File;
import precisioninfomatics.servicefirst.Adapters.CustomerBranchIncidentViewAdapter;
import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.CursorLoaders.CustomerBranchLoaders;
import precisioninfomatics.servicefirst.DAO.CustomerBranchDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.CustomerBranchModel;
import precisioninfomatics.servicefirst.Networks.CustomerBranchNetwork;
import precisioninfomatics.servicefirst.R;



public class CustomerBranchDialogFragment  extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView mRecyclerView;
    private Integer incidentid;
    private TextView emptyview;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_recyclerview, container, false);
        mRecyclerView = v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        emptyview = v.findViewById(R.id.emptytext);
       Toolbar toolbar = v. findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        if (toolbar!=null) {
            toolbar.setTitle("CustomerBranch");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        }
        Bundle bundle=getArguments();
        if(bundle!=null){
            incidentid=bundle.getInt("incid");
          //  inscode=bundle.getString("inscode");
        }
        new CustomerBranchNetwork(getActivity(),incidentid);
        getLoaderManager().initLoader(0,null,this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       /* return new CursorLoader(getActivity(), CustomerBranchDAO.DB_SF_CustomerBranch,null,null, null, null) {
            private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                Cursor  c= customerBranchDAO.CustomerBranchList(incidentid);
                if (c != null) {
                    c.getCount();
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }

        };
    */return new CustomerBranchLoaders(getActivity(), CustomerBranchDAO.DB_SF_CustomerBranch,null,null, null, null,incidentid);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        CustomerBranchIncidentViewAdapter customerBranchAdapter=new CustomerBranchIncidentViewAdapter(data);
        mRecyclerView.setAdapter(customerBranchAdapter);
        if(customerBranchAdapter.getItemCount()==0){
            emptyview.setVisibility(View.VISIBLE);
            emptyview.setText("Refresh");
            new CustomerBranchNetwork(getActivity(),incidentid);

        }
        else {
            emptyview.setVisibility(View.GONE);
        }
        emptyview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        customerBranchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Cursor cursor) {
                int id=cursor.getInt(cursor.getColumnIndex(CustomerBranchModel.CustomerBranchid));
                File   downloadfile= Utility.GetFileName(getActivity(),"ServiceFirst/I_Note", "Installation Note Print.pdf");
                IncidentView.DownloadTask downloadTask = new IncidentView.DownloadTask(getActivity(), downloadfile, "Downloading",incidentid,3);
                downloadTask.execute(URL.SF_URL+"/inoteprint/"+ incidentid +"/"+id);
                Log.d("pdfurl",URL.SF_URL+"/inoteprint/"+ incidentid +"/"+id);
                  if (getDialog() != null && getDialog().isShowing()) {
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onedestroy","ondestroy");
       getLoaderManager().destroyLoader(0);
    }



}

