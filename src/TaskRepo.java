import java.io.*;
import java.nio.file.*;
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

    public void ReadCSV() {
//        reading the file and convert it into list of objects (List<Task>)
    }

    public void SaveCSV() {
//        takes the list of objects (List<Task>) and parses it into the csv
    }
    
}
