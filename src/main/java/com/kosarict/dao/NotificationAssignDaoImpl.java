package com.kosarict.dao;

import com.kosarict.entity.Notification;
import com.kosarict.entity.NotificationAssign;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public List<NotificationAssign> getNotificationAssinByNotification(long notificationId) {
        String queryString = "SELECT notificationAssign FROM NotificationAssign notificationAssign WHERE notificationAssign.notification.notificationId=:notificationId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("notificationId", notificationId);
        return query.getResultList();

    }
}
