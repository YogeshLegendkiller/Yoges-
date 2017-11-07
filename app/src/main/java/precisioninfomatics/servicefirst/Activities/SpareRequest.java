package precisioninfomatics.servicefirst.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.OnChipClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DAO.SpareDAO;
import precisioninfomatics.servicefirst.Fragments.SpareStatus;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.SuggestionModel;
import precisioninfomatics.servicefirst.Networks.FileUpload;
import precisioninfomatics.servicefirst.R;

public class SpareRequest extends AppCompatActivity implements OnChipClickListener,SpareStatus.SpareStatusListener{
    private EditText Remarks, DefectiveSerialno,status,PartSpecification;
    private CheckBox advancereturn, samepart;
    private String BrandName,categoryName,specification,modelName,StatusText;
    private Integer PartID,id, VisitwebID,WebID, flag,incidentid,statusID,VisitPrimaryID;
    private SpareDAO spareDAO;
      private AutoCompleteTextView Partno;
    private List<Chip> chipList;
    private ChipView chipDefault;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_request);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Remarks = findViewById(R.id.remarks);
        chipList=new ArrayList<>();
        DefectiveSerialno = findViewById(R.id.defective_serial_no);
        Partno = findViewById(R.id.partno);
        samepart = findViewById(R.id.samepart);
        PartSpecification= findViewById(R.id.partspecification);
        samepart.setChecked(false);
        chipDefault = findViewById(R.id.chipview);
        chipDefault.setChipLayoutRes(R.layout.chip_close);
        chipDefault.setOnChipClickListener(this);
        chipDefault.setChipBackgroundColor(Color.parseColor("#e3e3e3"));
        advancereturn = findViewById(R.id.advance_return);
        spareDAO = new SpareDAO(SpareRequest.this);
  //      suggestionAdapter=new SuggestionAdapter(this);
   //     Partno.setAdapter(suggestionAdapter);
        Partno.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(s.length()>2) {
                  new SuggestionList().execute(s.toString());

                 }
            }
        });
        Partno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SuggestionModel suggestionModel= (SuggestionModel) adapterView.getAdapter().getItem(i);
                String partno=suggestionModel.getText();
                Partno.setVisibility(View.INVISIBLE);
                chipList.add(new SuggestionModel(partno));
                chipDefault.setChipList(chipList);
                PartID=suggestionModel.getValue();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SpareRequest.this, VisitUpdation.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });}
        status = findViewById(R.id.status);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            flag = bundle.getInt("flag");
            if (flag == 1) {
                VisitPrimaryID = bundle.getInt("visitprimaryID");
                VisitwebID = bundle.getInt("webid");
                incidentid=bundle.getInt("incidentid");
            } else {
                id = bundle.getInt("id");
                WebID = bundle.getInt("webid");
                SpareUpdate(id);
            }
        }

       final  android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
       status.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SpareStatus spareStatus = new SpareStatus();
               spareStatus.setRetainInstance(true);
               Bundle bundle = new Bundle();
               bundle.putInt("sequence", 1);
               bundle.putInt("flag", 1);
               spareStatus.setArguments(bundle);
               spareStatus.show(fm, "fragment_name");

           }
       });
        samepart.setClickable(false);
        advancereturn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    samepart.setClickable(true);
                } else {
                    samepart.setClickable(false);
                    samepart.setChecked(false);
                }
            }
        });
        getSupportActionBar().setTitle("Part Request");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (flag == 1) {
            inflater.inflate(R.menu.save, menu);

        } else {
            inflater.inflate(R.menu.save_delete, menu);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if (Partno.getText().toString().isEmpty() ) {
                    Partno.setError("Please Select The Partno");
                } else if (status.getText().toString().isEmpty()) {
                    Toast.makeText(SpareRequest.this, "Please select the Part Status", Toast.LENGTH_SHORT).show();
                }
                else if(advancereturn.isChecked()&&DefectiveSerialno.getText().toString().isEmpty()){
                    Toast.makeText(SpareRequest.this, "Please Fill The Defective Serial Number", Toast.LENGTH_SHORT).show();

                }
                else {
                    SpareModel spareModel = new SpareModel();
                    spareModel.setPartno(Partno.getText().toString());
                    spareModel.setPartID(PartID);
                    spareModel.setIncidentID(incidentid);
                    spareModel.setRemarks(Remarks.getText().toString());
                    spareModel.setIssue("No");
                    spareModel.setVisitPrimaryID(VisitPrimaryID);
                    spareModel.setVisitWebID(VisitwebID);
                    spareModel.setBrandName(BrandName);
                    spareModel.setRequestedPartSpecification(PartSpecification.getText().toString());
                    spareModel.setModelName(modelName);
                    spareModel.setCategoryName(categoryName);
                    spareModel.setPartSpecification(specification);
                    spareModel.setPartStatus(StatusText);
                    spareModel.setStatusID(statusID);
                    if (advancereturn.isChecked()) {
                        spareModel.setAdvanceReturn(1);
                    } else {
                        spareModel.setAdvanceReturn(0);
                    }
                    if (samepart.isChecked()) {
                        spareModel.setSamePart(1);
                    } else {
                        spareModel.setSamePart(0);
                    }
                    spareModel.setEditFlag(1);
                    spareModel.setDefectiveSerialno(DefectiveSerialno.getText().toString());
                    if(flag==1){
                        spareModel.setUpdateID(2);
                        spareDAO.Insert(spareModel);
                    }
                    else {
                        spareModel.setID(id);
                        spareModel.setWebID(WebID);
                        spareDAO.Update(spareModel);
                    }
                    Intent intent = new Intent(this, VisitUpdation.class);
               //     intent.putExtra("spareobj", spareModel);
                    setResult(RESULT_OK, intent);
                    finish();

                }
                break;
            case R.id.delete:


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                alertDialog.setTitle("Confirm Delete...");

                alertDialog.setMessage("Are you sure you want delete this?");

                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        SpareModel spareModel=new SpareModel();
                        spareModel.setEditFlag(2);
                        spareModel.setIncidentID(incidentid);
                        spareModel.setWebID(WebID);
                        spareModel.setID(id);
                        spareDAO.DeleteUpdate(spareModel);
                        Log.d("id",String.valueOf(spareDAO.getCount(incidentid)));
                        Intent intent = new Intent(SpareRequest.this, VisitUpdation.class);
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



    public void SpareUpdate(final int id) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = spareDAO.SpareUpdate(id);
                cursor.moveToFirst();
                Partno.setText(cursor.getString(cursor.getColumnIndex(SpareModel.partno)));
                Remarks.setText(cursor.getString(cursor.getColumnIndex(SpareModel.remarks)));
                int advancereturn_ = cursor.getInt(cursor.getColumnIndex(SpareModel.advreturn));
                if (advancereturn_ == 1) {
                    advancereturn.setChecked(true);
                }
                StatusText=cursor.getString(cursor.getColumnIndex(SpareModel.partstatus));
                status.setText(StatusText);
                statusID=cursor.getInt(cursor.getColumnIndex(SpareModel.statusid));
                int samepart_ = cursor.getInt(cursor.getColumnIndex(SpareModel.samepart));
                if (samepart_ == 1) {
                    samepart.setChecked(true);
                }
                VisitwebID=cursor.getInt(cursor.getColumnIndex(SpareModel.visitwebid));
                VisitPrimaryID=cursor.getInt(cursor.getColumnIndex(SpareModel.visitprimaryID));
                BrandName=cursor.getString(cursor.getColumnIndex(SpareModel.brandname));
                modelName=cursor.getString(cursor.getColumnIndex(SpareModel.modelname));
                categoryName=cursor.getString(cursor.getColumnIndex(SpareModel.catname));
                if(cursor.getString(cursor.getColumnIndex(SpareModel.requestedspec))==null){
                    PartSpecification.setText("");
                }
                else {
                    PartSpecification.setText(cursor.getString(cursor.getColumnIndex(SpareModel.requestedspec)));

                }
                specification=cursor.getString(cursor.getColumnIndex(SpareModel.specification));
              //  partid=cursor.getInt(cursor.getColumnIndex(SpareModel.partid));
                incidentid=cursor.getInt(cursor.getColumnIndex(SpareModel.incid));
                DefectiveSerialno.setText(cursor.getString(cursor.getColumnIndex(SpareModel.defectiveserialno)));
            }
        });
    }

  /*  @Override
    public void onReceive(int id, String text, String Brandname, String ModelName, String Category, String Specification,int webID) {
        Partno.setText(text);
        partid=webID;
        BrandName=Brandname;
        modelName=ModelName;
        specification=Specification;
        categoryName=Category;
    }
*/
    @Override
    public void spareValue(int id, String text) {
        StatusText=text;
        status.setText(text);
        statusID=id;
    }

    @Override
    public void onChipClick(Chip chip) {
        chipDefault.remove(chip);
        Partno.setVisibility(View.VISIBLE);
        Partno.setText("");
    }
    @SuppressLint("StaticFieldLeak")
    private   class SuggestionList extends AsyncTask<String,Void,String>{
        String  jsonResponse = null;


        @Override
        protected String doInBackground(String... strings) {
            try {
                FileUpload multipart = new FileUpload(precisioninfomatics.servicefirst.HelperClass.URL.SF_URL +"/GetSuggestedPartNo", "UTF-8");
                Log.d("value",strings[0]);
                multipart.addFormField("PartNo",strings[0]);
                jsonResponse = multipart.finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
           return jsonResponse;
        }


        @Override
        protected void onPostExecute(String s) {
            if(s!=null) {
                try {
                    Log.d("response",s);
                    ArrayList<SuggestionModel> descriptionList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("Results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjects = jsonArray.getJSONObject(i);
                        SuggestionModel suggestionModel = new SuggestionModel();
                        suggestionModel.setText(jsonObjects.getString("text"));
                        suggestionModel.setValue(jsonObjects.getInt("id"));
                        descriptionList.add(suggestionModel);
                    }
                    setAdapter(descriptionList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}

    }
    public  void setAdapter(ArrayList<SuggestionModel> descriptionList){
        ArrayAdapter<SuggestionModel> adapter = new ArrayAdapter<SuggestionModel>(
                this, android.R.layout.simple_dropdown_item_1line, descriptionList);
        Partno.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}