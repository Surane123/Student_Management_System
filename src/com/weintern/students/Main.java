package com.weintern.students;

import java.io.File;
import java.io.IOException;
import java.util.*;

/** Console interface for managing student records. */
public final class Main {
    private static final File DATA = new File("students.ser");
    private final Scanner in = new Scanner(System.in);
    private final StudentManager manager = new StudentManager();
    public static void main(String[] args) {
        Main app = new Main();
        try { app.manager.load(DATA); } catch (IOException ex) { System.out.println("Saved data could not be loaded: " + ex.getMessage()); }
        app.run();
    }
    private void run() {
        boolean running = true;
        while (running) {
            System.out.println("\nSTUDENT RECORDS\n1 Add  2 Display  3 Search  4 Update  5 Delete  6 Class average  0 Exit");
            switch (readInt("Choice: ")) {
                case 1: add(); break;
                case 2: display(); break;
                case 3: search(); break;
                case 4: update(); break;
                case 5: delete(); break;
                case 6: System.out.printf(Locale.ROOT, "Class average: %.2f%n", manager.average()); break;
                case 0: running = false; break;
                default: System.out.println("Choose a listed option.");
            }
        }
        try { manager.save(DATA); System.out.println("Records saved to " + DATA.getAbsolutePath()); }
        catch (IOException ex) { System.out.println("Could not save records: " + ex.getMessage()); }
    }
    private void add() {
        try {
            int id = readInt("ID: "); String name = readText("Name: "); int age = readInt("Age: ");
            double grade = readDouble("Grade (0-100): ");
            Student student = new Student(id, name, age, grade, readSubjects("Subjects (comma-separated): "));
            System.out.println(manager.add(student) ? "Student added." : "That ID already exists.");
        } catch (IllegalArgumentException ex) { System.out.println("Invalid record: " + ex.getMessage()); }
    }
    private void display() {
        System.out.printf("%-6s %-24s %-5s %-8s %s%n", "ID", "NAME", "AGE", "GRADE", "SUBJECTS");
        for (Student s : manager.all()) System.out.println(s);
        if (manager.all().isEmpty()) System.out.println("No records yet.");
    }
    private void search() {
        System.out.println("1 Search by ID  2 Search by name");
        int choice = readInt("Choice: ");
        if (choice == 1) { Student s = manager.findById(readInt("ID: ")); System.out.println(s == null ? "No match." : s); }
        else if (choice == 2) { List<Student> matches = manager.findByName(readText("Name contains: ")); matches.forEach(System.out::println); if (matches.isEmpty()) System.out.println("No match."); }
        else System.out.println("Choose 1 or 2.");
    }
    private void update() {
        Student s = manager.findById(readInt("Student ID: "));
        if (s == null) { System.out.println("No student with that ID."); return; }
        try {
            String grade = readText("New grade, or Enter to keep [" + s.getGrade() + "]: ");
            if (!grade.isEmpty()) s.setGrade(parseDouble(grade));
            String subjects = readText("New comma-separated subjects, or Enter to keep: ");
            if (!subjects.isEmpty()) s.setSubjects(parseSubjects(subjects));
            System.out.println("Student updated.");
        } catch (IllegalArgumentException ex) { System.out.println("Update rejected: " + ex.getMessage()); }
    }
    private void delete() {
        int id = readInt("Student ID: "); Student s = manager.findById(id);
        if (s == null) { System.out.println("No student with that ID."); return; }
        System.out.println(manager.delete(id) ? "Student deleted." : "Delete failed.");
    }
    private int readInt(String prompt) { while (true) try { return Integer.parseInt(readText(prompt)); } catch (NumberFormatException ex) { System.out.println("Enter a whole number."); } }
    private double readDouble(String prompt) { while (true) try { return parseDouble(readText(prompt)); } catch (NumberFormatException ex) { System.out.println("Enter a valid number."); } }
    private static double parseDouble(String value) { return Double.parseDouble(value.trim()); }
    private String readText(String prompt) { System.out.print(prompt); return in.nextLine().trim(); }
    private List<String> readSubjects(String prompt) { return parseSubjects(readText(prompt)); }
    private static List<String> parseSubjects(String value) { List<String> result = new ArrayList<>(); for (String part : value.split(",")) if (!part.trim().isEmpty()) result.add(part.trim()); return result; }
}
