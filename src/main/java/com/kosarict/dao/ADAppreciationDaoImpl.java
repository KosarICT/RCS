package com.kosarict.dao;

import com.kosarict.entity.Appreciation;
import com.sun.xml.bind.v2.TODO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 16/12/2016.
 */
@Repository
public class ADAppreciationDaoImpl implements ADAppreciationDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Appreciation> getAllAppreciationList() {
        String queryString = "SELECT appreciation FROM Appreciation appreciation where appreciation.enable=true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveAppreciation(Appreciation appreciationModel) {
        Appreciation appreciation = entityManager.merge(appreciationModel);
        return appreciation.getAppreciationId();
    }

    @Override
    public Appreciation findAppreciationById(long appreciationId) {
        return entityManager.find(Appreciation.class, appreciationId);
//        TODO:add enable=true parameter;
    }

    @Transactional
    @Override
    public boolean deleteAppreciation(long appreciationId) {
        Appreciation appreciation = findAppreciationById(appreciationId);
        entityManager.remove(appreciation);
        return true;
    }
}
