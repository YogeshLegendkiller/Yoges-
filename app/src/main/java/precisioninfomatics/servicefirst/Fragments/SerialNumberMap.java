package precisioninfomatics.servicefirst.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import precisioninfomatics.servicefirst.Adapters.SerialNumberMapView;
import precisioninfomatics.servicefirst.CursorLoaders.SerialNumberMapLoaders;
import precisioninfomatics.servicefirst.DAO.SerialNumberDAO;
import precisioninfomatics.servicefirst.DAO.SpareDAO;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 19/06/2017.
 */

public class SerialNumberMap extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private  RecyclerView recyclerView;
    private int id,webID;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visit_info, container, false);
        recyclerView= view.findViewById(R.id.detail_visit);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Intent intent=getActivity().getIntent();
        if(intent!=null){
            id=intent.getIntExtra("id",0);
            webID=intent.getIntExtra("webid",0);
        }
        getLoaderManager().initLoader(0,null,this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int ide, Bundle args) {
    /*    return new CursorLoader(getContext(), SpareDAO.DB_SF_Spare ,null,null, null, null) {
            private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                Cursor c =serialNumberDAO.SerialnumbermapView(id,webID);
                if (c != null) {
                    c.getCount();
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }

        };*/
    return new SerialNumberMapLoaders(getContext(), SpareDAO.DB_SF_Spare ,null,null, null, null,id,webID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        SerialNumberMapView serialNumberMapView=new SerialNumberMapView(data);
        recyclerView.setAdapter(serialNumberMapView);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}