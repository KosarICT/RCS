package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 2/1/2017.
 */
@Entity
public class NotificationAnswer {
    private long notificationAnswerId;
    private Notification notification;
    private String datetime;
    private String body;
    private Users submitUser;
    private Users assignUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationAnswer_Id")
    public long getNotificationAnswerId() {
        return notificationAnswerId;
    }

    public void setNotificationAnswerId(long notificationAnswerId) {
        this.notificationAnswerId = notificationAnswerId;
    }

    @ManyToOne
    @JoinColumn(name = "Notification_Id")
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @Basic
    @Column(name = "Datetime")
    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "Body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne
    @JoinColumn(name = "SubmitUser_Id")
    public Users getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(Users submitUser) {
        this.submitUser = submitUser;
    }

    @ManyToOne
    @JoinColumn(name = "AssignUser_Id")
    public Users getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(Users assignUser) {
        this.assignUser = assignUser;
    }


}
