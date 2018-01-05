/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断手机号是 移动 、联通 、 电信
 *
 * @author Liuk
 */
public class MobileUtil {

    /**
     * 
     * @param mobile
     * @return 1:移动  2:联通  3:电信
     */
    public static Integer matchesPhoneNumber(String mobile) {
        if (mobile == null || "".equals(mobile)) {
            return -1;
        }
        String s_yidong = "^(((134[0-8])|(1705))\\d{7}|((13[5-9])|(15[0-2])|(15[7-9])|(18[2-4])|(18[7-8])|(147)|(178))\\d{8})";
        Pattern p_yidong = Pattern.compile(s_yidong);
        Matcher m_yidong = p_yidong.matcher(mobile);
        if (m_yidong.matches()) {
            return 1;
        }
        String s_liantong = "^((1709)\\d{7}|((13[0-2])|(15[5-6])|(18[5-6])|(145)|(176))\\d{8})";
        Pattern p_liantong = Pattern.compile(s_liantong);
        Matcher m_liantong = p_liantong.matcher(mobile);
        if (m_liantong.matches()) {
            return 2;
        }
        String s_dianxin = "^((1700)\\d{7}|((18[0-1])|(133)|(153)|(189)|(177))\\d{8})";
        Pattern p_dianxin = Pattern.compile(s_dianxin);
        Matcher m_dianxin = p_dianxin.matcher(mobile);
        if (m_dianxin.matches()) {
            return 3;
        }
        return 0;
    }

    public static void main(String[] args) {
        String e = "fsdf";
        Integer s = matchesPhoneNumber(e);
        System.out.println("----------" + s);
    }
}
