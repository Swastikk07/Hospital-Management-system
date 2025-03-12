package HospitalManagementSystem;
import java.sql.*;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;

    public Patient(Connection connection, Scanner scanner) {
        this.connection=connection;
        this.sc=scanner;
    }

    //add patient function
    public void addPatient() {
        System.out.println("Enter Patient Name: ");
        sc.nextLine();  
        String name = sc.nextLine();  
        System.out.println("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = sc.next();
        try {
            String query = "insert into patients(name, age, gender) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("----Patient Added Successfully----");
            } else {
                System.out.println("Failed to add Patient!!");
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    //view patient function
    public void viewPatient() {
    String query = "SELECT * FROM patients;";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        System.out.println("Patients: ");
        System.out.println("+------------+------------------------+----------+------------+");
        System.out.println("| Patient Id | Name                   | Age      | Gender     |");
        System.out.println("+------------+------------------------+----------+------------+");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String gender = rs.getString("gender");
            // Use %d for age since it's an integer
            System.out.printf("| %-10d | %-22s | %-8d | %-10s |\n", id, name, age, gender);
            System.out.println("+------------+------------------------+----------+------------+");
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    //delete patient from database
    public void deletePatient() {
        System.out.println("Enter Patient ID to delete: ");
        int patientId = sc.nextInt(); // Getting the ID of the patient to delete
        
        try {
            String query = "DELETE FROM patients WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("----Patient Deleted Successfully----");
            } else {
                System.out.println("No patient found with the given ID!");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    //reset id
    public void resetId() {
        try {
            // SQL query to reset the auto-increment counter
            String resetQuery = "ALTER TABLE patients AUTO_INCREMENT = 1";
            PreparedStatement resetStatement = connection.prepareStatement(resetQuery);
            resetStatement.executeUpdate();
            System.out.println("Auto-increment value has been reset to 1.");
            resetStatement.close(); 
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }
        
    
    
    public boolean getPatientById(int id){
        String query = "select * from patients where id = ?";
        try{
            PreparedStatement preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs =preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
            else{
                return false;
            }
        }catch(SQLException e){
            e.getStackTrace();
        }
        return false;
    }
}


