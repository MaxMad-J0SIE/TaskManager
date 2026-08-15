Project Name: Task manager

Task manager project used to organize someone's work. Uses json to save tasks which are accessed by the User through a
console UI with commands.

1. Tasks spec: id, title, description, dueDate(localDate), status(TODO, IN_PROGRESS, DONE), priority(LOW, MEDIUM, HIGH)
2. Tasks saved in json/mySQL Lite (tasks.json / mySQL Lite)
3. Validation & Custom Exceptions (no crashes - errors to logs)
4. Clean layered architecture (different classes do different things):
    - 1st layer - Task object creation and handling (getting and setting) - Task.java
    - 2nd layer - handles data reading and writing - TaskRepo.java
    - 3rd layer - brains making all the decisions - TaskService.java
    - 4th layer - handles user inputs - ConsoleUI.java