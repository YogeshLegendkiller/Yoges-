package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.SharedPreferences.SharedPreferenceManager;

/**
 * Created by 4264 on 15/04/2017.
 */

public class FCMTokeRegistration implements PostMethod {
    private PostMethodToServer postMethodToServer;

   public void SendTokenToServer(Context context){
        try {
            LoginDAO loginobj = new LoginDAO(context);
            int userID=loginobj.getUserID();
            String fcmToken = FirebaseInstanceId.getInstance().getToken();
            fcmUserDetail(context);
            JSONObject jsonobj=new JSONObject();
            jsonobj.put("UserId",userID);
            jsonobj.put("MuId",fcmToken);
            if(userID!=0 && fcmToken!=null) {
               postMethodToServer.execute(jsonobj.toString());
           }
       else {
               Log.e("useridnotvalid","useridnotvalid");
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }

    @Override
    public void PostDataToServer(String obj) {
        try {
            JSONObject jsonObject=new JSONObject(obj);
            int check=Integer.valueOf(jsonObject.getString("status"));
            if(check ==1 ||check==2){
                SharedPreferenceManager.putInteger("fcmcheck",1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void fcmUserDetail(Context mcontext) {
        postMethodToServer = new PostMethodToServer(URL.SF_URL+"/Appusersave",mcontext);
        postMethodToServer.getResponse = this;
    }
}
