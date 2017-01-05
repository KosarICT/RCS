package com.kosarict.dao;

import com.kosarict.entity.Relation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 06/12/2016.
 */
@Repository
public class RelationDaoImpl implements RelationDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Relation> getAllRelationList() {
        String queryString = "SELECT relation FROM Relation relation  WHERE relation.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveRelation(Relation relationnModel) {
        return 0;
    }

    @Override
    public Relation findRelationById(short relationId) {
        return entityManager.find(Relation.class, relationId);
    }

    @Transactional
    @Override
    public boolean deleteRelation(short relationId) {
        Relation relation = findRelationById(relationId);
        relation.setEnable(false);
        entityManager.merge(relation);
        return true;
    }
}

