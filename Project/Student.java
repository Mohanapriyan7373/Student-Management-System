package Project;
import java.io.Serializable;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private int age;
    private String course;
    private double marks;

    public Student(String id, String name, int age, String course, double marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.marks = marks;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    // Grade calculation
    public String getGrade() {
        if (marks >= 90) {
        	return "A";
        }
        if (marks >= 75) {
        	return "B";
        
        }
        if (marks >= 60) { 
        	return "C";
        }
        if (marks >= 50) {
        	return "D";
        }
        return "F";
        }

    public String toString() {
        return String.format("ID: %s | Name: %s | Age: %d | Course: %s | Marks: %.2f | Grade: %s",id, name, age, course, marks, getGrade());
    }
}
