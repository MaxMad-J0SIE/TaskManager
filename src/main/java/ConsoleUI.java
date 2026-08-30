import java.io.IOException;
import java.util.Scanner;

public class ConsoleUI {
//    console controls
    private final TaskService taskService;
    private final Scanner scanner;

    public ConsoleUI(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    public void ScanSystemIn() throws IOException {
//        works
//        TODO make it into a command console controls
        StartTerminal();
        System.out.println("--help to list commands");
        while(scanner.hasNext()) {

        }
    }

    public void StartTerminal() throws IOException {
//        Uncomment the one needed (maybe will be automatic in the future)
//        Windows
        new ProcessBuilder("cmd.exe", "/c", "cmd.exe").start();

//        Mac
//        new ProcessBuilder("open", "-a", "Terminal").start();

//        Linux
//        new ProcessBuilder("gnome-terminal").start();
    }

    public void CommandHandler(String command) {
        if (command.contains("--help")) {
            System.out.println("Command List:");
        }
    }
}
