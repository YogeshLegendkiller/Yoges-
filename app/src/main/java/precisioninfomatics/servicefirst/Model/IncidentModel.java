package precisioninfomatics.servicefirst.Model;

import com.google.gson.annotations.SerializedName;

import precisioninfomatics.servicefirst.Activities.Incident;

/**
 * Created by 4264 on 24-11-2016.
 */

public class IncidentModel  {
    public static final String inc_table="inc_table";
    public static final String inc_id="inc_id";
    public static final String inc_regID="inc_regID";
    public static final String inc_companyname="inc_companyname";
    public static final String inc_incCode="inc_code";
    public static final String inc_probcategory="inc_pc";
    public static final String inc_status="inc_status";
    public static final String inc_cd="inc_cd";
    public static final String inc_localvendor="inc_lc";
    public static final String inc_statusID="inc_sid";
    public static final String inc_lmd="inc_lmd";
    public static final String inc_cb="inc_cb";
    public static final String inc_lmb="inc_lmb";
    public static final String inc_lat="inc_lat";
    public static final String inc_long="inc_long";
    public static final String inc_customeraddrs="cus_add";
    public static final String inc_callorgin="inc_co";
    public static final String inc_instalcall="inc_ic";
    public static final String inc_gc="inc_gc";
    public static final String inc_partreq="inc_partreq";
    public static final String inc_visitstatus="inc_visitstatus";
    public static final String serviceclassification="ic_sclas";
    public static final String sbu="sbu";
    public static final String servicecategory="servicecategory";
    public static final String bu="bu";
    public static final String servicesubcategory="servicesubcategory";
    public static final String subBu="subBu";
    public static final String probdescription="probdescription";
    public static final String priority="priority";
    public static final String remarks="remarks";

    public Integer id;
    private String Latitude;

    public String getVisitStatus() {
        return VisitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        VisitStatus = visitStatus;
    }

    private String CustomerAddress;
    private String VisitStatus;

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    private String Longtitude;

    public String getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(String longtitude) {
        Longtitude = longtitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    @SerializedName("RegistrationID")
    private Integer IncidentID;
    @SerializedName("StatusID")
    private Integer StatusID;

    public Integer getStatusID() {
        return StatusID;
    }

    public void setStatusID(Integer statusID) {
        StatusID = statusID;
    }
    @SerializedName("IncidentCode")
    private String IncidentCode;
    @SerializedName("Status")
    private String Status;
    @SerializedName("CallOrigin")
    private String CallOrigin;
   @SerializedName("CompanyName")
    private String CompanyName;
    @SerializedName("ProblemCategory")
    private String ProblemCategory;
    @SerializedName("Createdat")
    private String Createdat;
    @SerializedName("Lastmodifedat")
    private String Lastmodifedat;
    @SerializedName("Createdby")
    private Integer Createdby;
    @SerializedName("LastModifiedby")
    private Integer LastModifiedby;
    private Integer LocalVendor;

    public Integer getLocalVendor() {
        return LocalVendor;
    }

    public void setLocalVendor(Integer localVendor) {
        LocalVendor = localVendor;
    }

    public String getLastmodifedat() {
        return Lastmodifedat;
    }

    public void setLastmodifedat(String lastmodifedat) {
        Lastmodifedat = lastmodifedat;
    }

    public Integer getCreatedby() {
        return Createdby;
    }

    public void setCreatedby(Integer createdby) {
        Createdby = createdby;
    }

    public Integer getLastModifiedby() {
        return LastModifiedby;
    }

    public void setLastModifiedby(Integer lastModifiedby) {
        LastModifiedby = lastModifiedby;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsGeneralClaim() {
        return IsGeneralClaim;
    }

    public void setIsGeneralClaim(Integer isGeneralClaim) {
        IsGeneralClaim = isGeneralClaim;
    }

    public Integer getIsPartRequired() {
        return IsPartRequired;
    }

    public void setIsPartRequired(Integer isPartRequired) {
        IsPartRequired = isPartRequired;
    }

    public Integer getIsInstallationCall() {
        return IsInstallationCall;
    }

    public void setIsInstallationCall(Integer isInstallationCall) {
        IsInstallationCall = isInstallationCall;
    }

    private Integer IsPartRequired;
    private Integer IsGeneralClaim;
    private Integer IsInstallationCall;
   @SerializedName("ServiceClassification")
    private String ServiceClassification;
    @SerializedName("SBU")
    private String SBU;
    @SerializedName("ServiceCategory")
    private String ServiceCategory;
    @SerializedName("BU")
    private String BU;
    @SerializedName("ServiceSubCategory")
    private String ServiceSubCategory;
    @SerializedName("SubBU")
    private String SubBU;
    @SerializedName("ProblemDescription")
    private String ProblemDescription;
    @SerializedName("Priority")
    private String Priority;
    @SerializedName("Remarks")
    private String Remarks;

    public String getServiceClassification() {
        return ServiceClassification;
    }

    public void setServiceClassification(String serviceClassification) {
        ServiceClassification = serviceClassification;
    }

    public String getSBU() {
        return SBU;
    }

    public void setSBU(String SBU) {
        this.SBU = SBU;
    }

    public String getServiceCategory() {
        return ServiceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        ServiceCategory = serviceCategory;
    }

    public String getBU() {
        return BU;
    }

    public void setBU(String BU) {
        this.BU = BU;
    }

    public String getServiceSubCategory() {
        return ServiceSubCategory;
    }

    public void setServiceSubCategory(String serviceSubCategory) {
        ServiceSubCategory = serviceSubCategory;
    }

    public String getSubBU() {
        return SubBU;
    }

    public void setSubBU(String subBU) {
        SubBU = subBU;
    }

    public String getProblemDescription() {
        return ProblemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        ProblemDescription = problemDescription;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }


       public String getCreatedat() {
        return Createdat;
    }

    public void setCreatedat(String createdat) {
        Createdat = createdat;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }


    public String getProblemCategory() {
        return ProblemCategory;
    }

    public void setProblemCategory(String problemCategory) {
        ProblemCategory = problemCategory;
    }

    public String getCallOrigin() {
        return CallOrigin;
    }

    public void setCallOrigin(String callOrigin) {
        CallOrigin = callOrigin;
    }

    public String getIncidentCode() {
        return IncidentCode;
    }

    public void setIncidentCode(String incidentCode) {
        IncidentCode = incidentCode;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


     public Integer getIncidentID()
     {
        return IncidentID;
     }
     public void setIncidentID(Integer incidentID){
         this.IncidentID=incidentID;
     }
}
