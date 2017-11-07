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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;

import java.util.List;

import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.Adapters.PendingClassificationAdapter;
import precisioninfomatics.servicefirst.CursorLoaders.PendingClassificationLoaders;
import precisioninfomatics.servicefirst.DAO.PendingClassificationDAO;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 09-02-2017.
 */

public class PendingClassification  extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor>,android.widget.SearchView.OnQueryTextListener{
    private RecyclerView mRecyclerView;
    private PendingClassification.PendingClassificationResult mListener;
    PendingClassificationDAO pendingClassificationDAO;
    private String filterText;
    private Cursor c;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_recyclerview, container, false);
        mRecyclerView = v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Toolbar toolbar = v. findViewById(R.id.toolbar);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setHasOptionsMenu(true);
        if (toolbar!=null) {
            toolbar.setTitle("Pending Classification");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        }
        final SearchView searchView = new SearchView(getActivity());
        //     searchView.setIc
        searchView.setOnQueryTextListener(this);
        pendingClassificationDAO=new PendingClassificationDAO(getActivity());
        getActivity().getSupportLoaderManager().initLoader(0,null,this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       /* return new CursorLoader(getActivity(), PendingClassificationDAO.pendinclassification_URI,null,null, null, null) {
            private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {

                if(filterText!=null){
                     c=pendingClassificationDAO.pendingclassificationlist_search(filterText);
                }
                else {
                    c = pendingClassificationDAO.pendingclassificationlist();
                }
                if (c != null) {
                    c.getCount();
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }


        };
*/
    return new PendingClassificationLoaders(getActivity(), PendingClassificationDAO.pendinclassification_URI,null,null, null, null,filterText);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        PendingClassificationAdapter pendingClassificationAdapter=new PendingClassificationAdapter(data);
        mRecyclerView.setAdapter(pendingClassificationAdapter);
        pendingClassificationAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Cursor cursor) {
                int id=cursor.getInt(cursor.getColumnIndex(PendingClassificationModel.webid));
                String text=cursor.getString(cursor.getColumnIndex(PendingClassificationModel.text));
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

    public  interface PendingClassificationResult {
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
            mListener = (PendingClassification.PendingClassificationResult) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

}
