package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Networks.PostMethod;
import precisioninfomatics.servicefirst.Networks.PostMethodToServer;

/**
 * Created by 4264 on 11/04/2017.
 */

public class TravelData implements PostMethod {
    private Context mcontext;
    private VisitDAO visitDAO;
    private PostMethodToServer postMethodToServer;

    public void PostTransportData (Context context){
        this.mcontext=context;
        visitDAO=new VisitDAO(context);
        int isExsit=visitDAO.IsTravelExist();
        if(isExsit>0){
            visitDetails();
            //    Gson gson=new Gson();
            //     String vehicledata=gson.toJson(visitDAO.TravelData());
            //     Log.d("vehicledata",vehicledata);
            postMethodToServer.execute(Utility.ConvertListToString(visitDAO.TravelData()));
            Log.d("vehicledata",Utility.ConvertListToString(visitDAO.TravelData()));
        }
        else {
            Log.d("traveldata","no_traveldata");
        }

    }

    @Override
    public void PostDataToServer(String obj) {
        if(obj!=null){
            try {
                JSONArray jsonArray = new JSONArray(obj);
                for (int i = 0; i < jsonArray.length(); i++) {
                    VisitModel visitobj=new VisitModel();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                        visitobj.setVisitCode(jsonObject.getString("TargetCode"));
                        visitobj.setTravelID(jsonObject.getInt("TargetID"));
                        visitobj.setID(jsonObject.getInt("AppID"));
                        visitDAO.UpdateTravelWebID(visitobj);

                }}
            catch (JSONException e) {
                e.printStackTrace();

            }}
        else {
            Log.d("no value","no value");

        }
    }
    private void visitDetails() {
     postMethodToServer = new PostMethodToServer(URL.SF_URL+"/travelsave",mcontext);
    //    postMethodToServer = new PostMethodToServer("http://172.16.6.187/sf/api/inc/travelsave",mcontext);
        postMethodToServer.getResponse = this;
    }
}