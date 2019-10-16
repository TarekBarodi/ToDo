import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ControlObj {
    public int readCommandSelection(int bottomLimit, int topLimit) {
        Scanner scanner;
        int intSelection = bottomLimit-1;
        try {
            scanner = new Scanner(System.in);

            if (scanner.hasNextInt()) {
                intSelection = scanner.nextInt();
                if(intSelection >= bottomLimit && intSelection <= topLimit){
                    return intSelection;
                } else {
                    intSelection = bottomLimit-1;
                    throw new IndexOutOfBoundsException("Selection number is out of bound in controlObj.readCommandSelection method");
                }
            }

        } catch (Exception e) {
            ViewObj viewObj = new ViewObj();
            viewObj.display("ControlObj.readCommandSelection", "Failed to read the command selection.",e);
            intSelection = bottomLimit-1;
        } finally {
            return intSelection;
        }

    }

    public static boolean isInteger(String txt){
        try{
            Integer.parseInt(txt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String readFileName() {
        Scanner scanner = new Scanner(System.in);
        try {
            String newFileName = scanner.nextLine();
            newFileName = newFileName.replaceAll("\\s+", "") + ".tsk";
            if (newFileName.equals(".tsk")) {
                throw new IOException("Blank file name!");
            }
            if (newFileName.startsWith("-") | newFileName.startsWith("?") | newFileName.startsWith("+") | newFileName.startsWith("=") | newFileName.startsWith("&") | newFileName.startsWith("€") | newFileName.startsWith("!")){
                throw new IOException("file name cannot start with a symbol, use letters.");
            }
            return newFileName;
        }catch (Exception e){
            System.out.println("Failure in ControlObj.readFileName method; " + e.getMessage());
            return "";
        }
    }

    public Task readTask(List<String> projects) {
        Task task = new Task();

        Scanner scanner = new Scanner(System.in);
        ViewObj viewObj = new ViewObj();

        viewObj.display("Please insert the fields required for the Task.");

        // get the task title
        task.setTitle(readTaskTitleFromTerminal());

        // get the due date
        task.setDueDate(readTaskDueDateFromTerminal());

        // set the current date to the created date of the task
        Date createdDate = new Date();
        task.setCreatedDate(createdDate);

        // get the project name
        task.setProject(readTaskProjectFromTerminal(projects));


        // set the status to undone
        task.setStatus(Status.UNDONE);

        viewObj.display(task);


        return task;
    }



    private String readTaskProjectFromTerminal(List<String> projects) {
        ViewObj viewObj = new ViewObj();
        String projectName = "";

        //display the list of projects, 0 to enter the project name manually, and all existing projects are listed
        viewObj.display("To enter the project the task assigned to, kindly select one of the following options:");
        viewObj.display(0,"Enter new project name.");
        viewObj.displayOrdered(projects);

        // get the selection number, zero or an index of an existing project
        int selection = this.readCommandSelection(0, projects.size()); // can return -1 in case of failed selection

        //get project name according to each possible value of selection
        if (selection > 0) { // one of the projects is selected
            projectName = projects.get(selection-1);
        } else if (selection == 0) { // zero means that the user is going to enter the project name manually
            projectName = readNewProjectName();
            projectName = (projectName == "") ? projects.get(0) : projectName;
        } else if (selection == -1){
            // if selection failed, then the first item in projects list will be selected i.e. "None Project"
            projectName = projects.get(0);
        }
        return projectName;
    }

    private String readNewProjectName() {
        String projectName = null;
        ViewObj viewObj = new ViewObj();
        viewObj.display("Enter the new project name:");
        Scanner scanner = new Scanner(System.in);
        try {
            projectName = scanner.nextLine();
        } catch (Exception e) {
            projectName = "";
        }
        return projectName;
    }

    private Date readTaskDueDateFromTerminal() {
        ViewObj viewObj = new ViewObj();
        viewObj.display("Enter the due date in the format YYYY-MM-DD");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        boolean isDateParsed = false;
        Date dueDate = null;
        while (!isDateParsed) {
            try {
                Scanner scanner = new Scanner(System.in);
                String strDate = scanner.next();
                dueDate = dateFormat.parse(strDate);
                isDateParsed = true;
            } catch (ParseException e) {
                viewObj.display("ControlObj.readTaskFromTerminal","Could not read the date from terminal",e);
                viewObj.display("Re-enter the date in the correct format YYYY-MM-DD");
            }
        }
        return dueDate;

    }

    private String readTaskTitleFromTerminal() {
        ViewObj viewObj = new ViewObj();
        Scanner scanner = new Scanner(System.in);
        viewObj.display("Enter the task title:");
        return scanner.nextLine();
    }

    public List<Integer> readProjectsIndices() {
        List<Integer> projectsIndices = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextLine()) {
            String wholeLine = scanner.nextLine();
            wholeLine = wholeLine.replaceAll("\\s+","");

            scanner = new Scanner(wholeLine);
            scanner.useDelimiter(",");
            while (scanner.hasNextInt()) {
                projectsIndices.add(scanner.nextInt());
            }
        }

        return projectsIndices;

    }
}
