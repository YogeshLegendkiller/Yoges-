package precisioninfomatics.servicefirst.DBhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.acl.LastOwnerException;

import precisioninfomatics.servicefirst.Activities.IncidentView;
import precisioninfomatics.servicefirst.Activities.LocalVendor;
import precisioninfomatics.servicefirst.Fragments.CustomerBranch;
import precisioninfomatics.servicefirst.Fragments.GeneralClaim;
import precisioninfomatics.servicefirst.Fragments.Visit;
import precisioninfomatics.servicefirst.Model.ChartModel;
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.CustomerBranchModel;
import precisioninfomatics.servicefirst.Model.CustomerListModel;
import precisioninfomatics.servicefirst.Model.DeleteModel;
import precisioninfomatics.servicefirst.Model.FileModel;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Model.GpsModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.Model.LastModifiedModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Model.LoginModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.PartModel;
import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.Model.ResourceModel;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.SpareStatusModel;
import precisioninfomatics.servicefirst.Model.TransportModeModel;
import precisioninfomatics.servicefirst.Model.VehicleModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;

/**
 * Created by 4264 on 08-12-2016.
 */
public class SFTables extends SQLiteOpenHelper {
    private static SFTables instance;
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "ServiceFirst.db";
  private String VisitTable=" CREATE TABLE "  + VisitModel.table + "("
            + VisitModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + VisitModel.checkin + " TEXT ,"
            + VisitModel.checkout + " TEXT ,"
            + VisitModel.t_code + " TEXT ,"
            + VisitModel.findsatsite + " TEXT ,"
            + VisitModel.actiontaken + " TEXT ,"
            + VisitModel.calslipno + " TEXT ,"
            + VisitModel.nextvisitdate + " TEXT ,"
            + VisitModel.isfileexist +" INTEGER ,"
            + VisitModel.userid + " INTEGER ,"
            + VisitModel.isServer + " INTEGER  ,"
            + VisitModel.errorcode + " INTEGER ,"
            + VisitModel.errormessage +  " TEXT ,"
            + VisitModel.callslipurl + " TEXT ,"
            + VisitModel.islocalvendor + " INTEGER ,"
            + VisitModel.localvendor + " TEXT ,"
            + VisitModel.itmserialnomap + " TEXT ,"
            + VisitModel.partisue + " TEXT ,"
            + VisitModel.customer_branchid + " INTEGER ,"
            + VisitModel.checklistlineid  + " TEXT ,"
            + VisitModel.localvendorid + " INTEGER ,"
            + VisitModel.status + " TEXT ,"
            + VisitModel.statusID + " INTEGER ,"
            + VisitModel.pendingclas + " TEXT ,"
            + VisitModel.pendgclasid + " INTEGER ,"
            + VisitModel.observation + " INTEGER ,"
            + VisitModel.cutofdate + " TEXT ,"
            + VisitModel.webid + " INTEGER ,"
            + VisitModel.t_part + " TEXT ,"
            + VisitModel.remarks  + " TEXT ,"
            + VisitModel.sparepart + " TEXT ,"
            + VisitModel.VisitOrTravel_IsSent + " INTEGER ,"
            + VisitModel.iscompltd + " INTEGER ,"
            + VisitModel.ca + " TEXT ,"
            + VisitModel.lma + " TEXT ,"
            + VisitModel.duration + " TEXT ,"
            + VisitModel.cb + " INTEGER  ,"
            + VisitModel.lmb + " INTEGER  ,"
            + VisitModel.docpath + " TEXT ,"
            + VisitModel.docname + " TEXT ,"
            + VisitModel.incid + " INTEGER , "
            + VisitModel.travelorcallflag + " INTEGER "
            + ");";
  private String TravelTable= " CREATE TABLE " + VisitModel.t_table+ " ("
            + VisitModel.t_travelid + "  INTEGER PRIMARY KEY AUTOINCREMENT , "
            + VisitModel.travelwebid + " INTEGER ,"
            + VisitModel.incid + " INTEGER ,"
            + VisitModel.t_startlat + " TEXT ,"
            + VisitModel.endlat + " TEXT ,"
            + VisitModel.VisitOrTravel_IsSent + " INTEGER ,"
            + VisitModel.t_amnt + " TEXT ,"
            + VisitModel.isServer + " INTEGER  ,"
            + VisitModel.userid + " INTEGER ,"
            + VisitModel.duration + " TEXT ,"
            + VisitModel.maporginfilepath + " TEXT ,"
            + VisitModel.mapdestifilepath + " TEXT ,"
            + VisitModel.mapfilepath + " TEXT ,"
            + VisitModel.isfileexist +" INTEGER ,"
            + VisitModel.statadrs + " TEXT ,"
            + VisitModel.estdistance + " TEXT ,"
            + VisitModel.orginalfilepath + " TEXT ,"
            + VisitModel.destinationfilename + " TEXT ,"
             + VisitModel.endadrs + " TEXT ,"
            + VisitModel.checkin + " TEXT ,"
            + VisitModel.filePath + " TEXT ,"
            + VisitModel.fileName + " TEXT ,"
            + VisitModel.checkout + " TEXT ,"
            + VisitModel.docname + " TEXT ,"
            + VisitModel.docpath + " TEXT ,"
            + VisitModel.cb + " INTEGER  ,"
            + VisitModel.transportmode + " TEXT ,"
            + VisitModel.transportmodeID + " INTEGER ,"
            + VisitModel.maporothr + " INTEGER ,"
            + VisitModel.ca + " TEXT ,"
            + VisitModel.lmb + " INTEGER ,"
            + VisitModel.lma + " TEXT ,"
            + VisitModel.visitortravel + " TEXT ,"
            + VisitModel.t_code  + " TEXT ,"
            + VisitModel.iscompltd + " INTEGER ,"
            + VisitModel.km + " TEXT ,"
            + VisitModel.startlong + " TEXT ,"
            + VisitModel.endlong  + " TEXT ,"
            + VisitModel.travelorcallflag + " INTEGER "
            + ");";

