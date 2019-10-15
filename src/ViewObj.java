import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewObj {
    public void display(String text) {
        System.out.println(text);
    }

    public void display(int i, String s) {
        System.out.println("(" + i + ")" + "\t" + s);
    }

    public void display(List<String> ListOfStrings) {
        for (String txt:
             ListOfStrings) {
            System.out.println((txt));
        }
    }

    public void displayOrdered(List<String> ListOfStrings) {
        int counter = 0;
        for (String txt:
                ListOfStrings) {
            counter++;
            System.out.println("(" + counter + ")" + "\t" + txt);
        }
    }

    public void display(String methodWhereLocated,String reason, Exception e) {
        System.out.println(colorTxt(TxtColor.RED,"There is an Exception;" )
                + colorTxt(TxtColor.YELLOW,"\n Method:\t\t\t")
                + methodWhereLocated + colorTxt(TxtColor.YELLOW,"\n Reason:\t\t\t")
                + reason + colorTxt(TxtColor.YELLOW,"\n Exception Message:\t") + e.getMessage());
    }

    public String Bold(String txt) {
        return "\u001B[" + 1 + "m" + txt + "\u001B[" + 0 + "m";
    }

    public String Thin(String txt) {
        return "\u001B[" + 0 + "m" + txt + "\u001B[" + 0 + "m";
    }

    public String Underline(String txt) {
        return "\u001B[" + 4 + "m" + txt + "\u001B[" + 0 + "m";
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


    public void display(Task task) {
        System.out.println(colorTxt(TxtColor.BLUE,"Task Information:"));
        System.out.println("Task Title:\t\t\t" + task.getTitle());
        System.out.println("Task Created Date\t" + task.getCreatedDate());
        System.out.println("Task Due Date:\t\t" + task.getDueDate());
        System.out.println("Task Project:\t\t" + task.getProject());
        System.out.println("Task Status:\t\t" + task.getStatus());
        System.out.println();
    }



    public void displayProjectViewOptions() {
        display("Enter the indices of the projects separated by commas,");
        display("OR");
        display("Press enter to show all tasks sorted by projects.");
        display("");
    }

    public void displayTasksOptions() {
        display(1, "View the tasks list");
        display(2, "Add new task");
        display(3, "Remove a task");
        display(4, "Edit a task");
        display(0, "Quit");
    }

    public void displayTasksGeneralInfo(TasksPool tasksPool) {
        display("\nYou have " + tasksPool.getCountOfUndoneTasks() + " tasks to do, and "
                + tasksPool.getCountOfDoneTask() + " tasks done.");
    }

    public void displayTasksViewOptions() {
        display(1, "View All");
        display(2, "View by project");
        display(3, "View by due date");
    }

    public void displayAllTasks(TasksPool tasksPool) {
        List<Task> tasks = tasksPool.getTasksList();

        int numberOfTasks = tasks.size();
        for (int i = 0; i < numberOfTasks; i++) {
            displayTaskInLine(tasks.get(i));
        }
    }

    public void displayTaskInLine(Task task){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        display(task.getTitle() + "\t" + dateFormat.format(task.getDueDate()) + "\t" + task.getProject() + "\t"
                + task.getStatus());
    }

    public void displayAllTasksInColumns(TasksPool tasksPool){ //Tasks are presented in well formatted table,
                                                               // with headers.
        List<Task> tasks = tasksPool.getTasksList();
        List<Integer> tabs = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int[] maxNumberOfChars = {0,0,0,0,0}; // related to index, task title, due date, project and status respectively
        int[] numberOfChars = {0,0,0,0,0}; // related to index, task title, due date, project and status respectively

        for (int i = 0; i < tasks.size(); i++) {
            Task value = tasks.get(i); //looping over elements in tasksList
            numberOfChars[0] = (""+i+1).length();
            numberOfChars[1] = value.getTitle().length();
            numberOfChars[2] = dateFormat.format(value.getDueDate()).length();
            numberOfChars[3] = value.getProject().length();
            numberOfChars[4] = value.getStatus().toString().length();

            //update the max number of characters of each field
            for (int j = 0; j <= 4; j++) {
                if (numberOfChars[j] > maxNumberOfChars[j]) {
                    maxNumberOfChars[j] = numberOfChars[j];
                }
            }
        }

        // every tab is 8 characters, and the value in tabs list represent the number of string tabs that can cover
        // the with of the field
        //0 for title, 1 for due date, 2 for project and 3 for status
        for (int j = 0; j <= 4; j++) tabs.add(maxNumberOfChars[j] / 8 + 1);

        //display the headers of the columns: Title, Due Date, Project and Status
        displayTasksHeaderInColumns(tabs);

        // display each task in a row, using tabs to format the with of columns
        //looping over elements in tasksList
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            displayTaskInColumns(i+1,task, tabs);
        }

    }

    private void displayTasksHeaderInColumns(List<Integer> tabs) {
        String aTab = "\t";
        System.out.printf("%-"+tabs.get(0)*8+"s" +"%-"+tabs.get(1)*8+"s" +"%-"+tabs.get(2)*8+"s"
                        +"%-"+tabs.get(3)*8+"s" +"%-"+tabs.get(4)*8+"s%n"
                        ,"Index" , "Title", "DueDate", "Project" , "Status");
        System.out.printf("%-"+tabs.get(0)*8+"s"+"%-"+tabs.get(1)*8+"s"+"%-"+tabs.get(2)*8 +"s"
                        +"%-"+tabs.get(3)*8+"s" +"%-"+tabs.get(4)*8+"s%n"
                ,"-".repeat(tabs.get(0)*8-2), "-".repeat(tabs.get(1)*8-2), "-".repeat(tabs.get(2)*8-2)
                , "-".repeat(tabs.get(3)*8-2) , "-".repeat(tabs.get(4)*8-2));
    }

    public void displayTaskInColumns(int index, Task task, List<Integer> tabs) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String aTab = "\t";
        System.out.printf("%-"+tabs.get(0)*8+"s"+"%-"+tabs.get(1)*8+"s"+"%-"+tabs.get(2)*8+"s"
                        +"%-"+tabs.get(3)*8+"s" +"%-"+tabs.get(4)*8+"s%n"
                        ,""+index ,task.getTitle(), dateFormat.format(task.getDueDate())
                        , task.getProject() , task.getStatus());
    }


    public void displayAsTitle(String txt) {
        display(Underline(Bold("\n"+txt)));
    }

    public void displayPrompt(String txt) {
        display(colorTxt(TxtColor.RED,txt));
    }

    public void displayInstruction(String txt) {
        display(colorTxt(TxtColor.YELLOW,txt));
    }
}
