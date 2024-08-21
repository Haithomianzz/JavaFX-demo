package com.functions;


import com.database.DatabaseConnector;
import com.database.Handler;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Appointment {

    private int patientId;
    private int doctorId;
    private com.company.Date Date;
    private String DateString;
    private static int lastId;
    private final int ID;
    private static HashSet<Appointment> appointments = new HashSet<>();
    private static Map<Integer, Appointment> appointmentHashMap = new HashMap<>();
    static {
        lastId = loadLastId();
        appointments = loadToHashSet(); // Load from database after initialization
        appointmentHashMap = Handler.loadAppointments();
    }
    public Appointment(int doctorId, int patientId,  LocalDate date) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.ID = ++lastId;
        this.Date = new com.company.Date(date);
        this.DateString = Date.toString();
        addAppointment(this);
    }
    public Appointment(int ID,int doctorId, int patientId,  String date) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.ID = ID;
        this.Date = new com.company.Date(date);
        this.DateString = Date.toString();
        addAppointment(this);
    }
    public int getPatientId() {
        return patientId;
    }
    private void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
    public int getDoctorId() {
        return doctorId;
    }
    public com.company.Date getDate() {return Date;}
    public String getDateString() {return DateString;}
    public int getID() {
        return ID;
    }
    public Boolean EditAppointment(Integer newPatientId, Integer newDoctorId, LocalDate newDate) {
        if (newDate != null && newDate.isAfter(LocalDate.now())) {
            this.doctorId = (newDoctorId != null) ? newDoctorId : this.doctorId;
            this.patientId = (newPatientId != null) ? newPatientId : this.patientId;
            this.Date = new com.company.Date(newDate);
            return true;
        }
        else
            return false;
    }
    public static boolean add(Appointment appointment) {
        appointments.add(appointment);
        DatabaseConnector.connect();
        String sql = "INSERT INTO appointments (idappointments, physicianId, patientId, date) VALUES (?, ?, ?, ?)";
        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql); ){
            // Save patient if no duplicates
            preparedStmt.setInt(1, appointment.getID());
            preparedStmt.setInt(2, appointment.getPatientId());
            preparedStmt.setInt(3, appointment.getDoctorId());
            preparedStmt.setString(4, appointment.getDate().toString());
            preparedStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static boolean save(Appointment appointment) {
        DatabaseConnector.connect();
        String sql = "UPDATE appointments SET physicianId = ?, patientId = ?, date = ? WHERE idappointments = ?";
        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql)) {
            preparedStmt.setInt(1, appointment.getDoctorId());
            preparedStmt.setInt(2, appointment.getPatientId());
            preparedStmt.setString(3, appointment.Date.toString());
            preparedStmt.setInt(4, appointment.getID());
            preparedStmt.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static boolean delete(Appointment appointment) {
        appointments.remove(appointment);
        DatabaseConnector.connect();
        String qry = "DELETE FROM appointments WHERE idappointments= '%s'".formatted(appointment.getID());
        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(qry);
        ) {
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isThereAppointment(LocalDate targetDate) {
        for (Appointment appointment : appointments) {
            if (appointment.getDate().getDate().equals(targetDate)) {
                return true;
            }
        }
        return false;
    }
    public static Appointment atDate(LocalDate targetDate) {
        for (Appointment appointment : appointments) {
            if (appointment.getDate().getDate().equals(targetDate)) {
                return appointment;
            }
        }
        return null;
    }
    private static int loadLastId() {
        DatabaseConnector.connect();
        int lastId = 1; // Default value if loading fails
        String query = "SELECT MAX(idappointments) AS idappointments FROM appointments";
        try (Connection conn = DatabaseConnector.connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt("idappointments");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }
    public static HashSet<Appointment> loadAppointments(){return appointments;}
    private static HashSet<Appointment> loadToHashSet(){
        return new HashSet<>(appointmentHashMap.values());
    }
}