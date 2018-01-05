package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/12.
 */

import com.oruit.app.client.XMLConverUtil;
import com.oruit.app.oruitkey.OruitMD5;
import com.oruit.app.util.MD5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.oruit.app.util.map.BaiDuMapUtils.MD5;

/**
 * @author @wyt
 * @create 2017-09-12 17:18
 */
public class WinInterface {

    /**
     * 查票的接口
     * @param schemes 查询的票编号
     * @return
     */
    public  static String QueryLottery(List<Scheme> schemes){

        Body body = new Body();
        body.setSchemes(schemes);

        String s = getString(body);
        return  s ;
    }

    /**
     *查询开奖结果  查询期数信息
     * @param game 彩种编号
     * @param gameIssue 期号
     * @return
     */
    public  static String QueryWinResult(String game,String gameIssue){

       Issue issue = new Issue();
       issue.setGame(game);
       issue.setGameIssue(gameIssue);

        Body body = new Body();
        body.setIssue(issue);

        String s = getString(body);
        return  s ;
    }

    /**
     *保存中奖明细
     * @param schemeid  票编号
     * @param prize 奖金
     * @return
     */
    public  static String saveWinResult(String schemeid,String prize){

        Scheme scheme = new Scheme();
        scheme.setSchemeId(schemeid);
        scheme.setPrize(prize);

        Body body = new Body();
        body.setScheme(scheme);

        String s = getString(body);
        return  s ;
    }

    /**
     * 投注接口
     * @param lists
     * @return
     */
    public  static String betting(List<Scheme> lists){

        Body body = new Body();
        body.setSchemes(lists);

        String s = getString(body);
        return  s ;
    }

    private static String getString(Body body) {
        String s1 = XMLConverUtil.convertToXMLGBK(body);
        String substring = s1.substring(s1.indexOf("<body>") + 6, s1.indexOf("</body>"));
        String yMdHms = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String yMd = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        String agentId = "100001";//小程序出票系统为商户分配的唯一编号。
        String signKey = "#changsha2017";
        //消息编号不可重复。具体格式为六位商户编号即agentId+八位年月日YYYYMMDD+流水号。流水号可以是六位的随机数字。
        String messageId = agentId + yMd + random;
        //时间戳，格式：4位年+2位月+2位日+2位时+2位分+2位秒，即yyyyMMddHHmmss。
        String timestamp = yMdHms ;
        String digest = messageId + timestamp + signKey + substring ;
        digest = MD5.encode(digest);
        Head head = new Head();
        head.setAgentId(agentId);
        head.setDigest(digest);
        head.setMessageId(messageId);
        head.setTimestamp(timestamp);
        LotteryOrder lotteryOrder = new LotteryOrder();
        lotteryOrder.setBody(body);
        lotteryOrder.setHead(head);
        System.out.println("---xml:"+XMLConverUtil.convertToXMLGBK(lotteryOrder));
        return XMLConverUtil.convertToXMLGBK(lotteryOrder);
    }

    public static void main(String[] args) {

        String ssq = QueryWinResult("10", "2017105");
        System.out.println(ssq);

    }
}
