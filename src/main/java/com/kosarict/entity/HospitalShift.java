package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
@Table(name = "HospitalShift", schema = "dbo", catalog = "Monitoring")
public class HospitalShift {
    private int hospitalShiftId;
    private Hospital hospital;
    private Shift shift;

    @Id
    @Column(name = "HospitalShift_Id")
    public int getHospitalShiftId() {
        return hospitalShiftId;
    }

    public void setHospitalShiftId(int hospitalShiftId) {
        this.hospitalShiftId = hospitalShiftId;
    }


    @ManyToOne
    @JoinColumn(name = "Hospital_Id")
    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    @ManyToOne
    @JoinColumn(name = "Shift_Id")
    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }


}
