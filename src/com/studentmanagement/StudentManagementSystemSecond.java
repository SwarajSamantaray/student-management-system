package com.studentmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Console-based Student Management System demonstrating usage of the
 * Java Collection Framework: ArrayList, HashMap and LinkedHashMap.
 */
public class StudentManagementSystemSecond {

    // Primary storage - ArrayList maintains insertion order and allows easy iteration.
    private static final List<Student> studentList = new ArrayList<>();

    // HashMap for fast ID-based lookup (no ordering guarantee).
    private static final Map<Integer, Student> studentMap = new HashMap<>();

    // LinkedHashMap to demonstrate insertion-order-preserving map traversal.
    private static final Map<Integer, Student> studentLinkedMap = new LinkedHashMap<>();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    displayAllStudents();
                    break;
                case "3":
                    searchStudent();
                    break;
                case "4":
                    updateStudent();
                    break;
                case "5":
                    removeStudent();
                    break;
                case "6":
                    displayStudentsByDepartment();
                    break;
                case "7":
                    displayTopStudent();
                    break;
                case "8":
                    displayStudentMap();
                    break;
                case "9":
                    searchStudentUsingMap();
                    break;
                case "10":
                    displayStudentsUsingLinkedHashMap();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n====================================");
        System.out.println("     STUDENT MANAGEMENT SYSTEM");
        System.out.println("====================================");
        System.out.println("1. Add Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Remove Student");
        System.out.println("6. Display Students by Department");
        System.out.println("7. Display Top Student");
        System.out.println("8. Display Student Map (HashMap)");
        System.out.println("9. Search Student Using Map");
        System.out.println("10. Display Students Using LinkedHashMap");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // ---------------------------------------------------------------
    // 1. Add Student
    // ---------------------------------------------------------------
    private static void addStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            if (id <= 0) {
                System.out.println("Invalid Student ID.");
                return;
            }

            if (studentMap.containsKey(id)) {
                System.out.println("Student ID already exists.");
                return;
            }

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            if (age <= 0) {
                System.out.println("Invalid Age.");
                return;
            }

            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();
            if (department.isEmpty()) {
                System.out.println("Department cannot be empty.");
                return;
            }

            System.out.print("Enter Marks: ");
            double marks = Double.parseDouble(scanner.nextLine().trim());
            if (marks < 0 || marks > 100) {
                System.out.println("Marks should be between 0 and 100.");
                return;
            }

            Student student = new Student(id, name, age, department, marks);
            studentList.add(student);
            studentMap.put(id, student);
            studentLinkedMap.put(id, student);

            System.out.println("Student added successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        }
    }

    // ---------------------------------------------------------------
    // 2. Display All Students
    // ---------------------------------------------------------------
    private static void displayAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("---------------------------------------------------------");
        System.out.printf("%-8s %-11s %-6s %-15s %-6s%n", "ID", "Name", "Age", "Department", "Marks");
        System.out.println("---------------------------------------------------------");

        for (Student s : studentList) {
            System.out.println(s.toTableRow());
        }

        System.out.println("---------------------------------------------------------");
    }

    // ---------------------------------------------------------------
    // 3. Search Student (linear search through ArrayList)
    // ---------------------------------------------------------------
    private static void searchStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            for (Student s : studentList) {
                if (s.getStudentId() == id) {
                    System.out.println("Student Found\n");
                    System.out.println(s.toDetailedString());
                    return;
                }
            }

            System.out.println("Student not found.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Student ID must be numeric.");
        }
    }

    // ---------------------------------------------------------------
    // 4. Update Student
    // ---------------------------------------------------------------
    private static void updateStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Student student = studentMap.get(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.print("Enter New Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter New Age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            if (age <= 0) {
                System.out.println("Invalid Age.");
                return;
            }

            System.out.print("Enter New Department: ");
            String department = scanner.nextLine().trim();
            if (department.isEmpty()) {
                System.out.println("Department cannot be empty.");
                return;
            }

            System.out.print("Enter New Marks: ");
            double marks = Double.parseDouble(scanner.nextLine().trim());
            if (marks < 0 || marks > 100) {
                System.out.println("Marks should be between 0 and 100.");
                return;
            }

            student.setName(name);
            student.setAge(age);
            student.setDepartment(department);
            student.setMarks(marks);

            // studentList, studentMap and studentLinkedMap all hold references
            // to the same Student object, so all three stay in sync automatically.

            System.out.println("Student updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        }
    }

    // ---------------------------------------------------------------
    // 5. Remove Student
    // ---------------------------------------------------------------
    private static void removeStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Student student = studentMap.get(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            studentList.remove(student);
            studentMap.remove(id);
            studentLinkedMap.remove(id);

            System.out.println("Student removed successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Student ID must be numeric.");
        }
    }

    // ---------------------------------------------------------------
    // 6. Display Students by Department
    // ---------------------------------------------------------------
    private static void displayStudentsByDepartment() {
        System.out.print("Enter Department: ");
        String department = scanner.nextLine().trim();

        System.out.println("--------- " + department.toUpperCase() + " STUDENTS ---------\n");

        boolean found = false;
        for (Student s : studentList) {
            if (s.getDepartment().equalsIgnoreCase(department)) {
                System.out.println(s.toDepartmentRow());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No students found in this department.");
        }
    }

    // ---------------------------------------------------------------
    // 7. Display Top Student (highest marks)
    // ---------------------------------------------------------------
    private static void displayTopStudent() {
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        Student topStudent = studentList.get(0);
        for (Student s : studentList) {
            if (s.getMarks() > topStudent.getMarks()) {
                topStudent = s;
            }
        }

        System.out.println("====================================");
        System.out.println("          TOP STUDENT");
        System.out.println("====================================\n");
        System.out.println("Student ID : " + topStudent.getStudentId());
        System.out.println("Name       : " + topStudent.getName());
        System.out.println("Department : " + topStudent.getDepartment());
        System.out.println("Marks      : " + topStudent.getMarks());
        System.out.println("\n====================================");
    }

    // ---------------------------------------------------------------
    // 8. Display Student Map (HashMap)
    // ---------------------------------------------------------------
    private static void displayStudentMap() {
        if (studentMap.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("====================================");
        System.out.println("          STUDENT MAP");
        System.out.println("====================================\n");

        for (Map.Entry<Integer, Student> entry : studentMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue().getName());
        }
    }

    // ---------------------------------------------------------------
    // 9. Search Student Using Map
    // ---------------------------------------------------------------
    private static void searchStudentUsingMap() {
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Student student = studentMap.get(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.println("Student Found\n");
            System.out.println("ID         : " + student.getStudentId());
            System.out.println("Name       : " + student.getName());
            System.out.println("Department : " + student.getDepartment());
            System.out.println("Marks      : " + student.getMarks());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Student ID must be numeric.");
        }
    }

    // ---------------------------------------------------------------
    // 10. Display Students Using LinkedHashMap
    // ---------------------------------------------------------------
    private static void displayStudentsUsingLinkedHashMap() {
        if (studentLinkedMap.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("Insertion Order Maintained\n");

        for (Map.Entry<Integer, Student> entry : studentLinkedMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue().getName());
        }
    }
}
