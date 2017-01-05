package com.kosarict.dao;

import com.kosarict.entity.Complain;
import com.kosarict.entity.Complainant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 07/12/2016.
 */
@Repository("ComplainantDao")
public class ComplainantDaoImpl implements ComplainantDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Complainant> getAllComplainantList() {
        String queryString = "SELECT complainant FROM Complainant complainant ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveComplainant(Complainant complainantModel) {
        return 0;
    }

    @Override
    public Complainant findComplainantById(short complainantId) {
        return entityManager.find(Complainant.class, complainantId);
    }

    @Transactional
    @Override
    public boolean deleteComplainant(short complainantId) {
        Complainant complainant = findComplainantById(complainantId);
        entityManager.remove(complainant);
        return true;
    }

}
