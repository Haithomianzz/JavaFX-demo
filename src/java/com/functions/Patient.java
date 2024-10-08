package com.functions;

import com.database.DatabaseConnector;
import com.database.Handler;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;

public abstract class Patient extends Person {
    private String gender;
    private String symptoms;
    private String paymentMethod;
    private String diagnosis;
    private boolean emergency;
    private String roomNumber = "N/A";
    private int doctorInCharge;
    private static int lastId;
    private final int ID;
    private static HashSet<Patient> patients = new HashSet<>();
    private static Map<Integer, Patient> patientsMap= new HashMap<>();
    static {
        lastId = loadLastId();
        patientsMap= Handler.loadPatients();
        patients = loadToHashSet(); // Load from database after initialization

    }
    public Patient(String name, String address, String phoneNumber, String gender,
                   String symptoms, String paymentMethod, boolean emergency, int doctorInCharge) {
        super(name, address, phoneNumber);
        this.gender = gender;
        this.symptoms = symptoms;
        this.paymentMethod = paymentMethod;
        this.emergency = emergency;
        this.ID = ++lastId;
        this.doctorInCharge = doctorInCharge;
        addPatient(this);
    }
    public Patient(int ID,String name, String address, String phoneNumber, String gender,
                   String symptoms, String paymentMethod, boolean emergency,int doctorInCharge) {
        super(name, address, phoneNumber);
        this.gender = gender;
        this.symptoms = symptoms;
        this.paymentMethod = paymentMethod;
        this.emergency = emergency;
        this.ID = ID;
        this.doctorInCharge = doctorInCharge;
        addPatient(this);
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
//    public void setRoomNumber(){}
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
    public void setRoomNumber(Integer roomNumber) {
        if (roomNumber == null || roomNumber == 0)
            this.roomNumber = "N/A";
        else
            this.roomNumber = String.valueOf(roomNumber);
    }
    public void setDoctorInCharge(int doctorInCharge) {
        this.doctorInCharge = doctorInCharge;
    }
    public int getDoctorInCharge(){
        return doctorInCharge;
    }
    public static void updateSet(ObservableList<Patient> newPatients){
        patients.clear();
        patients.addAll(newPatients);
    }
    public void EditPatient(String newName, String newAddress, String newPhoneNumber, String newGender,
                             String newSymptoms, String newPaymentMethod,String newRoomNumber, Boolean newEmergency, Integer doctorInCharge) {
        // Update fields only if new values are provided
        this.name = (newName != null) ? newName : this.name;
        this.address = (newAddress != null) ? newAddress : this.address;
        this.phoneNumber = (newPhoneNumber != null) ? newPhoneNumber : this.phoneNumber;
        this.gender = (newGender != null) ? newGender : this.gender;
        this.symptoms = (newSymptoms != null) ? newSymptoms : this.symptoms;
        this.paymentMethod = (newPaymentMethod != null) ? newPaymentMethod : this.paymentMethod;
        this.emergency = (newEmergency != null) ? newEmergency : this.emergency;
        if(newEmergency)
            this.roomNumber = (newRoomNumber != null) ? newRoomNumber : this.roomNumber;
        else
            this.roomNumber = "N/A";
//            this.doctorInCharge = (newDoctorInCharge != null) ? newDoctorInCharge : this.doctorInCharge;
        this.doctorInCharge = (doctorInCharge != null) ? doctorInCharge : this.doctorInCharge;
    }
    public static boolean save(Patient patient) {
        DatabaseConnector.connect();
        String sql = "UPDATE patients SET name = ?, `address` = ?, `phoneNumber` = ?, `gender` = ?, `symptoms` = ?, `paymentMethod` = ?, `diagno` = ?, `emergency` = ?, `roomNumber` = ?, `doctorid` = ? WHERE (`idpatients` = ?);";
        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql);) {
                // Save patient if it exists
                preparedStmt.setString(1, patient.getName());
                preparedStmt.setString(2, patient.getAddress());
                preparedStmt.setString(3, patient.getPhoneNumber());
                preparedStmt.setString(4, patient.getGender());
                preparedStmt.setString(5, patient.getSymptoms());
                preparedStmt.setString(6, patient.getPaymentMethod());
                preparedStmt.setString(7, patient.getDiagnosis());
                if (patient.isEmergency()){
                    preparedStmt.setInt(8, 1);
                    preparedStmt.setInt(9, Integer.parseInt(patient.getRoomNumber()));
                }
                else{
                    preparedStmt.setInt(8, 0);
                    preparedStmt.setInt(9, 0);
                }
                preparedStmt.setInt(10, patient.getDoctorInCharge());
                preparedStmt.setInt(11, patient.getID());
                preparedStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean delete(Patient patient) {
        patients.remove(patient);
        for (Appointment A : Appointment.loadAppointments())
            if (A.getPatientId() == patient.getID())
                Appointment.delete(A);

        DatabaseConnector.connect();
        String qry = "DELETE FROM patients WHERE idpatients= '%s'".formatted(patient.getID());
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
    public static boolean add(Patient patient) {
        patients.add(patient);
        DatabaseConnector.connect();
        String sql = "INSERT INTO patients (idpatients, name, address, phoneNumber, gender, symptoms, paymentMethod, diagno, emergency, roomNumber, doctorid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql); ){
                // Save patient if no duplicates
                preparedStmt.setInt(1, patient.getID());
                preparedStmt.setString(2, patient.getName());
                preparedStmt.setString(3, patient.getAddress());
                preparedStmt.setString(4, patient.getPhoneNumber());
                preparedStmt.setString(5, patient.getGender());
                preparedStmt.setString(6, patient.getSymptoms());
                preparedStmt.setString(7, patient.getPaymentMethod());
                preparedStmt.setString(8, patient.getDiagnosis());
                if (patient.isEmergency()){
                    preparedStmt.setInt(9, 1);
                    preparedStmt.setInt(10, Integer.parseInt(patient.getRoomNumber()));
                }
                else{
                    preparedStmt.setInt(9, 0);
                    preparedStmt.setInt(10, 0);
                }
                preparedStmt.setInt(11, patient.getDoctorInCharge());
                preparedStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static boolean diagnose(Patient patient) {
        DatabaseConnector.connect();
        String query = String.format("UPDATE patients SET diagno = '%s' WHERE idpatients = '%d';", patient.getDiagnosis(), patient.getID());

        try (var connection = DatabaseConnector.connection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.executeUpdate(query);
            System.out.println("Patient diagnosed");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean vacate() {
        this.roomNumber = "N/A";
        DatabaseConnector.connect();
        String query = String.format("UPDATE patients SET roomNumber = NULL WHERE idpatients = '%d';", this.getID());
        try (var connection = DatabaseConnector.connection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.executeUpdate(query);
            System.out.println("Patient Vacated");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean discharge() {
        this.setEmergency(false);
        DatabaseConnector.connect();
        String query = String.format("UPDATE patients SET emergency = 0 WHERE idpatients = '%d';", this.getID());
        try (var connection = DatabaseConnector.connection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.executeUpdate(query);
            System.out.println("Patient Discharged");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static int loadLastId() {
        DatabaseConnector.connect();
        int lastId = 1; // Default value if loading fails
        String query = "SELECT MAX(idpatients) AS idpatients FROM patients";
        try (Connection conn = DatabaseConnector.connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt("idpatients");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }
    public static HashSet<Patient> loadPatients() { return patients;}
    public static HashSet<Patient> loadToHashSet(){
        return new HashSet<>(patientsMap.values());
    }
    @Override
    public String toString() {
        return "Patient{[%d] %s %s %s %s %s %s %s %b %d}".formatted(getID(), name, address, phoneNumber, gender, symptoms, paymentMethod, diagnosis, emergency);
    }
    public String getRoomNumber() {
        return roomNumber;
    }
}