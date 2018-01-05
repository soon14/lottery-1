/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.logic.util;

import com.alibaba.fastjson.JSONObject;
import java.util.Random;

/**
 * 活动相关通用处理方法
 * @author hanfeng
 */
public class ActivityUtil {
    public final static String ACTIVITY_REWARD_YUN = "元";
    public final static String ACTIVITY_REWARD_JIAO = "角";
    public final static String ACTIVITY_REWARD_FEN = "分";
    
    public final static String ACTIVITY_CASH_FIRST = "cash_first";
    public final static String ACTIVITY_SHARE_FIRST = "share_first";
    
    // 获取随机奖励的金币数
    public static Integer getActivityRewardGold(Integer mingold,Integer maxgold,String rewardMoneyType)
    {
        if(mingold.equals(maxgold))
        {
            return mingold;
        }
        Integer rewardGold = 0;
        switch (rewardMoneyType)
        {
            case ACTIVITY_REWARD_YUN:
                rewardGold = mingold + ((new Random()).nextInt(maxgold/1000 - mingold/1000)) * 1000;
                break;
            case ACTIVITY_REWARD_JIAO:
                rewardGold = mingold + ((new Random()).nextInt(maxgold/100 - mingold/100)) * 100;
                break;
            case ACTIVITY_REWARD_FEN:
                rewardGold = mingold + ((new Random()).nextInt(maxgold/10 - mingold/10)) * 10;
                break;
            default:
                rewardGold = mingold;
        }
        return rewardGold;
    }
}
