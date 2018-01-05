/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hanfeng
 */
public class URLNetUtil {
    
    public static String urlDecoder(String dealString)
    {
        try {
            return URLDecoder.decode(dealString, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLNetUtil.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public static String urlEncoder(String dealString)
    {
        try {
            return URLEncoder.encode(dealString, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLNetUtil.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
