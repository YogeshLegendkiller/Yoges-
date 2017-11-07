package precisioninfomatics.servicefirst.Model;

import java.util.List;

/**
 * Created by 4264 on 04/04/2017.
 */

public class GeneralClaimModel  {
    public static final String GC_Table="gc_table";
    public static final String GC_ID="id";
    public static final String GC_FromDate="fromdate";
    public static final String GC_ClaimID="claimid";
    public static final String GC_ToDate="todate";
     public static final String GC_Total="total";
    public static final String Gc_IncidentID="inciID";
     public static final String GC_isServer="isserver";
    public static final String GC_Filepath="filepath";
    public static final String GC_Code="gc_code";
    public static final String GC_FromLoc="fromloc";
    public static final String Gc_ToLoc="toloc";
    public static final String GC_issent="ssent";
    public static final String GC_isexist="isfileexist";
    public static final String GC_filename="filename";
    public static final String GC_cb="createdby";
    public static final String GC_ca="createdat";
    public static final String GC_lmb="lastmoby";
    public static final String GC_claimcost="claimcost";
    public static final String GC_lma="lastmoat";
    public static final String GC_cbName="createdbyName";

    public static final String GC_ServerPath="serverPath";
    private String FromDate;

    public String getFromLoc() {
        return FromLocation;
    }

    public void setFromLoc(String fromLoc) {
        FromLocation = fromLoc;
    }

    public String getToLoc() {
        return ToLocation;
    }

    public void setToLoc(String toLoc) {
        ToLocation = toLoc;
    }

    private String FromLocation;
    private String ClaimCostValue;

    public void setClaimCost(List<ClaimFieldModel> claimCost) {
        ClaimCost = claimCost;
    }

    private List<ClaimFieldModel>ClaimCost;
    public String getClaimCost() {
        return ClaimCostValue;
    }

    public void setClaimCost(String claimCost) {
        ClaimCostValue = claimCost;
    }

    private String ToLocation;
    private String ToDate;

      public  Integer getIsServer() {
        return IsServer;
    }

    public void setIsServer(Integer isServer) {
        IsServer = isServer;
    }

    private Integer IsServer;
    private String Code;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    private Integer AppID;
   private List<GeneralClaimDocument> Documents;

    public void setDocs(List<GeneralClaimDocument> docs) {
        Documents = docs;
    }

    public Integer getID() {
        return AppID;
    }

    public void setID(Integer ID) {
        this.AppID = ID;
    }

    public String getDocs() {
        return FileUpload;
    }

    public void setDocs(String docs) {
        FileUpload = docs;
    }

    private String FileUpload;

    public Integer getClaimID() {
        return ClaimID;
    }

    public void setClaimID(Integer claimID) {
        ClaimID = claimID;
    }

    private Integer ClaimID;
    private String CreatedDateTime;
    private Integer CreatedBy;

    public Integer getIncidentID() {
        return RegistrationID;
    }

    public void setIncidentID(Integer incidentID) {
        RegistrationID = incidentID;
    }

    private Integer RegistrationID;

    public String getCreatedbyName() {
        return CreatedbyName;
    }

    public void setCreatedbyName(String createdbyName) {
        CreatedbyName = createdbyName;
    }

    private String CreatedbyName;

    public String getCreatedAt() {
        return CreatedDateTime;
    }

    public void setCreatedAt(String createdAt) {
        CreatedDateTime = createdAt;
    }

    public Integer getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Integer createdBy) {
        CreatedBy = createdBy;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

     private String FileName;
    private String LastModifiedDateTime;
    private Integer  LastModifiedBy;
    private Integer UserID;

    public String getLastModifiedDateTime() {
        return LastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        LastModifiedDateTime = lastModifiedDateTime;
    }
   private List<GeneralClaimDocument>list;

    public List<GeneralClaimDocument> getList() {
        return list;
    }

    public void setList(List<GeneralClaimDocument> list) {
        this.list = list;
    }

    public Integer getLastModifiedBy() {
        return LastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        LastModifiedBy = lastModifiedBy;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

     public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

   public String getSelectSliptoUpload() {
        return SelectSliptoUpload;
    }

    public void setSelectSliptoUpload(String selectSliptoUpload) {
        SelectSliptoUpload = selectSliptoUpload;
    }

    public Integer getIsSent() {
        return IsSent;
    }

    public void setIsSent(Integer isSent) {
        IsSent = isSent;
    }

    public Integer getIsFileExist() {
        return IsFileExist;
    }

    public void setIsFileExist(Integer isFileExist) {
        IsFileExist = isFileExist;
    }

    private String SelectSliptoUpload;
    private Integer IsSent;
    private Integer IsFileExist;

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    private Double Total;
}
