import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TasksPool {
    private ArrayList<Task> tasksList;
    private TasksFile tasksFile;
    List<String> projects = new ArrayList<>();

    public TasksPool(TasksFile tasksFile) {

        // add one item to projects list "None Project"
        projects.add("None Project");

        this.tasksFile = tasksFile;

        // get the tasks list from the tasks file
        tasksList = tasksFile.readTasksList(); //not implemented yet

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

    public TasksFile getTasksFile() { return tasksFile; }
}
