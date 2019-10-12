import java.io.IOException;
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
}
