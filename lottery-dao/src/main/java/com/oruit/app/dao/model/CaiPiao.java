package com.oruit.app.dao.model;

import java.util.Date;

public class CaiPiao {
    private Integer caipiaoId;

    private String caipiaoName;

    private String caipiaoIcon;

    private String caipiaoDayIcon;

    private String intro;

    private Integer displayOrder;

    private Integer state;

    private Date createTime;

    public Integer getCaipiaoId() {
        return caipiaoId;
    }

    public void setCaipiaoId(Integer caipiaoId) {
        this.caipiaoId = caipiaoId;
    }

    public String getCaipiaoName() {
        return caipiaoName;
    }

    public void setCaipiaoName(String caipiaoName) {
        this.caipiaoName = caipiaoName == null ? null : caipiaoName.trim();
    }

    public String getCaipiaoIcon() {
        return caipiaoIcon;
    }

    public void setCaipiaoIcon(String caipiaoIcon) {
        this.caipiaoIcon = caipiaoIcon == null ? null : caipiaoIcon.trim();
    }

    public String getCaipiaoDayIcon() {
        return caipiaoDayIcon;
    }

    public void setCaipiaoDayIcon(String caipiaoDayIcon) {
        this.caipiaoDayIcon = caipiaoDayIcon == null ? null : caipiaoDayIcon.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}