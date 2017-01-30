package com.kosarict.entity;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
@Table(name = "Role", schema = "dbo", catalog = "Monitoring")
public class Role {
    private short roleId;
    private String name;
    private String description;
    private boolean enable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