    private String StatusTable=" CREATE TABLE " + VisitStatusModel.table + " ("
            + VisitStatusModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + VisitStatusModel.status + " TEXT ,"
            + VisitStatusModel.statusid  + " INTEGER "
            + ");";

   private String VehicleRegistrationTable=" CREATE TABLE " + VehicleModel.table + " ("
           + VehicleModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
           + VehicleModel.regno + " TEXT ,"
           + VehicleModel.licno + " TEXT ,"
           + VehicleModel.lictype + " TEXT ,"
           + VehicleModel.userid + " INTEGER ,"
           + VehicleModel.vehicletype + " INTEGER ,"
           + VehicleModel.lictypeID + " INTEGER ,"
           + VehicleModel.licexpdate + " TEXT ,"
           + VehicleModel.ins_expdate + " TEXT " + ");";

    private String PartTable=" CREATE TABLE " + PartModel.table + " ("
            +PartModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            +PartModel.partno + " TEXT ,"
            +PartModel.specification + " TEXT ,"
            +PartModel.productno + " TEXT ,"
            +PartModel.incid + " INTEGER ,"
            +PartModel.brand + " TEXT ,"
            +PartModel.model + " TEXT ,"
            +PartModel.category + " TEXT ,"
            +PartModel.webid + " INTEGER  "+ ");";
   private String SpareTable=" CREATE TABLE " + SpareModel.table + "("
           + SpareModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
           + SpareModel.webid + " INTEGER ,"
           + SpareModel.issued + " TEXT ,"
           + SpareModel.partno + " TEXT ,"
           + SpareModel.samepart + " INTEGER ,"
           + SpareModel.updateid + " INTEGER ,"
           + SpareModel.partstatus + " TEXT ,"
           + SpareModel.defectiveserialno + " TEXT ,"
           + SpareModel.visitprimaryID + " INTEGER ,"
           + SpareModel.requestedspec + " TEXT ,"
           + SpareModel.visitwebid + " INTEGER ,"
           + SpareModel.editflag + " INTEGER ,"
            + SpareModel.statusid + " INTEGER  ,"
           + SpareModel.brandname + " TEXT ,"
           + SpareModel.modelname + " TEXT ,"
           + SpareModel.catname + " TEXT ,"
           + SpareModel.specification + " TEXT ,"
           + SpareModel.partid + " INTEGER ,"
           + SpareModel.incid + " INTEGER ,"
           + SpareModel.advreturn + " INTEGER ,"
           + SpareModel.remarks + " TEXT "+ ");";
    private String IncidentTable=" CREATE TABLE " + IncidentModel.inc_table + "("
            + IncidentModel.inc_id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + IncidentModel.inc_regID  + " INTEGER ,"
            + IncidentModel.inc_visitstatus + " TEXT ,"
            + IncidentModel.inc_companyname  + " TEXT ,"
            + IncidentModel.inc_customeraddrs + " TEXT ,"
            + IncidentModel.inc_partreq + " INTEGER ,"
            + IncidentModel.inc_gc + " INTEGER ,"
            + IncidentModel.inc_lmd + " TEXT ,"
            + IncidentModel.inc_lat + " TEXT ,"
            + IncidentModel.inc_long + " TEXT ,"
            + IncidentModel.probdescription + " TEXT ,"
            + IncidentModel.inc_instalcall + " INTEGER ,"
            + IncidentModel.inc_status + " TEXT ,"
            + IncidentModel.inc_statusID + " INTEGER ,"
            + IncidentModel.inc_localvendor + " INTEGER ,"
            + IncidentModel.inc_probcategory  + " TEXT ,"
            + IncidentModel.inc_incCode + " TEXT ,"
           +  IncidentModel.inc_cd  + " TEXT "+ ");";
    private String IncidentViewTable=" CREATE TABLE "+ IncidentViewModel.table + " ("
            + IncidentViewModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + IncidentModel.inc_callorgin + " TEXT ,"
            + IncidentModel.inc_regID  + " INTEGER ,"
            + IncidentModel.serviceclassification + " TEXT ,"
            + IncidentModel.sbu + " TEXT ,"
            + IncidentModel.servicecategory + " TEXT ,"
            + IncidentModel.bu + " TEXT ,"
            + IncidentModel.servicesubcategory + " TEXT ,"
            + IncidentModel.subBu + " TEXT ,"
            + IncidentModel.probdescription + " TEXT ,"
            + IncidentModel.priority + " TEXT ,"
            + IncidentModel.remarks + " TEXT ,"
            + IncidentModel.inc_companyname + " TEXT ,"
            + IncidentModel.inc_cb + " INTEGER ,"
            + IncidentViewModel.checklistid + " INTEGER ,"
            + IncidentModel.inc_lmb + " INTEGER ,"
            + IncidentModel.inc_lmd + " TEXT ,"
            + IncidentViewModel.contractcode + " TEXT ,"
            + IncidentViewModel.contaract_sdate + " TEXT ,"
            + IncidentViewModel.contract_enddate + " TEXT ,"
            + IncidentViewModel.acm + " TEXT ,"
            + IncidentViewModel.conatractstatus + " TEXT ,"
            + IncidentViewModel.inc_probcategory + " TEXT ,"
            + IncidentViewModel.customercode + " TEXT ,"
            + IncidentViewModel.customerbranchaddress + " TEXT ,"
            + IncidentViewModel.contactname + " TEXT ,"
            + IncidentViewModel.designation + " TEXT ,"
            + IncidentViewModel.mailid + " TEXT ,"
            + IncidentViewModel.phno + " TEXT ,"
            + IncidentViewModel.serviceprovider + " TEXT ,"
            + IncidentViewModel.assetcategoryname + " TEXT ,"
            + IncidentViewModel.serialno + " TEXT ,"
            + IncidentViewModel.baseconfig + " TEXT ,"
            + IncidentViewModel.currentconfig + " TEXT ,"
            + IncidentViewModel.partno + " TEXT ,"
            + IncidentViewModel.periodtype + " TEXT ,"
            + IncidentViewModel.periodfrom + " TEXT ,"
            + IncidentViewModel.periodto + " TEXT "
            +");";
    private String PendingClassification=" CREATE TABLE " + PendingClassificationModel.table + " ("
            + PendingClassificationModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + PendingClassificationModel.text + " TEXT ,"
            + PendingClassificationModel.webid  + " INTEGER "
            + ");";
    private String TransportModeTable=" CREATE TABLE " + TransportModeModel.table + " ("
            + TransportModeModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + TransportModeModel.name + " TEXT ,"
            + TransportModeModel.webid  + " INTEGER "
            + ");";
    private String GPSTable=" CREATE TABLE " + GpsModel.Table + " ("
            +  GpsModel.ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +  GpsModel.LAT + " DOUBLE  UNIQUE ,"
            +  GpsModel.flag + " INTEGER ,"
            +  GpsModel.Long   + " DOUBLE  UNIQUE "
            + ");";
    private String SpareStatus=" CREATE TABLE " + SpareStatusModel.table + " ("
            + SpareStatusModel.primaryid + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + SpareStatusModel.statusname + " TEXT ,"
             + SpareStatusModel.sequence + " INTEGER ,"
            + SpareStatusModel.statusid  + " INTEGER "
            + ");";
    private String LoginTable=" CREATE TABLE "+ LoginModel.table + "  ("
            + LoginModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + LoginModel.userid + " INTEGER ,"
            + LoginModel.empcode + " TEXT ,"
            + LoginModel.empname + " TEXT ,"
            + LoginModel.moduleID + " INTEGER ,"
          //  + LoginModel.FcmRegID + " TEXT ,"
            + LoginModel.modulename + " TEXT ,"
            + LoginModel.roleid + " INTEGER ,"
            + LoginModel.rolename + " TEXT ,"
            + LoginModel.userloginid + " INTEGER "+ ");";
    private String ResourceTable=" CREATE TABLE " + ResourceModel.table + " ("
            + ResourceModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + ResourceModel.resourceid + " INTEGER ,"
            + ResourceModel.resourcename + " TEXT ,"
            + ResourceModel.telephone + " TEXT ,"
            + ResourceModel.email + " TEXT ,"
            + ResourceModel.statusid  + " INTEGER ,"
            + ResourceModel.photourl + " TEXT ,"
            + ResourceModel.localpath + " TEXT ,"
            + ResourceModel.designation + " TEXT ,"
            + ResourceModel.status + " TEXT ,"
            + ResourceModel.resourcestatus + " TEXT ,"
            + ResourceModel.resourcealocid  + " INTEGER ,"
            + ResourceModel.instruction + " TEXT ,"
            + ResourceModel.assignedate + " TEXT ,"
            + ResourceModel.incid + " INTEGER "
            + ");";
    private String LocalVendorTable=" CREATE TABLE "+ LocalVendorModel.table + " ("
            + LocalVendorModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + LocalVendorModel.webid + " INTEGER ,"
            + LocalVendorModel.incID + " INTEGER ,"
            + LocalVendorModel.invoiceno + " TEXT ,"
            + LocalVendorModel.invoicedate + " TEXT ,"
            + LocalVendorModel.sparecharge + " INTEGER ,"
            + LocalVendorModel.is_fileexist + " INTEGER ,"
            + LocalVendorModel.attachpath + " TEXT ,"
            + LocalVendorModel.userorvendorid + " INTEGER ,"
            + LocalVendorModel.cb + " INTEGER ,"
            + LocalVendorModel.lmb + " INTEGER ,"
            + LocalVendorModel.cd + " TEXT ,"
           // + LocalVendorModel.invoiceitems + " TEXT ,"
            + LocalVendorModel.LV_Total + " TEXT ,"
            + LocalVendorModel.issent   + " INTEGER ,"
            + LocalVendorModel.ld + " TEXT ,"
            + LocalVendorModel.Resourcename + " TEXT ,"
            + LocalVendorModel.chargetype + " INTEGER ,"
            + LocalVendorModel.targettype + " INTEGER ,"
            + LocalVendorModel.servicecharge + " INTEGER  ,"
            + LocalVendorModel.localfilename + " TEXT ,"
            + LocalVendorModel.localfilepath + " TEXT ,"
            + LocalVendorModel.attachname + " TEXT "
            + ");";
    private String PartIssueTable=" CREATE TABLE "+ PartIssueModel.table + " ("
            + PartIssueModel.id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + PartIssueModel.issuedpartno + " TEXT ,"
            + PartIssueModel.serialno + " TEXT ,"
            + PartIssueModel.statusname + " TEXT ,"
            + PartIssueModel.curentpartstatusname + " TEXT , "
            + PartIssueModel.reqstatusID + " INTEGER ,"
            + PartIssueModel.currentpartstatusID + " INTEGER ,"
            + PartIssueModel.issuedwarehouseid + " INTEGER ,"
            + PartIssueModel.cb + " INTEGER ,"
            + PartIssueModel.lmb + " INTEGER ,"
            + PartIssueModel.cd + " TEXT ,"
            + PartIssueModel.ld + " TEXT ,"
            + PartIssueModel.reqid + " INTEGER ,"
            + PartIssueModel.partstatusid + " INTEGER ,"
            + PartIssueModel.issuedpartid + " INTEGER ,"
            + PartIssueModel.squence + " INTEGER ,"
            + PartIssueModel.updatedid + " INTEGER ,"
            + PartIssueModel.incid + " INTEGER ,"
            + PartIssueModel.webid + " INTEGER "
            +");";
    private String SerialNumberTable=" CREATE TABLE "+ SerialNumberMapModel.table + " ("
            + SerialNumberMapModel.id + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + SerialNumberMapModel.checklistlineid + " INTEGER ,"
            + SerialNumberMapModel.incid + " INTEGER ,"
            + SerialNumberMapModel.serialno + " TEXT "
            +");";
    private String SerialMapTable=" CREATE TABLE "+ SerialNumberMapModel.SerialMapTable + " ("
            + SerialNumberMapModel.id + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + SerialNumberMapModel.checklistlineid + " INTEGER ,"
            + SerialNumberMapModel.incid + " INTEGER ,"
       //     + SerialNumberMapModel.updatedid + " INTEGER ,"
            + SerialNumberMapModel.visitid + " INTEGER ,"
            + SerialNumberMapModel.visitwebid + " INTEGER ,"
            + SerialNumberMapModel.shiptoserialno + " TEXT ,"
            + SerialNumberMapModel.serialno + " TEXT "
            +");";
    private String GeneralClaimTable=" CREATE TABLE "+ GeneralClaimModel.GC_Table + " ("
            + GeneralClaimModel.GC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + GeneralClaimModel.GC_FromDate  + " TEXT ,"
            + GeneralClaimModel.GC_ToDate + " TEXT ,"
             + GeneralClaimModel.GC_ClaimID + " INTEGER ,"
            + GeneralClaimModel.GC_isServer + " INTEGER ,"
             + GeneralClaimModel.GC_filename + " TEXT ,"
            + GeneralClaimModel.GC_ca + " TEXT ,"
            + GeneralClaimModel.GC_cbName + " TEXT ,"
            + GeneralClaimModel.GC_claimcost + " TEXT ,"
            + GeneralClaimModel.GC_Total + " DOUBLE ,"
            + GeneralClaimModel.GC_cb + " INTEGER ,"
            + GeneralClaimModel.GC_FromLoc + " TEXT ,"
            + GeneralClaimModel.Gc_ToLoc + " TEXT ,"
            + GeneralClaimModel.GC_Code + " TEXT ,"
           + GeneralClaimModel.GC_lma + " TEXT ,"
            + GeneralClaimModel.GC_lmb + " INTEGER ,"
            + GeneralClaimModel.Gc_IncidentID + " INTEGER ,"
            + GeneralClaimModel.GC_ServerPath + " TEXT ,"
             + GeneralClaimModel.GC_Filepath  + " TEXT ,"
            + GeneralClaimModel.GC_issent  + " INTEGER ,"
            + GeneralClaimModel.GC_isexist  + " INTEGER "
            +");";
    private String DeleteTable=" CREATE TABLE "+ DeleteModel.DeleteTable + " ("
            + DeleteModel.DeleteID + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + DeleteModel.WeBID + " INTEGER ,"
            + DeleteModel.flag  + " INTEGER , "
            + DeleteModel.issent + " INTEGER "
            +");";
    private String PieTable=" CREATE TABLE "+ ChartModel.Pie_Table + " ("
            + ChartModel.ID + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + ChartModel.Data + " INTEGER ,"
             + ChartModel.Label + " TEXT "
            +");";
    private String BarTable=" CREATE TABLE "+ ChartModel.Bar_Table + " ("
            + ChartModel.ID + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + ChartModel.Data + " INTEGER ,"
            + ChartModel.Label + " TEXT "
            +");";
    private String SummaryTable=" CREATE TABLE "+ ChartModel.Summary_Table + " ("
            + ChartModel.ID + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + ChartModel.Data + " INTEGER ,"
            + ChartModel.Label + " TEXT "
            +");";
    private String ClaimFieldTable=" CREATE TABLE "+ ClaimFieldModel.ClaimFieldTable + " ("
            + ClaimFieldModel.ID + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + ClaimFieldModel.fieldpairid + " INTEGER ,"
            + ClaimFieldModel.fieldname + " TEXT "
            +");";
    private String CustomerListTable=" CREATE TABLE "+ CustomerListModel.table + " ("
            + CustomerListModel.id + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + CustomerListModel.customerbranchid + " INTEGER ,"
            + CustomerListModel.incidentid + " INTEGER ,"
            + CustomerListModel.flag + " INTEGER ,"
            + CustomerListModel.customerbranchname + " TEXT "
            +");";
    private String CustomerBranchTable=" CREATE TABLE "+ CustomerBranchModel.CustomerBranchTable + " ("
            + CustomerBranchModel.CustomerBranchPrimaryID  + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + CustomerBranchModel.CustomerBranchid + " TEXT  ,"
            + CustomerBranchModel.RegID + " INTEGER ,"
            + CustomerBranchModel.CustomerBranchname + " TEXT "
            +");";
    private String FileTable=" CREATE TABLE "+ FileModel.FileTable + " ("
            + FileModel.FileID  + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + FileModel.Filename + " TEXT  ,"
            + FileModel.Incidentid + " INTEGER ,"
            + FileModel.Filepath  + " TEXT ,"
            + FileModel.Filetype + " INTEGER "
            +");";
    private String LastModifiedTable=" CREATE TABLE "+ LastModifiedModel.lmd_table + " ("
            + LastModifiedModel.lmd_id  + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + LastModifiedModel.lmd_lmd + " TEXT  ,"
            + LastModifiedModel.lmd_incid + " INTEGER "
            +");";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(VisitTable);
        sqLiteDatabase.execSQL(StatusTable);
        sqLiteDatabase.execSQL(SpareTable);
        sqLiteDatabase.execSQL(CustomerBranchTable);
        sqLiteDatabase.execSQL(VehicleRegistrationTable);
        sqLiteDatabase.execSQL(SerialNumberTable);
        sqLiteDatabase.execSQL(TravelTable);
        sqLiteDatabase.execSQL(IncidentTable);
        sqLiteDatabase.execSQL(CustomerListTable);
        sqLiteDatabase.execSQL(ResourceTable);
        sqLiteDatabase.execSQL(DeleteTable);
        sqLiteDatabase.execSQL(PendingClassification);
        sqLiteDatabase.execSQL(PartTable);
        sqLiteDatabase.execSQL(GeneralClaimTable);
        sqLiteDatabase.execSQL(LoginTable);
        sqLiteDatabase.execSQL(LocalVendorTable);
        sqLiteDatabase.execSQL(PartIssueTable);
        sqLiteDatabase.execSQL(GPSTable);
        sqLiteDatabase.execSQL(PieTable);
        sqLiteDatabase.execSQL(FileTable);
        sqLiteDatabase.execSQL(BarTable);
        sqLiteDatabase.execSQL(IncidentViewTable);
        sqLiteDatabase.execSQL(SpareStatus);
        sqLiteDatabase.execSQL(SerialMapTable);
        sqLiteDatabase.execSQL(SummaryTable);
        sqLiteDatabase.execSQL(TransportModeTable);
        sqLiteDatabase.execSQL(ClaimFieldTable);
        sqLiteDatabase.execSQL(LastModifiedTable);
    }
    public static synchronized SFTables getHelper(Context context) {
        if (instance == null)
            instance = new SFTables(context.getApplicationContext());
        return instance;
    }

    private SFTables(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VisitTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StatusTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SpareTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CustomerBranchTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VehicleRegistrationTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SerialNumberTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TravelTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IncidentTable);
       // sqLiteDatabase.needUpgrade()
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CustomerListTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ResourceTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DeleteTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PendingClassification);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PartTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GeneralClaimTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LoginTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocalVendorTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PartIssueTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GPSTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PieTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FileTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BarTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IncidentViewTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SpareStatus);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SerialMapTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SummaryTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TransportModeTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClaimFieldTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LastModifiedTable);
    }
}
