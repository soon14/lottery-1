package com.oruit.app.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class ShuangseqiuOrder {
    private String caipiaoOrderSubId;

    private String caipiaoOrderSubCode;

    private String caipiaoOrderId;

    private String issueNo;

    private Integer playType;

    private String danma;

    private String tuoma;

    private String hongqiu;

    private String lanqiu;

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
        return "ShuangseqiuOrder{" +
                "caipiaoOrderSubId='" + caipiaoOrderSubId + '\'' +
                ", caipiaoOrderSubCode='" + caipiaoOrderSubCode + '\'' +
                ", caipiaoOrderId='" + caipiaoOrderId + '\'' +
                ", issueNo='" + issueNo + '\'' +
                ", playType=" + playType +
                ", danma='" + danma + '\'' +
                ", tuoma='" + tuoma + '\'' +
                ", hongqiu='" + hongqiu + '\'' +
                ", lanqiu='" + lanqiu + '\'' +
                ", qishu=" + qishu +
                ", beishu=" + beishu +
                ", zhushu=" + zhushu +
                ", amount=" + amount +
                ", redpacketAmount=" + redpacketAmount +
                ", createTime=" + createTime +
                '}';
    }
}