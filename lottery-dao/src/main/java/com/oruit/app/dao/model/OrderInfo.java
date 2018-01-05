package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private Integer orderId;

    private String userId;

    private Short payType;

    private Short state;

    private BigDecimal total;

    private BigDecimal discount;

    private BigDecimal amount;

    private Date payTime;

    private Date updateTime;

    private Date createTime;

    private BigDecimal deliveryAmount;

    private Integer deliveryStatus;

    private Date deliveryTime;

    private String remark;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public BigDecimal getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(BigDecimal deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderId=" + orderId +
                ", userId='" + userId + '\'' +
                ", payType=" + payType +
                ", state=" + state +
                ", total=" + total +
                ", discount=" + discount +
                ", amount=" + amount +
                ", payTime=" + payTime +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", deliveryAmount=" + deliveryAmount +
                ", deliveryStatus=" + deliveryStatus +
                ", deliveryTime=" + deliveryTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}