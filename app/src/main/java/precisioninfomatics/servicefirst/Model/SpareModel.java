package precisioninfomatics.servicefirst.Model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 4264 on 25-01-2017.
 */

public class SpareModel implements Serializable {
    public static final String table="spare_table";
    public static final String id="id";
    public static final String webid="webID";
    public static final String visitprimaryID="visitpID";
    public static final String partno="partno";
    public static final String partstatus="partstatus";
    public static final String partid="partid";
    public static final String advreturn="return";
    public static final String remarks="remarks";
    public static final String samepart="samepart";
    public static final String visitwebid="visitWebID";
    public static final String incid="incidentID";
    public static final String statusid="statusID";
    public static final String requestedspec="reqspec";
    public static final String brandname="brandname";
    public static final String modelname="modelname";
    public static final String catname="CategoryName";
    public static final String specification="specification";
    public static final String defectiveserialno="defectiveserialno";
    public static final String editflag="editflag";
    public static final String deleteflag="deleteflag";

    //for delete value
    public static String updateid="updateid";
    public static String issued="issue";
    private Integer AppID;
    private Integer IncidentID;

    public Integer getEditFlag() {
        return EditFlag;
    }

    public void setEditFlag(Integer editFlag) {
        EditFlag = editFlag;
    }


    private Integer EditFlag;
    public Integer getUpdateID() {
        return UpdateID;
    }

    public void setUpdateID(Integer updateID) {
        UpdateID = updateID;
    }

    private Integer UpdateID;
    private Integer Quantity;

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Integer getStatusID() {
        return RequestedStatusID;
    }

    public void setStatusID(Integer statusID) {
        RequestedStatusID = statusID;
    }

    private Integer RequestedStatusID;
    private Integer IsSamePart;
    private Integer RequestID;
    private String BrandName;
    private String ModelName;

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getPartSpecification() {
        return PartSpecification;
    }

    public void setPartSpecification(String partSpecification) {
        PartSpecification = partSpecification;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    private String CategoryName;
    private String PartSpecification;
    public Integer getWebID() {
        return RequestID;
    }

    public void setWebID(Integer webID) {
        RequestID = webID;
    }

    public Integer getPartID() {
        return RequestedPartID;
    }

    public void setPartID(Integer partID) {
        RequestedPartID = partID;
    }

    private Integer RequestedPartID;

    public String getDefectiveSerialno() {
        return DefectiveSerialno;
    }

    public void setDefectiveSerialno(String defectiveSerialno) {
        DefectiveSerialno = defectiveSerialno;
    }

    private String DefectiveSerialno;
    public Integer getSamePart() {
        return IsSamePart;
    }

    public void setSamePart(Integer samePart) {
        IsSamePart = samePart;
    }

    public Integer getVisitWebID() {
        return VisitWebID;
    }

    public void setVisitWebID(Integer visitWebID) {
        VisitWebID = visitWebID;
    }

    private Integer VisitWebID;
    private Integer VisitPrimaryID;

    public Integer getVisitPrimaryID() {
        return VisitPrimaryID;
    }

    public void setVisitPrimaryID(Integer visitPrimaryID) {
        VisitPrimaryID = visitPrimaryID;
    }

    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    public Integer getID() {
        return AppID;
    }

    public void setID(Integer ID) {
        this.AppID = ID;
    }


    public String getPartno() {
        return RequestedPartNo;
    }

    public void setPartno(String partno) {
        RequestedPartNo = partno;
    }

    public String getRequestedPartSpecification() {
        return RequestedPartSpecification;
    }

    public void setRequestedPartSpecification(String requestedPartSpecification) {
        RequestedPartSpecification = requestedPartSpecification;
    }

    private String RequestedPartSpecification;
    public String getPartStatus() {
        return PartStatus;
    }

    public void setPartStatus(String partStatus) {
        PartStatus = partStatus;
    }

    public String getRemarks() {
        return PartRemarks;
    }

    public void setRemarks(String remarks) {
        PartRemarks = remarks;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public Integer getAdvanceReturn() {
        return IsAdvanceReturn;
    }

    public void setAdvanceReturn(Integer advanceReturn) {
        IsAdvanceReturn = advanceReturn;
    }

    private String RequestedPartNo;
    private Integer IsIssued;

    public Integer getIsIssued() {
        return IsIssued;
    }

    public void setIsIssued(Integer isIssued) {
        IsIssued = isIssued;
    }

    private String PartStatus;
    private String PartRemarks;
    private String Issue;
    private Integer IsAdvanceReturn;

}
