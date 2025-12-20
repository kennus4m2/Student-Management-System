/*
    Name: Nickson M. Formento
    Course/Year/Block: BSCS 2-1
    email: formentonickson@gmail.com
    github: kennus4m2
    Description: A simple Student Management System for BSCS 2-1 students only.
*/

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
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}

// 🤩Abstract Class🤩
abstract class Person{
    private String name;
    private int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public abstract void describe();

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
interface Manageable{
    void add();
    void viewAll();
    void search();
    void update();
    void delete();
}

// 👨‍🎓🏫 Student Class👨‍🎓🏫
class Student extends Person{
    private String id;
    private final String section = "BSCS 2-1";

    private static int totalStudents = 0;
    private static float[] grades = new float[50]; //float na iyan
    private int gradeIndex;

    public Student(String id, String name, int age){
        super(name, age);
        this.id = id;

        this.gradeIndex = totalStudents;
        grades[gradeIndex] = 0.0f;
        totalStudents++;
    }

    @Override
    public void describe(){
        System.out.println("ID: " + id + " | Name: " + getName() + " | Age: " + getAge() +
            " | Course/Year/Block: " + section + " | Grade: " + grades[gradeIndex]
        );
    }

    public String getId(){ 
        return id; 
    }
    public void setGrade(float grade){ 
        grades[gradeIndex] = grade; 
    }
    public float getGrade(){ 
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

    // ID (mandatory)
    String id;
    while(true){
        System.out.print("Student ID: ");
        id = sc.nextLine();
        if(!id.isBlank()) break;
        System.out.println("Student ID cannot be blank!");
    }

    // Name (mandatory)
    String name;
    while(true){
        System.out.print("Name: ");
        name = sc.nextLine();
        if(!name.isBlank()) break;
        System.out.println("Name cannot be blank!");
    }

    // Age (mandatory, validated, dynamic po)
    int age;
    while(true){
        System.out.print("Age (15-100): ");
        String ageInput = sc.nextLine();
        try{
            age = Integer.parseInt(ageInput);
            if(age < 15 || age > 100){
                System.out.println("Invalid age! Enter 15-100 only.");
            } else {
                break;
            }
        }
        catch(NumberFormatException e){
            System.out.println("Please enter a valid number for age!");
        }
    }

    // GWA (mandatory, validated, dynamic rin
    float grade;
    while(true){
        System.out.print("GWA (1.0-5.0): ");
        String gradeInput = sc.nextLine();
        try{
            grade = Float.parseFloat(gradeInput);
            if(grade < 1.0f || grade > 5.0f){
                System.out.println("Invalid GWA! Enter 1.0-5.0 only.");
            } else {
                break;
            }
        }
        catch(NumberFormatException e){
            System.out.println("Please enter a valid number for GWA!");
        }
    }

    // Final confirmation before saving
    System.out.println("\n--- Review Student Info ---");
    System.out.println("ID: " + id);
    System.out.println("Name: " + name);
    System.out.println("Age: " + age);
    System.out.println("GWA: " + grade);

    String confirm;
    while(true){
        System.out.print("Do you want to save this student? [yes/no]: ");
        confirm = sc.nextLine().trim().toLowerCase();
        if(confirm.equals("yes") || confirm.equals("no")) break;
        System.out.println("Invalid input! Type 'yes' or 'no'.");
    }

    if(confirm.equals("yes")){
        Student s = new Student(id, name, age);
        s.setGrade(grade);
        students[count++] = s;
        System.out.println("Student added successfully!");
    } else {
        System.out.println("Add student cancelled.");
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
        System.out.print("Enter student ID to search: ");
        String id = sc.nextLine();

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
    System.out.print("Enter student ID to update: ");
    String id = sc.nextLine();

    for (int i = 0; i < count; i++){
        if (students[i].getId().equals(id)){
            Student s = students[i];

            // Temporary variables to hold new info
            String tempName = s.getName();
            int tempAge = s.getAge();
            float tempGrade = s.getGrade();

            // Show menu for which field to update
            System.out.println("\nWhat do you want to update?");
            System.out.println("1. Name");
            System.out.println("2. Age");
            System.out.println("3. Grade");
            System.out.println("4. Cancel");
            System.out.print("Choice: ");

            int choice;
            try{
                choice = sc.nextInt();
                sc.nextLine();
            } catch(InputMismatchException e){
                System.out.println("Invalid input!");
                sc.nextLine();
                return;
            }

            switch (choice){
                case 1 -> {
                    System.out.print("New Name (Current: " + s.getName() + ") leave blank to keep: ");
                    String newName = sc.nextLine();
                    if (!newName.isBlank()) tempName = newName;
                }
                case 2 -> {
                    while(true){
                        System.out.print("New Age (15-100, Current: " + s.getAge() + ") leave blank to keep: ");
                        String ageInput = sc.nextLine();
                        if(ageInput.isBlank()) break; // keep current age
                        try{
                            int newAge = Integer.parseInt(ageInput);
                            if(newAge < 15 || newAge > 100){
                                System.out.println("Invalid age! Enter 15-100 only.");
                            } else {
                                tempAge = newAge;
                                break;
                            }
                        } catch(NumberFormatException e){
                            System.out.println("Invalid input! Enter a number.");
                        }
                    }
                }
                case 3 -> {
                    while(true){
                        System.out.print("New GWA (1.0-5.0, Current: " + s.getGrade() + ") leave blank to keep: ");
                        String gradeInput = sc.nextLine();
                        if(gradeInput.isBlank()) break; // keep current grade
                        try{
                            float newGrade = Float.parseFloat(gradeInput);
                            if(newGrade < 1.0f || newGrade > 5.0f){
                                System.out.println("Invalid GWA! Enter 1.0-5.0 only.");
                            } else {
                                tempGrade = newGrade;
                                break;
                            }
                        } catch(NumberFormatException e){
                            System.out.println("Invalid input! Enter a number.");
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Update cancelled.");
                    return;
                }
                default -> {
                    System.out.println("Invalid option!");
                    return;
                }
            }

            // Final confirmation before saving changes
            System.out.println("\n--- Review Changes ---");
            System.out.println("Name: " + tempName);
            System.out.println("Age: " + tempAge);
            System.out.println("GWA: " + tempGrade);

            String confirm;
            while(true){
                System.out.print("Do you want to save these changes? [yes/no]: ");
                confirm = sc.nextLine().trim().toLowerCase();
                if(confirm.equals("yes") || confirm.equals("no")) break;
                System.out.println("Invalid input! Type 'yes' or 'no'.");
            }

            if(confirm.equals("yes")){
                s.setName(tempName);
                s.setAge(tempAge);
                s.setGrade(tempGrade);
                System.out.println("Changes saved successfully!");
            } else {
                System.out.println("Update cancelled. No changes applied.");
            }

            return; // exit after update attempt
        }
    }
    System.out.println("Student not found.");
}

    @Override
    public void delete(){
        System.out.print("Enter student ID to delete: ");
        String id = sc.nextLine();

        for (int i = 0; i < count; i++){
            if (students[i].getId().equals(id)){
                // Show student info before deleting
                System.out.println("\nStudent info to be deleted:");
                students[i].describe();

                // Final confirmation before deleting
                String confirm;
                while(true){
                    System.out.print("Are you sure you want to delete this student? [yes/no]: ");
                    confirm = sc.nextLine().trim().toLowerCase();
                    if(confirm.equals("yes") || confirm.equals("no")) break;
                    System.out.println("Invalid input! Type 'yes' or 'no'.");
                }

                if(confirm.equals("yes")){
                    for (int j = i; j < count - 1; j++){
                        students[j] = students[j + 1];
                    }
                    count--;
                    System.out.println("Student deleted successfully!");
                } else {
                    System.out.println("Deletion cancelled. No changes applied.");
                }

                return;
            }
        }
        System.out.println("Student not found.");
    }
}