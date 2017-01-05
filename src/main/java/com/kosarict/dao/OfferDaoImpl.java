package com.kosarict.dao;

import com.kosarict.entity.Complain;
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
 * Created by Ali-Pc on 12/15/2016.
 */
@Repository("OfferDao")
public class OfferDaoImpl implements OfferDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public boolean trackingCodeIsExist(String trackingCode) {
        boolean isExist = false;
        List<Complain> complainList;

        String queryString = "SELECT offer FROM Offer offer where  offer.enable=true  and offer.trackingCode=:trackingCode";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("trackingCode", trackingCode);

        complainList = query.getResultList();

        if (complainList.size() > 0) {
            isExist = true;
        }

        return isExist;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveOffer(Offer offerModel)
    {
        Offer offer=entityManager.merge(offerModel);
        return offer.getOfferId();
    }
}

