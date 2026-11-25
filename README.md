# 📚 Library Management System — v2.0 Advanced

![Java](https://img.shields.io/badge/Java-17-orange)
![C++](https://img.shields.io/badge/C++-17-blue)
![Semester](https://img.shields.io/badge/Semester-3rd-green)
![Status](https://img.shields.io/badge/Status-Working-brightgreen)

**A complete Library Management System built in both Java and C++ — same idea, two languages, so you can learn the difference.**

> 👨‍🎓 **Author:** Ritik Kumar — BTech CSE (AIML), 3rd Semester
> 🔗 **GitHub:** [ritik4343](https://github.com/ritik4343)
> 📁 **Repo:** [library-management-system](https://github.com/ritik4343/library-management-system)

---

## ✨ What's New in v2.0

| Feature | v1.0 | v2.0 Advanced |
|---|---|---|
| Add / View / Delete Book | ✅ | ✅ |
| Search by Title only | ✅ | ✅ **+ by ID & Author** |
| Issue / Return Book | ✅ | ✅ **+ Due Date (14 days)** |
| Late Fine | ❌ | ✅ **Rs. 10/day, auto-calculated** |
| Category & Price | ❌ | ✅ |
| Sort by Title | ❌ | ✅ |
| Dashboard Stats | ❌ | ✅ **Total / Available / Issued / Fine Collected** |
| Validation | Basic | ✅ **Duplicate ID, empty title, issued-book protection** |

---

## 🚀 Features

- **📖 Book Management** — Add books with ID, Title, Author, Category and Price
- **🔍 Smart Search** — Search by ID, Title or Author (case-insensitive, partial match)
- **↕️ Sorting** — Sort all books alphabetically by Title
- **📤 Issue System** — Issue a book to a student, due date is auto-set to **14 days**
- **💰 Late Fine System** — Return after 14 days? Fine is **Rs. 10/day**, calculated automatically
  - *Example: 20 din me return kiya → 6 din late × Rs. 10 = Rs. 60 fine*
- **📊 Live Dashboard** — Every menu shows Total, Available, Issued books and total fine collected
- **🛡️ Safe Delete** — An issued book cannot be deleted, return it first
- **💾 Auto Save** — All data is saved in `books.txt`, so nothing is lost when you close the program

---

## 🗂️ Project Structure

```
library-management-system/
├── README.md
├── Main.java              # Java version (v2.0)
├── main.cpp               # C++ version (v2.0)
└── books.txt              # Auto-created data file (after first run)
```

---

## ▶️ How to Run

### Java

```bash
javac Main.java
java Main
```

> Requires JDK 8 or above.

### C++

```bash
g++ main.cpp -o library
./library
```

> Windows: `g++ main.cpp -o library.exe` then run `library.exe`

---

## 🧭 How to Use (Demo Steps)

1. Run the program and choose **1** — Add a book
   - Example: ID `101`, Title `Let Us C`, Author `Yashwant Kanetkar`, Category `CSE`, Price `450`
2. Choose **2** — See it in the list, status will be `Available`
3. Choose **5** — Issue it to `Ritik`, due date will be shown (today + 14 days)
4. Choose **6** — Return it
   - Java: fine is calculated from the real date
   - C++: it asks "Kitne din baad return kar rahe ho?" — enter `20` to see Rs. 60 fine
5. Choose **3** — Search it by Title, Author or ID
6. Choose **4** — Sort all books by Title
7. Choose **8** — Exit, data is saved in `books.txt`

---

## 🧠 Concepts Used (3rd Semester)

### Java
- **OOP** — `Book` class, constructor, encapsulation
- **ArrayList** — Dynamic storage, size grows as books are added
- **Sorting** — `Comparator` / lambda sort by title
- **File Handling** — `FileReader`, `PrintWriter`, `books.txt`
- **Date API** — `LocalDate`, `ChronoUnit.DAYS` for due date & fine
- **Exception Handling** — Wrong number input does not crash the program
- **Streams** — Dashboard counts with `stream().filter()`

### C++
- **OOP** — `class Book`, constructor, public members
- **vector** — Java's `ArrayList` equivalent
- **Sorting** — `std::sort` with a lambda comparator
- **File Handling** — `ifstream` / `ofstream`
- **string** — `transform` + `tolower` for case-insensitive search

### Java vs C++ — Key Difference
| | Java | C++ |
|---|---|---|
| Storage | `ArrayList<Book>` | `vector<Book>` |
| Sorting | `list.sort(...)` | `std::sort(...)` |
| Date | `LocalDate.now()` | Days entered by user |
| Memory | Garbage Collection (automatic) | Manual care needed |

---

## 🎤 Viva Questions & Answers

**Q1. Class aur Object me kya difference hai?**
> `Book` ek class hai (blueprint). Har book, jaise `Let Us C`, us class ka ek object hai.

**Q2. ArrayList / vector kyun use kiya, array kyun nahi?**
> Kyunki pehle se nahi pata kitni books aayengi. Array ka size fixed hota hai, ArrayList/vector ka size badhta-ghatta hai.

**Q3. Fine kaise calculate hota hai?**
> Issue date + 14 days = Due Date. Return date due date se late ho to `lateDays × 10` fine lagta hai.

**Q4. File Handling kyun chahiye?**
> Program band hone par data RAM se khatm ho jata hai. `books.txt` me save karne se dobara kholne par data wapas milta hai.

**Q5. Issued book delete kyun nahi hoti?**
> Data integrity ke liye — pehle book return karni hogi, tabhi delete hogi, warna record galat ho jayega.

---

## 🔮 Future Scope

- [ ] Student / Member registration
- [ ] Login system (Admin / Student)
- [ ] MySQL database instead of `books.txt`
- [ ] GUI version with Java Swing / JavaFX
- [ ] Python + ML version — recommend books based on past issues

---

## 📅 Learning Plan (7 Days)

| Day | Task |
|---|---|
| 1–2 | Java version chalao, 5 books add karke issue/return + fine dekho |
| 3–4 | Code ko bina dekhe dobara likhne ki koshish karo |
| 5–6 | C++ version chalao, dono ko side-by-side compare karo |
| 7 | Khud ek feature add karo — jaise Category-wise book count |

---

## 🤝 Contributing

Ye project Ritik Kumar ke learning ke liye hai. Suggestions aur improvements welcome hain — fork karo, change karo, pull request bhejo.

**Made with ❤️ by Ritik Kumar | CSE (AIML), 3rd Sem**
