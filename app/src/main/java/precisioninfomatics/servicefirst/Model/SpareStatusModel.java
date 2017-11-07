package precisioninfomatics.servicefirst.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4264 on 18/03/2017.
 */

public class SpareStatusModel {
    public static final String table="sparestatus_table";
    public static final String primaryid="id";
    public static final String statusid="statusid";
    public static final String statusname="sstatusname";
    public static final String sequence="sequence";

    @SerializedName("StatusID")
    private Integer StatusID;

    @SerializedName("StatusName")
    private String Status;

    @SerializedName("Sequence")
    private Integer Sequence;

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

    public Integer getSequence() {
        return Sequence;
    }

    public void setSequence(Integer sequence) {
        Sequence = sequence;
    }


}
