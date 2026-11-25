// Library Management System - C++
// Ritik Kumar | BTech CSE (AIML), 3rd Semester
// 3rd Sem Topics: OOP (Class, Object), vector, File Handling, string
// Run: g++ main.cpp -o library  ->  ./library   (Windows: g++ main.cpp -o library.exe -> library.exe)

#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <algorithm>
using namespace std;

class Book {
public:
    int id;
    string title;
    string author;
    bool isIssued;
    string issuedTo;

    Book(int id, string title, string author) {
        this->id = id;
        this->title = title;
        this->author = author;
        this->isIssued = false;
        this->issuedTo = "";
    }

    void display() {
        string status = isIssued ? "Issued to " + issuedTo : "Available";
        cout << id << " | " << title << " | " << author << " | " << status << endl;
    }
};

vector<Book> books;
const string FILE_NAME = "books.txt";

void saveToFile() {
    ofstream out(FILE_NAME);
    for (auto &b : books) {
        out << b.id << "," << b.title << "," << b.author << ","
            << b.isIssued << "," << b.issuedTo << "\n";
    }
    out.close();
}

void loadFromFile() {
    ifstream in(FILE_NAME);
    if (!in) return;
    string line;
    while (getline(in, line)) {
        if (line.empty()) continue;
        // Simple parsing: id,title,author,isIssued,issuedTo
        size_t p1 = line.find(',');
        size_t p2 = line.find(',', p1 + 1);
        size_t p3 = line.find(',', p2 + 1);
        size_t p4 = line.find(',', p3 + 1);
        if (p1 == string::npos || p2 == string::npos || p3 == string::npos) continue;
        int id = stoi(line.substr(0, p1));
        string title = line.substr(p1 + 1, p2 - p1 - 1);
        string author = line.substr(p2 + 1, p3 - p2 - 1);
        bool issued = (line.substr(p3 + 1, p4 - p3 - 1) == "1");
        string to = (p4 == string::npos) ? "" : line.substr(p4 + 1);
        Book b(id, title, author);
        b.isIssued = issued;
        b.issuedTo = to;
        books.push_back(b);
    }
    in.close();
}

void addBook() {
    int id;
    string title, author;
    cout << "Book ID: "; cin >> id;
    cin.ignore();
    for (auto &b : books) {
        if (b.id == id) { cout << "Ye ID pehle se hai!\n"; return; }
    }
    cout << "Title: "; getline(cin, title);
    cout << "Author: "; getline(cin, author);
    books.push_back(Book(id, title, author));
    saveToFile();
    cout << "Book add ho gayi!\n";
}

void viewAll() {
    if (books.empty()) { cout << "Library khaali hai. Pehle book add karo.\n"; return; }
    cout << "\nID | Title | Author | Status\n";
    cout << "---------------------------------\n";
    for (auto &b : books) b.display();
}

void searchBook() {
    string key;
    cout << "Title likho (ya uska part): ";
    cin.ignore();
    getline(cin, key);
    bool found = false;
    for (auto &b : books) {
        string t = b.title;
        // case-insensitive simple search
        string tl = t, kl = key;
        transform(tl.begin(), tl.end(), tl.begin(), ::tolower);
        transform(kl.begin(), kl.end(), kl.begin(), ::tolower);
        if (tl.find(kl) != string::npos) { b.display(); found = true; }
    }
    if (!found) cout << "Book nahi mili!\n";
}

void issueBook() {
    int id;
    cout << "Book ID: "; cin >> id;
    for (auto &b : books) {
        if (b.id == id) {
            if (b.isIssued) { cout << "Ye book pehle se issued hai.\n"; return; }
            cout << "Kisko issue karni hai (Student Name): ";
            cin.ignore();
            getline(cin, b.issuedTo);
            b.isIssued = true;
            saveToFile();
            cout << "Book issue ho gayi!\n";
            return;
        }
    }
    cout << "Book nahi mili!\n";
}

void returnBook() {
    int id;
    cout << "Book ID: "; cin >> id;
    for (auto &b : books) {
        if (b.id == id) {
            if (!b.isIssued) { cout << "Ye book issued hi nahi thi.\n"; return; }
            b.isIssued = false;
            b.issuedTo = "";
            saveToFile();
            cout << "Book return ho gayi!\n";
            return;
        }
    }
    cout << "Book nahi mili!\n";
}

void deleteBook() {
    int id;
    cout << "Book ID: "; cin >> id;
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
    cout << "=================================\n";
    cout << "  LIBRARY MANAGEMENT SYSTEM\n";
    cout << "  Ritik Kumar | CSE (AIML) 3rd Sem\n";
    cout << "=================================\n";

    while (true) {
        cout << "\n1. Add Book\n2. View All Books\n3. Search Book by Title\n";
        cout << "4. Issue Book\n5. Return Book\n6. Delete Book\n7. Exit\n";
        cout << "Choose (1-7): ";
        int choice;
        cin >> choice;
        if (choice == 1) addBook();
        else if (choice == 2) viewAll();
        else if (choice == 3) searchBook();
        else if (choice == 4) issueBook();
        else if (choice == 5) returnBook();
        else if (choice == 6) deleteBook();
        else if (choice == 7) { saveToFile(); cout << "Data save ho gaya. Bye Ritik!\n"; break; }
        else cout << "Galat choice, 1-7 daalo.\n";
    }
    return 0;
}
