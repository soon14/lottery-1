package com.oruit.weixin.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: rizenguo Date: 2014/10/22 Time: 16:42
 */
/**
 * 被扫支付提交Post数据给到API之后，API会返回XML格式的数据，这个类用来装这些数据
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferReqData {

    //每个字段具体的意思请查看API文档
    @Override
    public String toString() {
        return "TransferReqData{" + "mch_appid=" + mch_appid + ", mchid=" + mchid + ", device_info=" + device_info + ", nonce_str=" + nonce_str + ", sign=" + sign + ", partner_trade_no=" + partner_trade_no + ", openid=" + openid + ", check_name=" + check_name + ", re_user_name=" + re_user_name + ", amount=" + amount + ", desc=" + desc + ", spbill_create_ip=" + spbill_create_ip + '}';
    }

    @XmlElement
    private String mch_appid;
    @XmlElement
    private String mchid;
    @XmlElement
    private String device_info;
    @XmlElement
    private String nonce_str;
    @XmlElement
    private String sign;
    @XmlElement
    private String partner_trade_no;
    @XmlElement
    private String openid;
    @XmlElement
    private String check_name;
    @XmlElement
    private String re_user_name;
    @XmlElement
    private int amount;
    @XmlElement
    private String desc;
    @XmlElement
    private String spbill_create_ip;

    /**
     * @return the mch_appid
     */
    public String getMch_appid() {
        return mch_appid;
    }

    /**
     * @param mch_appid the mch_appid to set
     */
    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    /**
     * @return the mchid
     */
    public String getMchid() {
        return mchid;
    }

    /**
     * @param mchid the mchid to set
     */
    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    /**
     * @return the device_info
     */
    public String getDevice_info() {
        return device_info;
    }

    /**
     * @param device_info the device_info to set
     */
    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    /**
     * @return the nonce_str
     */
    public String getNonce_str() {
        return nonce_str;
    }

    /**
     * @param nonce_str the nonce_str to set
     */
    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * @return the partner_trade_no
     */
    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    /**
     * @param partner_trade_no the partner_trade_no to set
     */
    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    /**
     * @return the openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * @param openid the openid to set
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return the check_name
     */
    public String getCheck_name() {
        return check_name;
    }

    /**
     * @param check_name the check_name to set
     */
    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    /**
     * @return the re_user_name
     */
    public String getRe_user_name() {
        return re_user_name;
    }

    /**
     * @param re_user_name the re_user_name to set
     */
    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the spbill_create_ip
     */
    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    /**
     * @param spbill_create_ip the spbill_create_ip to set
     */
    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
