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
import android.widget.TextView;
import precisioninfomatics.servicefirst.Adapters.SpareAdapter;
import precisioninfomatics.servicefirst.DAO.SpareDAO;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 21-12-2016.
 */

public class Spares extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mrecyclerview;
    private SpareDAO spareDAO;
    private int id,webID;
    TextView EmptyView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_spare, container, false);
        mrecyclerview=(RecyclerView)view.findViewById(R.id.spare);
        spareDAO=new SpareDAO(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mrecyclerview.setLayoutManager(layoutManager);
        Intent intent=getActivity().getIntent();
        EmptyView=(TextView)view.findViewById(R.id.empty_view);
        if(intent!=null){
            id=intent.getIntExtra("id",0);
            webID=intent.getIntExtra("webid",0);
        }
        getLoaderManager().initLoader(0,null,this);
        return view;
    }
        @Override
    public Loader<Cursor> onCreateLoader(int ide, Bundle args) {
            return new CursorLoader(getContext(), SpareDAO.DB_SF_Spare ,null,null, null, null) {
                private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
                @Override
                public Cursor loadInBackground() {
                    Cursor c =spareDAO.SpareViewByID(id,webID);
                    if (c != null) {
                        c.getCount();
                        c.registerContentObserver(mObserver);
                        c.setNotificationUri(getContext().getContentResolver(), getUri());
                    }
                    return c;
                }

            };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        SpareAdapter spareAdapter=new SpareAdapter(data);
        mrecyclerview.setAdapter(spareAdapter);
        if(spareAdapter.getItemCount() == 0){
            EmptyView.setText("No Data Available");
            EmptyView.setVisibility(View.VISIBLE);
        }
        else {
            EmptyView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
