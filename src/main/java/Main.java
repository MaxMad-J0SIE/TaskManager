import java.util.Scanner;

class Main {
    static void main() {
//        task repo to be rewriten

        TaskService taskService = new TaskService(repository);

        Scanner scanner = new Scanner(System.in);
        ConsoleUI cli = new ConsoleUI(taskService, scanner);
        cli.ScanSystemIn();
    }
}
