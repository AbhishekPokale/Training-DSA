package service;

import model.Employee;
import model.Task;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private Map<Employee, List<Task>> taskMap = new HashMap<>();

    public void assignTask(Employee employee, Task task) {
        taskMap.computeIfAbsent(employee, k -> new ArrayList<>()).add(task);
    }

    public List<Task> getTasksForEmployee(Employee employee) {
        return taskMap.getOrDefault(employee, new ArrayList<>());
    }

    public void printAllTasks() {
        for (Map.Entry<Employee, List<Task>> entry : taskMap.entrySet()) {
            System.out.println("Employee: " + entry.getKey());
            for (Task task : entry.getValue()) {
                System.out.println("  " + task);
            }
        }
    }

    public List<Task> searchTasksByKeyword(String keyword) {
        return taskMap.values().stream()
                .flatMap(List::stream)
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByPriority(Employee employee) {
        return getTasksForEmployee(employee).stream()
                .sorted(Comparator.comparing(Task::getPriority)) // Enum order
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByDueDate(Employee employee) {
        return getTasksForEmployee(employee).stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    public void printSearchResults(String keyword) {
        List<Task> result = searchTasksByKeyword(keyword);
        if (result.isEmpty()) {
            System.out.println("No tasks found containing keyword: " + keyword);
        } else {
            System.out.println("Tasks matching keyword '" + keyword + "':");
            result.forEach(System.out::println);
        }
    }

    public Map<Employee, List<Task>> getAllTasks() {
        return taskMap;
    }

    public List<Task> getTasksDueTomorrow() {
        return taskMap.values().stream()
                .flatMap(List::stream)
                .filter(task -> task.getDueDate().equals(LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesWithMoreThanThreePendingTasks() {
        return taskMap.entrySet().stream()
                .filter(entry ->
                        entry.getValue().stream()
                                .filter(task -> task.getStatus() == Task.Status.PENDING)
                                .count() > 3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
