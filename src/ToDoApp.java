import java.util.List;

public class ToDoApp {

    public ToDoApp() {

    }

    public static void main(String[] args) throws Exception {
        // user select an option to open the Tasks File
        int selectedOpenOption = selectOpenFileOption();

        //Create a tasksFile depending on the option selected: 1 for recent, 2 for existing, 3 for new file.
        //including registering its path in the log file.
        TasksFile tasksFile = createTaskFile(selectedOpenOption);

        // warning: to do the following TasksFile should not be null
        TasksPool tasksPool = new TasksPool(tasksFile); //in the constructor TasksPool read tasksFile

        ViewObj viewObj = new ViewObj();

        // user will see an overview info about total number of done and undone tasks
        viewObj.displayAsTitle("Tasks File '" + tasksPool.getTasksFile().getFileName() + "'Opened");
        viewObj.displayTasksGeneralInfo(tasksPool);


        ControlObj controlObj = new ControlObj();
        int selectedTasksOption = -1;
        while (selectedTasksOption == -1) {
            // user will see the option available to view, add, remove, edit a task or to quit
            viewObj.displayInstruction("Pick an option:");
            viewObj.displayTasksOptions();
            viewObj.displayPrompt("Enter a number between 1 and 4:");

            // user select an option to (1)view, (2)add, (3)remove, (4)edit a task or (0)quit
            selectedTasksOption = controlObj.readCommandSelection(0, 4); // return -1 if no proper number selected
            viewObj.display((selectedTasksOption == -1) ? "You have to enter a number between 0 and 4, please try again!" : "");
        }


        switch (selectedTasksOption) {
            case 0: // to quit
                viewObj.display("Thank You, Good Luck!");
                break;
            case 1: // view tasks
                viewObj.displayAsTitle("VIEW THE TASKS LIST");
                viewTasks(tasksPool);
                break;
            case 2: // add task
                Task task = controlObj.readTask(tasksPool.projects);
                tasksPool.addTask(task);
                break;
            case 3: // remove task
                break;
            case 4: // edit task

        }

       /* //try code
        ControlObj controlObj = new ControlObj();
        for (int i=0; i<5; ++i) {
            Task task = controlObj.readTask(tasksPool.projects);
            tasksPool.addTask(task);*/
    }

    public static void viewTasks(TasksPool tasksPool) {
        ViewObj viewObj = new ViewObj();
        ControlObj controlObj = new ControlObj();

        int selectedViewOption = 0;
        while (selectedViewOption == 0) { // continue looping until a proper number picked
            viewObj.display("Pick an option to view the tasks:");

            // user will see the options: view all tasks, view by project, view by due date
            viewObj.displayTasksViewOptions();

            // when the user make a selection, it will stored in selectedViewOption as int
            viewObj.displayPrompt("Enter a number between 1 and 3:");
            selectedViewOption = controlObj.readCommandSelection(1, 3);

            viewObj.display((selectedViewOption == 0) ? "You have to enter a number between 1 and 3, please try again!" : "");
        }

        switch (selectedViewOption) {
            case 1: // view all tasks
                viewObj.displayAsTitle("\nAll Tasks:");
                viewObj.displayAllTasksInColumns(tasksPool);
                break;
            /*case 2: // view by project
                viewObj.display("You have the following projects:");
                viewObj.displayOrdered(tasksPool.projects);

                viewObj.displayProjectViewOptions();

                int[] projectIndices = controlObj.readProjectsIndices(); //projects indices starts from 0, if it is -1
                                                                         // this means no entered value
                viewObj.displayTasksByProjects(projectIndices);
                break;
            case 3: // view by due date*/

        }


    }


    public static int selectOpenFileOption() throws Exception {
        boolean isOptionSelected = false;
        int selectedOption = 0;
        while (!isOptionSelected) {
            ViewObj viewObj = new ViewObj();
            viewObj.display("Welcome to ToDo List application");
            viewObj.displayInstruction("Pick an option to open a file:");
            viewObj.display(1, "Open the recent Tasks file.");
            viewObj.display(2, "Open an existing Tasks file.");
            viewObj.display(3, "Open a new Tasks file.");
            viewObj.display(0, "Escape ");
            viewObj.displayPrompt("Enter the selection number 1 or 2 or 3, and press return:");

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





