package precisioninfomatics.servicefirst.Model;

import java.util.List;

/**
 * Created by 4264 on 08-12-2016.
 */

public class VisitModel {
    public static final String table = "visittable";
    public static final String id = "visitID";
    public static final String webid = "webid";
    public static final String checkin = "checkin";
    public static final String checkout = "checkot";
    public static final String errorcode="errorcode";
    public static final String errormessage="errormsg";
    public static final String partisue="ptisue";
    public static final String findsatsite = "findatsite";
    public static final String actiontaken = "at";
    public static final String calslipno = "calslno";
    public static final String nextvisitdate = "nextvdate";
    public static final String estdistance="estdistance";
    public static final String observation = "observation";
    public static final String mapfilepath="mapfilepath";
    public static final String maporginfilepath="orginpath";
    public static final String mapdestifilepath="destipath";
    public static final String cutofdate = "cutofdate";
    public static final String remarks = "remarks";
    public static final String iscompltd = "iscomp";
    public static final String localvendor = "lv";
    public static final String localvendorid = "lvid";
    public static final String status = "vstatus";
    public static final String statusID = "statusid";
    public static final String travelwebid = "travelwebid";
    public static final String maporothr = "maporotr";
    public static final String pendingclas = "pendclas";
    public static final String customer_branchid="cusbranchid";
    public static final String checklistlineid="checklistlineid";
    public static final String itmserialnomap="itemserialnomap";
    public static final String callslipurl="callslipurl";
    public static final String pendgclasid = "pendclasid";
    public static final String incid = "incid";
    public static final String isfileexist="isexist";
    public static final String isServer="isServer";
    //
    public static final String t_table = "t_table";
    public static final String t_travelid = "_id";
    public static final String t_incid = "inci_id";
    public static final String t_code="t_code";
    public static final String t_startlat = "Startlat";
    public static final String t_amnt = "amnt";
    public static final String t_part = "t_part";
    public static final String VisitOrTravel_IsSent="issent";
    public static final String islocalvendor = "islocalvendor";
    public static final String sparepart = "sparepart";
    public static final String userid = "UserID";
    public static final String statadrs = "statadrs";

    public static final String endadrs = "endadrs";
    public static final String endlat = "Endlat";
    public static final String endlong = "Endlong";
    public static final String km = "km";
    public static final String docpath = "docpath";
    public static final String duration = "duration";
    public static final String docname = "docname";
    //cb-createdby,lmb-lastmodifiedby,ca-createat,lma-lastmodifiedat
    public static final String cb = "dcb";
    public static final String lmb = "lmb";
    public static final String ca = "ca";
    public static final String lma = "lma";
    public static final String orginalfilepath = "orginalfilepath";
    public static final String destinationfilename="destifilename";
     public static final String visitortravel = "visitortravel";
    public static final String travelorcallflag = "visitflag";
    public static final String startlong = "startlong";
    public static final String transportmode = "transportmode";
    public static final String transportmodeID = "transportmodeID";
    public static final String TempID = "tempid";
    public static final String TempCheckin = "tempcheckin";
    public static final String TempCheckout = "tpcheckot";
    public static final String fileName="filename";
    public static final String filePath="filepath";
    public static final String TempMode="tmode";
    public static final String TempStartLat = "tpstartlat";
    public static final  String TempStartAddress = "tpstrtadrs";
    public static final String TempAtorEndAdr="tpatorend";
    public static final String TempStartLong = "tpstrtlong";
    public static final String TempWebID="tpwbid";
    public static final String TempKm="tempkm";
    public static final String TempEstimatedKilometer="tpestkm";
    public static final String TempMaporother = "tpmaporother";
    public static final String Tempduration="tempduration";
    public static final String TempAmnt="tempamnt";


    private Integer TransportModeID;
    private String TransportModeText;
    private Integer TravelMode;
    private Integer IsSent;
    private String MapFilePath;
    private String MapOrginFilepath;

    public String getMapFilePath() {
        return MapFilePath;
    }

    public void setMapFilePath(String mapFilePath) {
        MapFilePath = mapFilePath;
    }

