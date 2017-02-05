package com.kosarict.dao;

import com.kosarict.entity.Notification;
import com.kosarict.entity.NotificationAssign;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by Ali-Pc on 2/5/2017.
 */
@Repository("NotificationAssignDao")
public class NotificationAssignDaoImpl implements NotificationAssignDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long save(NotificationAssign notificationAssignModel) {
        NotificationAssign notificationAssign = entityManager.merge(notificationAssignModel);
        return notificationAssign.getNotificationAssignId();
    }
}
