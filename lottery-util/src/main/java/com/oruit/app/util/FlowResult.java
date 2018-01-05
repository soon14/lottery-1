/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

/**
 * 流量接口返回的对象
 * @author Liuk
 */
public class FlowResult {
    private String msgid;
    
    private String status;
    
    private String description;

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FlowResult{" + "msgid=" + msgid + ", status=" + status + ", description=" + description + '}';
    }
    
    
    
}
