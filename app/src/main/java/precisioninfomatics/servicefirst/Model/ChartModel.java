package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 22/05/2017.
 */

public class ChartModel {
    public static final String Pie_Table="pie_table";
    public static final String Bar_Table="bar_table";
    public static final String Summary_Table="summary_table";
    public static final String ID="id";
    public static final String Label="label";
    public static final String Data="data";
   private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    private float  data;
}
