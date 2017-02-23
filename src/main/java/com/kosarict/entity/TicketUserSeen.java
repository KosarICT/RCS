package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
@Entity
@Table(name = "TicketUserSeen", schema = "dbo", catalog = "Monitoring")
public class TicketUserSeen {
    private long ticketUserSeenId;
    private Ticket ticket;
    private Users user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TicketUserSeen_Id")
    public long getTicketUserSeenId() {
        return ticketUserSeenId;
    }

    public void setTicketUserSeenId(long ticketUserSeenId) {
        this.ticketUserSeenId = ticketUserSeenId;
    }

    @ManyToOne
    @JoinColumn(name = "Ticket_Id")
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket ){
        this.ticket = ticket;
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
