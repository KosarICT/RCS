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
 * Created by Younes on 17/12/2016.
 */
@Repository
public class ADCriticismDaoImpl implements ADCriticismDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Criticize> getAllCriticizeList() {
        String queryString = "SELECT criticize FROM Criticize criticize where  criticize.enable=true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveCriticize(Criticize criticizeModel) {

        Criticize criticize = entityManager.merge(criticizeModel);
        return criticize.getCriticizeId();
    }

    @Override
    public Criticize findCriticizeById(long criticizeId) {
        return entityManager.find(Criticize.class, criticizeId);
    }

    @Transactional
    @Override
    public boolean deleteCriticize(long criticizeId) {
        Criticize criticize = findCriticizeById(criticizeId);
        criticize.setEnable(false);
        entityManager.merge(criticize);
        return true;
    }
}
