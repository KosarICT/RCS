package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/26/2017.
 */
@Entity
public class SectionPermission {
    private int sectionPermissionId;
    private Permission permission;
    private Section section;

    @Id
    @Column(name = "SectionPermission_Id")
    public int getSectionPermissionId() {
        return sectionPermissionId;
    }

    public void setSectionPermissionId(int sectionPermissionId) {
        this.sectionPermissionId = sectionPermissionId;
    }

    @ManyToOne
    @JoinColumn (name = "Permission_Id")
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @ManyToOne
    @JoinColumn (name = "Section_Id")
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

}
