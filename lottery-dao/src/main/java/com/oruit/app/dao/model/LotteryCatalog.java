package com.oruit.app.dao.model;

import java.util.Date;

public class LotteryCatalog {
    private Integer lotteryCatalogId;

    private String lotteryCatalogName;

    private String lotteryCatalogIcon;

    private String lotteryDayIcon;

    private String intro;

    private Integer displayOrder;

    private Integer state;

    private Date createTime;

    public Integer getLotteryCatalogId() {
        return lotteryCatalogId;
    }

    public void setLotteryCatalogId(Integer lotteryCatalogId) {
        this.lotteryCatalogId = lotteryCatalogId;
    }

    public String getLotteryCatalogName() {
        return lotteryCatalogName;
    }

    public void setLotteryCatalogName(String lotteryCatalogName) {
        this.lotteryCatalogName = lotteryCatalogName == null ? null : lotteryCatalogName.trim();
    }

    public String getLotteryCatalogIcon() {
        return lotteryCatalogIcon;
    }

    public void setLotteryCatalogIcon(String lotteryCatalogIcon) {
        this.lotteryCatalogIcon = lotteryCatalogIcon == null ? null : lotteryCatalogIcon.trim();
    }

    public String getLotteryDayIcon() {
        return lotteryDayIcon;
    }

    public void setLotteryDayIcon(String lotteryDayIcon) {
        this.lotteryDayIcon = lotteryDayIcon == null ? null : lotteryDayIcon.trim();
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