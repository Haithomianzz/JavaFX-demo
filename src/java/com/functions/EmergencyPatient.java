package com.functions;

public class EmergencyPatient extends Patient {
    private int roomNumber;
    // private Doctor[] doctorInCharge;
    
    public EmergencyPatient(String name, String address, String phoneNumber, String gender,
     String symptoms,String paymentMethod, String diagnosis,boolean emergency, int roomNumber) {
        super(name, address, phoneNumber, gender, symptoms, paymentMethod, diagnosis, emergency, roomNumber);
        setEmergency(true);
        this.roomNumber = roomNumber;
        // this.doctorInCharge = new Doctor[0];
    }



    public int getRoomNumber(){
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    
    // public boolean addDoctor(Doctor doctor) {
    //     if (doctor == null) return false;
    //     Doctor[] newDoctors = new Doctor[doctorInCharge.length + 1];
    //     System.arraycopy(doctorInCharge, 0, newDoctors, 0, doctorInCharge.length);
    //     newDoctors[doctorInCharge.length] = doctor;
    //     doctorInCharge = newDoctors;
    //     return true;
    // }
}
