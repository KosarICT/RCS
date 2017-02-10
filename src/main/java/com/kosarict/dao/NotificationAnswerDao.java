package com.kosarict.dao;

import com.kosarict.entity.NotificationAnswer;

import java.util.List;

/**
 * Created by Ali-Pc on 2/5/2017.
 */
public interface NotificationAnswerDao {
    long save(NotificationAnswer notificationAnswer);

    List<NotificationAnswer> getNotificationAnswerByNotification(long notificationId);

    List<NotificationAnswer> getNotificationAnswerByNotificationUser(long notificationId,int userId);
}
