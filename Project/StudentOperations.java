package Project;
import java.io.IOException;
import java.util.List;

interface StudentOperations {
    void addStudent(Student s) throws InvalidDataException;
    void updateStudent(String id, Student updated) throws StudentNotFoundException, InvalidDataException;
    void deleteStudent(String id) throws StudentNotFoundException;
    Student findStudentById(String id) throws StudentNotFoundException;
    List<Student> findStudentsByName(String name);
    List<Student> getAllStudents();
    void saveToFile() throws IOException;
    void loadFromFile() throws IOException, ClassNotFoundException;
}


