package todoapp;

import filehandling.*;
import taskhandling.*;
import userinputhandling.*;
import viewhandling.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ToDoApp {
    private static ViewObj viewObj = new ViewObj();
    private static ControlObj controlObj = new ControlObj();

    public ToDoApp() {

    }

    public static void main(String[] args) {
        // user select an option to open the Tasks File
        int selectedOpenOption = getSelectedOpenFileOption();

        //Create a tasksFile depending on the option selected: 1 for recent, 2 for existing, 3 for new file.
        //including registering its path in the log file.
        TasksFile tasksFile = createTaskFile(selectedOpenOption);

        if (tasksFile != null) {

            // read all tasks in the task file in the device and store as a tasks list in tasksPool object.
            TasksPool tasksPool = new TasksPool(tasksFile); //in the constructor TasksPool read tasksFile

            //report of one line summarizing how many tasks are done and to do.
            // user will see an overview info about total number of done and undone tasks
            viewObj.displayAsTitle("Tasks File '" + tasksPool.getTasksFile().getFileName() + "'Opened");
            viewObj.displayTasksGeneralInfo(tasksPool);

            //a list of options to manage the tasks appear, so that the user can pick one of them
            int selectedTasksManagementOption = -1;

            //looping till the user enter a valid number of option
            while (selectedTasksManagementOption == -1) {
                // user will see the option available to view, add, remove, edit a task or to quit
                viewObj.displayInstruction("Pick an option:");
                viewObj.displayTasksOptions();
                viewObj.displayPrompt("Enter a number between 1 and 4:");

                // user select an option to (1)view, (2)add, (3)remove, (4)edit a task or (0)quit
                selectedTasksManagementOption = controlObj.readCommandSelection(0, 4); // return -1 if no proper number selected
                viewObj.display((selectedTasksManagementOption == -1) ? "You have to enter a number between 0 and 4, please try again!" : "");
            }

            //the selected option specify the action to be executed regarding managing the tasks list
            switch (selectedTasksManagementOption) {
                case 0: // to quit
                    //just a goodbye message appear with no further action
                    viewObj.display("Thank You, Good Luck!");
                    break;
                case 1: // view tasks
                    //Viewing tasks has more options encapsulated in viewTasks method.
                    viewObj.displayAsTitle("VIEW THE TASKS LIST");
                    viewTasks(tasksPool);
                    break;
                case 2: // add task
                    Task task = controlObj.readTask(tasksPool.getProjects());
                    //Adding tasks has more options encapsulated in addTasks method.
                    //addTasks();
                    break;
                case 3: // remove task
                    break;
                case 4: // edit task

            }
        }

       /* //try code
        for (int i=0; i<5; ++i) {
            Task task = controlObj.readTask(tasksPool.projects);
            tasksPool.addTask(task);*/
    }

    public static void viewTasks(TasksPool tasksPool) {

        //get the option to how to view the tasks: view all, by project or by due date
        int selectedViewOption = getViewTasksOption();

        switch (selectedViewOption) {
            case 1: // view all tasks
                viewObj.displayAsTitle("\nAll Tasks:");
                viewObj.displayAllTasksInColumns(tasksPool.getTasksList());
                break;

            case 2: // view by project
                //Get project view option: view all categorized by project or view only related tasks for a project
                int selectedProjectViewOption = getProjectViewOption();

                //if option equals 1, then show all tasks categorized by projects
                if (selectedProjectViewOption == 1) {
                    viewObj.displayAllTasksByProjects(tasksPool);

                    //if option equals 2, then show only the tasks related to some projects
                } else if (selectedProjectViewOption == 2) {
                    //display the tasks categorized and sorted by projects
                    viewObj.display("You can now view only the tasks related to the projects you select.");

                    //Get the project indices entered by user
                    List<Integer> projectIndices = getProjectIndices(tasksPool);

                    viewObj.displayTasksByProjects(tasksPool, projectIndices);
                }

                break;
            case 3: // view by due date*/
                //Get due date view option: view all tasks sorted by due date, view tasks due by today, view tasks due
                //by certain date
                int selectedDateViewOption = getDateViewOption();

                //if option equals 1, then show all tasks sorted by due date
                if (selectedDateViewOption == 1) {
                    List<Task> tasksListSortedByDueDate = tasksPool.getTasksListSortedByDueDate();
                    viewObj.displayAllTasksInColumns(tasksListSortedByDueDate);
                } else if (selectedDateViewOption == 2) {
                    //display the tasks due by today date
                    viewObj.display("You can now view only the tasks due by today date "
                            + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                    //Get the date of today
                    Date date = new Date();

                    List<Task> tasksListBeforeADueDate = tasksPool.getTasksListBeforeADueDate(date);
                    viewObj.displayAllTasksInColumns(tasksListBeforeADueDate);
                } else if (selectedDateViewOption == 3) {
                    //display the tasks due by a specific date
                    viewObj.display("You can now view only the tasks due by the date you specify.");

                    //Get the date entered by user
                    Date date = controlObj.readTaskDueDateFromTerminal();

                    List<Task> tasksListBeforeADueDate = tasksPool.getTasksListBeforeADueDate(date);
                    viewObj.displayAllTasksInColumns(tasksListBeforeADueDate);
                }
        }

    }

    private static int getDateViewOption() {
        {

            boolean isDateOptionSelected = false;
            int selectedDateViewOption = 0;
            // Loop until a valid option number entered by user
            while (isDateOptionSelected == false) {
                // display three options: all tasks sorted by due date, tasks due by today, tasks due by other date
                viewObj.displayInstruction("Pick an option to view tasks by due date:");
                viewObj.displayDateViewOptions();
                viewObj.display("Enter a number between 1 and 3:");

                //read the user input
                selectedDateViewOption = controlObj.readCommandSelection(1, 3);

                if (selectedDateViewOption >= 1 && selectedDateViewOption <=3) {
                    isDateOptionSelected = true;
                } else {
                    viewObj.display("Invalid entry, try again!");
                }
            }
            return selectedDateViewOption;
        }


    }

    private static int getViewTasksOption() {

        int selectedViewOption = 0;
        //keep looping till the user enter a valid selection number
        while (selectedViewOption == 0) { // continue looping until a proper number picked
            viewObj.displayInstruction("Pick an option to view the tasks:");

            // user will see the options: view all tasks, view by project, view by due date
            viewObj.displayTasksViewOptions();

            // when the user make a selection, it will stored in selectedViewOption as int
            viewObj.displayPrompt("Enter a number between 1 and 3:");
            selectedViewOption = controlObj.readCommandSelection(1, 3);

            viewObj.display((selectedViewOption == 0) ? "You have to enter a number between 1 and 3, please try again!" : "");
        }
        return selectedViewOption;
    }

    //Get project view option
    private static int getProjectViewOption() {

        boolean isProjectOptionSelected = false;
        int selectedProjectViewOption = 0;
        // Loop until a valid option number entered by user
        while (isProjectOptionSelected == false) {
            // display two options: just press enter button or enter projects indices separated by commas
            viewObj.displayInstruction("Pick an option to view tasks by project:");
            viewObj.displayProjectViewOptions();
            viewObj.display("Enter a number either 1 or 2:");

            //read the user input
            selectedProjectViewOption = controlObj.readCommandSelection(1, 2);

            if (selectedProjectViewOption == 1 | selectedProjectViewOption == 2) {
                isProjectOptionSelected = true;
            } else {
                viewObj.display("Invalid entry, try again!");
            }
        }
        return selectedProjectViewOption;
    }

    //Get the project indices entered by user
    private static List<Integer> getProjectIndices(TasksPool tasksPool) {
        boolean isProjectIndicesEntered = false;
        List<Integer> projectIndices = null;
        while (isProjectIndicesEntered == false) {
            viewObj.display("You have the following projects:");
            viewObj.displayOrdered(tasksPool.getProjects());
            viewObj.displayPrompt("Enter the indices of the projects separated by commas:");

            //get the project indices entered by user as a list of integers
            projectIndices = controlObj.readProjectsIndices();

            //clean projectIndices: remove all invalid indices: <= 0 and > projectsCounting
            projectIndices = tasksPool.cleanProjectIndices(projectIndices);

            //If the projectIndices still empty then display a message and keep isProjectIndicesEntered
            //false to keep looping in while body until the user enter a valid entry
            if (projectIndices.isEmpty()) {
                viewObj.display("Invalid entry, try again!");
            } else {
                isProjectIndicesEntered = true;
            }
        }
        return projectIndices;
    }


    public static int getSelectedOpenFileOption() {
        boolean isOptionSelected = false;
        int selectedOption = 0;
        while (!isOptionSelected) {
            viewObj.display("Welcome to ToDo List application");
            viewObj.displayInstruction("Pick an option to open a file:");
            viewObj.display(1, "Open the recent Tasks file.");
            viewObj.display(2, "Open an existing Tasks file.");
            viewObj.display(3, "Open a new Tasks file.");
            viewObj.display(0, "Escape ");
            viewObj.displayPrompt("Enter the selection number 1 or 2 or 3, and press return:");

            try {
                selectedOption = controlObj.readCommandSelection(0,3);
                isOptionSelected = true;
            } catch (Exception e) {
                viewObj.display("ToDoAp.selectOpenFileOption","Failed to select an option to open file",e);
                return  -1;
            }


        }
        return selectedOption;
    }



    public static TasksFile createTaskFile(int option) {
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

    private static TasksFile openRecentTasksFile(LogFile logFile) {
        String tasksFileName = FilesHandler.getFileNameFromPath(logFile.readTasksFilePath());
        TasksFile tasksFile = null;
        if (TasksFile.exists(tasksFileName)){
            tasksFile = new TasksFile(tasksFileName);
        } else {
            viewObj.display("There is no recent tasks file in the log file, opening process is failed, you can rerun the application and select another option");
            tasksFile = null;
        }
        return tasksFile;

    }



    private static TasksFile openExistingTasksFile() {
        boolean selected = false;
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

    private static TasksFile openNewTasksFile() {
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

}







