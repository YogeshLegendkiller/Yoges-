package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 25/07/2017.
 */

public class LastModifiedModel  {
    public static final String lmd_table="lmd_table";
    public static final String lmd_id="lmd_id";
    public static final String lmd_incid="lmd_incid";
    public static final String lmd_lmd="lmd";

    public String getLastModifiedDateTime() {
        return LastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        LastModifiedDateTime = lastModifiedDateTime;
    }

    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    private String LastModifiedDateTime;
    private Integer IncidentID;

}
