import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class TasksPool {
    private ArrayList<Task> tasksList;
    private TasksFile tasksFile;
    private List<String> projects = new ArrayList<>();

    public TasksPool(TasksFile tasksFile) {

        // add one item to projects list "None Project"
        projects.add("None Project");

        this.tasksFile = tasksFile;

        // get the tasks list from the tasks file
        tasksList = tasksFile.readTasksList();

        // get the projects list from the tasks list
        for (Task task:
             tasksList) {
            addProjectToProjects(task.getProject());
        }

    }

    public boolean addTask(Task task){

        // add the task to the tasks list
        boolean addedTask = tasksList.add(task);

        // add the task project to the list of projects if it does not exist in the list
        boolean addedProject = addProjectToProjects(task.getProject());


        return addedTask;
    }


    private boolean addProjectToProjects(String project) {
        if (!projects.contains(project)) {
            projects.add(project);
            return true;
        } else {
            return false;
        }

    }

    public int getCountOfUndoneTasks() {
        int size = tasksList.size();
        int counter = 0;

        for (int i = 0; i < size; ++i) {
            if (tasksList.get(i).getStatus() == Status.UNDONE) {
                counter++;
            }
        }

        return counter;
    }

    public int getCountOfDoneTask() {
        int size = tasksList.size();
        int counter = 0;

        for (int i = 0; i < size; ++i) {
            if (tasksList.get(i).getStatus() == Status.DONE) {
                counter++;
            }
        }

        return counter;
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    public List<Task> getTasksListByProject(String projectName) {
        List<Task> tasksForProject = new ArrayList<>();

        if (projects.contains(projectName)){
            for (Task task: tasksList) {
                if (task.getProject().equals(projectName)) {
                    tasksForProject.add(task);
                }
            }
        }

        return tasksForProject;

    }

    public TasksFile getTasksFile() { return tasksFile; }

    public List<String> getProjects() {
        return projects;
    }


    public List<Integer> cleanProjectIndices(List<Integer> projectIndices) {
        int size = projects.size();

        // Check if all projectIndices withing the size of the projectsList, Eliminate all out of index numbers
        //sorting projectsIndices list
        Collections.sort(projectIndices);

        //Remove all projectsIndices values greater than size of projectsList or less than or equals zero
        //case 1: projectIndex > projectsList size
        //  projectIndex [1 - number of projects]
        //case 2: projectIndex <= 0
        Iterator<Integer> it = projectIndices.iterator();
        //iterate over all items in projectIndices
        while (it.hasNext()) {
            Integer projectIndex = it.next();
            //compare projectIndex
            if (projectIndex > size | projectIndex <= 0) {
                it.remove();
            }
        }

        return projectIndices;

    }

    public List<Task> getTasksListSortedByDueDate() {
        List<Task> tasksListSortedByDueDate = new ArrayList<>(tasksList);

        Collections.sort(tasksListSortedByDueDate, (a,b)->{return a.getDueDate().compareTo(b.getDueDate());});

        return tasksListSortedByDueDate;
    }

    public List<Task> getTasksListBeforeADueDate(Date date) {
        List<Task> getTasksListBeforeADueDate = getTasksListSortedByDueDate();

        getTasksListBeforeADueDate.removeIf(p -> p.getDueDate().after(date));

        return getTasksListBeforeADueDate;
    }
}
