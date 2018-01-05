/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.weixin.bean;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class CacheToken {
    private String access_token;
    private Date last_date;
    
    public String getToken(){
        return access_token;
    }
    
    public void setToken(String _access_token){
        access_token=_access_token;
    }
    
    public Date getDate(){
        return last_date;
    }
    
    public void setDate(Date _last_date){
        last_date=_last_date;
    }
}
