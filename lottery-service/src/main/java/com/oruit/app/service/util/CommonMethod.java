/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.service.util;

import java.util.Map;

/**
 *
 * @author hanfeng
 */
public class CommonMethod {
    public static String getNickName(Map<String,Object> userinfoMap)
    {
        String nickname = "";
        if (userinfoMap != null) {
            if (userinfoMap.get("NickName") == null || "".equals(userinfoMap.get("NickName"))) {
                String mobile = (String) userinfoMap.get("Mobile");
                nickname = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            } else {
                nickname = userinfoMap.get("NickName").toString();
            }
        }
        return nickname;
    }
}
