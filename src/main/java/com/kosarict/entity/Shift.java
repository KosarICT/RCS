package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
public class Shift {
    private short shiftId;
    private String title;
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
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Eanble")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
