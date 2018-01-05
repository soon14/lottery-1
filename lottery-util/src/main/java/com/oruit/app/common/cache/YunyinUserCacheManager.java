/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.common.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * 红包缓存
 *
 * @author Liuk
 */
public class YunyinUserCacheManager {

    //缓存容器

    private static List<Integer> users = new ArrayList<>();//初始化缓存

    /**
     * 添加缓存
     *
     * @param userList
     */
    public static void put(List<Integer> userList) {
        if (userList == null) {
            return;
        }
        if (users == null) {
            users = new ArrayList<>();
        }
        users.clear();//首先清空之前的缓存
        users.addAll(userList);//添加缓存
    }

    /**
     * 获取缓存
     *
     * @return
     */
    public static List<Integer> get() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    /**
     * 清除缓存
     *
     */
    public static void removeAll() {
        if (users!=null) {
            users.clear();
        }
    }
}
