package com.kosarict.dao;

import com.kosarict.entity.ComplainAttachment;
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
@Repository("ComplainAttachmentDao")
public class ComplainAttachmentDaoImpl implements ComplainAttachmentDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public List<ComplainAttachment> getComplainAttachmentListByComplainId(long complainId) {
        String queryString = "SELECT ca FROM ComplainAttachment ca WHERE ca.complainId =:complainId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("complainId", complainId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveComplainAttachment(ComplainAttachment complainAttachmentModel) {
        ComplainAttachment complainAttachment = entityManager.merge(complainAttachmentModel);
        return complainAttachment.getComplainAttachmentId();
    }
}
