package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.HelperClass.Utility;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.ResourceModel;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VehicleModel;
import precisioninfomatics.servicefirst.Model.VisitModel;

/**
 * Created by 4264 on 08-12-2016.
 */

public class VisitDAO extends SFSingelton {
    private Context mcontext;
    private static String DATABASE_NAME = "SFdatabasename";
    public static final Uri VisitDAO_URI = Uri
            .parse("sqlite://" + "sf_visit" + "/" + DATABASE_NAME);

    public VisitDAO(Context context) {
        super(context);
        this.mcontext = context;
    }

    public void InsertVisitUpdate(VisitModel visitModel) {
        //  IncidentModel incidentViewModel=new IncidentViewModel();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VisitModel.checkin, visitModel.getCheckInDate());
        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.findsatsite, visitModel.getFindingsAtSite());
        contentValues.put(VisitModel.actiontaken, visitModel.getActionTaken());
        contentValues.put(VisitModel.calslipno, visitModel.getCallSlipNo());
        contentValues.put(VisitModel.nextvisitdate, visitModel.getNextVisitDate());
        contentValues.put(VisitModel.localvendorid, visitModel.getLocalVendorID());
        contentValues.put(VisitModel.localvendor, visitModel.getLocalVendorText());
        contentValues.put(VisitModel.observation, visitModel.getObservation());
        contentValues.put(VisitModel.pendgclasid, visitModel.getPendingClassification());
        contentValues.put(VisitModel.pendingclas, visitModel.getPendingClassificationText());
        contentValues.put(VisitModel.status, visitModel.getVisitStatus());
        contentValues.put(VisitModel.statusID, visitModel.getStatus());
        contentValues.put(VisitModel.islocalvendor, visitModel.getIsLocalVendor());
        contentValues.put(VisitModel.cutofdate, visitModel.getCutOffDate());
        contentValues.put(VisitModel.travelorcallflag, visitModel.getTravelrVisitFlag());
        contentValues.put(VisitModel.remarks, visitModel.getRemarks());
        contentValues.put(VisitModel.t_part, visitModel.getPart());
        contentValues.put(VisitModel.partisue, visitModel.getPartIssue());
        contentValues.put(VisitModel.webid, visitModel.getWebID());
        contentValues.put(VisitModel.isfileexist, visitModel.getIsFileExist());
        contentValues.put(VisitModel.t_code, visitModel.getVisitCode());
        contentValues.put(VisitModel.userid, visitModel.getUserID());
        contentValues.put(VisitModel.isServer,visitModel.getIsServer());
        contentValues.put(VisitModel.sparepart, visitModel.getSpareRequest());
        contentValues.put(VisitModel.ca, visitModel.getCreatedat());
        contentValues.put(VisitModel.VisitOrTravel_IsSent, visitModel.getIsSent());
        contentValues.put(VisitModel.docpath, visitModel.getDocumentPath());
        contentValues.put(VisitModel.docname, visitModel.getDocumentName());
        contentValues.put(VisitModel.cb, visitModel.getCreatedby());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        Long id = db.insert(VisitModel.table, null, contentValues);
    }
    public void InsertWebVisitUpdate(VisitModel visitModel) {
        //  IncidentModel incidentViewModel=new IncidentViewModel();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VisitModel.checkin, visitModel.getCheckInDate());
        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.findsatsite, visitModel.getFindingsAtSite());
        contentValues.put(VisitModel.actiontaken, visitModel.getActionTaken());
        contentValues.put(VisitModel.calslipno, visitModel.getCallSlipNo());
        contentValues.put(VisitModel.nextvisitdate, visitModel.getNextVisitDate());
        contentValues.put(VisitModel.localvendorid, visitModel.getLocalVendorID());
        contentValues.put(VisitModel.localvendor, visitModel.getLocalVendorText());
        contentValues.put(VisitModel.observation, visitModel.getObservation());
        contentValues.put(VisitModel.pendgclasid, visitModel.getPendingClassification());
        contentValues.put(VisitModel.pendingclas, visitModel.getPendingClassificationText());
        contentValues.put(VisitModel.status, visitModel.getVisitStatus());
        contentValues.put(VisitModel.errorcode,visitModel.getErrorCode());
        contentValues.put(VisitModel.errormessage,visitModel.getErrorMessage());
        contentValues.put(VisitModel.statusID, visitModel.getStatus());
        contentValues.put(VisitModel.islocalvendor, visitModel.getIsLocalVendor());
        contentValues.put(VisitModel.cutofdate, visitModel.getCutOffDate());
        contentValues.put(VisitModel.travelorcallflag, visitModel.getTravelrVisitFlag());
        contentValues.put(VisitModel.remarks, visitModel.getRemarks());
        contentValues.put(VisitModel.t_part, visitModel.getPart());
        contentValues.put(VisitModel.partisue, visitModel.getPartIssue());
        contentValues.put(VisitModel.webid, visitModel.getWebID());
        contentValues.put(VisitModel.isfileexist, visitModel.getIsFileExist());
        contentValues.put(VisitModel.t_code, visitModel.getVisitCode());
        contentValues.put(VisitModel.userid, visitModel.getUserID());
        contentValues.put(VisitModel.isServer,visitModel.getIsServer());
        contentValues.put(VisitModel.sparepart, visitModel.getSpareRequest());
        contentValues.put(VisitModel.ca, visitModel.getCreatedat());
        contentValues.put(VisitModel.VisitOrTravel_IsSent, visitModel.getIsSent());
        contentValues.put(VisitModel.cb, visitModel.getCreatedby());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
         db.insert(VisitModel.table, null, contentValues);
    }

    public void UpdateVisit(VisitModel visitModel) {
        ContentValues contentValues = new ContentValues();
        String[] id = {String.valueOf(visitModel.getID())};
        contentValues.put(VisitModel.partisue, visitModel.getPartIssue());
        contentValues.put(VisitModel.t_code, visitModel.getVisitCode());
        contentValues.put(VisitModel.VisitOrTravel_IsSent, visitModel.getIsSent());
        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.findsatsite, visitModel.getFindingsAtSite());
        contentValues.put(VisitModel.actiontaken, visitModel.getActionTaken());
        contentValues.put(VisitModel.calslipno, visitModel.getCallSlipNo());
        contentValues.put(VisitModel.nextvisitdate, visitModel.getNextVisitDate());
        contentValues.put(VisitModel.errorcode,visitModel.getErrorCode());
       // contentValues.put(VisitModel.callslipurl, visitModel.getCallSlipUrl());
        contentValues.put(VisitModel.localvendorid, visitModel.getLocalVendorID());
        contentValues.put(VisitModel.localvendor, visitModel.getLocalVendorText());
        contentValues.put(VisitModel.observation, visitModel.getObservation());
        contentValues.put(VisitModel.islocalvendor, visitModel.getIsLocalVendor());
        contentValues.put(VisitModel.pendgclasid, visitModel.getPendingClassification());
        contentValues.put(VisitModel.pendingclas, visitModel.getPendingClassificationText());
        contentValues.put(VisitModel.status, visitModel.getVisitStatus());
        contentValues.put(VisitModel.statusID, visitModel.getStatus());
        contentValues.put(VisitModel.cutofdate, visitModel.getCutOffDate());
        contentValues.put(VisitModel.travelorcallflag, visitModel.getTravelrVisitFlag());
        contentValues.put(VisitModel.remarks, visitModel.getRemarks());
        contentValues.put(VisitModel.customer_branchid, visitModel.getCustmerBranchID());
        contentValues.put(VisitModel.checklistlineid, visitModel.getCheckListLineID());
        contentValues.put(VisitModel.itmserialnomap, visitModel.getItemserialnomap());
        contentValues.put(VisitModel.isfileexist, visitModel.getIsFileExist());
        contentValues.put(VisitModel.t_part, visitModel.getPart());
        contentValues.put(VisitModel.webid,visitModel.getWebID());
        contentValues.put(VisitModel.t_code,visitModel.getVisitCode());
        contentValues.put(VisitModel.sparepart, visitModel.getSpareRequest());
        contentValues.put(VisitModel.docname, visitModel.getDocumentName());
        contentValues.put(VisitModel.docpath, visitModel.getDocumentPath());
        //   contentValues.put(VisitModel.ca, visitModel.getCreatedby());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.update(VisitModel.table, contentValues, VisitModel.id + "= ?", id);
    }

    public void UpdateWebVisit(VisitModel visitModel) {
        ContentValues contentValues = new ContentValues();
        String[] id = {String.valueOf(visitModel.getWebID())};
        contentValues.put(VisitModel.t_code, visitModel.getVisitCode());
        contentValues.put(VisitModel.partisue, visitModel.getPartIssue());
        contentValues.put(VisitModel.VisitOrTravel_IsSent, visitModel.getIsSent());
        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.findsatsite, visitModel.getFindingsAtSite());
        contentValues.put(VisitModel.actiontaken, visitModel.getActionTaken());
        contentValues.put(VisitModel.calslipno, visitModel.getCallSlipNo());
        contentValues.put(VisitModel.errorcode,visitModel.getErrorCode());
        contentValues.put(VisitModel.errormessage,visitModel.getErrorMessage());
        contentValues.put(VisitModel.nextvisitdate, visitModel.getNextVisitDate());
        contentValues.put(VisitModel.isServer,visitModel.getIsServer());
        contentValues.put(VisitModel.localvendorid, visitModel.getLocalVendorID());
        contentValues.put(VisitModel.localvendor, visitModel.getLocalVendorText());
        contentValues.put(VisitModel.observation, visitModel.getObservation());
        contentValues.put(VisitModel.islocalvendor, visitModel.getIsLocalVendor());
        contentValues.put(VisitModel.pendgclasid, visitModel.getPendingClassification());
        contentValues.put(VisitModel.pendingclas, visitModel.getPendingClassificationText());
        contentValues.put(VisitModel.status, visitModel.getVisitStatus());
        contentValues.put(VisitModel.statusID, visitModel.getStatus());
        contentValues.put(VisitModel.cutofdate, visitModel.getCutOffDate());
        contentValues.put(VisitModel.travelorcallflag, visitModel.getTravelrVisitFlag());
        contentValues.put(VisitModel.remarks, visitModel.getRemarks());
        contentValues.put(VisitModel.t_part, visitModel.getPart());
        contentValues.put(VisitModel.webid, visitModel.getWebID());
        contentValues.put(VisitModel.sparepart, visitModel.getSpareRequest());
        contentValues.put(VisitModel.ca, visitModel.getCreatedat());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.update(VisitModel.table, contentValues, VisitModel.webid + "= ?", id);
    }

    /* public Cursor visitList(int  id){
       String visitquery= " Select  "  + VisitModel.travelorcallflag  + " ," + VisitModel.id  + " AS " + VisitModel.actiontaken  + " ," + VisitModel.checkin  + " AS " + VisitModel.ca  +  " ," +  VisitModel.checkout  +  " AS " + VisitModel.lma + " ," + VisitModel.Visit_EmployeeName  +  " AS " + VisitModel.Lat + " ," + VisitModel.cb   +  " AS " + VisitModel.Long +  "  FROM " + VisitModel.table + " WHERE " + VisitModel.incid + " =" +id  +  " UNION ALL "  + " SELECT "  + VisitModel.travelorcallflag + " ," + VisitModel.t_travelid  + " AS " + VisitModel.actiontaken +  " ," + VisitModel.checkin + " AS" + VisitModel.ca + " ," + VisitModel.checkout + " AS " + VisitModel.lma + " ," + VisitModel.t_startlat  +  " AS " +  VisitModel.Lat +  " ," + VisitModel.endlat +  " AS " +VisitModel.Long  +" FROM " + VisitModel.t_table + " WHERE " + VisitModel.t_incid + " =" + id + "";
       Cursor cursor = db.rawQuery(visitquery ,null);
       cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
       return cursor;
     }*/
    public Cursor visitUpdate(int id) {
     //   String visitquery = " Select  " + VisitModel.checkin + "," + VisitModel.checkout + " ," + VisitModel.findsatsite + "," + VisitModel.actiontaken + " ," + VisitModel.status + "," + VisitModel.statusID +   "," + VisitModel.pendingclas + "," + VisitModel.pendgclasid + "," + VisitModel.nextvisitdate + "," + VisitModel.cutofdate + "," + VisitModel.remarks + "," + VisitModel.calslipno + "," + VisitModel.docpath + "," + VisitModel.docname + " from " + VisitModel.table + " where " + VisitModel.id + " =" + id + "";
       //
        String visitquery = " Select  * from " + VisitModel.table + " where " + VisitModel.id + " ="+ id;
        Cursor cursor = db.rawQuery(visitquery, null);
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
        return cursor;
    }
    public Cursor TravelUpdate(int id) {
         String traveledit = " Select  * from " + VisitModel.t_table  + " where " + VisitModel.t_travelid + " ="+ id;
        Cursor cursor = db.rawQuery(traveledit, null);
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
        return cursor;
    }
    public Cursor TravelView(int id) {
        Cursor cursor = db.rawQuery(" select " + VisitModel.checkin + " ," + VisitModel.checkout + " ," + VisitModel.km + " ," + VisitModel.filePath  + " , "+ VisitModel.maporothr + ","  + VisitModel.statadrs  + " , " + VisitModel.endadrs  +    "," +VisitModel.docpath + " ," + VisitModel.t_amnt + "," + VisitModel.transportmode + " from " + VisitModel.t_table + " where " + VisitModel.t_travelid + " =" + id, null);
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
        return cursor;
    }

   /* public List<VisitModel> date(int id) {
        String countQuery = " Select * from (SELECT DISTINCT strftime('%Y-%m-%d'," + VisitModel.checkin + ") FROM " + VisitModel.table + " WHERE " + VisitModel.incid + "= " + id + " UNION    SELECT DISTINCT strftime('%Y-%m-%d'," + VisitModel.checkin + ") FROM " + VisitModel.t_table + " WHERE " + VisitModel.t_incid + "= " + id + " )" + " order by  1 DESC ";
        Cursor cursor = db.rawQuery(countQuery, null);
        List<VisitModel> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String monthname = cursor.getString(0);
                    String[] datearray = monthname.split("-");
                    int monthNumber = Integer.valueOf(datearray[1]);
                    monthNumber--; // convert to 0-based for Calendar
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.MONTH, monthNumber);
                    VisitModel visitModel = new VisitModel();
                    visitModel.setCheckInDate(monthname);
                    list.add(visitModel);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
            cursor.close();
        }
        return list;
    }*/

    public List<VisitModel> FileList() {

            String query = " SELECT " + VisitModel.docpath  + " ,"
                    + VisitModel.incid + " ,"
                    + VisitModel.userid + " ,"
                    + VisitModel.id + " from "
                    + VisitModel.table + " where " + VisitModel.isfileexist  + " =" + 1;

            Cursor cursor = db.rawQuery(query, null);
            List<VisitModel> visitlist = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        VisitModel visitModel = new VisitModel();
                        visitModel.setDocumentPath(cursor.getString(cursor.getColumnIndex(VisitModel.docpath)));
                        visitModel.setUserID(cursor.getInt(cursor.getColumnIndex(VisitModel.userid)));
                        visitModel.setIncidentID(cursor.getInt(cursor.getColumnIndex(VisitModel.incid)));
                        visitModel.setID(cursor.getInt(cursor.getColumnIndex(VisitModel.id)));
                        visitlist.add(visitModel);
                    } while (cursor.moveToNext());
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return visitlist;
    }
    public void UpdateFileID(VisitModel visitobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitobj.getID())};
    //    contentValues.put(VisitModel.webid, visitobj.getWebID());
        contentValues.put(VisitModel.isfileexist ,visitobj.getIsFileExist());
        contentValues.put(VisitModel.callslipurl, visitobj.getCallSlipUrl() );
        db.update(VisitModel.table, contentValues, VisitModel.id + "= ?", args);
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
    }

    public Cursor visitList(int id) {
        String visitquery = " Select " + VisitModel.travelorcallflag + " ,"
                + VisitModel.incid + " ,"
                + VisitModel.id + " AS " + VisitModel.TempID + " ,"
                + VisitModel.checkin + " AS " + VisitModel.TempCheckin + " ,"
                + VisitModel.checkout + " AS " + VisitModel.TempCheckout + " ,"
                + VisitModel.webid + " AS " + VisitModel.TempWebID + " ,"
                + VisitModel.observation  + " AS " + VisitModel.TempStartLat + " ,"
                + VisitModel.status + " AS " + VisitModel.TempStartLong + " , "
                + VisitModel.errorcode + " AS " + VisitModel.TempMaporother + " ,"
                + VisitModel.actiontaken + " AS " + VisitModel.TempAtorEndAdr + " ,"
                + VisitModel.remarks + " AS " + VisitModel.TempMode + " ,"
                + VisitModel.callslipurl + " AS " + VisitModel.Tempduration + " ,"
                + VisitModel.findsatsite  + " AS " + VisitModel.TempStartAddress + " ,"
                + VehicleModel.userid + " AS " + VisitModel.userid + " ,"
                + VisitModel.ca + " AS " + VisitModel.TempAmnt + " ,"
                + VisitModel.errormessage +         " AS " + VisitModel.TempKm + " ,"
                + VisitModel.calslipno + " AS " + VisitModel.TempEstimatedKilometer
                + "  FROM " + VisitModel.table + " WHERE " + VisitModel.incid + " =" + id + " UNION ALL " + " SELECT "
                + VisitModel.travelorcallflag
                + " ," + VisitModel.incid + " ,"
                + VisitModel.t_travelid + " AS " + VisitModel.TempID + " ,"
                + VisitModel.checkin + " AS" + VisitModel.TempCheckin + " ,"
                + VisitModel.checkout + " AS " + VisitModel.TempCheckout + " ,"
                + VisitModel.travelwebid + " AS " + VisitModel.TempWebID + " ,"
                + VisitModel.t_startlat + " AS " + VisitModel.TempStartLat + " ,"
                + VisitModel.startlong + " AS " + VisitModel.TempStartLong + " ,"
                + VisitModel.maporothr + " AS " + VisitModel.TempMaporother + " ,"
                + VisitModel.endadrs + " AS " +  VisitModel.TempAtorEndAdr + " ,"
                + VisitModel.transportmode + " AS " + VisitModel.TempMode + " ,"
                + VisitModel.duration  + " AS " + VisitModel.Tempduration + " ,"
                + VisitModel.statadrs + " AS " + VisitModel.TempStartAddress + " ,"
                + VehicleModel.userid + " AS " + VisitModel.userid + " ,"
                + VisitModel.t_amnt +  " AS " + VisitModel.TempAmnt + " ,"
                + VisitModel.km   + " AS "  + VisitModel.TempKm + " ,"
                + VisitModel.estdistance + " AS " + VisitModel.TempEstimatedKilometer +
                " FROM " + VisitModel.t_table + " WHERE " + VisitModel.incid + " =" + id + " order by  4 DESC";
        Cursor cursor = db.rawQuery(visitquery, null);
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
        return cursor;
    }

    public Cursor  visitView(int id) {

        String query = " SELECT " + VisitModel.pendingclas + " ,"
                + VisitModel.status + " ,"
                + VisitModel.docpath + " ,"
                + VisitModel.docname + " ,"
                + VisitModel.nextvisitdate + " ,"
                + VisitModel.cutofdate + " ,"
                + VisitModel.remarks + " ,"
                + VisitModel.checkin + " ,"
                + VisitModel.checkout + " ,"
                + VisitModel.findsatsite + " ,"
                + VisitModel.calslipno + " ,"
                + VisitModel.actiontaken + " from "


                + VisitModel.table + " where " + VisitModel.id + " =" + id;

        Cursor cursor = db.rawQuery(query, null);
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);

     /*   List<VisitModel> visitlist = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    VisitModel visitModel = new VisitModel();
                    visitModel.setCallSlipNo(cursor.getString(cursor.getColumnIndex(VisitModel.calslipno)));
                    visitModel.setCutOffDate(cursor.getString(cursor.getColumnIndex(VisitModel.cutofdate)));
                    visitModel.setNextVisitDate(cursor.getString(cursor.getColumnIndex(VisitModel.nextvisitdate)));
                    visitModel.setPendingClassificationText(cursor.getString(cursor.getColumnIndex(VisitModel.pendingclas)));
                    visitModel.setVisitStatus(cursor.getString(cursor.getColumnIndex(VisitModel.status)));
                    visitModel.setDocumentPath(cursor.getString(cursor.getColumnIndex(VisitModel.docpath)));
                    visitModel.setDocumentName(cursor.getString(cursor.getColumnIndex(VisitModel.docname)));
                    visitModel.setCheckInDate(cursor.getString(cursor.getColumnIndex(VisitModel.checkin)));
                    visitModel.setCheckOutDate(cursor.getString(cursor.getColumnIndex(VisitModel.checkout)));
                    visitModel.setFindingsAtSite(cursor.getString(cursor.getColumnIndex(VisitModel.findsatsite)));
                    visitModel.setActionTaken(cursor.getString(cursor.getColumnIndex(VisitModel.actiontaken)));
                    //     visitModel.setCreatedat(cursor.getString(cursor.getColumnIndex(VisitModel.ca)));
                    //   visitModel.setLastModifiedat(cursor.getString(cursor.getColumnIndex(VisitModel.lma)));
                    //  visitModel.setCreatedby(cursor.getString(cursor.getColumnIndex(VisitModel.cb)));
                    // visitModel.setLastmodifiedby(cursor.getString(cursor.getColumnIndex(VisitModel.lmb)));
                    visitlist.add(visitModel);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }*/
        return cursor;
    }

    public List<VisitModel> VisitData() {
        String query = " SELECT "
                + VisitModel.id + " ,"
                + VisitModel.webid + ","
                + VisitModel.incid + ","
                + VisitModel.checkin + " ,"
                + VisitModel.checkout + " ,"
                + VisitModel.ca + " ,"
                + VisitModel.lma + " ,"
                + VisitModel.lmb + " ,"
                + VisitModel.cb + " ,"
                + VisitModel.isfileexist + " ,"
                + VisitModel.calslipno + " ,"
                + VisitModel.partisue + "  ,"
                + VisitModel.callslipurl + " ,"
                + VisitModel.findsatsite + " ,"
                + VisitModel.itmserialnomap + " ,"
                + VisitModel.actiontaken + " ,"
                + VisitModel.userid + " ,"
                + VisitModel.statusID + " ,"
                + VisitModel.pendgclasid + " ,"
                + VisitModel.nextvisitdate + " ,"
                + VisitModel.customer_branchid + " ,"
                + VisitModel.cutofdate + " ,"
                + VisitModel.remarks + " ,"
                + VisitModel.islocalvendor + " ,"
                + VisitModel.sparepart + "  FROM " + VisitModel.table +
                " WHERE " + VisitModel.VisitOrTravel_IsSent + " =" + 1 + " and " + VisitModel.isfileexist + " =" + 0 + " and " + VisitModel.errorcode  + " =" + 0 +  "";
        Cursor cursor = db.rawQuery(query, null);
        List<VisitModel> listobj = new ArrayList<>();
        SpareDAO spareDAO=new SpareDAO(mcontext);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    VisitModel visitobj = new VisitModel();
                    visitobj.setID(cursor.getInt(cursor.getColumnIndex(VisitModel.id)));
                    visitobj.setWebID(cursor.getInt(cursor.getColumnIndex(VisitModel.webid)));
                    visitobj.setCustmerBranchID(cursor.getInt(cursor.getColumnIndex(VisitModel.customer_branchid)));
                    visitobj.setCallSlipNo(cursor.getString(cursor.getColumnIndex(VisitModel.calslipno)));
                    List<SerialNumberMapModel> serialNumberMapModelList = Utility.GenericList(SerialNumberMapModel.class,cursor.getString(cursor.getColumnIndex(VisitModel.itmserialnomap)));
                    visitobj.setInstalledSerialNumber(serialNumberMapModelList);
                    visitobj.setUserID(cursor.getInt(cursor.getColumnIndex(VisitModel.userid)));
                    List<PartIssueModel> partIssueModelList = Utility.GenericList(PartIssueModel.class,cursor.getString(cursor.getColumnIndex(VisitModel.partisue)));
                    List<SpareModel>sparestring= (spareDAO.spareRequestList(cursor.getInt(cursor.getColumnIndex(VisitModel.incid))));
                    visitobj.setSparePart(partIssueModelList);
                    visitobj.setPart(sparestring);
                    visitobj.setIncidentID(cursor.getInt(cursor.getColumnIndex(VisitModel.incid)));
                    visitobj.setCheckInDate(cursor.getString(cursor.getColumnIndex(VisitModel.checkin)));
                    visitobj.setCheckOutDate(cursor.getString(cursor.getColumnIndex(VisitModel.checkout)));
                    visitobj.setCreatedat(cursor.getString(cursor.getColumnIndex(VisitModel.ca)));
                    visitobj.setLastModifiedat(cursor.getString(cursor.getColumnIndex(VisitModel.lma)));
                    visitobj.setLastmodifiedby(cursor.getInt(cursor.getColumnIndex(VisitModel.lmb)));
                    visitobj.setCreatedby(cursor.getInt(cursor.getColumnIndex(VisitModel.cb)));
                    visitobj.setFindingsAtSite(cursor.getString(cursor.getColumnIndex(VisitModel.findsatsite)));
                    visitobj.setActionTaken(cursor.getString(cursor.getColumnIndex(VisitModel.actiontaken)));
                    visitobj.setStatus(cursor.getInt(cursor.getColumnIndex(VisitModel.statusID)));
                    visitobj.setPendingClassification(cursor.getInt(cursor.getColumnIndex(VisitModel.pendgclasid)));
                    visitobj.setNextVisitDate(cursor.getString(cursor.getColumnIndex(VisitModel.nextvisitdate)));
                    visitobj.setCutOffDate(cursor.getString(cursor.getColumnIndex(VisitModel.cutofdate)));
                    visitobj.setRemarks(cursor.getString(cursor.getColumnIndex(VisitModel.remarks)));
                    visitobj.setCallSlipUrl(cursor.getString(cursor.getColumnIndex(VisitModel.callslipurl)));
                    visitobj.setIsLocalVendor(cursor.getInt(cursor.getColumnIndex(VisitModel.islocalvendor)));
                    listobj.add(visitobj);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null || !cursor.isClosed()) {
            cursor.close();
        }
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
        return listobj;
    }

    public Long InsertTravel(VisitModel visitModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        contentValues.put(VisitModel.travelorcallflag, visitModel.getTravelrVisitFlag());
        contentValues.put(VisitModel.t_startlat, visitModel.getStartLat());
        contentValues.put(VisitModel.startlong, visitModel.getStartLong());
        contentValues.put(VisitModel.userid, visitModel.getUserID());
        contentValues.put(VisitModel.checkin, visitModel.getCheckInDate());
        contentValues.put(VisitModel.t_amnt, visitModel.getAmount());
        contentValues.put(VisitModel.maporginfilepath,visitModel.getMapOrginFilepath());
        contentValues.put(VisitModel.estdistance, visitModel.getEstimatedDistance());
        contentValues.put(VisitModel.duration, visitModel.getDuration());
        contentValues.put(VisitModel.statadrs, visitModel.getStartAddress());
        contentValues.put(VisitModel.endadrs,visitModel.getEndAddress());
        contentValues.put(VisitModel.maporothr, visitModel.getMaporOther());
        contentValues.put(VisitModel.transportmodeID, visitModel.getTransportMode());
        contentValues.put(VisitModel.transportmode, visitModel.getTransportModeText());
        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.visitortravel, visitModel.getTravelOrVistText());
        contentValues.put(VisitModel.endlong, visitModel.getEndLong());
        contentValues.put(VisitModel.travelwebid,visitModel.getTravelID());
        contentValues.put(VisitModel.endlat, visitModel.getEndLat());
        contentValues.put(VisitModel.docname, visitModel.getDocumentName());
        contentValues.put(VisitModel.docpath, visitModel.getDocumentPath());
        contentValues.put(VisitModel.isfileexist, visitModel.getIsFileExist());
        contentValues.put(VisitModel.isServer,visitModel.getIsServer());
        contentValues.put(VisitModel.t_code,visitModel.getVisitCode());
        contentValues.put(VisitModel.cb, visitModel.getCreatedby());
        contentValues.put(VisitModel.filePath, visitModel.getFilePath());
        contentValues.put(VisitModel.fileName,visitModel.getFileName());
        contentValues.put(VisitModel.ca, visitModel.getCreatedat());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.km, visitModel.getKilometer());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        Long id = db.insert(VisitModel.t_table, null, contentValues);
        return id;
    }
    public void InsertWebTravel(VisitModel visitModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        contentValues.put(VisitModel.travelorcallflag, visitModel.getTravelrVisitFlag());
        contentValues.put(VisitModel.t_startlat, visitModel.getStartLat());
        contentValues.put(VisitModel.startlong, visitModel.getStartLong());
        contentValues.put(VisitModel.userid, visitModel.getUserID());
        contentValues.put(VisitModel.checkin, visitModel.getCheckInDate());
        contentValues.put(VisitModel.t_amnt, visitModel.getAmount());
        contentValues.put(VisitModel.estdistance, visitModel.getEstimatedDistance());
        contentValues.put(VisitModel.statadrs, visitModel.getStartAddress());
        contentValues.put(VisitModel.endadrs,visitModel.getEndAddress());
      //  contentValues.put(VisitModel.maporothr, visitModel.getMaporOther());
        contentValues.put(VisitModel.transportmodeID, visitModel.getTransportMode());
        contentValues.put(VisitModel.transportmode, visitModel.getTransportModeText());
        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.visitortravel, visitModel.getTravelOrVistText());
        contentValues.put(VisitModel.endlong, visitModel.getEndLong());
        contentValues.put(VisitModel.travelwebid,visitModel.getTravelID());
        contentValues.put(VisitModel.endlat, visitModel.getEndLat());
         contentValues.put(VisitModel.isfileexist, visitModel.getIsFileExist());
        contentValues.put(VisitModel.isServer,visitModel.getIsServer());
        contentValues.put(VisitModel.t_code,visitModel.getVisitCode());
        contentValues.put(VisitModel.cb, visitModel.getCreatedby());
        contentValues.put(VisitModel.filePath, visitModel.getFilePath());
        contentValues.put(VisitModel.fileName,visitModel.getFileName());
        contentValues.put(VisitModel.ca, visitModel.getCreatedat());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.km, visitModel.getKilometer());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
         db.insert(VisitModel.t_table, null, contentValues);
    }
    public void UpdateTravel(VisitModel visitModel) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitModel.getID())};
        contentValues.put(VisitModel.checkin,visitModel.getCheckInDate());
        contentValues.put(VisitModel.t_amnt, visitModel.getAmount());
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.km, visitModel.getKilometer());
        contentValues.put(VisitModel.endlat, visitModel.getEndLat());
        contentValues.put(VisitModel.transportmodeID, visitModel.getTransportMode());
        contentValues.put(VisitModel.transportmode, visitModel.getTransportModeText());
        contentValues.put(VisitModel.endlong, visitModel.getEndLong());
        contentValues.put(VisitModel.endadrs, visitModel.getEndAddress());
     //   contentValues.put(VisitModel.duration, visitModel.getDuration());
        contentValues.put(VisitModel.statadrs, visitModel.getStartAddress());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.docname, visitModel.getDocumentName());
        contentValues.put(VisitModel.filePath, visitModel.getFilePath());
        contentValues.put(VisitModel.fileName,visitModel.getFileName());
        contentValues.put(VisitModel.mapdestifilepath,visitModel.getDestiFilePath());
        contentValues.put(VisitModel.maporothr,visitModel.getMaporOther());
        //added
        contentValues.put(VisitModel.VisitOrTravel_IsSent, visitModel.getIsSent());

        contentValues.put(VisitModel.docpath, visitModel.getDocumentPath());
        contentValues.put(VisitModel.isfileexist, visitModel.getIsFileExist());
        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.t_code,visitModel.getVisitCode());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.update(VisitModel.t_table, contentValues, VisitModel.t_travelid + "= ?", args);
    }
    public void EditTravel(VisitModel visitModel) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitModel.getID())};
        contentValues.put(VisitModel.checkin,visitModel.getCheckInDate());
        contentValues.put(VisitModel.t_amnt, visitModel.getAmount());
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.km, visitModel.getKilometer());
        contentValues.put(VisitModel.endlat, visitModel.getEndLat());
        contentValues.put(VisitModel.transportmodeID, visitModel.getTransportMode());
        contentValues.put(VisitModel.transportmode, visitModel.getTransportModeText());
        contentValues.put(VisitModel.endlong, visitModel.getEndLong());
        contentValues.put(VisitModel.endadrs, visitModel.getEndAddress());
        //   contentValues.put(VisitModel.duration, visitModel.getDuration());
        contentValues.put(VisitModel.statadrs, visitModel.getStartAddress());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.docname, visitModel.getDocumentName());
            //added
        contentValues.put(VisitModel.VisitOrTravel_IsSent, visitModel.getIsSent());

        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.t_code,visitModel.getVisitCode());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.update(VisitModel.t_table, contentValues, VisitModel.t_travelid + "= ?", args);
    }
    public void UpdateWebTravel(VisitModel visitModel) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitModel.getTravelID())};
        contentValues.put(VisitModel.t_amnt, visitModel.getAmount());
        contentValues.put(VisitModel.incid, visitModel.getIncidentID());
        contentValues.put(VisitModel.iscompltd, visitModel.getIsCompleted());
        contentValues.put(VisitModel.km, visitModel.getKilometer());
        contentValues.put(VisitModel.endlat, visitModel.getEndLat());
        contentValues.put(VisitModel.transportmodeID, visitModel.getTransportMode());
        contentValues.put(VisitModel.transportmode, visitModel.getTransportModeText());
        contentValues.put(VisitModel.endlong, visitModel.getEndLong());
        contentValues.put(VisitModel.endadrs, visitModel.getEndAddress());
        contentValues.put(VisitModel.duration, visitModel.getDuration());
        contentValues.put(VisitModel.lmb, visitModel.getLastmodifiedby());
        //contentValues.put(VisitModel.maporothr,visitModel.getMaporOther());
        contentValues.put(VisitModel.lma, visitModel.getLastModifiedat());
        contentValues.put(VisitModel.travelorcallflag, visitModel.getTravelrVisitFlag());
        contentValues.put(VisitModel.statadrs, visitModel.getStartAddress());
        contentValues.put(VisitModel.endadrs,visitModel.getEndAddress());
        contentValues.put(VisitModel.travelwebid,visitModel.getTravelID());
        contentValues.put(VisitModel.isServer,visitModel.getIsServer());
        contentValues.put(VisitModel.filePath, visitModel.getFilePath());
        contentValues.put(VisitModel.fileName,visitModel.getFileName());
        contentValues.put(VisitModel.VisitOrTravel_IsSent, visitModel.getIsSent());
        contentValues.put(VisitModel.isfileexist, visitModel.getIsFileExist());
        contentValues.put(VisitModel.checkout, visitModel.getCheckOutDate());
        contentValues.put(VisitModel.t_code,visitModel.getVisitCode());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.update(VisitModel.t_table, contentValues, VisitModel.travelwebid + "= ?", args);
    }
    public void TravelInsertorUpdate(VisitModel modlobj) {

        String query = " SELECT  * FROM " + VisitModel.t_table  + " WHERE " + VisitModel.travelwebid  + "  =" + modlobj.getTravelID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateWebTravel(modlobj);
        } else {
            InsertWebTravel(modlobj);
        }
        c.close();
    }

    public int Visit_IsCompleted_Count() {
        String countQuery = "SELECT  " + VisitModel.iscompltd + " from " + VisitModel.table + " where " + VisitModel.iscompltd + " = " + 1 + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int IsLocalVendorRequired(int id) {
        String countQuery = "SELECT  " + VisitModel.islocalvendor  + " from " + VisitModel.table + " where " + VisitModel.incid + " = " + id +  " and " + VisitModel.islocalvendor + " = 1";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int Travel_IsCompleted_Count() {
        String countQuery = "SELECT  " + VisitModel.iscompltd + " from " + VisitModel.t_table + " where " + VisitModel.iscompltd + " = " + 1 + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
         return cnt;
    }
    public int IsVisitCompleted(int incid,int userid) {
        String countQuery = "SELECT  " + VisitModel.statusID  + " from " + VisitModel.table  + " where " + VisitModel.statusID + " = " + 7 + " and " + VisitModel.incid + "= "+ incid + " and " + VisitModel.userid + "= "+userid;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

  /* public boolean ResourceCheck(int id){
        String sql= "select " + ResourceModel.resourceid  + " from  " + ResourceModel.table  + " where "+ ResourceModel.resourceid  + " =" +id ;
        Cursor c=db.rawQuery(sql,null);
        try {
            if (c.moveToFirst()) {
            return true;
        } else {
            return false;
        }
        }
        finally{
            c.close();
        }
    }
*/
    public void VisitInsertorUpdate(VisitModel modlobj) {

        String query = " SELECT  * FROM " + VisitModel.table + " WHERE " + VisitModel.webid + "  =" + modlobj.getWebID() + "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            UpdateWebVisit(modlobj);
        } else {
            InsertWebVisitUpdate(modlobj);
        }
        c.close();
    }


    public Integer[] checklistline(String array) {


        String[] items = array.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        Integer[] results = new Integer[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
            }
        }

        return results;
    }

    public void UpdateWebID(VisitModel visitobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitobj.getID())};
        //added
        contentValues.put(VisitModel.VisitOrTravel_IsSent,0);

        contentValues.put(VisitModel.webid, visitobj.getWebID());
        contentValues.put(VisitModel.t_code, visitobj.getVisitCode());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.update(VisitModel.table, contentValues, VisitModel.id + "= ?", args);
    }
    public void UpdateErrorMessage(VisitModel visitobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitobj.getID())};
        //added
        contentValues.put(VisitModel.VisitOrTravel_IsSent,1);
        contentValues.put(VisitModel.iscompltd,1);
        contentValues.put(VisitModel.errorcode,visitobj.getErrorCode());
        contentValues.put(VisitModel.errormessage,visitobj.getErrorMessage());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.update(VisitModel.table, contentValues, VisitModel.id + "= ?", args);
    }
    public int IsVisitExist() {
        String countQuery = "SELECT  * FROM " + VisitModel.table + " where " + VisitModel.VisitOrTravel_IsSent + " = " + 1 + " and " + VisitModel.isfileexist + " =" + 0 + " and " + VisitModel.errorcode + " =" + 0 +  "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public Integer  getFlag(int id) {
        String countQuery = "SELECT  "+ VisitModel.maporothr +" FROM " +VisitModel.t_table   + " where " + VisitModel.travelwebid  + " =" + id;
        Cursor cursor = db.rawQuery(countQuery, null);
        int  flag=0;
        if(cursor.moveToFirst()) {
            flag = cursor.getInt(0);
        } cursor.close();
        return flag;
    }
    public int IsTravelExist() {
        String countQuery = "SELECT  * FROM " + VisitModel.t_table + " where " + VisitModel.VisitOrTravel_IsSent + " = " + 1 + " and " + VisitModel.isfileexist + " =" + 0 + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public int IsVisitFileExist() {
        String countQuery = "SELECT  * FROM " + VisitModel.table + " where " + VisitModel.isfileexist  + " = " + 1 + "";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public int IsNormalTravelFileExist() {
        String countQuery = "SELECT  * FROM " + VisitModel.t_table  + " where " + VisitModel.isfileexist  + " = " + 1 + " and "  + VisitModel.maporothr +" =" + 2;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public int IsMapTravelFileExist() {
        String countQuery = "SELECT  * FROM " + VisitModel.t_table  + " where " + VisitModel.isfileexist  + " = " + 1 + " and "  + VisitModel.maporothr +" =" + 1;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public List<VisitModel> TravelData() {
        String query = " SELECT "
                + VisitModel.t_travelid + " ,"
                + VisitModel.travelwebid + ","
                + VisitModel.incid + ","
                + VisitModel.checkin + " ,"
                + VisitModel.checkout + " ,"
                + VisitModel.transportmodeID + " ,"
                + VisitModel.userid + " ,"
                + VisitModel.mapfilepath + " ,"
                + VisitModel.orginalfilepath + " ,"
                + VisitModel.destinationfilename + " ,"
                + VisitModel.lmb + " ,"
                + VisitModel.cb + " ,"
                + VisitModel.km + " ,"
                + VisitModel.t_amnt + " ,"
                + VisitModel.isfileexist + " ,"
                + VisitModel.filePath + "  ,"
                + VisitModel.fileName + " , "
                + VisitModel.ca + " ,"
                + VisitModel.lma + " ,"
                + VisitModel.statadrs + " ,"
                + VisitModel.endadrs +
                "  FROM " + VisitModel.t_table +
                " WHERE " + VisitModel.VisitOrTravel_IsSent + " =" + 1 + " and " + VisitModel.isfileexist + " =" + 0 + "";
        Cursor cursor = db.rawQuery(query, null);
        List<VisitModel> listobj = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    VisitModel visitobj = new VisitModel();
                    visitobj.setMapFilePath(cursor.getString(cursor.getColumnIndex(VisitModel.mapfilepath)));
                    visitobj.setOrginMapFileName(cursor.getString(cursor.getColumnIndex(VisitModel.orginalfilepath)));
                    visitobj.setDestinationMapFileName(cursor.getString(cursor.getColumnIndex(VisitModel.destinationfilename)));
                    visitobj.setAmount(cursor.getString(cursor.getColumnIndex(VisitModel.t_amnt)));
                    visitobj.setKilometer(cursor.getString(cursor.getColumnIndex(VisitModel.km)));
                    visitobj.setID(cursor.getInt(cursor.getColumnIndex(VisitModel.t_travelid)));
                    visitobj.setIncidentID(cursor.getInt(cursor.getColumnIndex(VisitModel.incid)));
                    visitobj.setTravelID(cursor.getInt(cursor.getColumnIndex(VisitModel.travelwebid)));
                    visitobj.setCheckInDate(cursor.getString(cursor.getColumnIndex(VisitModel.checkin)));
                    visitobj.setCheckOutDate(cursor.getString(cursor.getColumnIndex(VisitModel.checkout)));
                    visitobj.setTransportMode(cursor.getInt(cursor.getColumnIndex(VisitModel.transportmodeID)));
                    visitobj.setUserID(cursor.getInt(cursor.getColumnIndex(VisitModel.userid)));
                    visitobj.setLastmodifiedby(cursor.getInt(cursor.getColumnIndex(VisitModel.lmb)));
                    visitobj.setCreatedby(cursor.getInt(cursor.getColumnIndex(VisitModel.cb)));
                    visitobj.setFileName(cursor.getString(cursor.getColumnIndex(VisitModel.fileName)));
                    visitobj.setFilePath(cursor.getString(cursor.getColumnIndex(VisitModel.filePath)));
                    visitobj.setCreatedat(cursor.getString(cursor.getColumnIndex(VisitModel.ca)));
                    visitobj.setLastModifiedat(cursor.getString(cursor.getColumnIndex(VisitModel.lma)));
                    visitobj.setStartAddress(cursor.getString(cursor.getColumnIndex(VisitModel.statadrs)));
                    visitobj.setEndAddress(cursor.getString(cursor.getColumnIndex(VisitModel.endadrs)));
                    listobj.add(visitobj);
                } while (cursor.moveToNext());

            }
        }
        if (cursor != null || !cursor.isClosed()) {
            cursor.close();
        }
        cursor.setNotificationUri(mcontext.getContentResolver(), VisitDAO_URI);
        return listobj;
    }
    public int IsVisitAssigned(int userid,int incid) {
        String countQuery = "SELECT  " + ResourceModel.resourceid  + " from " + ResourceModel.table  + " where " + ResourceModel.resourceid + "= "+userid + " and " + ResourceModel.incid + "= "+ incid;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public void UpdateTravelWebID(VisitModel visitobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitobj.getID())};
        contentValues.put(VisitModel.travelwebid , visitobj.getTravelID());
        contentValues.put(VisitModel.VisitOrTravel_IsSent,0);
        contentValues.put(VisitModel.t_code, visitobj.getVisitCode());
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
        db.update(VisitModel.t_table, contentValues, VisitModel.t_travelid + "= ?", args);
    }
    public List<VisitModel> FileTravel() {

        String query = " SELECT " + VisitModel.docpath  + " ,"
                + VisitModel.incid + " ,"
                + VisitModel.userid + " ,"
                + VisitModel.travelwebid + " ,"
                + VisitModel.t_travelid + " from "
                + VisitModel.t_table + " where " + VisitModel.isfileexist  + " =" + 1 + " and "  + VisitModel.maporothr +" =" + 2;

        Cursor cursor = db.rawQuery(query, null);
        List<VisitModel> visitlist = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    VisitModel visitModel = new VisitModel();
                    visitModel.setTravelID(cursor.getInt(cursor.getColumnIndex(VisitModel.travelwebid)));
                    visitModel.setDocumentPath(cursor.getString(cursor.getColumnIndex(VisitModel.docpath)));
                    visitModel.setUserID(cursor.getInt(cursor.getColumnIndex(VisitModel.userid)));
                    visitModel.setIncidentID(cursor.getInt(cursor.getColumnIndex(VisitModel.incid)));
                    visitModel.setID(cursor.getInt(cursor.getColumnIndex(VisitModel.t_travelid)));
                    visitlist.add(visitModel);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return visitlist;
    }

    public List<VisitModel> MapFile() {

        String query = " SELECT " + VisitModel.maporginfilepath  + " ,"
                + VisitModel.incid + " ,"
                + VisitModel.mapdestifilepath  + " ,"
                + VisitModel.userid + " ,"
                + VisitModel.travelwebid + " ,"
                + VisitModel.t_travelid + " from "
                + VisitModel.t_table + " where " + VisitModel.isfileexist  + " =" + 1 + " and "  + VisitModel.maporothr +" =" + 1;

        Cursor cursor = db.rawQuery(query, null);
        List<VisitModel> visitlist = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    VisitModel visitModel = new VisitModel();
                    visitModel.setTravelID(cursor.getInt(cursor.getColumnIndex(VisitModel.travelwebid)));
                    visitModel.setMapOrginFilepath(cursor.getString(cursor.getColumnIndex(VisitModel.maporginfilepath)));
                    visitModel.setDestiFilePath(cursor.getString(cursor.getColumnIndex(VisitModel.mapdestifilepath)));
                    visitModel.setUserID(cursor.getInt(cursor.getColumnIndex(VisitModel.userid)));
                    visitModel.setIncidentID(cursor.getInt(cursor.getColumnIndex(VisitModel.incid)));
                    visitModel.setID(cursor.getInt(cursor.getColumnIndex(VisitModel.t_travelid)));
                    visitlist.add(visitModel);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return visitlist;
    }

    public void UpdateFileTravelID(VisitModel visitobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitobj.getID())};
      //  contentValues.put(VisitModel.travelwebid, visitobj.getTravelID());
        contentValues.put(VisitModel.isfileexist ,visitobj.getIsFileExist());
        contentValues.put(VisitModel.filePath, visitobj.getFilePath() );
        contentValues.put(VisitModel.fileName,visitobj.getFileName());
        db.update(VisitModel.t_table, contentValues, VisitModel.t_travelid + "= ?", args);
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
    }
    public void UpdateMapFileTravelID(VisitModel visitobj) {
        ContentValues contentValues = new ContentValues();
        String[] args = {String.valueOf(visitobj.getID())};
        contentValues.put(VisitModel.travelwebid, visitobj.getTravelID());
        contentValues.put(VisitModel.isfileexist ,visitobj.getIsFileExist());
        contentValues.put(VisitModel.mapfilepath, visitobj.getMapFilePath());
        contentValues.put(VisitModel.orginalfilepath,visitobj.getOrginMapFileName());
        contentValues.put(VisitModel.destinationfilename,visitobj.getDestinationMapFileName());
        db.update(VisitModel.t_table, contentValues, VisitModel.t_travelid + "= ?", args);
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
    }
     public void DeleteUnwantedVisit(int id){
        db.delete(VisitModel.table,VisitModel.id + " ="+id+"",null);
        mcontext.getContentResolver().notifyChange(VisitDAO_URI, null);
    }
}