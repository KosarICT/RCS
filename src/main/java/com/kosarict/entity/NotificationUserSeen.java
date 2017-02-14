package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 2/14/2017.
 */
@Entity
public class NotificationUserSeen {
    private long notificationUserSeenId;
    private Notification notification;
    private Users user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationUserSeen_Id")
    public long getNotificationUserSeenId() {
        return notificationUserSeenId;
    }

    public void setNotificationUserSeenId(long notificationUserSeenId) {
        this.notificationUserSeenId = notificationUserSeenId;
    }

    @ManyToOne
    @JoinColumn(name = "Notification_Id")
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @ManyToOne
    @JoinColumn(name = "User_Id")
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
