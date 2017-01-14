package com.kosarict.dao;

import com.kosarict.entity.TicketStatus;

/**
 * Created by Ali-Pc on 1/14/2017.
 */
public interface TicketStatusDao {
    TicketStatus findTicketStatusById(short ticketStatusId);
}
