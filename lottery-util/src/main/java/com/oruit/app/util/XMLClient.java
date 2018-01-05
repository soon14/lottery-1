package com.oruit.app.util;

import com.oruit.app.ssq.Issue;
import com.oruit.app.ssq.LotteryOrder;
import com.oruit.app.ssq.Scheme;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import com.oruit.app.client.XMLConverUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.oruit.app.ssq.WinInterface.*;

/**
 * @author wyt
 * @create 2017-09-20 17:18
 */
public class XMLClient {

   private static final String  URLCONN = "http://120.77.2.56:8088/interface/";
    //private static final String  URLCONN = "";
    /**
     * 发送xml请求到server端
     * @param url       xml请求数据地址
     * @param xmlString 发送的xml数据流
     * @return null发送失败，否则返回响应内容
     */
    public static LotteryOrder sendPost(String url, String xmlString) {
        //创建httpclient工具对象
        HttpClient client = new HttpClient();
        //创建post请求方法
        PostMethod myPost = new PostMethod(url);
        //设置请求超时时间
        client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        LotteryOrder lotteryOrder = new LotteryOrder();
        try {
            // 设置连接超时
            client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
            // 设置字符集
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            // 使用系统提供的默认的恢复策略 该策略在碰到IO异常的时候将自动重试3次。
            myPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 20000);
            // 异常时，重试处理器
            myPost.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            NameValuePair[] nameValuePairs = new NameValuePair[1];
            nameValuePairs[0] = new NameValuePair("xml", xmlString);
            myPost.setRequestBody(nameValuePairs);
            int statusCode = client.executeMethod(myPost);
            //只有请求成功200了，才做处理
            if (statusCode == HttpStatus.SC_OK) {
                String rspXml = new String(myPost.getResponseBody(), "UTF-8");
                lotteryOrder = XMLConverUtil.convertToObject(LotteryOrder.class, rspXml);
                //System.out.println(lotteryOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myPost.releaseConnection();
        }
        return lotteryOrder;
    }

    public static void main(String[] args) throws Exception {
      /*  List<Scheme> schemes = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setSchemeId("3d"+PayUtil.getTradeNo());
        scheme.setGame("D3");
        scheme.setGameIssue("2017292");
        scheme.setBetType("0");
        scheme.setBetMulti("1");
        scheme.setBetMoney("200");
        scheme.setBetCode("56701^");
        schemes.add(scheme);
        Map<String, Object> map = oltpPrintTicketProxyServlet(schemes);
        System.out.println(map);*/
       /* List<Scheme> schemes = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setSchemeId("ssq"+PayUtil.getTradeNo());
        scheme.setGame("SSQ");
        scheme.setGameIssue("2017292");
        scheme.setBetType("0");
        scheme.setBetMulti("1");
        scheme.setBetMoney("200");
        scheme.setBetCode("56701^");
        schemes.add(scheme);
        Map<String, Object> map = oltpPrintTicketProxyServlet(schemes);
        System.out.println(map);*/

     /*  List<Scheme> schemes = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setSchemeId("ssq20171205230844125271197144");
        scheme.setGame("SSQ");
        scheme.setGameIssue("2017144");
        scheme.setBetType("1");
        scheme.setBetMulti("1");
        scheme.setBetMoney("200");
        scheme.setBetCode("0103051418230201^");
        schemes.add(scheme);
        Map<String, Object> map = oltpPrintTicketProxyServlet(schemes);
        System.out.println(map);*/

      /* Map<String, Object> stringObjectMap = oltpQueryOpenResultServlet("10", "20171128076");
        System.out.println(stringObjectMap);*/
        List<Scheme> schemes = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setSchemeId("ssq20171218222621660597134149");
        schemes.add(scheme);
        List<Scheme> schemes1 = oltpPrintTicketQueryServlet(schemes);
        System.out.println(schemes1);

        /*List<Scheme> schemes = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setSchemeId("SSQtest201709210908123456789101111");
        scheme.setGame("10");
        scheme.setGameIssue("20170925071");
        scheme.setBetType("0");
        scheme.setBetMulti("1");
        scheme.setBetMoney("200");
        scheme.setBetCode("0102R1");
        schemes.add(scheme);
        String betting = betting(schemes);
        System.out.println(betting);*/
        /*Map<String, Object> map = oltpSaveBonusDetailServlet("ssq20171113140956257399008134", "10");
        System.out.println(map);
        String s = "2017-11-09 09:00:02";*/
    }

