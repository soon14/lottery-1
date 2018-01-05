package com.oruit.app.service;/**
 * Created by wyt on 2017/8/28.
 */

import com.oruit.app.util.web.ResultBean;

import java.text.ParseException;
import java.util.Map;

/**
 * @author
 * @create 2017-08-28 15:25
 */
public interface WithdrawService {

    /**
     * 支付宝微信提现
     * @param params
     * @return
     */
    ResultBean withdrawals(Map<String,Object> params) throws ParseException;

    /**
     * 提现详情
     * @param params
     * @return
     */
    ResultBean withdrawalsDetails(Map<String,Object> params);

    /**
     * 提现到银行卡页面初始化
     * @param params
     * @return
     */
    ResultBean BankCardInitialization(Map<String,Object> params);

    /**
     * 提现到银行卡
     * @param params
     * @return
     */
    ResultBean BankCardWithdrawals(Map<String,Object> params) throws ParseException;
    /**
     * 提现到银行卡记录
     * @param params
     * @return
     */
    ResultBean BankCardWithdrawalsList(Map<String,Object> params);






}
