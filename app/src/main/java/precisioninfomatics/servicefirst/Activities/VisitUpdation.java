package precisioninfomatics.servicefirst.Activities;


import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.Adapters.SerialNumberAdapter;
import precisioninfomatics.servicefirst.Adapters.SpareIssueAdapter;
import precisioninfomatics.servicefirst.Adapters.Spare_Visit_cursorAdapter;
import precisioninfomatics.servicefirst.CursorLoaders.VisitUpdateLoaders;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.PartIssueDAO;
import precisioninfomatics.servicefirst.DAO.SerialNumberDAO;
import precisioninfomatics.servicefirst.DAO.SpareDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.Fragments.CustomerBranch;
import precisioninfomatics.servicefirst.Fragments.PendingClassification;
import precisioninfomatics.servicefirst.Fragments.SpareStatus;
import precisioninfomatics.servicefirst.Fragments.VisitStatus;
import precisioninfomatics.servicefirst.HelperClass.DatePicker;
import precisioninfomatics.servicefirst.HelperClass.DateValue;
import precisioninfomatics.servicefirst.HelperClass.FileChoosers;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Networks.FileVisit;
import precisioninfomatics.servicefirst.Networks.VisitViewData;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static precisioninfomatics.servicefirst.Activities.Dashboard.RequestPermissionCode;

public class VisitUpdation extends AppCompatActivity implements VisitStatus.OnCompleteListener, LoaderManager.LoaderCallbacks<Cursor>, PendingClassification.PendingClassificationResult, DateValue,SpareStatus.SpareStatusListener ,CustomerBranch.CustomerBranchResult {

