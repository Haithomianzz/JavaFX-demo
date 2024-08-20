package com.functions;


import com.database.DatabaseConnector;
import com.database.Handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Appointment {

    private int patientId;
    private int doctorId;
    private String Date;
    private static int lastId = loadLastId();
    private final int ID = lastId++;
    private static Set<Appointment> appointments = new HashSet<>();

    static {
        appointments = loadToHashSet(); // Load from database after initialization
        Map<Integer, Appointment> appointmentHashMap = Handler.loadAppointments();
    }

    public Appointment(int patientId, int doctorId) {
        this.doctorId = patientId;
        this.doctorId = doctorId;
        add(this);

    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getID() {
        return ID;
    }
    private void add(Appointment appointment) {
        appointments.add(appointment);
    }

    public static boolean save() {
        DatabaseConnector.connect();
        String sql = "INSERT INTO appointments (idappointments, physicianId, patientId) VALUES (?, ?)";
        try (var connection = DatabaseConnector.connection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql)) {


            ResultSet resultSet = preparedStmt.executeQuery();

            for (Appointment appointment : appointments) {
                preparedStmt.setInt(1, appointment.getID());
                preparedStmt.setInt(2, appointment.getDoctorId());
                preparedStmt.setInt(3, appointment.getPatientId());
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
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

    private static HashSet<Appointment> loadToHashSet(){
        return new HashSet<>(Handler.loadAppointments().values());
    }

}
    

