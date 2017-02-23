package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
@Table(name = "Shift", schema = "dbo", catalog = "Monitoring")
public class Shift {
    private short shiftId;
    private String title;
    private String hourWork;
    private boolean enable;

    @Id
    @Column(name = "Shift_Id")
    public short getShiftId() {
        return shiftId;
    }

    public void setShiftId(short shiftId) {
        this.shiftId = shiftId;
    }

    @Basic
    @Column(name = "HourWork")
    public String getHourWork() {
        return hourWork;
    }

    public void setHourWork(String hourWork) {
        this.hourWork = hourWork;
    }

    @Basic
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Enable")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
