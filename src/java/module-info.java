module com.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;


    opens com.company to javafx.fxml;
    exports com.company;
}