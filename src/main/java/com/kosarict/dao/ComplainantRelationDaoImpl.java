package com.kosarict.dao;


import com.kosarict.entity.ComplainantRelation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 09/12/2016.
 */
@Repository("ComplainantRelationDao")
public class ComplainantRelationDaoImpl implements ComplainantRelationDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<ComplainantRelation> getAllComplainantRelationList() {
        String queryString = "SELECT complainantRelation FROM ComplainantRelation complainantRelation ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveComplainantRelation(ComplainantRelation complainantRelationModel) {
        ComplainantRelation complainantRelation = entityManager.merge(complainantRelationModel);
        return complainantRelation.getComplainantRelationId();
    }

    @Override
    public ComplainantRelation findComplainantRelationById(short complainantRelationId) {
        return entityManager.find(ComplainantRelation.class, complainantRelationId);
    }

    @Transactional
    @Override
    public boolean deleteComplainantRelation(short complainantRelationId) {
        ComplainantRelation complainantRelation = findComplainantRelationById(complainantRelationId);
        entityManager.remove(complainantRelation);
        return true;
    }
}
