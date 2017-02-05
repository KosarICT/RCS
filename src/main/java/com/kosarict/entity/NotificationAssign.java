package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 2/1/2017.
 */
@Entity
public class NotificationAssign {
    private long notificationAssignId;
    private Notification notification;
    private Users user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationAssign_Id")
    public long getNotificationAssignId() {
        return notificationAssignId;
    }

    public void setNotificationAssignId(long notificationAssignId) {
        this.notificationAssignId = notificationAssignId;
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
