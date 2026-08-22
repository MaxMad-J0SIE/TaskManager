import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public void CreateTask() {

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
    public void DeleteTask() {

    }

//    Task Update
    public void UpdateTitle() {

    }

    public void UpdateDescription() {

    }

    public void UpdateDeadline() {

    }

    public void UpdateStatus() {

    }

    public void UpdatePriority() {

    }


}
