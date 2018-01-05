/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.common.cache;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 红包缓存对象
 *
 * @author Liuk
 */
public class RedPackeCache implements Serializable{

    //红包个数 
    private AtomicInteger redCount = new AtomicInteger();
    
    //红包金额列表
    private List<Integer> redSet = new LinkedList<>();
    
    //抢红包的用户列表
    private Set<Integer> userSet = new LinkedHashSet<>();
    //红包ID
    private Integer redId = 0;
    
    private Integer luckyKingIndex = -1;

    public AtomicInteger getRedCount() {
        return redCount;
    }

    public void setRedCount(AtomicInteger redCount) {
        this.redCount = redCount;
    }

    public List<Integer> getRedSet() {
        return redSet;
    }

    public void setRedSet(List<Integer> redSet) {
        this.redSet = redSet;
    }

    public Set<Integer> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<Integer> userSet) {
        this.userSet = userSet;
    }

    public Integer getRedId() {
        return redId;
    }

    public void setRedId(Integer redId) {
        this.redId = redId;
    }

    public Integer getLuckyKingIndex() {
        return luckyKingIndex;
    }

    public void setLuckyKingIndex(Integer luckyKingIndex) {
        this.luckyKingIndex = luckyKingIndex;
    }

}
