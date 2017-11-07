package precisioninfomatics.servicefirst.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4264 on 28-12-2016.
 */







public  class VisitStatusModel  {
    public static final  String table="status_table";
    public static final String id="id";
    public static final String status="visit_text";
    public static final String statusid="statusid";

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getStatusID() {
        return StatusID;
    }

    public void setStatusID(Integer statusID) {
        StatusID = statusID;
    }

    @SerializedName("Name")
    private String Status;
    @SerializedName("Value")
    private Integer StatusID;

}
