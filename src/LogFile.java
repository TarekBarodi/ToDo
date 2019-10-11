import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogFile  extends FilesHandler {
    private String filePath;
    private String fileName;

    public LogFile(String logFileName) {
        super(logFileName);
        filePath = super.getFilePath();
        fileName = super.getFileName();
    }

    public void registerTasksFile(String tasksFilePath) throws IOException {
        try {
            if (Files.isWritable(Paths.get(filePath)))  {
                Files.write(Paths.get(filePath),tasksFilePath.getBytes());
            } else {
                throw new IOException("The log file is uneditable");
            }

        } catch (IOException e){
            throw new IOException("The tasks file could not be registered in log file");
        }


    }

    public String readTasksFilePath() throws IOException {
        String tasksFilePath = Files.readString(Paths.get(filePath));
        return  tasksFilePath;
    }


}