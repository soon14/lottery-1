package com.oruit.weixin.api;

import java.io.Serializable;

public class GetAccessTokenRsp implements Serializable
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7021131613095678023L;

    private String accessToken;

    private  String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    @Override
    public String toString()
    {
        return "GetAccessTokenRsp [accessToken=" + accessToken + "]";
    }



}
