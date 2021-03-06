package com.kosarict.dao;

import com.kosarict.entity.Hospital;
import com.kosarict.entity.Notification;
import com.kosarict.entity.Ticket;
import org.hibernate.Session;
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Notification> getNotificationByUserId(int userId) {
        Session session = entityManager.unwrap(Session.class);

        String queryString = "SELECT DISTINCT Notification.* FROM Notification notification " +
                "LEFT JOIN NotificationAssign notificationAssign ON notification.Notification_Id=notificationAssign.Notification_Id\n" +
                "where notification.SubmitUser_Id="+userId+" OR notificationAssign.User_Id="+userId;

        List query = session.createSQLQuery(queryString).addEntity(Notification.class).list();


        return query;

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
