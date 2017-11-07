package precisioninfomatics.servicefirst.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VehicleDAO;
import precisioninfomatics.servicefirst.Fragments.TransportModeFragment;
import precisioninfomatics.servicefirst.HelperClass.DatePicker;
import precisioninfomatics.servicefirst.HelperClass.DateValue;
import precisioninfomatics.servicefirst.Model.VehicleModel;
import precisioninfomatics.servicefirst.Networks.TransportModeData;
import precisioninfomatics.servicefirst.Networks.VehicleData;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

public class VehicleRegistration extends AppCompatActivity implements DateValue,TransportModeFragment.Transportmoderesult {
    private RadioGroup radioGroup;
    private int insert_update, logincheck,userid,VehicleTypeID;
    private VehicleDAO vehicleDAO;
    private EditText RegNo, LicNo, LicExpiryDate, InsuranceExpiryDate,VehicleType;
    private Spinner LicType;
    private ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RegNo = findViewById(R.id.registrationno);
        VehicleType= findViewById(R.id.vehicletype);
        LicNo = findViewById(R.id.lic_no);
        LicType = findViewById(R.id.license_type);
        new TransportModeData(VehicleRegistration.this);
        getSupportActionBar().setTitle("Vehicle Registration");
        LicExpiryDate = findViewById(R.id.license_expirydate);
        InsuranceExpiryDate = findViewById(R.id.insurace_expirydate);
        RadioButton yes = findViewById(R.id.yes);
        RadioButton no = findViewById(R.id.no);
        vehicleDAO = new VehicleDAO(VehicleRegistration.this);
        logincheck = vehicleDAO.verifybike();
        int travelcheck = 0;
        VehicleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                TransportModeFragment transportModeFragment = new TransportModeFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("flag",1);
                transportModeFragment.setArguments(bundle);
                transportModeFragment.setRetainInstance(true);
                transportModeFragment.show(fm, "fm");
            }
        });
        LoginDAO loginobj = new LoginDAO(this);
        userid = loginobj.getUserID();
        adapter = ArrayAdapter.createFromResource(this,R.array.vehicle_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LicType.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            insert_update = bundle.getInt("flag");
            Edit_registration();
            travelcheck = bundle.getInt("check", 0);
            findViewById(R.id.card).setVisibility(View.VISIBLE);
        } else {
            boolean on_time_login=SharedPreferenceManager.getBooean("vehicle_flag",false);
            if (on_time_login) {
                Intent intent = new Intent(VehicleRegistration.this, Dashboard.class);
                startActivity(intent);
            }
        }
        radioGroup = findViewById(R.id.radio_grp);
        if (travelcheck == 1) {
            no.setChecked(true);
            DisableRegistration();

        } else {
            yes.setChecked(true);
            EnableRegistration();
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.yes) {
                    EnableRegistration();
                } else if(i==R.id.no){
                    DisableRegistration();
                }
            }
        });
        InsuranceExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayDatePicker(1);

            }
        }
        );
         LicExpiryDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DisplayDatePicker(2);
             }
         });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.yes) {
                    //      boolean flag=true;
                    if (RegNo.getText().toString().isEmpty()) {
                        RegNo.setError("Please Fill The Registration No");
                        //      flag=false;
                    } else if (LicNo.getText().toString().isEmpty()) {
                        LicNo.setError("Please Fill The License No");
                        //        flag=false;
                    } else if (LicType.getSelectedItem().toString().equals("--Select--")) {
                        //     LicType.setError("Please Fill The License Type");
                        Toast.makeText(VehicleRegistration.this, "Please Select The License Type", Toast.LENGTH_SHORT).show();
                        //       flag=false;
                    } else if (LicExpiryDate.getText().toString().isEmpty()) {
                        LicExpiryDate.setError("Please Fill The License Expiry Date");
                        //      flag=false;
                    } else if (InsuranceExpiryDate.getText().toString().isEmpty()) {
                        InsuranceExpiryDate.setError("Please Fill The InsuranceExpiry Date");
                        //       flag=false;
                    } else if (VehicleType.getText().toString().isEmpty() ){
                        VehicleType.setError("Please Fill The Vehicle Type");
                }  else {
                        VehicleModel vehicleModel = new VehicleModel();
                        vehicleModel.setRegNo(RegNo.getText().toString());
                        vehicleModel.setLicenseNo(LicNo.getText().toString());
                        vehicleModel.setLicenseType(LicType.getSelectedItem().toString());
                        vehicleModel.setExpiryDate(LicExpiryDate.getText().toString());
                        SharedPreferenceManager.putInteger("check", 2);
                        vehicleModel.setVehicleType(VehicleTypeID);
                        vehicleModel.setUserID(userid);
                        if(LicType.getSelectedItemPosition()==2){
                            vehicleModel.setLicenseTypeID(1);
                        }
                        else if(LicType.getSelectedItemPosition()==3){
                            vehicleModel.setLicenseTypeID(2);
                        }
                        else {
                            vehicleModel.setLicenseTypeID(3);
                        }
                        SharedPreferenceManager.putInteger("two_wheeler_check",1);
                        SharedPreferenceManager.putInteger("vehicletype",VehicleTypeID);
                        vehicleModel.setInsuranceExpiryDate(InsuranceExpiryDate.getText().toString());
                        if (insert_update == 1 && logincheck > 0) {
                            vehicleDAO.UpdateVehicleDetails(vehicleModel);
                            Intent intent = new Intent(this, RegistrationView.class);
                            startActivity(intent);
                        } else {
                            vehicleDAO.InsertVehicleDetails(vehicleModel);
                            VehicleData vehicleData=new VehicleData();
                            vehicleData.PostVehicleData(VehicleRegistration.this);
                            Intent intent = new Intent(this, Dashboard.class);
                            startActivity(intent);
                        }

                    }}
                 else {
                    Intent intent = new Intent(this, Dashboard.class);
                    startActivity(intent);
                    //no vehicle check;
                    SharedPreferenceManager.putInteger("check", 1);
                    SharedPreferenceManager.putInteger("two_wheeler_check",2);
                }
                SharedPreferenceManager.putBoolean("vehicle_flag", true);
               // editor.apply();
        }
        return super.onOptionsItemSelected(item);
    }

    public void DisplayDatePicker(int flag) {
        DatePicker datePicker = new DatePicker(VehicleRegistration.this, flag);
        datePicker.dateValue = VehicleRegistration.this;
        datePicker.displaydatepicker();
    }

    public void Edit_registration() {
        Cursor c = vehicleDAO.vehicleedit();
        if (c.moveToFirst()) {
            VehicleTypeID=c.getInt(c.getColumnIndex(VehicleModel.vehicletype));
            if(VehicleTypeID==1){
                VehicleType.setText("TWO WHEELER");
            }
            else {
                VehicleType.setText("CAR");
            }
            RegNo.setText(c.getString(c.getColumnIndex(VehicleModel.regno)));
            LicNo.setText(c.getString(c.getColumnIndex(VehicleModel.licno)));
            String lictype=(c.getString(c.getColumnIndex(VehicleModel.lictype)));
            LicType.setSelection(adapter.getPosition(lictype));
            LicExpiryDate.setText(c.getString(c.getColumnIndex(VehicleModel.licexpdate)));
            InsuranceExpiryDate.setText(c.getString(c.getColumnIndex(VehicleModel.ins_expdate)));
        }
    }

    @Override
    public void getDate(Calendar calendar, int flag) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        if (flag == 1) {
            InsuranceExpiryDate.setText(dateFormatter.format(calendar.getTime()));
        }
        else{
            LicExpiryDate.setText(dateFormatter.format(calendar.getTime()));
        }
    }
    public void DisableRegistration(){
        findViewById(R.id.card1).setVisibility(View.GONE);
        findViewById(R.id.card2).setVisibility(View.GONE);
        findViewById(R.id.textregno).setVisibility(View.GONE);
        findViewById(R.id.registrationno).setVisibility(View.GONE);
        findViewById(R.id.radio_view).setVisibility(View.GONE);
        findViewById(R.id.vehicletype).setVisibility(View.GONE);
        findViewById(R.id.vehicletypeview).setVisibility(View.GONE);
        findViewById(R.id.vehicletypetxt).setVisibility(View.GONE);
    }
    public void EnableRegistration(){
        findViewById(R.id.card1).setVisibility(View.VISIBLE);
        findViewById(R.id.card2).setVisibility(View.VISIBLE);
        findViewById(R.id.textregno).setVisibility(View.VISIBLE);
        findViewById(R.id.registrationno).setVisibility(View.VISIBLE);
        findViewById(R.id.vehicletype).setVisibility(View.VISIBLE);
        findViewById(R.id.vehicletypeview).setVisibility(View.VISIBLE);
        findViewById(R.id.vehicletypetxt).setVisibility(View.VISIBLE);
    }

    @Override
    public void onResultRecieve(int id, String text) {
        VehicleType.setText(text);
        VehicleTypeID=id;
    }
}
