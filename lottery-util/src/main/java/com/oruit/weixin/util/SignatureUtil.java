package com.oruit.weixin.util;

import com.oruit.weixin.paymch.bean.MchPayNotify;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SignatureUtil {

    /**
     * 生成 flowSingn 流量包 加密认证
     *
     * @param map
     * @return
     */
    public static String generateFlowSign(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        boolean flag = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            if (flag) {
                builder.append(",").append(value);
            } else {
                builder.append(value);
                flag = true;
            }
        }
        System.out.println("----" + builder.toString());
        return DigestUtils.md5Hex(builder.toString()).toUpperCase();
    }

    /**
     * 生成事件消息接收签名
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String generateEventMessageSignature(String token, String timestamp, String nonce) {
        String[] array = new String[]{token, timestamp, nonce};
        Arrays.sort(array);
        String s = StringUtils.arrayToDelimitedString(array, "");
        return DigestUtils.shaHex(s);
    }

    /**
     * 生成 package 字符串
     *
     * @param map
     * @param paternerKey
     * @return
     */
    public static String generatePackage(Map<String, String> map, String paternerKey) {
        String sign = generateSign(map, paternerKey);
        Map<String, String> tmap = MapUtil.order(map);
        String s2 = MapUtil.mapJoin(tmap, false, true);
        return s2 + "&sign=" + sign;
    }

    /**
     * 生成sign MD5 加密 toUpperCase
     *
     * @param map
     * @param paternerKey
     * @return
     */
    public static String generateSign(Map<String, String> map, String paternerKey) {
        Map<String, String> tmap = MapUtil.order(map);
        if (tmap.containsKey("sign")) {
            tmap.remove("sign");
        }
        String str = MapUtil.mapJoin(tmap, false, false);
        return DigestUtils.md5Hex(str + "&key=" + paternerKey).toUpperCase();
    }
    /**
     * 生成sign MD5 加密 toUpperCase
     *
     * @param map
     * @param paternerKey 支付生成key
     * @return
     */
    public static String generateSign1(Map<String, Object> map, String paternerKey) {
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + paternerKey;
        //Util.log("Sign Before MD5:" + result);
        result =DigestUtils.md5Hex(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }

    /**
     * 生成 paySign
     *
     * @param map
     * @param paySignKey
     * @return
     */
    public static String generatePaySign(Map<String, String> map, String paySignKey) {
        if (paySignKey != null) {
            map.put("appkey", paySignKey);
        }
        Map<String, String> tmap = MapUtil.order(map);
        String str = MapUtil.mapJoin(tmap, true, false);
        return DigestUtils.shaHex(str);
    }

    /**
     * 验证 mch pay notify sign 签名
     *
     * @param mchPayNotify
     * @param key mch key
     * @return
     */
    public static boolean validateAppSignature(MchPayNotify mchPayNotify, String key) {
        Map<String, String> map = MapUtil.objectToMap(mchPayNotify);
        return mchPayNotify.getSign().equals(generateSign(map, key));
    }
}
