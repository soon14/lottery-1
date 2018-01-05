package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class ShuangseqiuPrizeItem {
    private Integer id;

    private Integer openId;

    private String prize;

    private String prizeName;

    private String winningRequire;

    private Integer winningNum;

    private BigDecimal singleBonus;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOpenId() {
        return openId;
    }

    public void setOpenId(Integer openId) {
        this.openId = openId;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize == null ? null : prize.trim();
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public String getWinningRequire() {
        return winningRequire;
    }

    public void setWinningRequire(String winningRequire) {
        this.winningRequire = winningRequire == null ? null : winningRequire.trim();
    }

    public Integer getWinningNum() {
        return winningNum;
    }

    public void setWinningNum(Integer winningNum) {
        this.winningNum = winningNum;
    }

    public BigDecimal getSingleBonus() {
        return singleBonus;
    }

    public void setSingleBonus(BigDecimal singleBonus) {
        this.singleBonus = singleBonus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}