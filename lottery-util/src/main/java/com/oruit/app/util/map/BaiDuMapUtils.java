/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util.map;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.math.RandomUtils;

/**
 *
 * @author hanfeng
 */
public class BaiDuMapUtils {

//    private static String ak = "izdsDyOsAXKpxTxLITqe2lohOBsh71pZ";
//    private static String ak = "Xt64DdiefEohtu3FD2yWTKEh";
//    private static String ak = "ek2qu2DPUwjTgWHIY4sOUXMa8oTLuOBU";
    private static String [] akArr = {"DPUXv8wvv2k7gw4Gj9IM11Bns0IoM7ck"
            ,"gklPsjmLd4Z9kBVbdHcDQT6ifQ5cxVjr","it8vtjc2T5n19OqxTuyoGF0ztnnl2raT","BR6Z8uFGXWaap5IFEn65SavUcfnh7IOG"
            ,"izdsDyOsAXKpxTxLITqe2lohOBsh71pZ" ,"Xt64DdiefEohtu3FD2yWTKEh","ek2qu2DPUwjTgWHIY4sOUXMa8oTLuOBU"
            ,"8HAFCTDy2D7ybsdKI7csyEkHp64EEwZz","NlOvnsDuGwk6iqbxdMttmboSZpGvYyBd","elPqKFT9jXTuAKVxmGZHS7K2EnG854sF"
            ,"qmwm6R8emUctSFiSBfnG9oBOuUKSjbFx","B7DPZH1IrPbrwqzYswm5uYU5dVWqmM3K","ajcy7mCIRHlocIXKiCRj1k0Dcd47us4T"
            ,"pwlWYWlTsP5Ln66MXC0BzOjXzAgrptMV","k4taG9al2E7vpGkXmt78PdLTxOSyfcYY","CTiGSc4uwCOrS6ACiuGlP4zE9a1Gnyvq"
            ,"rpS44xwLo5o9gO9XQGzsbpZ2aznnFZru","7LuhcrcpgaPGptTydYZbBToxfGn4YoLd","oOhhuZkZOLIz0Zhr0QcTXrpMQsqkQEks"
            ,"Hktc1XANiqd1XbKxriHkNprbttApTCFK","w8gHmgrAnCEtUGo0rmySHV0HlpRVyvnm","mLTq1aHapgt57U9XQg8C9Dvx6rofr5lk"
            ,"GfqWi3eOiToWYxyPdURYr97zmjFDprjm","lkbPFuS4m3S6d3coRENRvOfCULNOA3Ns","2j1YCg9hGutGM1U5qKN6k8fGi0LDtxH0"
            ,"G7uaDBBTgmeKbOvkfNKZl2wYD4dluhZ5","1KwUSWGD7TmSyLzY6YjhGSL4KKXB6t0I","cwVU6S0gNoz3IFgsxChvVCVPdLdLKLEb"
            ,"GEQha6Cy1G2zQvyCf9oyW8vR6QSAXNyZ","w90nmgd7U5sbzEAglp1PqSGMkhr4mDCd","FpQ8shgz3rTtzOEgixG4Vcea62I6aH0R"
            ,"cv8YzhIptLtyu7gasFADfPGrw5SpBtSv","NEG0G900lPwbB5zxSyHkjs5OXadpDskY","7LuhcrcpgaPGptTydYZbBToxfGn4YoLd"
            ,"oOhhuZkZOLIz0Zhr0QcTXrpMQsqkQEks","Hktc1XANiqd1XbKxriHkNprbttApTCFK","w8gHmgrAnCEtUGo0rmySHV0HlpRVyvnm"
            ,"mLTq1aHapgt57U9XQg8C9Dvx6rofr5lk","GfqWi3eOiToWYxyPdURYr97zmjFDprjm","lkbPFuS4m3S6d3coRENRvOfCULNOA3Ns"
            ,"2j1YCg9hGutGM1U5qKN6k8fGi0LDtxH0","G7uaDBBTgmeKbOvkfNKZl2wYD4dluhZ5","1KwUSWGD7TmSyLzY6YjhGSL4KKXB6t0I"
            ,"cwVU6S0gNoz3IFgsxChvVCVPdLdLKLEb","GEQha6Cy1G2zQvyCf9oyW8vR6QSAXNyZ","w90nmgd7U5sbzEAglp1PqSGMkhr4mDCd"
            ,"FpQ8shgz3rTtzOEgixG4Vcea62I6aH0R","cv8YzhIptLtyu7gasFADfPGrw5SpBtSv","NEG0G900lPwbB5zxSyHkjs5OXadpDskY"
            ,"XYTqvbwnGkPZu5XZRkKrj07ze87otrz6","pDwdyTxTLbD3Z21wD9qtG9my7vPxzRAA","nHcfEt0sx2sac39oYIToailZhX2Q563n"
            ,"8OpDTidGCnGg7tjvOLL3RhZihQuijk2H","lG6jF1UmKI5eavIGlqT5FdjHA8KnpxNi","SaVMsGwnuk70bnAXLwpZp4Um8DKt2Xk8"
            ,"NcjgDGtxawsAlHGlfOqybMVijcGQp9Gj","qCTnlmzWqqb8yLBXygqE9O8jh0iIdDHh","jNLIXy0FydcX1zOMUCOAprkZQLdCBD38"
            ,"G9DDeMETf12uE1hmBdpfRRdGoxNLHXj5","QMa6Dv57Tk3dSDpM976hHGrAryphBxBK","EKg0wF8tGarnHilD51laZmk7GfsftqYi"
            ,"I2DLGyPM9pyLEBZD4Fe0GALPrOz96oGE","mbV2YZnOztmiqKNG8cnccGVvwl6eyrPG","zeEheZjwk0Y4mn7rg2DFXwrcXKxmYznE"
            ,"ZHGgjsPGSadVZGQXcOkz6ym6yGP3ANhB","XotLbAvdwGOkWvZySW5TP1PG8jQCrNTS","OcT3r86XNif5oUA3pfTp1bOTuLGI1TDa"
            ,"Czy5Z1MIFMB31kyo5UVtGvcx4njgMCWg","7izQiEk4P6TobNiYvjwr7NrdU9kGRpnr","ylhhK4sh2tsbjSQHviGfXTGci2a4dCT"
            ,"PwmBnAGZXeHGW7hEa5UGYXoQsU9VxMv9","K73jeS92qTia7fT19cvVPWCRTemOe97P","4Uof3atQiYssH4jmNL6okunGXEoOmCzw"
            ,"oGjlRhp8GhlnOfXF3LgnUno1OPiPnS3W","udQBHS05zbSGZtBguF1s73XKIseT16NH","o2Y1j9CamFC1pPXfHh7M9h9BIz4rxc7c"
            ,"NSZ3ItmlV17sxKyRQ4jgrhGwoPTG7bFx","dmN9t6MeZMI3cW3n9BD8HZwquxSD4bfj","IWTRxz9Gag2hnciqroGkeZPOdCsmLrlt"
            ,"EFC6DiPmOvGHiB7tUZVDYuUh0HiznA5D","ruukdnbqL0zZQUhZLcyOCF8Hni4KclUo","qhP5PYrIVKGRxBh7aA00LqOdSsLbR0Oj","WGSuNYDUXh27F5W6xiShMojMrNiAfXiq "}; 
    public static void main(String[] args) throws Exception, IOException {
        Random random = new Random();
        int index = random.nextInt(akArr.length);
        //这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=" + akArr[index] + "&ip=223.21.141.243");
        System.out.println(json.toString());
        System.out.println(((JSONObject) json.get("content")).get("address"));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDhhmmss");
//        System.out.println(simpleDateFormat.format(new Date()));
    }
    
    
    public static String getAddressStringByIp(String ip) throws Exception
    {
        Random random = new Random();
        int index = random.nextInt(akArr.length);
        JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=" + akArr[index] + "&ip=" + ip);
        return json.toString();
    }
    
