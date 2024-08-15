package com.functions;

import com.database.DatabaseConnector;
import com.database.Handler;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Doctor extends Person{

    private String specialty;
    private static int lastId = loadLastId();
    private final int ID = lastId++;
    private static HashSet<Doctor> doctors = new HashSet<>();
    private static Map<Integer, Doctor> doctorHashMap;
    static {
    doctors = loadToHashSet(); // Load from database after initialization
         doctorHashMap = Handler.loadDoctors();
}

    public Doctor(String name, String address, String phoneNumber, String specialty) {
        super(name, address, phoneNumber);
        synchronized (Person.class){
        this.specialty = specialty;
        addDoctor(this);
        }
    }

    public Doctor() {
        super();
    }

    public String getSpecialty() {
        return specialty;
    }


    public int getID() {
        return ID;
    }

    public static Set<Doctor> getDoctors() {
        return doctors;
    }

    private void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void EditDoctor(String newName, String newAddress, String newPhoneNumber, String newSpecialty) {
        // Retrieve the doctor object from the map using the provided doctorId


            // Update fields only if new values are provided
            this.name = (newName != null) ? newName : this.name;
            this.address = (newAddress != null) ? newAddress : this.address;
            this.phoneNumber = (newPhoneNumber != null) ? newPhoneNumber : this.phoneNumber;
            this.specialty = (newSpecialty != null) ? newSpecialty : this.specialty;

    }

    public static boolean save() {
        DatabaseConnector.connect();
        String sql = "INSERT INTO doctors (idDoctors, name, address, phoneNumber, specialty) VALUES (?, ?, ?, ?, ?)";
        String checkQuery = "SELECT COUNT(*) FROM doctors WHERE phoneNumber = ?";
        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql);
             PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {

            for (var doctor : doctors) {
                // Check for duplicates
                checkStmt.setString(1, doctor.phoneNumber);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    System.out.println("Record already exists");
                    continue; // Skip saving this doctor
                }

                // Save doctor if no duplicates
                preparedStmt.setInt(1, doctor.getID());
                preparedStmt.setString(2, doctor.name);
                preparedStmt.setString(3, doctor.address);
                preparedStmt.setString(4, doctor.phoneNumber);
                preparedStmt.setString(5, doctor.getSpecialty());
                preparedStmt.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }



    public static boolean delete(Doctor doctor) {
        DatabaseConnector.connect();
        String query = "SELECT * FROM doctors";
        String qry = "DELETE FROM doctors WHERE idDoctors= '%s'".formatted(doctor.getID());
        try (var connection = DatabaseConnector.connection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(qry);
        ) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if (resultSet.getString("phoneNumber").equals(doctor.phoneNumber)) {
                    preparedStatement.execute();
                    System.out.println("Record deleted");
                    return true;
                }
            }
            System.out.println("record doesn't exist");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    private static int loadLastId() {
        DatabaseConnector.connect();
        int lastId = 1; // Default value if loading fails
        String query = "SELECT MAX(idDoctors) AS idDoctors FROM doctors";
        try (Connection conn = DatabaseConnector.connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    lastId = rs.getInt("idDoctors");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    public static HashSet<Doctor> loadToHashSet(){
        return new HashSet<>(Handler.loadDoctors().values());
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "[%d] %s %s %s %s".formatted(getID(), name, address, phoneNumber, specialty);
    }
}