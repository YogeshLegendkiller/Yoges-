package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 24/05/2017.
 */

public class ClaimFieldModel {

    public static final String ID="id";
    public static final String ClaimFieldTable="claim_ftable";
    public static final String fieldpairid="pairID";
    public static final String fieldname="name";

    private String Name;
    private Integer PairID;
    private Integer ClaimAmount;

    public Integer getClaimAmount() {
        return ClaimAmount;
    }

    public void setClaimAmount(Integer claimAmount) {
        ClaimAmount = claimAmount;
    }



    public void setClaimTypeID(Integer claimTypeID) {
        ClaimTypeID = claimTypeID;
    }

    private int ClaimTypeID;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPairID() {
        return PairID;
    }

    public void setPairID(Integer pairID) {
        PairID = pairID;
    }
}


