package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
@Table(name = "TicketUserSeen", schema = "dbo", catalog = "Monitoring")
public class ComplainantRelation {
    private long complainantRelationId;
    private short relationId;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private long ticketId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ComplainantRelation_Id")
    public long getComplainantRelationId() {
        return complainantRelationId;
    }

    public void setComplainantRelationId(long complainantRelationId) {
        this.complainantRelationId = complainantRelationId;
    }

    @Basic
    @Column(name = "Relation_Id")
    public short getRelationId() {
        return relationId;
    }

    public void setRelationId(short relationId) {
        this.relationId = relationId;
    }


    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "NationalCode")
    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }


    @Basic
    @Column(name = "Ticket_Id")
    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }
}
