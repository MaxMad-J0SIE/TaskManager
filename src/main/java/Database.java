import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    static String sql = """
            CREATE TABLE IF NOT EXISTS tasks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                description TEXT,
                due_date TEXT NOT NULL,
                status TEXT NOT NULL,
                priority TEXT NOT NULL
            )
            """;

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:data/tasks.db");
    }

    public static void Create_DB(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }
}
