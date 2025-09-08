package Project;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

class Main {
     static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String storagePath = "students.ser";
        StudentManagementSystem sms = new StudentManagementSystem(storagePath);
        Scanner scanner = new Scanner(System.in);

        AutoSaveThread autosave = new AutoSaveThread(sms,60_0000);
        autosave.start();

        boolean exit = false;
        while (!exit) {
            showMenu();
            String choice = scanner.nextLine().trim();
            System.out.println("DEBUG: choice='" + choice + "'");

            try {
                switch (choice) {
                    case "1": addStudent(sms); break;
                    case "2": updateStudent(sms); break;
                    case "3": deleteStudent(sms); break;
                    case "4": searchById(sms); break;
                    case "5": searchByName(sms); break;
                    case "6": listAll(sms); break;
                    case "7": sms.saveToFile(); System.out.println("Saved manually."); break;
                    case "8": sms.loadFromFile(); System.out.println("Loaded from file."); break;
                    case "9": exit = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.err.println("Exception caught: " + e);
                e.printStackTrace();
            }
        }

        try {
            sms.saveToFile();
            System.out.println("Data saved. Exiting...");
        } catch (IOException e) {
            System.out.println("Final save failed: " + e.getMessage());
        }
        autosave.terminate();
    }

    private static void showMenu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student by ID");
        System.out.println("3. Delete Student by ID");
        System.out.println("4. Search Student by ID");
        System.out.println("5. Search Students by Name");
        System.out.println("6. Display All Students");
        System.out.println("7. Save Now");
        System.out.println("8. Load From File");
        System.out.println("9. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addStudent(StudentManagementSystem sms) {
        try {
            System.out.print("Enter ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Course: ");
            String course = scanner.nextLine().trim();
            System.out.print("Enter Marks (0-100): ");
            double marks = Double.parseDouble(scanner.nextLine().trim());

            Student s = new Student(id, name, age, course, marks);
            sms.addStudent(s);
            System.out.println("Student added successfully.");
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format. " + e.getMessage());
        } catch (InvalidDataException e) {
            System.err.println("Invalid data: " + e.getMessage());
        }
    }

    private static void updateStudent(StudentManagementSystem sms) {
        try {
            System.out.print("Enter ID to update: ");
            String id = scanner.nextLine().trim();
            Student existing = sms.findStudentById(id);
            System.out.println("Existing: " + existing);

            System.out.print("Enter New Name (leave blank to keep): ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter New Age (leave blank to keep): ");
            String ageStr = scanner.nextLine().trim();
            System.out.print("Enter New Course (leave blank to keep): ");
            String course = scanner.nextLine().trim();
            System.out.print("Enter New Marks (leave blank to keep): ");
            String marksStr = scanner.nextLine().trim();

            String newName = name.isEmpty() ? existing.getName() : name;
            int newAge = ageStr.isEmpty() ? existing.getAge() : Integer.parseInt(ageStr);
            String newCourse = course.isEmpty() ? existing.getCourse() : course;
            double newMarks = marksStr.isEmpty() ? existing.getMarks() : Double.parseDouble(marksStr);
       Student updated = new Student(id, newName, newAge, newCourse, newMarks);
            sms.updateStudent(id, updated);
            System.out.println("Student updated.");
        } catch (StudentNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format.");
        } catch (InvalidDataException e) {
            System.err.println("Invalid data: " + e.getMessage());
        }
    }
private static void deleteStudent(StudentManagementSystem sms) {
        try {
            System.out.print("Enter ID to delete: ");
            String id = scanner.nextLine().trim();
            sms.deleteStudent(id);
            System.out.println("Student deleted.");
        } catch (StudentNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
private static void searchById(StudentManagementSystem sms) {
        try {
            System.out.print("Enter ID to search: ");
            String id = scanner.nextLine().trim();
            Student s = sms.findStudentById(id);
            System.out.println(s);
        } catch (StudentNotFoundException e) {
            System.err.println(e.getMessage());
        }}
   private static void searchByName(StudentManagementSystem sms) {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();
        List<Student> list = sms.findStudentsByName(name);
        if (list.isEmpty()) System.out.println("No students found.");
        else list.forEach(System.out::println);
    }

    private static void listAll(StudentManagementSystem sms) {
        List<Student> all = sms.getAllStudents();
        if (all.isEmpty()) System.out.println("No student records.");
        else all.forEach(System.out::println);
    }
}
