/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.client.sms;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.oruit.app.logic.util.ConstUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SmsHelper {

    private final static Logger log = Logger.getLogger(SmsHelper.class);
    public final static String SMS_URL = "http://dx.ipyy.net/sms.aspx";
    public final static String SMS_TEMPLATE = "您的验证码是：%s，请于5分钟内正确输入。";

    //发送短信验证码
    public static void sendMessage(String mobile, String vCode) {
        String content = String.format("【呱呱" + ConstUtil.BASE_NAME + "】"  + SMS_TEMPLATE, vCode);
        String send_content = "";
        try {
            // 设置发送内容的编码方式
            send_content = URLEncoder.encode(content.replaceAll("<br/>", " "), "UTF-8");// 发送内容
        } catch (UnsupportedEncodingException ex) {
            log.debug(ex.toString());
        }
        String requestUrl = SMS_URL + "?action=send&userid="
                    + ConstUtil.USERID + "&account=" + ConstUtil.ACCOUNT + "&password=" + ConstUtil.PASSWORD
                    + "&mobile=" + mobile + "&content=" + send_content;
        readStringXml(sendMessage(requestUrl).toString());
    }
    
    public static Integer getMessageRemainNum()
    {
        //action=overage&userid=&account={0}&password={1}
        String requestUrl = SMS_URL + "?action=overage&userid="
                    + ConstUtil.USERID + "&account=" + ConstUtil.ACCOUNT + "&password=" + ConstUtil.PASSWORD;
        readStringXmlForRemainNum(sendMessage(requestUrl).toString());
        return 0;
    }

    public static void sendActivityMessage(String mobile, String msgModel, String data) {
        String content = String.format(msgModel, data);
        String send_content = "";
        try {
            // 设置发送内容的编码方式
            send_content = URLEncoder.encode(content.replaceAll("<br/>", " "), "UTF-8");// 发送内容
        } catch (UnsupportedEncodingException ex) {
            log.debug(ex.toString());
        }
        String requestUrl = SMS_URL + "?action=send&userid="
                    + ConstUtil.USERID + "&account=" + ConstUtil.ACCOUNT + "&password=" + ConstUtil.PASSWORD
                    + "&mobile=" + mobile + "&content=" + send_content;
        readStringXml(sendMessage(requestUrl).toString());
    }

    // 发送短信、查余量
    private static StringBuffer sendMessage(String requestUrl) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),
                    "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("");
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(buffer.toString());
        return buffer;
    }
    // 发送短信获取返回值
    private static void readStringXml(String xml) {
        Document doc = null;
        log.info(xml);
        try {
            //将字符转化为XML
            doc = DocumentHelper.parseText(xml);
            //获取根节点
            Element rootElt = doc.getRootElement();

            //拿到根节点的名称
            //System.out.println("根节点名称："+rootElt.getName());
            //获取根节点下的子节点的值
            String returnstatus = rootElt.elementText("returnstatus").trim();
            String message = rootElt.elementText("message").trim();
            String payinfo = rootElt.elementText("taskID").trim();
            String overage = rootElt.elementText("remainpoint").trim();
            String sendTotal = rootElt.elementText("successCounts").trim();

            log.info("返回状态为：" + returnstatus);
            log.info("返回信息提示：" +URLDecoder.decode(message));
            log.info("返回支付方式：" + payinfo);
            log.info("返回剩余短信条数：" + overage);
            log.info("返回总条数：" + sendTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        // 发送短信获取返回值
    private static void readStringXmlForRemainNum(String xml) {
        Document doc = null;
        log.info(xml);
        try {
            //将字符转化为XML
            doc = DocumentHelper.parseText(xml);
            //获取根节点
            Element rootElt = doc.getRootElement();

            //拿到根节点的名称
            //System.out.println("根节点名称："+rootElt.getName());
            //获取根节点下的子节点的值
            String overage= rootElt.elementText("overage").trim();

            log.info("余量：" + overage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //短信通道 2 *********************************
    public static void sendMessageTow(String mobile, String vCode) {
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount("8a48b551501216950150121a1ed80006", "70d22175aef042e8979f7381e0944b76");// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId("aaf98f89501219890150121f8c6c0004");// 初始化应用ID
        result = restAPI.sendTemplateSMS(mobile, "42528", new String[]{vCode, "5"});
        log.info("SDKTestSendTemplateSMS result=" + result);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                log.info(key + " = " + object);
            }
        } else {
            //异常返回输出错误码和错误信息
            log.debug("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }
    }
    
    public static StringBuffer sendADMessageGet(String content,String mobile)
    {
        StringBuffer buffer = new StringBuffer();
        try {
            // 设置发送内容的编码方式
            String send_content = URLEncoder.encode(
                    content.replaceAll("<br/>", " "), "UTF-8");// 发送内容

            URL url = new URL("http://125.208.3.91:8888/sms.aspx?action=send&userid=10454&account=" + "guaguaduobao" + "&password=" + "x8slQCL6ab"
                    + "&mobile=" + mobile + "&content=" + send_content + "&sendTime="+ "&extno=");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("");
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        readStringXml(buffer.toString());
        return buffer;
    }
    
    // 管理端群推短信使用
    public static StringBuffer sendADMessagePost(String content,String mobile)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 设置发送内容的编码方式
            String send_content = URLEncoder.encode(
                    content.replaceAll("<br/>", " "), "UTF-8");// 发送内容
            
        String param = "action=send&userid=10454&account=" + "guaguaduobao" + "&password=" + "x8slQCL6ab"
                    + "&mobile=" + mobile + "&content=" + send_content + "&sendTime="+ "&extno=";
            URL url = new URL("http://125.208.3.91:8888/sms.aspx");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line).append("");
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        readStringXml(buffer.toString());
        return buffer;
    }
    
    public static StringBuffer sendADMessagePostV2(String content,String mobile)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 设置发送内容的编码方式
            String send_content = URLEncoder.encode(
                    content.replaceAll("<br/>", " "), "UTF-8");// 发送内容
            
        //密码：x8slQCL6ab
        String param = "action=send&userid=&account=" + "guaguatuiguang" + "&password=" + "9DB4C2071AD415D0AFD0821A1C20E7CD"
                    + "&mobile=" + mobile + "&content=" + send_content + "&sendTime="+ "&extno=";
            URL url = new URL("http://114.113.154.110/sms.aspx");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line).append("");
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        readStringXml(buffer.toString());
        return buffer;
    }

    public static void main(String[] args) {
        System.out.println(BigDecimal.valueOf(Long.valueOf("8134")).movePointLeft(3));
 //       getMessageRemainNum();
//        SmsHelper.sendMessage("18701079978", "123");
           // SmsHelper.sendADMessagePostV2("系统赠送了您1000 金币，红包接龙系统全新升级，快点击下载领取金币吧！http://t.cn/RqntQWo，退订回N【呱呱呱】","18501362268");
    }
}
