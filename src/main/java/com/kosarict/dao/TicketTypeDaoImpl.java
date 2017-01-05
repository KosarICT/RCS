package com.kosarict.dao;

import com.kosarict.entity.TicketType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by Ali-Pc on 1/4/2017.
 */
@Repository("TicketTypeDao")
public class TicketTypeDaoImpl implements TicketTypeDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    public TicketType getTicketType(short ticketTypeId){
            return entityManager.find(TicketType.class, ticketTypeId);
    }
}
