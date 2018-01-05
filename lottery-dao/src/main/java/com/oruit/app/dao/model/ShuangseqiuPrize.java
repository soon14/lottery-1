package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class ShuangseqiuPrize {
    private Integer openId;

    private Integer caipiaoId;

    private String openDate;

    private String deadLine;

    private String issueNo;

    private String number;

    private String referNumber;

    private BigDecimal saleAmount;

    private BigDecimal totalMoney;

    private Date createTime;

    public Integer getOpenId() {
        return openId;
    }

    public void setOpenId(Integer openId) {
        this.openId = openId;
    }

    public Integer getCaipiaoId() {
        return caipiaoId;
    }

    public void setCaipiaoId(Integer caipiaoId) {
        this.caipiaoId = caipiaoId;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate == null ? null : openDate.trim();
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine == null ? null : deadLine.trim();
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo == null ? null : issueNo.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReferNumber() {
        return referNumber;
    }

    public void setReferNumber(String referNumber) {
        this.referNumber = referNumber == null ? null : referNumber.trim();
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}