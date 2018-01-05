package com.oruit.app.dao.model;

import java.util.Date;

public class WechatTradeLog {
    private Integer logId;

    private String orderId;

    private String wechatPrepayId;

    private Date createTime;

    private String content;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWechatPrepayId() {
        return wechatPrepayId;
    }

    public void setWechatPrepayId(String wechatPrepayId) {
        this.wechatPrepayId = wechatPrepayId == null ? null : wechatPrepayId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}