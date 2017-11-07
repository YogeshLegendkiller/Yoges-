package precisioninfomatics.servicefirst.Activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import precisioninfomatics.servicefirst.Adapters.IncidentCursorAdapter;
import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.BackgroundServices.IncidentDataSerivce;
import precisioninfomatics.servicefirst.CursorLoaders.IncidentLoader;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Networks.IncidentData;
import precisioninfomatics.servicefirst.R;

public class Incident extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,SearchView.OnQueryTextListener ,SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Integer flag;
    private boolean internetcheck;
    private String label,filterText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.incident_recyclerview);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            flag=bundle.getInt("flag");
            label=bundle.getString("label");
        }
        else {
            flag=2;
        }
        recyclerView.setLayoutManager(layoutManager);
        internetcheck = Utility.isNetworkAvailable(this);
        if(internetcheck){
            new IncidentData(Incident.this, null,1);
            startService(new Intent(Incident.this, IncidentDataSerivce.class));
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Incident");
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Incident.this, Dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
        getLoaderManager().initLoader(0, null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        return new IncidentLoader(this, IncidentDAO.DB_SF_Incident, null, null, null, null,flag,filterText,label);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ondestroy_inc","ondestroy_inc");
        swipeRefreshLayout=null;
        recyclerView.setAdapter(null);
        getLoaderManager().destroyLoader(0);
        flag=null;
        filterText=null;
        Runtime.getRuntime().gc();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader,final Cursor cursor) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IncidentCursorAdapter  incidentCursorAdapter = new IncidentCursorAdapter(cursor);
                recyclerView.setAdapter(incidentCursorAdapter);
                TextView textView = findViewById(R.id.emptytext);
                if (incidentCursorAdapter.getItemCount() == 0) {
                    textView.setText("No Data Available");
                    textView.setVisibility(View.VISIBLE);
                } else {
                    textView.setVisibility(View.INVISIBLE);
                }
                incidentCursorAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClicked(Cursor cursor) {
                        Intent intent = new Intent(Incident.this, IncidentView.class);
                        int status=cursor.getInt(cursor.getColumnIndex(IncidentModel.inc_statusID));
                        String inccode=cursor.getString(cursor.getColumnIndex(IncidentModel.inc_incCode));
                        int is_localvendor = cursor.getInt(cursor.getColumnIndex(IncidentModel.inc_localvendor));
                        int is_generalclaim = cursor.getInt(cursor.getColumnIndex(IncidentModel.inc_gc));
                        int is_partrequired = cursor.getInt(cursor.getColumnIndex(IncidentModel.inc_partreq));
                        int is_installationcall = cursor.getInt(cursor.getColumnIndex(IncidentModel.inc_instalcall));
                        int id = cursor.getInt(cursor.getColumnIndex(IncidentModel.inc_regID));
                        String lat=cursor.getString(cursor.getColumnIndex(IncidentModel.inc_lat));
                        String longt=cursor.getString(cursor.getColumnIndex(IncidentModel.inc_long));
                        String customeraddress=cursor.getString(cursor.getColumnIndex(IncidentModel.inc_customeraddrs));
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putString("customeraddress",customeraddress);
                        bundle.putString("lat", lat);
                        bundle.putString("long", longt);
                        bundle.putInt("generalclaim", is_generalclaim);
                        bundle.putInt("localvendor", is_localvendor);
                        bundle.putInt("status",status);
                        bundle.putInt("partrequired", is_partrequired);
                        bundle.putString("inscode",inccode);
                        bundle.putInt("installationcall", is_installationcall);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Incident.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appcompatsearch, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView msearchview=(SearchView) MenuItemCompat.getActionView(searchItem);
        msearchview.setQueryHint("Customer Name or Incident Code.....");
        msearchview.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onRefresh() {
        if (internetcheck) {
            new IncidentData(Incident.this, swipeRefreshLayout,2);
        }
        else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(Incident.this, "Please Check Your Network Connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onstop_inc","onstop_inc");
        stopService(new Intent(Incident.this, IncidentDataSerivce.class));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterText = !TextUtils.isEmpty(newText) ? newText : null;
        getLoaderManager() .restartLoader(0,null,this);
        return false;
    }
}


