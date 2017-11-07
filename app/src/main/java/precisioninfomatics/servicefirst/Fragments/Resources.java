package precisioninfomatics.servicefirst.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import precisioninfomatics.servicefirst.Adapters.ResourceViewAdapter;
import precisioninfomatics.servicefirst.CursorLoaders.ResourceLoaders;
import precisioninfomatics.servicefirst.DAO.FileDAO;
import precisioninfomatics.servicefirst.DAO.ResourceDAO;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Networks.FileTravel;
import precisioninfomatics.servicefirst.Networks.ResourceData;
import precisioninfomatics.servicefirst.Networks.TravelData;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 25-11-2016.
 */

public class Resources extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private Integer id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.resource_layout, container, false);
        recyclerView= view.findViewById(R.id.resource_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
         Bundle bundle=getActivity().getIntent().getExtras();
        if(bundle!=null){
            id=bundle.getInt("id");
        }
        boolean internetcheck= Utility.isNetworkAvailable(getActivity());
        if(internetcheck) {
            new ResourceData(getActivity(), id);
        }
        // boolean resourcecheck=resourceDAO.ResourceCheck(id);
      ///  if(!resourcecheck){
      //      new ResourceData(getActivity(),id);
        //}
        ///else {
          ///  Log.d("exist","exist");
       // }
          return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onpause_resource","onresource");
        recyclerView.setAdapter(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int ids, Bundle args) {
     /*   return new CursorLoader(getActivity(), ResourceDAO.DB_SF_Resource,null,null, null, null) {
             private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                Cursor c;
                c =resourceDAO.Resource(id);
                if (c != null) {
                    c.getCount();
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }

        };
    */
    return new ResourceLoaders(getActivity(), ResourceDAO.DB_SF_Resource,null,null, null, null,id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ResourceViewAdapter resourceViewAdapter=new ResourceViewAdapter(getActivity(),data);
        recyclerView.setAdapter(resourceViewAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(1,null,this);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ondestroyview","ondestroyview");
        recyclerView.setAdapter(null);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
