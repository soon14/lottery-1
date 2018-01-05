package com.oruit.weixin.api;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import com.oruit.weixin.bean.Article;
import com.oruit.weixin.bean.BaseResult;
import com.oruit.weixin.bean.Media;
import com.oruit.weixin.bean.MessageSendResult;
import com.oruit.weixin.bean.Uploadvideo;
import com.oruit.weixin.bean.massmessage.MassMessage;
import com.oruit.weixin.bean.message.Message;
import com.oruit.weixin.bean.templatemessage.TemplateMessage;
import com.oruit.weixin.bean.templatemessage.TemplateMessageResult;
import com.oruit.app.client.LocalHttpClient;
import com.oruit.app.util.JsonDealUtil;
import com.oruit.weixin.bean.templatemessage.TemplateMessageItem;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;

/**
 * 当用户主动发消息给公众号的时候 （包括发送信息、点击自定义菜单click事件、订阅事件、扫描二维码事件、支付成功事件、用户维权），
 * 微信将会把消息数据推送给开发者， 开发者在一段时间内（目前修改为48小时）可以调用客服消息接口， 通过POST一个JSON数据包来发送消息给普通用户，
 * 在48小时内不限制发送次数。 此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
 *
 *
 */
public class MessageAPI extends BaseAPI {

    private final static Logger log = Logger.getLogger(MessageAPI.class);

