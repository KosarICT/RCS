package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
@Entity
@Table(name = "TicketErrand", schema = "dbo", catalog = "Monitoring")
public class TicketErrand {
    private long ticketErrandId;
    private String submitDateTime;
    private boolean isView;
    private String description;

    private Ticket ticket;
    private Users createUser;
    private Users assignedUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TicketErrand_Id")
    public long getTicketErrandId() {
        return ticketErrandId;
    }

    public void setTicketErrandId(long ticketErrandId) {
        this.ticketErrandId = ticketErrandId;
    }

    @Basic
    @Column(name = "SubmitDateTime")
    public String getSubmitDate() {
        return submitDateTime;
    }

    public void setSubmitDate(String submitDateTime) {
        this.submitDateTime = submitDateTime;
    }

    @Basic
    @Column(name = "IsView")
    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "CreateUser_Id")
    public Users getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Users createUser) {
        this.createUser = createUser;
    }

    @ManyToOne
    @JoinColumn(name = "AssignedUser_Id")
    public Users getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(Users assignedUser) {
        this.assignedUser = assignedUser;
    }

    @ManyToOne
    @JoinColumn(name = "Ticket_Id")
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


}