    public String getMapOrginFilepath() {
        return MapOrginFilepath;
    }

    public void setMapOrginFilepath(String mapOrginFilepath) {
        MapOrginFilepath = mapOrginFilepath;
    }

    public String getDestiFilePath() {
        return DestiFilePath;
    }

    public void setDestiFilePath(String destiFilePath) {
        DestiFilePath = destiFilePath;
    }

    private String DestiFilePath;
    private String  OrginMapFileName;
    private String  DestinationMapFileName;

    public String getOrginMapFileName() {
        return OrginMapFileName;
    }

    public void setOrginMapFileName(String orginMapFileName) {
        OrginMapFileName = orginMapFileName;
    }

    public String getDestinationMapFileName() {
        return DestinationMapFileName;
    }

    public void setDestinationMapFileName(String destinationMapFileName) {
        DestinationMapFileName = destinationMapFileName;
    }

    public Integer getIsServer() {
        return IsServer;
    }

    public void setIsServer(Integer isServer) {
        IsServer = isServer;
    }

    private Integer IsServer;
    private Integer IsFileExist;

    public String getCallSlipUrl() {
        return CallSlipUrl;
    }

    public void setCallSlipUrl(String callSlipUrl) {
        CallSlipUrl = callSlipUrl;
    }

    private String CallSlipUrl;

    public String getItemserialnomap() {
        return itemserialnomap;
    }

    public void setItemserialnomap(String itemserialnomap) {
        this.itemserialnomap = itemserialnomap;
    }

    private String itemserialnomap;
  private List<SerialNumberMapModel>InstalledSerialNumber;

    public List<SerialNumberMapModel> getInstalledSerialNumber() {
        return InstalledSerialNumber;
    }

    public void setInstalledSerialNumber(List<SerialNumberMapModel> installedSerialNumber) {
        InstalledSerialNumber = installedSerialNumber;
    }

    public String getCheckListLineID() {
        return CheckListLineString;
    }

    public void setCheckListLineID(String checkListLineID) {
        CheckListLineString = checkListLineID;
    }
   private Integer ErrorCode;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(Integer errorCode) {
        ErrorCode = errorCode;
    }

    private String ErrorMessage;
    public Integer getCustmerBranchID() {
        return CustomerBranchID;
    }

    public void setCustmerBranchID(Integer custmerBranchID) {
        CustomerBranchID = custmerBranchID;
    }

    private Integer CustomerBranchID;
    private String  CheckListLineString;

    public Integer getIsFileExist() {
        return IsFileExist;
    }

    public void setIsFileExist(Integer isFileExist) {
        IsFileExist = isFileExist;
    }

    public String getPartIssue() {
        return PartIssue;
    }

    public void setPartIssue(String partIssue) {
        PartIssue = partIssue;
    }


    private List<PartIssueModel>SparePart;

    public void setSparePart(List<PartIssueModel> sparePart) {
        this.SparePart = sparePart;
    }

    private String PartIssue;

    public Integer getIsSent() {
        return IsSent;
    }

    public void setIsSent(Integer isSent) {
        IsSent = isSent;
    }

    public String getEstimatedDistance() {
        return EstimatedDistance;
    }

    public void setEstimatedDistance(String estimatedDistance) {
        EstimatedDistance = estimatedDistance;
    }
    private Integer[]CheckListLineID;

    public void setCheckListLineID(Integer[] checkListLineID) {
        CheckListLineID = checkListLineID;
    }

    private String EstimatedDistance;


    public Integer getMaporOther() {
        return TravelMode;
    }

    public void setMaporOther(Integer maporOther) {
        TravelMode = maporOther;
    }

    public Integer getTransportMode() {
        return TransportModeID;
    }

    public void setTransportMode(Integer transportMode) {
        TransportModeID = transportMode;
    }

    public String getTransportModeText() {
        return TransportModeText;
    }

    public void setTransportModeText(String transportModeText) {
        TransportModeText = transportModeText;
    }

    private String CheckInDate;
    private String FromLocation;

