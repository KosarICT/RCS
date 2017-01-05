package com.kosarict.dao;

import com.kosarict.entity.ComplainErrand;
import com.kosarict.entity.TicketErrand;
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
                "WHERE te.ticket.ticketId =:ticketId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("ticketId", ticketId);

        return query.getResultList();
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
