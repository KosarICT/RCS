package com.kosarict.dao;

import com.kosarict.entity.OfferAttachment;
import com.kosarict.entity.TicketAttachment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Ali-Pc on 1/4/2017.
 */
@Repository
public class TicketAttachmentDaoImpl implements TicketAttachmentDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;
    private long ticketId;


    @Override
    public List<TicketAttachment> getTicketAttachmentListByTicketId(long ticketId) {
        this.ticketId = ticketId;
        String queryString = "SELECT ta FROM TicketAttachment ta WHERE ta.ticketId =:ticketId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("ticketId", ticketId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveTicketAttachment(TicketAttachment ticketAttachmentModel)
    {
        TicketAttachment ticketAttachment=entityManager.merge(ticketAttachmentModel);
        return ticketAttachment.getTicketAttachmentId();
    }
}
