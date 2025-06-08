package util;

import model.Employee;
import model.Task;
import service.TaskManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TaskMonitor extends Thread {
    private final TaskManager taskManager;

    public TaskMonitor(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("\n🔔 [TaskMonitor] Will check of any overdue task afterwards");

            Map<Employee, List<Task>> allTasks = taskManager.getAllTasks(); // We'll add this method next
            for (Map.Entry<Employee, List<Task>> entry : allTasks.entrySet()) {
                Employee emp = entry.getKey();
                List<Task> tasks = entry.getValue();

                tasks.stream()
                        .filter(task -> task.getDueDate().isBefore(LocalDate.now()) && task.getStatus() != Task.Status.COMPLETED)
                        .forEach(task -> System.out.println("⚠️ Overdue: " + task + " [Employee: " + emp.getName() + "]"));
            }

            try {
                Thread.sleep(60000); // Sleep for 1 minute
            } catch (InterruptedException e) {
                System.out.println("TaskMonitor interrupted. Stopping...");
                break;
            }
        }
    }
}
