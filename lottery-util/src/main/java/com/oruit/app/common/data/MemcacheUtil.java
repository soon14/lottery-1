/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.common.data;

import java.io.IOException;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.internal.OperationFuture;

/**
 * memcache
 *
 * @author hanfeng
 */
public class MemcacheUtil {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MemcacheUtil.class);

    private final static String HOST = "f2834dba1c7e41a0.m.cnqdalicm9pub001.ocs.aliyuncs.com";//控制台上的“内网地址”
    private final static String PORT = "11211"; //默认端口 11211，不用改
    private final static String USERNAME = "f2834dba1c7e41a0";//控制台上的“访问账号“
    private final static String PASSWORD = "4wSUnMsb3TeVY0JuzT";//邮件或短信中提供的“密码”

    private MemcachedClient cache = null;
    private static MemcacheUtil memcacheUtil = null;

    protected MemcacheUtil() {
        createMemcacheClient();
    }

    public static MemcacheUtil getInstance() {
        if (memcacheUtil == null) {
            memcacheUtil = new MemcacheUtil();
        }
        return memcacheUtil;
    }
    
    
    /**
     * 更新数据对一致性要求不高的数据
     * @param key
     * @param exp
     * @param value 
     */
    public void cacheSet(String key,int exp,Object value)
    {
        cache.set(key, exp, value);
    }
    
    /**
     * 更新数据对一致性要求不高的数据
     * @param key
     * @return 
     */
    public Object cacheGet(String key)
    {
        return cache.get(key);
    }
    
    /**
     * 在memcached中存入数据
     * @param key
     * @param exp
     * @param value 
     */
    public void cacheAdd(String key, int exp, Object value) {
        cache.add(key, exp, value);
    }
    
    /**
     * 更新单原子操作的值
     * @param key
     * @param casId
     * @param exp
     * @param value 
     * @return  
     */
    public boolean cacheCas(String key, long casId, int exp, Object value) {
        CASResponse casResponse = cache.cas(key, casId, exp, value);
        if(casResponse == casResponse.OK)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 获取单元字操作的值和唯一标记
     * @param key
     * @return 
     */
    public CacheCasValue cacheGets(String key) {
        CASValue casValue = cache.gets(key);
        CacheCasValue cacheCasValue = new CacheCasValue();
        cacheCasValue.setCas(casValue.getCas());
        cacheCasValue.setValue(casValue.getValue());
        return cacheCasValue;
    }
    
    /**
     * 删除指定key的memcache对应的值
     * @param key 
     */
    public void cacheDelete(String key) {
        cache.delete(key);
    }
    
    /**
     * 删除指定key的memcache对应的值(带事务的版本)
     * @param key 
     */
    public boolean cacheDelete(String key,long cas) {
        OperationFuture<Boolean> operationFuture = cache.delete(key,cas);
        return operationFuture.getStatus().isSuccess();
    }

    private void createMemcacheClient() {
        AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(USERNAME, PASSWORD));
        try {
            cache = new MemcachedClient(
                    new ConnectionFactoryBuilder().setProtocol(Protocol.BINARY)
                    .setAuthDescriptor(ad)
                    .build(),
                    AddrUtil.getAddresses(HOST + ":" + PORT));
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }
}
