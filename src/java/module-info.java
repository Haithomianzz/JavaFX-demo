module com.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;
    requires jdk.jshell;
    requires jdk.compiler;


    opens com.company to javafx.fxml;
    opens com.functions to javafx.base;

    exports com.company;
}