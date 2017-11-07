package precisioninfomatics.servicefirst.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;

import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.CursorLoaders.VisitStatusLoader;
import precisioninfomatics.servicefirst.DAO.VisitStatusDAO;
import precisioninfomatics.servicefirst.Adapters.VisitStatusAdapter;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 29-12-2016.
 */

public class VisitStatus extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor>,android.widget.SearchView.OnQueryTextListener{
    private String filterText;
    private RecyclerView mRecyclerView;
    private VisitStatus.OnCompleteListener mListener;
      @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_recyclerview, container, false);
        mRecyclerView = v.findViewById(R.id.recyclerview);
         mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
         getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Toolbar  toolbar = v. findViewById(R.id.toolbar);
         setHasOptionsMenu(true);
        if (toolbar!=null) {
              toolbar.setTitle("Status");
              toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        }
        final  SearchView searchView = new SearchView(getActivity());
   //     searchView.setIc
        searchView.setOnQueryTextListener(this);
      //  searchView.setBackgroundResource(R.drawable.ic_search);
        getActivity().getSupportLoaderManager().initLoader(0,null,this);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setActionView(searchView);
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.search);
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
      /*  return new CursorLoader(getActivity(), VisitStatusDAO.VisitDAO_URI ,null,null, null, null) {
            private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                if(filterText!=null){
                    c=visitStatusDAO.FilterStatus(filterText);
                }
                else {
                    c = visitStatusDAO.visitStatusList();

                    }

                return c;
            }

        };*/
      return new VisitStatusLoader(getActivity(), VisitStatusDAO.VisitDAO_URI ,null,null, null, null,filterText);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        VisitStatusAdapter visitStatusAdapter =new VisitStatusAdapter(data);
        mRecyclerView.setAdapter(visitStatusAdapter);
        visitStatusAdapter.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClicked(Cursor cursor) {
              int id=cursor.getInt(cursor.getColumnIndex(VisitStatusModel.statusid));
             String text=cursor.getString(cursor.getColumnIndex(VisitStatusModel.status));
             mListener.onComplete(id,text);
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
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filterText = !TextUtils.isEmpty(s) ? s : null;
        getActivity().getSupportLoaderManager().restartLoader(0,null,this);
        return false;
    }


    public  interface OnCompleteListener {
        void onComplete(int  id,String text);
    }

    public void onAttach(Context context) {
        Activity activity=null;
        super.onAttach(context);
        try {
             activity = (Activity) context;
            mListener = (VisitStatus.OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

}