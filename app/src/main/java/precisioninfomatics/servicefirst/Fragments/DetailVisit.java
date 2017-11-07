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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import precisioninfomatics.servicefirst.Activities.*;
import precisioninfomatics.servicefirst.Adapters.VisitViewCursorAdapter;
import precisioninfomatics.servicefirst.CursorLoaders.DetailVisitLoaders;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 21-12-2016.
 */
public class DetailVisit extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>  {
    private int id,IncidentID,ispartrequired,isInstallationCall,Userid;
     RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visit_info, container, false);
         recyclerView = view.findViewById(R.id.detail_visit);
         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent=getActivity().getIntent();
        setHasOptionsMenu(true);
        if(intent!=null){
            id=intent.getIntExtra("id",0);
            IncidentID=intent.getIntExtra("incidentid",0);
            Userid=intent.getIntExtra("userid",0);
            ispartrequired = intent.getIntExtra("ispartrequired", 0);
            isInstallationCall=intent.getIntExtra("installationcall",0);
        }
        getLoaderManager().initLoader(0, null, this);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
           LoginDAO loginDAO=new LoginDAO(getActivity());
           int userid=loginDAO.getUserID();
            if(userid==Userid)
            {
            Intent intent=new Intent(getActivity(), VisitUpdation.class);
            intent.putExtra("id",id);
            intent.putExtra("editflag",2);
            intent.putExtra("incidentid",IncidentID);
            intent.putExtra("ispartrequired",ispartrequired);
            intent.putExtra("installationcall",isInstallationCall);
            startActivityForResult(intent,1);
        }
            else {
                Toast.makeText(getActivity(),"You Cannot Edit This",Toast.LENGTH_SHORT).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int ids, Bundle args) {
      /*  return new CursorLoader(getActivity(), VisitDAO.VisitDAO_URI, null, null, null, null) {
            private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                Cursor c;
                c = visitDAO.visitView(id);
                if (c != null) {
                    c.getCount();
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());
                }
                return c;
            }

        };*/
      return new DetailVisitLoaders(getActivity(), VisitDAO.VisitDAO_URI, null, null, null, null,id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        VisitViewCursorAdapter visitViewCursorAdapter=new VisitViewCursorAdapter(getActivity(),data);
        recyclerView.setAdapter(visitViewCursorAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}