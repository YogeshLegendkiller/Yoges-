package precisioninfomatics.servicefirst.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.LoginModel;
import precisioninfomatics.servicefirst.Networks.FCMTokeRegistration;
import precisioninfomatics.servicefirst.R;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

import static precisioninfomatics.servicefirst.HelperClass.URL.SF_FotoURL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

 private EditText UserID,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserID= findViewById(R.id.employeeID);
        Password= findViewById(R.id.password);
        TextView textView= findViewById(R.id.text);
        String first = "Servic";
        String next = "<font color='#FFB236'>e</font>";
        String second="Firs";
        String second_next="<font color='#FFB236'>t</font>";
        textView.setText(Html.fromHtml(first + next + " "+second +second_next));
        Button  btn_signIn= findViewById(R.id.btn);
        Boolean on_time_login=SharedPreferenceManager.getServiceBoolean("flag",false,this);
        if(on_time_login){
            Intent intent = new Intent(LoginActivity.this,VehicleRegistration.class);
            startActivity(intent);
        }
        btn_signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                 boolean internetcheck = Utility.isNetworkAvailable(LoginActivity.this);
                if (internetcheck) {
                    if (UserID.getText().toString().isEmpty() || UserID.getText().toString().matches(" ")) {
                        UserID.setError("Please Enter EmployeeCode");
                    } else if (Password.getText().toString().isEmpty() || Password.getText().toString().matches(" ")) {
                        Password.setError("Please Enter Password");
                    } else {
                        JSONObject jsonobj = new JSONObject();
                        try {
                            jsonobj.put("EmployeeCode", UserID.getText().toString());
                            jsonobj.put("Password", Password.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Loginasync loging = new Loginasync(LoginActivity.this);
                        loging.execute(jsonobj.toString());
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please Check Your Network Connectivity", Toast.LENGTH_SHORT).show();
                }
            }});
    }
    public static  class  Loginasync extends AsyncTask<String,Void,String >{

        private String JsonResponse;
        private Context context;
        private ProgressDialog pd;
        private Loginasync(Context c) {
        this.context=c;
            pd=new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Signing In....");
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection=null;
            String  jsonObject= strings[0];
            byte[] post = jsonObject.getBytes();
            int request_post = post.length;
            BufferedReader reader = null;
            try {
                Thread.sleep(2000);
                //precisioninfomatics.servicefirst.HelperClass.URL.SF_URL+"/login"
              URL url = new URL(SF_FotoURL+"/api/login");
              // URL url=new URL("http://172.16.6.187/sf/api/login");
               // URL url=new URL("http://192.168.137.1/sf/api/login");
              //  URL url=new URL("http://10.225.251.147/sf/api/login");
             //   URL url = new URL(precisioninfomatics.servicefirst.HelperClass.URL.SF_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setRequestProperty("Content-Length", Integer.toString(request_post));
                urlConnection.setDoOutput(true);
                urlConnection.setConnectTimeout(50000);
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(jsonObject);
                writer.close();
               int  Server_response = urlConnection.getResponseCode();
                if (Server_response >= 400 && Server_response <= 499) {
                    Log.e("", "HTTPx Response: " + Server_response + " - " + urlConnection.getResponseMessage());
                }
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    return null;
                }
                JsonResponse = buffer.toString();
                Log.i("response", JsonResponse);
                return JsonResponse;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("", "Error closing stream", e);
                    }
                }
            }
            return JsonResponse;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
         //   Log.d("userdetails",s);
            pd.dismiss();
            if (s != null) {
                LoginModel loginobjs = gson.fromJson(s, LoginModel.class);
                if (loginobjs.getUserID() != 0 ) {
                    LoginDAO loginobj = new LoginDAO(context);
                    loginobj.InsertLoginDetails(loginobjs);
                    SharedPreferenceManager.puString("Name",loginobjs.getEmployeeName());
                    SharedPreferenceManager.puString("Role",loginobjs.getRoleName());
                    SharedPreferenceManager.putBoolean("flag",true);
                    FCMTokeRegistration fcmTokeRegistration=new FCMTokeRegistration();
                    fcmTokeRegistration.SendTokenToServer(context);
                    Intent intent = new Intent(context, VehicleRegistration.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Please check your login credentials", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
