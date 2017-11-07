package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 21/03/2017.
 */

public class GpsModel  {
    public static final String Table="gps_Table";
    public static final String ID="ID";
    public static final String LAT="lat";
    public static final String Long="long";
    public static final String flag="flag";
    private Double Latitude;
    private Double Longtitude;
    private Integer Flag;

    public Integer getFlag() {
        return Flag;
    }

    public void setFlag(Integer flag) {
        Flag = flag;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(Double longtitude) {
        Longtitude = longtitude;
    }
}
