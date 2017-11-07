package precisioninfomatics.servicefirst.Activities;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import precisioninfomatics.servicefirst.DAO.LocalVendorDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.DatePicker;
import precisioninfomatics.servicefirst.HelperClass.DateValue;
import precisioninfomatics.servicefirst.HelperClass.FileChoosers;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.InvoiceItemsModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Networks.LocalVendorFile;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

import static precisioninfomatics.servicefirst.Activities.Dashboard.RequestPermissionCode;

public class LocalVendor extends AppCompatActivity implements DateValue {
    private EditText invoicenumber,invoicedate,spare_charge,attachment,servicecharge;
    private String userChoosenTask,filepath, filename;
    private Integer IncidentID;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView service_charge_text,spare_charge_text;
    private View service_charge_view,spare_charge_view;
    private SimpleDateFormat dateFormat,serverformat;
//    private RadioGroup radioGroup;
    private CheckBox sparecost,servicecost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_vendor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LocalVendor.this, IncidentView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
         }
        service_charge_text= findViewById(R.id.service_charge_text);
        spare_charge_text= findViewById(R.id.spare_charge_text);
        service_charge_view= findViewById(R.id.service_charge_view);
        spare_charge_view= findViewById(R.id.spare_charge_view);
       // radioGroup=(RadioGroup)findViewById(R.id.radio_grp) ;
        servicecharge= findViewById(R.id.service_charge);
        invoicenumber= findViewById(R.id.invoicenumber);
        invoicedate= findViewById(R.id.invoicedate);
        spare_charge= findViewById(R.id.sparecost);
          sparecost= findViewById(R.id.spare_cost_check);
        servicecost= findViewById(R.id.service_charge_check);
        sparecost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    spare_charge.setVisibility(View.VISIBLE);
                    spare_charge_text.setVisibility(View.VISIBLE);
                    spare_charge_view.setVisibility(View.VISIBLE);
                }
                else {
                    spare_charge.setVisibility(View.GONE);
                    spare_charge_text.setVisibility(View.GONE);
                    spare_charge_view.setVisibility(View.GONE);
                }
            }
        });
        servicecost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    service_charge_view.setVisibility(View.VISIBLE);
                    service_charge_text.setVisibility(View.VISIBLE);
                    servicecharge.setVisibility(View.VISIBLE);

                }
                else {
                    service_charge_view.setVisibility(View.GONE);
                    service_charge_text.setVisibility(View.GONE);
                    servicecharge.setVisibility(View.GONE);

                }
            }
        });
    /*    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.yes) {
                    spare_charge.setVisibility(View.GONE);
                    spare_charge_text.setVisibility(View.GONE);
                    spare_charge_view.setVisibility(View.GONE);

                    service_charge_view.setVisibility(View.VISIBLE);
                    service_charge_text.setVisibility(View.VISIBLE);
                    servicecharge.setVisibility(View.VISIBLE);
                } else if(i==R.id.no){
                    service_charge_view.setVisibility(View.GONE);
                    service_charge_text.setVisibility(View.GONE);
                    servicecharge.setVisibility(View.GONE);

                    spare_charge.setVisibility(View.VISIBLE);
                    spare_charge_text.setVisibility(View.VISIBLE);
                    spare_charge_view.setVisibility(View.VISIBLE);
                }
            }
        });*/
        attachment= findViewById(R.id.attachment);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         serverformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Bundle bundle=getIntent().getExtras();
        invoicedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayDatePicker(1);
            }
        });
        if(bundle!=null){
            IncidentID=bundle.getInt("incidentID");
        }
        View bottomSheet = findViewById(R.id.intentbottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        ImageView cancel_bottomsheet = findViewById(R.id.cancel);
        cancel_bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        RelativeLayout file= findViewById(R.id.file);
        file.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                userChoosenTask = "Choose from Library";
                if(Utility.checkPermission(LocalVendor.this)){
                    FileChoosers.galleryIntent(LocalVendor.this);
                }
                else {
                    Utility.requestPermission(LocalVendor.this);
                }
            }
        });
        RelativeLayout camera= findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                userChoosenTask = "Take Photo";
                if(Utility.checkPermission(LocalVendor.this)){
                    FileChoosers.cameraIntent(LocalVendor.this);
                }
                else {
                    Utility.requestPermission(LocalVendor.this);
                }
            }
        });
        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED );
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:
                boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean externalstorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0) {
                    if (CameraPermission && externalstorage) {

                        if (userChoosenTask.equals("Take Photo"))
                            FileChoosers.cameraIntent(LocalVendor.this);
                        else if (userChoosenTask.equals("Choose from Library"))
                            FileChoosers.galleryIntent(LocalVendor.this);
                    }
                    else {
                        //    Toast.makeText(VisitUpdation.this,"Please Enable Some Permission To Access Camera or File ",Toast.LENGTH_LONG).show();
                        Utility.requestPermission(LocalVendor.this);
                    }
                }

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if(invoicenumber.getText().toString().isEmpty()||invoicenumber.getText().toString().startsWith(" ")){
                    Toast.makeText(this, "Please fill the InvoiceNumber", Toast.LENGTH_SHORT).show();
                }
                else if(invoicedate.getText().toString().isEmpty()||invoicedate.getText().toString().startsWith(" ")){
                    Toast.makeText(this, "Please fill the InvoiceDate", Toast.LENGTH_SHORT).show();
                }

                else if(!sparecost.isChecked()&&!servicecost.isChecked()){
                    Toast.makeText(this, "Please fill either sparecost or servicecharge", Toast.LENGTH_SHORT).show();
                }
                else if(sparecost.isChecked()&&spare_charge.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please fill the Spare Charge", Toast.LENGTH_SHORT).show();

                }
                else if(servicecost.isChecked()&&servicecharge.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please fill the Service Charge", Toast.LENGTH_SHORT).show();

                }
        //   else if(attachment.getText().toString().isEmpty()){
       // //      Toast.makeText(this, "Please attach the InvoiceBill", Toast.LENGTH_SHORT).show();
       //   }
                else {
                    try {
                    Handler handler = new Handler();
                    LocalVendorModel localVendor = new LocalVendorModel();
                    if(servicecost.isChecked() && sparecost.isChecked()){
                        localVendor.setChargeType(3);
                    }
                    else if(servicecost.isChecked()){
                        localVendor.setChargeType(1);
                      //  sparecost.setVisibility(View.INVISIBLE);
                        spare_charge.setText(String.valueOf(0));
                    }
                    else if(sparecost.isChecked()){
                        localVendor.setChargeType(2);
                    //    servicecost.setVisibility(View.INVISIBLE);
                        servicecharge.setText(String.valueOf(0));
                    }
                    localVendor.setServiceCharge(Integer.valueOf(servicecharge.getText().toString()));
                    localVendor.setIncidentID(IncidentID);
                    localVendor.setSpareCharge(Integer.valueOf(spare_charge.getText().toString()));
                    localVendor.setInvoiceNumber(invoicenumber.getText().toString());
                    String invoicedatetxt = ViewDateTimeFormat.Date(invoicedate.getText().toString(), 1);
                    localVendor.setInvoiceDate(invoicedatetxt);
                    LoginDAO loginDAO = new LoginDAO(this);
                    int userID = loginDAO.getUserID();
                    localVendor.setUserOrVendorID(userID);
                    localVendor.setLocalFilePath(filepath);
                    localVendor.setIsSent(1);
                    localVendor.setTargetType(3);
                    localVendor.setCreatedBy(userID);
                    localVendor.setResourceName(SharedPreferenceManager.getServiceString("Name", null, this));
                    localVendor.setLastModifiedBy(userID);
                    localVendor.setCreatedDateTime(serverformat.format(new Date()));
                    localVendor.setLastModifiedDateTime(serverformat.format(new Date()));
                    localVendor.setLocalFileName(filename);
                    if (filepath == null) {
                        localVendor.setIsFileExist(0);
                        Log.d("file", "null");
                    } else {
                        localVendor.setIsFileExist(1);
                        Log.d("file", "1");
                    }
                    LocalVendorDAO localvendorobj = new LocalVendorDAO(this);
                    localvendorobj.InsertLocalVendor(localVendor);
                    final boolean internetcheck = Utility.isNetworkAvailable(this);
                    if (internetcheck) {
                        LocalVendorFile localVendorFile = new LocalVendorFile();
                        localVendorFile.PostFile(this, IncidentID,userID);
                    }
                    final ProgressDialog progressDialog=Utility.showProgressDialog(this,"Please wait");
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Intent intent = new Intent(LocalVendor.this, IncidentView.class);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }, 6000);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }


                }
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if(data!=null) {
                Uri uri = data.getData();
                filepath = FileChoosers.getPath(this, uri);
                filename = FileChoosers.getFileName(uri, this);
                Log.d("file", filepath);
            }} else if (requestCode == 1) {
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String uri=  preferences.getString("uri", "");
            filepath = FileChoosers.getPath(this, Uri.parse(uri));
            filename = FileChoosers.getFileName(Uri.parse(uri), this);
            Log.d("file", filepath);
            preferences.edit().remove("uri").apply();
        }
        bottomSheetBehavior.setHideable(true);
        if (filepath != null) {
            File isexist = new File(filepath);
            if (isexist.exists()) {
                attachment.setText(filename);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {
                filepath=null;
            }
        }
        else {
            Log.d("tryagain", "try");
        }
    }



    public void DisplayDatePicker(int flag) {
        DatePicker datePicker = new DatePicker(LocalVendor.this, flag);
        datePicker.dateValue = LocalVendor.this;
        datePicker.displaydatepicker();
    }

    @Override
    public void getDate(Calendar calendar, int flag) {
        if(flag==1) {
             invoicedate.setText(dateFormat.format(calendar.getTime()));
        }
    }

}
