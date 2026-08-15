import java.util.Scanner;

class Main {
    static void main() {
        TaskRepo repository = new TaskRepo("Tasks.csv");

        TaskService taskService = new TaskService(repository);

        Scanner scanner = new Scanner(System.in);
        ConsoleUI cli = new ConsoleUI(taskService, scanner);
        cli.ScanSystemIn();
    }
}
