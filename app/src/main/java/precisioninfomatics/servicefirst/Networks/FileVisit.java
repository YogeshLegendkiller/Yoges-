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

public class FileVisit implements FileData  {
private VisitDAO visitDAO;
private int  incidentID;
private Context context;
    public void PostFile(Context context,int IncidentID) {
        visitDAO = new VisitDAO(context);
        this.incidentID=IncidentID;
        this.context=context;
        int isFileExist=visitDAO.IsVisitFileExist();
            if(isFileExist>0){
         Log.d("id","insideFile");
            List<VisitModel> filelist=visitDAO.FileList();
            for(int i=0;i<filelist.size();i++){
                FileUploadAsync fileUploadAsync=new FileUploadAsync();
                fileUploadAsync.fileData=FileVisit.this;
                fileUploadAsync.execute(URL.SF_URL+"/uploadfiles",String.valueOf(2),String.valueOf(filelist.get(i).getIncidentID()),String.valueOf(filelist.get(i).getID()),String.valueOf(filelist.get(i).getUserID()),filelist.get(i).getDocumentPath(),String.valueOf(0));
            }
        }
        else {
               //  Log.d("id","insideVisit");
               //  VisitData visitData=new VisitData();
               // visitData.PostVisitData(context,IncidentID,1,null);
             //   VisitViewData visitViewData=new VisitViewData();
             //   visitViewData.VisitView(context,IncidentID,0,1,null);
                VisitSpareData visitSpareData=new VisitSpareData();
                visitSpareData.VisitSpareData(context,IncidentID,0,1,null);
            }
    }


    @Override
    public void getValue(String obj) {
            if(obj!=null){
                try {
                    VisitModel visitobj = new VisitModel();
                   Log.d("value",obj);
                    JSONObject jsonObject = new JSONObject(obj);
                    visitobj.setCallSlipUrl(jsonObject.getString("Url"));
                    //         visitobj.setWebID(jsonObject.getInt("VisitID"));
                    Log.d("id", String.valueOf(jsonObject.getInt("AppID")));
                    visitobj.setID(jsonObject.getInt("AppID"));
                    visitobj.setIsFileExist(0);
                    visitobj.setIsSent(1);

                    visitDAO.UpdateFileID(visitobj);
                 //   VisitData visitData = new VisitData();
                 //   visitData.PostVisitData(context, incidentID, 1, null);

                }
        catch (JSONException e) {
            e.printStackTrace();

        }}
        else {
                Log.d("visit","no file");
            }

     //   VisitViewData visitViewData=new VisitViewData();
      //  visitViewData.VisitView(context,incidentID,0,1,null);
        VisitSpareData visitSpareData=new VisitSpareData();
        visitSpareData.VisitSpareData(context,incidentID,0,1,null);

    }
}
