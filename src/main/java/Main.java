import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

class Main {
    static void main() throws SQLException {
        Connection conn = Database.connect();
        Database.Create_DB(conn);

        TaskRepo repository = new TaskRepo(conn);

        TaskService taskService = new TaskService(repository);

        Scanner scanner = new Scanner(System.in);
        ConsoleUI cli = new ConsoleUI(taskService, scanner);
        cli.ScanSystemIn();
    }
}
