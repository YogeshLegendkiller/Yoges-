package precisioninfomatics.servicefirst.Networks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import precisioninfomatics.servicefirst.Activities.LocalVendor;
import precisioninfomatics.servicefirst.DAO.LocalVendorDAO;
import precisioninfomatics.servicefirst.DAO.LoginDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 29/04/2017.
 */

public class LocalVendorFile implements FileData  {
    private LocalVendorDAO localVendorDAO;
    private int  incidentID;
    private Context context;
    private int UserID;
    public void PostFile(Context context,int IncidentID,int UserID) {
        localVendorDAO = new LocalVendorDAO(context);
        this.incidentID = IncidentID;
        this.UserID=UserID;
        this.context = context;
        int check = localVendorDAO.IsVendorExist();
        if (check > 0) {
            int file = localVendorDAO.IsLocalFileExist();
            if (file > 0) {
                LoginDAO loginDAO = new LoginDAO(context);
                int userID = loginDAO.getUserID();
                      List<LocalVendorModel> filelist = localVendorDAO.Document();
               for (int i = 0; i < filelist.size(); i++) {
                    FileUploadAsync fileUploadAsync = new FileUploadAsync();
                    fileUploadAsync.fileData = LocalVendorFile.this;
                    fileUploadAsync.execute(URL.SF_URL + "/uploadfiles", String.valueOf(5), String.valueOf(filelist.get(i).getIncidentID()), String.valueOf(filelist.get(i).getPrimaryID()), String.valueOf(userID), filelist.get(i).getLocalFilePath(), String.valueOf(0));
                }
            } else {
                Log.d("no file","no file");
                LocalVendorData localVendorData = new LocalVendorData();
                localVendorData.PostData(context, IncidentID,UserID);

            }
        }
        else {
            String url = URL.SF_URL + "/VendorInvoiceList/"+ incidentID+"/"+UserID;
            Log.d("url",url);
            VolleyJsonArray.makeJsonArraytRequest(url, new GetMethod() {
                @Override
                public void getDataFromServer(String obj) {
                    try {
                        JSONArray jsonArray = new JSONArray(obj);
                        localVendorDAO.Delete(incidentID);
                        Log.d("value",obj);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            LocalVendorModel localVendorModel=new LocalVendorModel();
                            localVendorModel.setWebID(jsonObject.getInt("InvoiceID"));
                            localVendorModel.setIncidentID(jsonObject.getInt("RegistrationID"));
                            localVendorModel.setInvoiceNumber(jsonObject.getString("InvoiceNumber"));
                            localVendorModel.setInvoiceDate(jsonObject.getString("InvoiceDate"));
                            localVendorModel.setSpareCharge(jsonObject.getInt("SpareCharge"));
                            localVendorModel.setServiceCharge((jsonObject.getInt("ServiceCharge")));
                            localVendorModel.setUserOrVendorID(jsonObject.getInt("UserOrVendorID"));
                            localVendorModel.setAttachmentPath(jsonObject.getString("FilePath"));
                            localVendorModel.setAttachmentName(jsonObject.getString("FileName"));
                            localVendorModel.setCreatedBy(jsonObject.getInt("CreatedBy"));
                            localVendorModel.setTotal(jsonObject.getInt("TotalCharge"));
                            localVendorModel.setResourceName(jsonObject.getString("ResourceName"));
                            localVendorModel.setCreatedDateTime(jsonObject.getString("CreatedDateTime"));
                            localVendorModel.setLastModifiedDateTime(jsonObject.getString("LastModifiedDateTime"));
                            localVendorModel.setLastModifiedBy(jsonObject.getInt("LastModifiedBy"));
                            localVendorDAO.InsertOrUpdate(localVendorModel);
                        }
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finally{
                        Log.d("inside_finally","end_db");
                        // visitDAO.db.endTransaction();
                    }

                }
            });
        }
    }

    @Override
    public void getValue(String obj) {
            if (obj != null) {
                try {
                    LocalVendorModel localVendorModel = new LocalVendorModel();
                    JSONObject jsonObject = new JSONObject(obj);
                    localVendorModel.setAttachmentName(jsonObject.getString("FileName"));
                    ///   Log.d("id",String.valueOf(jsonObject.getInt("AppID")));
                    localVendorModel.setAttachmentPath(jsonObject.getString("FilePath"));
                    localVendorModel.setPrimaryID(jsonObject.getInt("AppID"));
                    localVendorDAO.UpdateFileID(localVendorModel);
                    LocalVendorData localVendorData = new LocalVendorData();
                    localVendorData.PostData(context, incidentID,UserID);

                }
                catch(JSONException e){
                e.printStackTrace();

            }
        }
        else {
                Toast.makeText(context,"Please Try aftersometime",Toast.LENGTH_SHORT).show();
            }


}}
