package HospitalManagementSystem;
import java.sql.*;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection=connection;
    }


    //view patient function
    public void viewDoctor(){
        String query = "select * from doctors;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs =preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Doctor Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(rs.next()){
                int id = rs.getInt("id");
                String name =rs.getString("name");
                String specialization =rs.getString("specialization");
                System.out.printf("| %-12s | %-20s | %-18s |\n", id, name,specialization);
                System.out.println("+------------+--------------------+----------+------------+");
            }
        }catch(SQLException e){
            e.getStackTrace();
        }
    }


    public boolean getDoctorById(int id){
        String query = "Select * from patients where id = ?";
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