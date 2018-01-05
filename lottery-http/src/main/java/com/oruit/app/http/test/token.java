package com.oruit.app.http.test;/**
 * Created by wyt on 2017/8/30.
 */


import com.alibaba.fastjson.JSONObject;
import com.oruit.weixin.api.TokenAPI;
import com.oruit.weixin.bean.Token;

import java.math.BigDecimal;

/**
 * @author
 * @create 2017-08-30 18:18
 */
public class token {
    public static void main(String[] args) {
        /*String wxa5179dcdd1b4f901 = TokenAPI.getToken("wxa5179dcdd1b4f901", "14b9a6971a000176cd0040f5699ec776","081RcgSN0rUXL526nRQN0n47SN0RcgSa");
        System.out.println(wxa5179dcdd1b4f901);*/
        int money = 20;
        System.out.println(new BigDecimal(money));

    }
}
