package simplifii.framework.asyncmanager;

/**
 * Created by raghu on 23/8/16.
 */
import java.io.File;

/**
 * Created by nbansal2211 on 09/08/16.
 */
public class FileParamObject extends HttpParamObject {
    private File file;
    private String fileKeyName;
    private String fileName;
    public FileParamObject(File file, String fileName, String fileKeyName){
        this.file = file;
        this.fileKeyName = fileKeyName;
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileKeyName() {
        return fileKeyName;
    }

    public void setFileKeyName(String fileKeyName) {
        this.fileKeyName = fileKeyName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}