    private SimpleDateFormat  dateFormat,dateview;
    private String userChoosenTask;
    private Spinner LocalVendor;
    private SpareDAO spareDAO;
    private Integer ID, WebID,editflag, StatusID, PendingClassificationID, userid, IncidentID;
    private RecyclerView spare_recyclerview,spare_issue,serialnumber;
    private String filepath, filename, CheckoutTime;
    private  Spare_Visit_cursorAdapter spare_visit_cursorAdapter;
    private EditText CheckIN,CustomerBranch, CheckOut, FindingsAtSite, CallSlipno, CutoffDate, ActionTaken, Attachment, Status, PendingStatus, NextVisitDate, Remarks;
    private PartIssueDAO partIssueDAO;
    private SerialNumberDAO serialNumberDAO;
    private android.support.v4.app.FragmentManager fm;
    private  SpareIssueAdapter spareIssueAdapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private VisitDAO visitDAO ;
    private  Button btn_spare;
    private ImageView btn_checkout;
    private VisitModel visitModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_update);
        Attachment = findViewById(R.id.attachment);
        CheckIN = findViewById(R.id.checkin);
        CheckOut = findViewById(R.id.checkout);
        FindingsAtSite = findViewById(R.id.findingatsite);
        Remarks = findViewById(R.id.remarks);
        CustomerBranch= findViewById(R.id.customer_branch);
        spare_recyclerview = findViewById(R.id.spare_recyclerview);
        btn_spare = findViewById(R.id.btn_spare);
        CallSlipno = findViewById(R.id.callslipno);
        spare_issue= findViewById(R.id.spare_issue);
        serialnumber= findViewById(R.id.serialnumber);
        CutoffDate = findViewById(R.id.cutoffdate);
        NextVisitDate = findViewById(R.id.nextvisitdate);
        ActionTaken = findViewById(R.id.actiontaken);
        Status = findViewById(R.id.status);
        Toolbar toolbar = findViewById(R.id.toolbar);
        LocalVendor= findViewById(R.id.localvendor);
        View bottomSheet = findViewById(R.id.intentbottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        visitDAO = new VisitDAO(VisitUpdation.this);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        serialNumberDAO=new SerialNumberDAO(this);
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
                if(Utility.checkPermission(VisitUpdation.this)){

                    galleryIntent();
                }
                else {
                    Utility.requestPermission(VisitUpdation.this);
                }
             }
        });

        RelativeLayout camera= findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                userChoosenTask = "Take Photo";
                if(Utility.checkPermission(VisitUpdation.this)){
                    cameraIntent();
                }
                else {
                    Utility.requestPermission(VisitUpdation.this);
                }
            }
        });

        CustomerBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precisioninfomatics.servicefirst.Fragments.CustomerBranch customerBranch = new CustomerBranch();
                Bundle bundle=new Bundle();
                bundle.putInt("incid",IncidentID);
                bundle.putInt("flag",1);
                customerBranch.setArguments(bundle);
                customerBranch.setRetainInstance(true);
                customerBranch.show(fm, "fm");
            }
        });
        PendingStatus = findViewById(R.id.pending_status);
         btn_checkout = findViewById(R.id.btn_checkout);
        final CardView spare_request = findViewById(R.id.card1);
        final CardView item_map = findViewById(R.id.serialnumber_card);
        final CardView spareissue = findViewById(R.id.card2);
        spareDAO = new SpareDAO(this);
        LoginDAO loginobj = new LoginDAO(this);
        fm = getSupportFragmentManager();
        userid = loginobj.getUserID();
        LinearLayoutManager serialnumbermanager = new LinearLayoutManager(VisitUpdation.this);
        serialnumber.setLayoutManager(serialnumbermanager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(VisitUpdation.this);
        spare_recyclerview.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManagerPartIssue=new LinearLayoutManager(VisitUpdation.this);
        spare_issue.setLayoutManager(layoutManagerPartIssue);
        TextView localvendortext= findViewById(R.id.local_vendor_text);
        Intent intent = getIntent();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.localvendor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocalVendor.setAdapter(adapter);
        visitModel= new VisitModel();
        if (intent != null) {
            ID = intent.getIntExtra("id", 0);
            editflag = intent.getIntExtra("editflag", 0);
            int    isinstallationcall=intent.getIntExtra("installationcall",0);
            if(isinstallationcall==0){
                   item_map.setVisibility(View.GONE);
            } else {
                item_map.setVisibility(View.VISIBLE);
            }
          int   partrequired = intent.getIntExtra("ispartrequired", 0);
            if (partrequired == 0) {
                spare_request.setVisibility(View.GONE);
                localvendortext.setVisibility(View.GONE);
                LocalVendor.setVisibility(View.GONE);
                spareissue.setVisibility(View.GONE);
            }
            IncidentID = intent.getIntExtra("incidentid", 0);
            if (editflag == 0) {
                WebID = intent.getIntExtra("webid", 0);
                String  CheckinTime = intent.getStringExtra("checkin");
                String checkin_view = ViewDateTimeFormat.DateView(CheckinTime,1);
                CheckIN.setText(checkin_view);
            } else if(editflag == 2){
                btn_checkout.setVisibility(View.GONE);
                item_map.setVisibility(View.GONE);
                spare_request.setVisibility(View.GONE);
                spareissue.setVisibility(View.GONE);
                Status.setEnabled(false);
                VisitUpdate(ID);
            }
             else if(editflag==3){
                btn_checkout.setVisibility(View.GONE);
                VisitUpdate(ID);
            }
        }
        VisitViewData visitViewData=new VisitViewData();
        visitViewData.VisitView(this,IncidentID,0,1,null);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateview = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        partIssueDAO=new PartIssueDAO(VisitUpdation.this);
        NextVisitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Status.getText().toString().equals("FOLLOW UP")) {
                    DisplayDatePicker(1);
                } else {
                    Toast.makeText(VisitUpdation.this, "Please Select the Valid Status", Toast.LENGTH_SHORT).show();
                }
            }
        });
        CutoffDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Status.getText().toString().equals("OBSERVATION")) {
                    DisplayDatePicker(2);
                } else {
                    Toast.makeText(VisitUpdation.this, "Please Select the Valid Status", Toast.LENGTH_SHORT).show();

                }
            }
        });
        Button btn_item_map= findViewById(R.id.btn_item_serial_num);
         btn_item_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!CustomerBranch.getText().toString().isEmpty()) {
                    Intent intent = new Intent(VisitUpdation.this, MapSerialNumber.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("incidentid", IncidentID);
                    bundle.putInt("visitid", ID);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 4);
                }
                else {
                    Toast.makeText(VisitUpdation.this,"Please Select The Customer Branch",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_spare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitUpdation.this, SpareRequest.class);
                Bundle bundle = new Bundle();
                bundle.putInt("visitprimaryID", ID);
                bundle.putInt("incidentid", IncidentID);
                bundle.putInt("webid", WebID);
                //Flag for Save and Update
                bundle.putInt("flag", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });
        setSupportActionBar(toolbar);
         Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitStatus visitStatus = new VisitStatus();
                visitStatus.setRetainInstance(true);
                visitStatus.show(fm, "fragment_name");

            }
        });

        PendingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Status.getText().toString().equals("PENDING")) {
                    PendingClassification pendingClassification = new PendingClassification();
                    pendingClassification.setRetainInstance(true);
                    pendingClassification.show(fm, "fm");
                } else {
                    Toast.makeText(VisitUpdation.this, "Please Select the Valid Status", Toast.LENGTH_SHORT).show();

                }
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            getSupportActionBar().setTitle("Visit Updation");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VisitUpdation.this, IncidentView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

            btn_checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckoutTime = dateview.format(new Date());
                    CheckOut.setText(CheckoutTime);
                    btn_checkout.setVisibility(View.INVISIBLE);

                    btn_checkout.setVisibility(View.INVISIBLE);
                }
            });
            Attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED );
                }
            });
        }
        getLoaderManager().initLoader(1, null, this);
        getLoaderManager().initLoader(2,null,this);
        getLoaderManager().initLoader(3,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
            MenuInflater inflater = getMenuInflater();
        if (editflag == 3) {
            inflater.inflate(R.menu.save_delete, menu);
            View view = findViewById(R.id.delete);
            new MaterialShowcaseView.Builder(VisitUpdation.this)
                    .setTarget(view)
                    .setTitleText("Delete Visit")
                    .setDismissText("GOT IT")
                    .setMaskColour(Color.argb(150, 0, 0, 0))
                    .setDismissOnTouch(true)
                    .setContentText("This Is To Delete The Unsynced Visit")
                    .setDelay(1000) // optional but starting animations immediately in onCreate can make them choppy
                    .singleUse("delete") // provide a unique ID used to ensure it is only shown once
                    .show();
        } else {
            inflater.inflate(R.menu.save, menu);
            View view =  findViewById(R.id.save);
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500);
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(VisitUpdation.this, "visitupdate");
            sequence.setConfig(config);
            sequence.addSequenceItem(new MaterialShowcaseView.Builder(VisitUpdation.this)
                    .setTarget( view )
                    .setDismissText("GOT IT")
                    .setMaskColour(Color.argb(150, 0, 0, 0))
                    .setContentText("Once You Completed Your Visit Hit This Button After Filing The Details")
                    .build()
            );
           int sparecheck=SharedPreferenceManager.getServiceInteger("spare",0,VisitUpdation.this);
            if(sparecheck==0) {
                View targetView = findViewById(R.id.card1);
                targetView.getParent().requestChildFocus(targetView, targetView);
                sequence.addSequenceItem(
                        new MaterialShowcaseView.Builder(VisitUpdation.this)
                                .setTarget(btn_spare)
                                .setDismissText("GOT IT")
                                .setMaskColour(Color.argb(150, 0, 0, 0))
                                .setTitleText("Spare Request")
                                .setContentText("Hit This Button To Request The Spare")
                                .build()
                );
                SharedPreferenceManager.putInteger("spare", 1);
            }
            sequence.start();
        }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:

                if (CheckOut.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please Checkout from this visit", Toast.LENGTH_SHORT).show();
                } else if (FindingsAtSite.getText().toString().isEmpty() ) {
                    Toast.makeText(this, "Please Fill the Findings at site", Toast.LENGTH_SHORT).show();
                } else if (ActionTaken.getText().toString().isEmpty() ) {
                    Toast.makeText(this, "Please Fill the Action Taken", Toast.LENGTH_SHORT).show();

                } else if (Status.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please Fill Visit Status", Toast.LENGTH_SHORT).show();
                } else if (Status.getText().toString().contains("FOLLOW UP") && NextVisitDate.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please Select The Next Visit Date", Toast.LENGTH_SHORT).show();

                } else if (Status.getText().toString().contains("PENDING") && PendingStatus.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please Select The Pending Classification", Toast.LENGTH_SHORT).show();

                } else if (Status.getText().toString().contains("OBSERVATION") && CutoffDate.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please Select The Cut off Date", Toast.LENGTH_SHORT).show();
                } else {
                    Handler handler = new Handler();
                   final Intent intent = new Intent(this, IncidentView.class);
                    IncidentModel incidentModel=new IncidentModel();

                    Date checkoutdate= null;
                    try {
                        checkoutdate = dateview.parse(CheckOut.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    visitModel.setCheckOutDate(dateFormat.format(checkoutdate));
                    visitModel.setPendingClassificationText(PendingStatus.getText().toString());
                    //  visitModel.setPendingClassification(PendingClassificationID);
                    visitModel.setID(ID);
                    Log.d("checkout",dateFormat.format(checkoutdate));
                    if(LocalVendor.getSelectedItem().toString().equals("YES")){
                        visitModel.setLocalVendorID(1);
                        intent.putExtra("flag",1);
                        incidentModel.setLocalVendor(1);
                        visitModel.setIsLocalVendor(1);
                    }
                    else {
                        visitModel.setIsLocalVendor(0);
                        incidentModel.setLocalVendor(0);
                        intent.putExtra("flag",0);
                        visitModel.setLocalVendorID(0);
                    }
                   // String spareRequest=Utility.ConvertListToString(spareDAO.spareRequestList(IncidentID));
                   // visitModel.setSpareRequest(spareRequest);
                    String partIssue=Utility.ConvertListToString( partIssueDAO.partIssueModelList(IncidentID));
                    visitModel.setPartIssue(partIssue);
                    visitModel.setItemserialnomap(Utility.ConvertListToString(serialNumberDAO.serialnumberlist(ID)));
                    visitModel.setCustmerBranchID(SharedPreferenceManager.getServiceInteger("customerbranchid",0,VisitUpdation.this));
                    visitModel.setLocalVendorText(LocalVendor.getSelectedItem().toString());
                    visitModel.setActionTaken(ActionTaken.getText().toString());
                    visitModel.setFindingsAtSite(FindingsAtSite.getText().toString());
                    visitModel.setDocumentName(filename);
                    visitModel.setDocumentPath(filepath);
                    if(filepath!=null){
                        Log.d("filepath","exist");
                        visitModel.setIsFileExist(1);
                    }
                    else {
                        Log.d("file","not exist");
                        visitModel.setIsFileExist(0);
                    }
                    visitModel.setCallSlipNo(CallSlipno.getText().toString());
                    if(!NextVisitDate.getText().toString().isEmpty()){
                        visitModel.setNextVisitDate(ViewDateTimeFormat.Date(NextVisitDate.getText().toString(),1));
                    }
                    if(!CutoffDate.getText().toString().isEmpty()){
                        visitModel.setCutOffDate(ViewDateTimeFormat.Date(CutoffDate.getText().toString(),1));
                    }
                    visitModel.setLastmodifiedby(userid);
                    visitModel.setIsCallCompleted(0);
                    visitModel.setLastModifiedat(dateFormat.format(new Date()));
                    visitModel.setRemarks(Remarks.getText().toString());
                    visitModel.setIncidentID(IncidentID);
                    visitModel.setIsSent(1);
                    visitModel.setTravelrVisitFlag(1);

                    //added
                    visitModel.setCheckInDate(CheckIN.getText().toString());
                    visitModel.setCreatedby(userid);
                    visitModel.setCreatedat(dateFormat.format(new Date()));

                    //  Log.d("statusid",String.valueOf(SharedPreferenceManager.getServiceInteger("statusid",0,VisitUpdation.this)));
                    visitModel.setWebID(WebID);
                    visitModel.setVisitStatus(Status.getText().toString());
                    if(editflag==0){
                        visitModel.setStatus(SharedPreferenceManager.getServiceInteger("statusid",0,VisitUpdation.this));
                        visitModel.setPendingClassification(SharedPreferenceManager.getServiceInteger("pendingid",0,VisitUpdation.this));
                    }
                    else {
                        visitModel.setPendingClassification(PendingClassificationID);
                        visitModel.setStatus(StatusID);
                    }
                  //  CallSlipno
                    IncidentDAO incidentDAO=new IncidentDAO(VisitUpdation.this);
                    incidentDAO.UpdateIncidentLocalVendor(incidentModel,IncidentID);
                    visitModel.setSparePart(partIssueDAO.partIssueModelList(IncidentID));
                    visitModel.setUserID(userid);
                    visitModel.setPart(spareDAO.spareRequestList(IncidentID));
                    visitModel.setErrorCode(0);
                    spareDAO.UpdateIDUpdte(IncidentID);
                    visitDAO.UpdateVisit(visitModel);
                    SharedPreferenceManager.RemoveValue("statusid");
                    boolean internetcheck = Utility.isNetworkAvailable(VisitUpdation.this);
                    if(internetcheck){
                        FileVisit fileVisit = new FileVisit();
                        fileVisit.PostFile(VisitUpdation.this, IncidentID);
                    }
                    final ProgressDialog progressDialog=Utility.showProgressDialog(this,"Please wait");
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                         //   intent.putExtra("claim",GeneralClaim);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }, 6000);

                }
                break;
            case R.id.delete:


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                alertDialog.setTitle("Confirm Delete...");


                alertDialog.setMessage("Are you sure you want delete this?");

                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        visitDAO.DeleteUnwantedVisit(ID);
                        Intent intent = new Intent(VisitUpdation.this, IncidentView.class);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cameraIntent() {
       Uri fileUri = FileChoosers.getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        Intent it = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit()
                .putString("uri", fileUri.toString())
                .apply();
        it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(it, 1);
    }



    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        sIntent.putExtra("CONTENT_TYPE", "*/*");
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (getPackageManager().resolveActivity(sIntent, 0) != null){
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
        }
        else {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            startActivityForResult(chooserIntent, 2);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this , "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
        }
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
                            FileChoosers.cameraIntent(this);
                        else if (userChoosenTask.equals("Choose from Library"))
                            FileChoosers.galleryIntent(this);
                    }
                    else {
                    //    Toast.makeText(VisitUpdation.this,"Please Enable Some Permission To Access Camera or File ",Toast.LENGTH_LONG).show();
                        Utility.requestPermission(this);
                    }
                }

                break;
        }
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
            }
        } else if (requestCode == 1) {
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String uri=  preferences.getString("uri", "");
            filepath = FileChoosers.getPath(this, Uri.parse(uri));
            filename = FileChoosers.getFileName(Uri.parse(uri), this);
            Log.d("file", filepath);
            preferences.edit().remove("uri").apply();
        }
        if (filepath != null) {
            File isexist = new File(filepath);
            if (isexist.exists()) {
                Attachment.setText(filename);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {
                filepath=null;
            }
        }
        else {
            Log.d("tryagain", "try");
         }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        if(!CheckOut.getText().toString().isEmpty()){
            btn_checkout.setVisibility(View.GONE);
        }
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VisitUpdation.this, IncidentView.class);
        spareDAO.DeleteNotUpdate(IncidentID);
  //     serialNumberDAO.DeleteSerialNumNotUpdated(ID);
        setResult(RESULT_OK, intent);
        finish();
     //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }


    @Override
    public void onComplete(int id, String text) {
        Status.setText(text);
        StatusID = id;
        SharedPreferenceManager.putInteger("statusid",StatusID);
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, Bundle args) {

      return new VisitUpdateLoaders(this, SpareDAO.DB_SF_Spare, null, null, null, null,IncidentID,WebID,ID,id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        SerialNumberAdapter serialNumberMapAdapter=null;
        switch (loader.getId()) {
            case 1:
                spare_visit_cursorAdapter = new Spare_Visit_cursorAdapter(data);
                spare_recyclerview.setAdapter(spare_visit_cursorAdapter);
                break;
            case 2:
                spareIssueAdapter = new SpareIssueAdapter(data);
                spare_issue.setAdapter(spareIssueAdapter);
                break;
            case 3:
                serialNumberMapAdapter = new SerialNumberAdapter(this, data);
                serialnumber.setAdapter(serialNumberMapAdapter);
                break;
        }
        TextView empty_view_Partissue = findViewById(R.id.empty_view_issue);
        TextView empty_view_PartReq = findViewById(R.id.empty_view);
        TextView empty_view_serialnumber = findViewById(R.id.empty_serialnumberview);
        if (spareIssueAdapter != null) {
            if (spareIssueAdapter.getItemCount() == 0) {
                empty_view_Partissue.setVisibility(View.VISIBLE);
            } else {
                empty_view_Partissue.setVisibility(View.INVISIBLE);
            }

            spareIssueAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClicked(Cursor cursor) {
                    int sequence = cursor.getInt(cursor.getColumnIndex(PartIssueModel.squence));
                    int webid = cursor.getInt(cursor.getColumnIndex(PartIssueModel.reqid));
                    Log.d("webid", String.valueOf(webid));
                    if (sequence < 4) {
                        int spareIssueID = cursor.getInt(cursor.getColumnIndex(PartIssueModel.updatedid));
                        SpareStatus spareStatus = new SpareStatus();
                        spareStatus.setRetainInstance(true);
                        Bundle bundle = new Bundle();
                        bundle.putInt("sequence", sequence + 1);
                        bundle.putInt("id", spareIssueID);
                        bundle.putInt("flag", 2);
                        spareStatus.setArguments(bundle);
                        spareStatus.show(fm, "fragment_name");
                    }
                }
            });

        }
        if (spare_visit_cursorAdapter != null) {

            if (spare_visit_cursorAdapter.getItemCount() == 0) {
                empty_view_PartReq.setVisibility(View.VISIBLE);
            } else {
                empty_view_PartReq.setVisibility(View.INVISIBLE);
            }

            spare_visit_cursorAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClicked(Cursor cursor) {
                    int spareid = cursor.getInt(cursor.getColumnIndex(SpareModel.id));
                    int webid = cursor.getInt(cursor.getColumnIndex(SpareModel.webid));
                    boolean isPartIssued = partIssueDAO.ispartIssued(webid);
                    if (!isPartIssued) {
                        Log.d("webid", String.valueOf(webid));
                        Bundle bundle = new Bundle();
                        bundle.putInt("flag", 2);
                        bundle.putInt("webid", webid);
                        bundle.putInt("id", spareid);
                        Intent intent = new Intent(VisitUpdation.this, SpareRequest.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 3);
                    } else {
                        Toast.makeText(VisitUpdation.this, "Part Issued", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (serialNumberMapAdapter != null) {
            if (serialNumberMapAdapter.getItemCount() == 0) {
                empty_view_serialnumber.setVisibility(View.VISIBLE);
            } else {
                empty_view_serialnumber.setVisibility(View.INVISIBLE);
            }
              serialNumberMapAdapter.setOnItemClickListener(new OnItemClickListener() {
                  @Override
                  public void onItemClicked(Cursor cursor) {
                                      final Integer id=cursor.getInt(cursor.getColumnIndex(SerialNumberMapModel.id));
                      AlertDialog.Builder alertDialog = new AlertDialog.Builder(VisitUpdation.this);

                      alertDialog.setTitle("Confirm Delete...");

                      alertDialog.setMessage("Are you sure you want delete this?");

                      alertDialog.setIcon(R.drawable.ic_delete);

                      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog,int which) {
                              serialNumberDAO.Delete(id);
                          }
                      });

                      alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                          }
                      });

                      alertDialog.show();


                  }
              });
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onResultRecieve(int id, String text) {
        PendingStatus.setText(text);
        PendingClassificationID = id;
        SharedPreferenceManager.putInteger("pendingid",id);
    }

    @Override
    public void getDate(Calendar calendar, int flag) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        if (flag == 1) {
            NextVisitDate.setText(dateFormatter.format(calendar.getTime()));
        } else if (flag == 2) {
            CutoffDate.setText(dateFormatter.format(calendar.getTime()));
        }
    }

    public void DisplayDatePicker(int flag)   {
        DatePicker datePicker = new DatePicker(VisitUpdation.this, flag);
        datePicker.dateValue = VisitUpdation.this;
        datePicker.displaydatepicker();
    }

    private void VisitUpdate(int id) {
        VisitDAO visitDAO = new VisitDAO(VisitUpdation.this);
        Cursor c = visitDAO.visitUpdate(id);
        if (c.moveToFirst()) {
            String checkin_view = ViewDateTimeFormat.DateView((c.getString(c.getColumnIndex(VisitModel.checkin))),1);
            CheckIN.setText(checkin_view);
            //CheckOut.setText(c.getString(c.getColumnIndex(VisitModel.checkout)));
            CheckoutTime=c.getString(c.getColumnIndex(VisitModel.checkout));
            String checkout_view = ViewDateTimeFormat.DateView(CheckoutTime,1);
            CheckOut.setText(checkout_view);
            FindingsAtSite.  setText(c.getString(c.getColumnIndex(VisitModel.findsatsite)));
            ActionTaken.setText(c.getString(c.getColumnIndex(VisitModel.actiontaken)));
            String status=c.getString(c.getColumnIndex(VisitModel.status));
            Status.setText(status);
            StatusID = c.getInt(c.getColumnIndex(VisitModel.statusID));

            String pendingstatus=c.getString(c.getColumnIndex(VisitModel.pendingclas));
            if(pendingstatus!=null) {
                if(!pendingstatus.contains("null"))
                PendingStatus.setText(c.getString(c.getColumnIndex(VisitModel.pendingclas)));
            else {
                PendingStatus.setText("");
            }}
            PendingClassificationID = c.getInt(c.getColumnIndex(VisitModel.pendgclasid));
            String nextvisitdate=c.getString(c.getColumnIndex(VisitModel.nextvisitdate));
            Log.d("pending",pendingstatus);
            if(nextvisitdate!=null) {
             if(!nextvisitdate.contains("null")){
                 NextVisitDate.setText(c.getString(c.getColumnIndex(VisitModel.nextvisitdate)));
             }
            else {
                NextVisitDate.setText("");
            }}
            String cutoffdate=c.getString(c.getColumnIndex(VisitModel.cutofdate));
            if(cutoffdate!=null) {
                if (!cutoffdate.contains("null")) {
                    CutoffDate.setText(c.getString(c.getColumnIndex(VisitModel.cutofdate)));

                } else {
                    CutoffDate.setText("");
              }}
            String remarks=c.getString(c.getColumnIndex(VisitModel.remarks));
            if(remarks!=null) {
                Remarks.setText(remarks);
            }
            else {
                Remarks.setText("");
            }
            String callsilpno=c.getString(c.getColumnIndex(VisitModel.calslipno));
            if(callsilpno!=null) {
                if(!callsilpno.contains("null"))
                {
                CallSlipno.setText(callsilpno);
                }
            else {
                CallSlipno.setText("");
            }
            }
            filename = c.getString(c.getColumnIndex(VisitModel.docname));
            filepath = c.getString(c.getColumnIndex(VisitModel.docpath));
            WebID=c.getInt(c.getColumnIndex(VisitModel.webid));
            Attachment.setText(filename);
        }
    }

    @Override
    public void spareValue(int id, String text) {
       //  spareIssueAdapter.notifyDataSetChanged();
    }



    @Override
    public void Customerresults(int id, String text) {
             CustomerBranch.setText(text);
            SharedPreferenceManager.putInteger("customerbranchid",id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        spare_recyclerview.setAdapter(null);
        spare_issue.setAdapter(null);
        serialNumberDAO=null;
        spareDAO=null;
        getLoaderManager().destroyLoader(1);
        getLoaderManager().destroyLoader(2);
        getLoaderManager().destroyLoader(3);
        Runtime.getRuntime().gc();
    }
}




