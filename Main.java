// ============================================================
//  Library Management System - ADVANCED Version (Java)
//  Ritik Kumar | BTech CSE (AIML), 3rd Semester
//  GitHub: ritik4343
//
//  NEW in v2.0:
//  1. Category + Price per book
//  2. Issue Date + Due Date (14 days) using LocalDate
//  3. Late Fine: Rs. 10/day after due date
//  4. Search by ID / Title / Author
//  5. Sort by Title / ID
//  6. Dashboard Stats: Total / Available / Issued / Fine Collected
//  7. Better validation + Exception Handling
//
//  3rd Sem Topics: OOP, Encapsulation, ArrayList, Sorting,
//  File Handling, LocalDate, Exception Handling
//
//  Run: javac Main.java  ->  java Main
// ============================================================

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Book {
    int id;
    String title;
    String author;
    String category;
    double price;
    boolean isIssued;
    String issuedTo;
    String issueDate; // yyyy-MM-dd, empty if not issued
    int totalFinePaid;

    Book(int id, String title, String author, String category, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.isIssued = false;
        this.issuedTo = "";
        this.issueDate = "";
        this.totalFinePaid = 0;
    }

    LocalDate getDueDate() {
        if (issueDate == null || issueDate.isEmpty()) return null;
        return LocalDate.parse(issueDate).plusDays(14);
    }

    // Late fine: due date ke baad Rs.10/day
    int calculateFine() {
        if (!isIssued || issueDate == null || issueDate.isEmpty()) return 0;
        LocalDate due = getDueDate();
        long lateDays = ChronoUnit.DAYS.between(due, LocalDate.now());
        if (lateDays <= 0) return 0;
        return (int) lateDays * 10;
    }

    void display() {
        String status = isIssued
            ? "Issued to " + issuedTo + " | Due: " + getDueDate() + " | Fine now: Rs." + calculateFine()
            : "Available";
        System.out.println(id + " | " + title + " | " + author + " | " + category
            + " | Rs." + price + " | " + status);
    }

    String toFileLine() {
        // commas in title/author break the file, so replace them
        String t = title.replace(",", " ");
        String a = author.replace(",", " ");
        String c = category.replace(",", " ");
        String to = issuedTo.replace(",", " ");
        return id + "," + t + "," + a + "," + c + "," + price + "," + isIssued + "," + to + "," + issueDate + "," + totalFinePaid;
    }

    static Book fromFileLine(String line) {
        String[] p = line.split(",", -1);
        Book b = new Book(Integer.parseInt(p[0]), p[1], p[2], p[3], Double.parseDouble(p[4]));
        b.isIssued = Boolean.parseBoolean(p[5]);
        b.issuedTo = p.length > 6 ? p[6] : "";
        b.issueDate = p.length > 7 ? p[7] : "";
        b.totalFinePaid = p.length > 8 && !p[8].isEmpty() ? Integer.parseInt(p[8]) : 0;
        return b;
    }
}

public class Main {
    static ArrayList<Book> books = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE = "books.txt";
    static final int FINE_PER_DAY = 10;
    static final int ISSUE_DAYS = 14;

