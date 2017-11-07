package precisioninfomatics.servicefirst.Model;

import java.io.Serializable;

/**
 * Created by 4264 on 14/03/2017.
 */

public class PartIssueModel implements Serializable {
    public static final String table="parti_table";
    public static final String id="id";
    public static final String issuedpartno="issueno";
    public static final String serialno="partisueno";
    public static final String statusname="issuestatus";
    public static final String reqstatusID="statusid";
    public static final String currentpartstatusID="partisustatusid";
    public static final String reqid="reqid";
    public static final String issuedpartid="issuedid";
    public static final String squence="sequencde";
    public static final String updatedid="updatedid";
    public static final String incid="incid";
    public static final String partstatusid="partstatusid";
    public static final String webid="webid";
    public static final String issuedwarehouseid="isuedwarehouseid";
    public static final String lmb="lmby";
    public static final String cd="createddate";
    public static final String ld="ld";
    public static final String curentpartstatusname="cpartsname";
    public static final  String cb="createdy";
    private String IssuedPartNo;

    private Integer IssuedWarehouseID;
    private Integer LastModifiedBy;
    private Integer IncidentID;

    public Integer getIssuedWarehouseID() {
        return IssuedWarehouseID;
    }

    public void setIssuedWarehouseID(Integer issuedWarehouseID) {
        IssuedWarehouseID = issuedWarehouseID;
    }

    public Integer getLastModifiedBy() {
        return LastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        LastModifiedBy = lastModifiedBy;
    }

    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        CreatedDateTime = createdDateTime;
    }

    public String getLastModifiedDateTime() {
        return LastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        LastModifiedDateTime = lastModifiedDateTime;
    }

    public Integer getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Integer createdBy) {
        CreatedBy = createdBy;
    }

    private String  CreatedDateTime;
    private String  LastModifiedDateTime;
    private Integer CreatedBy;

    public Integer getPartStatusID() {
        return PartStatusID;
    }

    public void setPartStatusID(Integer partStatusID) {
        PartStatusID = partStatusID;
    }

    private Integer PartStatusID;
    private String CurrentPartStatusName;

    public String getCurrentPartStatusName() {
        return CurrentPartStatusName;
    }

    public void setCurrentPartStatusName(String currentPartStatusName) {
        CurrentPartStatusName = currentPartStatusName;
    }

    private Integer VisitWebID;
    private String SerialNo;
    private String PartStatusName;
    private Integer RequestedStatusID;
    private Integer CurrentPartStatusID;
    private Integer RequestID;
    private Integer IssuedPartID;

    private Integer Sequence;
    private Integer UpdateID;
    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    public Integer getVisitWebID() {
        return VisitWebID;
    }

    public void setVisitWebID(Integer visitWebID) {
        VisitWebID = visitWebID;
    }


    public String getStatusName() {
        return PartStatusName;
    }

    public void setStatusName(String statusName) {
        PartStatusName = statusName;
    }

    public String getIssuedPartNo() {
        return IssuedPartNo;
    }

    public void setIssuedPartNo(String issuedPartNo) {
        IssuedPartNo = issuedPartNo;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public Integer getRequestedStatusID() {
        return RequestedStatusID;
    }

    public void setRequestedStatusID(Integer requestedStatusID) {
        RequestedStatusID = requestedStatusID;
    }

    public Integer getCurrentPartStatusID() {
        return CurrentPartStatusID;
    }

    public void setCurrentPartStatusID(Integer currentPartStatusID) {
        CurrentPartStatusID = currentPartStatusID;
    }

    public Integer getRequestID() {
        return RequestID;
    }

    public void setRequestID(Integer requestID) {
        RequestID = requestID;
    }

    public Integer getIssuedPartID() {
        return IssuedPartID;
    }

    public void setIssuedPartID(Integer issuedPartID) {
        IssuedPartID = issuedPartID;
    }

    public Integer getSequence() {
        return Sequence;
    }

    public void setSequence(Integer sequence) {
        Sequence = sequence;
    }

    public Integer getUpdatedID() {
        return UpdateID;
    }

    public void setUpdatedID(Integer updatedID) {
        UpdateID = updatedID;
    }


}
