import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
//    console controls
    private final TaskService taskService;
    private final Scanner scanner;

    public ConsoleUI(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    public void ScanSystemIn() throws IOException, SQLException {
//        works
//        TODO make it into a command console controls
        StartTerminal();
        System.out.println("help to list commands");
        while(scanner.hasNext()) {
            CommandHandler(scanner.nextLine());
        }
    }

    public void StartTerminal() throws IOException {
//        Uncomment the one needed (maybe will be automatic in the future)
//        Windows
//        new ProcessBuilder("cmd.exe", "/c", "cmd.exe").start();

//        Mac
        new ProcessBuilder("open", "-a", "Terminal").start();

//        Linux
//        new ProcessBuilder("gnome-terminal").start();
    }

    public void CommandHandler(String command) throws SQLException {
        if (command.toLowerCase().startsWith("add")) {
            AddCommandHandler();
        } else if (command.toLowerCase().startsWith("delete")) {
            DeleteCommandHandler();
        } else if (command.toLowerCase().startsWith("update")) {
            UpdateCommandHandler();
        } else if (command.toLowerCase().startsWith("search")) {
            SearchCommandHandler();
        } else if (command.toLowerCase().startsWith("tasklist")) {
            ReturnTasksCommandHandler();
        } else if (command.toLowerCase().startsWith("help")) {
            HelpCommandHandler();
        }
    }

    public void AddCommandHandler() throws SQLException {
        System.out.println("Please input task details: ");
        System.out.print("Task Title: ");
        String title = scanner.nextLine();
        System.out.print("Task Description (optional): ");
        String description = scanner.nextLine();
        System.out.print("Task Deadline (format YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();
        System.out.print("Task Priority (LOW, MEDIUM, HIGH): ");
        String priority = scanner.nextLine();

        taskService.CreateTask(title, description, dueDate, priority);
    }

    public void DeleteCommandHandler() throws SQLException {

    }

    public void UpdateCommandHandler() throws SQLException {

    }

    public void SearchCommandHandler() throws SQLException {

    }

    public void ReturnTasksCommandHandler() {
        List<Task> tasks;
        tasks = taskService.TaskList();
        System.out.println("Task List");
        for (Task task : tasks) {
            System.out.println(task.getId() + " " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Deadline: " + task.getDueDate());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Priority: " + task.getPriority());
            System.out.println();
        }
    }

    public void HelpCommandHandler() {
        System.out.println("Add task by typing: add");
        System.out.println("Delet task byt typing: delete");
        System.out.println("Search tasks by typing: search");
        System.out.println("List all tasks by typing: tasklist");
    }
}