    public static void main(String[] args) {
        loadFromFile();
        System.out.println("==============================================");
        System.out.println("  LIBRARY MANAGEMENT SYSTEM  v2.0 ADVANCED");
        System.out.println("  Ritik Kumar | CSE (AIML) 3rd Sem");
        System.out.println("  Issue: 14 days | Late Fine: Rs.10/day");
        System.out.println("==============================================");

        while (true) {
            showDashboard();
            System.out.println("\n1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book (ID / Title / Author)");
            System.out.println("4. Sort Books by Title");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book (Fine auto-calculate)");
            System.out.println("7. Delete Book");
            System.out.println("8. Exit");
            System.out.print("Choose (1-8): ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Sahi number daalo!");
                continue;
            }

            if (choice == 1) addBook();
            else if (choice == 2) viewAll();
            else if (choice == 3) searchBook();
            else if (choice == 4) sortByTitle();
            else if (choice == 5) issueBook();
            else if (choice == 6) returnBook();
            else if (choice == 7) deleteBook();
            else if (choice == 8) {
                saveToFile();
                System.out.println("Data save ho gaya. Bye Ritik!");
                break;
            } else System.out.println("Galat choice, 1-8 daalo.");
        }
    }

    static void showDashboard() {
        int total = books.size();
        long issued = books.stream().filter(b -> b.isIssued).count();
        long available = total - issued;
        int fineCollected = books.stream().mapToInt(b -> b.totalFinePaid).sum();
        System.out.println("\n--- Dashboard ---  Total: " + total
            + " | Available: " + available
            + " | Issued: " + issued
            + " | Fine Collected: Rs." + fineCollected);
    }

    static Book findById(int id) {
        for (Book b : books) if (b.id == id) return b;
        return null;
    }

    static void addBook() {
        try {
            System.out.print("Book ID: ");
            int id = Integer.parseInt(sc.nextLine());
            if (findById(id) != null) { System.out.println("Ye ID pehle se hai!"); return; }
            System.out.print("Title: ");
            String title = sc.nextLine().trim();
            if (title.isEmpty()) { System.out.println("Title khaali nahi ho sakta!"); return; }
            System.out.print("Author: ");
            String author = sc.nextLine().trim();
            System.out.print("Category (e.g. CSE / AIML / Novel): ");
            String category = sc.nextLine().trim();
            System.out.print("Price (Rs.): ");
            double price = Double.parseDouble(sc.nextLine());
            books.add(new Book(id, title, author, category, price));
            saveToFile();
            System.out.println("Book add ho gayi!");
        } catch (NumberFormatException e) {
            System.out.println("Number galat daala! ID aur Price me sirf number daalo.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewAll() {
        if (books.isEmpty()) { System.out.println("Library khaali hai. Pehle book add karo."); return; }
        System.out.println("\nID | Title | Author | Category | Price | Status");
        System.out.println("------------------------------------------------------------");
        for (Book b : books) b.display();
    }

    static void searchBook() {
        System.out.println("1. By ID  2. By Title  3. By Author");
        System.out.print("Choose: ");
        int type = Integer.parseInt(sc.nextLine());
        boolean found = false;
        if (type == 1) {
            System.out.print("ID: ");
            Book b = findById(Integer.parseInt(sc.nextLine()));
            if (b != null) { b.display(); found = true; }
        } else if (type == 2 || type == 3) {
            System.out.print("Keyword: ");
            String key = sc.nextLine().toLowerCase();
            for (Book b : books) {
                String field = (type == 2 ? b.title : b.author).toLowerCase();
                if (field.contains(key)) { b.display(); found = true; }
            }
        } else {
            System.out.println("Galat choice!");
            return;
        }
        if (!found) System.out.println("Book nahi mili!");
    }

    static void sortByTitle() {
        if (books.isEmpty()) { System.out.println("Koi book nahi hai."); return; }
        books.sort((a, b) -> a.title.compareToIgnoreCase(b.title));
        saveToFile();
        System.out.println("Title ke hisaab se sort ho gayi!");
        viewAll();
    }

    static void issueBook() {
        try {
            System.out.print("Book ID: ");
            Book b = findById(Integer.parseInt(sc.nextLine()));
            if (b == null) { System.out.println("Book nahi mili!"); return; }
            if (b.isIssued) { System.out.println("Ye book pehle se issued hai."); return; }
            System.out.print("Kisko issue karni hai (Student Name): ");
            b.issuedTo = sc.nextLine();
            b.isIssued = true;
            b.issueDate = LocalDate.now().toString();
            saveToFile();
            System.out.println("Book issue ho gayi! Due Date: " + b.getDueDate()
                + " (14 din, uske baad Rs.10/day fine)");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void returnBook() {
        try {
            System.out.print("Book ID: ");
            Book b = findById(Integer.parseInt(sc.nextLine()));
            if (b == null) { System.out.println("Book nahi mili!"); return; }
            if (!b.isIssued) { System.out.println("Ye book issued hi nahi thi."); return; }
            int fine = b.calculateFine();
            if (fine > 0) {
                System.out.println("LATE RETURN! Fine: Rs." + fine + " (Rs.10/day)");
                b.totalFinePaid += fine;
            } else {
                System.out.println("On time return. Koi fine nahi!");
            }
            b.isIssued = false;
            b.issuedTo = "";
            b.issueDate = "";
            saveToFile();
            System.out.println("Book return ho gayi!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteBook() {
        try {
            System.out.print("Book ID: ");
            int id = Integer.parseInt(sc.nextLine());
            Book b = findById(id);
            if (b != null && b.isIssued) {
                System.out.println("Ye book issued hai, pehle return karwao, phir delete karo.");
                return;
            }
            boolean removed = books.removeIf(x -> x.id == id);
            if (removed) { saveToFile(); System.out.println("Book delete ho gayi."); }
            else System.out.println("Book nahi mili!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Book b : books) pw.println(b.toFileLine());
        } catch (IOException e) {
            System.out.println("Save me error: " + e.getMessage());
        }
    }

    static void loadFromFile() {
        File f = new File(FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try { books.add(Book.fromFileLine(line)); }
                catch (Exception e) {
                    // Purana v1 format (5 fields) ho to skip nahi, convert karo
                    try {
                        String[] p = line.split(",", 5);
                        Book b = new Book(Integer.parseInt(p[0]), p[1], p[2], "General", 0);
                        b.isIssued = Boolean.parseBoolean(p[3]);
                        b.issuedTo = p.length > 4 ? p[4] : "";
                        books.add(b);
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            System.out.println("Load me error: " + e.getMessage());
        }
    }
}
