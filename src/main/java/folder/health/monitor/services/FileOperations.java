package folder.health.monitor.services;

import java.io.File;
import java.util.List;

public interface FileOperations {
    public boolean copyTempToSecure(String source,String destination);
    public void moveSecureToArchive(String source, String destination, List<File> archiveList);
    public long deleteExtnFilesAndReturnSecureSize(String source, List<String> fileExtToDelete);
}
