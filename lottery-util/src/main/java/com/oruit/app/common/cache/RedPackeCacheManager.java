/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 红包缓存
 * @author Liuk
 */
public class RedPackeCacheManager {
    //map key的前缀
    private static String KEY = "group";
    //缓存容器
    private static Map<String,RedPackeCache> cacheMap = new ConcurrentHashMap<>();
    
    /**
     * 添加缓存
     * @param groupId
     * @param cache 
     */
    public static void put(Integer groupId,RedPackeCache cache){
        cacheMap.put(KEY+groupId, cache);
    }
    /**
     * 获取缓存
     * @param groupId
     * @return 
     */
    public static RedPackeCache get(Integer groupId){
        if(cacheMap.containsKey(KEY+groupId)){
             return cacheMap.get(KEY+groupId);
        }else{
            return null;
        }
    }
    
    /**
     * 删除缓存
     * @param groupId 
     */
    public static void remove(Integer groupId){
        cacheMap.remove(KEY+groupId);
    }
}
