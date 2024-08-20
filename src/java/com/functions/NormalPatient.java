package com.functions;


public class NormalPatient extends Patient {

    public NormalPatient(String name, String address, String phoneNumber, String gender, String symptoms, String paymentMethod,  boolean emergency, int doctor) {
        super(name, address, phoneNumber, gender, symptoms, paymentMethod, emergency,doctor);
//        setEmergency(false);
    }
    public NormalPatient(int ID,String name, String address, String phoneNumber, String gender, String symptoms, String paymentMethod,  boolean emergency,int doctor) {
        super(ID,name, address, phoneNumber, gender, symptoms, paymentMethod, emergency,doctor);
//        setEmergency(false);
    }
}
