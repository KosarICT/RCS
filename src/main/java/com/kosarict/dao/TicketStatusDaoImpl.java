package com.kosarict.dao;

import com.kosarict.entity.Ticket;
import com.kosarict.entity.TicketStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by Ali-Pc on 1/14/2017.
 */

@Repository("TicketStatusDao")
public class TicketStatusDaoImpl implements TicketStatusDao{
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public TicketStatus findTicketStatusById(short ticketStatusId) {
        return entityManager.find(TicketStatus.class, ticketStatusId);
    }
}
