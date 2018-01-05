/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

/**
 *
 * @author 康
 */
public class EmojiFilterUtils {
     /**
     * 将emoji表情替换成*
     * 
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        }else{
            return source;
        }
    }
    public static void main(String[] arg ){
        try{
            String text = "Chennie🌟";
            System.out.println(text);
            System.out.println(text.length());
            System.out.println(filterEmoji(text));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
