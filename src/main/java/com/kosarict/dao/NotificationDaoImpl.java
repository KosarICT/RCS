package com.kosarict.dao;

import com.kosarict.entity.Hospital;
import com.kosarict.entity.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Ali-Pc on 2/3/2017.
 */
@Repository("NotificationDao")
public class NotificationDaoImpl implements NotificationDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Notification> getNotificationByUserId(int userId) {
        String queryString = " SELECT notification FROM Notification notification where notification.submitUser.userId=:userId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);


        return query.getResultList();

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long Save(Notification notificationModel) {
        Notification notification = entityManager.merge(notificationModel);
        return notification.getNotificationId();

    }

    @Override
    public Notification findNotification(long notificationId) {
        return entityManager.find(Notification.class, notificationId);
    }
}
