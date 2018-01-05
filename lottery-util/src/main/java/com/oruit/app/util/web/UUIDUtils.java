package com.oruit.app.util.web;/**
 * Created by wyt on 2017/8/24.
 */

import java.util.UUID;

/**
 * @author
 * @create 2017-08-24 14:57
 */
public class UUIDUtils {

    public static String uuid(){
        String str = UUID.randomUUID().toString();
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return temp;
    }
}
