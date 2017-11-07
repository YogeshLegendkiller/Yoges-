package precisioninfomatics.servicefirst.Model;

import com.plumillonforge.android.chipview.Chip;

/**
 * Created by 4264 on 12/04/2017.
 */

public class GeneralClaimDocument implements Chip {
 private String FilePath;
 private String FileName;

    public GeneralClaimDocument() {
    }

    public GeneralClaimDocument(String filePath, String fileName) {
        FilePath = filePath;
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    @Override
    public String getText() {
        return FileName;
    }
}
