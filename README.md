# Student Management System

A Java 11+ console application for creating and managing student records. It demonstrates encapsulation, collections, validation, file persistence, and a menu-driven interface.

## Features

- Add students with an ID, name, age, grade, and subjects
- Display all records in a formatted table
- Search by ID or case-insensitive name
- Update grades and subjects
- Delete records and calculate the class average
- Save and load records using Java serialization

## Requirements

- JDK 11 or newer
- Eclipse, IntelliJ IDEA, or a terminal with `javac` and `java` on `PATH`

## Run from a terminal

From this project directory, in PowerShell:

```powershell
javac -d out (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName)
java -cp out com.weintern.students.Main
```

In Eclipse, import this directory using **File → Import → General → Existing Projects into Workspace** and run `com.weintern.students.Main`.

Records are saved to `students.ser` in the current working directory when you exit. The file is created locally and is not part of the source repository.

## Project structure

```text
src/com/weintern/students/
  Main.java           Console menu and input handling
  Student.java        Validated student model
  StudentManager.java CRUD, search, average, and persistence
class-diagram.puml    UML class diagram source
```
