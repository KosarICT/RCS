package com.kosarict.dao;

import com.kosarict.entity.AppreciationAttachment;
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
@Repository("AppreciationAttachmentDao")
public class AppreciationAttachmentDaoImpl implements  AppreciationAttachmentDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public List<AppreciationAttachment> getAppreciationAttachmentListByAppreciationId(long appreciationId) {
        String queryString = "SELECT aa FROM AppreciationAttachment aa WHERE aa.appreciationId =:appreciationId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("appreciationId", appreciationId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long saveAppreciationAttachment(AppreciationAttachment appreciationAttachmentModel) {
        AppreciationAttachment appreciationAttachment = entityManager.merge(appreciationAttachmentModel);
        return appreciationAttachment.getAppreciationAttachmentId();
    }
}
