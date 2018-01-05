package com.oruit.app.http.action;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */;

import com.oruit.app.util.web.ResultBean;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Liuk
 */
@Controller
public class BaseAction {

    public static final Logger log = Logger.getLogger(BaseAction.class);
    //是否发送短信
    @Value("#{configProperties['sendCodeFlag']}")
    public String sendCodeFlag;
    //站点域名
    @Value("#{configProperties['SITE_URL']}")
    public String SITE_URL;
    //项目名称
    @Value("#{configProperties['APP_NAME']}")
    public String APP_NAME;

    //微信号名称
    @Value("#{configProperties['WX_TITLE']}")
    public String WX_TITLE;

    //图片上传路径
    @Value("#{configProperties['uploadPath']}")
    public String uploadPath;

    //图片上传路径
    @Value("#{configProperties['IMG_URL']}")
    public String IMG_URL;

    // 环信：API_SERVER_HOST
    @Value("#{configProperties['API_SERVER_HOST']}")
    public String API_SERVER_HOST;
    // 环信：APPKEY
    @Value("#{configProperties['APPKEY']}")
    public String APPKEY;
    // 环信：APP_CLIENT_ID
    @Value("#{configProperties['APP_CLIENT_ID']}")
    public String APP_CLIENT_ID;
    // 环信：APP_CLIENT_SECRET
    @Value("#{configProperties['APP_CLIENT_SECRET']}")
    public String APP_CLIENT_SECRET;
    //微信公众平台appid
    @Value("#{configProperties['appid']}")
    public String appid;
    //微信公众普通token
    @Value("#{configProperties['token']}")
    public String token;
    //微信公众普通secret
    @Value("#{configProperties['secret']}")
    public String secret;
    //微信商户号
    @Value("#{configProperties['mch_id']}")
    public String mch_id;
    //微信支付授权密钥
    @Value("#{configProperties['PartnerKey']}")
    public String PartnerKey;
    //微信服务器ip
    @Value("#{configProperties['spbill_create_ip']}")
    public String spbill_create_ip;
    //微信回掉地址
    @Value("#{configProperties['NOTIFY_URL']}")
    public String NOTIFY_URL;
    //订单有效期（天）
    @Value("#{configProperties['youxiaoqi']}")
    public String youxiaoqi;

    //SESSION-微信OPENID名称（openid）
    public String sessionNameString = "openId";

    @Value("#{configProperties['BaiduMapKEY']}")
    public String BaiduMapKEY;

    public String OPENID = "MYOPENID";

    public ResultBean resultBean;

    public void Log(String _function, String _msg){
        log.info(String.format("-------------------------%s-----------------------------\n%s\n-------------------------###-----------------------------", _function,_msg));
    }

    public boolean JudgeIsMoblie(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile"};
        if (request.getHeader("User-Agent") != null) {
            for (String mobileAgent : mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }

    public boolean JudgeIsMoblie_Iphone(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = {"iphone"};
        if (request.getHeader("User-Agent") != null) {
            for (String mobileAgent : mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }

    /**
     * 设置/更新保存在Session的对象
     * @param request
     * @param key
     * @param value
     * @return
     */
    Boolean SetSessionObject(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession(true);
        session.setAttribute(key, value);
        return true;
    }

    /**
     * 获取保存在Session的对象(不清除)
     * @param request
     * @param key
     * @return
     */
    Object GetSessionObject(HttpServletRequest request, String key) {
        return GetSessionObject(request, key, false);
    }
    /**
     * 获取保存在Session的对象
     * @param request
     * @param key
     * @param isclear
     * @return
     */
    Object GetSessionObject(HttpServletRequest request, String key, Boolean isclear) {
        HttpSession session = request.getSession();
        if(session.getAttribute(key) != null) {
            if(!StringUtils.isBlank(session.getAttribute(key).toString())) {
                Object value = session.getAttribute(key);
                if(isclear) {
                    session.removeAttribute(key);
                }
                return value;
            }
        }
        return null;
    }

    /**
     * 清除session
     * @param request
     * @return
     */
    Boolean ClearSession(HttpServletRequest request) {
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        return true;
    }
    /**
     * 清除指定session
     * @param request
     * @param key
     * @return
     */
    Boolean ClearSession(HttpServletRequest request, String key) {
        if(request.getSession().getAttribute(key) != null) {
            request.getSession().removeAttribute(key);
            return true;
        }
        return false;
    }

    Boolean SaveOpenId(HttpServletRequest request, String _openid) {
        return SetSessionObject(request, "openidsss" , _openid);
    }

    String GetOpenId(HttpServletRequest request) {
        if(GetSessionObject(request,"openidsss")!=null) {
            return (String) GetSessionObject(request,"openidsss");
        }
        return "";
    }


    boolean GetWeixinOpenid(HttpServletRequest request, HttpServletResponse response) {

        return true;
    }
}
