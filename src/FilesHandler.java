import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilesHandler {
    private String filePath;
    private String fileName;

    private String separator = FileSystems.getDefault().getSeparator();

    public FilesHandler(String aFileName) {
        //set value of this.fileName
        setFileName(aFileName);

        //create the user directory if it does not exist, if the directory is successfully created or already exists, this return true
        String userDirectoryPath = createUserDirectory(); // returning "" means the failure of creation
        try {
            if (userDirectoryPath != "") { // if the user directory exists, then create the file
                String aFilePath = createFileInUserDirectory(aFileName); // returning "" means the failure of creation
                if (aFilePath != "") {
                    filePath = aFilePath;
                } else {
                    throw new IOException("Could not create the file in the user directory");
                }
            } else {
                throw new IOException("Could not create the user directory");
            }
        } catch (IOException e) {
            ViewObj viewObj = new ViewObj();
            viewObj.display("FileHandler Constructor", "Could not create the user directory or a file in it.",e);
        }
    }

    private String createFileInUserDirectory(String aFileName) {
        if (existsUserDirectory()) {
            //composite the file path from strings
            Path aFilePath = Paths.get(getUserDirectoryPath(), aFileName);

            if (!Files.exists(aFilePath)) {
                try {
                    aFilePath = Files.createFile(aFilePath);
                    return aFilePath.toString();
                } catch (IOException e) {
                    ViewObj viewObj = new ViewObj();
                    viewObj.display("FileHandler.createFileInUserDirectory", "Could not create a file", e);
                    return "";
                }
            } else {
                return aFilePath.toString();
            }

        } else {
            return "";
        }
    }

    public boolean existsUserDirectory(){
        //The Current Directory
        String currentDirectory = System.getProperty("user.dir");

        //The new path for the tasks files
        Path userDirectoryPath = Paths.get(currentDirectory,"taskFiles");

        if (Files.exists(userDirectoryPath)) {
            // if the user directory exists then return true
            return true;
        } else {
            return false;
        }
    }

    public String getUserDirectoryPath(){
        //The Current Directory
        String currentDirectory = System.getProperty("user.dir");

        //The new path for the tasks files
        Path userDirectoryPath = Paths.get(currentDirectory,"taskFiles");

        if (Files.exists(userDirectoryPath)) {
            // if the user directory exists then return its path as String
            return userDirectoryPath.toString();
        } else {
            return "";
        }
    }

    private String createUserDirectory() {
        //The Current Directory
        String currentDirectory = System.getProperty("user.dir");

        //The new path for the tasks files
        Path userDirectoryPath = Paths.get(currentDirectory,"taskFiles");

        if (!Files.exists(userDirectoryPath)) {
            try {
                //to create directory
                Path createdDirectory = Files.createDirectory(userDirectoryPath);
                return createdDirectory.toString();
            } catch (IOException e){
                return "";
            }
        } else {
            return userDirectoryPath.toString();
        }

    }

    private void setFileName(String aFileName) {
        this.fileName = aFileName;
    }

    public static List<String> getListOfFiles() {
        List <String> ListOfFiles;
        String currentDirectory = System.getProperty("user.dir");

        List<String> listOfFileNames = new ArrayList<>();

        try {
            ListOfFiles = Files.list(Paths.get(currentDirectory, "taskFiles")).map(Path::toString).collect(Collectors.toList());

            for (String aFilePath :
                    ListOfFiles) {
                listOfFileNames.add(Paths.get(aFilePath).getFileName().toString());
            }
        } catch (IOException e) {
            ViewObj viewObj = new ViewObj();
            viewObj.display("FilesHandler.getListOfFiles"
                            , "Failed to get the list of files in user directory", e);
            listOfFileNames = null;
        }

        return listOfFileNames;
    }

    public static List<String> getListOfFiles(String fileExtent) {
        DirectoryStream<Path> ListOfFiles;
        String currentDirectory = System.getProperty("user.dir");
        List<String> listOfFileNames = new ArrayList<>();
        try {
            ListOfFiles = Files.newDirectoryStream(Paths.get(currentDirectory, "taskFiles"), new DirectoryStream.Filter<Path>() {
                @Override
                public boolean accept(Path entry) throws IOException {
                    return entry.getFileName().toString().endsWith("." + fileExtent);
                }
            });

            for (Path aFilePath :
                    ListOfFiles) {
                listOfFileNames.add(aFilePath.getFileName().toString());
            }
        } catch (IOException e) {
            ViewObj viewObj = new ViewObj();
            viewObj.display("FilesHandler.getListOfFiles","Failed to filter the list of tasks files",e);
            listOfFileNames = null;
        }
        return listOfFileNames;
    }

    public static boolean exists(String aFileName) {
        //The Current Directory
        String currentDirectory = System.getProperty("user.dir");

        //The new path for the tasks files
        Path targetDirectoryPath = Paths.get(currentDirectory,"taskFiles");


        //Add the tasks directory if it does not exist yet
        if (!Files.exists(targetDirectoryPath)) {
            //The created directory
            return false;
        } else {
            //The task file path
            Path targetFilePath = Paths.get(targetDirectoryPath.toString(),aFileName);

            if (!Files.exists(targetFilePath)) {
                return false;
            } else {
                return true;
            }

        }



    }






    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }


    public static String getFileNameFromPath(String aFilePath){
        return Paths.get(aFilePath).getFileName().toString();

    }
}
