package com.kosarict.dao;

import com.kosarict.entity.Offer;
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
public class ADOfferDaoImpl implements ADOfferDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Offer> getAllOfferList() {
        String queryString = "SELECT offer FROM Offer offer where offer.enable=true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveOffer(Offer offerModel) {
        Offer offer = entityManager.merge(offerModel);
        return offer.getOfferId();
    }

    @Override
    public Offer findOfferById(long offerId) {
        return entityManager.find(Offer.class, offerId);
//        TODO:add enable=true parameter;
    }

    @Transactional
    @Override
    public boolean deleteOffer(long offerId) {
        Offer offer = findOfferById(offerId);
        offer.setEnable(false);
        entityManager.merge(offer);
        return true;
    }
}
