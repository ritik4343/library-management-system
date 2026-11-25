// ============================================================
//  Library Management System - ADVANCED Version (C++)
//  Ritik Kumar | BTech CSE (AIML), 3rd Semester
//  GitHub: ritik4343
//
//  NEW in v2.0:
//  1. Category + Price per book
//  2. Issue period 14 days, Late Fine Rs.10/day
//     (Return par poochha jata hai: kitne din me return kar rahe ho?)
//  3. Search by ID / Title / Author
//  4. Sort by Title
//  5. Dashboard Stats: Total / Available / Issued / Fine Collected
//  6. Issued book delete nahi hogi - pehle return karni hogi
//
//  3rd Sem Topics: OOP, vector, Sorting (std::sort), File Handling
//
//  Run: g++ main.cpp -o library  ->  ./library
//  Windows: g++ main.cpp -o library.exe -> library.exe
// ============================================================

#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <algorithm>
using namespace std;

const int ISSUE_DAYS = 14;
const int FINE_PER_DAY = 10;

class Book {
public:
    int id;
    string title;
    string author;
    string category;
    double price;
    bool isIssued;
    string issuedTo;
    int daysKept;      // return par user batata hai kitne din rakhi
    int totalFinePaid;

    Book(int id, string title, string author, string category, double price) {
        this->id = id;
        this->title = title;
        this->author = author;
        this->category = category;
        this->price = price;
        this->isIssued = false;
        this->issuedTo = "";
        this->daysKept = 0;
        this->totalFinePaid = 0;
    }

    int calculateFine() {
        if (!isIssued) return 0;
        int late = daysKept - ISSUE_DAYS;
        if (late <= 0) return 0;
        return late * FINE_PER_DAY;
    }

    void display() {
        string status = isIssued ? "Issued to " + issuedTo + " | Days kept: " + to_string(daysKept)
                                 : "Available";
        cout << id << " | " << title << " | " << author << " | " << category
             << " | Rs." << price << " | " << status << endl;
    }
};

vector<Book> books;
const string FILE_NAME = "books.txt";

string lowerStr(string s) {
    transform(s.begin(), s.end(), s.begin(), ::tolower);
    return s;
}

Book* findById(int id) {
    for (auto &b : books) if (b.id == id) return &b;
    return nullptr;
}

void saveToFile() {
    ofstream out(FILE_NAME);
    for (auto &b : books) {
        out << b.id << "," << b.title << "," << b.author << "," << b.category << ","
            << b.price << "," << b.isIssued << "," << b.issuedTo << ","
            << b.daysKept << "," << b.totalFinePaid << "\n";
    }
    out.close();
}

void loadFromFile() {
    ifstream in(FILE_NAME);
    if (!in) return;
    string line;
    while (getline(in, line)) {
        if (line.empty()) continue;
        // Split by comma into 9 parts
        vector<string> p;
        size_t start = 0, pos;
        while ((pos = line.find(',', start)) != string::npos) {
            p.push_back(line.substr(start, pos - start));
            start = pos + 1;
        }
        p.push_back(line.substr(start));
        if (p.size() < 5) continue;
        try {
            Book b(stoi(p[0]), p[1], p[2], p.size() > 3 ? p[3] : "General",
                   p.size() > 4 ? stod(p[4]) : 0);
            if (p.size() > 5) b.isIssued = (p[5] == "1");
            if (p.size() > 6) b.issuedTo = p[6];
            if (p.size() > 7) b.daysKept = stoi(p[7]);
            if (p.size() > 8) b.totalFinePaid = stoi(p[8]);
            books.push_back(b);
        } catch (...) { /* purani line ho to skip */ }
    }
    in.close();
}

void showDashboard() {
    int total = books.size();
    int issued = 0, fine = 0;
    for (auto &b : books) {
        if (b.isIssued) issued++;
        fine += b.totalFinePaid;
    }
    cout << "\n--- Dashboard ---  Total: " << total
         << " | Available: " << (total - issued)
         << " | Issued: " << issued
         << " | Fine Collected: Rs." << fine << endl;
}

void addBook() {
    int id;
    string title, author, category;
    double price;
    cout << "Book ID: "; cin >> id;
    if (findById(id)) { cout << "Ye ID pehle se hai!\n"; return; }
    cin.ignore();
    cout << "Title: "; getline(cin, title);
    if (title.empty()) { cout << "Title khaali nahi ho sakta!\n"; return; }
    cout << "Author: "; getline(cin, author);
    cout << "Category (e.g. CSE / AIML / Novel): "; getline(cin, category);
    cout << "Price (Rs.): "; cin >> price;
    books.push_back(Book(id, title, author, category, price));
    saveToFile();
    cout << "Book add ho gayi!\n";
}

