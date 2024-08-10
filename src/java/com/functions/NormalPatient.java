package com.functions;


public class NormalPatient extends Patient {

    public NormalPatient(String name, String address, String phoneNumber, String gender, String symptoms, String paymentMethod, String diagnosis, boolean emergency, int roomNumber) {
        super(name, address, phoneNumber, gender, symptoms, paymentMethod, diagnosis, emergency, roomNumber);
        setEmergency(false);
    }
}
