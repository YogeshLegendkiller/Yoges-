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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.Adapters.TransportModeAdapter;
import precisioninfomatics.servicefirst.CursorLoaders.TransportModelLoaders;
import precisioninfomatics.servicefirst.DAO.TransportModeDAO;
import precisioninfomatics.servicefirst.Model.TransportModeModel;
import precisioninfomatics.servicefirst.Networks.TransportModeData;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 17-02-2017.
 */

public class TransportModeFragment  extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView mRecyclerView;
    private TransportModeFragment.Transportmoderesult mListener;
 //   private TransportModeDAO transportModeDAO;
    private Integer flag;

    private TextView emptyview;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_recyclerview, container, false);
        mRecyclerView = v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        emptyview = v.findViewById(R.id.emptytext);
        new TransportModeData(getActivity());
        Toolbar toolbar = v. findViewById(R.id.toolbar);
        setHasOptionsMenu(true);

        Bundle bundle=getArguments();
        if(bundle!=null){
            flag=bundle.getInt("flag");
        }
        if (toolbar!=null) {
            if(flag==1){
                toolbar.setTitle("Vehicle Type");
            }
            else {
                toolbar.setTitle("TransportMode");
            }
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        }
        getActivity().getSupportLoaderManager().initLoader(0,null,this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /*return new CursorLoader(getActivity(), TransportModeDAO.TransportDAO_URI,null,null, null, null) {
            private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                Cursor c;
                if(flag==1){
                   c= transportModeDAO.VehicleRegistration();
                }
                else {
                    c = transportModeDAO.transportlist();
                }

                if (c != null) {
                    c.getCount();
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }

        };*/
        return new TransportModelLoaders(getActivity(), TransportModeDAO.TransportDAO_URI,null,null, null, null,flag);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        TransportModeAdapter transportModeAdapter=new TransportModeAdapter(data);
        mRecyclerView.setAdapter(transportModeAdapter);
        if(transportModeAdapter.getItemCount()==0){
            emptyview.setVisibility(View.VISIBLE);
            emptyview.setText("Refresh");

        }
        else {
            emptyview.setVisibility(View.GONE);
        }
        emptyview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new precisioninfomatics.servicefirst.Networks.TransportModeData(getActivity());

            }
        });
        transportModeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Cursor cursor) {
                int id=cursor.getInt(cursor.getColumnIndex(TransportModeModel.webid));
                String text=cursor.getString(cursor.getColumnIndex(TransportModeModel.name));
                mListener.onResultRecieve(id,text);
                if (getDialog() != null && getDialog().isShowing()) {
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public  interface Transportmoderesult {
        void onResultRecieve(int  id,String text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onedestroy","ondestroy");
        getActivity().getSupportLoaderManager().destroyLoader(0);
    }

    public void onAttach(Context context) {
        Activity activity=null;
        super.onAttach(context);
        try {
            activity = (Activity) context;
            mListener = (TransportModeFragment.Transportmoderesult) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

}
