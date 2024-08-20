package com.functions;

import com.database.DatabaseConnector;
import com.database.Handler;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Doctor extends Person{

    private String specialty;
    private static int lastId;
    private final int ID;
    private static HashSet<Doctor> doctors = new HashSet<>();
    private static Map<Integer, Doctor> doctorMap = new HashMap<>();
    static {
        lastId = loadLastId();
        doctorMap = Handler.loadDoctors();
        doctors = loadToHashSet(); // Load from database after initialization
    }
    public Doctor(String name, String address, String phoneNumber, String specialty) {
        super(name, address, phoneNumber);
        this.specialty = specialty;
        this.ID = ++lastId;
        addDoctor(this);
    }
    public Doctor(int ID, String name, String address, String phoneNumber, String specialty) {
        super(name, address, phoneNumber);
            this.specialty = specialty;
            this.ID = ID;
            addDoctor(this);
    }
    public static void updateSet(ObservableList<Doctor> newDoctors){
        doctors.clear();
        doctors.addAll(newDoctors);
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

    public static boolean save(Doctor doctor) {
        DatabaseConnector.connect();
        String sql = "UPDATE doctors SET name = ?, address = ?, phoneNumber = ?, specialty = ? WHERE idDoctors = ?";
        try (var connection = DatabaseConnector.connection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);) {
                // Save doctor if no duplicates
                preparedStmt.setString(1, doctor.getName());
                preparedStmt.setString(2, doctor.getAddress());
                preparedStmt.setString(3, doctor.getPhoneNumber());
                preparedStmt.setString(4, doctor.getSpecialty());
                preparedStmt.setInt(5, doctor.getID());
                preparedStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
//    public static Boolean save(Doctor doctor){
//        if (delete(doctor) && add(doctor)){
//            System.out.println("Save Successful");
//            return true;
//        }
//        else    {
//            System.out.println("Save Failed");
//            return false;
//        }
//
//    }
    public static boolean delete(Doctor doctor) {
        DatabaseConnector.connect();
        String qry = "DELETE FROM doctors WHERE idDoctors= '%s'".formatted(doctor.getID());
        try (var connection = DatabaseConnector.connection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(qry);
        ) {
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean add(Doctor doctor) {
        DatabaseConnector.connect();
        String sql = "INSERT INTO doctors (idDoctors, name, address, phoneNumber, specialty) VALUES (?, ?, ?, ?, ?)";
        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql);) {
                // Save doctor if no duplicates
                preparedStmt.setInt(1, doctor.getID());
                preparedStmt.setString(2, doctor.getName());
                preparedStmt.setString(3, doctor.getAddress());
                preparedStmt.setString(4, doctor.getPhoneNumber());
                preparedStmt.setString(5, doctor.getSpecialty());
                preparedStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
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
    public static HashSet<Doctor> loadDoctors(){return doctors;}
    public static HashSet<Doctor> loadToHashSet(){
        return new HashSet<>(doctorMap.values());
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    @Override
    public String toString() {
        return "[%d] %s %s %s %s".formatted(getID(), name, address, phoneNumber, specialty);
    }
}