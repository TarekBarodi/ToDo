package taskhandling;

import filehandling.*;

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


    public List<Integer> cleanProjectsIndices(List<Integer> projectIndices) {
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


    public List<Integer> cleanTasksIndices(List<Integer> tasksIndices) {
        int size = tasksList.size();

        // Check if all tasksIndices withing the size of the tasksList, Eliminate all out of index numbers
        //sorting tasksIndices list
        Collections.sort(tasksIndices);

        //Remove all tasksIndices values greater than size of tasksList or less than or equals zero
        //case 1: taskIndex > tasksList size
        //  taskIndex [1 - number of tasks]
        //case 2: taskIndex <= 0
        Iterator<Integer> it = tasksIndices.iterator();
        //iterate over all items in tasksIndices
        while (it.hasNext()) {
            Integer taskIndex = it.next();
            //compare taskIndex
            if (taskIndex > size || taskIndex <= 0) {
                it.remove();
            }
        }

        return tasksIndices;

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

    public List<Task> getTasksByIndices(List<Integer> tasksIndices){
        //Make a list of the tasks as per the tasksIndices
        List<Task> tasksByIndices = new ArrayList<>();
        for (Integer index:tasksIndices) {
            tasksByIndices.add(tasksList.get(index-1));
        }

        return tasksByIndices;
    }

    public void removeTasksByIndices(List<Integer> tasksIndices) {
        //Make a list of the tasks to be removed, this list is used to figure out which tasks to be removed from the
        //master list of the tasks (tasksList)
        List<Task> tasksToRemove = new ArrayList<>();
        for (Integer index:tasksIndices) {
            tasksToRemove.add(tasksList.get(index-1));
        }

        //Here we have one iterator which is looping only one time over the tasksList. In the same time there is another
        //loop over the tasks to remove, and this will be one time also.
        //We assume that there is no repetition of the tasks in tasksList.
        Iterator<Task> it = tasksList.iterator();
        for (Task task:tasksToRemove){
            boolean found = false;
            while (it.hasNext() && !found){
                if (it.next() == task) {
                    it.remove();
                    found = true;
                }
            }
        }

    }
}
