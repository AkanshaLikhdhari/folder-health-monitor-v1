package folder.health.monitor.serviceImpl;

import folder.health.monitor.services.FileOperations;
import folder.health.monitor.services.MonitorFolder;
import folder.health.monitor.utilities.FolderUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MonitorFolderImpl implements MonitorFolder {
    private FileOperations fileOperations = new FileOperationsImpl();
    private FolderUtility folderUtility = new FolderUtility();

    public void monitor(String source, String destination, List<String> fileExtension, long max_size) {
        long secureSizeAfterDeletion = fileOperations.deleteExtnFilesAndReturnSecureSize(source, fileExtension);
        SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        double sizeInMb = (double) secureSizeAfterDeletion / (1024 * 1024);
        System.out.println("Secured folder size after deletion of extn files if any : " + sizeInMb + " MB at : "
                + timeFormat.format(new Date(System.currentTimeMillis())));
        if (secureSizeAfterDeletion > max_size) {

            System.out.println("Moving files to archive folder as secure folder size more than max size provided .");
            List<File> archiveFileList = folderUtility.archiveOlderFileList(source, destination, max_size);
            fileOperations.moveSecureToArchive(source, destination, archiveFileList);

        } else {
            System.out.println("******** RECURRING SCHEDULER *********************");
        }
    }
}
