package com.kosarict.dao;

import com.kosarict.entity.ComplainErrand;
import com.kosarict.entity.TicketErrand;

import java.util.List;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
public interface TicketErrandDao {
    List<TicketErrand> getAllErrandList();

    List<TicketErrand> getTicketErrandListByTicketId(long ticketId);

    long saveTicketErrand(TicketErrand ticketErrandModel);

    TicketErrand findTicketErrandById(long ticketErrandId);

    boolean deleteTicketErrand(long ticketErrandId);
}
