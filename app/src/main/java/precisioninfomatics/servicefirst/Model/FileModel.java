package precisioninfomatics.servicefirst.Model;

/**
 * Created by 4264 on 21/06/2017.
 */

public class FileModel  {
    public static final String FileTable="filetable";
    public static final String FileID="fileID";
    public static final String Filename="name";
    public static final String Filepath="path";
    public static final String Incidentid="incid";
    public static final String Filetype="filetype";

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public Integer getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(Integer incidentID) {
        IncidentID = incidentID;
    }

    public Integer getFileType() {
        return FileType;
    }

    public void setFileType(Integer fileType) {
        FileType = fileType;
    }

    private String FileName;
    private String FilePath;
    private Integer IncidentID;
    private Integer FileType;

}
