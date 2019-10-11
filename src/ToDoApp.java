import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ToDoApp {

    public ToDoApp() {

    }

    public static void main(String[] args) throws Exception {
        // user select an option to open the Tasks File
        int selectedOption = selectOpenFileOption();

        //Create a tasksFile depending on the option selected: 1 for recent, 2 for existing, 3 for new file.
        //including registering its path in the log file.
        TasksFile tasksFile = createTaskFile(selectedOption);



    }

    public static int selectOpenFileOption() throws Exception {
        boolean isOptionSelected = false;
        int selectedOption = 0;
        while (!isOptionSelected) {
            ViewObj viewObj = new ViewObj();
            viewObj.display("Welcome to ToDo List application");
            viewObj.display("You can select one of the following:");
            viewObj.display(1, viewObj.colorTxt(TxtColor.YELLOW, "Open the ")
                    + viewObj.colorTxt(TxtColor.BLUE, "recent ") + viewObj.colorTxt(TxtColor.YELLOW, "Tasks file."));
            viewObj.display(2, viewObj.colorTxt(TxtColor.YELLOW, "Open ")
                    + viewObj.colorTxt(TxtColor.BLUE, "existing ") + viewObj.colorTxt(TxtColor.YELLOW, "Tasks file."));
            viewObj.display(3, viewObj.colorTxt(TxtColor.YELLOW, "Open ")
                    + viewObj.colorTxt(TxtColor.BLUE, "new ") + viewObj.colorTxt(TxtColor.YELLOW, "Tasks file."));
            viewObj.display(0, viewObj.colorTxt(TxtColor.YELLOW, "Escape "));
            viewObj.display("Note: Enter the selection number 1 or 2 or 3, and press return!");

            ControlObj controlObj = new ControlObj();
            try {
                selectedOption = controlObj.readCommandSelection(0,3);
                isOptionSelected = true;
            } catch (Exception e) {
                throw new Exception("Failed to select an option to open file");
            }


        }
        return selectedOption;
    }

    public static boolean isInteger(String txt){
        try{
            Integer.parseInt(txt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String colorTxt(TxtColor txtColor, String txt){
        String index = null;

        switch (txtColor){
            case BLACK:
                index = "30";
                break;
            case RED:
                index = "31";
                break;
            case GREEN:
                index = "32";
                break;
            case YELLOW:
                index = "33";
                break;
            case BLUE:
                index = "34";
                break;
            case MAGENTA:
                index = "35";
                break;
            case CYAN:
                index = "36";
                break;
            case WHITE:
                index = "37";
                break;
        }

        return "\u001B[" + index + "m" + txt + "\u001B[" + 0 + "m";

    }

    public static TasksFile createTaskFile(int option) throws Exception {
        ViewObj viewObj = new ViewObj();
        ControlObj controlObj = new ControlObj();
        TasksFile tasksFile = null;
        String tasksFileName = "";
        LogFile logFile = new LogFile("logDoc.log");
        switch (option) {
            case 0: // Escape
                viewObj.display("Thank You, Good Luck!");
                break;
            case 1: // Open the recent opened taskFile
                tasksFile = openRecentTasksFile(logFile);
                break;
            case 2: // Open an existing tasks file.
                tasksFile = openExistingTasksFile();
                break;
            case 3: // New Tasks File
                tasksFile = openNewTasksFile();
                break;
        }

        // If taskFile created successfully (which means there is file created on the device), then register its path
        // in the log file.
        if (tasksFile != null) {
            logFile.registerTasksFile(tasksFile.getFilePath());
        }

        return tasksFile;
    }

    private static TasksFile openNewTasksFile() {
        ViewObj viewObj = new ViewObj();
        ControlObj controlObj = new ControlObj();
        String tasksFileName = "";
        TasksFile tasksFile = null;
        boolean repeatedFileName = true;
        while (repeatedFileName) {
            viewObj.display("Enter the tasks file name:");
            tasksFileName = controlObj.readFileName();
            if (TasksFile.exists(tasksFileName)) {
                viewObj.display("There is a tasks file already exists, retype another name, please!");
            } else { // initiated a tasksFile obj
                repeatedFileName = false;
                tasksFile = new TasksFile(tasksFileName);
            }
        }
        return tasksFile;
    }

    private static TasksFile openExistingTasksFile() {
        boolean selected = false;
        ViewObj viewObj = new ViewObj();
        ControlObj controlObj = new ControlObj();
        String tasksFileName = "";
        TasksFile tasksFile = null;
        while (!selected) {
            viewObj.display("You can open one of the following task files:");
            List<String> listOfTaskFiles = FilesHandler.getListOfFiles("tsk");
            viewObj.displayOrdered(listOfTaskFiles);
            viewObj.display("");
            viewObj.display("Enter the number of the index of the file to open!");
            int selectionOfTaskFile = controlObj.readCommandSelection(1, listOfTaskFiles.size()); //returning zero here means that there is no proper selection number is entered.
            if (selectionOfTaskFile != 0){
                tasksFileName = listOfTaskFiles.get(selectionOfTaskFile-1);
                tasksFile = new TasksFile(tasksFileName);
                selected = true;
            } else {
                viewObj.display(viewObj.colorTxt(TxtColor.RED,"There is a problem is selection number, you have to re-enter the index again."));
            }
        }
        return tasksFile;
    }

    private static TasksFile openRecentTasksFile(LogFile logFile) {
        String tasksFileName = FilesHandler.getFileNameFromPath(logFile.readTasksFilePath());
        TasksFile tasksFile = null;
        if (TasksFile.exists(tasksFileName)){
            tasksFile = new TasksFile(tasksFileName);
        } else {
            ViewObj viewObj = new ViewObj();
            viewObj.display("There is no recent tasks file in the log file, opening process is failed, you can rerun the application and select another option");
            tasksFile = null;
        }
        return tasksFile;

    }


}
