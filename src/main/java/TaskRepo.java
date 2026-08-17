import java.util.ArrayList;
import java.util.List;

public class TaskRepo {
//    reading and writing the json file
//    reads once when opening the app and puts in into object instances
//    save every time new task is added

    private final String filePath;

    public TaskRepo(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> ReadCSV() {
//        reading the file and convert it into list of objects (List<Task>)
        List<Task> tasks = new ArrayList<Task>();
        return tasks;
    }

    public void SaveCSV(List<Task> tasks) {
//        takes the list of objects (List<Task>) and parses it into the csv
    }
    
}
