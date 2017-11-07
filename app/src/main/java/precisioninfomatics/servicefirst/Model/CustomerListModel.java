package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 16/06/2017.
 */

public class CustomerListModel {
    public static final String table="customerlist_table";
    public static final String id="id";
    public static final String customerbranchid="branchid";
    public static final String customerbranchname="branchname";
    public static final String incidentid="incid";
    public static final String flag="flag";

    public Integer getFlag() {
        return Flag;
    }

    public void setFlag(Integer flag) {
        Flag = flag;
    }

    private Integer Flag;
    private String CustomerBranchName;
    private Integer CustomerBranchID;
    private Integer IncidentID;

    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    public String getCustomerBranchName() {
        return CustomerBranchName;
    }

    public void setCustomerBranchName(String customerBranchName) {
        CustomerBranchName = customerBranchName;
    }

    public Integer getCustomerBranchID() {
        return CustomerBranchID;
    }

    public void setCustomerBranchID(Integer customerBranchID) {
        CustomerBranchID = customerBranchID;
    }
}
