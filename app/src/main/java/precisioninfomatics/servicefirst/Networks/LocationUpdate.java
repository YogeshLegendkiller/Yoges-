package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;

/**
 * Created by 4264 on 11/05/2017.
 */

public class LocationUpdate  implements PostMethod {
    private Context mcontext;
     private PostMethodToServer postMethodToServer;

    public void LocationUpdate (Context context,double lat,double longt){
        this.mcontext=context;
        LocationDetail();
        JSONObject jsonObject=new JSONObject();
        LoginDAO loginobj = new LoginDAO(context);
        int userID=loginobj.getUserID();
        try {
            jsonObject.put("Latitude",lat);
            jsonObject.put("Longitude",longt);
            jsonObject.put("UserID",userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postMethodToServer.execute(jsonObject.toString());

    }

    @Override
    public void PostDataToServer(String obj) {
        postMethodToServer.cancel(true);
        postMethodToServer.getResponse=null;
        postMethodToServer=null;
    }
    private void LocationDetail() {
        postMethodToServer = new PostMethodToServer(URL.SF_URL+"/Appuserlocationsave",mcontext);
        postMethodToServer.getResponse = this;
    }
}

