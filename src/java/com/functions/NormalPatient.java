package com.functions;


public class NormalPatient extends Patient {

    public NormalPatient(String name, String address, String phoneNumber, String gender, String symptoms, String paymentMethod,  boolean emergency) {
        super(name, address, phoneNumber, gender, symptoms, paymentMethod, emergency);
        setEmergency(false);
    }
}
