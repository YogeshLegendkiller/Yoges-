package precisioninfomatics.servicefirst.Model;

import java.util.List;

import precisioninfomatics.servicefirst.Activities.IntroSlider;

/**
 * Created by 4264 on 02/03/2017.
 */

public class LocalVendorModel  {
    public static final String table="lv_table";
    public static final String id="id";
    public static final String webid="webid";
    public static final String incID="incID";
    public static final String invoiceno="invoiceno";
    public static final String invoicedate="invoicedate";
    public static final String sparecharge="sparecharge";
    public static final String cb="cb";
    public static final String lmb="lmb";
    public static final String servicecharge="servicecharge";
    public static final String cd="cd";
    public static final String issent="issent";
    public static final String ld="ld";
    public static final String localfilepath="localfilepath";
    public static final String is_fileexist="is_fileexist";
    public static final String Resourcename="resourcename";
    public static final String LV_Total="total";
    public static final String localfilename="localfilename";
    public static final String attachpath="attachpath";
    public static final String userorvendorid="userorvendorid";
    public static final String attachname="attachname";
    public static final String chargetype="chargetype";
   // public static final String invoiceitems="invoiceitems";
    public static final String targettype="targettype";
    private String InvoiceItem;

    public String getInvoiceItem() {
        return InvoiceItem;
    }

    public void setInvoiceItem(String invoiceItem) {
        InvoiceItem = invoiceItem;
    }

    private Integer AppID;
    private List<InvoiceItemsModel> InvoiceItems;
    public void  setInvoiceItems(List<InvoiceItemsModel> invoiceItems){
        this.InvoiceItems=invoiceItems;
    }
    public Integer getIsFileExist() {
        return IsFileExist;
    }

    public void setIsFileExist(Integer isFileExist) {
        IsFileExist = isFileExist;
    }

    private Integer IsFileExist;

    public Integer getTotal() {
        return TotalCharge;
    }

    public void setTotal(Integer total) {
        TotalCharge = total;
    }

    public Integer getChargeType() {
        return ChargeType;
    }

    public void setChargeType(Integer chargeType) {
        ChargeType = chargeType;
    }

    public Integer getTargetType() {
        return TargetType;
    }

    public void setTargetType(Integer targetType) {
        TargetType = targetType;
    }

    private Integer TotalCharge;
    private Integer ChargeType;
    private Integer TargetType;
    private Integer InvoiceID;
    private Integer RegistrationID;

    public String getResourceName() {
        return ResourceName;
    }

    public void setResourceName(String resourceName) {
        ResourceName = resourceName;
    }

    private String ResourceName;
    private String InvoiceNumber;
    private Integer CreatedBy;
    private Integer ServiceCharge;
    private Integer SpareCharge;

    public Integer getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(Integer serviceCharge) {
        ServiceCharge = serviceCharge;
    }

    public Integer getUserOrVendorID() {
        return UserOrVendorID;
    }

    public void setUserOrVendorID(Integer userOrVendorID) {
        UserOrVendorID = userOrVendorID;
    }

    private Integer UserOrVendorID;
    public String getLocalFileName() {
        return LocalFileName;
    }

    public void  setLocalFileName(String localFileName) {
        LocalFileName = localFileName;
    }

    public String getLocalFilePath() {
        return LocalFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        LocalFilePath = localFilePath;
    }

    public String getLastModifiedDateTime() {
        return LastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        LastModifiedDateTime = lastModifiedDateTime;
    }

    public Integer getVendorInvoiceID() {
        return InvoiceID;
    }

    public void setVendorInvoiceID(Integer vendorInvoiceID) {
        InvoiceID = vendorInvoiceID;
    }

    public Integer getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Integer createdBy) {
        CreatedBy = createdBy;
    }

    public Integer getIsSent() {
        return IsSent;
    }

    public void setIsSent(Integer isSent) {
        IsSent = isSent;
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

    private Integer IsSent;
     private Integer LastModifiedBy;
    private String  CreatedDateTime;
    private String LastModifiedDateTime;
    private String LocalFilePath;
    private String LocalFileName;


    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        InvoiceDate = invoiceDate;
    }

    public Integer getSpareCharge() {
        return SpareCharge;
    }

    public void setSpareCharge(Integer spareCharge) {
        SpareCharge = spareCharge;
    }

    public String getAttachmentPath() {
        return FilePath;
    }

    public void setAttachmentPath(String attachmentPath) {
        FilePath = attachmentPath;
    }

    public String getAttachmentName() {
        return FileName;
    }

    public void setAttachmentName(String attachmentName) {
        FileName = attachmentName;
    }

    public Integer getPrimaryID() {
        return AppID;
    }

    public void setPrimaryID(Integer primaryID) {
        AppID = primaryID;
    }

    public Integer getWebID() {
        return InvoiceID;
    }

    public void setWebID(Integer webID) {
        InvoiceID = webID;
    }

    public Integer getIncidentID() {
        return RegistrationID;
    }

    public void setIncidentID(Integer incidentID) {
        RegistrationID = incidentID;
    }

    private String InvoiceDate;
    private String FilePath;
    private String FileName;

}