    /**
     * 消息发送
     *
     * @param access_token
     * @param messageJson
     * @return
     */
    public static BaseResult messageCustomSend(String access_token, String messageJson) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/message/custom/send")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(messageJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, BaseResult.class);
    }

    /**
     * 消息发送
     *
     * @param access_token
     * @param message
     * @return
     */
    public static BaseResult messageCustomSend(String access_token, Message message) {
        String str = JsonDealUtil.toJSONString(message);
        return messageCustomSend(access_token, str);
    }

    /**
     * 高级群发 构成 MassMPnewsMessage 对象的前置请求接口
     *
     * @param access_token
     * @param articles 图文信息 1-10 个
     * @return
     */
    public static Media mediaUploadnews(String access_token, List<Article> articles) {
        String str = JsonDealUtil.toJSONString(articles);
        String messageJson = "{\"articles\":" + str + "}";
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/media/uploadnews")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(messageJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, Media.class);
    }

    /**
     * 高级群发 构成 MassMPvideoMessage 对象的前置请求接口
     *
     * @param access_token
     * @param uploadvideo
     * @return
     */
    public static Media mediaUploadvideo(String access_token, Uploadvideo uploadvideo) {
        String messageJson = JsonDealUtil.toJSONString(uploadvideo);
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(MEDIA_URI + "/cgi-bin/media/uploadvideo")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(messageJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, Media.class);
    }

    /**
     * 高级群发接口 根据分组进行群发
     *
     * @param access_token
     * @param messageJson
     * @return
     */
    public static MessageSendResult messageMassSendall(String access_token, String messageJson) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/message/mass/sendall")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(messageJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, MessageSendResult.class);
    }

    /**
     * 高级群发接口 根据分组进行群发
     *
     * @param access_token
     * @param massMessage
     * @return
     */
    public static MessageSendResult messageMassSendall(String access_token, MassMessage massMessage) {
        String str = JsonDealUtil.toJSONString(massMessage);
        return messageMassSendall(access_token, str);
    }

    /**
     * 高级群发接口 根据OpenID列表群发
     *
     * @param access_token
     * @param messageJson
     * @return
     */
    public static MessageSendResult messageMassSend(String access_token, String messageJson) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/message/mass/send")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(messageJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, MessageSendResult.class);
    }

    /**
     * 高级群发接口 根据OpenID列表群发
     *
     * @param access_token
     * @param massMessage
     * @return
     */
    public static MessageSendResult messageMassSend(String access_token, MassMessage massMessage) {
        String str = JsonDealUtil.toJSONString(massMessage);
        return messageMassSend(access_token, str);
    }

    /**
     * 高级群发接口	删除群发 请注意，只有已经发送成功的消息才能删除删除消息只是将消息的图文详情页失效， 已经收到的用户，还是能在其本地看到消息卡片。
     * 另外，删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
     *
     * @param access_token
     * @param msgid
     * @return
     */
    public static BaseResult messageMassDelete(String access_token, String msgid) {
        String messageJson = "{\"msgid\":" + msgid + "}";
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/message/mass/delete")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(messageJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, BaseResult.class);
    }

    /**
     * 模板消息发送
     *
     * @param access_token
     * @param templateMessage
     * @return
     */
    public static TemplateMessageResult messageTemplateSend(String access_token, TemplateMessage templateMessage) {
        String messageJson = JsonDealUtil.toJSONString(templateMessage);
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/message/template/send")
                .addParameter("access_token", access_token)
                .setEntity(new StringEntity(messageJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, TemplateMessageResult.class);
    }

    /*
            呱呱赚发送模板消息(服务消息通知)
                消息提醒 - first
                标题：任务奖励 - Title
                日期：2015年9月17日 - dates
                您的徒弟完成了#####任务 - remark
     */
//        public static long SendGGCMsgToWechat(String access_token,String openid, String first, String title, String dates, String remark){
//            TemplateMessage tm = new TemplateMessage();
//            tm.setTouser(openid);
//            tm.setTemplate_id("ybdRupIJk9cCinL7e0Ywe7BFbukC4QD_J0iYU0EFloY");
//            tm.setTopcolor("#ffffff");
//            tm.setUrl("");
//            LinkedHashMap<String, TemplateMessageItem> da = new LinkedHashMap<String, TemplateMessageItem>();
//            da.put("first", new TemplateMessageItem(first,"#173177"));
//            da.put("keyword1", new TemplateMessageItem(title,"#173177"));
//            da.put("keyword2", new TemplateMessageItem(dates,"#173177"));
//            da.put("remark", new TemplateMessageItem(remark,"#173177"));
//            tm.setData(da);
//            return messageTemplateSend(access_token,tm).getMsgid();
//        }
    /**
     *
     * 呱呱赚发送模板消息(服务消息通知) 消息提醒 - first 标题：任务奖励 - Title 日期：2015年9月17日 - dates
     * 您的徒弟完成了#####任务 - remark
     *
     * @param openid
     * @param first
     * @param title
     * @param dates
     * @param remark
     * @param templateId 消息模板id
     * @param appid 微信appid
     * @param secret 微信
     * @return * public static long SendGGCMsgToWechat(String openid, String
     * first, String title, String dates, String remark, String
     * templateId,String appid, String secret){ TemplateMessage tm = new
     * TemplateMessage(); tm.setTouser(openid); //
     * tm.setTemplate_id("ybdRupIJk9cCinL7e0Ywe7BFbukC4QD_J0iYU0EFloY");
     * tm.setTemplate_id(templateId); tm.setTopcolor("#ffffff"); tm.setUrl("");
     * LinkedHashMap<String, TemplateMessageItem> da = new LinkedHashMap<>();
     * da.put("first", new TemplateMessageItem(first,"#173177"));
     * da.put("keyword1", new TemplateMessageItem(title,"#173177"));
     * da.put("keyword2", new TemplateMessageItem(dates,"#173177"));
     * da.put("remark", new TemplateMessageItem(remark,"#173177"));
     * tm.setData(da); System.out.println("-----------进入微信推送的方法----");
     * System.out.println("-----------进入微信推送的方法----"+ openid); return
     * messageTemplateSend(TokenAPI.getTokenFromCache(appid,secret),tm).getMsgid();
     * }
     */
    /**
     *
     * 呱呱赚发送模板消息(服务消息通知) 消息提醒 - first 标题：任务奖励 - Title 日期：2015年9月17日 - dates
     * 您的徒弟完成了#####任务 - remark
     *
     * @param openid
     * @param first
     * @param title
     * @param dates
     * @param remark
     * @param templateId 消息模板id
     * @param access_token
     * @return
     */
     @Deprecated
    public static long SendGGCMsgToWechat(String openid, String first, String title, String dates, String remark,
            String templateId, String access_token) {
        TemplateMessage tm = new TemplateMessage();
        tm.setTouser(openid);
//            tm.setTemplate_id("ybdRupIJk9cCinL7e0Ywe7BFbukC4QD_J0iYU0EFloY");
        tm.setTemplate_id(templateId);
        tm.setTopcolor("#ffffff");
        tm.setUrl("");
        LinkedHashMap<String, TemplateMessageItem> da = new LinkedHashMap<>();
        da.put("first", new TemplateMessageItem(first, "#173177"));
        da.put("keyword1", new TemplateMessageItem(title, "#173177"));
        da.put("keyword2", new TemplateMessageItem(dates, "#173177"));
        da.put("remark", new TemplateMessageItem(remark, "#173177"));
        tm.setData(da);
        System.out.println("-----------进入微信推送的方法----");
        System.out.println("-----------进入微信推送的方法----" + openid);
        TemplateMessageResult templateMessageResult = messageTemplateSend(access_token, tm);
        return templateMessageResult.getMsgid();
    }

    public static long SendGGCMsgToWechat(String openid, String first, String title, String dates, String remark, String url,
            String templateId, String access_token) {
        TemplateMessage tm = new TemplateMessage();
        tm.setTouser(openid);
//            tm.setTemplate_id("ybdRupIJk9cCinL7e0Ywe7BFbukC4QD_J0iYU0EFloY");
        tm.setTemplate_id(templateId);
        tm.setTopcolor("#ffffff");
        tm.setUrl(url);
        LinkedHashMap<String, TemplateMessageItem> da = new LinkedHashMap<>();
        da.put("first", new TemplateMessageItem(first, "#173177"));
        da.put("keyword1", new TemplateMessageItem(title, "#173177"));
        da.put("keyword2", new TemplateMessageItem(dates, "#173177"));
        da.put("remark", new TemplateMessageItem(remark, "#173177"));
        tm.setData(da);
        System.out.println("-----------进入微信推送的方法----");
        System.out.println("-----------进入微信推送的方法----" + openid);
        return messageTemplateSend(access_token, tm).getMsgid();
    }

    /*
        public static long SendGGCMsgToWechat(String openid,LinkedHashMap<String, TemplateMessageItem> data,
                                                String templateId,String appid, String secret){
            TemplateMessage tm = new TemplateMessage();
            tm.setTouser(openid);
//            tm.setTemplate_id("ybdRupIJk9cCinL7e0Ywe7BFbukC4QD_J0iYU0EFloY");
            tm.setTemplate_id(templateId);
            tm.setTopcolor("#ffffff");
            tm.setUrl("");
            tm.setData(data);
            System.out.println("-----------进入微信推送的方法----");
            System.out.println("-----------进入微信推送的方法----"+ openid);
            return messageTemplateSend(TokenAPI.getTokenFromCache(appid,secret),tm).getMsgid(); 
        }
     */
    public static long SendGGCMsgToWechat(String openid, LinkedHashMap<String, TemplateMessageItem> data,
            String templateId, String access_token) {
        TemplateMessage tm = new TemplateMessage();
        tm.setTouser(openid);
//            tm.setTemplate_id("ybdRupIJk9cCinL7e0Ywe7BFbukC4QD_J0iYU0EFloY");
        tm.setTemplate_id(templateId);
        tm.setTopcolor("#ffffff");
        tm.setUrl("");
        tm.setData(data);
        System.out.println("-----------进入微信推送的方法----");
        System.out.println("-----------进入微信推送的方法----" + openid);
        return messageTemplateSend(access_token, tm).getMsgid();
    }

    /**
     * 任务新模板
     * @param openid
     * @param first
     * @param adName
     * @param taskType
     * @param gold
     * @param remark
     * @param templateId
     * @param access_token
     * @return 
     */
    public static long SendGGCMsgToWechatV2(String openid, String first, String adName, String taskType, String gold, String remark,
            String templateId, String access_token) {
        TemplateMessage tm = new TemplateMessage();
        tm.setTouser(openid);
//            tm.setTemplate_id("ybdRupIJk9cCinL7e0Ywe7BFbukC4QD_J0iYU0EFloY");
        tm.setTemplate_id(templateId);
        tm.setTopcolor("#ffffff");
        tm.setUrl("");
        LinkedHashMap<String, TemplateMessageItem> da = new LinkedHashMap<>();
        da.put("first", new TemplateMessageItem(first, "#173177"));
        da.put("keyword1", new TemplateMessageItem(adName, "#173177"));
        da.put("keyword2", new TemplateMessageItem(taskType, "#173177"));
        da.put("keyword3", new TemplateMessageItem(gold, "#173177"));
        da.put("remark", new TemplateMessageItem(remark, "#173177"));
        tm.setData(da);
        System.out.println("-----------进入微信推送的方法----");
        System.out.println("-----------进入微信推送的方法----" + openid);
        TemplateMessageResult templateMessageResult = messageTemplateSend(access_token, tm);
        return templateMessageResult.getMsgid();
    }
}
