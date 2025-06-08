package model;

import java.time.LocalDate;

public class Task implements Comparable<Task> {
    public enum Status { PENDING, IN_PROGRESS, COMPLETED }
    public enum Priority { LOW, MEDIUM, HIGH }

    private int id;
    private String description;
    private Status status;
    private LocalDate dueDate;
    private Priority priority;

    public Task(int id, String description, Status status, LocalDate dueDate, Priority priority) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }
    public Priority getPriority() { return priority; }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int compareTo(Task other) {
        return this.priority.compareTo(other.priority); // HIGH < LOW by enum order
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", desc='" + description + "', status=" + status +
                ", dueDate=" + dueDate + ", priority=" + priority + "}";
    }
}
