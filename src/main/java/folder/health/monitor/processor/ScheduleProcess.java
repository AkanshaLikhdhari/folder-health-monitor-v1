package folder.health.monitor.processor;

import folder.health.monitor.serviceImpl.FileOperationsImpl;
import folder.health.monitor.serviceImpl.MonitorFolderImpl;
import folder.health.monitor.services.FileOperations;
import folder.health.monitor.services.MonitorFolder;
import folder.health.monitor.utilities.FolderUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleProcess {

    private List<String> fileExtToDelete;
    private long monInterval;
    private long copyInterval;
    private String tempPath;
    private String securePath;
    private String archivePath;
    private long maxSecureSize;

    private MonitorFolder monitorFolder = new MonitorFolderImpl();
    private FileOperations fileOperations = new FileOperationsImpl();

    public void setFileExtToDelete(List<String> fileExtToDelete) {
        this.fileExtToDelete = fileExtToDelete;
    }

    public long getMonInterval() {
        return monInterval;
    }

    public void setMonInterval(Long monInterval) {
        this.monInterval = monInterval;
    }

    public void setCopyInterval(long copyInterval) {
        this.copyInterval = copyInterval;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public void setSecurePath(String securePath) {
        this.securePath = securePath;
    }

    public void setArchivePath(String archivePath) {
        this.archivePath = archivePath;
    }

    public void setMaxSecureSize(long maxSecureSize) {
        this.maxSecureSize = maxSecureSize;
    }

    public void tasksToExecute() {
        //Check if the secured folder exists, if not tries to create one, if creation fails, prints message and stops
        if (FolderUtility.ifFolderExistOrCreateFolderSuccess(securePath)) {
            //Check if the archive folder exists, if not tries to create one, if creation fails, prints message and stops
            if (FolderUtility.ifFolderExistOrCreateFolderSuccess(archivePath)) {
                final SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
                //Scheduler to copy files from temp to secure folder
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Date startDate = new Date(System.currentTimeMillis());
                        System.out.println("Temp folder files copy to secure folder started at Time :"
                                + timeFormat.format(startDate));
                        fileOperations.copyTempToSecure(tempPath, securePath);
                        System.out.println("Copying temporary folder finished at Time :"
                                + timeFormat.format(new Date(System.currentTimeMillis())));
                        System.out.println();

                    }
                }, 0, copyInterval);
                //Scheduler to monitor secured folder
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Monitoring Secured Folder ");
                        monitorFolder.monitor(securePath, archivePath, fileExtToDelete, maxSecureSize);
                    }
                }, 10000, monInterval);
            } else
                System.out.println("Archive folder creation failed");
        } else {
            System.out.println("Secure folder failed creation failed");
        }
    }
}
