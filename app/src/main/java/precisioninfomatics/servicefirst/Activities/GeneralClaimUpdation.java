package precisioninfomatics.servicefirst.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.OnChipClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import precisioninfomatics.servicefirst.DAO.ClaimFieldDAO;
import precisioninfomatics.servicefirst.DAO.GeneralClaimDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.DatePicker;
import precisioninfomatics.servicefirst.HelperClass.DateValue;
import precisioninfomatics.servicefirst.HelperClass.FileChoosers;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.GeneralClaimDocument;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Networks.GeneralClaimFile;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

import static precisioninfomatics.servicefirst.Activities.Dashboard.RequestPermissionCode;

public class GeneralClaimUpdation extends AppCompatActivity implements DateValue,OnChipClickListener {
private EditText FromDate,ToDate,FromLoc,ToLoc;
    private BottomSheetBehavior bottomSheetBehavior;
    private String userChoosenTask,filepath, filename;
    private Integer incidentID,flag,id;
    private List<Chip> chipList;
    private GeneralClaimDAO generalClaimDAO;
    private  List<EditText> editexts;
    private ChipView chipDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalupdation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GeneralClaimUpdation.this, IncidentView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
    }

        generalClaimDAO=new GeneralClaimDAO(this);
        Intent intent=getIntent();
        chipList=new ArrayList<>();
        FromLoc= findViewById(R.id.fromloc);
        ToLoc= findViewById(R.id.toloc);
        FromDate= findViewById(R.id.from_date);
        ToDate= findViewById(R.id.to_date);
         View bottomSheet = findViewById(R.id.intentbottomsheet);
        editexts = new ArrayList<EditText>();
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        ImageView cancel_bottomsheet = findViewById(R.id.cancel);
        cancel_bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        chipDefault = findViewById(R.id.chipview);
        chipDefault.setChipLayoutRes(R.layout.chip_close);

        chipDefault.setOnChipClickListener(this);
        chipDefault.setChipBackgroundColor(Color.parseColor("#e3e3e3"));
        RelativeLayout Attachment= findViewById(R.id.attachment);

        RelativeLayout file= findViewById(R.id.file);
        file.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                userChoosenTask = "Choose from Library";
                userChoosenTask = "Choose from Library";
                if(Utility.checkPermission(GeneralClaimUpdation.this)){

                    FileChoosers.galleryIntent(GeneralClaimUpdation.this);
                }
                else {
                    Utility.requestPermission(GeneralClaimUpdation.this);
                }
            }

        });
        RelativeLayout camera= findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                userChoosenTask = "Take Photo";
                userChoosenTask = "Take Photo";
                if(Utility.checkPermission(GeneralClaimUpdation.this)){
                    FileChoosers.cameraIntent(GeneralClaimUpdation.this);
                }
                else {
                    Utility.requestPermission(GeneralClaimUpdation.this);
                }
            }


        });
        FromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayDatePicker(1);

            }
        });
        ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayDatePicker(2);

            }
        });
        Attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED );
            }
        });
        if(intent!=null){
            incidentID=intent.getIntExtra("incidentID",0);
            flag=intent.getIntExtra("flag",0);
            if(flag==2){
                id=intent.getIntExtra("id",0);
             //   VisitUpdate(id);
            }

        }
        DynamicFields();


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
                if (FromDate.getText().toString().isEmpty()) {
                    FromDate.setError("Please Fill the FromDate");
                } else if (ToDate.getText().toString().isEmpty()) {
                    ToDate.setError("Please Fill the FromDate");

                } else if (FromLoc.getText().toString().isEmpty()) {
                    FromLoc.setError("Please Fill the From Location");
                } else if (ToLoc.getText().toString().isEmpty()) {
                    ToLoc.setError("Please Fill the To Location");
                }
                else {
                    try {
                        Handler handler = new Handler();
                        GeneralClaimModel generalClaimModel=new GeneralClaimModel();
                        int sum=0;
                        List<ClaimFieldModel>list=new ArrayList<>();
                        for (int i=0;i<editexts.size();i++){
                            ClaimFieldModel claimFieldModel=new ClaimFieldModel();
                            if (editexts.get(i).getText().toString().isEmpty()){
                                 claimFieldModel.setClaimAmount(Integer.valueOf(String.valueOf(0)));
                        }
                        else {
                                sum=sum+Integer.valueOf(editexts.get(i).getText().toString());
                                claimFieldModel.setClaimAmount(Integer.valueOf(editexts.get(i).getText().toString()));
                            }
                         claimFieldModel.setClaimTypeID(editexts.get(i).getId());
                         claimFieldModel.setName(editexts.get(i).getTag().toString());
                         list.add(claimFieldModel);
                        }
                    Gson listtest=new Gson();
                    String cost=listtest.toJson(list);
                    generalClaimModel.setClaimCost(cost);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    generalClaimModel.setFromDate(ViewDateTimeFormat.Date(FromDate.getText().toString(),1));
                    generalClaimModel.setToDate(ViewDateTimeFormat.Date(ToDate.getText().toString(),1));
                    generalClaimModel.setFileName(filename);
                    generalClaimModel.setLastModifiedDateTime(dateFormat.format(new Date()));
                    LoginDAO loginobj = new LoginDAO(this);
                    generalClaimModel.setIncidentID(incidentID);
                    String name= SharedPreferenceManager.getServiceString("Name",null,this);
                    generalClaimModel.setCreatedBy(loginobj.getUserID());
                     generalClaimModel.setCreatedbyName(name);
                    generalClaimModel.setIsSent(1);
                    if(chipList.size()>0){
                        Gson gson=new Gson();
                        generalClaimModel.setIsFileExist(1);
                        String uploads=gson.toJson(chipList);
                        generalClaimModel.setDocs(uploads);
                        generalClaimModel.setSelectSliptoUpload(uploads);
                    }
                    else {
                        generalClaimModel.setIsFileExist(0);
                    }
                    generalClaimModel.setFromLoc(FromLoc.getText().toString());
                    generalClaimModel.setToLoc(ToLoc.getText().toString());
                    generalClaimModel.setTotal((double) sum);
                    generalClaimModel.setLastModifiedBy(loginobj.getUserID());
                    generalClaimModel.setCreatedAt(dateFormat.format(new Date()));
                    if(flag==1) {
                        generalClaimDAO.InsertGeneralClaim(generalClaimModel);
                    }
                    else {
                        generalClaimModel.setID(id);
                        generalClaimDAO.UpdateLocalGeneralClaim( generalClaimModel);
                    }
                    final boolean internetcheck = Utility.isNetworkAvailable(this);
                    if (internetcheck) {
                        GeneralClaimFile generalClaimFile=new GeneralClaimFile();
                        generalClaimFile.PostFile(this,incidentID);
                    }
                     final ProgressDialog progressDialog=Utility.showProgressDialog(this,"Please wait");
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Intent intent = new Intent(GeneralClaimUpdation.this, IncidentView.class);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }, 6000);
                }catch (Exception ex){
                        Toast.makeText(this, ex.toString(),Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();

                    }
                }
                }

        return super.onOptionsItemSelected(item);
    }

    public void DisplayDatePicker(int flag) {
        DatePicker datePicker = new DatePicker(GeneralClaimUpdation.this, flag);
        datePicker.dateValue = GeneralClaimUpdation.this;
        datePicker.displaydatepicker();
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
                            FileChoosers.cameraIntent(GeneralClaimUpdation.this);
                        else if (userChoosenTask.equals("Choose from Library"))
                            FileChoosers.galleryIntent(GeneralClaimUpdation.this);
                    }
                    else {
                        //    Toast.makeText(VisitUpdation.this,"Please Enable Some Permission To Access Camera or File ",Toast.LENGTH_LONG).show();
                      Utility.requestPermission(GeneralClaimUpdation.this);
                    }
                }

                break;
        }
    }

    @Override
    public void getDate(Calendar calendar, int flag) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        if(flag==1){
            FromDate.setText(dateFormatter.format(calendar.getTime()));
        }
        else if(flag==2){
            ToDate.setText(dateFormatter.format(calendar.getTime()));

        }

    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     //   if (resultCode == RESULT_OK) {
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
            if(requestCode==2&&data==null){
                Log.d("no file","no file");
            }
            else {
                File file=new File(filepath);
                if(file.exists()){
                   bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                   chipList.add(new GeneralClaimDocument(filepath,filename));
                   Log.d("size",Utility.ConvertListToString(chipList));
                   chipDefault.setChipList(chipList);
        }}
    }

    @Override
    public void onChipClick(Chip chip) {
        chipDefault.remove(chip);
    }


    /* public void VisitUpdate(int id){
                Cursor c = generalClaimDAO.GeneralClaimLUpdate(id);
                if (c.moveToFirst()) {
                      FromDate.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_FromDate)));
                      ToDate.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_ToDate)));
                      FromLoc.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_FromLoc)));
                      ToLoc.setText(c.getString(c.getColumnIndex(GeneralClaimModel.Gc_ToLoc)));
                      Ticket.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_Ticket)));
                      Conveyance.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_Conveyance)));
                      Lodge.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_Lodge)));
                      Meals.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_Meals)));
                      Telephone.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_Telephone)));
                      Entertainment.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_Enter)));
                      Others.setText(c.getString(c.getColumnIndex(GeneralClaimModel.GC_Otr)));
                    Type type = new TypeToken<ArrayList<GeneralClaimDocument>>() {
                    }.getType();
                    Gson gson = new Gson();
                    chipList = gson.fromJson(c.getString(c.getColumnIndex(GeneralClaimModel.GC_Filepath)), type);
                    chipDefault.setChipList(chipList);

                }
        }*/
   public static int convertPixelsToDp(float px, Context context){
       Resources resources = context.getResources();
       DisplayMetrics metrics = resources.getDisplayMetrics();
       return (int) (px / (metrics.densityDpi / 160f));
   }

   public void DynamicFields(){
       LinearLayout linearLayout= findViewById(R.id.l1);
       ClaimFieldDAO claimFieldDAO=new ClaimFieldDAO(this);
       List<ClaimFieldModel>list=claimFieldDAO.fieldlist();
       for(int i=0;i<list.size();i++){
           TextView textView = new TextView(this);
           int myMarginPx = (int) (getResources().getDimension(R.dimen.my_margin) / getResources().getDisplayMetrics().density);
           LinearLayout.LayoutParams textviewparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
           textviewparam.setMargins(myMarginPx, convertPixelsToDp(5,this), convertPixelsToDp(5,this) , 0);
           textView.setLayoutParams(textviewparam);
           textView.setTextColor(Color.parseColor("#666666"));
           textView.setTypeface(null, Typeface.BOLD);
           textView.setText(list.get(i).getName());
           EditText editText =new EditText(this);
           editexts.add(editText);
           editText.setInputType(InputType.TYPE_CLASS_NUMBER);
           editText.setId(list.get(i).getPairID());
           editText.setTextSize(14);
           editText.setTextColor(Color.parseColor("#666666"));
           editText.setPadding(20,editText.getPaddingTop(),editText.getPaddingRight(),editText.getPaddingBottom());
           editText.setTag(list.get(i).getName());
           editText.setBackgroundResource(R.color.white);
           View v = new View(this);
           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,convertPixelsToDp(5,this));
           lp.setMargins(0, convertPixelsToDp(5,this), 0, 0);
           v.setBackgroundColor(Color.parseColor("#e3e3e3"));
           v.setLayoutParams(lp);
           LinearLayout.LayoutParams editext_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
           editext_param.setMargins(0, convertPixelsToDp(10,this), 0, convertPixelsToDp(3,this));
           editText.setLayoutParams(editext_param);
           linearLayout.addView(textView);
           linearLayout.addView( editText);
           linearLayout.addView(v);
       }

   }
}