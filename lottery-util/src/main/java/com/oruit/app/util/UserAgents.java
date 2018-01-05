/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpContext;



/**
 *
 * @author liuk
 */
public class UserAgents {
    ///<summary>
    /// 根据 Agent 判断是否是智能手机
    ///</summary>
    ///<returns></returns>
    public static String CheckAgent(HttpServletRequest request)
    {
        String result = null;
        String agent = request.getHeader("User-Agent");
           //排除 Windows 桌面系统
            if (!agent.contains("Windows NT") || (agent.contains("Windows NT") && agent.contains("compatible; MSIE 9.0;")))
            {
                //排除 苹果桌面系统
                if (!agent.contains("Windows NT") && !agent.contains("Macintosh"))
                {
                        if (agent.contains("Android"))
                        {
                            result = "Android";
                        }else if (agent.contains("iPhone")){
                             result = "iPhone";
                        }else{
                        }
                }
            }
            return result;
    }
}
