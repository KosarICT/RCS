package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 2/21/2017.
 */
@Entity
@Table(name = "RequestStatus", schema = "dbo", catalog = "Monitoring")
public class RequestStatus {
    private int requestStatusId;
    private String title;
    private boolean enable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequestStatus_Id")
    public int getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(int requestStatusId) {
        this.requestStatusId = requestStatusId;
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
    @Column(name = "Enable")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
