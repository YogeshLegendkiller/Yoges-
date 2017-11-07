package precisioninfomatics.servicefirst.Fragments;

import android.app.Activity;
import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.Adapters.SpareStatusAdapter;
import precisioninfomatics.servicefirst.CursorLoaders.SpareStatusLoaders;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.PartIssueDAO;
import precisioninfomatics.servicefirst.DAO.SpareStatusDAO;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.SpareStatusModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 18/03/2017.
 */

public class SpareStatus extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView mRecyclerView;
    private SpareStatusListener mListener;
    //private SpareStatusDAO StatusDAO;
    private Cursor c;
    private TextView emptyview;
    private int updateID,sequenceID,flag;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_recyclerview, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //    getDialog().setTitle("Select Status");
        new precisioninfomatics.servicefirst.Networks.SpareStatus(getActivity());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Toolbar toolbar = (Toolbar)v. findViewById(R.id.toolbar);
        emptyview = (TextView) v.findViewById(R.id.emptytext);
        setHasOptionsMenu(true);
        if (toolbar!=null) {
            toolbar.setTitle("Status");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        }
         Bundle bundle=getArguments();
        if(bundle!=null){
            updateID=bundle.getInt("id");
            sequenceID=bundle.getInt("sequence");
            flag=bundle.getInt("flag");
        }
        getActivity().getSupportLoaderManager().initLoader(0,null,this);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                  return false;
            }
        });
         return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onedestroy","ondestroy");
        getActivity().getSupportLoaderManager().destroyLoader(0);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      /*  return new CursorLoader(getActivity(), SpareStatusDAO.DB_SF_SpareStatus,null,null, null, null) {
            private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                c = StatusDAO.SpareStatus(sequenceID);
                if (c != null) {
                    c.getCount();
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }

        };
    */
      return new SpareStatusLoaders(getActivity(), SpareStatusDAO.DB_SF_SpareStatus,null,null, null, null,sequenceID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        SpareStatusAdapter spareStatusAdapter =new SpareStatusAdapter(data);
        mRecyclerView.setAdapter(spareStatusAdapter);
        if(spareStatusAdapter.getItemCount()==0){
            emptyview.setVisibility(View.VISIBLE);
            emptyview.setText("Refresh");

        }
        else {
            emptyview.setVisibility(View.GONE);
        }
         emptyview.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new precisioninfomatics.servicefirst.Networks.SpareStatus(getActivity());

             }
         });
            spareStatusAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Cursor cursor) {
                int id=cursor.getInt(cursor.getColumnIndex(SpareStatusModel.statusid));
                String text=cursor.getString(cursor.getColumnIndex(SpareStatusModel.statusname));
                if(flag==1){
                    mListener.spareValue(id,text);
                }
                else if(flag==2){
                    PartIssueDAO partIssueDAO=new PartIssueDAO(getActivity());
                    PartIssueModel partIssueModel=new PartIssueModel();
                    partIssueModel.setStatusName(text);
                   // partIssueModel.setCurrentPartStatusID(id);
                    partIssueModel.setPartStatusID(id);
                    LoginDAO loginDAO=new LoginDAO(getActivity());
                    int userid=loginDAO.getUserID();
                    partIssueModel.setLastModifiedBy(userid);
                    SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    partIssueModel.setLastModifiedDateTime(dateFormat.format(new Date()));
                    partIssueModel.setUpdatedID(updateID);
                    partIssueDAO.UpdatePart(partIssueModel);
                }
                if (getDialog() != null && getDialog().isShowing()) {
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public void onAttach(Context context) {
        Activity activity=null;
        super.onAttach(context);
        try {
            activity = (Activity) context;
            mListener = (SpareStatusListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
    public  interface SpareStatusListener {
        void spareValue(int  id,String text);
    }

}
