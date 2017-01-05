package com.kosarict.dao;


import com.kosarict.entity.Criticize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Admin on 12/15/2016.
 */
@Repository("CriticismsDao")
public class CriticismsDaoIpml implements CriticismsDao{

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public boolean trackingCodeIsExist(String trackingCode) {
        boolean isExist = false;
        List<Criticize> criticizeList;

        String queryString = "SELECT criticize FROM Criticize criticize where  criticize.enable=true  and criticize.trackingCode=:trackingCode";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("trackingCode", trackingCode);

        criticizeList = query.getResultList();

        if (criticizeList.size() > 0) {
            isExist = true;
        }

        return isExist;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveCriticize(Criticize criticizeModel) {
        Criticize criticize = entityManager.merge(criticizeModel);
        return criticize.getCriticizeId();
    }
}
