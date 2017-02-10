package com.kosarict.dao;

import com.kosarict.entity.Notification;
import com.kosarict.entity.NotificationAnswer;
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
@Repository("NotificationAnswerDao")
public class NotificationAnswerDaoImpl implements NotificationAnswerDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long save(NotificationAnswer notificationAnswerModel){
        NotificationAnswer notificationAnswer = entityManager.merge(notificationAnswerModel);
        return notificationAnswer.getNotificationAnswerId();

    }

    @Override
    public List<NotificationAnswer> getNotificationAnswerByNotification(long notificationId) {
        String queryString = "SELECT notificationAnswer FROM NotificationAnswer notificationAnswer WHERE notificationAnswer.notification.notificationId=:notificationId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("notificationId", notificationId);
        return query.getResultList();
    }

    @Override
    public List<NotificationAnswer> getNotificationAnswerByNotificationUser(long notificationId, int userId) {
        String queryString = "SELECT notificationAnswer FROM NotificationAnswer notificationAnswer WHERE notificationAnswer.notification.notificationId=:notificationId AND notificationAnswer.submitUser.userId=:userId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("notificationId", notificationId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
