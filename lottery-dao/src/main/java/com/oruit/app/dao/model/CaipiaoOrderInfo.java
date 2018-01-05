package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class CaipiaoOrderInfo {
    private String caipiaoOrderId;

    private String caipiaoOrderCode;

    private String userId;

    private Integer payType;

    private Integer state;

    private Integer caipiaoId;

    private BigDecimal total;

    private BigDecimal amount;

    private Integer usedRedpacket;

    private Integer redpacketId;

    private BigDecimal redpacketAmount;

    private Date payTime;

    private Date updateTime;

    private Date createTime;

    public String getCaipiaoOrderId() {
        return caipiaoOrderId;
    }

    public void setCaipiaoOrderId(String caipiaoOrderId) {
        this.caipiaoOrderId = caipiaoOrderId == null ? null : caipiaoOrderId.trim();
    }

    public String getCaipiaoOrderCode() {
        return caipiaoOrderCode;
    }

    public void setCaipiaoOrderCode(String caipiaoOrderCode) {
        this.caipiaoOrderCode = caipiaoOrderCode == null ? null : caipiaoOrderCode.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCaipiaoId() {
        return caipiaoId;
    }

    public void setCaipiaoId(Integer caipiaoId) {
        this.caipiaoId = caipiaoId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getUsedRedpacket() {
        return usedRedpacket;
    }

    public void setUsedRedpacket(Integer usedRedpacket) {
        this.usedRedpacket = usedRedpacket;
    }

    public Integer getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(Integer redpacketId) {
        this.redpacketId = redpacketId;
    }

    public BigDecimal getRedpacketAmount() {
        return redpacketAmount;
    }

    public void setRedpacketAmount(BigDecimal redpacketAmount) {
        this.redpacketAmount = redpacketAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}