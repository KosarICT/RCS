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
    private Tab tab;

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

    @ManyToOne
    @JoinColumn(name = "Tab_Id")
    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }
}
