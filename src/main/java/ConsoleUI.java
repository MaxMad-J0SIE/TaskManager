import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleUI {
//    console controls
    private final TaskService taskService;
    private final Scanner scanner;
    private boolean running = true;

    public ConsoleUI(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    public void ScanSystemIn() throws IOException, SQLException {
        StartTerminal();
        System.out.println("help to list commands");
        while (running) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
//            print the error and keep going instead of crashing on a typo
            try {
                CommandHandler(scanner.nextLine());
            } catch (IllegalArgumentException | NoSuchElementException e) {
                System.out.println("Error: " + e.getMessage());
            }
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

//    splits the line into the command word and its argument, e.g. "done 3" -> "done" + "3"
    public void CommandHandler(String line) throws SQLException {
        String[] parts = line.trim().split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
            case "" -> { }
            case "add" -> AddCommandHandler();
            case "delete" -> DeleteCommandHandler(argument);
            case "update" -> UpdateCommandHandler(argument);
            case "done" -> DoneCommandHandler(argument);
            case "search" -> SearchCommandHandler(argument);
            case "list", "tasklist" -> ReturnTasksCommandHandler();
            case "help" -> HelpCommandHandler();
            case "exit", "quit" -> ExitCommandHandler();
            default -> System.out.println("Unknown command: " + command + " (type help to list commands)");
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
        System.out.println("Task added");
    }

    public void DeleteCommandHandler(String argument) throws SQLException {
        Integer taskID = argument.isEmpty() ? SelectTask("delete") : ParseId(argument);
        if (taskID == null) {
            return;
        }
        Task task = taskService.SearchById(taskID);
        PrintTask(task);
        System.out.print("Delete this task? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            taskService.DeleteTask(taskID);
            System.out.println("Task deleted");
        } else {
            System.out.println("Delete cancelled");
        }
    }

    public void UpdateCommandHandler(String argument) throws SQLException {
        Integer taskID = argument.isEmpty() ? SelectTask("update") : ParseId(argument);
        if (taskID == null) {
            return;
        }
        Task task = taskService.SearchById(taskID);

        System.out.println("Input changes, if not leave empty");

//        no hasNext() here, it blocks on empty lines so Enter couldn't skip a field
        System.out.print("Title [" + task.getTitle() + "] <- ");
        String title = scanner.nextLine();
        System.out.print("Description [" + task.getDescription() + "] <- ");
        String description = scanner.nextLine();
        System.out.print("Deadline (YYYY-MM-DD) [" + task.getDueDate() + "] <- ");
        String dueDate = scanner.nextLine();
        System.out.print("Status (TODO, IN_PROGRESS, DONE) [" + task.getStatus() + "] <- ");
        String status = scanner.nextLine();
        System.out.print("Priority (LOW, MEDIUM, HIGH) [" + task.getPriority() + "] <- ");
        String priority = scanner.nextLine();

        taskService.UpdateTask(taskID, title, description, dueDate, status, priority);
        System.out.println("Task updated");
    }

//    shortcut: "done 3" marks task 3 as DONE without going through all the update prompts
    public void DoneCommandHandler(String argument) throws SQLException {
        Integer taskID = argument.isEmpty() ? SelectTask("mark as done") : ParseId(argument);
        if (taskID == null) {
            return;
        }
        taskService.UpdateTask(taskID, "", "", "", Status.DONE.name(), "");
        System.out.println("Task " + taskID + " marked as DONE");
    }

    public void SearchCommandHandler(String argument) {
        if (argument.isEmpty()) {
            PrintSearchHelp();
            System.out.print("Search: ");
            argument = scanner.nextLine().trim();
        }
        PrintTasks(Search(argument));
    }

//    lets the user search first and then pick an id, so they don't have to know it up front
//    returns null when nothing was found
    private Integer SelectTask(String action) {
        System.out.println("Find the task to " + action + " (leave empty to list all)");
        PrintSearchHelp();
        System.out.print("Search: ");
        List<Task> matches = Search(scanner.nextLine().trim());
        if (matches.isEmpty()) {
            System.out.println("No tasks found");
            return null;
        }
        PrintTasks(matches);
        System.out.print("Input task ID to " + action + ": ");
        return ParseId(scanner.nextLine());
    }

//    first word picks the search type, anything else is treated as a keyword
//    e.g. "status done", "priority high", "due 2026-12-01", "overdue", "id 3", "milk"
    private List<Task> Search(String query) {
        String[] parts = query.split("\\s+", 2);
        String type = parts[0].toLowerCase();
        String value = parts.length > 1 ? parts[1].trim() : "";

        switch (type) {
            case "", "all" -> {
                return taskService.TaskList();
            }
            case "overdue" -> {
                return taskService.SearchOverdue();
            }
            case "id" -> {
                return List.of(taskService.SearchById(ParseId(value)));
            }
            case "title" -> {
                return taskService.SearchByTitle(value);
            }
            case "status" -> {
                return taskService.SearchByStatus(value);
            }
            case "priority" -> {
                return taskService.SearchByPriority(value);
            }
            case "due" -> {
                return taskService.SearchByDueDate(value);
            }
            default -> {
                return taskService.SearchByKeyword(query);
            }
        }
    }

    private void PrintSearchHelp() {
        System.out.println("  <keyword>                   title or description contains keyword");
        System.out.println("  title <exact title>");
        System.out.println("  status TODO|IN_PROGRESS|DONE");
        System.out.println("  priority LOW|MEDIUM|HIGH");
        System.out.println("  due YYYY-MM-DD");
        System.out.println("  overdue");
        System.out.println("  id <number>");
        System.out.println("  all");
    }

    private int ParseId(String input) {
        String trimmed = input.trim();
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Task ID must be a number, got: " + trimmed);
        }
    }

    public void ReturnTasksCommandHandler() {
        System.out.println("Task List");
        PrintTasks(taskService.TaskList());
    }

    private void PrintTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }
        for (Task task : tasks) {
            PrintTask(task);
        }
    }

    private void PrintTask(Task task) {
        System.out.println(task.getId() + " " + task.getTitle());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Deadline: " + task.getDueDate());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Priority: " + task.getPriority());
        System.out.println();
    }

    public void ExitCommandHandler() {
        System.out.println("Bye");
        running = false;
    }

    public void HelpCommandHandler() {
        System.out.println("Add task by typing: add");
        System.out.println("Delete task by typing: delete (or delete <id>)");
        System.out.println("Update task by typing: update (or update <id>)");
        System.out.println("Mark task as done by typing: done (or done <id>)");
        System.out.println("Search tasks by typing: search (or search <query>, e.g. search status todo)");
        System.out.println("List all tasks by typing: list");
        System.out.println("Exit by typing: exit or quit");
    }
}