    public static Map<String,String> getAddressByIp(String ip) throws Exception
    {
        Random random = new Random();
        int index = random.nextInt(akArr.length);
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=" + akArr[index] + "&ip=" + ip);
            System.out.println(json.toString());
            map.put("province", ((JSONObject) ((JSONObject) json.get("content")).get("address_detail")).get("province").toString());
            map.put("city", ((JSONObject) ((JSONObject) json.get("content")).get("address_detail")).get("city").toString());
            map.put("district", ((JSONObject) ((JSONObject) json.get("content")).get("address_detail")).get("district").toString());
            map.put("baidujson", ((JSONObject) json.get("content")).get("point").toString());
        } catch (Exception ex) {
            return null;
        }
        return map;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            is.close();
            // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");
        }
    }

    // ===========================签名 -开始 暂时不用====================================//
    public static String getSn() throws Exception {
        // 计算sn跟参数对出现顺序有关，所以用LinkedHashMap保存<key,value>，此方法适用于get请求，如果是为发送post请求的url生成签名，请保证参数对按照key的字母顺序依次放入Map。以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
        Map paramsMap = new LinkedHashMap<>();
        paramsMap.put("address", "百度大厦");
        paramsMap.put("output", "json");
        paramsMap.put("ak", "izdsDyOsAXKpxTxLITqe2lohOBsh71pZ");

        // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
        String paramsStr = toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
        String wholeStr = "/geocoder/v2/?" + paramsStr + "pIY41MFyckhetDH1ytk8cPQztxrQzxbn";

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        System.out.println(MD5(tempStr));
        return MD5(tempStr);
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    public static String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey()).append("=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8")).append("&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
        // ===========================签名 -结束 暂时不用====================================//
}
