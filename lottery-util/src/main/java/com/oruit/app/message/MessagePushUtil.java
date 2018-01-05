/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.message;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.logic.util.ConstUtil;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Liuk
 */
public class MessagePushUtil {

    public static final String TITLE = "系统信息";

    public static void main(String[] args) {
        
    }

    /**
     * 推送消息V3版本
     *
     * @param userList
     * @param map
     * @param Content
     * @param title
     * @param type 1：安卓 2：iOS 3：两个都推
     */
    public static String SendPushV3(List<String> userList, Map<String, String> map, String Content, String title, Integer type) {
        PushPayload payloadAndroid = null;
        PushPayload payloadIos = null;
        switch (type) {
            case 1:
                payloadAndroid = PushObject_android(userList, map, Content, title);
                break;
            case 2:
                payloadIos = PushObject_ios(userList, map, Content, title);
                break;
            case 3:
                payloadAndroid = PushObject_android(userList, map, Content, title);
                payloadIos = PushObject_ios(userList, map, Content, title);
                break;
            default:
                return "fail";
        }
        PushResult pushResult  = null;
        String result  = null;
        try {

            for(String[] item :ConstUtil.JPUSH_KEY)
            {
                JPushClient jpushClient = new JPushClient(item[1], item[0]);
                if (payloadAndroid != null) {
                     pushResult = jpushClient.sendPush(payloadAndroid);
                }
                if (payloadIos != null) {
                     pushResult = jpushClient.sendPush(payloadIos);
                }
            }
            result  = "success";
        } catch (APIConnectionException | APIRequestException e) {
            result = e.getMessage();
        }
        return result;

    }

    /**
     * 推送消息V2版本
     *
     * @param userList
     * @param map
     * @param Content
     * @param title
     */
    public static void SendPushV2(List<String> userList, Map<String, String> map, String Content, String title) {
        PushPayload payloadAndroid = PushObject_android(userList, map, Content, title);
        PushPayload payloadIos = PushObject_ios(userList, map, Content, title);
        try {
            for(String[] item :ConstUtil.JPUSH_KEY)
            {
                JPushClient jpushClient = new JPushClient(item[1], item[0]);
                PushResult result = jpushClient.sendPush(payloadAndroid);
                result = jpushClient.sendPush(payloadIos);
                System.out.println("-----------" + result);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 推送消息
     *
     * @param userList
     * @param map
     * @param Content
     */
    public static void SendPush(List<String> userList, Map<String, String> map, String Content) {
        try {
            PushPayload payloadAndroid = PushObject_android(userList, map, Content, TITLE);
            PushPayload payloadIos = PushObject_ios(userList, map, Content, TITLE);
        
            for(String[] item :ConstUtil.JPUSH_KEY)
            {
                JPushClient jpushClient = new JPushClient(item[1], item[0]);
                PushResult result = jpushClient.sendPush(payloadAndroid);
                result = jpushClient.sendPush(payloadIos);
                System.out.println("-----------" + result);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 消息推送
     *
     * @param userList 推个用户 id
     * @param map
     * @param Content 内容
     * @param title
     * @return
     */
    public static PushPayload PushObject_android(List<String> userList, Map<String, String> map, String Content, String title) {
        System.out.println("-----------:方法进入");
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias(userList))
                        .build())
                .setMessage(Message.newBuilder().setTitle(title)
                        .setMsgContent(Content)
                        .addExtra("data", JSONObject.toJSONString(map))
                        .build())
                .build();
    }

    public static PushPayload PushObject_ios(List<String> userList, Map<String, String> map, String Content, String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias(userList))
                        .build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(map.get("Content"))
                                .setBadge(1)
                                .setSound("happy")
                                .addExtras(map)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }
    
    /**
     * 推送消息
     *
     * @param map
     * @param Content
     */
    public static String SendPushALL(Map<String, String> map, String Content) {
        String results = "";
        PushPayload payloadAndroid = PushObject_android_all(map, Content, TITLE);
        //PushPayload payloadIos = PushObject_ios_all(map, Content, TITLE);
        try {
            for(String[] item :ConstUtil.JPUSH_KEY)
            {
                JPushClient jpushClient = new JPushClient(item[1], item[0]);
                PushResult result = jpushClient.sendPush(payloadAndroid);
                // result = jpushClient.sendPush(payloadIos);
                System.out.println("-----------" + result);
            }
            results = "success";
        } catch (APIConnectionException | APIRequestException e) {
            results = e.getMessage();
        }
        return results;
    }
    
    /**
     * 消息推送
     *
     * @param map
     * @param title
     * @param Content 内容
     * @return
     */
    public static PushPayload PushObject_android_all(Map<String, String> map, String Content, String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder().setTitle(title)
                        .setMsgContent(Content)
                        .addExtra("data", JSONObject.toJSONString(map))
                        .build())
                .build();
    }

    public static PushPayload PushObject_ios_all(Map<String, String> map, String Content, String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(map.get("Content"))
                                .setBadge(1)
                                .setSound("happy")
                                .addExtras(map)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }
}
