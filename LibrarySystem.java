import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Inventory inventory = new Inventory();

            System.out.println("=== Basic Library System ===");

            boolean running = true;
            while (running) {
                printMenu();
                int choice;
                try {
                    System.out.print("Enter choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 6.");
                    scanner.nextLine();
                    continue;
                }

                switch (choice) {
                    case 1 -> addBook(scanner, inventory);
                    case 2 -> borrowBook(scanner, inventory);
                    case 3 -> returnBook(scanner, inventory);
                    case 4 -> searchByTitle(scanner, inventory);
                    case 5 -> printAll(inventory);
                    case 6 -> {
                        System.out.println("Exiting the program. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please select 1-6.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add Book        3. Return Book      5. Print All Books");
        System.out.println("2. Borrow Book     4. Search by Title  6. Exit");
    }

    private static void addBook(Scanner scanner, Inventory inventory) {
        try {
            System.out.print("Enter book ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (inventory.idExists(id)) {
                System.out.println("A book with that ID already exists. Cancelling add.");
                return;
            }

            System.out.print("Enter title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Enter author: ");
            String author = scanner.nextLine().trim();

            System.out.print("Enter ISBN: ");
            String isbn = scanner.nextLine().trim();

            System.out.print("Enter number of pages: ");
            int pages = Integer.parseInt(scanner.nextLine());

            inventory.addBook(new Book(id, title, author, isbn, pages));

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Book ID and pages must be whole numbers.");
            scanner.nextLine();
        }
    }

    private static void borrowBook(Scanner scanner, Inventory inventory) {
        try {
            System.out.print("Enter book ID to borrow: ");
            int id = Integer.parseInt(scanner.nextLine());
            inventory.borrowBook(id);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Book ID must be a whole number.");
            scanner.nextLine();
        }
    }

    private static void returnBook(Scanner scanner, Inventory inventory) {
        if (inventory.getLendingInventoryCount() == 0) {
            System.out.println("No books are currently checked out.");
            return;
        }
        try {
            System.out.print("Enter book ID to return: ");
            int id = Integer.parseInt(scanner.nextLine());
            inventory.returnBook(id);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Book ID must be a whole number.");
            scanner.nextLine();
        }
    }

    private static void searchByTitle(Scanner scanner, Inventory inventory) {
        System.out.print("Enter title or partial title to search: ");
        String query = scanner.nextLine().trim();
        List<Book> results = inventory.searchByTitle(query);
        if (results.isEmpty()) {
            System.out.println("No matching book found.");
        } else {
            System.out.println("Matching books (" + results.size() + " found):");
            for (Book b : results) {
                b.printBookInfo();
            }
        }
    }

    private static void printAll(Inventory inventory) {
        System.out.println("Books in main inventory (" + inventory.getMainInventoryCount() + "):");
        inventory.printAll();
    }
}