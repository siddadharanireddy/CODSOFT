import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

class Course {
    String code;
    String title;
    String description;
    int capacity;
    String schedule;
    int enrolled;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolled = 0;
    }

    public boolean hasAvailableSlots() {
        return enrolled < capacity;
    }

    public void enrollStudent() {
        if (hasAvailableSlots()) {
            enrolled++;
        }
    }

    public void dropStudent() {
        if (enrolled > 0) {
            enrolled--;
        }
    }

    @Override
    public String toString() {
        return code + " - " + title + " (" + enrolled + "/" + capacity + " slots filled)\nSchedule: " + schedule;
    }
}

class Student {
    String id;
    String name;
    ArrayList<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.enrollStudent();
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
        }
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

public class CourseManagementApp extends JFrame implements ActionListener {
    HashMap<String, Course> courseDatabase = new HashMap<>();
    HashMap<String, Student> studentDatabase = new HashMap<>();

    // Swing components
    JComboBox<String> studentDropdown;
    JComboBox<String> courseDropdown;
    JTextArea courseDetailsArea;
    JButton registerButton;
    JButton dropButton;

    public CourseManagementApp() {
        setTitle("Course Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Sample data
        initializeData();

        // Swing components
        studentDropdown = new JComboBox<>();
        courseDropdown = new JComboBox<>();
        courseDetailsArea = new JTextArea(5, 40);
        courseDetailsArea.setEditable(false);
        registerButton = new JButton("Register");
        dropButton = new JButton("Drop");

        // Populate dropdowns
        updateStudentDropdown();
        updateCourseDropdown();

        // Add components to frame
        add(new JLabel("Select Student:"));
        add(studentDropdown);
        add(new JLabel("Select Course:"));
        add(courseDropdown);
        add(new JScrollPane(courseDetailsArea));
        add(registerButton);
        add(dropButton);

        // Add action listeners
        courseDropdown.addActionListener(this);
        registerButton.addActionListener(this);
        dropButton.addActionListener(this);
    }

    private void initializeData() {
        // Sample courses
        courseDatabase.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basics of computer science.", 30, "Mon-Wed-Fri 9:00-10:00 AM"));
        courseDatabase.put("CS102", new Course("CS102", "Data Structures", "Learn about data structures.", 25, "Tue-Thu 11:00-12:30 PM"));
        courseDatabase.put("CS103", new Course("CS103", "Algorithms", "Introduction to algorithms.", 20, "Mon-Wed 2:00-3:30 PM"));

        // Sample students
        studentDatabase.put("S001", new Student("S001", "Alice"));
        studentDatabase.put("S002", new Student("S002", "Bob"));
        studentDatabase.put("S003", new Student("S003", "Charlie"));
    }

    private void updateStudentDropdown() {
        studentDropdown.removeAllItems();
        for (String studentId : studentDatabase.keySet()) {
            studentDropdown.addItem(studentId + " - " + studentDatabase.get(studentId).name);
        }
    }

    private void updateCourseDropdown() {
        courseDropdown.removeAllItems();
        for (String courseCode : courseDatabase.keySet()) {
            courseDropdown.addItem(courseCode);
        }
    }

    private void displayCourseDetails() {
        String selectedCourseCode = (String) courseDropdown.getSelectedItem();
        if (selectedCourseCode != null && courseDatabase.containsKey(selectedCourseCode)) {
            Course course = courseDatabase.get(selectedCourseCode);
            courseDetailsArea.setText(course.toString());
        }
    }

    private void registerStudentForCourse() {
        String selectedStudentInfo = (String) studentDropdown.getSelectedItem();
        String selectedCourseCode = (String) courseDropdown.getSelectedItem();

        if (selectedStudentInfo != null && selectedCourseCode != null) {
            String studentId = selectedStudentInfo.split(" - ")[0];
            Student student = studentDatabase.get(studentId);
            Course course = courseDatabase.get(selectedCourseCode);

            if (course.hasAvailableSlots()) {
                student.registerCourse(course);
                JOptionPane.showMessageDialog(this, "Successfully registered for " + course.title);
            } else {
                JOptionPane.showMessageDialog(this, "Course is full!");
            }
            displayCourseDetails();
        }
    }

    private void dropStudentFromCourse() {
        String selectedStudentInfo = (String) studentDropdown.getSelectedItem();
        String selectedCourseCode = (String) courseDropdown.getSelectedItem();

        if (selectedStudentInfo != null && selectedCourseCode != null) {
            String studentId = selectedStudentInfo.split(" - ")[0];
            Student student = studentDatabase.get(studentId);
            Course course = courseDatabase.get(selectedCourseCode);

            student.dropCourse(course);
            JOptionPane.showMessageDialog(this, "Successfully dropped " + course.title);
            displayCourseDetails();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == courseDropdown) {
            displayCourseDetails();
        } else if (e.getSource() == registerButton) {
            registerStudentForCourse();
        } else if (e.getSource() == dropButton) {
            dropStudentFromCourse();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CourseManagementApp().setVisible(true);
        });
    }
}
