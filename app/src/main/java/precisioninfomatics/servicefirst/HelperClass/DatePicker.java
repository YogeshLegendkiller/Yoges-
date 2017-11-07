package precisioninfomatics.servicefirst.HelperClass;

import android.app.DatePickerDialog;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import precisioninfomatics.servicefirst.Activities.Dashboard;

/**
 * Created by 4264 on 10-02-2017.
 */

public class DatePicker  {
    private Context mcontext;
    public DateValue dateValue;
    private  int flag;
  public DatePicker(Context context,int flag){
      this.mcontext=context;
      this.flag=flag;
  }

    public void displaydatepicker(){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog   datePickerDialog = new DatePickerDialog(mcontext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateValue.getDate(newDate,flag);
          //      ActivityDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
         datePickerDialog.show();
    }
}