void viewAll() {
    if (books.empty()) { cout << "Library khaali hai. Pehle book add karo.\n"; return; }
    cout << "\nID | Title | Author | Category | Price | Status\n";
    cout << "------------------------------------------------------------\n";
    for (auto &b : books) b.display();
}

void searchBook() {
    cout << "1. By ID  2. By Title  3. By Author\nChoose: ";
    int type; cin >> type;
    bool found = false;
    if (type == 1) {
        int id; cout << "ID: "; cin >> id;
        Book* b = findById(id);
        if (b) { b->display(); found = true; }
    } else if (type == 2 || type == 3) {
        string key;
        cout << "Keyword: "; cin.ignore(); getline(cin, key);
        string kl = lowerStr(key);
        for (auto &b : books) {
            string field = lowerStr(type == 2 ? b.title : b.author);
            if (field.find(kl) != string::npos) { b.display(); found = true; }
        }
    } else {
        cout << "Galat choice!\n"; return;
    }
    if (!found) cout << "Book nahi mili!\n";
}

void sortByTitle() {
    if (books.empty()) { cout << "Koi book nahi hai.\n"; return; }
    sort(books.begin(), books.end(), [](const Book& a, const Book& b) {
        return lowerStr(a.title) < lowerStr(b.title);
    });
    saveToFile();
    cout << "Title ke hisaab se sort ho gayi!\n";
    viewAll();
}

void issueBook() {
    int id;
    cout << "Book ID: "; cin >> id;
    Book* b = findById(id);
    if (!b) { cout << "Book nahi mili!\n"; return; }
    if (b->isIssued) { cout << "Ye book pehle se issued hai.\n"; return; }
    cout << "Kisko issue karni hai (Student Name): ";
    cin.ignore();
    getline(cin, b->issuedTo);
    b->isIssued = true;
    b->daysKept = 0;
    saveToFile();
    cout << "Book issue ho gayi! Return " << ISSUE_DAYS
         << " din me karo, uske baad Rs." << FINE_PER_DAY << "/day fine.\n";
}

void returnBook() {
    int id;
    cout << "Book ID: "; cin >> id;
    Book* b = findById(id);
    if (!b) { cout << "Book nahi mili!\n"; return; }
    if (!b->isIssued) { cout << "Ye book issued hi nahi thi.\n"; return; }
    cout << "Kitne din baad return kar rahe ho? ";
    cin >> b->daysKept;
    int fine = b->calculateFine();
    if (fine > 0) {
        cout << "LATE RETURN! Fine: Rs." << fine << " (Rs.10/day)\n";
        b->totalFinePaid += fine;
    } else {
        cout << "On time return. Koi fine nahi!\n";
    }
    b->isIssued = false;
    b->issuedTo = "";
    b->daysKept = 0;
    saveToFile();
    cout << "Book return ho gayi!\n";
}

void deleteBook() {
    int id;
    cout << "Book ID: "; cin >> id;
    Book* b = findById(id);
    if (b && b->isIssued) {
        cout << "Ye book issued hai, pehle return karwao, phir delete karo.\n";
        return;
    }
    for (size_t i = 0; i < books.size(); i++) {
        if (books[i].id == id) {
            books.erase(books.begin() + i);
            saveToFile();
            cout << "Book delete ho gayi.\n";
            return;
        }
    }
    cout << "Book nahi mili!\n";
}

int main() {
    loadFromFile();
    cout << "==============================================\n";
    cout << "  LIBRARY MANAGEMENT SYSTEM  v2.0 ADVANCED\n";
    cout << "  Ritik Kumar | CSE (AIML) 3rd Sem\n";
    cout << "  Issue: 14 days | Late Fine: Rs.10/day\n";
    cout << "==============================================\n";

    while (true) {
        showDashboard();
        cout << "\n1. Add Book\n2. View All Books\n3. Search Book (ID / Title / Author)\n";
        cout << "4. Sort Books by Title\n5. Issue Book\n6. Return Book (Fine auto-calculate)\n";
        cout << "7. Delete Book\n8. Exit\nChoose (1-8): ";
        int choice;
        cin >> choice;
        if (choice == 1) addBook();
        else if (choice == 2) viewAll();
        else if (choice == 3) searchBook();
        else if (choice == 4) sortByTitle();
        else if (choice == 5) issueBook();
        else if (choice == 6) returnBook();
        else if (choice == 7) deleteBook();
        else if (choice == 8) { saveToFile(); cout << "Data save ho gaya. Bye Ritik!\n"; break; }
        else cout << "Galat choice, 1-8 daalo.\n";
    }
    return 0;
}
