package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 21/06/2017.
 */

public class CustomerBranchModel  {
    public static final String CustomerBranchTable="customerbranchtable";
    public static final String CustomerBranchPrimaryID="pid";
    public static final String CustomerBranchid="customerbranchid";
    public static final String CustomerBranchname="customerbranchname";
    public static final String RegID="regid";

    private String CustomerBranchID;

    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    public String getCustomerBranchID() {
        return CustomerBranchID;
    }

    public void setCustomerBranchID(String customerBranchID) {
        CustomerBranchID = customerBranchID;
    }

    public String getCustomerBranchName() {
        return CustomerBranchName;
    }

    public void setCustomerBranchName(String customerBranchName) {
        CustomerBranchName = customerBranchName;
    }

    private String CustomerBranchName;
    private Integer IncidentID;
}
