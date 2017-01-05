package com.kosarict.dao;

import com.kosarict.entity.ComplainErrand;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
@Repository("ComplainErrandDao")
public class ComplainErrandDaoImpl implements ComplainErrandDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public List<ComplainErrand> getAllErrandList() {
        String queryString = "SELECT ce FROM ComplainErrand ce";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public List<ComplainErrand> getComplainErrandListByComplainId(long complainId) {
        String queryString = "SELECT ce FROM ComplainErrand ce " +
                "WHERE ce.complain.complainId =:complainId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("complainId", complainId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveComplainErrand(ComplainErrand complainErrandModel) {
        ComplainErrand complainErrand = entityManager.merge(complainErrandModel);
        return complainErrand.getComplainErrandId();
    }

    @Override
    public ComplainErrand findComplainErrandById(long complainErrandId) {
        return entityManager.find(ComplainErrand.class, complainErrandId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean deleteComplainErrand(long complainErrandId) {
        ComplainErrand complainErrand = findComplainErrandById(complainErrandId);
        entityManager.remove(complainErrand);

        return true;
    }
}
