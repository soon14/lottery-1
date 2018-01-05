package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/7.
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 适用于查票接口
 * 投注接口
 * 保存中奖明细
 * @author
 * @create 2017-09-07 11:11
 */
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Scheme {
    @XmlElement
    private String schemeId;

    @XmlElement
    private String game;

    @XmlElement
    private String gameIssue;

    @XmlElement
    private String betType;

    @XmlElement
    private String betMulti;

    @XmlElement
    private String betMoney;

    @XmlElement
    private String betCode;
    @XmlElement
    private String prize;
    @XmlElement
    private String totalPrize;
    @XmlElement
    private String status;

    public String getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(String totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getGameIssue() {
        return gameIssue;
    }

    public void setGameIssue(String gameIssue) {
        this.gameIssue = gameIssue;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public String getBetMulti() {
        return betMulti;
    }

    public void setBetMulti(String betMulti) {
        this.betMulti = betMulti;
    }

    public String getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(String betMoney) {
        this.betMoney = betMoney;
    }

    public String getBetCode() {
        return betCode;
    }

    public void setBetCode(String betCode) {
        this.betCode = betCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Scheme{" +
                "schemeId='" + schemeId + '\'' +
                ", game='" + game + '\'' +
                ", gameIssue='" + gameIssue + '\'' +
                ", betType='" + betType + '\'' +
                ", betMulti='" + betMulti + '\'' +
                ", betMoney='" + betMoney + '\'' +
                ", betCode='" + betCode + '\'' +
                ", prize='" + prize + '\'' +
                ", totalPrize='" + totalPrize + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
