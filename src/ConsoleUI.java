import java.util.Scanner;

public class ConsoleUI {
//    console controls
    private final TaskService taskService;
    private final Scanner scanner;

    public ConsoleUI(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }
}
