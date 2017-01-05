package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
public class ComplainantRelation {
    private long complainantRelationId;
    private short relationId;
    private long complainId;
    private String firstName;
    private String lastName;
    private String nationalCode;

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
    @Column(name = "Complain_Id")
    public long getComplainId() {
        return complainId;
    }

    public void setComplainId(long complainId) {
        this.complainId = complainId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplainantRelation that = (ComplainantRelation) o;

        if (complainantRelationId != that.complainantRelationId) return false;
        if (relationId != that.relationId) return false;
        if (complainId != that.complainId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (nationalCode != null ? !nationalCode.equals(that.nationalCode) : that.nationalCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (complainantRelationId ^ (complainantRelationId >>> 32));
        result = 31 * result + (int) relationId;
        result = 31 * result + (int) (complainId ^ (complainId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (nationalCode != null ? nationalCode.hashCode() : 0);
        return result;
    }
}
