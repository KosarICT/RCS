package com.kosarict.dao;

import com.kosarict.entity.Appreciation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Admin on 12/16/2016.
 */
@Repository("AppreciationDao")
public class AppreciationDaoImpl implements AppreciationDao{
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public boolean trackingCodeIsExist(String trackingCode) {
        boolean isExist = false;
        List<Appreciation> appreciationList;

        String queryString = "SELECT appreciation FROM Appreciation appreciation where  appreciation.enable=true  and appreciation.trackingCode=:trackingCode";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("trackingCode", trackingCode);

        appreciationList = query.getResultList();

        if (appreciationList.size() > 0) {
            isExist = true;
        }

        return isExist;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveAppreciation(Appreciation appreciationModel) {
        try {
            Appreciation appreciation = entityManager.merge(appreciationModel);
            long x=appreciation.getAppreciationId();
            return x;
        }catch (Exception ex){
            return 0;
        }
    }
}
