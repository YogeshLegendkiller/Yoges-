package precisioninfomatics.servicefirst.Model;

import java.io.Serializable;

import precisioninfomatics.servicefirst.Activities.Incident;

/**
 * Created by 4264 on 29-11-2016.
 */

public class ResourceModel implements Serializable {
    public static final String table="res_table";
    public static final String id="primaryID";
    public static final String resourceid="id";
    public static final String resourcename="name";
    public static final String telephone="telephone";
    public static final String email="email";
    public static final String statusid="statusid";
    public static final String status="status";
    public static final String resourcealocid="allocid";
    public static final String designation="designation";
    public static final String instruction="instruction";
    public static final String resourcestatus="resourcestatus";
    public static final String assignedate="assigneddate";
    public static final String incid="incidentid";
    public static final String photourl="photourl";
    public static final String localpath="localpath";
    private Integer ResourceID;
    private Integer IncidentID;
    private String ResourceStatus;

    public String getResourceStatus() {
        return ResourceStatus;
    }

    public void setResourceStatus(String resourceStatus) {
        ResourceStatus = resourceStatus;
    }

    private String EmployeeID;

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    private String Designation;

    public String getLocalPath() {
        return LocalPath;
    }

    public void setLocalPath(String localPath) {
        LocalPath = localPath;
    }

    public String getPhotourl() {
        return Photourl;
    }

    public void setPhotourl(String photourl) {
        Photourl = photourl;
    }

    private String LocalPath;
    private String Photourl;
    private String Visit_Status;
    private String EmployeeName;
    private String StatusText;
    private Integer Status;
    private String Telephone;
    private String Email;
    private String Instruction;
    private String AssignedDate;

    public Integer getResourceAllocationID() {
        return ResourceAllocationID;
    }

    public void setResourceAllocationID(Integer resourceAllocationID) {
        ResourceAllocationID = resourceAllocationID;
    }

    private Integer ResourceAllocationID;


    public String getAssignedDate() {
        return AssignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        AssignedDate = assignedDate;
    }


    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getVisit_Status() {
        return Visit_Status;
    }

    public void setVisit_Status(String visit_Status) {
        Visit_Status = visit_Status;
    }

    public String getStatusText() {
        return StatusText;
    }

    public void setStatusText(String statusText) {
        StatusText = statusText;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }


    public Integer getResourceID() {
        return ResourceID;
    }

    public void setResourceID(Integer resourceID) {
        ResourceID = resourceID;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }

    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}
