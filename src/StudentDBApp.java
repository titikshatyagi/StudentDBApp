import java.sql.*;
import java.util.*;

class Student {
    private int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
}

public class StudentDBApp {
    static final String URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "root";
    static final String PASS = "pwd"; // change this

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n1. Add Student\n2. View All\n3. Search by ID\n4. Update Student\n5. Delete Student\n6. Exit");
                int choice = sc.nextInt(); sc.nextLine();

                switch (choice) {
                    case 1 -> addStudent(conn, sc);
                    case 2 -> viewAllStudents(conn);
                    case 3 -> searchStudent(conn, sc);
                    case 4 -> updateStudent(conn, sc);
                    case 5 -> deleteStudent(conn, sc);
                    case 6 -> System.exit(0);
                    default -> System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt(); sc.nextLine();

        // Check if ID already exists
        PreparedStatement check = conn.prepareStatement("SELECT id FROM students WHERE id = ?");
        check.setInt(1, id);
        ResultSet rs = check.executeQuery();
        if (rs.next()) {
            System.out.println("ID already exists. Use update or delete it first.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO students VALUES (?, ?, ?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.setInt(3, age);
        stmt.setString(4, course);

        stmt.executeUpdate();
        System.out.println("Student added.");
    }

    static void viewAllStudents(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");

        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.printf("ID: %d, Name: %s, Age: %d, Course: %s\n",
                    rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("course"));
        }
        if (!found) System.out.println("No students found.");
    }

    static void searchStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt(); sc.nextLine();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            System.out.printf("ID: %d, Name: %s, Age: %d, Course: %s\n",
                    rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("course"));
        } else {
            System.out.println("Student not found.");
        }
    }

    static void updateStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt(); sc.nextLine();

        PreparedStatement check = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
        check.setInt(1, id);
        ResultSet rs = check.executeQuery();

        if (!rs.next()) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();
        System.out.print("Enter New Age: ");
        int age = sc.nextInt(); sc.nextLine();
        System.out.print("Enter New Course: ");
        String course = sc.nextLine();

        PreparedStatement stmt = conn.prepareStatement("UPDATE students SET name=?, age=?, course=? WHERE id=?");
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setString(3, course);
        stmt.setInt(4, id);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    static void deleteStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt(); sc.nextLine();

        PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE id = ?");
        stmt.setInt(1, id);
        int affected = stmt.executeUpdate();

        if (affected > 0) System.out.println("Student deleted.");
        else System.out.println("Student not found.");
    }
}
