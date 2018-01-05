package com.oruit.app.test.base;/**
 * Created by wyt on 2017/8/30.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.weixin.api.GetAccessTokenRsp;
import com.oruit.weixin.api.WeiXinAccessTokenUtil;

/**
 * @author
 * @create 2017-08-30 19:56
 */
public class token {
    public static void main(String[] args) {
        GetAccessTokenRsp weiXinAccessToken = WeiXinAccessTokenUtil.getWeiXinAccessToken( "011D4bv90Zpk4v1b8kt90I03v90D4bvt");
        JSONObject info = WeiXinAccessTokenUtil.info(weiXinAccessToken.getAccessToken(), weiXinAccessToken.getOpenid());
        info.get("openid").toString();
        info.get("nickname").toString();
        info.get("sex").toString();
        info.get("city").toString();
        info.get("province").toString();
        info.get("headimgurl").toString();
        info.get("unionid").toString();

    }
}
