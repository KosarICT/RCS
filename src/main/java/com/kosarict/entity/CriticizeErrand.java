package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Younes on 18/12/2016.
 */
@Entity
public class CriticizeErrand {
    private long criticismErranId;
    private long criticizeId;
    private Integer createUserId;
    private int assignedUserId;
    private String submitDate;
    private boolean isView;
    private String description;

    @Id
    @Column(name = "CriticismErran_Id")
    public long getCriticismErranId() {
        return criticismErranId;
    }

    public void setCriticismErranId(long criticismErranId) {
        this.criticismErranId = criticismErranId;
    }

    @Basic
    @Column(name = "Criticize_Id")
    public long getCriticizeId() {
        return criticizeId;
    }

    public void setCriticizeId(long criticizeId) {
        this.criticizeId = criticizeId;
    }

    @Basic
    @Column(name = "CreateUser_Id")
    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    @Basic
    @Column(name = "AssignedUser_Id")
    public int getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(int assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    @Basic
    @Column(name = "SubmitDate")
    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    @Basic
    @Column(name = "IsView")
    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
