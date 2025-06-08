package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import exception.TaskNotFoundException;

public class TaskRepository<T> {
    private List<T> taskList = new ArrayList<>();

    public void add(T task) {
        taskList.add(task);
    }

    public boolean remove(T task) {
        return taskList.remove(task);
    }

    public Optional<T> find(Predicate<T> predicate) {
        return taskList.stream().filter(predicate).findFirst();
    }

    public void update(T task, Predicate<T> predicate) throws TaskNotFoundException {
        Optional<T> existing = find(predicate);
        if (existing.isPresent()) {
            taskList.remove(existing.get());
            taskList.add(task);
        } else {
            throw new TaskNotFoundException("Task not found to update.");
        }
    }

    public List<T> getAll() {
        return taskList;
    }
}
