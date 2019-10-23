package filehandling;

import taskhandling.*;
import viewhandling.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TasksFile extends FilesHandler {
    private String filePath;
    private String fileName;


    public TasksFile(String tasksFileName) {
        super(tasksFileName);
        filePath = super.getFilePath();
        fileName = super.getFileName();

    }


    public ArrayList<Task> readTasksList() {

        ArrayList<Task> tasksList = new ArrayList<>();
        ViewObj viewObj = new ViewObj();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            int numberOfLines = lines.size();


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < numberOfLines; i++) {
                Task task = new Task();
                Scanner scanner = new Scanner(lines.get(i));
                scanner.useDelimiter("\t+");
                if (scanner.hasNext()) {
                    task.setTitle(scanner.next());
                    task.setCreatedDate(dateFormat.parse(scanner.next()));
                    task.setDueDate(dateFormat.parse(scanner.next()));
                    task.setProject(scanner.next());
                    task.setStatus(Status.parse(scanner.next()));
                }

                tasksList.add(task);

            }
        } catch (IOException e){
            viewObj.display("TasksFile.readTasksList",
                    "Could not read all lines from the task file on the device",e);
            return null;
        } catch (ParseException e){
            viewObj.display("TasksFile.readTasksList",
                    "Could not parse the text date of status",e);
            return null;
        }
        return tasksList;
    }



    public void addTasktoFile(){
        //Implementation required
    }

    public void updateTasks(TasksPool tasksPool) {
        List<Task> tasksList = tasksPool.getTasksList();

        try {

            if (Files.isWritable(Paths.get(filePath))) {
                Files.write(Paths.get(filePath), "".getBytes());
                for (Task task:tasksList) {
                    Files.write(Paths.get(filePath), (task.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
                }
            } else {
                throw new IOException("The tasks file is uneditable");
            }

        } catch (IOException e){
            ViewObj viewObj = new ViewObj();
            viewObj.display("TasksFile.updateTasks","Failed to write to the log file.",e);
        }

    }
}


