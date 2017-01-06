package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
public class Role {
    private short roleId;
    private String name;
    private byte[] description;
    private boolean enable;

    @Id
    @Column(name = "Role_Id")
    public short getRoleId() {
        return roleId;
    }

    public void setRoleId(short roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Description")
    public byte[] getDescription() {
        return description;
    }

    public void setDescription(byte[] description) {
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

}
