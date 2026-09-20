import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class TaskService {
//    program brains
//    in charge of checking if tasks are correctly labeled
//    error/exception handling
//    TODO when creating a task check if the date is in the past (exception)

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
        LocalDate dueDate1 = LocalDate.parse(dueDate);
        Status status = Status.TODO;
        Priority priority1 = Priority.valueOf(priority);
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

    public List<Task> SearchByDueDate(LocalDate dueDate) {
        return tasks.stream().filter(task -> task.getDueDate().isEqual(dueDate)).toList();
    }

    public List<Task> SearchOverdue() {
        LocalDate today = LocalDate.now();
        return tasks.stream().filter(task -> task.getDueDate().isBefore(today)).toList();
    }

    public List<Task> SearchByStatus(String status) {
        return tasks.stream().filter(task -> task.getStatus() == Status.valueOf(status)).toList();
    }

    public List<Task> SearchByPriority(String priority) {
        return tasks.stream().filter(task -> task.getPriority() == Priority.valueOf(priority)).toList();
    }

//    Task Delete
    public void DeleteTask(Integer id) throws SQLException {
        Task task = SearchById(id);
        repository.DeleteTaskDB(id);
        tasks.remove(task);
    }

//    Task Update
    public void UpdateTitle(Integer id, String newTitle) {
        Task task = SearchById(id);
        task.setTitle(newTitle);
    }

    public void UpdateDescription(Integer id, String newDescription) {
        Task task = SearchById(id);
        task.setDescription(newDescription);
    }

    public void UpdateDeadline(Integer id, LocalDate newDeadline) {
        Task task = SearchById(id);
        task.setDueDate(newDeadline);
    }

    public void UpdateStatus(Integer id, Status newStatus) {
        Task task = SearchById(id);
        task.setStatus(newStatus);
    }

    public void UpdatePriority(Integer id, Priority newPriority) {
        Task task = SearchById(id);
        task.setPriority(newPriority);
    }

    public List<Task> TaskList() {
        return this.tasks;
    }

}
