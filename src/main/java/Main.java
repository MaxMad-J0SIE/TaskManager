import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

class Main {
    static void main() throws SQLException {
        Connection conn = Database.connect();
        Database.Create_DB(conn);

        TaskRepo repository = new TaskRepo(conn);
        List<Task> taskList = repository.ReadDB();

//        TaskService taskService = new TaskService(repository);

        Scanner scanner = new Scanner(System.in);
        ConsoleUI cli = new ConsoleUI(taskService, scanner);
        cli.ScanSystemIn();
    }
}
