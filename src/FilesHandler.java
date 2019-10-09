import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilesHandler {
    private String filePath;
    private String fileName;

    private String separator = FileSystems.getDefault().getSeparator();

    public FilesHandler(String aFileName) throws IOException {
        fileName = aFileName;

        boolean fileExists = exists(aFileName);

        //The Current Directory
        String currentDirectory = System.getProperty("user.dir");

        //The new path for the tasks files
        Path targetDirectoryPath = Paths.get(currentDirectory,"taskFiles");

        Path targetFilePath;
        if (fileExists){ // if the file exists just save the path to filePath

            //The task file path
            targetFilePath = Paths.get(targetDirectoryPath.toString(),aFileName);

            filePath = targetFilePath.toString();
        } else { // if the file does not exist create it first, then save the path to filePath
            //Add the tasks directory if it does not exist yet
            if (!Files.exists(targetDirectoryPath)) {
                //The created directory
                Path createdDirectory = Files.createDirectory(targetDirectoryPath);
            }

            //The task file path
            targetFilePath = Paths.get(targetDirectoryPath.toString(),aFileName);

            if (!Files.exists(targetFilePath)) {
                targetFilePath = Files.createFile(targetFilePath);
                filePath = targetFilePath.toString();
            }

        }



    }

    public static List<String> getListOfFiles() throws IOException {
        List <String> ListOfFiles;
        String currentDirectory = System.getProperty("user.dir");
        ListOfFiles = Files.list(Paths.get(currentDirectory,"taskFiles")).map(Path::toString).collect(Collectors.toList());

        List<String> ListOfFileNames = new ArrayList<>();
        for (String aFilePath:
                ListOfFiles) {
            ListOfFileNames.add(Paths.get(aFilePath).getFileName().toString());
        }

        return ListOfFileNames;
    }

    public static List<String> getListOfFiles(String fileExtent) throws IOException {
        DirectoryStream<Path> ListOfFiles;
        String currentDirectory = System.getProperty("user.dir");
        ListOfFiles = Files.newDirectoryStream(Paths.get(currentDirectory,"taskFiles"), new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return entry.getFileName().toString().endsWith("." + fileExtent);
            }
        });

        List<String> ListOfFileNames = new ArrayList<>();
        for (Path aFilePath:
                ListOfFiles) {
            ListOfFileNames.add(aFilePath.getFileName().toString());
        }

        return ListOfFileNames;
    }

    public static boolean exists(String aFileName) throws IOException {
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
