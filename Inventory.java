import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Book> mainInventory;
    private final List<Book> lendingInventory;

    public Inventory() {
        this.mainInventory = new ArrayList<>();
        this.lendingInventory = new ArrayList<>();
    }

    public void addBook(Book book) {
        mainInventory.add(book);
        System.out.println("Book added to the library.");
    }

    public boolean borrowBook(int id) {
        Book book = findInList(mainInventory, id);
        if (book != null) {
            lendingInventory.add(book);
            mainInventory.remove(book);
            System.out.println("Book successfully borrowed.");
            return true;
        }
        System.out.println("Book not found or already borrowed.");
        return false;
    }

    public boolean returnBook(int id) {
        Book book = findInList(lendingInventory, id);
        if (book != null) {
            mainInventory.add(book);
            lendingInventory.remove(book);
            System.out.println("Book successfully returned.");
            return true;
        }
        System.out.println("Error: This book is not in the lending list.");
        return false;
    }

    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : mainInventory) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public void printAll() {
        if (mainInventory.isEmpty()) {
            System.out.println("The library inventory is currently empty.");
        } else {
            for (Book book : mainInventory) {
                book.printBookInfo();
            }
        }
    }

    public int getMainInventoryCount() {
        return mainInventory.size();
    }

    public int getLendingInventoryCount() {
        return lendingInventory.size();
    }

    private Book findInList(List<Book> list, int id) {
        for (Book b : list) {
            if (b.getId() == id)
                return b;
        }
        return null;
    }

    public boolean idExists(int id) {
        return findInList(mainInventory, id) != null || findInList(lendingInventory, id) != null;
    }
}