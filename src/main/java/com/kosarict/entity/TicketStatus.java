package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/14/2017.
 */
@Entity
@Table(name = "TicketStatus", schema = "dbo", catalog = "")
public class TicketStatus {
    private short ticketStatusId;
    private String title;
    private boolean enable;

    @Id
    @Column(name = "TicketStatus_Id")
    public short getTicketStatusId() {
        return ticketStatusId;
    }

    public void setTicketStatusId(short ticketStatusId) {
        this.ticketStatusId = ticketStatusId;
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
