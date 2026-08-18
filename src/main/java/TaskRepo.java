import java.util.ArrayList;
import java.util.List;

public class TaskRepo {
//    reading and writing the json file
//    reads once when opening the app and puts in into object instances
//    save every time new task is added

    private final String dbConn;

    public TaskRepo(String dbConn) {
        this.dbConn = dbConn;
    }

    public static String ReadDB() {
//        reading the file and convert it into list of objects (List<Task>)
        String tasks = "";
        return tasks;
    }

    public static void SaveUpdateDB(List<Task> tasks) {
//        updated the whole db no matter how much data was updated - foolproof method
    }
    
}
