import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

public class TaskService {
//    program brains
//    in charge of checking if tasks are correctly labeled
//    error/exception handling

    private final TaskRepo repository;
    private final List<Task> tasks;

    public TaskService (TaskRepo repository) throws SQLException {
        this.repository = repository;
        this.tasks = repository.ReadDB();
    }

//    Task Create
    public void CreateTask(String title, String description, String dueDate, String priority ) throws SQLException {
//        create a task without id and pass it to DB
//        get id from db
//        validate before inserting so a bad input never reaches the DB
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        title = title.trim();
        description = description == null ? "" : description.trim();
        LocalDate dueDate1 = ParseDate(dueDate);
        Status status = Status.TODO;
        Priority priority1 = ParsePriority(priority);
        Integer id = repository.NewTaskSaveDB(title, description, dueDate1, status, priority1);
        Task task = new Task(id, title, dueDate1, priority1);
        task.setDescription(description);
        tasks.add(task);
    }

//    Search methods (id - Task, title - List, keyword(desc) - List, duedate(exact/overdue) - List, status - List, priority - List)
    public Task SearchById(int id) {
        for (Task task : tasks) {
            if (id == task.getId()) {
                return task;
            }
        }
        throw new NoSuchElementException("No task found with id " + id);
    }

    public List<Task> SearchByTitle(String title) {
        return tasks.stream().filter(task -> task.getTitle().equalsIgnoreCase(title)).toList();
    }

    public List<Task> SearchByKeyword(String keyword) {
        String needle = keyword.toLowerCase();
        return tasks.stream().filter(task -> task.getTitle().toLowerCase().contains(needle) || task.getDescription().toLowerCase().contains(needle)).toList();
    }

    public List<Task> SearchByDueDate(String dueDate) {
//        past dates are allowed here, you can search for old deadlines
        LocalDate wanted = ParseDateFormat(dueDate);
        return tasks.stream().filter(task -> task.getDueDate().isEqual(wanted)).toList();
    }

    public List<Task> SearchOverdue() {
        LocalDate today = LocalDate.now();
        return tasks.stream().filter(task -> task.getDueDate().isBefore(today)).toList();
    }

    public List<Task> SearchByStatus(String status) {
        Status wanted = ParseStatus(status);
        return tasks.stream().filter(task -> task.getStatus() == wanted).toList();
    }

    public List<Task> SearchByPriority(String priority) {
        Priority wanted = ParsePriority(priority);
        return tasks.stream().filter(task -> task.getPriority() == wanted).toList();
    }

//    Task Delete
    public void DeleteTask(Integer id) throws SQLException {
        Task task = SearchById(id);
        repository.DeleteTaskDB(id);
        tasks.remove(task);
    }

//    Task Update
//    blank input keeps the current value
    public void UpdateTask(int id, String title, String description, String dueDate, String status, String priority) throws SQLException {
        Task task = SearchById(id);
//        parse everything first so bad input doesn't leave the task half updated
        LocalDate newDueDate = dueDate.isBlank() ? task.getDueDate() : ParseDate(dueDate);
        Status newStatus = status.isBlank() ? task.getStatus() : ParseStatus(status);
        Priority newPriority = priority.isBlank() ? task.getPriority() : ParsePriority(priority);

        if (!title.isBlank()) {
            task.setTitle(title.trim());
        }
        if (!description.isBlank()) {
            task.setDescription(description);
        }
        task.setDueDate(newDueDate);
        task.setStatus(newStatus);
        task.setPriority(newPriority);

        repository.UpdateTaskDB(task);
    }

    public List<Task> TaskList() {
        return this.tasks;
    }

//    Input parsing/validation (shared by create, update and search)
    private LocalDate ParseDate(String input) {
        LocalDate date = ParseDateFormat(input);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline cannot be in the past");
        }
        return date;
    }

    private LocalDate ParseDateFormat(String input) {
        try {
            return LocalDate.parse(input.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must be in format YYYY-MM-DD");
        }
    }

    private Status ParseStatus(String input) {
        try {
            return Status.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status must be TODO, IN_PROGRESS or DONE");
        }
    }

    private Priority ParsePriority(String input) {
        try {
            return Priority.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Priority must be LOW, MEDIUM or HIGH");
        }
    }

}
