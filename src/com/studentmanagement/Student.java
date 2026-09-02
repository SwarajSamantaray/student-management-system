package com.studentmanagement;

/**
 * Represents a single student record.
 *
 * Supports two usage styles:
 *  1. Original style - three subject marks (subject1Marks, subject2Marks, subject3Marks)
 *  2. StudentManagementSystemSecond style - a department + single overall marks value
 */
public class Student {

    int studentId;
    String name;
    int age;
    double subject1Marks;
    double subject2Marks;
    double subject3Marks;

    // Extra fields needed by StudentManagementSystemSecond
    private String department;
    private double marks;

    // ---------------------------------------------------------------
    // Original constructor (subject-marks style) - kept exactly as-is
    // ---------------------------------------------------------------
    public Student(int studentId, String name, int age,
                   double subject1Marks, double subject2Marks, double subject3Marks) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.subject1Marks = subject1Marks;
        this.subject2Marks = subject2Marks;
        this.subject3Marks = subject3Marks;

        // Keep "marks" in sync so getMarks()/toTableRow() etc. still work
        this.marks = (subject1Marks + subject2Marks + subject3Marks) / 3;
    }

    // ---------------------------------------------------------------
    // New constructor (department + single marks style) - used by
    // StudentManagementSystemSecond's addStudent()
    // ---------------------------------------------------------------
    public Student(int studentId, String name, int age, String department, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.department = department;
        this.marks = marks;
    }

    /** Displays this student's full details (original method, unchanged). */
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

    // ---------------------------------------------------------------
    // Getters / setters needed by StudentManagementSystemSecond
    // ---------------------------------------------------------------
    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    /**
     * Row format used for the "Display All Students" table.
     */
    public String toTableRow() {
        return String.format("%-8d %-11s %-6d %-15s %-6.1f",
                studentId, name, age, department, marks);
    }

    /**
     * Compact row format used for department-wise listing.
     */
    public String toDepartmentRow() {
        return String.format("%-5d %-8s %-7s %-6.1f",
                studentId, name, department, marks);
    }

    /**
     * Detailed multi-line format used for search results / top student.
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID         : ").append(studentId).append("\n");
        sb.append("Name       : ").append(name).append("\n");
        sb.append("Age        : ").append(age).append("\n");
        sb.append("Department : ").append(department).append("\n");
        sb.append("Marks      : ").append(marks);
        return sb.toString();
    }
}
