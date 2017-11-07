package precisioninfomatics.servicefirst.Fragments;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import precisioninfomatics.servicefirst.Adapters.FileListAdapter;
import precisioninfomatics.servicefirst.Adapters.IncidentViewAdapter;
import precisioninfomatics.servicefirst.Adapters.OnItemClickListener;
import precisioninfomatics.servicefirst.CursorLoaders.IncidentViewLoaders;
import precisioninfomatics.servicefirst.DAO.FileDAO;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.FileModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 25-02-2017.
 */

public class IncidentView extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView recyclerView,file_recyclerview;
    private IncidentDAO incidentDAO;
    private FileDAO fileDAO;
    private int id;
    private CardView installationlist;
    private File downloadfile;
    private String incidentcode;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incident_view, container, false);
        recyclerView = view.findViewById(R.id.visit);
        file_recyclerview= view.findViewById(R.id.file_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager file_layout_manager = new LinearLayoutManager(getActivity());
        file_layout_manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        file_recyclerview.setLayoutManager(file_layout_manager);
        recyclerView.setNestedScrollingEnabled(false);
        Bundle bundle = getActivity().getIntent().getExtras();
        int is_installationcall = 0;
        int statusid=0;
        VisitDAO visitDAO=new VisitDAO(getActivity());
        if (bundle != null) {
            id = bundle.getInt("id");
            incidentcode=bundle.getString("inscode");
            is_installationcall = bundle.getInt("installationcall");
            statusid=bundle.getInt("status");
        }
        CardView cardView = view.findViewById(R.id.card);
        installationlist = view.findViewById(R.id.card1);
        RelativeLayout download = view.findViewById(R.id.downloadview);
        RelativeLayout upload = view.findViewById(R.id.uploadview);
        RelativeLayout print_i_note= view.findViewById(R.id.print_installation);
        RelativeLayout attachment= view.findViewById(R.id.attach);
        LoginDAO loginobj = new LoginDAO(getActivity());
        final int  userid = loginobj.getUserID();
        fileDAO=new FileDAO(getActivity());
        if (is_installationcall == 0) {
            cardView.setVisibility(View.GONE);
            installationlist.setVisibility(View.GONE);
        } else {
            if(statusid==8){
                download.setVisibility(View.GONE);
                upload.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                print_i_note.setVisibility(View.GONE);
                installationlist.setVisibility(View.GONE);
                attachment.setVisibility(View.GONE);
            }
            else {
                if (statusid != 7 && !(visitDAO.IsVisitCompleted(id, userid) >= 1)) {
                    download.setVisibility(View.VISIBLE);
                    upload.setVisibility(View.GONE);
                    print_i_note.setVisibility(View.GONE);
                    installationlist.setVisibility(View.VISIBLE);
                    attachment.setVisibility(View.GONE);
                } else {
                    download.setVisibility(View.VISIBLE);
                    upload.setVisibility(View.VISIBLE);
                    print_i_note.setVisibility(View.VISIBLE);
                    installationlist.setVisibility(View.VISIBLE);
                    attachment.setVisibility(View.VISIBLE);
                }
            }
        }
        incidentDAO = new IncidentDAO(getActivity());
        Boolean check = incidentDAO.incdentviewcheck(id);
        String url= precisioninfomatics.servicefirst.HelperClass.URL.SF_URL+"/getincidentview/"+id;
       if (!check) {
             new Incidentview(getActivity(),1).execute(url);
       }
        download.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                boolean result = Utility.checkPermission(getActivity());
                if (result) {
                    int checklistid=incidentDAO.getCheckListID(id);
                    downloadfile=Utility.GetFileName(getActivity(), "ServiceFirst/I_Note",id+ " "+"Installation Note.xlsx");
                    DownloadTask downloadTask = new DownloadTask(getActivity(), downloadfile, "Downloading",id,1);
                    downloadTask.execute(precisioninfomatics.servicefirst.HelperClass.URL.SF_URL+"/inotedown/"+checklistid);
                }
            }
        });
        final android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InoteUpload inoteUpload=new InoteUpload();
                Bundle bundle=new Bundle();
                bundle.putInt("id",id);
                bundle.putInt("userid",userid);
                inoteUpload.setArguments(bundle);
                inoteUpload.setRetainInstance(true);
                inoteUpload.show(fm, "fragment_name");
            }
        });
        print_i_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ShowDialog();
            }
        });
       attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Attachment attachment=new Attachment();
                Bundle bundle=new Bundle();
                bundle.putInt("id",id);
                attachment.setArguments(bundle);
                attachment.setRetainInstance(true);
                attachment.show(fm, "fragment_name");
            }
        });
        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().initLoader(1, null, this);
        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(final int ids, Bundle args) {
                return new IncidentViewLoaders(getActivity(), IncidentDAO.DB_SF_Incident, null, null, null, null,ids,id);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int checklistid=incidentDAO.getCheckListID(id);
                    DownloadTask downloadTask = new DownloadTask(getActivity(), downloadfile, "Downloading",id,1);
                    downloadTask.execute(precisioninfomatics.servicefirst.HelperClass.URL.SF_URL+"/inotedown/"+checklistid);
                } else {
                    Toast.makeText(getActivity(), "Please Enable the Permission to Download", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        FileListAdapter fileListAdapter=null;
        switch (loader.getId()) {
            case 0:
                IncidentViewAdapter incidentViewAdapter = new IncidentViewAdapter( data);
                recyclerView.setAdapter(incidentViewAdapter);
                break;
                case 1:
                fileListAdapter=new FileListAdapter(data);
                file_recyclerview.setAdapter(fileListAdapter);
                break;
        }
        int count=fileDAO.FileCountCheck(id);
        if(count>0){
            installationlist.setVisibility(View.VISIBLE);
        }
        else {
            installationlist.setVisibility(View.GONE);
        }
        if(fileListAdapter!=null){
        fileListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Cursor cursor) {
                    File file = new File(cursor.getString(cursor.getColumnIndex(FileModel.Filepath)));
                    openFile(file);
             }
          });
        }
    }
    public void openFile(File url) {

       Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(getContext(),
                    "com.precisioninfomatics.servicefirst.provider",
                   url);
           }else{
            uri = Uri.fromFile(url);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
       if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        }  else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        }  else {
             intent.setDataAndType(uri, "text/csv");
        }
        try {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getActivity(),"No Application is available to open this file",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ondestroyview","ondestroyview");
        recyclerView.setAdapter(null);
    }


    public static class DownloadTask extends AsyncTask<String, Integer, String> {
        private ProgressDialog mPDialog;
        private Context mContext;
        private PowerManager.WakeLock mWakeLock;
        private File mTargetFile;
        private Integer IncidentID,FileType;
        public DownloadTask(Context context, File targetFile, String dialogMessage,int incid,int flag) {
            this.mContext = context;
            this.FileType=flag;
            this.mTargetFile = targetFile;
            mPDialog = new ProgressDialog(context);
            this.IncidentID=incid;
            mPDialog.setMessage(dialogMessage);
            mPDialog.setIndeterminate(true);
            mPDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mPDialog.setCancelable(true);
            // reference to instance to use inside listener
            final DownloadTask me = this;
            mPDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    me.cancel(true);
                }
            });
            Log.i("DownloadTask", "Constructor done");
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                Log.i("DownloadTask", "Response " + connection.getResponseCode());


                int fileLength = connection.getContentLength();
               if(fileLength>0){
                   FileDAO fileDAO=new FileDAO(mContext);
                   fileDAO.DeleteFile(FileType,IncidentID);

               }
                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(mTargetFile, false);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        Log.i("DownloadTask", "Cancelled");
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                        getClass().getName());
                mWakeLock.acquire();
            }
             mPDialog.show();
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mPDialog.setIndeterminate(false);
            mPDialog.setMax(100);
            mPDialog.setProgress(progress[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("DownloadTask", "Work Done! PostExecute");
            mWakeLock.release();
            mPDialog.dismiss();
            if (result != null) {
                Toast.makeText(mContext, "Download error: " + result, Toast.LENGTH_LONG).show();
            }
            else{
               FileDAO fileDAO=new FileDAO(mContext);
               FileModel fileModel=new FileModel();
               fileModel.setFileName(mTargetFile.getName());
               fileModel.setFilePath(mTargetFile.toString());
               fileModel.setIncidentID(IncidentID);
               fileModel.setFileType(FileType);
               fileDAO.InsertOrUpdate(fileModel);
               Toast.makeText(mContext, "File Downloaded", Toast.LENGTH_SHORT).show();
        }
    }}
    public  class Incidentview extends AsyncTask<String, Void, String> {
        private static final String REQUEST_METHOD = "GET";
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECTION_TIMEOUT = 15000;
        private ProgressDialog pd;
         private Context context;
        private Integer flag;
        private Incidentview(Context c,int flag) {
            this.context=c;
            this.flag=flag;
            pd=new ProgressDialog(context);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            if(flag==1){
            pd.show();
            }
        }
        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL url = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
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
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            pd.dismiss();
            if (value != null) {
                Log.d("value", value);
                Gson gson = new Gson();
                IncidentViewModel incidentobj = gson.fromJson(value, IncidentViewModel.class);
                if(incidentobj!=null){
                IncidentDAO incidentDAO = new IncidentDAO(getActivity());
                incidentDAO.InsertOrUpdateIncView(incidentobj);
            }
        }
            else {
                Toast.makeText(context,"Please Check Your Network",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void ShowDialog(){
        CustomerBranchDialogFragment customerBranch = new CustomerBranchDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("incid",id);
        bundle.putString("inscode",incidentcode);
        customerBranch.setArguments(bundle);
        customerBranch.setRetainInstance(true);
        customerBranch.show(getFragmentManager(), "fm");
    }
}
