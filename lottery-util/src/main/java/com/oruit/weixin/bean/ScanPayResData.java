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
public class ScanPayResData {

    //协议层

    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String return_code;
    @XmlElement
    @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
    private String return_msg;

    //协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
    @XmlElement
    private String sign;
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
    private String mch_billno;
    @XmlElement
    private String mch_id;
    @XmlElement
    private String wxappid;
    @XmlElement
    private String re_openid;
    @XmlElement
    private String total_amount;
    @XmlElement
    private String send_time;
    @XmlElement
    private String send_listid;

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
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

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getSend_listid() {
        return send_listid;
    }

    public void setSend_listid(String send_listid) {
        this.send_listid = send_listid;
    }

    @Override
    public String toString() {
        return "ScanPayResData{" + "return_code=" + return_code + ", return_msg=" + return_msg + ", sign=" + sign + ", result_code=" + result_code + ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", mch_billno=" + mch_billno + ", mch_id=" + mch_id + ", wxappid=" + wxappid + ", re_openid=" + re_openid + ", total_amount=" + total_amount + ", send_time=" + send_time + ", send_listid=" + send_listid + '}';
    }

}
