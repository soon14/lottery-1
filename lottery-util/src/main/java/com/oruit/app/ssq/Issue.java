package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/7.
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 查询开奖课期数
 * @author
 * @create 2017-09-07 15:22
 */
@XmlAccessorType(value= XmlAccessType.FIELD)
public class Issue {
    @XmlElement
    private String game;
    @XmlElement
    private String gameIssue;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;
    @XmlElement
    private String drawCode;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDrawCode() {
        return drawCode;
    }

    public void setDrawCode(String drawCode) {
        this.drawCode = drawCode;
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

    @Override
    public String toString() {
        return "Issue{" +
                "game='" + game + '\'' +
                ", gameIssue='" + gameIssue + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", drawCode='" + drawCode + '\'' +
                '}';
    }
}
