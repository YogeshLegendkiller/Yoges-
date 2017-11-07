package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import precisioninfomatics.servicefirst.DAO.DeleteDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.DeleteModel;

/**
 * Created by 4264 on 03/05/2017.
 */

public class DeleteRecords implements PostMethod {
    private Context mcontext;
    private  DeleteDAO deleteDAO;
    private   PostMethodToServer postMethodToServer;

    public void DeleteRecord (Context context){
        this.mcontext=context;
        deleteDAO=new DeleteDAO(context);
        PostVehicleDetails();
        List<DeleteModel>list=deleteDAO.deletelist();
        Gson gson=new Gson();
        String delete=gson.toJson(list);
        Log.d("del",delete);
        postMethodToServer.execute(delete);
    }

    @Override
    public void PostDataToServer(String obj) {
        try {
            JSONArray jsonArray = new JSONArray(obj);
            Log.d("return", obj);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int status = jsonObject.getInt("Status");
                if (status == 0) {
                    int WebID = jsonObject.getInt("WebID");
                    int Flag = jsonObject.getInt("Flag");
                    deleteDAO.Delete(WebID, Flag);
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
        private void PostVehicleDetails() {
        postMethodToServer = new PostMethodToServer(URL.SF_URL+"/Delete",mcontext);
        postMethodToServer.getResponse = this;
    }
}