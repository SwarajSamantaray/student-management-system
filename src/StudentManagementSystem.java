import java.util.Scanner;

/**
 * Student Management System
 * Core Java Integrated Assignment
 *
 * Demonstrates: variables, operators, if/else, switch, type casting,
 * Scanner, for/while/do-while loops, nested loops, 1D & 2D arrays,
 * methods, method overloading, classes, objects, constructors, this keyword.
 *
 * No ArrayList / Collections / Streams / Lambdas / external libraries used.
 */
public class StudentManagementSystem {

    // ---------- Shared Scanner and student storage ----------
    static Scanner sc = new Scanner(System.in);

    static final int MAX_STUDENTS = 10;
    static Student[] students = new Student[MAX_STUDENTS];
    static int studentCount = 0; // how many slots in 'students' are filled

    public static void main(String[] args) {

        int choice = 0;

        // ----- WHILE LOOP: keep running until user selects Exit -----
        while (choice != 8) {

            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    displayResult();
                    break;
                case 5:
                    updateMarks();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    displaySubjectWiseMarks();
                    break;
                case 8:
                    System.out.println("\nExiting Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter a number between 1 and 8.");
            }
        }

        sc.close();
    }

    // =========================================================
    // MENU
    // =========================================================
    static void printMenu() {
        System.out.println("\n========================================");
        System.out.println("       STUDENT MANAGEMENT SYSTEM");
        System.out.println("========================================\n");
        System.out.println("1. Add Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Display Student Result");
        System.out.println("5. Update Student Marks");
        System.out.println("6. Class Statistics");
        System.out.println("7. Subject-wise Marks");
        System.out.println("8. Exit\n");
    }

    // =========================================================
    // 1. ADD STUDENT
    // =========================================================
    static void addStudent() {

        // Array capacity check
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("\nCannot add more students. Maximum limit (" + MAX_STUDENTS + ") reached.");
            return;
        }

        System.out.println();
        int id = readInt("Enter Student ID: ");

        // Student ID must be positive
        if (id <= 0) {
            System.out.println("Invalid Student ID. ID must be a positive number.");
            return;
        }

        // Student ID must not be duplicated
        if (findStudentIndex(id) != -1) {
            System.out.println("A student with ID " + id + " already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        int age = readInt("Enter Age: ");

        // Age should be reasonable
        if (age <= 0 || age > 100) {
            System.out.println("Invalid age. Age must be between 1 and 100.");
            return;
        }

        // DO-WHILE LOOP: repeated validation for each subject's marks
        double m1 = readValidatedMarks("Enter Subject 1 Marks: ");
        double m2 = readValidatedMarks("Enter Subject 2 Marks: ");
        double m3 = readValidatedMarks("Enter Subject 3 Marks: ");

        Student newStudent = new Student(id, name, age, m1, m2, m3);
        students[studentCount] = newStudent;
        studentCount += 1; // assignment + arithmetic operator usage

        System.out.println("\nStudent added successfully.");
    }

    /**
     * Uses a do-while loop to keep asking until marks are within 0-100.
     */
    static double readValidatedMarks(String prompt) {
        double marks;
        do {
            marks = readDouble(prompt);
            if (marks < 0 || marks > 100) {
                System.out.println("Invalid marks. Please enter a value between 0 and 100.");
            }
        } while (marks < 0 || marks > 100); // relational + logical operators
        return marks;
    }

    // =========================================================
    // 2. DISPLAY ALL STUDENTS
    // =========================================================
    static void displayAllStudents() {

        if (studentCount == 0) {
            System.out.println("\nNo students to display.");
            return;
        }

        System.out.println("\n--------------------------------------------------------");
        System.out.printf("%-8s%-14s%-8s%-8s%-8s%-8s%n", "ID", "Name", "Age", "Sub1", "Sub2", "Sub3");
        System.out.println("--------------------------------------------------------");

        // FOR LOOP: traverse the student array
        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            System.out.printf("%-8d%-14s%-8d%-8.1f%-8.1f%-8.1f%n",
                    s.studentId, s.name, s.age, s.subject1Marks, s.subject2Marks, s.subject3Marks);
        }

        System.out.println("--------------------------------------------------------");
    }

    // =========================================================
    // 3. SEARCH STUDENT
    // =========================================================
    static void searchStudent() {
        System.out.println();
        int id = readInt("Enter Student ID: ");

        int index = findStudentIndex(id);

        if (index == -1) {
            System.out.println("\nStudent not found.");
        } else {
            System.out.println();
            students[index].displayStudent();
        }
    }

    /**
     * Uses a for loop and conditional statements to locate a student by ID.
     * Returns the array index, or -1 if not found.
     */
    static int findStudentIndex(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].studentId == id) { // relational operator
                return i;
            }
        }
        return -1;
    }

    // =========================================================
    // 4. DISPLAY STUDENT RESULT
    // =========================================================
    static void displayResult() {
        System.out.println();
        int id = readInt("Enter Student ID: ");

        int index = findStudentIndex(id);
        if (index == -1) {
            System.out.println("\nStudent not found.");
            return;
        }

        Student s = students[index];

        double total = calculateTotal(s.subject1Marks, s.subject2Marks, s.subject3Marks);
        double average = calculateAverage(s.subject1Marks, s.subject2Marks, s.subject3Marks);
        double percentage = average; // out of 100 per subject, so average == percentage
        String grade = calculateGrade(percentage);
        boolean passed = isPassed(s.subject1Marks, s.subject2Marks, s.subject3Marks);

        System.out.println("\n====================================");
        System.out.println("          STUDENT RESULT");
        System.out.println("====================================\n");
        System.out.println("Student ID     : " + s.studentId);
        System.out.println("Name           : " + s.name);
        System.out.println("Age            : " + s.age);
        System.out.println();
        System.out.println("Subject 1      : " + s.subject1Marks);
        System.out.println("Subject 2      : " + s.subject2Marks);
        System.out.println("Subject 3      : " + s.subject3Marks);
        System.out.println();
        System.out.printf("Total Marks    : %.2f%n", total);
        System.out.printf("Average        : %.2f%n", average);
        System.out.printf("Percentage     : %.2f%%%n", percentage);
        System.out.println("Grade          : " + grade);
        System.out.println("Status         : " + (passed ? "PASS" : "FAIL"));
        System.out.println("\n====================================");
    }

    // =========================================================
    // 5. UPDATE STUDENT MARKS
    // =========================================================
    static void updateMarks() {
        System.out.println();
        int id = readInt("Enter Student ID: ");

        int index = findStudentIndex(id);
        if (index == -1) {
            System.out.println("\nStudent not found.");
            return;
        }

        Student s = students[index];

        double m1 = readValidatedMarks("Enter new Subject 1 Marks: ");
        double m2 = readValidatedMarks("Enter new Subject 2 Marks: ");
        double m3 = readValidatedMarks("Enter new Subject 3 Marks: ");

        s.subject1Marks = m1;
        s.subject2Marks = m2;
        s.subject3Marks = m3;

        System.out.println("\nStudent marks updated successfully.");
    }

    // =========================================================
    // 6. CLASS STATISTICS
    // =========================================================
    static void displayStatistics() {

        if (studentCount == 0) {
            System.out.println("\nNo students available for statistics.");
            return;
        }

        double sumPercentage = 0;
        double highest = -1;
        double lowest = 101;
        int passCount = 0;
        int failCount = 0;

        // FOR LOOP: calculate statistics across all students
        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            double percentage = calculateAverage(s.subject1Marks, s.subject2Marks, s.subject3Marks);

            sumPercentage += percentage; // compound assignment operator

            if (percentage > highest) {
                highest = percentage;
            }
            if (percentage < lowest) {
                lowest = percentage;
            }

            if (isPassed(s.subject1Marks, s.subject2Marks, s.subject3Marks)) {
                passCount++;
            } else {
                failCount++;
            }
        }

        double averagePercentage = sumPercentage / studentCount; // type-cast-safe: both doubles

        System.out.println("\n====================================");
        System.out.println("          CLASS STATISTICS");
        System.out.println("====================================\n");
        System.out.println("Number of Students : " + studentCount);
        System.out.printf("Average Percentage : %.2f%%%n", averagePercentage);
        System.out.printf("Highest Percentage : %.2f%%%n", highest);
        System.out.printf("Lowest Percentage  : %.2f%%%n", lowest);
        System.out.println("Pass Count         : " + passCount);
        System.out.println("Fail Count         : " + failCount);
        System.out.println("\n====================================");
    }

    // =========================================================
    // 7. SUBJECT-WISE MARKS  (uses a 2D array + nested loops)
    // =========================================================
    static void displaySubjectWiseMarks() {

        if (studentCount == 0) {
            System.out.println("\nNo students available.");
            return;
        }

        // Build a 2D array: rows = students, columns = subjects
        double[][] marksMatrix = new double[studentCount][3];

        // NESTED LOOP: fill the 2D matrix from student objects
        for (int i = 0; i < studentCount; i++) {
            double[] rowMarks = { students[i].subject1Marks, students[i].subject2Marks, students[i].subject3Marks };
            for (int j = 0; j < rowMarks.length; j++) {
                marksMatrix[i][j] = rowMarks[j];
            }
        }

        System.out.println("\n--------------------------------------------------");
        System.out.printf("%-9s%-14s%-10s%-10s%-10s%n", "ID", "Name", "Sub1", "Sub2", "Sub3");
        System.out.println("--------------------------------------------------");

        double[] subjectTotals = new double[3];

        // NESTED LOOP: display matrix and accumulate subject totals
        for (int i = 0; i < studentCount; i++) {
            System.out.printf("%-9d%-14s", students[i].studentId, students[i].name);
            for (int j = 0; j < 3; j++) {
                System.out.printf("%-10.1f", marksMatrix[i][j]);
                subjectTotals[j] += marksMatrix[i][j];
            }
            System.out.println();
        }

        System.out.println("--------------------------------------------------");

        // Type casting used to safely compute an average from a sum
        double sub1Avg = (double) subjectTotals[0] / studentCount;
        double sub2Avg = (double) subjectTotals[1] / studentCount;
        double sub3Avg = (double) subjectTotals[2] / studentCount;

        System.out.printf("Subject 1 Average : %.2f%n", sub1Avg);
        System.out.printf("Subject 2 Average : %.2f%n", sub2Avg);
        System.out.printf("Subject 3 Average : %.2f%n", sub3Avg);
    }

    // =========================================================
    // CALCULATION METHODS (with overloading)
    // =========================================================

    /** Overload 1: total of two int marks */
    static int calculateTotal(int mark1, int mark2) {
        return mark1 + mark2;
    }

    /** Overload 2: total of three double marks */
    static double calculateTotal(double mark1, double mark2, double mark3) {
        return mark1 + mark2 + mark3;
    }

    /** Overload 1: average of two int marks */
    static double calculateAverage(int mark1, int mark2) {
        return (double) (mark1 + mark2) / 2; // type casting to avoid integer division
    }

    /** Overload 2: average of three int marks */
    static double calculateAverage(int mark1, int mark2, int mark3) {
        int total = mark1 + mark2 + mark3;
        return (double) total / 3; // type casting requirement
    }

    /** Overload 3: average of three double marks */
    static double calculateAverage(double mark1, double mark2, double mark3) {
        double total = mark1 + mark2 + mark3;
        return total / 3;
    }

    /** Determines letter grade from percentage using if/else. */
    static String calculateGrade(double percentage) {
        String grade;
        if (percentage >= 90) {
            grade = "A+";
        } else if (percentage >= 80) {
            grade = "A";
        } else if (percentage >= 70) {
            grade = "B";
        } else if (percentage >= 60) {
            grade = "C";
        } else if (percentage >= 50) {
            grade = "D";
        } else if (percentage >= 40) {
            grade = "E";
        } else {
            grade = "F";
        }
        return grade;
    }

    /** A student passes only if all three subjects are >= 40. */
    static boolean isPassed(double m1, double m2, double m3) {
        return m1 >= 40 && m2 >= 40 && m3 >= 40; // relational + logical AND
    }

    // =========================================================
    // INPUT HELPER METHODS (Scanner handling + validation loops)
    // =========================================================

    /** Reads an integer safely, re-prompting on invalid (non-numeric) input. */
    static int readInt(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                value = Integer.parseInt(line);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
        return value;
    }

    /** Reads a double safely, re-prompting on invalid (non-numeric) input. */
    static double readDouble(String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                value = Double.parseDouble(line);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
        return value;
    }
}

/**
 * Represents a single student record.
 */
class Student {

    int studentId;
    String name;
    int age;
    double subject1Marks;
    double subject2Marks;
    double subject3Marks;

    // Parameterized constructor using the 'this' keyword
    public Student(int studentId, String name, int age,
                   double subject1Marks, double subject2Marks, double subject3Marks) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.subject1Marks = subject1Marks;
        this.subject2Marks = subject2Marks;
        this.subject3Marks = subject3Marks;
    }

    /** Displays this student's full details. */
    void displayStudent() {
        double total = this.subject1Marks + this.subject2Marks + this.subject3Marks;
        double average = total / 3;

        System.out.println("Student ID     : " + this.studentId);
        System.out.println("Name           : " + this.name);
        System.out.println("Age            : " + this.age);
        System.out.println("Subject 1      : " + this.subject1Marks);
        System.out.println("Subject 2      : " + this.subject2Marks);
        System.out.println("Subject 3      : " + this.subject3Marks);
        System.out.printf("Total Marks    : %.2f%n", total);
        System.out.printf("Average Marks  : %.2f%n", average);
    }
}