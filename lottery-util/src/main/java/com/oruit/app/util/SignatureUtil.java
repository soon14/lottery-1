/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Liuk
 */
public class SignatureUtil {

    /**
     * 生成 flowSingn 流量包 加密认证
     *
     * @param map
     * @return
     */
    public static String generateFlowSign(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        boolean flag = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
//            if (key.equals("Password")) {
//                builder.append(", ").append(value);
//            }
            if (flag) {
                builder.append(",").append(value);
            } else {
                builder.append(value);
                flag = true;
            }
        }
        System.out.println("---------------------"+builder.toString());
        return DigestUtils.md5Hex(builder.toString()).toUpperCase();
    }

    /**
     * 生成 flowSingn 流量包 加密认证
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str).toUpperCase();
    }
}
