package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/7.
 */


import com.oruit.app.client.XMLConverUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author
 * @create 2017-09-07 10:50
 */
public class TestXML {
    public static void main(String[] args) {

        List<Scheme> list = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setBetCode("040510R01");
        scheme.setBetMoney("101");
        list.add(scheme);
        list.add(scheme);


        Head head = new Head();
        head.setAgentId("210001");
        head.setDigest("8123f913e123e123990028d9c72e013c");
        head.setMessageId("21000120081201000001");
        long time = new Date().getTime();
        String s1 = String.valueOf(time);
        head.setTimestamp(s1);

        Body body = new Body();
        body.setSchemes(list);

        LotteryOrder lotteryOrder = new LotteryOrder();
        lotteryOrder.setBody(body);
        lotteryOrder.setHead(head);
        String s = XMLConverUtil.convertToXMLGBK(lotteryOrder);
        System.out.println(s);


    }
}
