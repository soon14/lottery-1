package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserStatisticsLog {
    private Long id;

    private String userId;

    private BigDecimal totalAmount;

    private BigDecimal balanceAmount;

    private BigDecimal winningTotalAmount;

    private BigDecimal winningBalabceAmount;

    private Integer tudiNum;

    private Integer tusunNum;

    private Integer winningNum;

    private Integer bettingNum;

    private Date createTime;

    private Integer totalPoints;

    private Integer balancePoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getWinningTotalAmount() {
        return winningTotalAmount;
    }

    public void setWinningTotalAmount(BigDecimal winningTotalAmount) {
        this.winningTotalAmount = winningTotalAmount;
    }

    public BigDecimal getWinningBalabceAmount() {
        return winningBalabceAmount;
    }

    public void setWinningBalabceAmount(BigDecimal winningBalabceAmount) {
        this.winningBalabceAmount = winningBalabceAmount;
    }

    public Integer getTudiNum() {
        return tudiNum;
    }

    public void setTudiNum(Integer tudiNum) {
        this.tudiNum = tudiNum;
    }

    public Integer getTusunNum() {
        return tusunNum;
    }

    public void setTusunNum(Integer tusunNum) {
        this.tusunNum = tusunNum;
    }

    public Integer getWinningNum() {
        return winningNum;
    }

    public void setWinningNum(Integer winningNum) {
        this.winningNum = winningNum;
    }

    public Integer getBettingNum() {
        return bettingNum;
    }

    public void setBettingNum(Integer bettingNum) {
        this.bettingNum = bettingNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getBalancePoints() {
        return balancePoints;
    }

    public void setBalancePoints(Integer balancePoints) {
        this.balancePoints = balancePoints;
    }
}