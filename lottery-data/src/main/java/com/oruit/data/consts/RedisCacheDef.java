package com.oruit.data.consts;

/**
 * 缓存常量定义
 * @author daijian.song
 * @create 2015-6-15 下午6:08:41
 * @version cache 2.0
 */
public class RedisCacheDef {
	
	/**
	 * redis操作成功常量
	 */
	public static final String OK = "OK";
	
	/**
	 * redis记录
	 */
	public static final String KEY_RECORD = "record:";
	
	/**
	 * redis活跃列表
	 */
	public static final String KEY_ACTIVE_LIST = "active";
	
	/**
	 * keyValue存储
	 */
	public static final String KEY_KEYVALUE = "keyValue:";

	/**
	 * 活动列表
	 */
	public static final String KEY_ACTIVITY_LIST = "activityList:";
	
	/**
	 * 启动屏
	 */
	public static final String KEY_LAUNCHER_LIST = "launcher:";
	
	/**
	 * banner列表
	 */
	public static final String KEY_BANNER_LIST = "bannerList:";
	
	/**
	 * 累计成交金额列表
	 */
	public static final String KEY_TURNOVER_LIST = "turnOverList:";
	
	/**
	 * 投资排行榜列表
	 */
	public static final String KEY_RANKING_LIST = "rankingList:";
	
	/**
	 * 投资记录列表
	 */
	public static final String KEY_INVESTRECORD_LIST = "investRecordList:";
	
	/**
	 * 项目信息
	 */
	public static final String KEY_LOAN = "newLoan";
	
	/**
	 * 平台统计信息 - 定时更新
	 */
//	public static final String KEY_PLATFORMSTATISTIC = "platformStatistic";
	
	/**
	 * 平台统计信息 - 日终更新
	 */
	public static final String KEY_PLATFORMSTATISTIC_DAYOFF = "platformStatisticDayOff";
	
	/**
	 * 平台统计信息 - 总注册用户数
	 */
	public static final String KEY_PLATFORM_TOTAL_INVESTOR = "totalInvestor";
	
	/**
	 * 平台统计信息 - 累计成交金额
	 */
	public static final String KEY_PLATFORM_TOTAL_AMOUNT = "totalAmount";
	
	/**
	 * 平台公告列表
	 */
	public static final String KEY_USERNOTICE_LIST = "userNoticeList:";
	
	// 用户信息缓存定义开始
	/**
	 * 用户信息列表
	 */
	public static final String KEY_USER_LIST = "userList:";
	
	/**
	 * 用户统计信息列表
	 */
	public static final String KEY_USER_STATISTIC_LIST = "userStatisticList:";
	
	/**
	 * 用户投资收益列表
	 */
	public static final String KEY_USER_INVESTPROFIT_LIST = "userInvestProfitList:";
	
	/**
	 * 启动屏KEY
	 */
	public static final String KEY_LAUNCHER = "launcher:";
}