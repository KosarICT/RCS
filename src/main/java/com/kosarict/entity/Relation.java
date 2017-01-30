package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
@Table(name = "Relation", schema = "dbo", catalog = "Monitoring")
public class Relation {
    private short relationId;
    private String title;
    private boolean enable;

    @Id
    @Column(name = "Relation_Id")
    public short getRelationId() {
        return relationId;
    }

    public void setRelationId(short relationId) {
        this.relationId = relationId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relation relation = (Relation) o;

        if (relationId != relation.relationId) return false;
        if (enable != relation.enable) return false;
        if (title != null ? !title.equals(relation.title) : relation.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) relationId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (enable ? 1 : 0);
        return result;
    }
}
