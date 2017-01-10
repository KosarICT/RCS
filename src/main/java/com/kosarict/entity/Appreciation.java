package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
public class Appreciation {
    private long appreciationId;
    private String firstName;
    private String lastName;
    private String persnolFirstName;
    private String persnolLastName;
    private int raiting;
    private String nationalCode;
    private String phoneNumber;
    private String mobile;
    private String subject;
    private String description;
    private String email;
    private boolean enable;
    private Hospital hospital;
    private Section section;
    private String trackingCode;

    @Id
    @Column(name = "Appreciation_Id")
    public long getAppreciationId() {
        return appreciationId;
    }

    public void setAppreciationId(long appreciationId) {
        this.appreciationId = appreciationId;
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
    @JoinColumn(name = "Section_Id")
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "PersnolFirstName")
    public String getPersnolFirstName() {
        return persnolFirstName;
    }

    public void setPersnolFirstName(String persnolFirstName) {
        this.persnolFirstName = persnolFirstName;
    }

    @Basic
    @Column(name = "PersnolLastName")
    public String getPersnolLastName() {
        return persnolLastName;
    }

    public void setPersnolLastName(String persnolLastName) {
        this.persnolLastName = persnolLastName;
    }

    @Basic
    @Column(name = "Raiting")
    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    @Basic
    @Column(name = "NationalCode")
    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    @Basic
    @Column(name = "PhoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "Mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "Subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "Enable")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    @Basic
    @Column(name = "TrackingCode")
    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
}
