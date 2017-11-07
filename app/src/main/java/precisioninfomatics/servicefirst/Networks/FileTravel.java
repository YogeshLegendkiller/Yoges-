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
 * Created by 4264 on 11/04/2017.
 */

public class FileTravel implements FileData  {
    private VisitDAO visitDAO;
    private Context context;
    public void PostFile(Context context) {
        visitDAO = new VisitDAO(context);
        this.context=context;
        int isFileExist=visitDAO.IsNormalTravelFileExist();
        if(isFileExist>0){
            List<VisitModel> filelist=visitDAO.FileTravel();
            for(int i=0;i<filelist.size();i++){
                FileUploadAsync fileUploadAsync=new FileUploadAsync();
                fileUploadAsync.fileData=FileTravel.this;
                fileUploadAsync.execute(URL.SF_URL+"/uploadfiles",String.valueOf(3),String.valueOf(filelist.get(i).getIncidentID()),String.valueOf(filelist.get(i).getID()),String.valueOf(filelist.get(i).getUserID()),filelist.get(i).getDocumentPath(),String.valueOf(filelist.get(i).getTravelID()),String.valueOf(0));
            }
        }
        else {
            Log.d("no file","no file");
            TravelData travelData=new TravelData();
            travelData.PostTransportData(context);
        }


    }


    @Override
    public void getValue(String obj) {
        try {
           Log.d("obj",obj);
            if(obj!=null) {
                VisitModel visitobj = new VisitModel();
                JSONObject jsonObject = new JSONObject(obj);
                visitobj.setFilePath(jsonObject.getString("FilePath"));
                visitobj.setFileName(jsonObject.getString("FileName"));
                Log.d("id", String.valueOf(jsonObject.getInt("AppID")));
                visitobj.setID(jsonObject.getInt("AppID"));
                visitobj.setIsFileExist(0);
                visitDAO.UpdateFileTravelID(visitobj);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        TravelData travelData = new TravelData();
        travelData.PostTransportData(context);
    }
}
