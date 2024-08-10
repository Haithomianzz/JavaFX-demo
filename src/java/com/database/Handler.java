package com.database;

import com.functions.Appointment;
import com.functions.Doctor;
import com.functions.Patient;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Handler {

    public static Map<Integer, Doctor> loadDoctors() {
        Map<Integer, Doctor> doctors = new HashMap<>();
        
        DatabaseConnector.connect();
        Connection connection = null;
        try {
            // Establish the connection
           connection = DatabaseConnector.connection();

            // SQL query to retrieve data
            String sql = "SELECT * FROM doctors";

            // Prepare statement and execute query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and populate the HashMap
            while (resultSet.next()) {
                int id = resultSet.getInt("idDoctors");
                Doctor doctor = new Doctor(
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("specialty"));

                doctors.put(id, doctor);
            }

            // Close the ResultSet and PreparedStatement
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return doctors;
    }



    public static Map<Integer, Patient> loadPatients() {
        Map<Integer, Patient> patientHashMap = new HashMap<>();

        DatabaseConnector.connect();
        Connection connection = null;
        try {
            // Establish the connection
            connection = DatabaseConnector.connection();

            // SQL query to retrieve data
            String sql = "SELECT * FROM patients";

            // Prepare statement and execute query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and populate the HashMap
            while (resultSet.next()) {
                int id = resultSet.getInt("idpatients");
                Patient patient = new Patient(
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("gender"),
                        resultSet.getString("symptoms"),
                        resultSet.getString("paymentMethod"),
                        resultSet.getString("diagno"),
                        resultSet.getBoolean("emergency"),
                        resultSet.getInt("roomNumber"));

                patientHashMap.put(id, patient);
            }

            // Close the ResultSet and PreparedStatement
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return patientHashMap;
    }

    public static Map<Integer, Appointment> loadAppointments() {
        Map<Integer, Appointment> appointments = new HashMap<>();

        DatabaseConnector.connect();
        Connection connection = null;
        try {
            // Establish the connection
            connection = DatabaseConnector.connection();

            // SQL query to retrieve data
            String sql = "SELECT * FROM appointments";

            // Prepare statement and execute query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and populate the HashMap
            while (resultSet.next()) {
                int id = resultSet.getInt("idappointments");
                Appointment appointment = new Appointment(
                        resultSet.getInt("physicianId"),
                        resultSet.getInt("patientId")
                );

                appointments.put(id, appointment);
            }

            // Close the ResultSet and PreparedStatement
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return appointments;
    }

}
