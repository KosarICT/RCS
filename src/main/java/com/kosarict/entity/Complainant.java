package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
public class Complainant {
    private short complainantId;
    private String title;

    @Id
    @Column(name = "Complainant_Id")
    public short getComplainantId() {
        return complainantId;
    }

    public void setComplainantId(short complainantId) {
        this.complainantId = complainantId;
    }

    @Basic
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Complainant that = (Complainant) o;

        if (complainantId != that.complainantId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) complainantId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
