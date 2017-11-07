package precisioninfomatics.servicefirst.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import precisioninfomatics.servicefirst.Adapters.TravelViewAdapter;
import precisioninfomatics.servicefirst.CursorLoaders.TravelViewLoader;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.R;

public class TravelView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mrecyclerview;
    private int id,flag,Userid,StatusID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mrecyclerview=(RecyclerView)findViewById(R.id.travelview) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(TravelView.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(layoutManager);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Travel Information");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TravelView.this, Dashboard.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
        Intent intent=getIntent();
        if(intent!=null){
            id=intent.getIntExtra("id",0);
            flag=intent.getIntExtra("flag",0);
            Userid=intent.getIntExtra("userid",0);
            StatusID=intent.getIntExtra("status",0);
        }

        getLoaderManager().initLoader(1,null,TravelView.this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(flag!=1){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                LoginDAO loginDAO=new LoginDAO(this);
                int userid=loginDAO.getUserID();
                if(userid==Userid && StatusID!=8){
                    Intent intent=new Intent(this,TravelEdit.class);
                    intent.putExtra("id",id);
                    intent.putExtra("flag",2);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TravelView.this,"You Cannot Edit This",Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int ids, Bundle args) {
      /*  return  new  android.content.CursorLoader(TravelView.this, VisitDAO.VisitDAO_URI, null, null, null, null){
            ForceLoadContentObserver mObserver=new ForceLoadContentObserver();
            @Override
            public Cursor loadInBackground() {
                Cursor c;
                c = visitDAO.TravelView(id);
                if (c != null) {
                    c.registerContentObserver(mObserver);
                    c.setNotificationUri(getContext().getContentResolver(), getUri());

                }
                return c;
            }
        };*/

        return new TravelViewLoader(this, VisitDAO.VisitDAO_URI, null, null, null, null,id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        TravelViewAdapter travelViewAdapter=new TravelViewAdapter(data);
        mrecyclerview.setAdapter(travelViewAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
