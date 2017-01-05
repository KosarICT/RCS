package com.kosarict.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Admin on 12/13/2016.
 */
@Entity
@Table(name = "UsersHospitalSection", schema = "dbo", catalog = "Monitoring")
public class UsersHospitalSection {
    private int usersHospitalSectionId;
    private HospitalSection hospitalSection;
    private Users user;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsersHospitalSection_Id")
    public int getUsersHospitalSectionId() {
        return usersHospitalSectionId;
    }

    public void setUsersHospitalSectionId(int usersHospitalSectionId) {
        this.usersHospitalSectionId = usersHospitalSectionId;
    }

    @ManyToOne
    @JoinColumn(name = "HospitalSection_Id")
    public HospitalSection getHospitalSection() {
        return hospitalSection;
    }

    public void setHospitalSection(HospitalSection hospitalSection) {
        this.hospitalSection = hospitalSection;
    }

    @ManyToOne
    @JoinColumn(name = "User_Id")
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }




}
