package com.oruit.app.util.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 王延通 on 2018/1/2 0002.
 */
public class CurrentIP {
    /**
     * 获取当前的Ip地址
     *
     * @param request
     * @return
     */
    public String getCurrentIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ipArr = ip.split(",");
        if (ipArr.length > 0) {
            ip = ipArr[0];
        }
        return ip;
    }
}
