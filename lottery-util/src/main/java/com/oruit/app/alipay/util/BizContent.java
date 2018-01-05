/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.alipay.util;

/**
 * 
 * @author hanfeng
 */
public class BizContent {
    
    /**
     * 商户转账唯一订单号。发起转账来源方定义的转账单据ID，用于将转账回执通知给来源方。 
     * 不同来源方给出的ID可以重复，同一个来源方必须保证其ID的唯一性。 只支持半角英文、数字，及“-”、“_”。
     * 最大长度：64
     */
    private String out_biz_no;
    /**
     * 收款方账户类型。可取值： 
     * 1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。 
     * 2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式（系统使用这个）
     */
    private String payee_type;
    /**
     * 收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
     */
    private String payee_account;
    /**
     * 转账金额，单位：元。 只支持2位小数，小数点前最大支持13位，金额必须大于0。
     */
    private String amount;
    /**
     * 付款方真实姓名（最长支持100个英文/50个汉字）。 
     * 如果本参数不为空，则会校验该账户在支付宝登记的实名是否与付款方真实姓名一致。
     * 为空即可
     */
    private String payer_real_name;
    /**
     * 付款方显示姓名（最长支持100个英文/50个汉字）。 如果不传，则默认显示该账户在支付宝登记的实名。收款方可见。
     * 默认：呱呱支付宝提现
     */
    private String payer_show_name;
    /**
     * 收款方真实姓名（最长支持100个英文/50个汉字）。
     * 如果本参数不为空，则会校验该账户在支付宝登记的实名是否与收款方真实姓名一致。
     * 该字段必须输入
     */
    private String payee_real_name;
    /**
     * 转账备注（支持200个英文/100个汉字）。 
     * 当付款方为企业账户，且转账金额达到（大于等于）50000元，remark不能为空。
     * 收款方可见，会展示在收款用户的账单中。
     */
    private String remark;
    /**
     * 扩展参数，json字符串格式，
     * 目前仅支持的key：order_title：收款方转账账单标题。 
     * 用于商户的特定业务信息的传递，只有商户与支付宝约定了传递此参数且约定了参数含义，
     * 此参数才有效
     * 空
     */
    private String ext_param;

    /**
     * @return the out_biz_no
     */
    public String getOut_biz_no() {
        return out_biz_no;
    }

    /**
     * @param out_biz_no the out_biz_no to set
     */
    public void setOut_biz_no(String out_biz_no) {
        this.out_biz_no = out_biz_no;
    }

    /**
     * @return the payee_type
     */
    public String getPayee_type() {
        return payee_type;
    }

    /**
     * @param payee_type the payee_type to set
     */
    public void setPayee_type(String payee_type) {
        this.payee_type = payee_type;
    }

    /**
     * @return the payee_account
     */
    public String getPayee_account() {
        return payee_account;
    }

    /**
     * @param payee_account the payee_account to set
     */
    public void setPayee_account(String payee_account) {
        this.payee_account = payee_account;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the payer_real_name
     */
    public String getPayer_real_name() {
        return payer_real_name;
    }

    /**
     * @param payer_real_name the payer_real_name to set
     */
    public void setPayer_real_name(String payer_real_name) {
        this.payer_real_name = payer_real_name;
    }

    /**
     * @return the payer_show_name
     */
    public String getPayer_show_name() {
        return payer_show_name;
    }

    /**
     * @param payer_show_name the payer_show_name to set
     */
    public void setPayer_show_name(String payer_show_name) {
        this.payer_show_name = payer_show_name;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the ext_param
     */
    public String getExt_param() {
        return ext_param;
    }

    /**
     * @param ext_param the ext_param to set
     */
    public void setExt_param(String ext_param) {
        this.ext_param = ext_param;
    }

    /**
     * @return the payee_real_name
     */
    public String getPayee_real_name() {
        return payee_real_name;
    }

    /**
     * @param payee_real_name the payee_real_name to set
     */
    public void setPayee_real_name(String payee_real_name) {
        this.payee_real_name = payee_real_name;
    }

    @Override
    public String toString() {
        return "AlipayFundTransToaccountTransferRequestParam{" + "out_biz_no=" + out_biz_no + ", payee_type=" + payee_type + ", payee_account=" + payee_account + ", amount=" + amount + ", payer_real_name=" + payer_real_name + ", payer_show_name=" + payer_show_name + ", payee_real_name=" + payee_real_name + ", remark=" + remark + ", ext_param=" + ext_param + '}';
    }
}
