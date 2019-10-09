import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TasksFile extends FilesHandler {
    private String filePath;
    private String fileName;


    public TasksFile(String tasksFileName) throws IOException {
        super(tasksFileName);
        filePath = super.getFilePath();
        fileName = super.getFileName();

    }


}
