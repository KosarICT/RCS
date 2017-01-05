package com.kosarict.dao;

import com.kosarict.entity.CriticizeAttachment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 17/12/2016.
 */
@Repository
public class ADCriticizeAttachmentDaoImpl implements ADCriticizeAttachmentDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;
    private long criticizeId;

    @Override
    public List<CriticizeAttachment> getCriticizeAttachmentListByCriticizeId(long criticizeId) {
        this.criticizeId = criticizeId;
        String queryString = "SELECT ca FROM CriticizeAttachment ca WHERE ca.criticizeId =:criticizeId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("criticizeId", criticizeId);

        return query.getResultList();
    }
}
