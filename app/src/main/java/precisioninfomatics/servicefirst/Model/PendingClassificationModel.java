package precisioninfomatics.servicefirst.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4264 on 09-02-2017.
 */

public class PendingClassificationModel  {

    public static final String table="pc_table";
    public static final String id="id";
    public static final String text="text";
    public static final String webid="webid";

    @SerializedName("Name")
    private String PendingClassification;


    @SerializedName("PairID")
    private Integer PendingClassificationID;

    public Integer getPendingClassificationID() {
        return PendingClassificationID;
    }

    public void setPendingClassificationID(Integer pendingClassificationID) {
        PendingClassificationID = pendingClassificationID;
    }

    public String getPendingClassification() {
        return PendingClassification;
    }

    public void setPendingClassification(String pendingClassification) {
        PendingClassification = pendingClassification;
    }


}
