package precisioninfomatics.servicefirst.Model;

import com.plumillonforge.android.chipview.Chip;

import java.io.Serializable;

/**
 * Created by 4264 on 07/07/2017.
 */

public class SuggestionModel implements Serializable,Chip {
    private String Text;
  public SuggestionModel(){

  }
    public SuggestionModel(String text) {
        Text = text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Integer getValue() {
        return Value;
    }

    public void setValue(Integer value) {
        Value = value;
    }

    private Integer Value;
    @Override
    public String getText() {
        return Text;
    }
    @Override
    public String toString() {
        return Text;
    }
}
