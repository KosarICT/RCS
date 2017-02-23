package com.kosarict.dao;

import com.kosarict.entity.ComplainErrand;
import com.kosarict.entity.Ticket;
import com.kosarict.entity.TicketErrand;
import org.hibernate.Session;
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
@Repository("TicketErrandDao")
public class TicketErrandDaoImpl implements TicketErrandDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public List<TicketErrand> getAllErrandList() {
        String queryString = "SELECT te FROM TicketErrand te";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

        @Override
    public List<TicketErrand> getTicketErrandListByTicketId(long ticketId) {
        String queryString = "SELECT te FROM TicketErrand te " +
                "WHERE te.ticket.ticketId =:ticketId " +
                "ORDER BY te.ticketErrandId DESC ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("ticketId", ticketId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<TicketErrand> getTicketErrandListByTicketId(long ticketId, int userId) {
        Session session = entityManager.unwrap(Session.class);

        String queryString = "Select distinct \n" +
                "\tTicketErrand.* \n" +
                "FROM  \n" +
                "\tTicketErrand \n" +
                "JOIN Ticket ON TicketErrand.Ticket_Id = Ticket.Ticket_Id\n" +
                "JOIN TicketStatus ON Ticket.TicketStatus_Id = TicketStatus.TicketStatus_Id \n" +
                "JOIN TicketType ON Ticket.TicketType_Id = TicketType.TicketType_Id \n" +
                "WHERE  \n" +
                "\tTicket.Ticket_Id = " + ticketId + " AND  \n" +
                "\tTicketErrand.CreateUser_Id != " + userId + " AND  \n" +
                "\tTicketStatus.TicketStatus_Id != 3 \n" +
                "ORDER BY  \n" +
                "\tTicketErrand.TicketErrand_Id DESC";

        List query = session.createSQLQuery(queryString).addEntity(TicketErrand.class).list();


        return query;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveTicketErrand(TicketErrand ticketErrandErrandModel) {
        TicketErrand ticketErrand = entityManager.merge(ticketErrandErrandModel);
        return ticketErrand.getTicketErrandId();
    }

    @Override
    public TicketErrand findTicketErrandById(long ticketErrandId) {
        return entityManager.find(TicketErrand.class, ticketErrandId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean deleteTicketErrand(long ticketErrandId) {
        TicketErrand ticketErrand = findTicketErrandById(ticketErrandId);
        entityManager.remove(ticketErrand);

        return true;
    }
}
