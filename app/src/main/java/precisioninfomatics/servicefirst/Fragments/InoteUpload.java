package precisioninfomatics.servicefirst.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import precisioninfomatics.servicefirst.DAO.FileDAO;
import precisioninfomatics.servicefirst.HelperClass.FileChoosers;
import precisioninfomatics.servicefirst.Model.FileModel;
import precisioninfomatics.servicefirst.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 4264 on 24/02/2017.
 */

public class InoteUpload extends DialogFragment {
    private  Spinner dateformat;
    private String filepath;
    private Integer id,userid;
    private EditText attachment;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upload_inote, container, false);
         dateformat = (Spinner)v. findViewById(R.id.dateformat);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        attachment=(EditText)v.findViewById(R.id.attachment);
        Button upload=(Button)v.findViewById(R.id.upload);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.date_format, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateformat.setAdapter(adapter);
        Bundle bundle=getArguments();
        if(bundle!=null){
      //      inscode=bundle.getString("inscode");
            id=bundle.getInt("id");
            userid=bundle.getInt("userid");
        }
          attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/csv");
                startActivityForResult(Intent.createChooser(intent, "Open CSV"), 2);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = attachment.getText().toString();
                String filetype = filename.substring(filename.indexOf("."));
                Log.d("filetype",filetype);
                if(dateformat.getSelectedItem().toString().contentEquals("--Select--")){
                    Toast.makeText(getActivity(),"Please Select The Valid Date Format",Toast.LENGTH_SHORT).show();
                }
                else if(!filetype.contains("csv")){
                    Toast.makeText(getActivity(),"Please Choose CSV File Format",Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        new UploadFile(String.valueOf(userid),precisioninfomatics.servicefirst.HelperClass.URL.SF_URL + "/inoteupload", getActivity(), new File(filepath), String.valueOf(id), dateformat.getSelectedItem().toString()).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return v;
    }
    public class UploadFile extends AsyncTask<String, Integer, String> {

        private  String boundary,Url,Date,IncidentId,userid;
        private static final String LINE_FEED = "\r\n";
        private HttpURLConnection httpConn;
        private OutputStream outputStream;
        private PrintWriter writer;
        private ProgressDialog progressDialog;
        private Context mcontext;
        private PowerManager.WakeLock mWakeLock;
        private int totalSize;
        private File file;

        public UploadFile(String userID,String requestURL,Context context,File mfile,String IncidentID,String Date)
                throws IOException {
            this.mcontext=context;
            progressDialog = new ProgressDialog(context);
            this.file=mfile;
            this.userid=userID;
            this.Url=requestURL;
            this.IncidentId=IncidentID;
            this.Date=Date;
            progressDialog.setMessage("Uploading");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(true);
            // reference to instance to use inside listener
            final UploadFile uploadFile = this;
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    uploadFile.cancel(true);
                }
            });

        }
        @Override
        protected void onPreExecute() {
            PowerManager pm = (PowerManager) mcontext.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            totalSize = (int) file.length();
            progressDialog.show();
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(progress[0]);
        }
        @Override
        protected String doInBackground(String... strings) {
            boundary = "*****";
            URL url = null;
            StringBuffer response = null;
            try {
                url = new URL(Url);

                Log.e("URL", "URL : " + Url);
                httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setUseCaches(false);
                httpConn.setDoOutput(true); // indicates POST method
                httpConn.setDoInput(true);
                httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                outputStream = httpConn.getOutputStream();
                writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"),
                        true);

                //formfield userID
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + "userid" + "\"")
                        .append(LINE_FEED);
                writer.append("Content-Type: text/plain; charset=" + "UTF-8").append(
                        LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(String.valueOf(userid)).append(LINE_FEED);
                writer.flush();

                //formfield userID
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + "incidentid" + "\"")
                        .append(LINE_FEED);
                writer.append("Content-Type: text/plain; charset=" + "UTF-8").append(
                        LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(String.valueOf(IncidentId)).append(LINE_FEED);
                writer.flush();

                //formfield date
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + "date" + "\"")
                        .append(LINE_FEED);
                writer.append("Content-Type: text/plain; charset=" + "UTF-8").append(
                        LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(Date).append(LINE_FEED);
                writer.flush();
                //fileupload
                String fileName = file.getName();
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append(
                        "Content-Disposition: form-data; name=\"" + "uploaded_file"
                                + "\"; filename=\"" + fileName + "\"")
                        .append(LINE_FEED);
                writer.append(
                        "Content-Type: "
                                + URLConnection.guessContentTypeFromName(fileName))
                        .append(LINE_FEED);
                writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();
                int progress = 0;
                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    progress += bytesRead;
                    publishProgress(((progress*100)/totalSize));
                }
                outputStream.flush();
                inputStream.close();

                writer.append(LINE_FEED);
                writer.flush();
                response = new StringBuffer();

                writer.append(LINE_FEED).flush();
                writer.append("--" + boundary + "--").append(LINE_FEED);
                writer.close();

                // checks server's status code first
                int status = httpConn.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            httpConn.getInputStream()));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    httpConn.disconnect();
                } else {
                    throw new IOException("Server returned non-OK status: " + status);
                }


                //   return returntype;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.toString();

        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            progressDialog.dismiss();
            getDialog().dismiss();
            FileDAO fileDAO=new FileDAO(getActivity());
            fileDAO.DeleteFile(2,Integer.valueOf(IncidentId));
            FileModel fileModel=new FileModel();
            fileModel.setFileName(file.getName());
            fileModel.setFilePath(file.toString());
            fileModel.setIncidentID(Integer.valueOf(IncidentId));
            fileModel.setFileType(2);
            fileDAO.InsertOrUpdate(fileModel);
            Log.i("DownloadTask", result);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Uri uri = data.getData();
                filepath = FileChoosers.getPath(getActivity(), uri);
                String  filename =FileChoosers.getFileName(uri,getActivity());
                attachment.setText(filename);
                Log.d("file", filepath);
            }
        }
            super.onActivityResult(requestCode, resultCode, data);
    }

}