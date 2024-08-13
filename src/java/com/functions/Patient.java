package com.functions;

import com.database.DatabaseConnector;
import com.database.Handler;

import java.sql.*;
import java.util.*;

public class Patient extends Person {
    private String gender;
    private String symptoms;
    private String paymentMethod;
    private String diagnosis;
    private boolean emergency;
    private int roomNumber;
    private static int lastId = loadLastId();
    private final int ID = lastId++;
    private static Set<Patient> patients = new HashSet<>();

    static {
        patients = loadToHashSet(); // Load from database after initialization
        Map<Integer, Patient> patientMap = Handler.loadPatients();
    }


    public Patient(String name, String address, String phoneNumber, String gender, String symptoms, String paymentMethod, String diagnosis, boolean emergency, int roomNumber) {
        super(name, address, phoneNumber);
        this.gender = gender;
        this.symptoms = symptoms;
        this.paymentMethod = paymentMethod;
        this.diagnosis = diagnosis;
        this.emergency = emergency;
        this.roomNumber = roomNumber;
        addPatient(this);
    }

    public Patient() {
    }

    private void addPatient(Patient patient) {
        patients.add(patient);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public int getID() {
        return ID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }


    public static boolean save() {
        DatabaseConnector.connect();
        String checkQuery = "SELECT COUNT(*) FROM patients WHERE phoneNumber = ?";
        String sql = "INSERT INTO patients (idpatients, name, address, phoneNumber, gender, symptoms, paymentMethod, diagno, emergency, roomNumber)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql);
             PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {

            for (Patient patient : patients) {
                // Check for duplicates
                checkStmt.setString(1, patient.phoneNumber);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    System.out.println("Record already exists for phone number: " + patient.phoneNumber);
                    continue; // Skip saving this patient
                }

                // Save patient if no duplicates
                preparedStmt.setInt(1, patient.getID());
                preparedStmt.setString(2, patient.name);
                preparedStmt.setString(3, patient.address);
                preparedStmt.setString(4, patient.phoneNumber);
                preparedStmt.setString(5, patient.getGender());
                preparedStmt.setString(6, patient.getSymptoms());
                preparedStmt.setString(7, patient.getPaymentMethod());
                preparedStmt.setString(8, patient.getDiagnosis());
                preparedStmt.setBoolean(9, patient.isEmergency());
                preparedStmt.setInt(10, patient.getRoomNumber());
                preparedStmt.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    public boolean delete(Patient patient) {
        DatabaseConnector.connect();
        String query = "SELECT * FROM patients";
        String qry = "DELETE FROM patients WHERE idpatients= '%s'".formatted(patient.getID());
        try (var connection = DatabaseConnector.connection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(qry);
        ) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if (resultSet.getString("phoneNumber").equals(patient.phoneNumber)) {
                    System.out.println("Record deleted");
                    preparedStatement.execute();
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
                lastId = rs.getInt("idDoctors") +1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    private static HashSet<Patient> loadToHashSet(){ return new HashSet<>(Handler.loadPatients().values()); }

    @Override
    public String toString() {
        return "Patient{[%d] %s %s %s %s %s %s %s %b %d}".formatted(getID(), name, address, phoneNumber, gender, symptoms, paymentMethod, diagnosis, emergency, roomNumber);
    }
}
