package com.oruit.data.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存计数器
 * 
 * @author daijian.song
 * @create 2015-6-17 上午10:05:27
 * @version cache 2.0
 */
public class RedisCacheCounter implements Serializable {

	private static final long serialVersionUID = -1268622763198788185L;

	private static Logger logger = LoggerFactory.getLogger(RedisCacheCounter.class);

	private long count = 0L;// 计数器

	private long targetCount = 0L;// 命中计算器

	private long count_B = 1L;

	private long targetCount_B = 1L;

	private final long long_max = Long.MAX_VALUE;

	private HashMap<String, String> cacheCounterMap = null;// 查询当前缓存状况

	private HashMap<String, String> dateCacheCounterMap = null;// 用于保存到日志中的缓存（一天一存）
	
	/**
	 * 最近更新时间
	 */
	private Date latestUpdateTime;

	public RedisCacheCounter() {
		super();
	}
	
	/**
	 * 缓存计数器+1
	 * 
	 *
	 * @author daijian.song
	 * @create 2015-6-17 上午10:39:18
	 * @version cache 2.0
	 */
	public void countAddOne() {
		if (count > long_max) {// 当当前计数值达到19位时 进位计数+1
			count = 0L;
			count_B++;
		}
		count++;
	}
	
	/**
	 * 命中率计数器+1
	 * 
	 *
	 * @author daijian.song
	 * @create 2015-6-17 上午10:38:58
	 * @version cache 2.0
	 */
	public void targetCountAddOne() {

		if (targetCount > long_max) {// 当当前计数值达到19位时 进位计数+1
			targetCount = 0L;
			targetCount_B++;
		}
		targetCount++;
	}
	
	/**
	 * 保存缓存计数器信息到日志
	 * 
	 *TODO
	 * @author daijian.song
	 * @create 2015-6-17 上午10:38:30
	 * @version cache 2.0
	 */
	public void saveCacheCount() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		HashMap<Integer, HashMap<String, String>> map = new HashMap<Integer, HashMap<String, String>>();
		dateCacheCounterMap = new HashMap<String, String>();
		dateCacheCounterMap.put("count", String.valueOf(count * count_B));
		dateCacheCounterMap.put("targetCount",
				String.valueOf(targetCount * targetCount_B));
		map.put(day, dateCacheCounterMap);
		logger.info("缓存计数器", map);
	}
	
	/**
	 * 清除计数器
	 *
	 * @author daijian.song
	 * @create 2015-6-17 上午10:38:13
	 * @version cache 2.0
	 */
	public void cleanRedisCacheCounter() {
		this.count = 0L;// 计数器
		this.targetCount = 0L;// 命中率计算器
		this.count_B = 1L;
		this.targetCount_B = 1L;
	}
	
	/**
	 * 获取缓存计数器信息
	 * @return
	 *
	 * @author daijian.song
	 * @create 2015-6-17 上午10:37:03
	 * @version cache 2.0
	 */
	public HashMap<String, String> getRedisCacheCounter() {
		cacheCounterMap = new HashMap<String, String>();
		cacheCounterMap.put("count", String.valueOf(count * count_B));
		cacheCounterMap.put("targetCount",
				String.valueOf(targetCount * targetCount_B));
		return cacheCounterMap;
	}

	/**
	 * 获取命中率
	 * 
	 * @return 命中率百分比
	 * 
	 * @author daijian.song
	 * @create 2015-6-17 上午10:36:14
	 * @version cache 2.0
	 */
	public String getHitRate() {
		String result = "";
		if (count == 0 || targetCount == 0) {
			return "0.00%";
		}
		try {
			double temp_y = targetCount * 1.0;
			double temp_z = count * 1.0;
			double fen = temp_y / temp_z;
			DecimalFormat df = new DecimalFormat("##.00%"); // ##.00%
			result = df.format(fen);
		} catch (Exception e) {
			result = "0.00%";
			e.printStackTrace();
		}
		return result;
	}

	public Date getLatestUpdateTime() {
		return latestUpdateTime;
	}

	public void setLatestUpdateTime(Date latestUpdateTime) {
		this.latestUpdateTime = latestUpdateTime;
	}
}