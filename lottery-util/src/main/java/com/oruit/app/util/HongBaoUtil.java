/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Liuk
 */
public class HongBaoUtil {

    /**
     *
     * @param total 总金额
     * @param num 红包的次数
     * @param min 最小红包的金额
     * @return
     */
    public static Set<Integer> hb(double total, int num, double min) {
        double targertotal = total;
        Set<Integer> list = new HashSet<>();
        for (int i = 1; i < num; i++) {
            double safe_total = (total - (num - i) * min) / (num - i);
            double money = Math.random() * (safe_total - min) + min;
            BigDecimal money_bd = new BigDecimal(money);
            money = money_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            total = total - money;
            BigDecimal total_bd = new BigDecimal(total);
            total = total_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Integer resultMoney = Double.valueOf(money * 1000).intValue();
            Integer resultTotle = Double.valueOf(total * 1000).intValue();
            System.out.println("第" + i + "个红包：" + resultMoney + ",余额为:" + resultTotle + "元");
            list.add(resultMoney);
        }
        Integer resultTotle = Double.valueOf(total * 1000).intValue();
        list.add(resultTotle);
        System.out.println("第" + num + "个红包：" + resultTotle + ",余额为:0元");
        if(list.size() == num){
             return list;
        }else{
            list = hb(targertotal, num, min);
            return list;
        }
    }
    
    public static void main(String[] args) {
    }
}
