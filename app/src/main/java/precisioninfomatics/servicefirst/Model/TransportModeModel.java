package precisioninfomatics.servicefirst.Model;

import com.google.gson.annotations.SerializedName;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;

/**
 * Created by 4264 on 17-02-2017.
 */

public class TransportModeModel  {
    public static final String table="trans_table";
    public static final String id="id";
    public static final String name="name";
    public static final String webid="webid";
    @SerializedName("Name")
    private String Name;
    @SerializedName("PairID")
    private Integer ID;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
