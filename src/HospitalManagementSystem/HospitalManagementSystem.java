package HospitalManagementSystem;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital"; 
    private static final String password = "SP16@mysql"; 
    private static final String username = "root"; 



    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner) {
        System.out.print("Enter Patient Id: ");
        int p_id=scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int d_id=scanner.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String a_date=scanner.next();
        if(doctor.getDoctorById(d_id) && patient.getPatientById(p_id)){
            if(checkDoctorAvaliability(d_id,a_date,connection)){
                            String appointquery="insert into appointments(patient_id ,doctor_id,appointment_date) values (?,?,?)";
                            try {
                                PreparedStatement preparedStatement =connection.prepareStatement(appointquery);
                                preparedStatement.setInt(1,p_id);
                                preparedStatement.setInt(2,d_id);
                                preparedStatement.setString(3,a_date);
                                int rowsAffected = preparedStatement.executeUpdate();
                                if(rowsAffected>0){
                                    System.out.println("------Appointment Booked------");
                                }else{
                                    System.out.println("Failed to book appointment!");
                                }
                            } catch (SQLException e) {
                                e.getStackTrace();
                            }
                        }
                    }
                    else{
                      System.out.println("Mismatch--Doctor is not avaliable");
                    }
                }
            
              
            
     public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection , scanner );
            Doctor doctor= new Doctor(connection);
            while(true){
                System.out.println("-------HOSPITAL DATABASE-------");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. Delete Patient");
                System.out.println("4. View Doctors");
                System.out.println("5. Book Appointments");
                System.out.println("6. Exit---");
                System.out.println("Enter your Choice: ");
                int choice= scanner.nextInt();
                switch(choice){
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // View Patient
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        // Delete patient
                        patient.deletePatient();
                        patient.resetId();
                        System.out.println();
                        break;
                    case 4:
                        // View Doctors
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 5:
                        // Book Appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                        break;
                }
                
            }
        }catch(SQLException e){
            e.printStackTrace();

        }
    }


    public static boolean checkDoctorAvaliability(int d_id, String a_date ,Connection connection) {
        String query ="select count (*) from appointments where doctor_id =? and appointment_date=?" ;
        try {
             PreparedStatement preparedStatement =connection.prepareStatement(query);
            preparedStatement.setInt(1,d_id);
            preparedStatement.setString(2,a_date);
            ResultSet resultSet =preparedStatement.executeQuery();
            if(resultSet.next()){
            int count =resultSet.getInt(1);
                if(count==0){
                    return true;
                }
                 else{
                    return false;
                }
        }
            
        }catch (SQLException e) {
            e.getStackTrace();
        }
        return false;
       
    }
}
    
