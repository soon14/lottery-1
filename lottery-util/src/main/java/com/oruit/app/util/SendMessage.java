package com.oruit.app.util;/**
 * Created by wyt on 2017-10-26.
 */
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import cn.jsms.api.template.SendTempSMSResult;
import cn.jsms.api.template.TemplatePayload;
import com.oruit.app.util.web.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author @wyt
 * @create 2017-10-26 15:27
 */
public class SendMessage {

    protected static final Logger LOG = LoggerFactory.getLogger(SendMessage.class);


    private static final String appkey = "c6f0bee18a34b033bbd9e147";
    private static final String masterSecret = "b14485147da95f12b8d72709";
    public static void SendSMSCode(String mobile) {
        SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder().setMobileNumber(mobile)
                .setTempId(145290)
                .addTempPara("code","1234")
                .build();
        try {
            SendSMSResult res = client.sendSMSCode(payload);
            System.out.println("--------------"+res.toString());
            LOG.info("----------------"+res.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    public static void SendTemplateSMS(String mobile,String code) {

        SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder().setMobileNumber(mobile)
                .setTempId(145728)
                .addTempPara("code", code)
                .build();
        try {
            SendSMSResult res = client.sendTemplateSMS(payload);
            LOG.info(res.toString());
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        }
    }
    public static void SendTemplateWinSMS(String mobile,String issueno,String name) {
        SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder().setMobileNumber(mobile)
                .setTempId(145706)
                .addTempPara("issueno", issueno)
                .addTempPara("name", name)
                .build();
        try {
            SendSMSResult res = client.sendTemplateSMS(payload);
            LOG.info(res.toString());
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        }
    }
    public void testCreateTemplate() {
        try {
            SMSClient client = new SMSClient(masterSecret, appkey);
            TemplatePayload payload = TemplatePayload.newBuilder()
                    .setTemplate("您好，您的验证码是{{code}}，2分钟内有效！")
                    .setType(1)
                    .setTTL(120)
                    .setRemark("验证短信")
                    .build();
            SendTempSMSResult result = client.createTemplate(payload);
            LOG.info(result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        //SendSMSCode("13121697601");
        //SendTemplateWinSMS("13121697601","20171109001","快乐十分");
        SendTemplateWinSMS("15649879760","20170101","快乐十分");
    }
}
