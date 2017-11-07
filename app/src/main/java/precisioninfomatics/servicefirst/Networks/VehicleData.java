package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import precisioninfomatics.servicefirst.DAO.VehicleDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.VehicleModel;


public class VehicleData implements PostMethod {
   private Context mcontext;
   private   PostMethodToServer postMethodToServer;

    public void PostVehicleData (Context context){
        this.mcontext=context;
        VehicleDAO vehicleDAO=new VehicleDAO(context);
        PostVehicleDetails();
        VehicleModel vehicleModel=vehicleDAO.getDetails();
        Gson gson=new Gson();
        String vehicledata=gson.toJson(vehicleModel);
    //    Log.d("vehicledata",vehicledata);
        postMethodToServer.execute(vehicledata);

    }

    @Override
    public void PostDataToServer(String obj) {
        Log.d("response",obj);

    }
    private void PostVehicleDetails() {
        postMethodToServer = new PostMethodToServer(URL.SF_URL+"/vehicleregistration",mcontext);
        postMethodToServer.getResponse = this;
    }
}
