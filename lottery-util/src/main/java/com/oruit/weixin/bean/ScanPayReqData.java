package com.oruit.weixin.bean;

/**
 * User: rizenguo
 * Date: 2014/10/22
 * Time: 21:29
 */




import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 请求被扫支付API需要提交的数据
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScanPayReqData {

    //每个字段具体的意思请查看API文档
    @XmlElement
    private String nonce_str ;
    @XmlElement
    private String sign ;
    @XmlElement
    private String mch_billno ;
    @XmlElement
    private String mch_id ;
    @XmlElement
    private String wxappid ;
    @XmlElement
    private String send_name ;
    @XmlElement
    private String re_openid ;
    @XmlElement
    private int total_amount = 0;
    @XmlElement
    private String total_num ;
    @XmlElement
    private String wishing ;
    @XmlElement
    private String client_ip ;
    @XmlElement
    private String act_name ;
    @XmlElement
    private String remark ;

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ScanPayReqData{" + "nonce_str=" + nonce_str + ", sign=" + sign + ", mch_billno=" + mch_billno + ", mch_id=" + mch_id + ", wxappid=" + wxappid + ", send_name=" + send_name + ", re_openid=" + re_openid + ", total_amount=" + total_amount + ", total_num=" + total_num + ", wishing=" + wishing + ", client_ip=" + client_ip + ", act_name=" + act_name + ", remark=" + remark + '}';
    }

    
    

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
