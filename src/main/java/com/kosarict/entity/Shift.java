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
    private boolean eanble;

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
    public boolean isEanble() {
        return eanble;
    }

    public void setEanble(boolean eanble) {
        this.eanble = eanble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shift shift = (Shift) o;

        if (shiftId != shift.shiftId) return false;
        if (eanble != shift.eanble) return false;
        if (title != null ? !title.equals(shift.title) : shift.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) shiftId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (eanble ? 1 : 0);
        return result;
    }
}
