package com.oruit.app.dao.model;

import java.util.Date;

public class PushConfig {
    private String pushCode;

    private String userId;

    private String pushName;

    private String pushType;

    private Integer pushEnable;

    private String parentCode;

    private String pushItems;

    private Date createTime;

    public String getPushCode() {
        return pushCode;
    }

    public void setPushCode(String pushCode) {
        this.pushCode = pushCode == null ? null : pushCode.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPushName() {
        return pushName;
    }

    public void setPushName(String pushName) {
        this.pushName = pushName == null ? null : pushName.trim();
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType == null ? null : pushType.trim();
    }

    public Integer getPushEnable() {
        return pushEnable;
    }

    public void setPushEnable(Integer pushEnable) {
        this.pushEnable = pushEnable;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getPushItems() {
        return pushItems;
    }

    public void setPushItems(String pushItems) {
        this.pushItems = pushItems == null ? null : pushItems.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}