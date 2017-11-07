package precisioninfomatics.servicefirst.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4264 on 07-02-2017.
 */

public class IncidentViewModel extends IncidentModel {

    public static final String table="incview_table";
    public static final String id="id";
    public static final String customercode="cd";
    public static final String customerbranchaddress="cba";
    public static final String contactname="cn";
    public static final String designation="designation";
    public static final String mailid="email";
    public static final String phno="phno";
    public static final String assetcategoryname="assetid";
    public static final String serialno="serialno";
    public static final String baseconfig="basegconfig";
    public static final String currentconfig="currentconfig";
    public static final String partno="partno";
    public static final String periodtype="periodtype";
    public static final String periodfrom="periodfrom";
    public static final String serviceprovider="serviceprovider";
    public static final String periodto="periodto";
    public static final String contractcode="contractcode";
    public static final String contaract_sdate="contractsdate";
    public static final String contract_enddate="contractendate";
    public static final String acm="acm";
    public static final String checklistid="checklistline";
    public static final String conatractstatus="contractstatus";
   @SerializedName("CustomerCode")
    private String CustomerCode;
    @SerializedName("CustomerBranchAddress")
   private String CustomerBranchAddress;
    @SerializedName("ContactName")
   private String ContactName;
    @SerializedName("Designation")
   private String Designation;
    @SerializedName("EmailID")
   private String EmailID;
    @SerializedName("PhoneNo")
   private String PhoneNo;
    @SerializedName("CheckListID")
    private Integer CheckListID;

    public void setCheckListID(Integer checkListID) {
        CheckListID = checkListID;
    }

    public Integer getCheckListID() {
        return CheckListID;
    }

    @SerializedName("AssetID")
   private Integer AssetID;
    @SerializedName("ServiceProvider")
    private String ServiceProvider;
    public String getServiceProvider() {
        return ServiceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        ServiceProvider = serviceProvider;
    }
   @SerializedName("ContractCode")
   private String ContractCode;

    @SerializedName("ContractStartDate")
    private String ContractStartDate;

    @SerializedName("ContractEndDate")
    private String ContractEndDate;

    @SerializedName("AccountManagerName")
    private String AccountManager;

    public String getAccountManager() {
        return AccountManager;
    }

    public void setAccountManager(String accountManager) {
        AccountManager = accountManager;
    }

    public String getContractEndDate() {
        return ContractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        ContractEndDate = contractEndDate;
    }

    public String getContractStatus() {
        return ContractStatus;
    }

    public void setContractStatus(String contractStatus) {
        ContractStatus = contractStatus;
    }

    @SerializedName("ContractStatus")
    private String ContractStatus;

   // @SerializedName("S")
    public String getContractCode() {
        return ContractCode;
    }

    public void setContractCode(String contractCode) {
        ContractCode = contractCode;
    }

    public String getContractStartDate() {
        return ContractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        ContractStartDate = contractStartDate;
    }

    @SerializedName("SerialNumber")
   private String SerialNumber;
    @SerializedName("BaseConfiguration")
   private String BaseConfiguration;
    @SerializedName("CurrentConfiguration")
   private String CurrentConfiguration;
    @SerializedName("PartNo")
   private String PartNo;
    @SerializedName("PeriodType")
   private String PeriodType;
    @SerializedName("PeriodFrom")
   private String PeriodFrom;
    @SerializedName("PeriodTo")
   private String PeriodTo;
    @SerializedName("VendorName")
    private String VendorName;
    @SerializedName("AssetCategoryName")
    private String AssetCategoryName;
    @SerializedName("AssetSubCategoryName")
    private String AssetSubCategoryName;
    @SerializedName("Brand")
    private String Brand;
    @SerializedName("Model")
    private String Model;
    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getCustomerBranchAddress() {
        return CustomerBranchAddress;
    }

    public void setCustomerBranchAddress(String customerBranchAddress) {
        CustomerBranchAddress = customerBranchAddress;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public Integer getAssetID() {
        return AssetID;
    }

    public void setAssetID(Integer assetID) {
        AssetID = assetID;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getBaseConfiguration() {
        return BaseConfiguration;
    }

    public void setBaseConfiguration(String baseConfiguration) {
        BaseConfiguration = baseConfiguration;
    }

    public String getCurrentConfiguration() {
        return CurrentConfiguration;
    }

    public void setCurrentConfiguration(String currentConfiguration) {
        CurrentConfiguration = currentConfiguration;
    }

    public String getPartNo() {
        return PartNo;
    }

    public void setPartNo(String partNo) {
        PartNo = partNo;
    }

    public String getPeriodType() {
        return PeriodType;
    }

    public void setPeriodType(String periodType) {
        PeriodType = periodType;
    }

    public String getPeriodFrom() {
        return PeriodFrom;
    }

    public void setPeriodFrom(String periodFrom) {
        PeriodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return PeriodTo;
    }

    public void setPeriodTo(String periodTo) {
        PeriodTo = periodTo;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getAssetCategoryName() {
        return AssetCategoryName;
    }

    public void setAssetCategoryName(String assetCategoryName) {
        AssetCategoryName = assetCategoryName;
    }

    public String getAssetSubCategoryName() {
        return AssetSubCategoryName;
    }

    public void setAssetSubCategoryName(String assetSubCategoryName) {
        AssetSubCategoryName = assetSubCategoryName;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }


}
