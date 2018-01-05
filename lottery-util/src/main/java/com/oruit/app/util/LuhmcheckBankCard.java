package com.oruit.app.util;/**
 * Created by wyt on 2017/9/23.
 */

import java.util.Scanner;

/**
 * 验证银行卡是否正确
 * @author @wyt
 * @create 2017-09-23 9:10
 */
public class LuhmcheckBankCard {
    public static void main(String[] args) {
        boolean b = checkBankCard("1651634653");
        System.out.println(b);
    }
    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        int cardLenth = nonCheckCodeCardId.trim().length();
        if (nonCheckCodeCardId == null || cardLenth == 0 || !nonCheckCodeCardId.matches("\\d+")) {
           return '2';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
