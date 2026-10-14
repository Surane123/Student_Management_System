package com.weintern.students;

import java.io.*;
import java.util.*;

/** In-memory student CRUD operations with optional serialized persistence. */
public final class StudentManager {
    private final List<Student> students = new ArrayList<>();
    public List<Student> all() { return Collections.unmodifiableList(students); }
    public boolean add(Student student) {
        if (findById(student.getId()) != null) return false;
        students.add(student);
        return true;
    }
    public Student findById(int id) {
        for (Student student : students) if (student.getId() == id) return student;
        return null;
    }
    public List<Student> findByName(String name) {
        List<Student> matches = new ArrayList<>();
        for (Student student : students) if (student.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) matches.add(student);
        return matches;
    }
    public boolean delete(int id) { return students.remove(findById(id)); }
    public double average() {
        if (students.isEmpty()) return 0.0;
        double total = 0;
        for (Student student : students) total += student.getGrade();
        return total / students.size();
    }
    public void save(File file) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) { out.writeObject(new ArrayList<>(students)); }
    }
    @SuppressWarnings("unchecked")
    public void load(File file) throws IOException {
        if (!file.isFile()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object value = in.readObject();
            if (!(value instanceof List<?>)) throw new IOException("Student data has an unexpected format.");
            students.clear();
            for (Object item : (List<?>) value) {
                if (!(item instanceof Student)) throw new IOException("Student data contains an invalid record.");
                students.add((Student) item);
            }
        } catch (ClassNotFoundException ex) { throw new IOException("Could not read saved student records.", ex); }
    }
}
