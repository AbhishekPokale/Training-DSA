import model.Employee;
import model.Task;
import model.Task.Priority;
import model.Task.Status;
import service.TaskManager;
import util.TaskMonitor;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager taskManager = new TaskManager();
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, Employee> employeeAccounts = new HashMap<>();

    public static void main(String[] args) {
        initUsers();
        TaskMonitor monitor = new TaskMonitor(taskManager);
        monitor.setDaemon(true);
        monitor.start();

        System.out.println("Welcome to Employee Task Tracker System");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (!authenticate(username, password)) {
            System.out.println("Invalid credentials. Exiting.");
            return;
        }

        if (username.equals("admin")) {
            adminMenu();
        } else {
            employeeMenu(employeeAccounts.get(username));
        }
    }

    private static void initUsers() {
        users.put("admin", "admin123");
        users.put("emp1", "pass1");
        users.put("emp2", "pass2");

        Employee e1 = new Employee(1, "Abhishek", "Engineering");
        Employee e2 = new Employee(2, "Sneha", "Design");

        employeeAccounts.put("emp1", e1);
        employeeAccounts.put("emp2", e2);
    }

    private static boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Assign Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Search Tasks by Keyword");
            System.out.println("4. Tasks Due Tomorrow");
            System.out.println("5. Employees with > 3 Pending Tasks");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> assignTask();
                case 2 -> taskManager.printAllTasks();
                case 3 -> {
                    System.out.print("Enter keyword: ");
                    String keyword = scanner.nextLine();
                    taskManager.printSearchResults(keyword);
                }
                case 4 -> {
                    List<Task> due = taskManager.getTasksDueTomorrow();
                    due.forEach(System.out::println);
                }
                case 5 -> {
                    List<Employee> emps = taskManager.getEmployeesWithMoreThanThreePendingTasks();
                    emps.forEach(System.out::println);
                }
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void assignTask() {
        System.out.println("Select Employee:");
        List<Employee> emps = new ArrayList<>(employeeAccounts.values());
        for (int i = 0; i < emps.size(); i++) {
            System.out.println((i + 1) + ". " + emps.get(i).getName());
        }

        int empChoice = scanner.nextInt() - 1;
        scanner.nextLine();
        if (empChoice < 0 || empChoice >= emps.size()) {
            System.out.println("Invalid employee.");
            return;
        }

        Employee emp = emps.get(empChoice);
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter Priority (HIGH, MEDIUM, LOW): ");
        Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Due Date (YYYY-MM-DD): ");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

        Task task = new Task(id, desc, Status.PENDING, dueDate, priority);
        taskManager.assignTask(emp, task);
        System.out.println("Task Assigned.");
    }

    private static void employeeMenu(Employee employee) {
        while (true) {
            System.out.println("\n--- Employee Menu (" + employee.getName() + ") ---");
            System.out.println("1. View My Tasks");
            System.out.println("2. Sort by Due Date");
            System.out.println("3. Sort by Priority");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    List<Task> tasks = taskManager.getTasksForEmployee(employee);
                    tasks.forEach(System.out::println);
                }
                case 2 -> {
                    List<Task> sortedDue = taskManager.getTasksSortedByDueDate(employee);
                    sortedDue.forEach(System.out::println);
                }
                case 3 -> {
                    List<Task> sortedPriority = taskManager.getTasksSortedByPriority(employee);
                    sortedPriority.forEach(System.out::println);
                }
                case 4 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
