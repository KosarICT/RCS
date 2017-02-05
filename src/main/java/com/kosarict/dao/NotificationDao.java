package com.kosarict.dao;

import com.kosarict.entity.Notification;

import java.util.List;

/**
 * Created by Ali-Pc on 2/3/2017.
 */
public interface NotificationDao {
    List<Notification> getNotificationByUserId(int userId);

    long Save(Notification notification);

    Notification findNotification(long notificationId);
}
