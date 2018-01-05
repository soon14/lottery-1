package com.oruit.app.oruitkey;

import org.apache.log4j.Logger;

/**
 * 瑞特特殊加密类
 *
 * @author lee
 *
 */
public class OruitKey {

    private static final Logger log = Logger.getLogger(OruitKey.class);
    /**
     * 瑞特特殊加密
     *
     * @param key 加密的秘钥
     * @param content 需要加密的内容
     * @return 加密结果 如果有加密失败将返回null
     */
    public static String encrypt(String key, String content) {
        try {
            String md5 = OruitMD5.getMD5UpperCaseStr(key);
            String encryptKey = md5.substring(1, 9);
            String result = OruitDES.encryptDES(content, encryptKey);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 瑞特特殊加密 version1.2
     * @param key 加密的秘钥
     * @param content 需要加密的内容
     * @return 加密结果 如果有加密失败将返回null
     */
    public static String encryptKey(String key, String content) {
        try {
            content = changKey(content);
            log.debug("---------转换后的 入参数-------------"+ content);
            String md5Content = OruitMD5.getMD5UpperCaseStr(content);
            String md5 = OruitMD5.getMD5UpperCaseStr(key);
            String encryptKey = md5.substring(1, 9);
            String result = null;
            result = OruitDES.encryptDES(md5Content, encryptKey);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
        /**
     * 瑞特特殊加密 version1.2
     * @param key 加密的秘钥
     * @param content 需要加密的内容
     * @return 加密结果 如果有加密失败将返回null
     */
    public static String encryptKeyReturn(String key, String content) {
        try {
            //log.debug("---------转换后的 入参数-------------"+ content);
            String md5Content = OruitMD5.getMD5UpperCaseStr(content);
            // log.debug("---------转换后的 入参数 md5 -------------"+ md5Content);
            String md5 = OruitMD5.getMD5UpperCaseStr(key);
            String encryptKey = md5.substring(1, 9);
             //log.debug("=========key============="+ encryptKey);
            String result = null;
            result = OruitDES.encryptDES(md5Content, encryptKey);
             //log.debug("=========结果============="+ result);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 瑞特特殊加密 version1.2
     * @param key 加密的秘钥
     * @param content 需要加密的内容
     * @return 加密结果 如果有加密失败将返回null
     */
    public static String encryptForShare(String key, String content) {
        try {
            log.debug("---------转换后的 入参数-------------"+ content);
            String md5Content = OruitMD5.getMD5UpperCaseStr(content);
             log.debug("---------转换后的 入参数 md5 -------------"+ md5Content);
            String md5 = OruitMD5.getMD5UpperCaseStr(key);
            String encryptKey = md5.substring(1, 9);
             log.debug("=========key============="+ encryptKey);
            String result = null;
            result = OruitDES.encryptDES(md5Content, encryptKey);
             log.debug("=========结果============="+ result);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 替换入参中的key的值
     *
     * @param content
     * @return
     */
    private static String changKey(String content) {
        String[] arr = content.split(",");
        StringBuilder buffer = new StringBuilder();
        for (String string : arr) {
            if (string.contains("\"Key\"") && string.contains("}")) {
                buffer.append("\"Key\":\"0\"}");
            } else if (string.contains("\"Key\"")) {
                buffer.append("\"Key\":\"0\"" + ",");
            } else if (string.contains("}")) {
                buffer.append(string);
            } else {
                buffer.append(string).append(",");
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        /*String ss = "{\"Method\":\"TalkList\",\"Infversion\":\"1.0\",\"Key\":\"AF4/3ma5fq7VZAXeYnrx91A==\",\"UID\":\"4441\",\"UserId\":\"1\",\"Type\":\"1\",\"Index\":\"1\"}";
        String[] arr = ss.split(",");
        StringBuilder buffer = new StringBuilder();
        for (String string : arr) {
            if (string.contains("\"Key\"") && string.contains("}")) {
                buffer.append("\"Key\":\"0\"}");
            } else if (string.contains("\"Key\"")) {
                buffer.append("\"Key\":\"0\"" + ",");
            } else if (string.contains("}")) {
                buffer.append(string);
            } else {
                buffer.append(string).append(",");
            }
        }
        System.out.println("---------" + buffer.toString());*/
        String encrypt = encrypt("11111111", "1111fd1111");
        System.out.println(encrypt);

    }
}
