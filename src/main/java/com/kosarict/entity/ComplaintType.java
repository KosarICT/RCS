package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
public class ComplaintType {
    private short complaintTypeId;
    private String title;
    private String description;
    private boolean enable;
    private Integer responceTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ComplaintType_Id")
    public short getComplaintTypeId() {
        return complaintTypeId;
    }

    public void setComplaintTypeId(short complaintTypeId) {
        this.complaintTypeId = complaintTypeId;
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

    @Basic
    @Column(name = "ResponceTime")
    public Integer getResponceTime() {
        return responceTime;
    }

    public void setResponceTime(Integer responceTime) {
        this.responceTime = responceTime;
    }

}
