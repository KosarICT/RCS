package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/26/2017.
 */
@Entity
@Table(name = "TabRole", schema = "dbo", catalog = "Monitoring")
public class TabRole {
    private int tabRoleId;
    private Role role;
    private Tab tab;

    @Id
    @Column(name = "TabRole_Id")
    public int getTabRoleId() {
        return tabRoleId;
    }

    public void setTabRoleId(int tabRoleId) {
        this.tabRoleId = tabRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "Role_Id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "Tab_Id")
    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }


}
