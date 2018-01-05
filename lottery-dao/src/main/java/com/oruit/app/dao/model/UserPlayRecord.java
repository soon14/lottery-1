package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserPlayRecord {
    private Integer playId;

    private String userId;

    private Integer caipiaoId;

    private String issueNo;

    private Integer playType;

    private Integer status;

    private BigDecimal winningMoney;

    private String prizeName;

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

    private String caipiaoOrderSubCode;

    private Date orderTime;

    private Integer planId;

    private Date createTime;

    private Integer queryLottery;

    public Integer getQueryLottery() {
        return queryLottery;
    }

    public void setQueryLottery(Integer queryLottery) {
        this.queryLottery = queryLottery;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getWinningMoney() {
        return winningMoney;
    }

    public void setWinningMoney(BigDecimal winningMoney) {
        this.winningMoney = winningMoney;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
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

    public String getCaipiaoOrderSubCode() {
        return caipiaoOrderSubCode;
    }

    public void setCaipiaoOrderSubCode(String caipiaoOrderSubCode) {
        this.caipiaoOrderSubCode = caipiaoOrderSubCode == null ? null : caipiaoOrderSubCode.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}