package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 03/05/2017.
 */

public class DeleteModel  {
    public static final  String DeleteTable="table_del";
    public static final String DeleteID="del_id";
    public static final String WeBID="gcid";
    public static final String issent="issent";
    public static final String flag="flag";


    private Integer Flag;

    public Integer getFlag() {
        return Flag;
    }

    public void setFlag(Integer flag) {
        Flag = flag;
    }

    public Integer getWebID() {
        return WebID;
    }

    public void setWebID(Integer webID) {
        WebID = webID;
    }

    private Integer WebID;
    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    private Integer isSent;

}
