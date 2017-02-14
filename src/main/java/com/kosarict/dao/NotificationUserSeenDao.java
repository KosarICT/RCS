package com.kosarict.dao;

import com.kosarict.entity.NotificationUserSeen;

/**
 * Created by Ali-Pc on 2/14/2017.
 */
public interface NotificationUserSeenDao {

    long save(NotificationUserSeen notificationUserSeen);

    boolean delete(long notificationId,int userId);

    int numderOfNotification(int userId);

    boolean isNewit(long notificatioId,int userId);
}
