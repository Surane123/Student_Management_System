package com.weintern.students;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A student's identity, grade, and enrolled subjects. */
public final class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private String name;
    private int age;
    private double grade;
    private final List<String> subjects;

    public Student(int id, String name, int age, double grade, List<String> subjects) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
        setName(name);
        setAge(age);
        setGrade(grade);
        this.subjects = new ArrayList<>();
        setSubjects(subjects);
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGrade() { return grade; }
    public List<String> getSubjects() { return Collections.unmodifiableList(subjects); }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name is required.");
        this.name = name.trim();
    }
    public void setAge(int age) {
        if (age < 3 || age > 120) throw new IllegalArgumentException("Age must be between 3 and 120.");
        this.age = age;
    }
    public void setGrade(double grade) {
        if (grade < 0 || grade > 100) throw new IllegalArgumentException("Grade must be between 0 and 100.");
        this.grade = grade;
    }
    public void setSubjects(List<String> values) {
        subjects.clear();
        if (values != null) for (String value : values) {
            if (value != null && !value.trim().isEmpty() && !subjects.contains(value.trim())) subjects.add(value.trim());
        }
    }
    @Override public String toString() {
        return String.format("%-6d %-24s %-5d %-8.2f %s", id, name, age, grade, String.join(", ", subjects));
    }
}
