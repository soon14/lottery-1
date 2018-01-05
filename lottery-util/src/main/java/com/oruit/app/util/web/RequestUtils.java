package com.oruit.app.util.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.logic.util.ConstUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class RequestUtils {

    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path;
        return basePath;
    }

    /**
     * 获取项目的全路径
     *
     * @param request
     * @return
     */
    public static String getPath(HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        return realPath;
    }

    /**
     * 获取temp目录的全路径
     *
     * @param request
     * @return
     */
    public static String getTempPath(HttpServletRequest request) {
        String path = getPath(request) + "temp/";
        return path;
    }

    /**
     * 获取设备信息
     *
     * @param request
     * @return
     */
    public static String getDevCode(HttpServletRequest request) {
        //获取设备信息
        String devCode = null;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            String[] str = userAgent.split("/");
            if (str.length > 8) {
                devCode = str[7];
            }
        }
        return devCode;
    }

    /**
     * 获取request请求的参数内容
     *
     * @param request
     * @param paramName
     * @return
     */
    public static String getRequestParam(HttpServletRequest request, String paramName) throws UnsupportedEncodingException {
        if (!StringUtils.isBlank(request.getParameter(paramName))) {
            return request.getParameter(paramName);
        } else {
            return "";
        }
    }

    /**
     * 获取完整路径
     *
     * @param request
     * @param paramName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getRequestFullUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        // 完整链接路径
        String strURL = request.getRequestURL().toString();
        if (!StringUtils.isBlank(request.getQueryString())) {
            strURL += "?" + request.getQueryString();
        }
        return strURL;
    }

    public static void requestPost(String urlPath, Map<String, String> mapParam) throws MalformedURLException, IOException {
        baserequestPost(urlPath, mapParam);
    }

    public static String baserequestPost(String urlPath, Map<String, String> mapParam) throws MalformedURLException, IOException {
        URL url = new URL(urlPath);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        try {
            con.setConnectTimeout(1000);
            // post请求必须设置下面两项
            con.setDoOutput(true);
            con.setDoInput(true);
            // 不使用缓存
            con.setUseCaches(false);

            // 设置自定义的请求头，也可以用这个方法得到发送数据
            // 这句是打开链接
            OutputStream out = con.getOutputStream();

            // 把数据写到报文
            for (Map.Entry<String, String> entry : mapParam.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                out.write((key + "=" + value).getBytes());

                // &号是数据间隔，多了一个&符号应该没有影响吧，先这样
                out.write("&".getBytes());
            }

            System.out.println("mapParam" + mapParam);

            out.flush();
            out.close();
            // 这句才是真正发送请求
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String response = "";
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                response = response + readLine;
            }
            in.close();
            br.close();
            System.out.println("response:" + response);
            return response;
        } finally {
            con.disconnect();
        }
    }

    /**
     * 获取当前的Ip地址
     *
     * @param request
     * @return
     */
    public static String getCurrentIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ipArr = ip.split(",");
        if (ipArr.length > 0) {
            ip = ipArr[0];
        }
        return ip;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = JSONArray.parseArray(jsonText);
            rd.close();
            return json;
        } finally {
            is.close();
        }
    }

    public static JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            rd.close();
            return json;
        } finally {
            is.close();
        }
    }

    /**
     * 发送post请求、并返回json
     *
     * @param params
     * @return
     */
    public static JSONObject basepostJSON(JSONObject params) throws Exception {

        String response = "";
        try {
            // 创建url资源
            URL url = new URL("http://" + ConstUtil.RED_PACKET_URL + "/app-http/api");
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            //转换为字节数组
            byte[] data = (params.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));

            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json");

            // 开始连接请求
            conn.connect();
            OutputStream out = conn.getOutputStream();
            // 写入请求的字符串
            out.write((params.toString()).getBytes());
            out.flush();
            out.close();

            System.out.println(conn.getResponseCode());

            // 请求返回的状态
            if (conn.getResponseCode() == 200) {
                System.out.println("连接成功");
                // 请求返回的数据
                InputStream in = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    response = response + readLine;
                }
                in.close();
                br.close();
                System.out.println("response:" + response);
            } else {
                System.out.println("no++");
            }
        } catch (Exception e) {
            throw e;
        }
        return JSONObject.parseObject(response);
    }

    public static void main(String[] args) throws IOException {
//        Map<String, String> params = new HashMap<>();
//        params.put("userid", "1000000013");
//        params.put("ip", "127.0.1.3");
//        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
//        String s = format1.format(new Date());
//        System.out.println("dddddddd" + s);
//        requestPost("http://9918116.cn/api/TestIp/10001", params);
//        String s1 = format1.format(new Date());
//        System.out.println("mmmmmmmm" + s1);

    }
}
