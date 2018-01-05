package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class KuaileshifenOrder {
    private String caipiaoOrderSubId;

    private String caipiaoOrderSubCode;

    private String caipiaoOrderId;

    private String issueNo;

    private Integer playType;

    private String playMethod;

    private String number;

    private Integer qishu;

    private Integer beishu;

    private Integer zhushu;

    private BigDecimal amount;

    private BigDecimal redpacketAmount;

    private Date createTime;

    public String getCaipiaoOrderSubId() {
        return caipiaoOrderSubId;
    }

    public void setCaipiaoOrderSubId(String caipiaoOrderSubId) {
        this.caipiaoOrderSubId = caipiaoOrderSubId == null ? null : caipiaoOrderSubId.trim();
    }

    public String getCaipiaoOrderSubCode() {
        return caipiaoOrderSubCode;
    }

    public void setCaipiaoOrderSubCode(String caipiaoOrderSubCode) {
        this.caipiaoOrderSubCode = caipiaoOrderSubCode == null ? null : caipiaoOrderSubCode.trim();
    }

    public String getCaipiaoOrderId() {
        return caipiaoOrderId;
    }

    public void setCaipiaoOrderId(String caipiaoOrderId) {
        this.caipiaoOrderId = caipiaoOrderId == null ? null : caipiaoOrderId.trim();
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo == null ? null : issueNo.trim();
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public String getPlayMethod() {
        return playMethod;
    }

    public void setPlayMethod(String playMethod) {
        this.playMethod = playMethod == null ? null : playMethod.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Integer getQishu() {
        return qishu;
    }

    public void setQishu(Integer qishu) {
        this.qishu = qishu;
    }

    public Integer getBeishu() {
        return beishu;
    }

    public void setBeishu(Integer beishu) {
        this.beishu = beishu;
    }

    public Integer getZhushu() {
        return zhushu;
    }

    public void setZhushu(Integer zhushu) {
        this.zhushu = zhushu;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRedpacketAmount() {
        return redpacketAmount;
    }

    public void setRedpacketAmount(BigDecimal redpacketAmount) {
        this.redpacketAmount = redpacketAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "KuaileshifenOrder{" +
                "caipiaoOrderSubId='" + caipiaoOrderSubId + '\'' +
                ", caipiaoOrderSubCode='" + caipiaoOrderSubCode + '\'' +
                ", caipiaoOrderId='" + caipiaoOrderId + '\'' +
                ", issueNo='" + issueNo + '\'' +
                ", playType=" + playType +
                ", playMethod='" + playMethod + '\'' +
                ", number='" + number + '\'' +
                ", qishu=" + qishu +
                ", beishu=" + beishu +
                ", zhushu=" + zhushu +
                ", amount=" + amount +
                ", redpacketAmount=" + redpacketAmount +
                ", createTime=" + createTime +
                '}';
    }
}