import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Status status;
    private Priority priority;

    public Task(int id, String title, LocalDate dueDate, Priority priority) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.status = Status.TODO;
        this.priority = priority;
    }

//    getters
    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public Priority getPriority() {
        return this.priority;
    }

//    setters
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID has to be a positive integer");
        }
        this.id = id;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title field cannot be empty");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        if (description == null) {
            this.description = "";
        } else {
            this.description = description.trim();
        }
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        this.dueDate = dueDate;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        this.priority = priority;
    }
}