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
public class TimeCacheArticleList {
    // 此处缓存推荐文章列表，一共缓存8个。
    private static TimeCacheMap<String, List<Map<String,Object>>> recommendCache = new TimeCacheMap<>(60, 8);//过期时间设置为1分钟 
    private static TimeCacheMap<String, List<Map<String,Object>>> recommendCacheV2 = new TimeCacheMap<>(60, 8);//过期时间设置为1分钟 
    
    /**
     * android4,android6,android8,android10  ios4,ios6,ios8,ios10
     * @param keyword
     * @return 
     */
    public static List<Map<String,Object>> getRecommendCache(String keyword)
    {
        return recommendCache.get(keyword);
    }
    
    public static void putRecommendCache(String keyword,List<Map<String,Object>> item)
    {
        recommendCache.put(keyword, item);
    }
    
    /**
     * android4,android6,android8,android10  ios4,ios6,ios8,ios10
     * @param keyword
     * @return 
     */
    public static List<Map<String,Object>> getRecommendCacheV2(String keyword)
    {
        return recommendCacheV2.get(keyword);
    }
    
    public static void putRecommendCacheV2(String keyword,List<Map<String,Object>> item)
    {
        recommendCacheV2.put(keyword, item);
    }
}
