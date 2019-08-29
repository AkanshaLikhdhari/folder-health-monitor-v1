package folder.health.monitor.serviceImpl;

import folder.health.monitor.services.FileOperations;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileOperationsImpl implements FileOperations {

    //method does the actual work of copying files
    public boolean copyTempToSecure(String source, String destination) {
        File tempPath = new File(source);
        File securePath = new File(destination);

        if (tempPath.list().length == 0) {
            System.out.println("Nothing to copy");
            return false;
        }
        for (File srcFile : tempPath.listFiles()) {
            if (!srcFile.isDirectory()) {
                try {
                    FileUtils.copyFileToDirectory(srcFile, securePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    //method moves files from secure to archive
    public void moveSecureToArchive(String securePath, String archievePath, List<File> filesToArchiveList) {
        List<String> archivedFiles = new ArrayList<String>();
        try {
            for (File file : filesToArchiveList) {
                File fileItem = new File(file.toString());
                File source = new File(securePath + File.separator + fileItem.getName());
                File destination = new File(archievePath + File.separator + fileItem.getName());
                if (destination.exists())
                    FileUtils.deleteQuietly(destination);
                FileUtils.moveFile(source, destination);
                archivedFiles.add(fileItem.getName());
            }
            System.out.println("Total number of archived files : " + archivedFiles.size());
            System.out.println("Archived File Details.");
            for (String fileName : archivedFiles) {
                System.out.println(fileName);
            }
            System.out.println();
            long securedFolderSize = deleteExtnFilesAndReturnSecureSize(securePath, new ArrayList<String>());
            double sizeMb = (double) securedFolderSize / (1024 * 1024);
            System.out.println(
                    "Secured Folder size post archive : " + Math.round(sizeMb * 100.0) / 100.0 + "  Mega bytes");
            System.out.println("=============================================================");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method deletes extension files and return secured folder size
    public long deleteExtnFilesAndReturnSecureSize(String securePath, List<String> extnFileToRemove) {
        long size = 0;
        File folder = new File(securePath);
        File[] listOfFiles = folder.listFiles();
        List<String> deletedFiles = new ArrayList<String>();
        if (listOfFiles.length > 0) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    int index = file.getName().lastIndexOf(".");
                    if (index > 0) {
                        String extension = file.getName().substring(index);
                        if (extnFileToRemove.contains(extension)) {
                            FileUtils.deleteQuietly(file);
                            deletedFiles.add(file.getName());
                            continue;
                        }
                    }
                    size += file.length();
                }
            }
        }
        if (deletedFiles.size() > 0) {
            System.out.println();
            System.out.println("Files deleted are : ");
            for (String fileName : deletedFiles) {
                System.out.println(fileName);
            }
            System.out.println();
        }
        return size;
    }


}