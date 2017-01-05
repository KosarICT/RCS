package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Sadegh-Pc on 12/5/2016.
 */
@Entity
public class SendType {
    private short sendTypeId;
    private String title;
    private String description;
    private boolean enable;

    @Id
    @Column(name = "SendType_Id")
    public short getSendTypeId() {
        return sendTypeId;
    }

    public void setSendTypeId(short sendTypeId) {
        this.sendTypeId = sendTypeId;
    }

    @Basic
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "Enable")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SendType sendType = (SendType) o;

        if (sendTypeId != sendType.sendTypeId) return false;
        if (enable != sendType.enable) return false;
        if (title != null ? !title.equals(sendType.title) : sendType.title != null) return false;
        if (description != null ? !description.equals(sendType.description) : sendType.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) sendTypeId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (enable ? 1 : 0);
        return result;
    }
}
