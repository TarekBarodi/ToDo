
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;

public class ToDoTest {

    @Test
    public void testGetDateViewOption() {
        TasksFile tasksFile = ToDoApp.createTaskFile(1);
        TasksFile tasksFile1 = new TasksFile("ddd.tsk");

        //assertEquals(tasksFile.getFilePath(), tasksFile1.getFilePath());
    }

}
