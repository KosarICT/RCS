package com.kosarict.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
@Table(name = "Users", schema = "dbo", catalog = "Monitoring")
public class Users {
    private int userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String displayName;
    private String imageName;
    private String tel;
    private String mobile;
    private short locked;
    private boolean enable;
    private boolean isSuperUser;
    private String personalNumber;
    private String macAddress;
    private String image;
    private Timestamp lastRequestFromMobile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "DisplayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "Image")
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Basic
    @Column(name = "Tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
    @Column(name = "Locked")
    public short getLocked() {
        return locked;
    }

    public void setLocked(short locked) {
        this.locked = locked;
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
    @Column(name = "IsSuperUser")
    public boolean isSuperUser() {
        return isSuperUser;
    }

    public void setSuperUser(boolean isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    @Basic
    @Column(name = "PersonalNumber")
    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    @Basic
    @Column(name = "MacAddress")
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Basic
    @Column(name = "LastRequestFromMobile")
    public Timestamp getLastRequestFromMobile() {
        return lastRequestFromMobile;
    }

    public void setLastRequestFromMobile(Timestamp lastRequestFromMobile) {
        this.lastRequestFromMobile = lastRequestFromMobile;
    }
}
