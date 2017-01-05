package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Entity
@Table(name = "Tab", schema = "dbo", catalog = "Monitoring")
public class Tab {
    private short tabId;
    private Integer parent;
    private String name;
    private String title;
    private String url;
    private String icon;
    private Short position;
    private Boolean isShowInMobile;
    private Boolean enable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tab_Id")
    public short getTabId() {
        return tabId;
    }

    public void setTabId(short tabId) {
        this.tabId = tabId;
    }

    @Basic
    @Column(name = "Parent")
    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
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
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "Icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "Position")
    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    @Basic
    @Column(name = "IsShowInMobile")
    public Boolean getIsShowInMobile() {
        return isShowInMobile;
    }

    public void setIsShowInMobile(Boolean isShowInMobile) {
        this.isShowInMobile = isShowInMobile;
    }

    @Basic
    @Column(name = "Enable")
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
