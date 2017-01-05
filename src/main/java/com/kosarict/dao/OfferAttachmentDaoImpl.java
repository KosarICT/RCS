package com.kosarict.dao;

import com.kosarict.entity.OfferAttachment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 17/12/2016.
 */
@Repository
public class OfferAttachmentDaoImpl implements OfferAttachmentDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;
    private long offerId;


    @Override
    public List<OfferAttachment> getOfferAttachmentListByOfferId(long offerId) {
        this.offerId = offerId;
        String queryString = "SELECT oa FROM OfferAttachment oa WHERE oa.offerId =:offerId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("offerId", offerId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveOfferAttachment(OfferAttachment offerAttachmentModel)
    {
        OfferAttachment offerAttachment=entityManager.merge(offerAttachmentModel);
        return offerAttachment.getOfferAttachmentId();
    }
}
