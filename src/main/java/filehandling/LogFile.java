package filehandling;

import viewhandling.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LogFile  extends FilesHandler {
    private String filePath;
    private String fileName;

    public LogFile(String logFileName) {
        super(logFileName);
        filePath = super.getFilePath();
        fileName = super.getFileName();
    }

    public void registerTasksFile(String tasksFilePath) {
        try {
            if (Files.isWritable(Paths.get(filePath)))  {
                Files.write(Paths.get(filePath),tasksFilePath.getBytes());
            } else {
                throw new IOException("The log file is uneditable");
            }

        } catch (IOException e){
            ViewObj viewObj = new ViewObj();
            viewObj.display("LofFile.registerTasksFile","Failed to write to the log file.",e);
        }


    }

    public String readTasksFilePath() {
        try {
            String tasksFilePath = Files.readString(Paths.get(filePath));
            return tasksFilePath;
        }  catch (IOException e) {
            ViewObj viewObj = new ViewObj();
            viewObj.display("LogFile.readTasksFilePath", "Failed to readString in Files class",e);
            return "";
        }
    }

}