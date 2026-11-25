# 3rd Semester Project — Library Management System
**Student:** Ritik Kumar | BTech CSE (AIML), 3rd Semester | GitHub: ritik4343

Ye project 3rd year ka nahi, **3rd semester ke hisaab se** banaya hai. Isi level ke questions viva me poochhe jate hain.

## Idea ek hi, do languages me
Dono me same kaam hai, taaki tum Java aur C++ ka difference samajh sako:
- Book Add karo
- Saari books dekho
- Title se search karo
- Book Issue karo (kisko di, wo save hoga)
- Book Return karo
- Book Delete karo
- Data `books.txt` file me save hota hai

## 1. Java version — `java-library-system/`
```bash
javac Main.java
java Main
```
**3rd Sem concepts isme:**
- OOP: `Book` class, uske attributes `id, title, author, isIssued`
- `ArrayList<Book>` — saari books store karne ke liye
- File Handling: `books.txt` me save/load
- Exception Handling: galat number daalo to program crash nahi hota

## 2. C++ version — `cpp-library-system/`
```bash
g++ main.cpp -o library
./library
```
Windows pe: `g++ main.cpp -o library.exe` phir `library.exe`
**3rd Sem concepts isme:**
- OOP: `class Book`, constructor
- `vector<Book>` — Java ke ArrayList jaisa hi hai
- File Handling: `ifstream / ofstream` se `books.txt`
- `string`, `transform` se case-insensitive search

## Viva me kya bolna hai?
- **Class vs Object:** `Book` class hai (blueprint), har book uska object hai.
- **ArrayList / vector kyun?** Kyunki pehle se nahi pata kitni books aayengi, size badhta-ghatta hai.
- **File Handling kyun?** Program band hone par data na khoye, isliye `books.txt` me save karte hain.
- **Java vs C++ difference:** Java me `ArrayList` aur garbage collection automatic hai, C++ me `vector` use karte hain aur memory ka dhyaan khud rakhna padta hai.

## Kaise seekhna hai? (7 din ka plan)
- **Day 1-2:** Java wala chalao, 5 books add karke issue/return karo.
- **Day 3-4:** Code ko bina dekhe dobara likhne ki koshish karo.
- **Day 5-6:** C++ wala chalao, aur dono code ko side-by-side compare karo.
- **Day 7:** Ek naya feature khud add karo — jaise Fine: agar book 7 din se zyada rakhi to Rs. 5/day fine.

Ye project GitHub `ritik4343` pe daalne ke liye bhi perfect hai — pehla repo isi ka banao.
