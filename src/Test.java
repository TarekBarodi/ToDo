import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Test {
    String fileName = "testFile.md";

    public static void main(String[] args) throws IOException {
        //System.out.println(createNewTaskFile());

        ViewObj view = new ViewObj();



    }

    public static Path createNewTaskFile() throws IOException {
        //The File System Separator
        String separator = FileSystems.getDefault().getSeparator();
        System.out.println("File System Separator is: '" + separator + "'");

        //The Current Directory
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("The current directory is: " + currentDirectory);

        //The new path for the tasks files
        Path tasksDirectoryPath = Paths.get(currentDirectory,"taskFiles");
        System.out.println("The tasks directory path is: " + tasksDirectoryPath);

        //Add the tasks directory if it does not exist yet
        if (!Files.exists(tasksDirectoryPath)) {
            //The created directory
            Path createdDirectory = Files.createDirectory(tasksDirectoryPath);
            System.out.println("The created directory is: " + createdDirectory);
        }

        //Read the tasks file name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the tasks file name:");
        String newTaskFileName = scanner.nextLine();
        newTaskFileName = newTaskFileName.replaceAll("\\s+","") + ".tsk";
        System.out.println("The tasks file name is: " + newTaskFileName);

        //The task file path
        Path taskFilePath = Paths.get(tasksDirectoryPath.toString(),newTaskFileName);
        System.out.println("The tasks file path is: " + taskFilePath);

        if (!Files.exists(taskFilePath)) {
            Files.createFile(taskFilePath);
        }

        return taskFilePath;
    }

}
