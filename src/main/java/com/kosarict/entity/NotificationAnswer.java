package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Ali-Pc on 2/1/2017.
 */
@Entity
public class NotificationAnswer {
    private long notificationAnswerId;
    private long notificationId;
    private String datetime;
    private String body;
    private int submitUserId;

    @Id
    @Column(name = "NotificationAnswer_Id")
    public long getNotificationAnswerId() {
        return notificationAnswerId;
    }

    public void setNotificationAnswerId(long notificationAnswerId) {
        this.notificationAnswerId = notificationAnswerId;
    }

    @Basic
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
    @Column(name = "Body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "SubmitUser_Id")
    public int getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(int submitUserId) {
        this.submitUserId = submitUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationAnswer that = (NotificationAnswer) o;

        if (notificationAnswerId != that.notificationAnswerId) return false;
        if (notificationId != that.notificationId) return false;
        if (submitUserId != that.submitUserId) return false;
        if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (notificationAnswerId ^ (notificationAnswerId >>> 32));
        result = 31 * result + (int) (notificationId ^ (notificationId >>> 32));
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + submitUserId;
        return result;
    }
}
