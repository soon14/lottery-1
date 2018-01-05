/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.common.data;

/**
 *
 * @author hanfeng
 */
public class MemcacheLogicConst {
    
    // 红包相关--红包计数
    public final static String RED_PACKET_COUNT = "redpacketcount";
    // 红包相关--红包运气王位置（-1的时候，表示需要运营号抢红包）
    public final static String RED_PACKET_LUCKY_KING_INDEX = "redpacketluckykingindex"; 
    // 红包相关--红包的信息（小红包）
    public final static String RED_PACKET_CACHE = "redpecketcache";
    // 红包相关--发红包的开关
    public final static String RED_PACKET_SWITCH = "redpacketswitch";
}
