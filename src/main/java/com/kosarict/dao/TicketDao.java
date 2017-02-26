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

    List<Ticket> getTicketListByTicketTypeId(short ticketTypeId, int userId);

    List<Ticket> getTicketList(short ticketTypeId, int userId, int hospitalId);

    long saveTicket(Ticket ticketModel);

    Ticket findTicketById(long ticketId);

    Ticket findTicketByTrackingCode(String trackingCode);

    boolean deleteTicket(long ticketId);

    List<TicketErrand> getTicketListByUserId(int userId);

    List<Users> forwardTicket(int hospitalId, int sectionId);

    boolean trackingCodeIsExist(String trackingCode);

    List<Ticket> getTop10Ticket(Users users);

    List<Ticket> getTicketArchiveList();

    int getTicketErrandedCount(int userId, int ticketTypeId);

    int getReadTicket(int userId, int ticketTypeId);

    int getUnReadTicket(int userId, int ticketTypeId);
}
