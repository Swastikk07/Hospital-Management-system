package HospitalManagementSystem;
import java.sql.*;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;

    public Patient(Connection connection, Scanner scanner) {
        this.connection=connection;
        this.sc=sc;
    }

    //add patient function
    public void addPatient() {
        System.out.println("Enter Patient Name: ");
        String name = sc.next();
        System.out.println("Enter Patient Age: ");
        String age = sc.next();
        System.out.println("Enter Patient Gender: ");
        String gender = sc.next();

        try {
            String query = "insert into patients(name, age, gender) values(? ? ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, age);
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
    public void viewPatient(){
        String query = "insert into patients(name, age, gender) values(? ? ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs =preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(rs.next()){
                int id = rs.getInt("id");
                String name =rs.getString("name");
                int age = rs.getInt("age");
                String gender =rs.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
        }catch(SQLException e){
            e.getStackTrace();
        }
    }
}
