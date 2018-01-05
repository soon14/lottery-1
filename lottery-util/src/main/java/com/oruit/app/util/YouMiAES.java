/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.net.URLEncoder;
import java.util.Random;
import javax.crypto.Cipher;
import sun.misc.BASE64Encoder;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.security.MessageDigest; 
import java.security.GeneralSecurityException; 


/**
 * 有米接口的认证
 * @author Liuk
 */
public class YouMiAES {

    public static String encode(String appid, String app_secret, String openid, String feedback) throws Exception {
        if (feedback == null) {
            feedback = "";
        }
        BASE64Encoder baseEncoder = new BASE64Encoder();
        String salt = baseEncoder.encode(openid.getBytes()) + "&" + baseEncoder.encode(feedback.getBytes());
        return URLEncoder.encode(appid + baseEncoder.encode(Encrypt(salt, app_secret)), "UTF-8");
    }

    // 加密
    public static byte[] Encrypt(String content, String key) throws Exception {
        if (key == null) {
            System.out.print("Key 为空 null");
            return null;
        }
        // 判断 Key 是否为 16 位
        if (key.length() != 16) {

            System.out.print("Key 长度不是 16 位");
            return null;
        }
        Random random = new Random();
        byte[] buff = new byte[16];
        random.nextBytes(buff);
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(buff);// 使用 CBC 模式，需要一个向量 iv，可增加加
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        int totalLength = iv.getIV().length + +encrypted.length;;
        byte[] combine = new byte[totalLength];
        System.arraycopy(iv.getIV(), 0, combine, 0, iv.getIV().length);
        System.arraycopy(encrypted, 0, combine, iv.getIV().length, encrypted.length);
        return combine;
    }
    
    public static void main(String[] args) throws Exception{
        // 对应公众号用户的 openid
    String openid = "aaaabbbbb";
    // 预留参数,如果不需要请留空
    String feedback = "ccccddddd";
    // 在有米官网获取的应用 ID
    String appid = "20bc607d983a540c";
    // 在有米官网获取的应用密钥
    String app_secret = "f27c36e5ab7ef02d";
    // 加密结果
    String encryptResult = encode(appid,app_secret,openid,feedback);
    System.out.println("encryptText: "+ encryptResult);
    }
    
    /**
     * 签名生成算法
     *
     * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
     * @param String                 dev_server_secret 开发者在有米后台设置的密钥
     * @return String
     * @throws IOException
     */
    public static String getSignature(HashMap<String, String> params, String dev_server_secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
 
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(dev_server_secret);
        //System.out.println(basestring.toString());
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }
        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }
 
    /**
     * 对一条完整的未签名的URL做签名，并将签名结果添加到URL的末尾
     * 
     * @param String url 未做签名的完整URL
     * @param String dev_server_secret 签名秘钥
     * @return String
     * @throws IOException, MalformedURLException
     */
    public static String getUrlSignature(String url, String dev_server_secret) throws IOException, MalformedURLException {
        try {
            URL urlObj = new URL(url);
            String query = urlObj.getQuery();
            String[] params = query.split("&");
            Map<String, String> map = new HashMap<String, String>();
            for (String each : params) {
                String name = each.split("=")[0];
                String value;
                try {
                    value = URLDecoder.decode(each.split("=")[1], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    value = "";
                }
                map.put(name, value);
            }
            String signature = getSignature((HashMap<String, String>) map, dev_server_secret);
            return url + "&sign=" + signature;
        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
 
    /**
     * 检查一条完整的包含签名参数的URL，其签名是否正确
     * 
     * @param String url 已经签名的完整URL
     * @param String dev_server_secret 签名秘钥
     * @return boolean
     */
    public static boolean checkUrlSignature(String signedUrl, String dev_server_secret) {
        String urlSign = "";
        try {
            URL urlObj = new URL(signedUrl);
            String query = urlObj.getQuery();
            String[] params = query.split("&");
            Map<String, String> map = new HashMap<String, String>();
            for (String each : params) {
                String name = each.split("=")[0];
                String value;
                try {
                    value = URLDecoder.decode(each.split("=")[1], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    value = "";
                }
                if ("sign".equals(name)) {
                    urlSign = value;
                } else {
                    map.put(name, value);
                }
            }
            if ("".equals(urlSign)) {
                return false;
            } else {
                String signature = getSignature((HashMap<String, String>) map, dev_server_secret);
                return urlSign.equals(signature);
            }
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

}
