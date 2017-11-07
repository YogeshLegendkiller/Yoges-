package precisioninfomatics.servicefirst.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4264 on 11-02-2017.
 */

public class LoginModel {
    public static final String table="login_table";
    public static final String id="id";
    public static final String userid="userid";
    public static final String empcode="loing_empcode";
    public static final String moduleID="moduleID";
  //  public static final String FcmRegID="fcmRegID";
    public static final String modulename="modulename";
    public static final String roleid="roleid";
    public static final String rolename="rolename";
    public static final String userloginid="userloginid";
    public static final String empname="empname";
    @SerializedName("UserID")
    private Integer UserID;
    @SerializedName("EmployeeCode")
    private String EmployeeCode;
    @SerializedName("ModuleID")
    private Integer ModuleID;
  //  private String FCMRegID;



    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    @SerializedName("EmployeeName")

    private String EmployeeName;
    public Integer getUserLoginID() {
        return UserLoginID;
    }

    public void setUserLoginID(Integer userLoginID) {
        UserLoginID = userLoginID;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        EmployeeCode = employeeCode;
    }

    public Integer getModuleID() {
        return ModuleID;
    }

    public void setModuleID(Integer moduleID) {
        ModuleID = moduleID;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }

    public Integer getRoleID() {
        return RoleID;
    }

    public void setRoleID(Integer roleID) {
        RoleID = roleID;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
    @SerializedName("ModuleName")
    private String ModuleName;
    @SerializedName("RoleID")
    private Integer RoleID;
    @SerializedName("RoleName")
    private String RoleName;
    @SerializedName("UserLoginID")
    private Integer UserLoginID;

}
