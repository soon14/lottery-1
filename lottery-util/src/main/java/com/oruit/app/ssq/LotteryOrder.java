package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/7.
 */

import javax.xml.bind.annotation.*;

/**
 * 出票接口
 * @author wyt
 * @create 2017-09-07 10:47
 */
@XmlRootElement(name="message")
@XmlAccessorType(XmlAccessType.FIELD)
public class LotteryOrder {
    @XmlElement
    private Head head;
    @XmlElement
    private Body body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "LotteryOrder{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
