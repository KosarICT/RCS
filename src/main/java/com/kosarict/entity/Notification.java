package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 2/1/2017.
 */
@Entity
@Table(name = "Notification", schema = "dbo", catalog = "Monitoring")
public class Notification {
    private long notificationId;
    private String datetime;
    private String subject;
    private String body;
    private Users submitUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Notification_Id")
    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
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
    @Column(name = "Subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
}
