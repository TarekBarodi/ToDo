import java.io.IOException;
import java.util.Scanner;

public class ControlObj {
    public int readCommandSelection(int bottomLimit, int topLimit) throws Exception {
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
            System.out.println("Failure in controlObj.readCommandSelection method; " + e.getMessage());
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
}