    public String getStartAddress() {
        return FromLocation;
    }

    public void setStartAddress(String startAddress) {
        this.FromLocation = startAddress;
    }

    public String getEndAddress() {
        return ToLocation;
    }
   private String VisitCode;

    public String getVisitCode() {
        return VisitCode;
    }

    public void setVisitCode(String visitCode) {
        VisitCode = visitCode;
    }

    public void setEndAddress(String endAddress) {
        this.ToLocation = endAddress;
    }

    private String ToLocation;
    private String CheckOutDate;
    private String VisitStatus;
    private String StartGeo;
    private String TypeofVehicle;
    private String FileName;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
       this.FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        this.FilePath = filePath;
    }

    private String FilePath;
    private Integer IsLocalVendorSupport;

    public Integer getIsLocalVendor() {
        return IsLocalVendorSupport;
    }

    public void setIsLocalVendor(Integer isLocalVendor) {
        IsLocalVendorSupport = isLocalVendor;
    }

    private Integer UserID;
    private String LocalVendorText;

    public String getLocalVendorText() {
        return LocalVendorText;
    }

    public void setLocalVendorText(String localVendorText) {
        LocalVendorText = localVendorText;
    }

    public Integer getLocalVendorID() {
        return LocalVendorID;
    }

    public void setLocalVendorID(Integer localVendorID) {
        LocalVendorID = localVendorID;
    }

    private Integer LocalVendorID;

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

    public String getSpareRequest() {
        return SpareRequest;
    }

    public void setSpareRequest(String sparePart) {
        SpareRequest = sparePart;
    }

    public String getPart() {
        return Parts;
    }

    public void setPart(String part) {
        Parts = part;
    }

    private String EndGeo;
    private String Parts;
    private String SpareRequest;


    private List<SpareModel> Part;

    public void setPart(List<SpareModel> part) {
        Part = part;
    }

    public String getTypeofVehicle() {
        return TypeofVehicle;
    }

    public void setTypeofVehicle(String typeofVehicle) {
        TypeofVehicle = typeofVehicle;
    }

    public String getEndGeo() {
        return EndGeo;
    }

    public void setEndGeo(String endGeo) {
        EndGeo = endGeo;
    }

    public String getStartGeo() {
        return StartGeo;
    }

    public void setStartGeo(String startGeo) {
        StartGeo = startGeo;
    }

    public String getAmount() {
        return ClaimAmount;
    }

    public void setAmount(String amount) {
        ClaimAmount = amount;
    }

    private String ClaimAmount;
    private Integer VisitStatusID;
    private String Path;
    private Integer TravelIncidentID;
    private Integer TravelID;
    private Integer TravelrVisitFlag;
    private String TravelOrVistText;
    private String StartLat;
    private String StartLong;
    private String EndLat;
    private String EndLong;
    private String Starttime;
    private String EndTime;
    private String Kilometers;
    private String Duration;
    private String CreatedDateTime;
    private String Distance;
    private String FindingsAtSite;
    private String ActionTaken;
    private String CallSlipNo;
    private String NextVisitDate;
    private Integer Observation;
    private String CutOffDate;
    private String Remarks;
    private Integer IsCompleted;
    private Integer AppID;
    private String PendingClassificationText;
    private Integer PendingClassificationID;
    private String LastModifiedDateTime;
    private Integer Createdby;
    private Integer Lastmodifiedby;
    private Integer VisitID;
    private Integer RegistrationID;
    private String EmployeeName;
    private String DocumentPath;
    private String DocumentName;


    public Integer getStatus() {
        return VisitStatusID;
    }

    public void setStatus(Integer status) {
        VisitStatusID = status;
    }
    public String getVisitStatus() {
        return VisitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        VisitStatus = visitStatus;
    }


    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }


    public String getPendingClassificationText() {
        return PendingClassificationText;
    }

    public void setPendingClassificationText(String pendingClassificationText) {
        PendingClassificationText = pendingClassificationText;
    }

    public Integer getPendingClassification() {
        return PendingClassificationID;
    }

    public void setPendingClassification(Integer pendingClassification) {
        PendingClassificationID = pendingClassification;
    }

    public Integer getWebID() {
        return VisitID;
    }

    public void setWebID(Integer webID) {
        VisitID = webID;
    }


    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }

    public String getDocumentPath() {
        return DocumentPath;
    }

    public void setDocumentPath(String documentPath) {
        DocumentPath = documentPath;
    }


    public String getTravelOrVistText() {
        return TravelOrVistText;
    }

    public void setTravelOrVistText(String travelOrVistText) {
        TravelOrVistText = travelOrVistText;
    }


    public Integer getTravelrVisitFlag() {
        return TravelrVisitFlag;
    }

    public void setTravelrVisitFlag(Integer visitFlag) {
        TravelrVisitFlag = visitFlag;
    }

    public String getStartLong() {
        return StartLong;
    }

    public void setStartLong(String startLong) {
        StartLong = startLong;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public Integer getTravelID() {
        return TravelID;
    }

    public void setTravelID(Integer travelID) {
        TravelID = travelID;
    }

    public String getStartLat() {
        return StartLat;
    }

    public void setStartLat(String startLat) {
        StartLat = startLat;
    }

    public Integer getTravelIncidentID() {
        return TravelIncidentID;
    }

    public void setTravelIncidentID(Integer travelIncidentID) {
        TravelIncidentID = travelIncidentID;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getKilometer() {
        return Kilometers;
    }

    public void setKilometer(String kilometer) {
        this.Kilometers = kilometer;
    }

    public String getEndLong() {
        return EndLong;
    }

    public void setEndLong(String endLong) {
        EndLong = endLong;
    }

    public String getEndLat() {
        return EndLat;
    }

    public void setEndLat(String endLat) {
        EndLat = endLat;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }


    public Integer getLastmodifiedby() {
        return Lastmodifiedby;
    }

    public void setLastmodifiedby(Integer lastmodifiedby) {
        Lastmodifiedby = lastmodifiedby;
    }

    public String getLastModifiedat() {
        return LastModifiedDateTime;
    }

    public void setLastModifiedat(String lastModifiedat) {
        LastModifiedDateTime = lastModifiedat;
    }

    public Integer getCreatedby() {
        return Createdby;
    }

    public void setCreatedby(Integer createdby) {
        Createdby = createdby;
    }

    public String getCreatedat() {
        return CreatedDateTime;
    }

    public void setCreatedat(String createdat) {
        CreatedDateTime = createdat;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }


    public Integer getID() {
        return AppID;
    }

    public void setID(Integer ID) {
        this.AppID = ID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getActionTaken() {
        return ActionTaken;
    }

    public void setActionTaken(String actionTaken) {
        ActionTaken = actionTaken;
    }

    public String getCallSlipNo() {
        return CallSlipNo;
    }

    public void setCallSlipNo(String callSlipNo) {
        CallSlipNo = callSlipNo;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public String getCutOffDate() {
        return CutOffDate;
    }

    public void setCutOffDate(String cutOffDate) {
        CutOffDate = cutOffDate;
    }

    public String getFindingsAtSite() {
        return FindingsAtSite;
    }

    public void setFindingsAtSite(String findingsAtSite) {
        FindingsAtSite = findingsAtSite;
    }

    public Integer getFlag() {
        return Flag;
    }

    public void setFlag(Integer flag) {
        Flag = flag;
    }

    public Integer getIncidentID() {
        return RegistrationID;
    }

    public void setIncidentID(Integer incidentID) {
        RegistrationID = incidentID;
    }

    public Integer getIsCompleted() {
        return IsCompleted;
    }

    public void setIsCallCompleted(Integer isCallCompleted) {
        IsCompleted = isCallCompleted;
    }

    public String getNextVisitDate() {
        return NextVisitDate;
    }

    public void setNextVisitDate(String nextVisitDate) {
        NextVisitDate = nextVisitDate;
    }

    public Integer getObservation() {
        return Observation;
    }

    public void setObservation(Integer observation) {
        Observation = observation;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    private Integer Flag;

}



