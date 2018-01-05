package com.oruit.weixin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * User: rizenguo Date: 2014/10/22 Time: 16:42
 */
/**
 * 被扫支付提交Post数据给到API之后，API会返回XML格式的数据，这个类用来装这些数据
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransfersResData {

    //协议层

    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String return_code;
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String return_msg;

    //协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String mch_appid;
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String mchid;
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String device_info;
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String nonce_str;
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String result_code;
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String err_code;
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String err_code_des;

    //业务返回的具体数据（以下字段在return_code 和result_code 都为SUCCESS 的时候有返回）
    @XmlElement
    private String partner_trade_no;
    @XmlElement
    private String payment_no;
    @XmlElement
    private String payment_time;
    
    
    @Override
    public String toString() {
        return "Transfers{" + "return_code=" + return_code + ", return_msg=" + return_msg + ", mch_appid=" + mch_appid + ", result_code=" + result_code + ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", mchid=" + mchid + ", device_info=" + device_info + ", nonce_str=" + nonce_str + ", partner_trade_no=" + partner_trade_no + ", payment_no=" + payment_no + ", payment_time=" + payment_time + '}';
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getMch_appid() {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMch_billno(String mchid) {
        this.mchid = mchid;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getPayment_no() {
        return payment_no;
    }

    public void setPayment_no(String payment_no) {
        this.payment_no = payment_no;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }


}
