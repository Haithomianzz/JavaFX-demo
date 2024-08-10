package com.functions;

public class Address {
    private int PatientID;
    private String Building;
    private String Area;
    private String District;
    private String City;
    private String Country;

    public boolean setBuilding(String data) {
        this.Building = data;
        return true;
    }

    public boolean setArea(String data) {
        this.Area = data;
        return true;
    }

    public boolean setDistrict(String data) {
        this.District = data;
        return true;
    }

    public boolean setCity(String data) {
        this.City = data;
        return true;
    }

    public boolean setCountry(String data) {
        this.Country = data;
        return true;
    }

    public int getPatientID() {
        return PatientID;
    }

    public boolean setPatientID(int ID) {
        this.PatientID = ID;
        return true;
    }

    public String getFullAddress() {
        return String.format("%s, %s, %s, %s, %s", Building, Area, District, City, Country);
    }
}

