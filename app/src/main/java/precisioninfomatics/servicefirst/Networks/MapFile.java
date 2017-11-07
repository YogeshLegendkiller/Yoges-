package precisioninfomatics.servicefirst.Networks;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 08/05/2017.
 */

public class MapFile  implements FileData  {
    private VisitDAO visitDAO;
    private Context context;
    public void PostFile(Context context) {
        visitDAO = new VisitDAO(context);
        this.context=context;
        int ismapexist=visitDAO.IsMapTravelFileExist();
        if(ismapexist>0){
            List<VisitModel> filelist=visitDAO.MapFile();
            for(int i=0;i<filelist.size();i++){
                FileUploadAsync fileUploadAsync=new FileUploadAsync();
                fileUploadAsync.fileData=MapFile.this;
                fileUploadAsync.execute(URL.SF_URL+"/uploadfiles",String.valueOf(3),String.valueOf(filelist.get(i).getIncidentID()),String.valueOf(filelist.get(i).getID()),String.valueOf(filelist.get(i).getUserID()),filelist.get(i).getMapOrginFilepath(),String.valueOf(filelist.get(i).getTravelID()),String.valueOf(1),filelist.get(i).getDestiFilePath());
            }
        }
    }


    @Override
    public void getValue(String obj) {
        try {
           Log.d("obj",obj);
            VisitModel visitobj=new VisitModel();
            JSONObject jsonObject = new JSONObject(obj);
            visitobj.setMapFilePath(jsonObject.getString("MapFilePath"));
            visitobj.setOrginMapFileName(jsonObject.getString("OrginMapFileName"));
            visitobj.setDestinationMapFileName(jsonObject.getString("DestinationMapFileName"));
       //     Log.d("id",String.valueOf(jsonObject.getInt("AppID")));
            visitobj.setID(jsonObject.getInt("AppID"));
//            visitobj.setTravelID(jsonObject.getInt("TravelID"));
            visitobj.setIsFileExist(0);
            visitDAO.UpdateMapFileTravelID(visitobj);
            TravelData travelData=new TravelData();
            travelData.PostTransportData(context);

        }
        catch (JSONException e) {
            e.printStackTrace();

        }
    }
}

