package precisioninfomatics.servicefirst.Networks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.os.BuildCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import precisioninfomatics.servicefirst.Activities.IncidentView;
import precisioninfomatics.servicefirst.Activities.LocalVendor;
import precisioninfomatics.servicefirst.DAO.LocalVendorDAO;
import precisioninfomatics.servicefirst.DAO.SpareDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.HelperClass.URL;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.ChartModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 4264 on 29/04/2017.
 */

public class LocalVendorData  implements PostMethod{
    private LocalVendorDAO localVendorDAO;
     private PostMethodToServer postMethodToServer;
   private Integer IncidentID,UserID;
    public void PostData(Context context, final  int id,final int userid) {
        localVendorDAO = new LocalVendorDAO(context);
        int check = localVendorDAO.IsVendorExist();
        this.IncidentID=id;
        this.UserID=userid;
        if (check > 0) {
            SendValueToServer(context);
            Log.d("lvdata", Utility.ConvertListToString(localVendorDAO.LocalVendorData()));
            postMethodToServer.execute(Utility.ConvertListToString(localVendorDAO.LocalVendorData()));
        }
        else {
            String url = URL.SF_URL + "/VendorInvoiceList/" + IncidentID+"/"+userid;
            Log.d("url",url);
            VolleyJsonArray.makeJsonArraytRequest(url, new GetMethod() {
                @Override
                public void getDataFromServer(String obj) {
                    try {
                        Log.d("value",obj);
                        JSONArray jsonArray = new JSONArray(obj);
                        localVendorDAO.Delete(IncidentID);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            LocalVendorModel localVendorModel=new LocalVendorModel();
                            localVendorModel.setWebID(jsonObject.getInt("InvoiceID"));
                            localVendorModel.setIncidentID(jsonObject.getInt("RegistrationID"));
                            localVendorModel.setInvoiceNumber(jsonObject.getString("InvoiceNumber"));
                            localVendorModel.setInvoiceDate(jsonObject.getString("InvoiceDate"));
                            localVendorModel.setSpareCharge((jsonObject.getInt("SpareCharge")));
                            localVendorModel.setServiceCharge(jsonObject.getInt("ServiceCharge"));
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
    public void PostDataToServer(String obj) {
        try {
            String url = URL.SF_URL + "/VendorInvoiceList/" + IncidentID +"/"+UserID ;
            Log.d("url",url);
            VolleyJsonArray.makeJsonArraytRequest(url, new GetMethod() {
                @Override
                public void getDataFromServer(String obj) {
                    try {
                        Log.d("value", obj);
                        JSONArray jsonArray = new JSONArray(obj);
                        localVendorDAO.Delete(IncidentID);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            LocalVendorModel localVendorModel = new LocalVendorModel();
                            localVendorModel.setWebID(jsonObject.getInt("InvoiceID"));
                            localVendorModel.setIncidentID(jsonObject.getInt("RegistrationID"));
                            localVendorModel.setInvoiceNumber(jsonObject.getString("InvoiceNumber"));
                            localVendorModel.setInvoiceDate(jsonObject.getString("InvoiceDate"));
                            localVendorModel.setSpareCharge((jsonObject.getInt("SpareCharge")));
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        Log.d("inside_finally", "end_db");
                        // visitDAO.db.endTransaction();
                    }

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
           /* LocalVendorModel localVendorModel = new LocalVendorModel();
            JSONArray jsonArray = new JSONArray(obj);
            /// SpareDAO spareDAO=new SpareDAO(context);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getInt("Status") > 0) {
                    localVendorModel.setWebID(jsonObject.getInt("VendorInvoiceID"));
                    localVendorModel.setPrimaryID(jsonObject.getInt("AppID"));
                    localVendorModel.setTotal(jsonObject.getString("Total"));
                    //     localVendorDAO.UpdateWebID(localVendorModel);
                    //    JSONArray  partarray=jsonObject.getJSONArray("partappdetails");
                }

            }
*/



    private void SendValueToServer(Context context) {
        postMethodToServer = new PostMethodToServer(URL.SF_URL + "/VendorInvoiceSave", context);
        postMethodToServer.getResponse = this;
    }



}