package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class Redacket {
    private Integer redpacketId;

    private String redpacketName;

    private BigDecimal amount;

    private String intro;

    private String normalImage;

    private String overImage;

    private Integer deleted;

    private Integer validDays;

    private Date createTime;

    public Integer getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(Integer redpacketId) {
        this.redpacketId = redpacketId;
    }

    public String getRedpacketName() {
        return redpacketName;
    }

    public void setRedpacketName(String redpacketName) {
        this.redpacketName = redpacketName == null ? null : redpacketName.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getNormalImage() {
        return normalImage;
    }

    public void setNormalImage(String normalImage) {
        this.normalImage = normalImage == null ? null : normalImage.trim();
    }

    public String getOverImage() {
        return overImage;
    }

    public void setOverImage(String overImage) {
        this.overImage = overImage == null ? null : overImage.trim();
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}