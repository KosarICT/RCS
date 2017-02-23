package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 2/21/2017.
 */
@Entity
@Table(name = "Request", schema = "dbo", catalog = "Monitoring")
public class Request {
    private int requestId;

    private Users user;
    private Hospital hospital;
    private RequestStatus requestStatus;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Request_Id")
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    @ManyToOne
    @JoinColumn(name = "User_Id")
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "Hospital_Id")
    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    @ManyToOne
    @JoinColumn (name = "RequestStatus_Id")
    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

}
