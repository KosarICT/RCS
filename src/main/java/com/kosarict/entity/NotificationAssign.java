package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Ali-Pc on 2/1/2017.
 */
@Entity
public class NotificationAssign {
    private long notificationAssignId;
    private long notificationId;
    private int userId;

    @Id
    @Column(name = "NotificationAssign_Id")
    public long getNotificationAssignId() {
        return notificationAssignId;
    }

    public void setNotificationAssignId(long notificationAssignId) {
        this.notificationAssignId = notificationAssignId;
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
    @Column(name = "User_Id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationAssign that = (NotificationAssign) o;

        if (notificationAssignId != that.notificationAssignId) return false;
        if (notificationId != that.notificationId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (notificationAssignId ^ (notificationAssignId >>> 32));
        result = 31 * result + (int) (notificationId ^ (notificationId >>> 32));
        result = 31 * result + userId;
        return result;
    }
}
