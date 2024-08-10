package com.functions;

import com.database.DatabaseConnector;
import com.database.Handler;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HospitalManagement {

    public <T extends Doctor> void displayDoctors() {
      for(Doctor d : Handler.loadDoctors().values()) {
          System.out.println(d.toString());
      }
    }

    public <T extends Doctor> void displayPatients() {
        for(Patient p : Handler.loadPatients().values()) {
            System.out.println(p.toString());
        }
    }
}
