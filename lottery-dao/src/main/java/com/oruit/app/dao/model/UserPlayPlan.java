package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserPlayPlan {
    private Integer planId;

    private String userId;

    private Integer caipiaoId;

    private Integer playType;

    private Integer status;

    private String danma;

    private String tuoma;

    private String hongqiu;

    private String lanqiu;

    private String playMethod;

    private String number;

    private Integer qishu;

    private Integer beishu;

    private Integer zhushu;

    private BigDecimal amount;

    private String caipiaoOrderSubId;

    private Date createTime;

    private Date updateTime;

    private Integer type;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getCaipiaoId() {
        return caipiaoId;
    }

    public void setCaipiaoId(Integer caipiaoId) {
        this.caipiaoId = caipiaoId;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDanma() {
        return danma;
    }

    public void setDanma(String danma) {
        this.danma = danma == null ? null : danma.trim();
    }

    public String getTuoma() {
        return tuoma;
    }

    public void setTuoma(String tuoma) {
        this.tuoma = tuoma == null ? null : tuoma.trim();
    }

    public String getHongqiu() {
        return hongqiu;
    }

    public void setHongqiu(String hongqiu) {
        this.hongqiu = hongqiu == null ? null : hongqiu.trim();
    }

    public String getLanqiu() {
        return lanqiu;
    }

    public void setLanqiu(String lanqiu) {
        this.lanqiu = lanqiu == null ? null : lanqiu.trim();
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

    public String getCaipiaoOrderSubId() {
        return caipiaoOrderSubId;
    }

    public void setCaipiaoOrderSubId(String caipiaoOrderSubId) {
        this.caipiaoOrderSubId = caipiaoOrderSubId == null ? null : caipiaoOrderSubId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}