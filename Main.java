// Library Management System - Java
// Ritik Kumar | BTech CSE (AIML), 3rd Semester
// 3rd Sem Topics: OOP (Class, Object, Encapsulation), ArrayList, File Handling, Exception Handling
// Run: javac Main.java  ->  java Main

import java.io.*;
import java.util.*;

class Book {
    int id;
    String title;
    String author;
    boolean isIssued;
    String issuedTo;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
        this.issuedTo = "";
    }

    void display() {
        String status = isIssued ? "Issued to " + issuedTo : "Available";
        System.out.println(id + " | " + title + " | " + author + " | " + status);
    }

    String toFileLine() {
        return id + "," + title + "," + author + "," + isIssued + "," + issuedTo;
    }

    static Book fromFileLine(String line) {
        String[] p = line.split(",", 5);
        Book b = new Book(Integer.parseInt(p[0]), p[1], p[2]);
        b.isIssued = Boolean.parseBoolean(p[3]);
        b.issuedTo = p.length > 4 ? p[4] : "";
        return b;
    }
}

public class Main {
    static ArrayList<Book> books = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE = "books.txt";

    public static void main(String[] args) {
        loadFromFile();
        System.out.println("=================================");
        System.out.println("  LIBRARY MANAGEMENT SYSTEM");
        System.out.println("  Ritik Kumar | CSE (AIML) 3rd Sem");
        System.out.println("=================================");

        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book by Title");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Exit");
            System.out.print("Choose (1-7): ");

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
            else if (choice == 4) issueBook();
            else if (choice == 5) returnBook();
            else if (choice == 6) deleteBook();
            else if (choice == 7) {
                saveToFile();
                System.out.println("Data save ho gaya. Bye Ritik!");
                break;
            } else System.out.println("Galat choice, 1-7 daalo.");
        }
    }

    static void addBook() {
        try {
            System.out.print("Book ID: ");
            int id = Integer.parseInt(sc.nextLine());
            for (Book b : books) {
                if (b.id == id) { System.out.println("Ye ID pehle se hai!"); return; }
            }
            System.out.print("Title: ");
            String title = sc.nextLine();
            System.out.print("Author: ");
            String author = sc.nextLine();
            books.add(new Book(id, title, author));
            saveToFile();
            System.out.println("Book add ho gayi!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewAll() {
        if (books.isEmpty()) { System.out.println("Library khaali hai. Pehle book add karo."); return; }
        System.out.println("\nID | Title | Author | Status");
        System.out.println("---------------------------------");
        for (Book b : books) b.display();
    }

    static void searchBook() {
        System.out.print("Title likho (ya uska part): ");
        String key = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Book b : books) {
            if (b.title.toLowerCase().contains(key)) { b.display(); found = true; }
        }
        if (!found) System.out.println("Book nahi mili!");
    }

    static void issueBook() {
        System.out.print("Book ID: ");
        int id = Integer.parseInt(sc.nextLine());
        for (Book b : books) {
            if (b.id == id) {
                if (b.isIssued) { System.out.println("Ye book pehle se issued hai."); return; }
                System.out.print("Kisko issue karni hai (Student Name): ");
                b.issuedTo = sc.nextLine();
                b.isIssued = true;
                saveToFile();
                System.out.println("Book issue ho gayi!");
                return;
            }
        }
        System.out.println("Book nahi mili!");
    }

    static void returnBook() {
        System.out.print("Book ID: ");
        int id = Integer.parseInt(sc.nextLine());
        for (Book b : books) {
            if (b.id == id) {
                if (!b.isIssued) { System.out.println("Ye book issued hi nahi thi."); return; }
                b.isIssued = false;
                b.issuedTo = "";
                saveToFile();
                System.out.println("Book return ho gayi!");
                return;
            }
        }
        System.out.println("Book nahi mili!");
    }

    static void deleteBook() {
        System.out.print("Book ID: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean removed = books.removeIf(b -> b.id == id);
        if (removed) { saveToFile(); System.out.println("Book delete ho gayi."); }
        else System.out.println("Book nahi mili!");
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
                if (!line.trim().isEmpty()) books.add(Book.fromFileLine(line));
            }
        } catch (Exception e) {
            System.out.println("Load me error: " + e.getMessage());
        }
    }
}
