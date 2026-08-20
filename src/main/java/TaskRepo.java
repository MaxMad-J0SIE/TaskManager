import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class TaskRepo {
//    reading and writing the json file
//    reads once when opening the app and puts in into object instances
//    save every time new task is added

    private final Connection dbConn;

    public TaskRepo(Connection dbConn) {
        this.dbConn = dbConn;
    }

    public List<Task> ReadDB() throws SQLException {
//        reading the file and convert it into list of objects (List<Task>)
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try(PreparedStatement ps = dbConn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        LocalDate.parse(rs.getString("due_date")),
                        Priority.valueOf(rs.getString("priority"))
                );
                task.setStatus(Status.valueOf(rs.getString("status")));
                task.setDescription(rs.getString("description"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public static void SaveUpdateDB(List<Task> tasks) {
//        updated the whole db no matter how much data was updated - foolproof method
    }
    
}
