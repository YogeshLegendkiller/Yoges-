package precisioninfomatics.servicefirst.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import precisioninfomatics.servicefirst.DAO.SerialNumberDAO;
import precisioninfomatics.servicefirst.Fragments.SerialNumber;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.R;

public class MapSerialNumber extends AppCompatActivity implements SerialNumber.SerialnumValue {
 private EditText SerialNumber,ShipToSerialNumber;
    private   int IncidentID,VisitID,ChecklistLineID;
    android.support.v4.app.FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_serial_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SerialNumber =(EditText)findViewById(R.id.serialnumber);
        ShipToSerialNumber=(EditText)findViewById(R.id.shiptoserialno);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            IncidentID=bundle.getInt("incidentid");
            VisitID=bundle.getInt("visitid");
        }
         fm = getSupportFragmentManager();
        SerialNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precisioninfomatics.servicefirst.Fragments.SerialNumber serialNumber = new SerialNumber();
                Bundle bundle=new Bundle();
                bundle.putInt("incid",IncidentID);
                serialNumber.setArguments(bundle);
                serialNumber.setRetainInstance(true);
                serialNumber.show(fm, "fm");
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
                SerialNumberDAO serialNumberDAO=new SerialNumberDAO(this);
                SerialNumberMapModel serialNumberMapModel=new SerialNumberMapModel();
                serialNumberMapModel.setIncidentID(IncidentID);
                serialNumberMapModel.setVisitID(VisitID);
                serialNumberMapModel.setSerialNumber(SerialNumber.getText().toString());
                serialNumberMapModel.setShipToSerialNumber(ShipToSerialNumber.getText().toString());
                serialNumberMapModel.setSerialNumberID(ChecklistLineID);
               if(!serialNumberDAO.SerialNumberDuplicate(ChecklistLineID)){
                    serialNumberDAO.InsertSerialNumberMap(serialNumberMapModel);
                    Intent intent = new Intent(this, VisitUpdation.class);
                    setResult(RESULT_OK, intent);
                    finish();
               }
               else {
                   Toast.makeText(this,"Already Exist",Toast.LENGTH_SHORT).show();
               }


        }
        return super.onOptionsItemSelected(item);
    }

                @Override
    public void onResultReciever(String text, Integer id) {
        ShipToSerialNumber.setText(text);
                    ChecklistLineID=id;
        SerialNumber.setText(text);
    }
}
