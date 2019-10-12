import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TasksPool {
    ArrayList<Task> tasksList;
    TasksFile tasksFile;
    List<String> projects = new ArrayList<>();

    public TasksPool(TasksFile tasksFile) throws IOException {

        // add one item to projects list "None Project"
        projects.add("None Project");

        this.tasksFile = tasksFile;
        tasksList = tasksFile.readTasksList();

        for (int i=0; i<5; ++i) {
            addTaskFromTerminal();
        }


    }


    public Task addTaskFromTerminal(){

        ControlObj controlObj = new ControlObj();
        Task task = controlObj.readTaskFromTerminal(projects);

        // add the task project to the list of projects if it does not exist in the list
        boolean addedProject = addProjectToProjects(task.getProject());

        return task;
    }

    private boolean addProjectToProjects(String project) {
        if (!projects.contains(project)) {
            projects.add(project);
        }

    }
}
