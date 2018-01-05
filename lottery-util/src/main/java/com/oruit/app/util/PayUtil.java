package com.oruit.app.util;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建时间：2016年11月2日 下午7:12:44
 * 
 * @author andy
 * @version 2.2
 */

public class PayUtil {
	public static void main(String[] args) {
		System.out.println(getTradeNo());
	}

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	public static String getTradeNo() {
		int random = (int) ((Math.random() * 9 + 1) * 100000);
		// 自增8位数 00000001
		return DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + random;
	}


	/**
	 * 退款单号
	 * 
	 * @return
	 */
	public static String getRefundNo() {
		// 自增8位数 00000001
		return "RNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
	}

	/**
	 * 退款单号
	 * 
	 * @return
	 */
	public static String getTransferNo() {
		// 自增8位数 00000001
		return "TNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
	}

	/**
	 * 获取服务器的ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getLocalIp(HttpServletRequest request) {
		return request.getLocalAddr();
	}

	/**
	 * 创建支付随机字符串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		return RandomUtil.randomString(RandomUtil.LETTER_NUMBER_CHAR, 32);
	}

	/**
	 * 支付时间戳
	 * 
	 * @return
	 */
	public static String payTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}


}