    /**
     *查期数信息接口
     * @param game 彩种编号
     * @param gameIssue 期号
     * @return
     */
    public static Map<String,Object> oltpQueryTermInfoServlet(String game ,String gameIssue) {
        String string = QueryWinResult(game, gameIssue);
        String pathUrl = URLCONN +"oltpQueryTermInfoServlet";
        LotteryOrder lotteryOrder = sendPost(pathUrl, string);
        String responseCode = lotteryOrder.getBody().getResponseCode();
        System.out.println("----------:"+lotteryOrder);
        Issue issue = lotteryOrder.getBody().getIssue();
        Map<String,Object> maps = new HashMap<>();
        if ("0".equals(responseCode)) {
            maps.put("gameIssue",issue.getGameIssue());
            maps.put("game",issue.getGame());
            maps.put("startTime",issue.getStartTime());
            maps.put("endTime",issue.getEndTime());
            return maps;
        }else{
            return null;
        }
        

    }
    /**
     *查开奖结果
     * @param game 彩种编号
     * @param gameIssue 期号
     * @return
     */
    public static Map<String,Object> oltpQueryOpenResultServlet(String game ,String gameIssue) {
        String string = QueryWinResult(game, gameIssue);
        String pathUrl = URLCONN +"oltpQueryOpenResultServlet";
        LotteryOrder lotteryOrder = sendPost(pathUrl, string);
        System.out.println("--------:"+lotteryOrder);
        String responseCode = lotteryOrder.getBody().getResponseCode();
        Issue issue = lotteryOrder.getBody().getIssue();
        Map<String,Object> maps = new HashMap<>();
        if ("0".equals(responseCode)) {
            maps.put("gameIssue",issue.getGameIssue());
            maps.put("game",issue.getGame());
            maps.put("drawCode",issue.getDrawCode());
        }
        return maps;
    }
    /**
     *查票接口
     * @param schemes 彩种编号
     * @param
     * @return
     */
    public static List<Scheme> oltpPrintTicketQueryServlet(List<Scheme> schemes) {
        String string = QueryLottery(schemes);
        String pathUrl = URLCONN +"oltpPrintTicketQueryServlet";
        LotteryOrder lotteryOrder = sendPost(pathUrl, string);
        String responseCode = lotteryOrder.getBody().getResponseCode();
        List<Scheme> lists = lotteryOrder.getBody().getSchemes();
        if ("0".equals(responseCode)) {
            return lists;
        }
        return null;
    }
    /**
     *投注接口
     * @param schemes 彩种编号
     * @param
     * @return
     */
    public static  Map<String,Object> oltpPrintTicketProxyServlet(List<Scheme> schemes) {
        Map<String,Object> maps = new HashMap<>();
        String string = betting(schemes);
        String pathUrl = URLCONN +"oltpPrintTicketProxyServlet";
        //LotteryOrder lotteryOrder = sendPost(pathUrl, string);
        //String responseCode = lotteryOrder.getBody().getResponseCode();
        maps.put("content",string);
        maps.put("responseCode","0");
        return maps;
    }

    /**
     * 保存中奖明细
     * @param schemeid
     * @param prize
     * @return
     */
    public static Map<String,Object> oltpSaveBonusDetailServlet(String schemeid,String prize) {
        Map<String,Object> maps = new HashMap<>();
        String string =saveWinResult(schemeid,prize);
        String pathUrl = URLCONN +"oltpSaveBonusDetailServlet";
     /* LotteryOrder lotteryOrder = sendPost(pathUrl, string);
        System.out.println("****************"+lotteryOrder);
        String responseCode = lotteryOrder.getBody().getResponseCode();
        String totalPrize = lotteryOrder.getBody().getTotalPrize();
        if ("0".equals(responseCode)) {
            maps.put("responseCode",responseCode);
            maps.put("totalPrize",totalPrize);
        }*/
       maps.put("totalPrize","0");
        maps.put("responseCode","0");
        return maps;
    }

}
