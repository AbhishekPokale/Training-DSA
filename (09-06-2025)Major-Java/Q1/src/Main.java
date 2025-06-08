import model.Book;
import model.Member;
import service.LibraryService;
import service.OverdueMonitor;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Scanner scanner = new Scanner(System.in);

        Thread monitorThread = new Thread(new OverdueMonitor(libraryService.getLendingRecords()));
        monitorThread.setDaemon(true);
        monitorThread.start();

        while (true) {
            System.out.println("\n=== Library Management Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Add Member");
            System.out.println("4. Remove Member");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. List Available Books");
            System.out.println("8. List Members with Borrowed Books");
            System.out.println("9. List Overdue Records");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Book ID: ");
                        String bId = scanner.nextLine();
                        System.out.print("Enter Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author: ");
                        String author = scanner.nextLine();
                        libraryService.addBook(new Book(bId, title, author));
                        System.out.println("Book added.");
                        break;
                    case 2:
                        System.out.print("Enter Book ID to remove: ");
                        libraryService.removeBook(scanner.nextLine());
                        System.out.println("Book removed.");
                        break;
                    case 3:
                        System.out.print("Enter Member ID: ");
                        String mId = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        libraryService.addMember(new Member(mId, name, email));
                        System.out.println("Member added.");
                        break;
                    case 4:
                        System.out.print("Enter Member ID to remove: ");
                        libraryService.removeMember(scanner.nextLine());
                        System.out.println("Member removed.");
                        break;
                    case 5:
                        System.out.print("Enter Book ID: ");
                        String issueBookId = scanner.nextLine();
                        System.out.print("Enter Member ID: ");
                        String issueMemberId = scanner.nextLine();
                        System.out.print("Enter Issue Date (YYYY-MM-DD): ");
                        LocalDate issueDate = LocalDate.parse(scanner.nextLine());
                        System.out.print("Enter Due Date (YYYY-MM-DD): ");
                        LocalDate dueDate = LocalDate.parse(scanner.nextLine());
                        libraryService.issueBook(issueBookId, issueMemberId, issueDate, dueDate);
                        System.out.println("Book issued.");
                        break;
                    case 6:
                        System.out.print("Enter Book ID to return: ");
                        String returnBookId = scanner.nextLine();
                        System.out.print("Enter Member ID: ");
                        String returnMemberId = scanner.nextLine();
                        libraryService.returnBook(returnBookId, LocalDate.parse(returnMemberId));
                        System.out.println("Book returned.");
                        break;
                    case 7:
                        System.out.println("Available Books:");
                        libraryService.getAvailableBooks()
                                .forEach(System.out::println);
                        break;
                    case 8:
                        System.out.println("Members with Borrowed Books:");
                        libraryService.getBorrowingMembers()
                                .forEach(System.out::println);
                        break;
                    case 9:
                        System.out.println("Overdue Records:");
                        libraryService.getOverdueRecords()
                                .forEach(System.out::println);
                        break;
                    case 0:
                        System.out.println("Exiting system...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
