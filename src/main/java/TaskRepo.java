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

    public void SaveUpdateDB(List<Task> tasks) throws SQLException {
//        tasks will be in when method is initialised
//        updated the whole db no matter how much data was updated - foolproof method
        String sqlDelete = "DELETE FROM tasks";
        String sqlInsert = "INSERT INTO tasks(id, title, description, due_date, status, priority) VALUES(?, ?, ?, ?, ?, ?)";

        try {
            dbConn.setAutoCommit(false);
            try (PreparedStatement deleteStmt = dbConn.prepareStatement(sqlDelete)) {
                deleteStmt.executeUpdate();
            }

            try (PreparedStatement insertStmt = dbConn.prepareStatement(sqlInsert)) {
                for (Task task : tasks) {
                    insertStmt.setInt(1, task.getId());
                    insertStmt.setString(2, task.getTitle());
                    insertStmt.setString(3, task.getDescription());
                    insertStmt.setString(4, task.getDueDate().toString());
                    insertStmt.setString(5, task.getStatus().name());
                    insertStmt.setString(6, task.getPriority().name());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }

            dbConn.commit();
        } catch (SQLException e) {
            dbConn.rollback();
            throw e;
        } finally {
            dbConn.setAutoCommit(true);
        }
    }
    
}
