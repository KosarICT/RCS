package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/26/2017.
 */
@Entity
public class RolePermission {
    private int rolePermissionId;
    private Permission permission;
    private Role role;

    @Id
    @Column(name = "RolePermission_Id")
    public int getSectionPermissionId() {
        return rolePermissionId;
    }

    public void setSectionPermissionId(int sectionPermissionId) {
        this.rolePermissionId = sectionPermissionId;
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
    @JoinColumn (name = "Role_Id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
