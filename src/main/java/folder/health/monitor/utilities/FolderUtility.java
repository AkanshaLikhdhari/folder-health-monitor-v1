package folder.health.monitor.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FolderUtility {

    //method checks if folder exists
    public static boolean ifTempExist(String tempPath) {
        return new File(tempPath).exists();
    }

    public static boolean ifFolderExistOrCreateFolderSuccess(String securePath) {
        try {
            File secureFolder = new File(securePath);
            if (!secureFolder.exists()) {
                secureFolder.mkdir();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //return the list of files to archive
    public List<File> archiveOlderFileList(String securePath, String archivePath, long maxSize) {

        long secureFolderSize = 0;
        File folder = new File(securePath);
        File[] files = folder.listFiles();
        List<File> filesToArchive = new ArrayList<File>();
        sortByLastModified(files);

        if (null != files && files.length > 0) {
            for (File file : files) {
                secureFolderSize = secureFolderSize + file.length();
                if (secureFolderSize > maxSize) {
                    filesToArchive.add(file);
                }
            }
        }
        return filesToArchive;
    }

    //sorts the files by modification timestamp for archiving
    private void sortByLastModified(File[] files) {
        Arrays.sort(files, new Comparator<File>() {

            public int compare(File file1, File file2) {

                return Long.valueOf(file2.lastModified()).compareTo(file1.lastModified());
            }
        });
    }
}


