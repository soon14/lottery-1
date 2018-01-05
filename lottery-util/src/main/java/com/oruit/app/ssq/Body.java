package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/12.
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author @wyt
 * @create 2017-09-12 16:50
 */
@XmlRootElement
@XmlAccessorType(value= XmlAccessType.FIELD)
public class Body {
    @XmlElement(name="scheme")
    private List<Scheme> schemes;//用于投注
    @XmlElement
    private Scheme scheme;//查票，保存中奖明细
    @XmlElement
    private Issue issue;//查询开奖和期数
    @XmlElement
    private String totalPrize;
    @XmlElement
    private String responseCode;

    public String getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(String totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<Scheme> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<Scheme> schemes) {
        this.schemes = schemes;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @Override
    public String toString() {
        return "Body{" +
                "schemes=" + schemes +
                ", scheme=" + scheme +
                ", issue=" + issue +
                ", totalPrize='" + totalPrize + '\'' +
                ", responseCode='" + responseCode + '\'' +
                '}';
    }
}
