package folder.health.monitor;

import folder.health.monitor.processor.ScheduleProcess;
import folder.health.monitor.utilities.FolderUtility;
import folder.health.monitor.utilities.PropertyUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

// Starter class for the project
public class Starter {

    public static void main(String[] args) {

        ScheduleProcess scheduleProcess = new ScheduleProcess();
        PropertyUtil propertyUtil = new PropertyUtil();
        Properties properties = propertyUtil.getAppProperties("application.properties");

        if (null != properties) {
            //Checks if temp folder exists, if not prints message and stops
            if (FolderUtility.ifTempExist(properties.getProperty("temp.folder.path"))) {
                scheduleProcess.setTempPath(properties.getProperty("temp.folder.path"));
                scheduleProcess.setSecurePath(properties.getProperty("secure.folder.path"));
                scheduleProcess.setArchivePath(properties.getProperty("archive.folder.path"));
                scheduleProcess.setCopyInterval(Long.parseLong(properties.getProperty("temp.folder.copy.interval")));
                scheduleProcess.setMonInterval(Long.parseLong(properties.getProperty("secure.folder.monitor.interval")));
                long maxSize = Long.parseLong(properties.getProperty("secure.folder.maxsize")) * 1024 * 1024;
                scheduleProcess.setMaxSecureSize(maxSize);
                List<String> fileExtToDelete = Arrays.asList(properties.getProperty("auto.delete.secure.file.extn").split(","));
                scheduleProcess.setFileExtToDelete(fileExtToDelete);
                //Start scheduler and the processes
                scheduleProcess.tasksToExecute();
            } else {
                System.out.println("Temporary folder does not exist. Provide a valid folder");
            }

        } else {
            System.out.println("Not able to load properties file");
        }
    }
}
