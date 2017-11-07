package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 24-01-2017.
 */

public class PartModel  {
    public static final String table="part_table";
    public static final String id="id";
    public static final String webid="webID";
    public static final String partno="no";
    public static final String specification="specification";
    public static final String productno="productno";
    public static final String brand="brand";
    public static final String model="odel";
    public static final String category="category";
    public static final String incid="incidentID";
    private Integer PartID;

    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    private Integer IncidentID;
    private String Partno;
    private String PartSpecification;
    private String Productno;
    private String Brand;
    private String Model;

    public Integer getPartID() {
        return PartID;
    }

    public void setPartID(Integer partID) {
        PartID = partID;
    }

    public String getPartno() {
        return Partno;
    }

    public void setPartno(String partno) {
        Partno = partno;
    }

    public String getPartSpecification() {
        return PartSpecification;
    }

    public void setPartSpecification(String partSpecification) {
        PartSpecification = partSpecification;
    }

    public String getProductno() {
        return Productno;
    }

    public void setProductno(String productno) {
        Productno = productno;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    private String Category;
}
