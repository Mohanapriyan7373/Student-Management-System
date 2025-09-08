package Project;
import java.io.*;
import java.util.*;

class StudentManagementSystem implements StudentOperations {
    private final Map<String, Student> students = new HashMap<>();
    private final File storageFile;

    public StudentManagementSystem(String storageFilePath) 
    {
        this.storageFile = new File(storageFilePath);
        try {
            if (storageFile.exists()) {
            	loadFromFile();}
        } catch (Exception e) {
        	
            System.out.println("Warning: could not load existing data - " + e.getMessage());
        }
    }

    private void validateStudent(Student s) throws InvalidDataException
    {
        if (s == null) throw new InvalidDataException("Student is null");
        if (s.getId() == null || s.getId().isEmpty()) throw new InvalidDataException("ID required");
        if (s.getName() == null || s.getName().isEmpty()) throw new InvalidDataException("Name required");
        if (s.getAge() <= 0) throw new InvalidDataException("Age must be positive");
        if (s.getMarks() < 0 || s.getMarks() > 100) throw new InvalidDataException("Marks must be between 0-100");
    }

    //Override addStudent
    public synchronized void addStudent(Student s) throws InvalidDataException {
        validateStudent(s);
        if (students.containsKey(s.getId())) {
            throw new InvalidDataException("Student with this ID already exists");
        }
        students.put(s.getId(), s);
    }

    //Override updateStudent
    public synchronized void updateStudent(String id, Student updated) throws StudentNotFoundException, InvalidDataException {
        if (!students.containsKey(id)) throw new StudentNotFoundException("Student not found");
        validateStudent(updated);
        updated.setId(id);
        students.put(id, updated);
    }

    //overridedaleteStudent
    public synchronized void deleteStudent(String id) throws StudentNotFoundException {
        if (students.remove(id) == null) throw new StudentNotFoundException("Student not found");
    }

    //Override findStudent
    public synchronized Student findStudentById(String id) throws StudentNotFoundException {
        Student s = students.get(id);
        if (s == null) throw new StudentNotFoundException("Student not found");
        return s;
    }

    //Override findStudentsByName
    public synchronized List<Student> findStudentsByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student s : students.values()) {
            if (s.getName().toLowerCase().contains(name.toLowerCase())) 
            	result.add(s);
        }
        return result;
    }

    //OverridegetAllStudent
    public synchronized List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    //OverridesaveToFil
    public synchronized void saveToFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(students);
        }
    }
    //Override loadFromFile
    public synchronized void loadFromFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                students.clear();
                students.putAll((Map<String, Student>) obj);
            }
        }
    }
}
