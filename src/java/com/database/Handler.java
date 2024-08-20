package com.database;

import com.company.Main;
import com.functions.*;

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
                        id,
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
                Patient patient;
                if (resultSet.getInt("emergency") == 1){
                     patient = new EmergencyPatient(
                            id,
                            resultSet.getString("name"),
                            resultSet.getString("address"),
                            resultSet.getString("phoneNumber"),
                            resultSet.getString("gender"),
                            resultSet.getString("symptoms"),
                            resultSet.getString("paymentMethod"),
                            resultSet.getInt("roomNumber"),
                            resultSet.getBoolean("emergency"),
                            resultSet.getInt("doctorid"));
                    patient.setDiagnosis(resultSet.getString("diagno"));
                }
                else {
                    patient = new NormalPatient(
                            id,
                            resultSet.getString("name"),
                            resultSet.getString("address"),
                            resultSet.getString("phoneNumber"),
                            resultSet.getString("gender"),
                            resultSet.getString("symptoms"),
                            resultSet.getString("paymentMethod"),
                            resultSet.getBoolean("emergency"),
                            resultSet.getInt("doctorid"));
                    patient.setDiagnosis(resultSet.getString("diagno"));
                }
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
                Appointment appointment = new Appointment(id,
                        resultSet.getInt("physicianId"),
                        resultSet.getInt("patientId"),
                        resultSet.getString("date")
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

    public static boolean verifyCredentials(String username, String password) {
    DatabaseConnector.connect();
    Connection connection = null;
    try {
        connection = DatabaseConnector.connection();

        // SQL query to retrieve data
        String sql = "SELECT * FROM userdata WHERE username = ?";

        // Prepare statement and set parameters
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        // Check if the username exists and verify the password
        if (resultSet.next()) {
            int storedId = resultSet.getInt("doctorid");
            String storedPassword = resultSet.getString("password");
            if (storedPassword.equals(password)) {
                if (storedId != 0){
                    for(Doctor d : Doctor.getDoctors()){
                        if (d.getID() == storedId){
                            Main.setCurrentDoctor(d);
                            Main.setUserType("Doctor");
                        }
                    }
                }
                else
                    Main.setUserType("Admin");
                return true;
            }
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
    return false;
}
}
