package com.functions;

public class EmergencyPatient extends Patient {
    // private Doctor[] doctorInCharge;
    
    public EmergencyPatient(String name, String address, String phoneNumber, String gender,
     String symptoms,String paymentMethod,int roomNumber,boolean emergency ) {
        super(name, address, phoneNumber, gender, symptoms, paymentMethod, emergency);
        setEmergency(true);
        setRoomNumber(roomNumber); ;
        // this.doctorInCharge = new Doctor[0];
    }





    
    // public boolean editDoctor(Doctor doctor) {
    //     if (doctor == null) return false;
    //     Doctor[] newDoctors = new Doctor[doctorInCharge.length + 1];
    //     System.arraycopy(doctorInCharge, 0, newDoctors, 0, doctorInCharge.length);
    //     newDoctors[doctorInCharge.length] = doctor;
    //     doctorInCharge = newDoctors;
    //     return true;
    // }
}
