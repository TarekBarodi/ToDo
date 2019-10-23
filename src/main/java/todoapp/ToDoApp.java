package todoapp;

import filehandling.*;
import taskhandling.*;
import userinputhandling.*;
import viewhandling.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoApp {
    private static ViewObj viewObj = new ViewObj();
    private static ControlObj controlObj = new ControlObj();

    public ToDoApp() {

    }

    public static void main(String[] args) {
        //Open a tasks file
        TasksFile tasksFile = openTasksFile();

        //Manage tasks
        manageTasks(tasksFile);


    }

    private static void manageTasks(TasksFile tasksFile) {
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
                viewObj.displayOptions(Options.tasksManagementOptions);

                viewObj.displayPrompt("Enter a number between 1 and 4:");

                // user select an option to (1)view, (2)add, (3)remove, (4)edit a task or (0)quit
                selectedTasksManagementOption = controlObj.readCommandSelection(0, 4); // return -1 if no proper number selected
                viewObj.display((selectedTasksManagementOption == -1) ? "You have to enter a number between 0 and 4, please try again!" : "");
            }

            //the selected option specify the action to be executed regarding managing the tasks list
            switch (selectedTasksManagementOption) {
                case 0: // to quit
                    //just a goodbye message appear with no further action
                    quit();

                    break;
                case 1: // view tasks
                    //Viewing tasks has more options encapsulated in viewTasks method.
                    viewTasks(tasksPool);

                    break;
                case 2: // add task
                    //Add the tasks one by one according to the user input
                    addTasks(tasksPool);

                    break;
                case 3: // remove task
                    removeTasks(tasksPool);


                    break;
                case 4: // edit task

            }
        }
    }

    private static void removeTasks(TasksPool tasksPool) {
        //Display the options to remove tasks
        viewObj.displayInstruction("Pick an option to remove some tasks:");
        viewObj.displayOptions(Options.removeOptions);
        viewObj.displayPrompt("Enter a number either 1 or 2:");

        //Get the selected remove option from the user
        int selectedRemoveOption = controlObj.readCommandSelection(1,2);
        List<Integer> tasksIndices = null;
        List<Task> tasksToRemove = null;
        boolean isConfirmed = false;
        switch (selectedRemoveOption) {
            case 1:
                //Display all tasks and get the tasks indices from the user
                viewObj.displayInstruction("Pick one or more tasks to be removed,");
                tasksIndices = getTasksIndices(tasksPool);

                //Display the tasks to remove
                viewObj.display("You are going to remove the following tasks:");
                tasksToRemove = tasksPool.getTasksByIndices(tasksIndices);
                viewObj.displayAllTasksInColumns(tasksToRemove);

                //Get the user confirmation to remove the tasks
                viewObj.displayConfirmRequest();
                isConfirmed =  controlObj.readYesNo();

                if (isConfirmed) {
                    //Remove the selected tasks from tasksPool
                    tasksPool.removeTasksByIndices(tasksIndices);

                    //Update the tasksFile with the updated tasksPool
                    tasksPool.getTasksFile().updateTasks(tasksPool);
                } else {
                    viewObj.display("You have not confirmed the removal, tasks list is kept the same!");
                }

                //Asking the user if he want to remove more tasks, if not return to the main menu
                viewObj.display("Would you like to remove more tasks? (Y/N");
                if (controlObj.readYesNo()) {
                    removeTasks(tasksPool);
                } else {
                    manageTasks(tasksPool.getTasksFile());
                }

                break;

            case 2: //Lookup the tasks using keywords of tasks titles
                //Get the tasks indices of which their titles contain the keyword entered by user
                viewObj.displayInstruction("Write a keyword to lookup the tasks which their titles match!");
                String keyword = controlObj.getTitleKeyword();
                tasksIndices = getTasksIndicesMatchTitleKeyword(tasksPool, keyword);

                //Display the tasks to remove
                viewObj.display("You are going to remove the following tasks:");
                tasksToRemove = tasksPool.getTasksByIndices(tasksIndices);
                viewObj.displayAllTasksInColumns(tasksToRemove);

                //Get the user confirmation to remove the tasks
                viewObj.displayConfirmRequest();
                isConfirmed =  controlObj.readYesNo();

                if (isConfirmed) {
                    //Remove the selected tasks from tasksPool
                    tasksPool.removeTasksByIndices(tasksIndices);

                    //Update the tasksFile with the updated tasksPool
                    tasksPool.getTasksFile().updateTasks(tasksPool);
                } else {
                    viewObj.display("You have not confirmed the removal, tasks list is kept the same!");
                }

                //Asking the user if he want to remove more tasks, if not return to the main menu
                viewObj.display("Would you like to remove more tasks? (Y/N");
                if (controlObj.readYesNo()) {
                    removeTasks(tasksPool);
                } else {
                    manageTasks(tasksPool.getTasksFile());
                }


    }

}

    private static List<Integer> getTasksIndicesMatchTitleKeyword(TasksPool tasksPool, String keyword) {
        List<Integer> tasksIndices = new ArrayList<>();
        List<Task> tasksList = tasksPool.getTasksList();
        for (int i = 0; i < tasksList.size(); i++){
            Task task = tasksList.get(i);
            if (task.getTitle().contains(keyword)){
                tasksIndices.add((Integer)(i+1));
            }
        }

        return tasksIndices;
    }

    private static void addTasks(TasksPool tasksPool) {
        //Read the task from console as the user enter the required data
        Task task = controlObj.readTask(tasksPool.getProjects());

        //This is adding the task to the tasks list and its project to the projects list in the tasksPool
        tasksPool.addTask(task);

        //Update the tasksFile with the updated tasksPool
        tasksPool.getTasksFile().updateTasks(tasksPool);

        //Asking the user to add one more task
        viewObj.display("Do you would like to add one more task?");
        viewObj.displayConfirmRequest();
        boolean isConfirmed = controlObj.readYesNo();

        if (isConfirmed){
            addTasks(tasksPool);
        } else {
            manageTasks(tasksPool.getTasksFile());
        }
    }

    private static void quit() {
        viewObj.display("Thank You, Good Luck!");
    }

    private static TasksFile openTasksFile() {
        // user select an option to open the Tasks File
        int selectedOpenFileOption = getOpenFileOption();

        //Create a tasksFile depending on the option selected: 1 for recent, 2 for existing, 3 for new file.
        //including registering its path in the log file.
        TasksFile tasksFile = createTaskFile(selectedOpenFileOption);

        return tasksFile;
    }

    private static List<Integer> getTasksIndices(TasksPool tasksPool) {
        boolean isTasksIndicesEntered = false;
        List<Integer> tasksIndices = null;
        while (isTasksIndicesEntered == false) {
            viewObj.display("You have the following tasks:");
            viewObj.displayAllTasksInColumns(tasksPool.getTasksList());
            viewObj.displayPrompt("Enter the indices of the tasks separated by commas:");

            //get the tasks indices entered by user as a list of integers
            tasksIndices = controlObj.readTasksIndices();

            //clean tasksIndices: remove all invalid indices: <= 0 and > projectsCounting
            tasksIndices = tasksPool.cleanTasksIndices(tasksIndices);

            //If the tasksIndices still empty then display a message and keep isTasksIndicesEntered
            //false to keep looping in while body until the user enter a valid entry
            if (tasksIndices.isEmpty()) {
                viewObj.display("Invalid entry, try again!");
            } else {
                isTasksIndicesEntered = true;
            }
        }
        return tasksIndices;
    }


    public static void viewTasks(TasksPool tasksPool) {
        //get the option to how to view the tasks: view all, by project or by due date
        viewObj.displayAsTitle("VIEW THE TASKS LIST");
        int selectedViewTasksOption = getViewTasksOption();

        switch (selectedViewTasksOption) {
            case 1: // view all tasks
                viewObj.displayAsTitle("\nAll Tasks:");
                viewObj.displayAllTasksInColumns(tasksPool.getTasksList());

                //Asking the user if he want to view more tasks, if not return to the main menu
                viewObj.display("Would you like to return to View Tasks Options? (Y/N)");
                if (controlObj.readYesNo()) {
                    viewTasks(tasksPool);
                } else {
                    manageTasks(tasksPool.getTasksFile());
                }
                break;

            case 2: // view by project
                //Get project view option: view all categorized by project or view only related tasks for a project
                int selectedViewTasksByProjectOption = getViewTasksByProjectOption();

                //if option equals 1, then show all tasks categorized by projects
                if (selectedViewTasksByProjectOption == 1) {
                    viewObj.displayAllTasksCategorizedByProject(tasksPool);

                    //if option equals 2, then show only the tasks related to some projects
                } else if (selectedViewTasksByProjectOption == 2) {
                    //display the tasks categorized and sorted by projects
                    viewObj.display("You can now view only the tasks related to the projects you select.");

                    //Get the project indices entered by user
                    List<Integer> projectIndices = getProjectIndices(tasksPool);

                    viewObj.displayTasksByProjects(tasksPool, projectIndices);
                }

                //Asking the user if he want to view more tasks, if not return to the main menu
                viewObj.display("Would you like to return to View Tasks Options? (Y/N)");
                if (controlObj.readYesNo()) {
                    viewTasks(tasksPool);
                } else {
                    manageTasks(tasksPool.getTasksFile());
                }

                break;
            case 3: // view by due date*/
                //Get due date view option: view all tasks sorted by due date, view tasks due by today, view tasks due
                //by certain date
                int selectedViewTasksByDueDateOption = getViewTasksByDueDateOption();

                //if option equals 1, then show all tasks sorted by due date
                if (selectedViewTasksByDueDateOption == 1) {
                    List<Task> tasksListSortedByDueDate = tasksPool.getTasksListSortedByDueDate();
                    viewObj.displayAllTasksInColumns(tasksListSortedByDueDate);
                } else if (selectedViewTasksByDueDateOption == 2) {
                    //display the tasks due by today date
                    viewObj.display("You can now view only the tasks due by today date "
                            + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                    //Get the date of today
                    Date date = new Date();

                    List<Task> tasksListBeforeADueDate = tasksPool.getTasksListBeforeADueDate(date);
                    viewObj.displayAllTasksInColumns(tasksListBeforeADueDate);
                } else if (selectedViewTasksByDueDateOption == 3) {
                    //display the tasks due by a specific date
                    viewObj.display("You can now view only the tasks due by the date you specify.");

                    //Get the date entered by user
                    Date date = controlObj.readTaskDueDateFromTerminal();

                    List<Task> tasksListBeforeADueDate = tasksPool.getTasksListBeforeADueDate(date);
                    viewObj.displayAllTasksInColumns(tasksListBeforeADueDate);
                }

                //Asking the user if he want to view more tasks, if not return to the main menu
                viewObj.display("Would you like to return to View Tasks Options? (Y/N)");
                if (controlObj.readYesNo()) {
                    viewTasks(tasksPool);
                } else {
                    manageTasks(tasksPool.getTasksFile());
                }
        }

    }

    private static int getViewTasksOption() {

        int selectedViewTasksOption = 0;
        //keep looping till the user enter a valid selection number
        while (selectedViewTasksOption == 0) { // continue looping until a proper number picked
            viewObj.displayInstruction("Pick an option to view the tasks:");

            // user will see the options: view all tasks, view by project, view by due date
            viewObj.displayOptions(Options.ViewTasksOption);


            // when the user make a selection, it will stored in selectedViewTasksOption as int
            viewObj.displayPrompt("Enter a number between 1 and 3:");
            selectedViewTasksOption = controlObj.readCommandSelection(1, 3);

            viewObj.display((selectedViewTasksOption == 0) ? "You have to enter a number between 1 and 3, please try again!" : "");
        }
        return selectedViewTasksOption;
    }

    //Get project view option
    private static int getViewTasksByProjectOption() {

        boolean isViewTasksByProjectOptionSelected = false;
        int selectedViewTasksByProjectOption = 0;
        // Loop until a valid option number entered by user
        while (isViewTasksByProjectOptionSelected == false) {
            // display two options: just press enter button or enter projects indices separated by commas
            viewObj.displayInstruction("Pick an option to view tasks by project:");
            viewObj.displayOptions(Options.ViewTasksByProjectOption);

            viewObj.display("Enter a number either 1 or 2:");

            //read the user input
            selectedViewTasksByProjectOption = controlObj.readCommandSelection(1, 2);

            if (selectedViewTasksByProjectOption == 1 | selectedViewTasksByProjectOption == 2) {
                isViewTasksByProjectOptionSelected = true;
            } else {
                viewObj.display("Invalid entry, try again!");
            }
        }
        return selectedViewTasksByProjectOption;
    }

    private static int getViewTasksByDueDateOption() {
        {

            boolean isViewTasksByDueDateOptionSelected = false;
            int selectedViewTasksByDueDateOption = 0;
            // Loop until a valid option number entered by user
            while (isViewTasksByDueDateOptionSelected == false) {
                // display three options: all tasks sorted by due date, tasks due by today, tasks due by other date
                viewObj.displayInstruction("Pick an option to view tasks by due date:");
                viewObj.displayOptions(Options.viewTasksByDueDateOption);

                viewObj.display("Enter a number between 1 and 3:");

                //read the user input
                selectedViewTasksByDueDateOption = controlObj.readCommandSelection(1, 3);

                if (selectedViewTasksByDueDateOption >= 1 && selectedViewTasksByDueDateOption <=3) {
                    isViewTasksByDueDateOptionSelected = true;
                } else {
                    viewObj.display("Invalid entry, try again!");
                }
            }
            return selectedViewTasksByDueDateOption;
        }


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
            projectIndices = tasksPool.cleanProjectsIndices(projectIndices);

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


    public static int getOpenFileOption() {
        boolean isOpenFileOptionSelected = false;
        int selectedOpenFileOption = 0;
        while (!isOpenFileOptionSelected) {
            viewObj.display("Welcome to ToDo List application");
            viewObj.displayInstruction("Pick an option to open a file:");
            viewObj.displayOptions(Options.openFileOptions);

            viewObj.displayPrompt("Enter the selection number 1 or 2 or 3, and press return:");

            try {
                selectedOpenFileOption = controlObj.readCommandSelection(0,3);
                isOpenFileOptionSelected = true;
            } catch (Exception e) {
                viewObj.display("ToDoAp.selectOpenFileOption","Failed to select an option to open file",e);
                return  -1;
            }


        }
        return selectedOpenFileOption;
    }



    public static TasksFile createTaskFile(int openFileOption) {
        TasksFile tasksFile = null;
        String tasksFileName = "";
        LogFile logFile = new LogFile("logDoc.log");
        switch (openFileOption) {
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

}







