import java.util.Scanner;
import java.util.InputMismatchException;

// 😛Main Class😛
public class StudentMS{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager(sc);

        while (true){
            System.out.println("\n--- STUDENT MANAGEMENT SYSTEM (BSCS 2-1) ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = 0;
            try{
                choice = sc.nextInt();
                sc.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println("Please enter a number for choice!");
                sc.nextLine();
                continue;
            }
            
            switch (choice){
                case 1 -> manager.add();
                case 2 -> manager.viewAll();
                case 3 -> manager.search();
                case 4 -> manager.update();
                case 5 -> manager.delete();
                case 6 -> {
                    System.out.println("Exiting system...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice po!");
            }
        }
    }
}
// 🤩Abstract Class🤩
abstract class Person{
    private String name;
    private int age;

    public Person(String name, int age){  // simple constructor
        this.name = name; // "this." keyword is used
        this.age = age;
    }

    //abstract method
    public abstract void describe(); // Polymorphism (over-🛵, haha corny)

    // Encapsulation🔒🔑 (public access modifiers), (getters and setters)
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
}

// 🤓Interface🤓
interface Manageable{ //contains abstract method/s
    void add();
    void viewAll();
    void search(); 
    void update();
    void delete();
}

// 👨‍🎓🏫 Class👨‍🎓🏫
class Student extends Person{ //inheritance (extends keyword)
    private String id; //private access modifier
    private final String section = "BSCS 2-1"; // 'final' a non-access modifier used to restrict modification

    private static int totalStudents = 0; 
    private static double[] grades = new double[50]; // in-limit ko man lang sir up to 50 students
    private int gradeIndex;

    public Student(String id, String name, int age){
        super(name, age);
        this.id = id;

        this.gradeIndex = totalStudents;
        grades[gradeIndex] = 0.0;
        totalStudents++;
    }

    @Override 
    public void describe(){
        System.out.println("ID: " + id + " | Name: " + getName() + " | Age: " + getAge() +
                           " | Course/Year/Block: " + section + " | Grade: " + grades[gradeIndex]);
    }

    // Encapsulation ulit (getters only)🔐
    public String getId(){ 
        return id; 
    }

    public String getSection(){ 
        return section;
    }

    // Grade handling (setters sa Grade)
    public void setGrade(double grade){ 
        grades[gradeIndex] = grade;
    }

    public double getGrade(){
        return grades[gradeIndex];
    }

    public static int getTotalStudents(){
        return totalStudents; 
    }
}

// ❤Student Manager Class❤
class StudentManager implements Manageable{
    private Student[] students = new Student[50];
    private int count = 0;
    private Scanner sc;

    public StudentManager(Scanner sc){
        this.sc = sc; 
    }

    @Override 
    public void add(){

        System.out.println("\n--- Add Student (BSCS 2-1 Only) ---");
        try{ 
            String id;
            while (true){
                System.out.print("Student ID: ");
                id = sc.nextLine();

                if (id.isBlank()){
                    System.out.println("Bawal po an blank lang! ompo.");
                } else {
                    break;
                }
            }

            String name;
            while (true){
                System.out.print("Name: ");
                name = sc.nextLine(); //may name po na merong number (stop discrimination sir)

                if (name.isBlank()){
                    System.out.println("Syempre may pangaran ka!");
                } else {
                    break;
                }
            }

            int age = 0;
            while (true){
                try{
                    System.out.print("Age: ");
                    age = sc.nextInt();
                    sc.nextLine();
                    break;
                }
                catch(InputMismatchException e){
                    System.out.println("Please enter a valid number for age!");
                    sc.nextLine(); 
                }
            }

            // Student is auto-assigned to BSCS 2-1
            Student s = new Student(id, name, age);
            students[count++] = s;

            // Input grade
            while (true){
                try{
                    System.out.print("GWA: ");
                    double grade = sc.nextDouble();
                    sc.nextLine();
                    s.setGrade(grade);
                    break;
                }
                catch(InputMismatchException e){
                    System.out.println("Please enter a valid number for grade!");
                    sc.nextLine();
                }
            }

            System.out.println("Student added successfully!");
        }
        catch(Exception e){
            System.out.println("Error adding student. Please try again.");
            sc.nextLine();
        }
    }

    @Override 
    public void viewAll(){
        System.out.println("\n--- Student List (BSCS 2-1) ---");
        if (count == 0){
            System.out.println("No students available.");
            return;
        }
        for (int i = 0; i < count; i++){
            students[i].describe();
        }
        System.out.println("\nTotal Students: " + Student.getTotalStudents());
    }

    @Override
    public void search(){
        String id;
        while (true){
            System.out.print("Enter student ID to search: ");
            id = sc.nextLine();

            if (id.isBlank()){
                System.out.println("Student ID cannot be blank!");
            } else {
                break;
            }
        }

        for (int i = 0; i < count; i++){
            if (students[i].getId().equals(id)){
                System.out.println("\nStudent found:");
                students[i].describe();
                return;
            }
        }
        System.out.println("Student not found");
    }

    @Override 
    public void update(){
        String id;
        while (true){
            System.out.print("Enter student ID to update: ");
            id = sc.nextLine();

            if (id.isBlank()){
                System.out.println("Student ID cannot be blank!");
            } else {
                break;
            }
        }

        for (int i = 0; i < count; i++){
            if (students[i].getId().equals(id)){

                String name;
                while (true){
                    System.out.print("New Name: ");
                    name = sc.nextLine();

                    if (name.isBlank()){
                        System.out.println("Name cannot be blank!");
                    } else {
                        break;
                    }
                }
                students[i].setName(name);

                int age = 0;
                while (true){
                    try{
                        System.out.print("New Age: ");
                        age = sc.nextInt();
                        sc.nextLine();
                        break;
                    }
                    catch(InputMismatchException e){
                        System.out.println("Please enter a valid number for age!");
                        sc.nextLine();
                    }
                }
                students[i].setAge(age);

                // NEW: Removed course + year editing ✔
                // Students stay in BSCS 2-1 permanently

                while (true){
                    try{
                        System.out.print("New Grade: ");
                        double grade = sc.nextDouble();
                        sc.nextLine();
                        students[i].setGrade(grade);
                        break;
                    }
                    catch(InputMismatchException e){
                        System.out.println("Please enter a valid number for grade!");
                        sc.nextLine();
                    }
                }

                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    @Override 
    public void delete(){
        String id;
        while (true){
            System.out.print("Enter student ID to delete: ");
            id = sc.nextLine();

            if (id.isBlank()){
                System.out.println("Student ID cannot be blank!");
            } else {
                break;
            }
        }

        for (int i = 0; i < count; i++){
            if (students[i].getId().equals(id)){
                for (int j = i; j < count - 1; j++){
                    students[j] = students[j + 1];
                }
                count--;
                System.out.println("Student deleted successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }
}