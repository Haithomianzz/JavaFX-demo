package com.functions;

public class Diagnosis {
    
    private int patientID;
    private int physicianID;
    private String[] prescribed;
   

    public int getPatientID() {
        return patientID;
    }
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getPhysicianID() {
        return physicianID;
    }
    public void setPhysicianID(int physicianID) {
        this.physicianID = physicianID;
    }

    public String[] getPrescribed() {
        return prescribed;
    }
    public void setPrescribed(String[] prescribed) {
        this.prescribed = prescribed;
    }

}
