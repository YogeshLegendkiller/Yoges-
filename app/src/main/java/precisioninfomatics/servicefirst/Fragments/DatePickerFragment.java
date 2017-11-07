package precisioninfomatics.servicefirst.Fragments;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import precisioninfomatics.servicefirst.Adapters.FilterAdapter;
import precisioninfomatics.servicefirst.Adapters.SerialNumberMapAdapter;
import precisioninfomatics.servicefirst.DAO.ChartDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.ChartModel;
import precisioninfomatics.servicefirst.Networks.IncidentSummaryData;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 08/07/2017.
 */

public class DatePickerFragment extends DialogFragment{
    private DatePickerFragment.OnCompleteListener mListener;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_recyclerview, container, false);
        RecyclerView  mRecyclerView = v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
         Toolbar toolbar = v. findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        if (toolbar!=null) {
            toolbar.setTitle("Filter");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        }
         final ArrayList<String>datelist=new ArrayList<>();
         datelist.add("Today");
         datelist.add("Yesterday");
         datelist.add("Last 7 Days");
         datelist.add("Last 30 Days");
         datelist.add("This Month");
         datelist.add("Last Month");
         datelist.add("This Year");
         datelist.add("Last Year");
         FilterAdapter filterAdapter=new FilterAdapter(datelist);
         mRecyclerView.setAdapter(filterAdapter);
         filterAdapter. setOnItemClickListener(new SerialNumberMapAdapter.OnItemClickListener() {
             @Override
             public void onItemClicked(int position) {
                 String s=datelist.get(position);
                 Calendar cal = Calendar.getInstance();
                 String fromdate=null;
                 String todate=null;
                 Date date = new Date();
                  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                 switch (s) {
                     case "Today":
                         cal.set(Calendar.HOUR_OF_DAY, 0);
                         cal.set(Calendar.MINUTE, 0);
                         cal.set(Calendar.SECOND, 0);
                         cal.set(Calendar.MILLISECOND, 0);
                         fromdate = dateFormat.format(cal.getTime());
                         cal.set(Calendar.HOUR_OF_DAY, 23);
                         cal.set(Calendar.MINUTE, 59);
                         cal.set(Calendar.SECOND, 59);
                         cal.set(Calendar.MILLISECOND, 999);
                         todate = dateFormat.format(cal.getTime());
                         break;
                     case "Yesterday":
                         cal.add(Calendar.DATE, -1);
                         cal.set(Calendar.HOUR_OF_DAY, 0);
                         cal.set(Calendar.MINUTE, 0);
                         cal.set(Calendar.SECOND, 0);
                         cal.set(Calendar.MILLISECOND, 0);
                         fromdate = dateFormat.format(cal.getTime());
                         cal.set(Calendar.HOUR_OF_DAY, 23);
                         cal.set(Calendar.MINUTE, 59);
                         cal.set(Calendar.SECOND, 59);
                         cal.set(Calendar.MILLISECOND, 999);
                         todate = dateFormat.format(cal.getTime());
                         break;
                     case "Last 7 Days":
                         cal.add(Calendar.DAY_OF_YEAR, -6);
                         cal.set(Calendar.HOUR_OF_DAY, 0);
                         cal.set(Calendar.MINUTE, 0);
                         cal.set(Calendar.SECOND, 0);
                         cal.set(Calendar.MILLISECOND, 0);
                         fromdate = dateFormat.format(cal.getTime());
                         cal.set(Calendar.HOUR_OF_DAY, 23);
                         cal.set(Calendar.MINUTE, 59);
                         cal.set(Calendar.SECOND, 59);
                         cal.set(Calendar.MILLISECOND, 999);
                         todate = dateFormat.format(date);
                          break;
                     case "Last 30 Days":
                         cal.add(Calendar.DATE, -29);
                         cal.set(Calendar.HOUR_OF_DAY, 0);
                         cal.set(Calendar.MINUTE, 0);
                         cal.set(Calendar.SECOND, 0);
                         cal.set(Calendar.MILLISECOND, 0);
                         fromdate = dateFormat.format(cal.getTime());
                         cal.set(Calendar.HOUR_OF_DAY, 23);
                         cal.set(Calendar.MINUTE, 59);
                         cal.set(Calendar.SECOND, 59);
                         cal.set(Calendar.MILLISECOND, 999);
                         todate = dateFormat.format(date);
                         break;
                     case "This Month":
                   //      cal.add(Calendar.MONTH, 1);
                         cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                         cal.set(Calendar.HOUR_OF_DAY, 0);
                         cal.set(Calendar.MINUTE, 0);
                         cal.set(Calendar.SECOND, 0);
                         cal.set(Calendar.MILLISECOND, 0);
                         Date nextMonthFirstDay = cal.getTime();
                         cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                         cal.set(Calendar.HOUR_OF_DAY, 23);
                         cal.set(Calendar.MINUTE, 59);
                         cal.set(Calendar.SECOND, 59);
                         cal.set(Calendar.MILLISECOND, 999);
                         Date nextMonthLastDay = cal.getTime();
                         fromdate=dateFormat.format(nextMonthFirstDay);
                         todate=dateFormat.format(nextMonthLastDay);
                         break;
                     case "Last Month":
                         cal.set(Calendar.DATE, 1);
                         cal.add(Calendar.DAY_OF_MONTH, -1);
                         cal.set(Calendar.HOUR_OF_DAY, 23);
                         cal.set(Calendar.MINUTE, 59);
                         cal.set(Calendar.SECOND, 59);
                         cal.set(Calendar.MILLISECOND, 999);
                         Date lastDateOfPreviousMonth = cal.getTime();
                         cal.set(Calendar.DATE, 1);
                         cal.set(Calendar.HOUR_OF_DAY, 0);
                         cal.set(Calendar.MINUTE, 0);
                         cal.set(Calendar.SECOND, 0);
                         cal.set(Calendar.MILLISECOND, 0);
                         Date firstDateOfPreviousMonth = cal.getTime();

                         fromdate=dateFormat.format(firstDateOfPreviousMonth);
                         todate=dateFormat.format(lastDateOfPreviousMonth);
                         break;
                     case "This Year":
                         cal.set(Calendar.DAY_OF_YEAR, 1);
                         cal.set(Calendar.MONTH,0);
                         cal.set(Calendar.HOUR_OF_DAY, 0);
                         cal.set(Calendar.MINUTE, 0);
                         cal.set(Calendar.SECOND, 0);
                         cal.set(Calendar.MILLISECOND, 0);
                         Date yearStartDate = cal.getTime();
                         fromdate=dateFormat.format(yearStartDate);
                         cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
                         cal.set(Calendar.HOUR_OF_DAY, 23);
                         cal.set(Calendar.MINUTE, 59);
                         cal.set(Calendar.SECOND, 59);
                         cal.set(Calendar.MILLISECOND, 999);
                         Date yearEndDate = cal.getTime();
                         todate=dateFormat.format(yearEndDate);
                         break;
                     case "Last Year":
                         cal.add(Calendar.YEAR, -1);
                         cal.set(Calendar.DAY_OF_YEAR,1);
                         cal.set(Calendar.MONTH,0);
                         cal.set(Calendar.HOUR_OF_DAY, 0);
                         cal.set(Calendar.MINUTE, 0);
                         cal.set(Calendar.SECOND, 0);
                         cal.set(Calendar.MILLISECOND, 0);
                         Date lastyearstartdate = cal.getTime();
                         cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
                         fromdate=dateFormat.format(lastyearstartdate);
                         Date lastyearEndDate = cal.getTime();
                        todate=dateFormat.format(lastyearEndDate);
                         break;
                 }
                 Log.d("date", fromdate);
                 Log.d("date", todate);
            //   new PieData(getActivity(),fromdate,todate);
                 new IncidentSummaryData(getActivity(),fromdate,todate);
                 LoginDAO loginobj=new LoginDAO(getActivity());
                 int   userid=loginobj.getUserID();
                 String url = URL.SF_URL + "/dash/2/"+userid+"/"+fromdate+"/"+todate;
              //   Log.d("url", url);
                 new PieData(getActivity()).execute(url);
              //   mListener.onComplete();

             /*)    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                 Calendar cal = Calendar.getInstance();
                 cal.getActualMaximum(Calendar.DAY_OF_MONTH);
              String ss=   dateFormat.format(cal.getTime());

               */

                 /*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                 Calendar calendar = Calendar.getInstance();
                 calendar.add(Calendar.MONTH, 1);
                 calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                 Date nextMonthFirstDay = calendar.getTime();
                 calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                 Date nextMonthLastDay = calendar.getTime();*/

            /*
                 Calendar aCalendar = Calendar.getInstance();
                 aCalendar.set(Calendar.DATE, 1);
                 aCalendar.add(Calendar.DAY_OF_MONTH, -1);
                 Date lastDateOfPreviousMonth = aCalendar.getTime();
                 aCalendar.set(Calendar.DATE, 1);
                 Date firstDateOfPreviousMonth = aCalendar.getTime();


 */           // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
               /*

                 cal.set(Calendar.DAY_OF_YEAR, 1);
                 Date yearStartDate = cal.getTime();

                 cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
                 Date yearEndDate = cal.getTime();*/
            //     Calendar cal = Calendar.getInstance();
              /*   cal.add(Calendar.YEAR, -1);
                 cal.set(Calendar.DAY_OF_YEAR,1);
                 Date yearStartDate = cal.getTime();
                 cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
                 String ss=dateFormat.format(yearStartDate);
                 Date yearEndDate = cal.getTime();
                 String ll=dateFormat.format(yearEndDate);
                 Log.d("date",ss);
                 Log.d("date",ll);
*/
             }
         });
            return v;
    }


    public  interface OnCompleteListener {
        void onComplete();
    }

    public void onAttach(Context context) {
        Activity activity=null;
        super.onAttach(context);
        try {
            activity = (Activity) context;
            mListener = (DatePickerFragment.OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public class PieData extends AsyncTask<String, Void, String> {
        private static final String REQUEST_METHOD = "GET";
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECTION_TIMEOUT = 15000;
        private ProgressDialog pd;
        private Context context;
        private Integer flag;

        private PieData(Context c) {
            this.context = c;
            //    this.flag=flag;
            pd = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
        //    if (flag == 1) {
                pd.show();
         //   }
        }

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result;
            String inputLine;

            try {
                //Create a URL object holding our url
                java.net.URL url = new java.net.URL(stringUrl);

                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        url.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String value) {
         //   super.onPostExecute(value);
            pd.dismiss();
            if(value!=null){
            ChartDAO chartDAO = new ChartDAO(getActivity());
            chartDAO.PieDelete();
            int count = chartDAO.getProfilesCount();
            Log.d("count", String.valueOf(count));
            try {
                JSONArray jsonArray = new JSONArray(value);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ChartModel chartModel = new ChartModel();
                    chartModel.setLabel(jsonObject.getString("label"));
                    chartModel.setData(jsonObject.getInt("data"));
                    chartDAO.PieInsert(chartModel);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }}
            else {
              Toast.makeText(context,"Please Try Again Later",Toast.LENGTH_SHORT).show();
          }
            mListener.onComplete();
            if (getDialog() != null && getDialog().isShowing()) {
                getDialog().dismiss();
            }
        }
    }

}

