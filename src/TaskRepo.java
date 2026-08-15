import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
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

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("id")) {
                    continue;
                }

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0].trim());
                String title = data[1].trim();
                boolean isCompleted = Boolean.parseBoolean(data[2].trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return tasks;
    }

    public void SaveCSV(List<Task> tasks) {
//        takes the list of objects (List<Task>) and parses it into the csv
    }
    
}
