package precisioninfomatics.servicefirst.Model;

import java.io.IOError;

/**
 * Created by 4264 on 16-01-2017.
 */

public class VehicleModel  {
    public static final String table="reg_table";
    public static final String id="ID";
    public static final String regno="regno";
    public static final String licno="licno";
    public static final String lictype="lictype";
    public static final String licexpdate="licexpdate";
    public static final String lictypeID="licetypeID";
    public static final String userid="userID";
    public static final String ins_expdate="insexpdate";
    public static final String vehicletype="vehicletype";
    private String RegNo;
    private String LicenseNo;
    private String LicenseType;
    private Integer UserID;
    private Integer VehicleType;

    public Integer getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        VehicleType = vehicleType;
    }

    public Integer getLicenseTypeID() {
        return LicenseTypeID;
    }

    public void setLicenseTypeID(Integer licenseTypeID) {
        LicenseTypeID = licenseTypeID;
    }

    private Integer LicenseTypeID;

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

    public String getRegNo() {
        return RegNo;
    }
    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public String getLicenseNo() {
        return LicenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        LicenseNo = licenseNo;
    }

    public String getLicenseType() {
        return LicenseType;
    }

    public void setLicenseType(String licenseType) {
        LicenseType = licenseType;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getInsuranceExpiryDate() {
        return InsuranceExpiryDate;
    }

    public void setInsuranceExpiryDate(String insuranceExpiryDate) {
        InsuranceExpiryDate = insuranceExpiryDate;
    }

       public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String ExpiryDate;
    private String InsuranceExpiryDate;
    private String ID;

}
