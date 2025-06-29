## StudentDBApp – Java JDBC MySQL Console App

A simple command-line Java application that uses JDBC to connect to a MySQL database and perform CRUD operations on student records.

---

## Features

- Add new student
- View all students
- Search student by ID
- Update student details
- Delete student by ID
- Prevents duplicate IDs

---

## Technologies Used

- Java (JDK 17 or later)
- MySQL (Server + Workbench)
- JDBC (MySQL Connector/J)
- IntelliJ IDEA (or any Java IDE)

---

## Database Setup

1. Start MySQL Server via Workbench or XAMPP.
2. Open MySQL Workbench.
3. Run the following SQL:

```sql
CREATE DATABASE student_db;
USE student_db;

CREATE TABLE students (
  id INT PRIMARY KEY,
  name VARCHAR(50),
  age INT,
  course VARCHAR(50)
);
```

---

## Configuration

In StudentDBApp.java, update the DB credentials:

```java
static final String URL = "jdbc:mysql://localhost:3306/student_db";
static final String USER = "root";
static final String PASS = ""; // or your MySQL root password
```

---

## How to Run
1. Open the project in IntelliJ IDEA.
2. Add mysql-connector-java-<version>.jar to project libraries: File > Project Structure > Libraries > + Add JAR
3. Run StudentDBApp.java.
4. Use the console menu to add/view/search/update/delete students.