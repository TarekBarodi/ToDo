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
}
