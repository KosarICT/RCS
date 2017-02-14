package com.kosarict.dao;

import com.kosarict.entity.Notification;
import com.kosarict.entity.NotificationUserSeen;
import com.kosarict.entity.TicketUserSeen;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Ali-Pc on 2/14/2017.
 */
@Repository("NotificationUserSeenDao")
public class NotificationUserSeenDaoImpl implements NotificationUserSeenDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long save(NotificationUserSeen notificationUserSeenModel) {
        NotificationUserSeen notificationUserSeen = entityManager.merge(notificationUserSeenModel);
        return notificationUserSeen.getNotificationUserSeenId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean delete(long notificationId, int userId) {
        String queryString = "SELECT notificationUserSeen FROM NotificationUserSeen notificationUserSeen WHERE notificationUserSeen.user.userId=:userId and notificationUserSeen.notification.notificationId=:notificationId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("notificationId", notificationId);
        query.setParameter("userId", userId);


        List<NotificationUserSeen> notificationUserSeenList = query.getResultList();

        if (notificationUserSeenList.size() > 0) {
            entityManager.remove(notificationUserSeenList.get(0));
            return true;
        }

        return false;

    }

    @Override
    public int numderOfNotification(int userId) {
        String queryString = "SELECT notificationUserSeen FROM NotificationUserSeen notificationUserSeen WHERE notificationUserSeen.user.userId=:userId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);


        List<NotificationUserSeen> notificationUserSeenList = query.getResultList();

        return notificationUserSeenList.size();
    }

    @Override
    public boolean isNewit(long notificatioId, int userId) {
        String queryString = "SELECT notificationUserSeen FROM NotificationUserSeen notificationUserSeen WHERE notificationUserSeen.user.userId=:userId and notificationUserSeen.notification.notificationId=:notificationId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);
        query.setParameter("notificationId", notificatioId);

        List<NotificationUserSeen> notificationUserSeenList = query.getResultList();

        return notificationUserSeenList.size()>0;

    }
}
