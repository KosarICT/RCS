package com.kosarict.dao;

import com.kosarict.entity.CriticizeAttachment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Sadegh-Pc on 12/23/2016.
 */
@Repository("CriticizeAttachmentDao")
public class CriticizeAttachmentDaoImpl implements CriticizeAttachmentDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public List<CriticizeAttachment> getCriticizeAttachmentListByCriticizeId(long criticizeId) {
        String queryString = "SELECT ca FROM CriticizeAttachment ca WHERE ca.criticizeId =:criticizeId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("criticizeId", criticizeId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveCriticizeAttachment(CriticizeAttachment criticizeAttachmentModel) {
        CriticizeAttachment criticizeAttachment = entityManager.merge(criticizeAttachmentModel);
        return criticizeAttachment.getCriticizeAttachmentId();
    }
}
