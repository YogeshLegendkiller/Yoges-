package precisioninfomatics.servicefirst.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.Fragments.TransportModeFragment;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Networks.FileTravel;
import precisioninfomatics.servicefirst.Networks.TravelData;
import precisioninfomatics.servicefirst.R;

public class TravelEdit extends AppCompatActivity implements TransportModeFragment.Transportmoderesult {
    private EditText CheckIn, Checkout, TransportMode, Amount,FromLocation,ToLocation;
    private Integer ID,WebID,IncidentID,TransportModeID;
    private String CheckOut;
    private SimpleDateFormat dateFormat;
    private  Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CheckIn = findViewById(R.id.checkin);
        Checkout = findViewById(R.id.checkout);
       EditText Attachment = findViewById(R.id.attachment);
        TransportMode = findViewById(R.id.transportmode);
        FromLocation = findViewById(R.id.fromloc);
        ToLocation = findViewById(R.id.toloc);
        Amount = findViewById(R.id.amount);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Intent intent = getIntent();
        if (intent != null) {
            ID = intent.getIntExtra("id", 0);
            EditTravel(ID);
            Attachment.setVisibility(View.GONE);
            View bottomSheet = findViewById(R.id.intentbottomsheet);
        BottomSheetBehavior    bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            findViewById(R.id.atta_text).setVisibility(View.GONE);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Travel Updation");
        }
            //    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
       // ImageView cancel_bottomsheet = (ImageView) findViewById(R.id.cancel);
      /*  final boolean result = Utility.checkPermission(TravelEdit.this);
        cancel_bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        RelativeLayout file = (RelativeLayout) findViewById(R.id.file);
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result) {
                    userChoosenTask = "Choose from Library";
                    FileChoosers.galleryIntent(TravelEdit.this);
                }
            }
        });*/
        TransportMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                TransportModeFragment transportModeFragment = new TransportModeFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("flag",2);
                transportModeFragment.setArguments(bundle);
                transportModeFragment.setRetainInstance(true);
                transportModeFragment.show(fm, "fm");
            }
        });
        CheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(TravelEdit.this,1);
            }
        });
        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(TravelEdit.this,2);
            }
        });
     /*   RelativeLayout camera = (RelativeLayout) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result) {
                    userChoosenTask = "Take Photo";
                  FileChoosers.cameraIntent(TravelEdit.this);
                }
            }
        });
      /*  Attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  selectImage();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
   */ }
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
                if (Checkout.getText().toString().isEmpty() ) {
                    Toast.makeText(TravelEdit.this, "Please Checkout ", Toast.LENGTH_SHORT).show();
                } else if (Amount.getText().toString().isEmpty()) {
                    Toast.makeText(TravelEdit.this, "Please Fill The Amount", Toast.LENGTH_SHORT).show();
                } else if (TransportMode.getText().toString().isEmpty()) {
                    Toast.makeText(TravelEdit.this, "Please Select the Mode of Transport", Toast.LENGTH_SHORT).show();
                }
                else if(FromLocation.getText().toString().isEmpty()){
                    Toast.makeText(TravelEdit.this, "From Location is Missing", Toast.LENGTH_SHORT).show();

                }
                else if(ToLocation.getText().toString().isEmpty()){
                    Toast.makeText(TravelEdit.this, "To Location is Missing", Toast.LENGTH_SHORT).show();
                }
                else {
                    VisitModel visitobj = new VisitModel();
                    visitobj.setID(ID);
                    visitobj.setCheckOutDate(Checkout.getText().toString());
                    visitobj.setIncidentID(IncidentID);
                    visitobj.setCheckInDate(CheckIn.getText().toString());
                    visitobj.setTravelrVisitFlag(2);
                    visitobj.setAmount(Amount.getText().toString());
                    LoginDAO loginobj = new LoginDAO(this);
                    int userid = loginobj.getUserID();
                    visitobj.setLastmodifiedby(userid);
                    visitobj.setMaporOther(2);
                    visitobj.setIsCallCompleted(0);
                    visitobj.setTransportModeText(TransportMode.getText().toString());
                    visitobj.setTransportMode(TransportModeID);
                    visitobj.setLastModifiedat(dateFormat.format(new Date()));
                     visitobj.setIsSent(1);
                    visitobj.setTravelID(WebID);
                    visitobj.setStartAddress(FromLocation.getText().toString());
                    visitobj.setEndAddress(ToLocation.getText().toString());
                    visitobj.setKilometer("");

                    VisitDAO visitdao = new VisitDAO(TravelEdit.this);
                    visitdao.EditTravel(visitobj);
                    FileTravel fileTravel=new FileTravel();
                    fileTravel.PostFile(TravelEdit.this);
                    Intent intent = new Intent(TravelEdit.this, VisitView.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
        }
    private void EditTravel(int id) {
        VisitDAO visitDAO = new VisitDAO(TravelEdit.this);
        Cursor c = visitDAO.TravelUpdate(id);
        if (c.moveToFirst()) {
            CheckIn.setText(c.getString(c.getColumnIndex(VisitModel.checkin)));
            Checkout.setText(c.getString(c.getColumnIndex(VisitModel.checkout)));
            FromLocation.setText(c.getString(c.getColumnIndex(VisitModel.endadrs)));
            ToLocation.setText(c.getString(c.getColumnIndex(VisitModel.endadrs)));
            Amount.setText(c.getString(c.getColumnIndex(VisitModel.t_amnt)));
            WebID=c.getInt(c.getColumnIndex(VisitModel.travelwebid));
            IncidentID=c.getInt(c.getColumnIndex(VisitModel.incid));
            TransportMode.setText(c.getString(c.getColumnIndex(VisitModel.transportmode)));
            TransportModeID=c.getInt(c.getColumnIndex(VisitModel.transportmodeID));
          }
    }





  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if(data!=null){
            Uri uri = data.getData();
            docpath = FileChoosers.getPath(this, uri);
            docname = FileChoosers.getFileName(uri, this);
            Log.d("file", docpath);}
        } else if (requestCode == 1) {
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String uri=  preferences.getString("uri", "");
            if (uri != null) {
                docpath = FileChoosers.getPath(this, Uri.parse(uri));
                docname = FileChoosers.getFileName(Uri.parse(uri), this);
                Log.d("file", docpath);
                preferences.edit().remove("uri").apply();
            } else {
                Toast.makeText(this, "Camera Closed", Toast.LENGTH_SHORT).show();
            }
        }
        if (docpath != null) {
            File isexist = new File(docpath);
            if (isexist.exists()) {
                Attachment.setText(docname);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {
                Log.d("closed", "closed");
            }
        }
        else {
            Log.d("tryagain", "try");

        }
    }
*/
    public void showDateTimePicker(final Context context, final int flag) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.v("test", "The choosen one " + date.getTimeInMillis());
                        if(flag==1){
                                CheckOut=dateFormat.format(date.getTime());
                                 CheckIn.setText(CheckOut);
                            }
                        else {
                                CheckOut=dateFormat.format(date.getTime());
                                //     checkoutdate = dateFormat.parse(CheckOut);
                                // String checkout_view = dateview.format(checkoutdate);
                                Checkout.setText(CheckOut);
                            }

                          }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
    @Override
    public void onResultRecieve(int id, String text) {
        TransportModeID = id;
        TransportMode.setText(text);
    }
}