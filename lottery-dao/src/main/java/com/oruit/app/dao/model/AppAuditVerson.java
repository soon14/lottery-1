package com.oruit.app.dao.model;

import java.util.Date;

public class AppAuditVerson {
    private Integer id;

    private String appVer;

    private Integer iosState;

    private Integer androidState;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer == null ? null : appVer.trim();
    }

    public Integer getIosState() {
        return iosState;
    }

    public void setIosState(Integer iosState) {
        this.iosState = iosState;
    }

    public Integer getAndroidState() {
        return androidState;
    }

    public void setAndroidState(Integer androidState) {
        this.androidState = androidState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}