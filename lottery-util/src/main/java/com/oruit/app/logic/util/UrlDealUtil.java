package com.oruit.app.logic.util;

import com.oruit.app.util.RandomUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Random;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author hanfeng
 */
public class UrlDealUtil {
    // 倒数第一个参数随机的长度：纯字母
    private static final Integer LAST_PARAM_SIZE_RANGE = 5;
    //倒数第二个参数随机的长度:纯数字
    private static final Integer NEXT_TO_LAST_PARAM_SIZE_RANGE = 4;
    private static final String[] URL_LAST_PARAM_ARR = {"deal","fhdeal","ad","ds","ainfo","rainfo","cnzz","ads","ddzcnnz","ruanwencnzz"};
    
    public static String getUrlAppendString()
    {
        StringBuilder urlAppendString = new StringBuilder();
        String lastParam = RandomUtils.getRandomLetters(LAST_PARAM_SIZE_RANGE).toLowerCase();
        for(String item : URL_LAST_PARAM_ARR)
        {
            if(lastParam.indexOf(item) == 0)
            {
                lastParam = "mm" + lastParam; 
            }
        }
        urlAppendString.append("/")
                .append(RandomUtils.getRandomNumbers(NEXT_TO_LAST_PARAM_SIZE_RANGE))
                .append("/")
                .append(lastParam)
                .append(".html");
        return  urlAppendString.toString();
    }
    
    /**
     * 将逻辑处理链接转化为显示链接
     * 注：展示链接处理方案：链接字符串参数只有个特殊标记key规则如下
     * uuid的第5位，作为表示为是否计量 偶数：不计量 奇数：计量
     * eg:3df4d12b-6a34-43c9-8537-6835f3b24ab2
     * @param dealUrl
     * @return 
     */
    public static String dealUrlToShowUrl(String dealUrl)
    {        
        //dealUrl：http://www.ddawd.cn/3df4d12b-6a34-43c9-8537-6835f3b24ab2/466720acd1e742f18de7f7d19a1ec/deal/1559/mitTy.html
        //showUrl：http://www.baidu.com/fe5f3m7f218d83490b9e1a6b640b76b6080Wed=
        // or  ads:  
        //dealUrl：http://www.udrpum.cn/7b0369c4-06f1-41df-90b5-01616b5c3ccc/466720acd1e742f18de7f7d19a1ec/15386/464683/2/ads/1742/umozi.html
        //showUrl：http://www.udrpum.cn/7b0369mc406f141df90b501616b5c3cccghg=/15386/464683/2
        String showUrl = "";
        try
        {
        String[] dealUrlParts = dealUrl.split("/");
        String uuid = dealUrlParts[3].replace("-", "");
        //realflag:偶数 showflag：奇数        
        String realFlag  = "";
        Random random = new Random();
        if (dealUrlParts[4].equalsIgnoreCase(ConstUtil.REAL_URL_FLAG)) {
            //REAL_URL_FLAG
            realFlag = ((random.nextInt(5) * 2) + 1) + "";
        }
        else if (dealUrlParts[4].equalsIgnoreCase(ConstUtil.SHOW_URL_FLAG)) {
            //SHOW_URL_FLAG
            realFlag = random.nextInt(5) * 2 + "";
        }
            if (dealUrl.toLowerCase().contains("/ads/")) {
                realFlag = "w";
            }
        //将标志位插入第5位
        uuid = uuid.substring(0, 4)+ realFlag + uuid.substring(4);
        
        //随机生成3到6位的随机字符串
        String randomString = RandomUtils.getRandomLetters(random.nextInt(3)+3).toLowerCase();
        
            if (realFlag.equals("w")) {                
                showUrl = dealUrlParts[0]+"//"+dealUrlParts[2] + "/" +uuid + randomString 
                        + "/" + dealUrlParts[5] + "/" + dealUrlParts[6] + "/" + dealUrlParts[7];
            }
            else        
            {
                showUrl = dealUrlParts[0]+"//"+dealUrlParts[2] + "/" +uuid + randomString;
            }
        }
        catch(Exception e)
        {
            showUrl = dealUrl;
        }
        return showUrl;
    }
    
