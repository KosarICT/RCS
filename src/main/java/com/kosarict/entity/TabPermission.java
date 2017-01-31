package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/26/2017.
 */
@Entity
public class TabPermission {
    private int tabPermissionId;
    private Permission permission;
    private Tab tab;

    @Id
    @Column(name = "TabPermission_Id")
    public int getTabPermissionId() {
        return tabPermissionId;
    }

    public void setTabPermissionId(int tabPermissionId) {
        this.tabPermissionId = tabPermissionId;
    }

    @ManyToOne
    @JoinColumn(name = "Permission_Id")
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
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
