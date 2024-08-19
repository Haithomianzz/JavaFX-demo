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
    private static int lastId = 1;
    private final int ID = lastId;

    private static Set<Patient> patients = new HashSet<>();
    private static Map<Integer, Patient> patientsMap= Handler.loadPatients();
    static {
        patients = loadToHashSet(); // Load from database after initialization
    }
    public Patient(String name, String address, String phoneNumber, String gender,
                   String symptoms, String paymentMethod, boolean emergency) {
        super(name, address, phoneNumber);
        this.gender = gender;
        this.symptoms = symptoms;
        this.paymentMethod = paymentMethod;
        this.emergency = emergency;
        lastId++;
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
    protected void setRoomNumber(int roomNumber) {
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
                             String newSymptoms, String newPaymentMethod,String newRoomNumber, Boolean newEmergency ) {
        // Retrieve the patient object from the map
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
    }
//    public static boolean save() {
//        DatabaseConnector.connect();
//        String deleteQuery = "DELETE FROM patients";
//        String sql = "INSERT INTO patients (idpatients, name, address, phoneNumber, gender, symptoms, paymentMethod, diagno, emergency, roomNumber, doctorid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
//        try (var connection = DatabaseConnector.connection();
//             PreparedStatement preparedStmt = connection.prepareStatement(sql);
//             PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);) {
//             deleteStmt.execute();
//            for (Patient patient : patients) {
//                // Save patient if no duplicates
//                preparedStmt.setInt(1, patient.getID());
//                preparedStmt.setString(2, patient.getName());
//                preparedStmt.setString(3, patient.getAddress());
//                preparedStmt.setString(4, patient.getPhoneNumber());
//                preparedStmt.setString(5, patient.getGender());
//                preparedStmt.setString(6, patient.getSymptoms());
//                preparedStmt.setString(7, patient.getPaymentMethod());
//                preparedStmt.setString(8, patient.getDiagnosis());
//                if (patient.isEmergency()){
//                    preparedStmt.setInt(9, 1);
//                    preparedStmt.setInt(10, Integer.parseInt(patient.getRoomNumber()));
//                }
//                else{
//                    preparedStmt.setInt(9, 0);
//                    preparedStmt.setInt(10, 0);
//                }
//                preparedStmt.setInt(11, patient.getDoctorInCharge());
//                preparedStmt.execute();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return true;
//    }
    public static Boolean save(Patient patient){
        if (delete(patient) && add(patient)){
            System.out.println("Save Successful");
            return true;
        }
        else    {
            System.out.println("Save Failed");
            return false;
        }

    }

    public static boolean delete(Patient patient) {
        patients.remove(patient);
        DatabaseConnector.connect();
        String query = "SELECT * FROM patients WHERE idpatients= '%s'".formatted(patient.getID());
        String qry = "DELETE FROM patients WHERE idpatients= '%s'".formatted(patient.getID());
        try (var connection = DatabaseConnector.connection();
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(qry);
        ) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getInt("idpatients") == (patient.getID())) {
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