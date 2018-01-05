package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/12.
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author @wyt
 * @create 2017-09-12 16:31
 */
@XmlAccessorType(value= XmlAccessType.FIELD)
public class Head {
    @XmlElement
    private  String messageId;
    @XmlElement
    private String agentId;
    @XmlElement
    private String timestamp;
    @XmlElement
    private String digest;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    @Override
    public String toString() {
        return "Head{" +
                "messageId='" + messageId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", digest='" + digest + '\'' +
                '}';
    }
}
