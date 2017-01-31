package com.kosarict.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Ali-Pc on 1/26/2017.
 */
@Entity
public class Permission {
    private short permissionId;
    private String title;
    private boolean enable;
    private List<SectionPermission> sectionPermissionList;

    @Id
    @Column(name = "Permission_Id")
    public short getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(short permissionId) {
        this.permissionId = permissionId;
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

    @OneToMany
    @JoinColumn(name = "Permission_Id")
    public List<SectionPermission> getSectionPermissionList() {
        return sectionPermissionList;
    }

    public void setSectionPermissionList(List<SectionPermission> sectionPermissionList) {
        this.sectionPermissionList = sectionPermissionList;
    }
}
