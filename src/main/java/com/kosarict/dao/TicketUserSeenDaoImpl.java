package com.kosarict.dao;

import com.kosarict.entity.TicketErrand;
import com.kosarict.entity.TicketUserSeen;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public boolean deleteTicketUserSeen(long ticketId, int userId) {
        String queryString="SELECT ticketUserSeen FROM TicketUserSeen ticketUserSeen WHERE ticketUserSeen.user.userId=:userId and ticketUserSeen.ticket.ticketId=:ticketId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("ticketId", ticketId);
        query.setParameter("userId", userId);


        List<TicketUserSeen> ticketUserSeenList = query.getResultList();

        if(ticketUserSeenList.size()>0){
            entityManager.remove(ticketUserSeenList.get(0));
            return true;
        }

        return false;


    }
}
