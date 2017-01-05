package com.kosarict.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
@Entity
@Table(name = "TicketUserSeen", schema = "dbo", catalog = "")
public class TicketUserSeen {
    private long ticketUserSeenId;

    @Id
    @Column(name = "TicketUserSeen_Id")
    public long getTicketUserSeenId() {
        return ticketUserSeenId;
    }

    public void setTicketUserSeenId(long ticketUserSeenId) {
        this.ticketUserSeenId = ticketUserSeenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketUserSeen that = (TicketUserSeen) o;

        if (ticketUserSeenId != that.ticketUserSeenId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (ticketUserSeenId ^ (ticketUserSeenId >>> 32));
    }
}
