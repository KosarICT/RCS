package com.kosarict.dao;

import com.kosarict.entity.TicketErrand;
import com.kosarict.entity.TicketUserSeen;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by Ali-Pc on 1/16/2017.
 */
@Repository("TicketUserSeenDao")
public class TicketUserSeenDaoImpl implements TicketUserSeenDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveTicketUserSeen(TicketUserSeen ticketUserSeenModel) {
        TicketUserSeen ticketUserSeen = entityManager.merge(ticketUserSeenModel);
        return ticketUserSeen.getTicketUserSeenId();
    }
}
