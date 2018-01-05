/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.common.cache;

import com.oruit.app.storm.utils.TimeCacheMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hanfeng
 */
public class TimeCacheMainPage {
    // 此处缓存特殊文章列表，分类按照android和ios的special
    private static TimeCacheMap<String, List<Map<String,Object>>> SPECIAL_ARTICLE_CACHE = new TimeCacheMap<>(60, 6);//过期时间设置为1分钟
    // 缓存红包雨群组信息
    private static TimeCacheMap<String, Map<String,Object>> REDPACKET_RAIN_GROUPINFO_CACHE = new TimeCacheMap<>(60, 3);//过期时间设置为1分钟
    // 缓存红包雨群组信息
    private static TimeCacheMap<String, Object> INDEX_PULLDOWN_ALERTAD_CACHE = new TimeCacheMap<>(60, 3);//过期时间设置为1分钟
    
    /**
     * 文章类别 '|choujiang|','|gonggao|','|xinshouxuetang|','|weixintixinyindao|','|shoutujiangli|','|erweimashoutuye|'
     * @param keyword = 平台+文章列表
     * @return 
     */
    public static List<Map<String,Object>> getSpecialArticleCache(String keyword)
    {
        return SPECIAL_ARTICLE_CACHE.get(keyword);
    }
    
    public static void putSpecialArticleCache(String keyword,List<Map<String,Object>> item)
    {
        SPECIAL_ARTICLE_CACHE.put(keyword, item);
    }
    
    public static Map<String,Object> getRPRainGroupInfoCache(String keyword)
    {
        return REDPACKET_RAIN_GROUPINFO_CACHE.get(keyword);
    }
    
    public static void putRPRainGroupInfoCache(String keyword,Map<String,Object> item)
    {
        REDPACKET_RAIN_GROUPINFO_CACHE.put(keyword, item);
    }
    // 
    public static Object getIndexPulldownAlertAdCache(String keyword)
    {
        return INDEX_PULLDOWN_ALERTAD_CACHE.get(keyword);
    }
    
    public static void putIndexPulldownAlertAdCache(String keyword,Object item)
    {
        INDEX_PULLDOWN_ALERTAD_CACHE.put(keyword, item);
    }
}
