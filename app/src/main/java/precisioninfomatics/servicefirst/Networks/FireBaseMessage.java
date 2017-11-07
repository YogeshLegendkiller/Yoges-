package precisioninfomatics.servicefirst.Networks;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import precisioninfomatics.servicefirst.Activities.Dashboard;
import precisioninfomatics.servicefirst.DAO.IncidentDAO;
import precisioninfomatics.servicefirst.DAO.VisitDAO;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 15/04/2017.
 */

public class FireBaseMessage extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private  String message;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

    //   Log.d(TAG, "From: " + remoteMessage.getData());
        try {
            JSONObject jsonObjects=new JSONObject(remoteMessage.getData());
            String title=jsonObjects.getString("title");
        ///    Log.d(TAG, "test: " + title);
            IncidentDAO incidentDAO = new IncidentDAO(this);
            switch (title) {
                case "1":
                    message = "One Incident has been Assigned";
                    String jsArray = jsonObjects.getString("message");
          //          Log.d(TAG, "test: " + jsArray);
                    JSONArray jsonArray = new JSONArray(jsArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        IncidentModel incidentModel = new IncidentModel();
                        incidentModel.setCompanyName(jsonObject.getString("CompanyName"));
                        incidentModel.setIncidentID(jsonObject.getInt("RegistrationID"));
                        incidentModel.setIncidentCode(jsonObject.getString("IncidentCode"));
                        incidentModel.setProblemCategory(jsonObject.getString("ServiceClassification"));
                        incidentModel.setStatus(jsonObject.getString("Status"));
                        incidentModel.setLatitude(jsonObject.getString("Latitude"));
                        incidentModel.setLongtitude(jsonObject.getString("Longitude"));
                        boolean is_installationcall = jsonObject.getBoolean("IsInstallationCall");
                        if (is_installationcall) {
                            incidentModel.setIsInstallationCall(1);
                        } else {
                            incidentModel.setIsInstallationCall(0);
                        }
                        boolean is_claimrequired = jsonObject.getBoolean("IsGeneralClaim");
                        if (is_claimrequired) {
                            incidentModel.setIsGeneralClaim(1);
                        } else {
                            incidentModel.setIsGeneralClaim(0);
                        }
                        //  incidentModel.setIsGeneralClaim(1);
                        boolean is_IsPartRequired = jsonObject.getBoolean("IsPartRequired");
                        if (is_IsPartRequired) {
                            incidentModel.setIsPartRequired(1);
                        } else {
                            incidentModel.setIsPartRequired(0);
                        }
                        incidentModel.setLocalVendor(0);
                        incidentModel.setStatusID(jsonObject.getInt("StatusID"));
                        incidentModel.setProblemDescription(jsonObject.getString("ProblemDescription"));
                        incidentModel.setCreatedat(jsonObject.getString("CreatedDateTime"));
                        incidentDAO.InsertOrUpdate(incidentModel);
                    }

                    break;
                case "2":
                    message = "One Incident Has Been UnAssigned";
                    String value = jsonObjects.getString("message");
                    JSONObject jsonObject = new JSONObject(value);
                    int incidentID = jsonObject.getInt("RegistrationID");
                    incidentDAO.DeleteIncident(incidentID);

                    break;
                case "3":
                    Log.d("3", "3");
                    message = "Visit ";
                    ///       JSONObject jsonObject = new JSONObject(data.get("message"));
                    //     VisitData visitData=new VisitData();
                    ///    visitData.PostVisitData(this,Integer.valueOf(incidentid));
                    break;
                case "4":
                    message = "Part Requested ";
                    break;
                case "5":
                    message = "Part Issued";
                    break;
                case "6":
                    message = "Completed";
                    break;
            }
            }
        catch (JSONException e) {
            e.printStackTrace();
        }
        sendNotification(message);

    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_1481892667_vespa)
                .setContentTitle("Service First")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}

