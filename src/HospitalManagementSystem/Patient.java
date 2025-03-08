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
