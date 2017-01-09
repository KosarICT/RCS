package com.kosarict.entity;

import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/6/2017.
 */
@Entity
@Table(name = "UserRole", schema = "dbo", catalog = "Monitoring")
public class UserRole {
    private int userRoleId;
    private Users users;
    private Role role;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserRole_Id")
    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "User_Id")
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @ManyToOne
    @JoinColumn(name = "Role_Id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
