package precisioninfomatics.servicefirst.Model;

import com.plumillonforge.android.chipview.Chip;

/**
 * Created by 4264 on 28/03/2017.
 */

public class SerialNumberMapModel implements Chip {
    public static final String table="serial_table";
    public static final String id="id";
    public static final String SerialMapTable="serialmaptable";
    public static final String serialno="serialnumber";
    public static final String checklistlineid="serialID";
    public static final String visitid="visitid";
    public static final String visitwebid="visitwebid";
    public static final String updatedid="updatedid";
    public static final String incid="incid";

    public Integer getUpdatedID() {
        return UpdatedID;
    }

    public void setUpdatedID(Integer updatedID) {
        UpdatedID = updatedID;
    }

    public static final String shiptoserialno="shiptoserialnum";
    private String SerialNumber;
    private Integer CheckListLineID;
    private String ShipToSerialNumber;
    private Integer IncidentID;
     private Integer VisitID;
private Integer UpdatedID;
    public Integer getVisitID() {
        return VisitID;
    }

    public void setVisitID(Integer visitID) {
        VisitID = visitID;
    }

    public Integer getVisitWebID() {
        return VisitWebID;
    }

    public void setVisitWebID(Integer visitWebID) {
        VisitWebID = visitWebID;
    }

    private Integer VisitWebID;
    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    public String getShipToSerialNumber() {
        return ShipToSerialNumber;
    }

    public void setShipToSerialNumber(String shipToSerialNumber) {
        ShipToSerialNumber = shipToSerialNumber;
    }

  public SerialNumberMapModel(){

  }
    public SerialNumberMapModel(String serialNumber,int id) {
        SerialNumber = serialNumber;
        CheckListLineID=id;
    }

    public Integer getSerialNumberID() {
        return CheckListLineID;
    }

    public void setSerialNumberID(Integer serialNumberID) {
        CheckListLineID = serialNumberID;
    }


    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SerialNumberMapModel that = (SerialNumberMapModel) o;

        return SerialNumber != null ? SerialNumber.equals(that.SerialNumber) : that.SerialNumber == null;

    }



    @Override

    public String getText() {
        return SerialNumber;
    }
}
