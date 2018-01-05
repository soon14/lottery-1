package com.oruit.app.dao.model;

import java.util.Date;

public class UserForbidden {
    private Integer id;

    private String userId;

    private Integer forbiddenType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getForbiddenType() {
        return forbiddenType;
    }

    public void setForbiddenType(Integer forbiddenType) {
        this.forbiddenType = forbiddenType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}