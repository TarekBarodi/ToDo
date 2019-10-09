import java.util.Scanner;

public class ControlObj {
    public int readCommandSelection(int bottomLimit, int topLimit) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String selection = "";
        int intSelection = 0;
        try {
            selection = scanner.next();
            if (selection != "" && selection != null) {
                if (isInteger(selection)) {
                    intSelection = Integer.parseInt(selection);
                    if (intSelection >= bottomLimit && intSelection <= topLimit) {
                        return intSelection;
                    } else {
                        intSelection = bottomLimit-1;
                        throw new IndexOutOfBoundsException("Selection number should be between " + bottomLimit + " and " + topLimit);
                    }
                } else {
                    intSelection = bottomLimit-1;
                    throw new Exception("Your selection should be a number");
                }
            }


        } catch (Exception e) {
            throw new Exception("Cannot figure out what you have selected, you can rerun the application and make a proper selection.");

        } finally {
            return  intSelection;
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
            return newFileName;
        }catch (Exception e){
            return "";
        }
    }
}
