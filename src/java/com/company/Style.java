package com.company;

public interface Style {
    String H1 = "-fx-font-weight: bold; -fx-font-size: 32pt; -fx-font-style: italic; -fx-font-family: Arial;";
    String H2 = "-fx-font-size: 26pt; -fx-font-family: Arial;";
    String H3 = "-fx-font-size: 20pt; -fx-font-family: Arial;";
    String Warning = "-fx-border-radius: 5pt; -fx-border-color: red; -fx-background-color: #f5cbc9 ;-fx-padding: 10px;-fx-text-fill: red;";
    String Success = "-fx-border-radius: 5pt; -fx-border-color: lime; -fx-background-color: #c9f5ca ;-fx-padding: 10px;-fx-text-fill: green;";

    String TableLabel = H1+ ";-fx-border-radius: 10pt ;-fx-border-color: black;-fx-padding: 10pt;-fx-background-color: #eeeeee";
    String ButtonStyle = "-fx-font-size: 25pt; -fx-font-family: Arial;";
    String BGColor = "-fx-background-color: #baf5ee;";
    int bwidth = 280;
    int blength= 140;

    String DOCTORS_ICON = "file:src/java/com/company/resouces/Untitled216_20240814165729.png";
    String PATIENTS_ICON = "file:src/java/com/company/resouces/Untitled216_20240814165722.png";
    String LOGOUT_ICON = "file:src/java/com/company/resouces/Untitled216_20240814165635.png";
    String APPTS_ICON ="file:src/java/com/company/resouces/Untitled216_20240814165641.png";
    String ROOM_ICON ="file:src/java/com/company/resouces/Untitled216_20240814165715.png";
}
