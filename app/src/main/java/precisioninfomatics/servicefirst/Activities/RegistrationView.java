package precisioninfomatics.servicefirst.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import precisioninfomatics.servicefirst.Adapters.VehicleRegistrationAdapter;
import precisioninfomatics.servicefirst.DAO.VehicleDAO;
import precisioninfomatics.servicefirst.Model.VehicleModel;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

public class RegistrationView extends AppCompatActivity {
    private  int travelcheck;
    private RadioButton yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_view);
        RecyclerView recyclerView = findViewById(R.id.registration_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RadioGroup radioGroup = findViewById(R.id.radio_grp);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        setSupportActionBar(toolbar);
        VehicleDAO vehicleDAO=new VehicleDAO(RegistrationView.this);
        List<VehicleModel>listobj=vehicleDAO.list();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Registration");
         }
        VehicleRegistrationAdapter vehicleRegistrationAdapter=new VehicleRegistrationAdapter(listobj);
        recyclerView.setAdapter(vehicleRegistrationAdapter);
        TextView emptytext= findViewById(R.id.emptytext);
        travelcheck= SharedPreferenceManager.getInteger("check",0);
        int TwoWheelerCheck=SharedPreferenceManager.getInteger("two_wheeler_check",0);
        if(TwoWheelerCheck==0 &&travelcheck==1 ){
            no.setChecked(true);
        }
        else if(TwoWheelerCheck==0&&travelcheck==2){
            yes.setChecked(true);
        }
        else if(TwoWheelerCheck==1){
            yes.setChecked(true);
        }
        else if(TwoWheelerCheck==2){
            no.setChecked(true);
        }
         if(vehicleRegistrationAdapter.getItemCount()==0||travelcheck==1){
              emptytext.setText("No Vehicle Registered");
              emptytext.setVisibility(View.VISIBLE);
              recyclerView.setVisibility(View.GONE);
              findViewById(R.id.card).setVisibility(View.GONE);
         }
        else {
            emptytext.setVisibility(View.GONE);
        }
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setTitle("Registration Detail");
                    Intent intent = new Intent(RegistrationView.this, Dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
      //  SharedPreferences settings = getApplicationContext().getSharedPreferences("two_wheeler_availablity", MODE_PRIVATE); // 0 - for private mode
      // final SharedPreferences.Editor editor = settings.edit();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.yes){
                    yes.setChecked(true);
                    SharedPreferenceManager.putInteger("two_wheeler_check",1);
                }
                else if(i==R.id.no) {
                    no.setChecked(true);
                    SharedPreferenceManager.putInteger("two_wheeler_check",2);
                }
             //   editor.apply();
            }
        });
         int id = radioGroup.getCheckedRadioButtonId();
        if(id==R.id.yes){
            SharedPreferenceManager.putInteger("two_wheeler_check",1);
        }
        else {
            SharedPreferenceManager.putInteger("two_wheeler_check",2);
        }
        //editor.apply();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent=new Intent(RegistrationView.this,VehicleRegistration.class);
                Bundle bundle=new Bundle();
                bundle.putInt("flag",1);
                bundle.putInt("check",travelcheck);
                intent.putExtras(bundle);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