    /**
     * 将显示链接转化为逻辑处理链接
     * @param showUrl
     * @return 
     */
    public static String showUrlToDealUrl(String showUrl)
    {
        //showUrl：http://www.baidu.com/fe5f3m7f218d83490b9e1a6b640b76b6080Wed=
        //dealUrl：http://www.ddawd.cn/3df4d12b-6a34-43c9-8537-6835f3b24ab2/466720acd1e742f18de7f7d19a1ec/deal/1559/mitTy.html

        // or  ads:    
        //showUrl：http://www.udrpum.cn/7b0369mc406f141df90b501616b5c3cccghg=/15386/464683/2
        //dealUrl：http://www.udrpum.cn/7b0369c4-06f1-41df-90b5-01616b5c3ccc/466720acd1e742f18de7f7d19a1ec/15386/464683/2/ads/1742/umozi.html

        String dealUrl = "";
        try 
        {
            String[] showlUrlParts = showUrl.split("[/?&]");
            if (showlUrlParts[3].length() <= 32 || showlUrlParts[3].contains("-")) {
                return showUrl;
            }
            String uuid = showlUrlParts[3].substring(0, 4) + showlUrlParts[3].substring(5,33);
            //增加上-
            uuid = uuid.substring(0, 8) 
                    + "-" + uuid.substring(8, 12) 
                    + "-" + uuid.substring(12, 16) 
                    + "-" + uuid.substring(16, 20) 
                    + "-" + uuid.substring(20);            
           //realflag:偶数 showflag：奇数 w:abs
           String strRealFlag ="";
            if (Character.isDigit(showlUrlParts[3].charAt(4))) {                
                int realFlag  = Character.getNumericValue(showlUrlParts[3].charAt(4));
                if (realFlag % 2 != 0) {
                    //REAL_URL_FLAG
                    strRealFlag = ConstUtil.REAL_URL_FLAG;
                }
                else{
                    //SHOW_URL_FLAG
                    strRealFlag = ConstUtil.SHOW_URL_FLAG;
                }     
            } 
            else if(showlUrlParts[3].charAt(4) == 'w')
            {
                //REAL_URL_FLAG
                strRealFlag = ConstUtil.REAL_URL_FLAG;
            }            
              
            if (showlUrlParts[3].charAt(4) == 'w') { 
                dealUrl = showlUrlParts[0] + "//"+showlUrlParts[2] + "/" + uuid + "/"+ strRealFlag 
                        + "/" + showlUrlParts[4] + "/" + showlUrlParts[5] + "/" + showlUrlParts[6]
                        + "/ads/1742/umozi.html";
            }
            else        
            {
                dealUrl = showlUrlParts[0] + "//"+showlUrlParts[2] + "/" + uuid + "/"+ strRealFlag + "/deal/1559/mitTy.html";
            }
        }
        catch(Exception e)
        {
            dealUrl = showUrl;
        }
        return dealUrl;
    }
    
        public static void main(String[] args) throws UnsupportedEncodingException, IOException {
//            System.out.println(showUrlToDealUrl("http://www.ddawd.cn/3df45d12b6a3443c985376835f3b24ab2wAe?a=1&b=2"));
//            System.out.println(dealUrlToShowUrl("http://www.ddawd.cn/3df4d12b-6a34-43c9-8537-6835f3b24ab2/466720acd1e742f18de7f7d19a1ec/deal/1559/mitTy.html"));
// 
//            
//            System.out.println(showUrlToDealUrl("http://www.udrpum.cn/7b03w69c406f141df90b501616b5c3cccghg=/15386/464683/2"));
//            System.out.println(dealUrlToShowUrl("http://www.udrpum.cn/7b0369c4-06f1-41df-90b5-01616b5c3ccc/466720acd1e742f18de7f7d19a1ec/15386/464683/2/ads/1742/umozi.html"));
            BASE64Decoder baseDecoder = new BASE64Decoder();
            byte [] b = baseDecoder.decodeBuffer("MTTvvJoxOeadpeS4gOadoeivhOiuug==");
            System.out.println(new String(b,"UTF-8"));
        }
}
