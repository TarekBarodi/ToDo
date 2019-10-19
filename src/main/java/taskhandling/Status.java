package taskhandling;

public enum Status {
    UNDONE,
    DONE,
    ;

    public static Status parse(String txt) {
        Status status = null;
        boolean isUNDONE = txt.equals("UNDONE");
        if (txt.equals("UNDONE")) {
            status=  Status.UNDONE;
        } else if (txt.equals("DONE")) {
            status = Status.DONE;
        }
        return status;
    }
}
