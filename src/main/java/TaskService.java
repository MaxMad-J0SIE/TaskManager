import java.util.List;

public class TaskService {
//    program brains
//    in charge of checking if tasks are correctly labeled
//    error/exception handling
//    TODO when creating a task check if the date is in the past (exception)

    private final TaskRepo repository;
    private final List<Task> tasks;

    public TaskService(TaskRepo repository) {
        this.repository = repository;
        this.tasks = repository.ReadCSV();
    }

}
