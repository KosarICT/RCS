package com.kosarict.dao;

import com.kosarict.entity.NotificationAssign;

import java.util.List;

/**
 * Created by Ali-Pc on 2/5/2017.
 */
public interface NotificationAssignDao {
    long save(NotificationAssign notificationAssign);

    List<NotificationAssign> getNotificationAssinByNotification(long notificationId);
}
