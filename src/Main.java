import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskRepo repository = new TaskRepo("Tasks.csv");

        TaskService taskService = new TaskService(repository);

        Scanner scanner = new Scanner(System.in);
        ConsoleUI cli = new ConsoleUI(taskService, scanner);
    }
}
