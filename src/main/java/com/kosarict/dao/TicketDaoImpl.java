package com.kosarict.dao;

import com.kosarict.entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
@Repository("TicketDao")
public class TicketDaoImpl implements TicketDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Ticket> getAllTicketList() {
        String queryString = "SELECT ticket FROM Ticket ticket WHERE ticket.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public List<Ticket> getTicketListByTicketTypeId(short ticketTypeId)

    {
        String queryString = "SELECT ticket FROM Ticket ticket WHERE ticket.ticketType.ticketTypeId =:ticketTypeId " +
                "and ticket.enable = true ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("ticketTypeId", ticketTypeId);
        return query.getResultList();
    }

    @Override
    public List<TicketErrand> getTicketListByUserId(int userId) {
        String queryString = "SELECT DISTINCT ticketErrand FROM TicketErrand ticketErrand " +
                "JOIN ticketErrand.ticket ticket " +
                "WHERE ticket.enable = true " +
                "AND ticketErrand.assignedUser.userId =:userId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)

    @Override
    public long saveTicket(Ticket ticketModel) {
        Ticket ticket = entityManager.merge(ticketModel);
        return ticket.getTicketId();
    }

    @Override
    public Ticket findTicketById(long ticketId) {
        return entityManager.find(Ticket.class, ticketId);
    }

    @Override
    public boolean deleteTicket(long complainId) {
        Ticket ticket = findTicketById(complainId);
        ticket.setEnable(false);
        saveTicket(ticket);

        return true;
    }

    @Override
    public List<UsersHospitalSection> forwardTicket(int hospitalId, int sectionId) {


        String queryString = "SELECT userSection  " +
                "FROM UsersHospitalSection userSection " +
                "JOIN userSection.hospitalSection hospitalSection " +
                "WHERE hospitalSection.hospital.hospitalId =:hospitalId " +
                "AND hospitalSection.section.sectionId =:sectionId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("hospitalId", hospitalId);
        query.setParameter("sectionId", sectionId);


        return query.getResultList();
    }

    @Override
    public boolean trackingCodeIsExist(String trackingCode) {

        boolean isExist = false;
        List<Ticket> ticketList;

        String queryString = "SELECT ticket FROM Ticket ticket where  ticket.enable=true  and ticket.trackingCode=:trackingCode";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("trackingCode", trackingCode);

        ticketList = query.getResultList();

        if (ticketList.size() > 0) {
            isExist = true;
        }

        return isExist;
    }

}
