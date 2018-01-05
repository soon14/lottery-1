/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.text.ParseException;

/**
 * 专业处理账号显示，主要是账号打码用
 * @author hanfeng
 */
public class AccountDealUtils {
    
    public enum AccountType {
        MOBILE, EMAIL, NORMAL_ACCOUNT
    }
    
    /**
     * 将账号部分隐藏
     * @param account
     * @return 
     */
    public static String AccountHidden(String account) {
        String dealAccount = account;
        if (StringUtils.isNotEmpty(account)) {
            switch (GetAccountType(account)) {
                case EMAIL:
                    System.out.println("EMAIL");
                    dealAccount = EmailHidden(account);
                    break;
                case MOBILE:
                    System.out.println("MOBILE");
                    dealAccount = MobileHidden(account);
                    break;
                case NORMAL_ACCOUNT:
                    System.out.println("NORMAL_ACCOUNT");
                    dealAccount = NormalAccountHidden(account);
                    break;
            }
        }
        return dealAccount;
    }
    
    public static AccountType GetAccountType(String account)
    {
        if(account.contains("@"))
        {
            return AccountType.EMAIL;
        }
        else if(PhoneFormatCheckUtils.isPhoneLegal(account))
        {
            return AccountType.MOBILE;
        }
        else
        {
            return AccountType.NORMAL_ACCOUNT;
        }
    }
    
    /**
     * 隐藏手机号
     * @param account
     * @return 
     */
    public static String MobileHidden(String account)
    {
        return account.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
    
    /**
     * 隐藏邮箱
     * @param account
     * @return 
     */
    public static String EmailHidden(String account)
    {
        return account.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }
    
    /**
     * 隐藏普通账号
     * @param account
     * @return 
     */
    public static String NormalAccountHidden(String account)
    {
        if(account.length() > 5)
        {
            return account.substring(0, 2) + createAsterisk(account.length()-5) + account.substring(account.length()-3, account.length());
        }
        return account;
    }
    
    /**
     * 隐藏姓名
     * @param name
     * @return 
     */
    public static String NameHidden(String name) {
        if(StringUtils.isEmpty(name))
        {
            return "";
        }
        if (name.length() <= 1) {
            return name;
        } else {
            return name.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + createAsterisk(name.length() - 1));
        }
    }
    
    //生成很多个*号  
    public static String createAsterisk(int length) {  
        StringBuilder stringBuffer = new StringBuilder();  
        for (int i = 0; i < length; i++) {  
            stringBuffer.append("*");  
        }  
        return stringBuffer.toString();  
    }  

    
    public static void main(String[] args) throws ParseException {
        System.out.println(NameHidden("田林"));
        System.out.println(AccountHidden("18701079978"));
        System.out.println(AccountHidden("xs_tianlin@12y.com"));
        System.out.println(AccountHidden("alilinfeng_yan"));
    }
}


