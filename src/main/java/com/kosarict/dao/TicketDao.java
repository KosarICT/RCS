package com.kosarict.dao;

import com.kosarict.entity.*;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
public interface TicketDao {

    List<Ticket> getAllTicketList();

    List<Ticket> getTicketListByTicketTypeId(short ticketTypeId);

    long saveTicket(Ticket ticketModel);

    Ticket findTicketById(long ticketId);

    boolean deleteTicket(long ticketId);

    List<TicketErrand> getTicketListByUserId(int userId);

    List<UsersHospitalSection> forwardTicket(int hospitalId, int sectionId);

    boolean trackingCodeIsExist(String trackingCode);

    List<Ticket> getTop10Ticket();

    List<Ticket> getTicketArchiveList();
}
