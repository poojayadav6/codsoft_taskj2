import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int filledSeats;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.filledSeats = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getFilledSeats() {
        return filledSeats;
    }

    public void setFilledSeats(int filledSeats) {
        this.filledSeats = filledSeats;
    }
}

class Student {
    private int id;
    private String name;
    private List<Course> registeredCourses;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
        course.setFilledSeats(course.getFilledSeats() + 1);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
        course.setFilledSeats(course.getFilledSeats() - 1);
    }
}

public class CourseRegistrationSystem {
    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        // Sample courses
        Course c1 = new Course("CSE101", "Introduction to Programming", "Introduction to Java programming language", 30, "Mon/Wed/Fri 10:00 AM - 11:30 AM");
        Course c2 = new Course("MAT202", "Linear Algebra", "Basic linear algebra concepts", 25, "Tue/Thu 1:00 PM - 2:30 PM");
        courses.add(c1);
        courses.add(c2);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Course Listing");
            System.out.println("2. Student Registration");
            System.out.println("3. Course Removal");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayCourseListing();
                    break;
                case 2:
                    performStudentRegistration(scanner);
                    break;
                case 3:
                    performCourseRemoval(scanner);
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void displayCourseListing() {
        System.out.println("\nCourse Listing:");
        for (Course course : courses) {
            System.out.println("Course Code: " + course.getCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Available Slots: " + (course.getCapacity() - course.getFilledSeats()));
            System.out.println();
        }
    }

    private static void performStudentRegistration(Scanner scanner) {
        System.out.print("\nEnter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        Student student = new Student(studentId, studentName);
        students.add(student);

        displayCourseListing();

        System.out.print("Enter course code to register: ");
        String courseCode = scanner.nextLine();

        Course selectedCourse = findCourse(courseCode);
        if (selectedCourse != null && selectedCourse.getFilledSeats() < selectedCourse.getCapacity()) {
            student.registerCourse(selectedCourse);
            System.out.println("Course registration successful.");
        } else {
            System.out.println("Course registration failed. Either course not found or no available slots.");
        }
    }

    private static void performCourseRemoval(Scanner scanner) {
        System.out.print("\nEnter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = findStudent(studentId);
        if (student != null) {
            List<Course> registeredCourses = student.getRegisteredCourses();
            if (!registeredCourses.isEmpty()) {
                System.out.println("Registered Courses:");
                for (int i = 0; i < registeredCourses.size(); i++) {
                    Course course = registeredCourses.get(i);
                    System.out.println((i + 1) + ". " + course.getTitle());
                }

                System.out.print("Enter course number to drop: ");
                int courseNumber = scanner.nextInt();
                if (courseNumber > 0 && courseNumber <= registeredCourses.size()) {
                    Course courseToDrop = registeredCourses.get(courseNumber - 1);
                    student.dropCourse(courseToDrop);
                    System.out.println("Course dropped successfully.");
                } else {
                    System.out.println("Invalid course number.");
                }
            } else {
                System.out.println("No registered courses found.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static Course findCourse(String code) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    private static Student findStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
}