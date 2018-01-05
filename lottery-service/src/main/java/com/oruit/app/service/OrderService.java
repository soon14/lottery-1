package com.oruit.app.service;/**
 * Created by wyt on 2017/8/28.
 */

import com.oruit.app.util.web.ResultBean;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

/**
 * wyt
 * 支付宝微信下单
 * @author
 * @create 2017-08-28 13:56
 */
public interface OrderService {
    /**
     * 双色球的购彩
     * @param map
     * @return
     * @throws Exception
     */
    ResultBean SSQPurchaseH5(Map<String,Object> map) throws Exception;

    /**
     * 提现和充值下单
     * @param map
     * @return
     */
    ResultBean Order(Map<String,Object> map);

    /**
     * 浏览器端快乐十分
     * @param request
     * @return
     */
    ResultBean ClientOrder(HttpServletRequest request) throws ParseException;

    /**
     * 修改订单状态
     * @param orderId
     * @param state
     * @return
     */
     Integer updateOrder(String orderId,Integer state );
    /**
     * 修改订单状态
     * @param orderId
     * @param state
     * @return
     */
    Integer updateOrderCheck(String orderId,Integer state );

    /**
     * 更新充值的钱数(成功充值)
     * @param openId
     * @param totalAmount
     * @return
     */
    Integer updateMoney(String openId , BigDecimal totalAmount );

    /**
     * 更新充值的钱数(充值失败)
     * @param openId
     * @param totalAmount
     * @return
     */
    Integer updateFailMoney(String openId , BigDecimal totalAmount );


    /**
     * APP 下单（双色球快乐十分）
     * @param map
     * @return
     */
    ResultBean ConfirmOrder(Map<String,Object> map) throws Exception;

    /**
     * 购买书籍下单
     * @param request
     * @return
     */
    ResultBean BookOrder(HttpServletRequest request);

    /**
     * 浏览器端快乐十分下单
     * @param request
     * @return
     */
    ResultBean BrowserOrders(HttpServletRequest request);

    /**
     * 支付宝更新充值的钱数(成功充值)
     * @param userid
     * @param totalAmount
     * @return
     */
    Integer updateMoneyAlipay(String userid , BigDecimal totalAmount );

    /**
     * 浏览器端快乐十分下单
     * @param request
     * @return
     */
    ResultBean CommitOrderH5(HttpServletRequest request);

    /**
     * 第三方购买
     * @return
     */
    void chongzhithree(String params) throws Exception;
    /**
     * 浏览器端快乐十分
     * @param request
     * @return
     */
    ResultBean ClientOrderTwo(HttpServletRequest request) throws ParseException;
    /**
     * 浏览器端快乐十分
     * @param request
     * @return
     */
    void Ordertuikuan(HttpServletRequest request) throws Exception;
}
