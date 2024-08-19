package com.company;

public interface Style {
    String H1 = "-fx-font-weight: bold; -fx-text-fill: #263238; -fx-font-size: 32pt; -fx-font-style: normal; -fx-font-family: Times New Roman;-fx-alignment: center;";
    String H2 = "-fx-font-size: 26pt; -fx-font-family: Times New Roman; -fx-alignment: center;";
    String H3 = "-fx-font-size: 20pt; -fx-font-family: Times New Roman;";
    String Warning = "-fx-border-radius: 5pt; -fx-border-color: red; -fx-background-color: #f5cbc9 ;-fx-padding: 10px;-fx-text-fill: red;";
    String Success = "-fx-border-radius: 5pt; -fx-border-color: lime; -fx-background-color: #c9f5ca ;-fx-padding: 10px;-fx-text-fill: green;";
    String Checkmark = "-fx-font-size: 16pt; -fx-font-family: Arial;";
    String TableLabel = H1+ ";-fx-border-radius: 10pt ;-fx-border-color: black;-fx-padding: 10pt;-fx-background-color: #eeeeee";
    String ButtonStyle = "-fx-font-size: 25pt; -fx-font-family: Times New Roman; -fx-text-fill: #263238";
    String BGColor = "-fx-background-color: #e1e3e3;";
    String FrameStyle = "-fx-background-color: #f0f0f0; -fx-border-color: #a9a9a9; -fx-border-radius: 15pt; -fx-background-radius: 15pt; -fx-padding: 20px;";
    String Header = "-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 24pt; -fx-padding: 10px;";
    int bwidth = 280;
    int blength= 140;
    String DOCTORS_ICON = "file:src/java/com/company/resources/Doctors.png";
    String PATIENTS_ICON = "file:src/java/com/company/resources/Patients.png";
    String LOGOUT_ICON = "file:src/java/com/company/resources/Logout.png";
    String APPTS_ICON ="file:src/java/com/company/resources/Appointments.png";
    String ROOM_ICON ="file:src/java/com/company/resources/Rooms.png";
    String DIAGNOSIS_ICON = "file:src/java/com/company/resources/Diagnosis.png";
}
