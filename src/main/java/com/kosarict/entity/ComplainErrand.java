package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
@Entity
public class ComplainErrand {
    private long complainErrandId;
    private String submitDate;
    private boolean isView;
    private String description;

    private Complain complain;
    private Users createUser;
    private Users assignedUser;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ComplainErrand_Id")
    public long getComplainErrandId() {
        return complainErrandId;
    }

    public void setComplainErrandId(long complainErrandId) {
        this.complainErrandId = complainErrandId;
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

    @ManyToOne
    @JoinColumn(name = "CreateUser_Id")
    public Users getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Users createUser) {
        this.createUser = createUser;
    }

    @ManyToOne
    @JoinColumn(name = "AssignedUser_Id")
    public Users getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(Users assignedUser) {
        this.assignedUser = assignedUser;
    }

    @ManyToOne
    @JoinColumn(name = "Complain_Id")
    public Complain getComplain() {
        return complain;
    }

    public void setComplain(Complain complain) {
        this.complain = complain;
    }

}
