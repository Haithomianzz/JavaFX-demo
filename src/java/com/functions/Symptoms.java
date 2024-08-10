package com.functions;

public class Symptoms {
    private int patientID;
    private int physicianID;
    private String[] symptoms;
   

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

    public String[] getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(String[] symptoms) {
        this.symptoms = symptoms;
    }
}
