package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
@Entity
@Table(name = "TicketType", schema = "dbo", catalog = "Monitoring")
public class TicketType {
    private short ticketTypeId;
    private String title;
    private boolean enable;

    @Id
    @Column(name = "TicketType_Id")
    public short getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(short ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketType that = (TicketType) o;

        if (ticketTypeId != that.ticketTypeId) return false;
        if (enable != that.enable) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) ticketTypeId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (enable ? 1 : 0);
        return result;
    }
}
