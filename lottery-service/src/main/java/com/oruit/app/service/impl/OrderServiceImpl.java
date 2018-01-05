package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/28.
 */

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.oruit.app.alipay.config.AlipayConfig;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.*;
import com.oruit.app.message.MessagePushUtil;
import com.oruit.app.service.OrderService;
import com.oruit.app.ssq.Issueno;
import com.oruit.app.ssq.Scheme;
import com.oruit.app.ssq.WinInterface;
import com.oruit.app.ssq.shuagnseqiu;
import com.oruit.app.util.XMLClient;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import com.oruit.app.util.web.UUIDUtils;
import com.oruit.weixin.api.PayMchAPI;
import com.oruit.weixin.paymch.bean.Unifiedorder;
import com.oruit.weixin.paymch.bean.UnifiedorderResult;
import com.oruit.weixin.util.PayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.oruit.app.util.XMLClient.oltpPrintTicketProxyServlet;

/**
 * @author wyt
 * @create 2017-08-28 13:57
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private CaipiaoOrderInfoMapper caipiaoOrderInfoMapper;
    @Autowired
    private KuaileshifenOrderMapper kuaileshifenOrderMapper;
    @Autowired
    private UserAuthorizationMapper userAuthorizationMapper;
    @Autowired
    private AuthorizationMapper authorizationMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserForbiddenMapper userForbiddenMapper;
    @Autowired
    private UserRedpacketMapper userRedpacketMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private UserStatisticsLogMapper userStatisticsLogMapper;
    @Autowired
    private ShuangseqiuOrderMapper shuangseqiuOrderMapper;
    @Autowired
    private UserPlayPlanMapper userPlayPlanMapper;
    @Autowired
    private UserPlayRecordMapper userPlayRecordMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private UserShareMapper userShareMapper;
    @Autowired
    private RedacketMapper redacketMapper;
    @Autowired
    private UserAccountStatementMapper userAccountStatementMapper;
    @Autowired
    private UserPlayLogMapper userPlayLogMapper;
    //微信公众平台appid
    @Value("#{configProperties['appid']}")
    public String appid;
    //微信公众普通token
    @Value("#{configProperties['token']}")
    public String token;
    //微信商户号
    @Value("#{configProperties['mch_id']}")
    public String mch_id;
    //微信支付授权密钥
    @Value("#{configProperties['PartnerKey']}")
    public String PartnerKey;
    //微信服务器ip
    @Value("#{configProperties['spbill_create_ip']}")
    public String spbill_create_ip;
    //微信回掉地址js
    @Value("#{configProperties['NOTIFY_URL']}")
    public String NOTIFY_URL;
    //微信回掉地址app
    @Value("#{configProperties['NOTIFY_URLAPP']}")
    public String NOTIFY_URLAPP;
    //订单有效期（天）
    @Value("#{configProperties['youxiaoqi']}")
    public String youxiaoqi;
    @Value("#{configProperties['ALIPAY_NOTIFY_URL']}")
    public String ALIPAY_NOTIFY_URL;

    /**
     * 充值下单
     *
     * @param map
     * @return
     */
    @Override
    public ResultBean Order(Map<String, Object> map) {
        ResultBean resultBean = null;
        String userId = map.get("userid").toString();
        if (userId == null || "".equals(userId)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String productId = map.get("productid").toString();
        if (productId == null || "".equals(productId)) {
            return new ResultBean("2000", "0|商品id为空！");
        }
        String type = map.get("type").toString();
        if (type == null || "".equals(type)) {
            return new ResultBean("2000", "0|充值类型为空！");
        }
        String paycode = map.get("paycode").toString();
        if (paycode == null || "".equals(paycode)) {
            return new ResultBean("2000", "0|支付方式为空！");
        }
        if ("alipay".equals(paycode)) {
            paycode = "2";
        }
        if ("wechatpay".equals(paycode)) {
            paycode = "1";
        }
        String moneys = map.get("money").toString();
        Map<String, Object> map1 = userInfoMapper.QueryUserInfoMy(userId);
        System.out.println("----------------用户不存在----------");
        if (map1 == null || "".equals(map1) || map1.isEmpty()) {
            System.out.println("----------------用户不存在用户不存在用户不存在用户不存在----------");
            return new ResultBean("2000", "0|用户不存在！");
        }
        ConfigInfo configInfos = configInfoMapper.selectByConnfigCode(Constants.CHONGTI_CONFIG);
        String configValues = configInfos.getConfigValue();
        JSONObject jsonObjects = JSONObject.parseObject(configValues);
        String tixian = jsonObjects.get("chongzhi").toString();
        int productCatalogId = Integer.parseInt(tixian);
        Map<String, Object> maps = new HashMap<>(2);
        maps.put("productCatalogId", productCatalogId);
        maps.put("productId", Integer.parseInt(productId));

        String price = "";
        if (Integer.parseInt(productId) != 0) {
            Map<String, Object> stringObjectMap = productInfoMapper.queryPrice(maps);
            if (stringObjectMap == null) {
                return new ResultBean("2000", "0|商品不存在！");
            } else {
                price = stringObjectMap.get("price").toString();
            }
        } else {
            price = moneys;
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setPayType(Short.parseShort(paycode));
        orderInfo.setUserId(userId);
        orderInfo.setTotal(new BigDecimal(price));
        orderInfo.setAmount(new BigDecimal(price));
        orderInfo.setState(Short.parseShort("1"));
        orderInfo.setCreateTime(new Date());
        orderInfo.setDiscount(new BigDecimal("0"));
        int i = orderInfoMapper.insertSelective(orderInfo);
        Integer orderId = orderInfo.getOrderId();
        if (i > 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCreateTime(new Date());
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(Integer.parseInt(productId));
            orderDetail.setPrice(new BigDecimal(price));
            orderDetail.setQuantity(1);
            int i1 = orderDetailMapper.insertSelective(orderDetail);
            String json = "";
            if (i1 > 0) {
                String UnionId = userAuthorizationMapper.queryUnionId(userId);
                String openId = authorizationMapper.queryOpenid(UnionId);
                //支付
                Map<String, Object> map2 = new HashMap<>(10);
                System.out.println("+++++++++++++++++++++++++++++++++:" + type + "--------------------");
                if ("APP".equals(type)) {
                    if ("1".equals(paycode)) {//weixin
                        map2 = getStringObjectMap(productId, Float.valueOf(price), "充值", map, NOTIFY_URLAPP, orderId, openId, "APP");
                        map2.put("orderStr", " ");
                    } else if ("2".equals(paycode)) {//支付宝
                        map2 = GetAlipayAPP(String.valueOf(orderId), price, map2);
                        map2.put("appId", "");
                        map2.put("secret", "");
                        map2.put("nonceStr", "");
                        map2.put("Package", "");
                        map2.put("paySign", "");
                        map2.put("signType", "");
                        map2.put("timeStamp", "");
                    } else {
                        return new ResultBean("2000", "0|请选择支付方式！");
                    }
                    resultBean = new ResultBean("1000", "1|下单成功！", map2, "1");
                }

            } else {
                resultBean = new ResultBean("2000", "1|下单失败！", "", "1");
            }

        } else {
            resultBean = new ResultBean("2000", "1|下单失败！", "", "1");
        }
        return resultBean;
    }

    /**
     * 购买书籍app
     *
     * @param price
     * @param map2
     * @return
     */
    private Map<String, Object> GetAlipayAPP(String orderid, String price, Map<String, Object> map2) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", "utf-8", AlipayConfig.ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setPassbackParams(URLEncoder.encode("用户充值"));  //描述信息  添加附加数据
        model.setSubject("充值"); //商品标题
        model.setOutTradeNo(orderid); //商家订单编号
        model.setTimeoutExpress("30m"); //超时关闭该订单时间
        model.setTotalAmount(price);  //订单总金额
        model.setProductCode("QUICK_MSECURITY_PAY"); //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        request.setBizModel(model);
        request.setNotifyUrl(ALIPAY_NOTIFY_URL);  //回调地址
        String orderStr = "";
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderStr = response.getBody();
            map2.put("orderStr", orderStr);
            System.out.println(orderStr);//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return map2;
    }

    /**
     * 微信
     *
     * @param into      充值 购买商品
     * @param map
     * @param Notifyurl 回调地址
     * @param orderId   订单id
     * @param openId    openid
     * @param type      JSAPI，NATIVE，APP，WAP
     * @return
     */
    private Map<String, Object> getStringObjectMap(String productId, Float amount, String into, Map<String, Object> map, String Notifyurl, Integer orderId, String openId, String type) {
        String json;
        Float money = Float.valueOf((float) (amount * 100));
        //Float money = Float.valueOf(price) * 100;
        String total_free = String.valueOf(money.intValue());
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(appid);
        unifiedorder.setMch_id(mch_id);
        unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        unifiedorder.setBody(into + money + "元");
        unifiedorder.setDevice_info("");
        if (!"WVEB".equals(type)) {
            unifiedorder.setOpenid(openId);
        }
        unifiedorder.setOut_trade_no(String.valueOf(orderId));
        unifiedorder.setAttach(String.valueOf(orderId));
        unifiedorder.setProduct_id(productId);
        unifiedorder.setTotal_fee(total_free);//单位分

        unifiedorder.setSpbill_create_ip(map.get("remoteAddr").toString());//IP
        unifiedorder.setNotify_url(Notifyurl); //回调地址
        unifiedorder.setTrade_type(type);//JSAPI，NATIVE，APP，WAP
        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, PartnerKey);
        json = PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(), appid, PartnerKey);
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, Object> map2 = new HashMap<>(6);
        String appId = jsonObject.get("appId").toString();
        String nonceStr = jsonObject.get("nonceStr").toString();
        String aPackage = jsonObject.get("package").toString();
        String paySign = jsonObject.get("paySign").toString();
        String signType = jsonObject.get("signType").toString();
        String timeStamp = jsonObject.get("timeStamp").toString();
        map2.put("appId", appId);
        map2.put("nonceStr", nonceStr);
        map2.put("Package", aPackage);
        map2.put("paySign", paySign);
        map2.put("signType", signType);
        map2.put("timeStamp", timeStamp);
        map2.put("secret", PartnerKey);
        System.out.println("----------------jsonObject--------:" + jsonObject);
        return map2;
    }

    /**
     * 微信   在非微信浏览器充值
     *
     * @param productId
     * @param amount
     * @param into
     * @param map
     * @param Notifyurl
     * @param orderId
     * @param openId
     * @param type
     * @return
     */
    private Map<String, Object> getStringObjectMapMEVB(String productId, Float amount, String into, Map<String, Object> map, String Notifyurl, String orderId, String openId, String type) {
        String json;
        Float money = Float.valueOf((float) (amount * 100));
        System.out.println(money + "------------------------------------");
        //Float money = Float.valueOf(price) * 100;
        String total_free = String.valueOf(money.intValue());
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(appid);
        unifiedorder.setMch_id(mch_id);
        unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        unifiedorder.setBody(into + money + "元");
        unifiedorder.setDevice_info("");
        if (!"WVEB".equals(type)) {
            unifiedorder.setOpenid(openId);
        }
        unifiedorder.setOut_trade_no(orderId);
        unifiedorder.setAttach(orderId);
        unifiedorder.setProduct_id(productId);
        unifiedorder.setTotal_fee(total_free);//单位分
        unifiedorder.setSpbill_create_ip(map.get("remoteAddr").toString());//IP
        unifiedorder.setNotify_url(Notifyurl); //回调地址
        unifiedorder.setTrade_type(type);//JSAPI，NATIVE，APP，WAP
        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, PartnerKey);
        json = PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getMweb_url(), unifiedorderResult.getPrepay_id(), appid, PartnerKey);
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, Object> map2 = new HashMap<>(7);
        String appId = jsonObject.get("appId").toString();
        String nonceStr = jsonObject.get("nonceStr").toString();
        String aPackage = jsonObject.get("package").toString();
        String paySign = jsonObject.get("paySign").toString();
        String signType = jsonObject.get("signType").toString();
        String timeStamp = jsonObject.get("timeStamp").toString();
        String mweb_url = jsonObject.get("mweb_url").toString();
        map2.put("appId", appId);
        map2.put("nonceStr", nonceStr);
        map2.put("Package", aPackage);
        map2.put("paySign", paySign);
        map2.put("signType", signType);
        map2.put("timeStamp", timeStamp);
        map2.put("mweb_url", mweb_url);
        System.out.println("----------------jsonObject--------:" + jsonObject);
        return map2;
    }

    /**
     * 微信端H5购买快乐十分下单
     *
     * @param request
     * @return
     * @throws ParseException
     */
    @Override
    public ResultBean ClientOrder(HttpServletRequest request) throws ParseException {
        ResultBean resultBean = null;
        String userid = request.getParameter("userid");
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String number = request.getParameter("number");
        if (number == null || "".equals(number)) {
            return new ResultBean("2000", "1|请选择号码！");
        }
        String s = caipiaoOrderInfoMapper.queryUseridOrder(userid);
        if (s != null && !"".equals(s)) {
            return new ResultBean("2000", "1|已经购买过彩票！");
        }
        String[] split = number.split(" ");
        int length = split.length;
        if (length < 2) {
            return new ResultBean("2000", "1|请选择足够的号码！");
        }
        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
        caipiaoOrderInfo.setCaipiaoOrderId(UUIDUtils.uuid());
        caipiaoOrderInfo.setCaipiaoId(114);
        caipiaoOrderInfo.setCaipiaoOrderCode(com.oruit.app.util.PayUtil.getTradeNo());
        caipiaoOrderInfo.setUserId(userid);
        caipiaoOrderInfo.setState(1);
        caipiaoOrderInfo.setPayType(1);
        caipiaoOrderInfo.setUsedRedpacket(1);
        caipiaoOrderInfo.setRedpacketId(1);
        caipiaoOrderInfo.setRedpacketAmount(new BigDecimal(1));
        caipiaoOrderInfo.setCreateTime(new Date());
        caipiaoOrderInfo.setUpdateTime(ymdhms.parse("1990-01-01"));
        caipiaoOrderInfo.setPayTime(ymdhms.parse("1990-01-01"));
        caipiaoOrderInfo.setTotal(new BigDecimal("2"));
        caipiaoOrderInfo.setAmount(new BigDecimal("1"));
        int i = caipiaoOrderInfoMapper.insertSelective(caipiaoOrderInfo);
        String caipiaoOrderId = caipiaoOrderInfo.getCaipiaoOrderId();
        if (i <= 0) {
            return new ResultBean("2000", "0|下单失败！！！");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String yyyyMMdd = format.format(new Date());
        String integer1 = shuagnseqiu.isKLSFissueno();
        if ("false".equals(integer1)) {
            integer1 = shuagnseqiu.KLSFissuenoquery();
        }
        System.out.println("-----------------yyyyMMdd------------------:" + yyyyMMdd);
        if (Integer.parseInt(integer1) > 84 && !"1000".equals(integer1)) {
            Calendar nowTime = Calendar.getInstance();
            nowTime.setTime(format.parse(yyyyMMdd));
            nowTime.add(Calendar.DATE, 1);
            Date time = nowTime.getTime();
            yyyyMMdd = format.format(time);
            integer1 = "01";
        }
        if ("1000".equals(integer1)) {
            integer1 = "01";
        }
        System.out.println("-----------------integer1------------------:" + integer1);
        System.out.println("-----------------yyyyMMdd------------------:" + yyyyMMdd);
        String issuno = yyyyMMdd + integer1;
        KuaileshifenOrder kuaileshifenOrder = new KuaileshifenOrder();
        kuaileshifenOrder.setAmount(new BigDecimal("1"));
        kuaileshifenOrder.setBeishu(1);
        kuaileshifenOrder.setCaipiaoOrderId(caipiaoOrderId);
        kuaileshifenOrder.setCaipiaoOrderSubCode(com.oruit.app.util.PayUtil.getTradeNo() + 1);
        kuaileshifenOrder.setCreateTime(new Date());
        kuaileshifenOrder.setZhushu(1);
        kuaileshifenOrder.setBeishu(1);
        kuaileshifenOrder.setPlayType(1);
        kuaileshifenOrder.setNumber(number);
        kuaileshifenOrder.setIssueNo(issuno);
        kuaileshifenOrder.setQishu(1);
        kuaileshifenOrder.setCaipiaoOrderSubId(UUIDUtils.uuid());
        if (length == 2) {
            kuaileshifenOrder.setPlayMethod("r2");
        }
        if (length == 3) {
            kuaileshifenOrder.setPlayMethod("r3");
        }
        if (length == 4) {
            kuaileshifenOrder.setPlayMethod("r4");
        }
        if (length == 5) {
            kuaileshifenOrder.setPlayMethod("r5");
        }
        int i1 = kuaileshifenOrderMapper.insertSelective(kuaileshifenOrder);
        String caipiaoOrderId1 = kuaileshifenOrder.getCaipiaoOrderId();
        String resultBean1 = "";
        if (i1 > 0) {
            String remoteAddr = request.getRemoteAddr();
            resultBean1 = wxOrder(2, remoteAddr, caipiaoOrderInfo, caipiaoOrderId1, userid);
            resultBean = new ResultBean("1000", "0|成功！", resultBean1, "1");
        } else {
            resultBean = new ResultBean("2000", "1|下单失败！！！");
        }
        return resultBean;
    }

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param state
     * @return
     */

    @Override
    public Integer updateOrder(String orderId, Integer state) {
        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        caipiaoOrderInfo.setCaipiaoOrderId(orderId);
        caipiaoOrderInfo.setPayTime(new Date());
        caipiaoOrderInfo.setUpdateTime(new Date());
        caipiaoOrderInfo.setState(state);
        int i = caipiaoOrderInfoMapper.updateByPrimaryKeySelective(caipiaoOrderInfo);
        return i;
    }

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param state
     * @return
     */
    @Override
    public Integer updateOrderCheck(String orderId, Integer state) {
        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        caipiaoOrderInfo.setCaipiaoOrderId(orderId);
        caipiaoOrderInfo.setPayTime(new Date());
        caipiaoOrderInfo.setUpdateTime(new Date());
        caipiaoOrderInfo.setState(state);
        int i = caipiaoOrderInfoMapper.updateByPrimaryKeySelective(caipiaoOrderInfo);

        CaipiaoOrderInfo caipiaoOrderInfo1 = caipiaoOrderInfoMapper.selectByPrimaryKey(orderId);
        String userId = caipiaoOrderInfo1.getUserId();

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        String queryordernum = caipiaoOrderInfoMapper.Queryordernum(userId);
        int i1 = Integer.parseInt(queryordernum);
        Integer parentId = userInfo.getParentId();
        if (parentId != 0 && i1 == 1) {
            UserShare userShare = userShareMapper.selectByPrimaryKey(parentId);
            String userId1 = userShare.getUserId();
            if (userId1 != null && !"".equals(userId1)) {
                ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.XINREN_CONFIG);
                String configValue = configInfo.getConfigValue();
                System.out.println("--------configValue" + configValue);
                JSONObject jsonObject = JSONObject.parseObject(configValue);
                String xinrenhongbao = jsonObject.get("xinrenhongbao").toString();

                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                Redacket redacket = redacketMapper.selectByPrimaryKey(Integer.parseInt(xinrenhongbao));
                Integer validDays = redacket.getValidDays();
                c.add(Calendar.DATE, validDays);
                UserRedpacket userRedpacket = new UserRedpacket();
                userRedpacket.setUserId(userId1);
                userRedpacket.setState(0);
                userRedpacket.setCreateTime(new Date());
                userRedpacket.setRedpacketId(Integer.parseInt(xinrenhongbao));
                userRedpacket.setBeginTime(new Date());
                userRedpacket.setOverTime(c.getTime());
                userRedpacketMapper.insertSelective(userRedpacket);
            }
        }
        //根据订单号查询到用户的id
        Map<String, Object> map = caipiaoOrderInfoMapper.queryUseridbyOrder(orderId);
        String userid = map.get("userid").toString();
        String issueno = map.get("issueno").toString();
        String caipiaoordersubid = map.get("caipiaoordersubid").toString();
        String number = map.get("number").toString();
        String caipiaoorderid = map.get("caipiaoorderid").toString();
        //插入账户明细
        userAccount("goucai", userid, new BigDecimal("1"), 0);
        //更新用户信息
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUserId(userid);
        userStatistics.setBettingNum(1);
        userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);
        //插入追号表和投注记录表
        UserPlayPlan userPlayPlan = new UserPlayPlan();
        userPlayPlan.setUserId(userid);
        userPlayPlan.setStatus(2);
        userPlayPlan.setType(0);
        userPlayPlan.setCaipiaoId(11);
        userPlayPlan.setPlayType(1);
        userPlayPlan.setDanma("");
        userPlayPlan.setTuoma("");
        userPlayPlan.setHongqiu("");
        userPlayPlan.setLanqiu("");
        userPlayPlan.setPlayMethod(" ");
        userPlayPlan.setNumber(" ");
        userPlayPlan.setQishu(1);
        userPlayPlan.setBeishu(1);
        userPlayPlan.setZhushu(1);
        userPlayPlan.setCreateTime(new Date());
        userPlayPlan.setCaipiaoOrderSubId(caipiaoordersubid);
        userPlayPlan.setAmount(new BigDecimal(1));
        System.out.println("---------userPlayPlan-------------:" + userPlayPlan);
        int i2 = userPlayPlanMapper.insertSelective(userPlayPlan);
        Integer planId = userPlayPlan.getPlanId();
        Integer playid = saveUserPlayRecord(0, 114, " ", userid, userPlayPlan, planId, issueno, new Date(), caipiaoordersubid);
        String replace = number.replace(" ", "") + "R01^";
        String s = issueno.substring(0, 8) + "0" + issueno.substring(8, 10);
        String caipiaoOrderSubCode = "klsf" + com.oruit.app.util.PayUtil.getTradeNo();
        List<Scheme> schemes = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setSchemeId(caipiaoOrderSubCode);
        scheme.setGame("10");
        scheme.setGameIssue(s);
        scheme.setBetType("0");
        scheme.setBetMulti("1");
        scheme.setBetMoney("200");
        scheme.setBetCode(replace);
        schemes.add(scheme);
        Map<String, Object> maps = XMLClient.oltpPrintTicketProxyServlet(schemes);
        UserPlayRecord userPlayRecord = new UserPlayRecord();
        if ("-1".equals(maps)) {
            //c出票失败的操作
            //getfail(userid, playid);
            userPlayRecord.setStatus(1);
        } else if ("0".equals(maps)) {
            userPlayRecord.setStatus(2);
        } else {
            userPlayRecord.setStatus(5);
        }
        //更新caipiaoOrderSubCode
        userPlayRecord.setPlayId(playid);
        userPlayRecord.setCaipiaoOrderSubCode(caipiaoOrderSubCode);
        int ii = userPlayRecordMapper.updateByPrimaryKeySelective(userPlayRecord);
        return i;
    }

    /**
     * 微信充值成功
     *
     * @param openId
     * @param totalAmount
     * @return
     */
    @Override
    public Integer updateMoney(String openId, BigDecimal totalAmount) {

        String queryunionid = authorizationMapper.queryunionid(openId);
        String userId = userAuthorizationMapper.queryUserid(queryunionid);
        UserStatistics userStatistics1 = userStatisticsMapper.selectByPrimaryKey(userId);
        BigDecimal totalAmount1 = userStatistics1.getTotalAmount();
        BigDecimal add = totalAmount1.add(totalAmount);
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUserId(userId);
        userStatistics.setTotalAmount(add);
        int i = userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);

        userAccount("chongzhi", userId, totalAmount, 0);

        return i;
    }

    /**
     * 支付宝充值成功
     *
     * @param userid
     * @param totalAmount
     * @return
     */
    @Override
    public Integer updateMoneyAlipay(String userid, BigDecimal totalAmount) {
        UserStatistics userStatistics1 = userStatisticsMapper.selectByPrimaryKey(userid);
        BigDecimal totalAmount1 = userStatistics1.getTotalAmount();
        BigDecimal add = totalAmount1.add(totalAmount);
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUserId(userid);
        userStatistics.setTotalAmount(add);
        BigDecimal balanceAmount = userStatistics1.getBalanceAmount();
        BigDecimal add1 = balanceAmount.add(totalAmount);
        userStatistics.setBalanceAmount(add1);
        userStatistics.setUpdateTime(new Date());
        int i = userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);

        UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
        userStatisticsLog.setTotalAmount(userStatistics.getTotalAmount());
        userStatisticsLog.setWinningTotalAmount(userStatistics.getWinningTotalAmount());
        userStatisticsLog.setCreateTime(new Date());
        userStatisticsLog.setBettingNum(userStatistics.getBettingNum());
        userStatisticsLog.setTudiNum(userStatistics.getTudiNum());
        userStatisticsLog.setTusunNum(userStatistics.getTusunNum());
        userStatisticsLog.setBalanceAmount(userStatistics.getBalanceAmount());
        userStatisticsLog.setUserId(userid);
        userStatisticsLog.setWinningBalabceAmount(userStatistics.getWinningBalabceAmount());
        userStatisticsLog.setWinningNum(userStatistics.getWinningNum());
        userStatisticsLogMapper.insertSelective(userStatisticsLog);

        userAccount("chongzhi", userid, totalAmount, 0);
        return i;
    }

    /**
     * 浏览器充值下单
     *
     * @param request
     * @return
     */
    @Override
    public ResultBean CommitOrderH5(HttpServletRequest request) {
        System.out.println("---------------------------CommitOrderH5-----------------" + request.getRemoteAddr());
        ResultBean resultBean = null;
        String userId = request.getParameter("userid");
        String productId = request.getParameter("productid");
        String paycode = request.getParameter("paycode");
        String moneys = request.getParameter("money");
        Map<String, Object> map = new HashMap<>(1);
        map.put("remoteAddr", request.getRemoteAddr());

        if ("alipay".equals(paycode)) {
            paycode = "2";
        }
        if ("wechatpay".equals(paycode)) {
            paycode = "1";
        }
        Map<String, Object> map1 = userInfoMapper.QueryUserInfoMy(userId);
        if (map1 == null) {
            return new ResultBean("2000", "1|用户不存在！");
        }
        ConfigInfo configInfos = configInfoMapper.selectByConnfigCode(Constants.CHONGTI_CONFIG);
        String configValues = configInfos.getConfigValue();
        JSONObject jsonObjects = JSONObject.parseObject(configValues);
        String tixian = jsonObjects.get("chongzhi").toString();
        int productCatalogId = Integer.parseInt(tixian);
        Map<String, Object> maps = new HashMap<>();
        maps.put("productCatalogId", productCatalogId);
        maps.put("productId", Integer.parseInt(productId));

        String price = "";
        if (Integer.parseInt(productId) != 0) {
            Map<String, Object> stringObjectMap = productInfoMapper.queryPrice(maps);
            if (stringObjectMap == null) {
                return new ResultBean("2000", "0|商品不存在！");
            } else {
                price = stringObjectMap.get("price").toString();
            }
        } else {
            price = moneys;
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setPayType(Short.parseShort(paycode));
        orderInfo.setUserId(userId);
        orderInfo.setTotal(new BigDecimal(price));
        orderInfo.setAmount(new BigDecimal(price));
        orderInfo.setState(Short.valueOf("1"));
        orderInfo.setCreateTime(new Date());
        orderInfo.setDiscount(new BigDecimal("0"));
        int i = orderInfoMapper.insertSelective(orderInfo);
        Integer orderId = orderInfo.getOrderId();
        if (i > 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCreateTime(new Date());
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(Integer.parseInt(productId));
            orderDetail.setPrice(new BigDecimal(price));
            orderDetail.setQuantity(1);
            int i1 = orderDetailMapper.insertSelective(orderDetail);
            String json = "";
            if (i1 > 0) {
                String UnionId = userAuthorizationMapper.queryUnionId(userId);
                String openId = authorizationMapper.queryOpenid(UnionId);
                //支付
                Map<String, Object> map2 = new HashMap<>();
                if ("1".equals(paycode)) {//weixin
                    map2 = getStringObjectMapMEVB(productId, Float.valueOf(price), "充值", map, NOTIFY_URLAPP, String.valueOf(orderId), openId, "MWEB");
                    map2.put("orderStr", " ");
                } else if ("2".equals(paycode)) {//支付宝
                    // 商户订单号，商户网站订单系统中唯一订单号，必填
                    String out_trade_no = String.valueOf(orderId);
                    // 订单名称，必填
                    String subject = "充值";
                    System.out.println(subject);
                    // 付款金额，必填
                    String total_amount = price;
                    // 商品描述，可空
                    String body = "";
                    // 超时时间 可空
                    String timeout_express = "2m";
                    String s = GetAlipayH5(ALIPAY_NOTIFY_URL, out_trade_no, subject, total_amount, body, timeout_express);
                    map2.put("appId", " ");
                    map2.put("nonceStr", " ");
                    map2.put("Package", " ");
                    map2.put("paySign", " ");
                    map2.put("signType", " ");
                    map2.put("timeStamp", " ");
                    map2.put("orderStr", s);

                } else {
                    return new ResultBean("2000", "0|请选择支付方式！");
                }
                resultBean = new ResultBean("1000", "1|下单成功！", map2, "1");
            } else {
                resultBean = new ResultBean("2000", "1|下单失败！", "", "1");
            }

        } else {
            resultBean = new ResultBean("2000", "1|下单失败！", "", "1");
        }
        return resultBean;

    }


    /**
     * 充值失败
     *
     * @param openId
     * @param totalAmount
     * @return
     */
    @Override
    public Integer updateFailMoney(String openId, BigDecimal totalAmount) {
        String queryunionid = authorizationMapper.queryunionid(openId);
        String userId = userAuthorizationMapper.queryUserid(queryunionid);
        UserStatistics userStatistics1 = userStatisticsMapper.selectByPrimaryKey(userId);
        BigDecimal totalAmount1 = userStatistics1.getTotalAmount();
        BigDecimal add = totalAmount1.subtract(totalAmount);
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUserId(userId);
        userStatistics.setTotalAmount(add);
        int i = userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);
        return i;
    }

    /**
     * APP提交订单
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public ResultBean ConfirmOrder(Map<String, Object> map) throws Exception {
        ResultBean resultBean = null;
        String orderid = map.get("orderid").toString();
        if (orderid == null || "".equals(orderid)) {
            return new ResultBean("2000", "0|订单id为空！");
        }
        String redpackageid = map.get("redpackageid").toString();
        if (redpackageid == null || "".equals(redpackageid)) {
            return new ResultBean("2000", "0|红包id为空！");
        }
        Integer redpackageid1 = Integer.parseInt(redpackageid);
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String s1 = userForbiddenMapper.QueryUserForbidden(userid);
        if (s1 != null && !"".equals(s1)) {
            return new ResultBean("2000", "1|非法操作禁止购彩！");
        }
        Map<String, Object> map1 = userInfoMapper.QueryUserInfo(userid);
        if (map1 == null) {
            return new ResultBean("2000", "0|用户不存在！");
        }
        CaipiaoOrderInfo caipiaoOrderInfo = caipiaoOrderInfoMapper.selectByPrimaryKey(orderid);
        if (caipiaoOrderInfo == null || "".equals(caipiaoOrderInfo)) {
            return new ResultBean("2000", "1|订单不存在！");
        }
        if (caipiaoOrderInfo.getState() != 1) {
            return new ResultBean("2000", "1|支付异常！");
        }
        Integer caipiaoId = caipiaoOrderInfo.getCaipiaoId();//取出彩种id
        if (caipiaoId == null || "".equals(caipiaoId)) {
            return new ResultBean("2000", "0|订单错误！");
        }
        if (caipiaoId == 11) {
            List<Map<String, Object>> maps = shuangseqiuOrderMapper.querySSQOrder(orderid);
            for (Map<String, Object> item : maps) {
                String issueNo = item.get("issueNo").toString();
                String s = Issueno.achieveNum();
                if (!s.equals(issueNo)) {//当前期号和数据库里面的期号作对比
                    ShuangseqiuOrder shuangseqiuOrder = new ShuangseqiuOrder();
                    shuangseqiuOrder.setCaipiaoOrderId(orderid);
                    shuangseqiuOrder.setIssueNo(s);
                    shuangseqiuOrderMapper.updateByPrimaryKeySelectiveIssuenossq(shuangseqiuOrder);
                    Map<String, Object> items = new HashMap<>();
                    String resultmessage = "第" + issueNo + "已经停止,是#否购买当前第" + s + "期";
                    items.put("result", resultmessage);
                    return new ResultBean("2004", "0|成功", items, "1");//返回当前期号
                }
            }
        }
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (caipiaoId == 114) {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
            String yyyyMMdd = format.format(new Date());
            String klsFissueno = shuagnseqiu.isKLSFissueno();

            if ("false".equals(klsFissueno)) {
                klsFissueno = shuagnseqiu.KLSFissuenoquery();
            }
            String s = yyyyMMdd + klsFissueno;
            Calendar nowTimes = Calendar.getInstance();
            if (Integer.parseInt(klsFissueno) > 84 && !"1000".equals(klsFissueno)) {
                nowTimes.setTime(new Date());
                nowTimes.add(Calendar.DATE, 1);
                Date time = nowTimes.getTime();
                s = format.format(time) + "01";
            }
            System.out.println("--------------sssssaaaaaaaaaaas---------:" + s);
            if ("1000".equals(klsFissueno)) {
                long time = new Date().getTime();//当前时间
                SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
                String format5 = format4.format(new Date());
                String s1s = format5 + " 08:59:00";
                String s2 = format5 + " 00:00:00";
                Date parse = format2.parse(s1s);
                Date parse1 = format2.parse(s2);
                if (time - parse1.getTime() > 0 && parse.getTime() - time > 0) {
                    s = getString(format, yyyyMMdd);
                }
            }
            System.out.println("--------------ssssss---------:" + s);
            List<Map<String, Object>> maps = kuaileshifenOrderMapper.querKLSFOrder(orderid);
            for (Map<String, Object> item : maps) {
                String issueNo = item.get("issueNo").toString();
                String substring = issueNo.substring(6, 8);
                String substring1 = yyyyMMdd.substring(6, 8);
                boolean b = Integer.parseInt(substring1) - Integer.parseInt(substring) == 0;
                if (!s.equals(issueNo) && b && !"1000".equals(s)) {//当前期号和数据库里面的期号作对比
                    KuaileshifenOrder kuaileshifenOrder = new KuaileshifenOrder();
                    kuaileshifenOrder.setCaipiaoOrderId(orderid);
                    kuaileshifenOrder.setIssueNo(s);
                    kuaileshifenOrderMapper.updateByPrimaryKeySelectiveIssueno(kuaileshifenOrder);
                    Map<String, Object> items = new HashMap<>();
                    String resultmessage = "第" + issueNo + "已经停止,是#否购买当前第" + s + "期";
                    items.put("result", resultmessage);
                    return new ResultBean("2004", "0|成功", items, "1");//返回当前期号
                }
            }
        }
        String discount = "0";
        if (Integer.parseInt(redpackageid) != 0) {
            map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            String s = userRedpacketMapper.QueryChoiceRedPackage(map);
            if (s == null || "".equals(s)) {
                discount = "0";
            } else {
                discount = s;
                //更新红包信息
                UserRedpacket userRedpacket = new UserRedpacket();
                userRedpacket.setState(1);
                userRedpacket.setId(Integer.parseInt(redpackageid));
                userRedpacketMapper.updateByPrimaryKeySelective(userRedpacket);
            }
        }
        BigDecimal total = caipiaoOrderInfo.getTotal();//总金额
        BigDecimal bigDecimal = new BigDecimal(discount);//红包金额
        BigDecimal account = new BigDecimal(0);
        if (total.compareTo(bigDecimal) >= 0) {
            account = total.subtract(bigDecimal);//实际付款金额 = 总金额 - 红包金额
        } else {
            account = new BigDecimal(0);
        }
        System.out.println("------------------account-----------------:" + account);
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.MAXMONEY_CONFIG);
        String configValue = configInfo.getConfigValue();
        System.out.println("-----------------account.compareTo(new BigDecimal(configValue))--------------:" + account.compareTo(new BigDecimal(configValue)));
        if (account.compareTo(new BigDecimal(configValue)) == 1) {

            return new ResultBean("2000", "1|为了您的财产安全，请选择较小的金额！");

        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        if (userStatistics == null || "".equals(userStatistics)) {

            return new ResultBean("2000", "1|账户不存在！");
        }
        BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();//中奖余额
        BigDecimal balanceAmount = userStatistics.getBalanceAmount();//余额
        BigDecimal add = winningBalabceAmount.add(balanceAmount);//中奖余额 + 余额
        /*System.out.println("--------account--------------:" + account);
        System.out.println("--------winningBalabceAmount--------------:" + winningBalabceAmount);
        System.out.println("--------balanceAmount--------------:" + balanceAmount);
        System.out.println("--------add--------------:" + add);
        System.out.println("--------account.compareTo(winningBalabceAmount)--------------:" + account.compareTo(winningBalabceAmount));
        System.out.println("--------account.compareTo(add)--------------:" + account.compareTo(add));*/
        //实际付款金额 > 中奖金额；实际付款金额 > 中奖金额 + 余额
        if (account.compareTo(winningBalabceAmount) == 1 && account.compareTo(add) == 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultBean("2000", "1|账户余额不足请及时充值！");
        }
        //(实际付款金额 > 中奖余额)   并且   (实际付款金额 = 中奖余额 + 余额 或者 实际付款金额 > 中奖余额 + 余额)
        if ((account.compareTo(winningBalabceAmount) == 1 || account.compareTo(winningBalabceAmount) == 0) && (account.compareTo(add) == 0 || account.compareTo(add) == -1)) {
            winningBalabceAmount = new BigDecimal(0);//中间金额减为 0
            balanceAmount = add.subtract(account);
            //System.out.println("---------------进入第一个----------------");
        }
        //实际付款金额 小于 中奖余额
        if (account.compareTo(winningBalabceAmount) == -1) {
            winningBalabceAmount = winningBalabceAmount.subtract(account);
            // System.out.println("---------------进入第二个----------------");
        }
        Integer bettingNum = userStatistics.getBettingNum();//取出投注次数
        List<Map<String, Object>> mapssize = new ArrayList<>();
        if (caipiaoId == 114) {
            mapssize = kuaileshifenOrderMapper.querKLSFOrder(orderid);
        }
        if (caipiaoId == 11) {
            mapssize = shuangseqiuOrderMapper.querySSQOrder(orderid);
        }
        //积分系统
        System.out.println("------------>>>>>>>>>>>>>>开始");
        pointSystem(userid, account);
        System.out.println("------------>>>>>>>>>>>>>>end");

        //更新用户的账户信息
        userStatistics.setWinningBalabceAmount(winningBalabceAmount);
        userStatistics.setBalanceAmount(balanceAmount);
        userStatistics.setUserId(userid);
        userStatistics.setUpdateTime(new Date());
        userStatistics.setBettingNum(bettingNum + mapssize.size());
        int i1 = userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);
        insertuserlog(userid, userStatistics, winningBalabceAmount, balanceAmount);
        if (i1 < 0) {
            return new ResultBean("2000", "1|购买失败！");
        }
        //追号记录
        UserPlayPlan userPlayPlan = new UserPlayPlan();
        userPlayPlan.setUserId(userid);
        userPlayPlan.setStatus(1);
        String beishu1 = "";
        String qishu1 = "";
        Integer pId = 0;
        Integer printed = 0;
        Date createTime = null;
        String issueNo = "";
        String caipiaoOrderSubId = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        String Sbetcodedan = "";
        String Sbetcodefu = "";
        Integer playid = 0;
        String danshiamount = "0";
        String fushiamount = "0";
        String playiddanshi = "";
        List<Map<String, Object>> maps = new ArrayList<>();
        if (caipiaoId == 11) {
            maps = shuangseqiuOrderMapper.querySSQOrder(orderid);
            for (Map<String, Object> item : maps) {
                issueNo = item.get("issueNo").toString();
                String s = Issueno.achieveNum();
                String s2 = Issueno.achieveNumssq();
                beishu1 = item.get("beishu").toString();
                Integer beishu = Integer.parseInt(beishu1);
                String hongqiu = item.get("hongqiu").toString();
                String lanqiu = item.get("lanqiu").toString();
                if (hongqiu == null || lanqiu == null) {
                    return new ResultBean("2000", "0|数据异常！");
                }
                userPlayPlan.setCaipiaoId(11);
                userPlayPlan.setPlayType(Integer.parseInt(item.get("playType").toString()));
                userPlayPlan.setDanma(item.get("danma").toString());
                userPlayPlan.setTuoma(item.get("tuoma").toString());
                userPlayPlan.setHongqiu(hongqiu);
                userPlayPlan.setLanqiu(lanqiu);
                userPlayPlan.setPlayMethod(" ");
                userPlayPlan.setNumber(" ");
                qishu1 = item.get("qishu").toString();
                Integer qishu = Integer.parseInt(qishu1);
                if (qishu == 1) {
                    userPlayPlan.setStatus(2);
                }
                if ("1000".equals(s2)) {//1 为预售
                    userPlayPlan.setType(1);
                    userPlayPlan.setStatus(1);
                } else {//立即投注
                    userPlayPlan.setType(0);
                }
                Integer zhushu = Integer.parseInt(item.get("zhushu").toString());
                userPlayPlan.setQishu(qishu);
                userPlayPlan.setBeishu(beishu);
                userPlayPlan.setZhushu(zhushu);
                userPlayPlan.setCreateTime(new Date());
                userPlayPlan.setCaipiaoOrderSubId(item.get("caipiaoOrderSubId").toString());
                int amount = qishu * beishu * zhushu * 2;
                userPlayPlan.setAmount(new BigDecimal(amount));
                System.out.println("---------userPlayPlan-------------:" + userPlayPlan);
                int i2 = userPlayPlanMapper.insertSelective(userPlayPlan);
                pId = userPlayPlan.getPlanId();
                userPlayPlan.setPlanId(null);
                createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.get("createTime").toString());
                caipiaoOrderSubId = item.get("caipiaoOrderSubId").toString();
                //保存投注记录双色球
                playid = saveUserPlayRecord(printed, 11, " ", userid, userPlayPlan, pId, issueNo, createTime, caipiaoOrderSubId);
                if (!"1000".equals(s2)) {
                    String replace = hongqiu.replace(" ", "");
                    String replace1 = lanqiu.replace(" ", "");
                    if ("1".equals(item.get("playType").toString())) {
                        String format1 = String.format("%02d", Integer.parseInt(beishu1));
                        Sbetcodedan = Sbetcodedan + replace + replace1 + format1 + "^";
                        String amounts = item.get("amount").toString();
                        danshiamount = new BigDecimal(amounts).add(new BigDecimal(danshiamount)).toString();
                        playiddanshi = playiddanshi + playid + "^";
                    }
                    if ("2".equals(item.get("playType").toString())) {
                        Sbetcodefu = replace + "|" + replace1 + "^";
                        fushiamount = item.get("amount").toString();
                        int fudivide = new BigDecimal(fushiamount).divide(new BigDecimal(qishu1)).multiply(new BigDecimal(100)).intValue();
                        getstring(redpackageid1, userid, "", "2", "SSQ", issueNo, sdf, beishu1, Sbetcodefu, fudivide, playid);
                    }
                }
            }
            if (Sbetcodedan != null && !"".equals(Sbetcodedan)) {
                //调取第三方接口
                String type = "SSQ";
                touzhu(redpackageid1, userid, playiddanshi, "1", danshiamount, issueNo, sdf, Sbetcodedan, beishu1, qishu1, type, playid);
            }
        }
        if (caipiaoId == 114) {
            maps = kuaileshifenOrderMapper.querKLSFOrder(orderid);
            String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String klsFissueno = shuagnseqiu.isKLSFissueno();
            if ("false".equals(klsFissueno)) {
                klsFissueno = shuagnseqiu.KLSFissueno();
            }
            String s = yyyyMMdd + klsFissueno;//当期期号
            if ("false".equals(klsFissueno)) {
                s = yyyyMMdd + shuagnseqiu.KLSFissueno();
            }
            String s2 = s.substring(0, 8) + "0" + s.substring(8, 10);
            Map<String, Object> stringObjectMap = XMLClient.oltpQueryTermInfoServlet("10", s2);
            for (Map<String, Object> item : maps) {
                issueNo = item.get("issueNo").toString();

                beishu1 = item.get("beishu").toString();
                userPlayPlan.setCaipiaoId(caipiaoId);
                userPlayPlan.setPlayType(Integer.parseInt(item.get("playType").toString()));
                userPlayPlan.setDanma("");
                userPlayPlan.setTuoma("");
                userPlayPlan.setHongqiu("");
                userPlayPlan.setLanqiu("");
                userPlayPlan.setPlayMethod(item.get("playMethod").toString());
                userPlayPlan.setNumber(item.get("number").toString());
                qishu1 = item.get("qishu").toString();
                Integer qishu = Integer.parseInt(qishu1);
                if (qishu == 1) {
                    userPlayPlan.setStatus(2);
                }
                if (!"1000".equals(klsFissueno) && Integer.parseInt(issueNo.substring(4, 10)) > Integer.parseInt(s.substring(4, 10))) {//数据里面的期数大于当前的期数  为预售
                    userPlayPlan.setType(1);
                    userPlayPlan.setStatus(1);
                } else if ("1000".equals(klsFissueno)) {
                    userPlayPlan.setType(1);
                    userPlayPlan.setStatus(1);
                } else if (stringObjectMap == null) {
                    userPlayPlan.setType(1);
                    userPlayPlan.setStatus(1);
                } else {//立即投注
                    userPlayPlan.setType(0);
                }
                Integer beishu = Integer.parseInt(beishu1);
                Integer zhushu = Integer.parseInt(item.get("zhushu").toString());
                userPlayPlan.setQishu(qishu);
                userPlayPlan.setBeishu(beishu);
                userPlayPlan.setZhushu(zhushu);
                userPlayPlan.setCreateTime(new Date());
                int amount = qishu * beishu * zhushu * 2;
                userPlayPlan.setAmount(new BigDecimal(amount));
                userPlayPlan.setCaipiaoOrderSubId(item.get("caipiaoOrderSubId").toString());
                userPlayPlanMapper.insertSelective(userPlayPlan);
                pId = userPlayPlan.getPlanId();
                userPlayPlan.setPlanId(null);
                createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.get("createTime").toString());
                caipiaoOrderSubId = item.get("caipiaoOrderSubId").toString();
                //保存投注记录快乐十分
                playid = saveUserPlayRecord(printed, 114, " ", userid, userPlayPlan, pId, issueNo, createTime, caipiaoOrderSubId);
                System.out.println("-------------------playid---------------------:" + playid);
                if (s.equals(issueNo) && stringObjectMap != null) {//当前期号和数据库里面的期号作对比相等  掉去投注接口
                    String number = item.get("number").toString();
                    String replace = number.replace(" ", "");
                    if ("1".equals(item.get("playType").toString())) {//单式
                        String format1 = String.format("%02d", Integer.parseInt(beishu1));
                        if ("r5".equals(item.get("playMethod").toString())) {
                            Sbetcodedan = Sbetcodedan + replace + format1 + "^";//投注的内容
                        } else {
                            Sbetcodedan = Sbetcodedan + replace + "R" + format1 + "^";//投注的内容
                        }
                        String amounts = item.get("amount").toString();
                        danshiamount = new BigDecimal(amounts).add(new BigDecimal(danshiamount)).toString();
                        playiddanshi = playiddanshi + playid + "^";//投注记录的id
                    }
                    if ("2".equals(item.get("playType").toString())) {//快乐十分复式
                        replace = replace + "^";
                        String amounts = item.get("amount").toString();
                        String playMethod = item.get("playMethod").toString();
                        if ("r2".equals(playMethod)) {
                            playMethod = "11";
                        }
                        if ("r3".equals(playMethod)) {
                            playMethod = "12";
                        }
                        if ("r4".equals(playMethod)) {
                            playMethod = "13";
                        }
                        if ("r5".equals(playMethod)) {
                            playMethod = "14";
                        }
                        int fudivide = new BigDecimal(amounts).divide(new BigDecimal(qishu1)).multiply(new BigDecimal(100)).intValue();
                        getstring(redpackageid1, userid, "", playMethod, "10", issueNo, sdf, beishu1, replace, fudivide, playid);
                    }
                }
            }
            if (s.equals(issueNo)) {//当前期号和数据库里面的期号作对比相等  掉去投注接口
                if (Sbetcodedan != null && !"".equals(Sbetcodedan)) {
                    //调取第三方接口
                    String type = "10";
                    touzhu(redpackageid1, userid, playiddanshi, "0", danshiamount, issueNo, sdf, Sbetcodedan, beishu1, qishu1, type, playid);
                }
            }
        }
        userAccount("goucai", userid, account, 0);
        //更新订单的状态
        caipiaoOrderInfo.setPayTime(new Date());
        caipiaoOrderInfo.setUpdateTime(new Date());
        caipiaoOrderInfo.setState(2);
        caipiaoOrderInfo.setAmount(account);
        if (Integer.parseInt(redpackageid) != 0) {
            caipiaoOrderInfo.setRedpacketAmount(bigDecimal);
            caipiaoOrderInfo.setRedpacketId(Integer.parseInt(redpackageid));
            caipiaoOrderInfo.setUsedRedpacket(1);
        } else {
            caipiaoOrderInfo.setUsedRedpacket(0);
            caipiaoOrderInfo.setRedpacketAmount(new BigDecimal(0));
            caipiaoOrderInfo.setRedpacketId(0);
        }
        caipiaoOrderInfo.setCaipiaoOrderId(orderid);
        caipiaoOrderInfoMapper.updateByPrimaryKeySelective(caipiaoOrderInfo);
        //插入日志
        //insertuserlog(userid, userStatistics, winningBalabceAmount, balanceAmount);
        return new ResultBean("1000", "0|购买成功！", "", "1");
    }

    //积分系统
    private void pointSystem(String userid, BigDecimal account) {

        ConfigInfo configinfo = configInfoMapper.selectByConnfigCode(Constants.JIFENJINANGLI_CONFIG);
        String shifu = configinfo.getConfigValue();//师傅的比例
        String shizhu = configinfo.getValue1();//师祖的比例
        int jifen = account.intValue();
        //判断用户是否是第一次购买
        String queryordernum = caipiaoOrderInfoMapper.Queryordernum(userid);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        Integer parentId = userInfo.getParentId();
        Integer firstPoint = 1000;//第一次否买师傅的积分
        Integer teacherPoint = jifen * Integer.parseInt(shifu) * 10;
        if (Integer.parseInt(queryordernum) <= 0) {
            //第一次购买
            //查询是否有师傅
            purchasePoint(parentId, firstPoint, jifen * 10 * Integer.parseInt(shizhu));
        } else {
            //第二次购买
            purchasePoint(parentId, teacherPoint, jifen * 10 * Integer.parseInt(shizhu));
        }
    }

    /**
     * 购买送积分
     *
     * @param parentId
     * @param grandfather
     * @param teacherPoint
     */
    private void purchasePoint(Integer parentId, Integer teacherPoint, Integer grandfather) {
        UserInfo userInfo;
        if (parentId != 0) {
            UserShare userShare = userShareMapper.selectByPrimaryKey(parentId);
            if (userShare != null && !"".equals(userShare)) {
                String userId = setPoint(teacherPoint, userShare);
                //查询师傅是否有师傅
                userInfo = userInfoMapper.selectByPrimaryKey(userId);
                Integer parentId1 = userInfo.getParentId();
                if (parentId1 != 0) {
                    userShare = userShareMapper.selectByPrimaryKey(parentId1);
                    if (userShare != null && !"".equals(userShare)) {
                        setPoint(grandfather, userShare);
                    }
                }
            }
        }
    }

    /**
     * 师徒奖励送积分
     *
     * @param firstPoint
     * @param userShare
     * @return
     */
    private String setPoint(Integer firstPoint, UserShare userShare) {
        String userId = userShare.getUserId();//师傅的id
        UserStatistics userStatistics2 = userStatisticsMapper.selectByPrimaryKey(userId);
        Integer balancePoints = userStatistics2.getBalancePoints();
        Integer totalPoints = userStatistics2.getTotalPoints();
        balancePoints = balancePoints + firstPoint;
        totalPoints = totalPoints + firstPoint;
        UserStatistics userStatistics1 = new UserStatistics();
        userStatistics1.setUserId(userId);
        userStatistics1.setBalancePoints(balancePoints);
        userStatistics1.setTotalPoints(totalPoints);
        userStatistics1.setUpdateTime(new Date());
        userStatisticsMapper.updateByPrimaryKeySelective(userStatistics1);
        //插入积分log
        insertpointlog(userId);
        //插入账户明细

        return userId;
    }

    /**
     * 插入积分日志
     *
     * @param userId
     */
    private void insertpointlog(String userId) {
        UserStatistics userStatistics2;
        userStatistics2 = userStatisticsMapper.selectByPrimaryKey(userId);
        UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
        userStatisticsLog.setBalanceAmount(userStatistics2.getWinningTotalAmount());
        userStatisticsLog.setBettingNum(userStatistics2.getBettingNum());
        userStatisticsLog.setWinningNum(userStatistics2.getWinningNum());
        userStatisticsLog.setUserId(userId);
        userStatisticsLog.setBalanceAmount(userStatistics2.getBalanceAmount());
        userStatisticsLog.setWinningBalabceAmount(userStatistics2.getWinningBalabceAmount());
        userStatisticsLog.setWinningTotalAmount(userStatistics2.getTotalAmount());
        userStatisticsLog.setCreateTime(new Date());
        userStatisticsLog.setTudiNum(userStatistics2.getTudiNum());
        userStatisticsLog.setTusunNum(userStatistics2.getTusunNum());
        userStatisticsLog.setTotalPoints(userStatistics2.getTotalPoints());
        userStatisticsLog.setBalancePoints(userStatistics2.getBalancePoints());
        userStatisticsLogMapper.insertSelective(userStatisticsLog);
    }

    private void insertuserlog(String userid, UserStatistics userStatistics, BigDecimal winningBalabceAmount, BigDecimal balanceAmount) {
        UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
        userStatisticsLog.setBalanceAmount(userStatistics.getWinningTotalAmount());
        userStatisticsLog.setBettingNum(userStatistics.getBettingNum());
        userStatisticsLog.setWinningNum(userStatistics.getWinningNum());
        userStatisticsLog.setUserId(userid);
        userStatisticsLog.setBalanceAmount(userStatistics.getBalanceAmount());
        userStatisticsLog.setWinningBalabceAmount(userStatistics.getWinningBalabceAmount());
        userStatisticsLog.setWinningTotalAmount(userStatistics.getTotalAmount());
        userStatisticsLog.setCreateTime(new Date());
        userStatisticsLog.setTudiNum(userStatistics.getTudiNum());
        userStatisticsLog.setTusunNum(userStatistics.getTusunNum());
        userStatisticsLogMapper.insertSelective(userStatisticsLog);
    }

    private String getString(SimpleDateFormat format11, String format1) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = format1.substring(0, 4) + "-" + format1.substring(4, 6) + "-" + format1.substring(6, 8);
        String s = s1 + " 23:00:00";
        System.out.println("---------l:" + s);
        Date parse = format.parse(s);
        long l = parse.getTime() - new Date().getTime();
        System.out.println("---------l:" + l);
        String issueno = "";
        if (l < 0) {
            Calendar nowTimes = Calendar.getInstance();
            nowTimes.setTime(format11.parse(format1));
            nowTimes.add(Calendar.DATE, 1);
            Date time = nowTimes.getTime();
            issueno = format11.format(time) + "01";
        } else {
            issueno = format11.format(format11.parse(format1)) + "01";
        }
        System.out.println(issueno);
        System.out.println("--------------" + format1);

        return issueno;
    }

    /**
     * 账户明细
     *
     * @param type
     * @param userid
     * @param account
     */
    private void userAccount(String type, String userid, BigDecimal account, Integer playId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = format.format(new Date());
        UserAccountStatementAccount userAccountStatement = new UserAccountStatementAccount();
        userAccountStatement.setAmount(account);
        userAccountStatement.setCreateTime(format1);
        userAccountStatement.setState(1);
        userAccountStatement.setType(type);
        userAccountStatement.setUpdateTime(format1);
        userAccountStatement.setUserId(userid);
        if (playId != 0) {
            userAccountStatement.setRemark(String.valueOf(playId));
        }
        System.out.println("//////////////////////////////////:" + playId);
        String date = new SimpleDateFormat("yyyyMM").format(new Date());
        String tableName = "user_account_statement_" + date;
        Integer integer = userAccountStatementMapper.existTable(tableName);
        if (integer > 0) {
            userAccountStatement.setTableName(tableName);
            userAccountStatementMapper.insertSelectiveAccount(userAccountStatement);
        }
    }

    /**
     * 调取第三方的接口出票
     *
     * @param danshiamount 总金额
     * @param issueNo      期号
     * @param sdf          日期格式化
     * @param sbetcodedan  单式 内容
     * @param beishu1      倍数
     * @param qishu1       期数
     * @param type         彩票类型
     * @param userid       用户ID
     * @param playiddanshi 投注记录id
     * @param bettype      类型
     * @param danshiamount 金额
     * @param issueNo      期号
     * @param playid
     */
    private void touzhu(Integer redpackageid, String userid, String playiddanshi, String bettype, String danshiamount, String issueNo, SimpleDateFormat sdf, String sbetcodedan, String beishu1, String qishu1, String type, Integer playid) {
        int divide = new BigDecimal(danshiamount).divide(new BigDecimal(qishu1)).multiply(new BigDecimal(100)).intValue();
        if (sbetcodedan != null || !"".equals(sbetcodedan)) {
            String Sbetcodedans = sbetcodedan.substring(0, sbetcodedan.length() - 1);
            String[] split = Sbetcodedans.split("\\^");
            getstringssq(redpackageid, userid, playiddanshi, bettype, issueNo, sdf, beishu1, divide, type, Sbetcodedans, split, playid);
        }
    }

    /**
     * 循环调用第三方的接口
     *
     * @param issueNo
     * @param sdf
     * @param beishu1
     * @param divide
     * @param type
     * @param sbetcodefus
     * @param split
     * @param playid
     */
    private void getstringssq(Integer redpackageid, String userid, String playiddanshi, String bettype, String issueNo, SimpleDateFormat sdf, String beishu1, Integer divide, String type, String sbetcodefus, String[] split, Integer playid) {
        if (split.length <= 5 && split.length > 0) {
            getstring(redpackageid, userid, playiddanshi, bettype, type, issueNo, sdf, beishu1, sbetcodefus + "^", divide, playid);
        } else {
            String str = "";
            String danshi = "";
            int j = 0;
            playiddanshi = playiddanshi.substring(0, playiddanshi.length() - 1);
            for (int jj = 0; jj < split.length; jj++) {
                if (j < 5) {//
                    str = str + split[jj] + "^";
                    j++;
                    if (j == 5 && jj < split.length) {
                        //str = str.substring(0, str.length() - 1);//投注内容
                        String[] split1 = playiddanshi.split("\\^");//追号id
                        for (int z = jj - 4; z < jj + 1; z++) {
                            danshi = danshi + split1[z] + "^";
                        }

                        getstring(redpackageid, userid, danshi, bettype, type, issueNo, sdf, beishu1, str, (divide / split.length) * j, playid);
                        str = "";
                        danshi = "";
                    }
                    if (j < 5 && jj == split.length - 1) {
                        //str = str.substring(0, str.length() - 1);
                        int i = (split.length) % 5;
                        String[] split1 = playiddanshi.split("\\^");
                        for (int k = 0; k < i; k++) {
                            danshi = danshi + split1[jj - k] + "^";
                        }

                        getstring(redpackageid, userid, danshi, bettype, type, issueNo, sdf, beishu1, str, (divide / split.length) * i, playid);
                        str = "";
                        danshi = "";
                    }
                } else {
                    j = 0;
                    jj--;
                }
            }
        }
    }

    /**
     * @param userid       用户id
     * @param playiddanshi 投注记录id
     * @param bettype      投注类型
     * @param type         类型 ssq:双色球  10:快乐十分
     * @param issueNo      期号
     * @param sdf          日期格式化
     * @param beishu1      倍数
     * @param sbetcodedans 投注内容
     * @param divide       金额
     * @param playid       投注记录id
     */
    private void getstring(Integer redpackageid, String userid, String playiddanshi, String bettype, String type, String issueNo, SimpleDateFormat sdf, String beishu1, String sbetcodedans, Integer divide, Integer playid) {
        String caipiaoOrderSubCode = "";
        String types = "";
        if ("SSQ".equals(type)) {
            types = "ssq";
            caipiaoOrderSubCode = com.oruit.app.util.PayUtil.getTradeNo();
            String substring = issueNo.substring(4);
            caipiaoOrderSubCode = types + caipiaoOrderSubCode + substring;
        } else if ("10".equals(type)) {
            types = "klsf";
            int i = Integer.parseInt(issueNo.substring(8));
            String format1 = String.format("%03d", i);
            issueNo = issueNo.substring(0, 8) + format1;
            caipiaoOrderSubCode = com.oruit.app.util.PayUtil.getTradeNo();
            caipiaoOrderSubCode = types + caipiaoOrderSubCode + format1;
        } else {

        }

        List<Scheme> schemes = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setSchemeId(caipiaoOrderSubCode);
        scheme.setGame(type);
        scheme.setGameIssue(issueNo);
        scheme.setBetType(bettype);
        scheme.setBetMulti(beishu1);
        scheme.setBetMoney(String.valueOf(divide));
        scheme.setBetCode(sbetcodedans);
        schemes.add(scheme);
        UserPlayRecord userPlayRecord = new UserPlayRecord();
        if (playiddanshi == null || "".equals(playiddanshi)) { //复式的投注 一次一张票上一条
            //保存投注内容
            String s = Insertlog(userid, schemes);
            /*if("-1".equals(s)){
                //c出票失败的操作
                getfail(redpackageid,userid, playid);
                userPlayRecord.setStatus(1);
            }else if("0".equals(s)){
                userPlayRecord.setStatus(2);
            }else{
                userPlayRecord.setStatus(5);
            }*/
            userPlayRecord.setStatus(5);
            //更新caipiaoOrderSubCode
            userPlayRecord.setPlayId(playid);
            userPlayRecord.setCaipiaoOrderSubCode(caipiaoOrderSubCode);
            int i = userPlayRecordMapper.updateByPrimaryKeySelective(userPlayRecord);
        } else {
            String substring = playiddanshi.substring(0, playiddanshi.length() - 1);
            String[] split = substring.split("\\^");
            //保存投注内容
            String s = Insertlog(userid, schemes);
            for (int i = 0; i < split.length; i++) {
                if ("-1".equals(s)) {
                    int playId = Integer.parseInt(split[i]);
                    //c出票失败的操作
                    getfail(redpackageid, userid, playId);
                    userPlayRecord.setStatus(1);
                } else if ("0".equals(s)) {
                    userPlayRecord.setStatus(5);
                } else {
                    userPlayRecord.setStatus(5);
                }
                //更新caipiaoOrderSubCode
                userPlayRecord.setPlayId(Integer.parseInt(split[i]));
                // caipiaoOrderSubCode = types + com.oruit.app.util.PayUtil.getTradeNo();
                userPlayRecord.setCaipiaoOrderSubCode(caipiaoOrderSubCode);
                int ii = userPlayRecordMapper.updateByPrimaryKeySelective(userPlayRecord);
                //System.out.println("--------------更新成功------------------:" + ii);
            }
        }
    }

    /**
     * 保存投注内容
     *
     * @param userid
     * @param schemes
     * @return
     */
    private String Insertlog(String userid, List<Scheme> schemes) {
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.INTERFACE_CONFIG);
        String configValue = configInfo.getConfigValue();
        Map<String, Object> map = new HashMap<>();
        if ("1".equals(configValue)) {
            map = XMLClient.oltpPrintTicketProxyServlet(schemes);
            String s = map.get("responseCode").toString();
            String content = "responseCode:" + s + "content:" + map.get("content").toString();
            UserPlayLog userPlayLog = new UserPlayLog();
            userPlayLog.setContent(content);
            userPlayLog.setCreateTime(new Date());
            userPlayLog.setUserId(userid);
            userPlayLogMapper.insertSelective(userPlayLog);
            return s;
        }
        return "0";
    }

    /**
     * 出票失败的操作
     *
     * @param userid
     * @param playId
     */
    private void getfail(Integer redpackageid, String userid, Integer playId) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("redpackageid", redpackageid);
        maps.put("userid", userid);
        BigDecimal redpackageAmount = new BigDecimal("0");
        String s = userRedpacketMapper.QueryuserRedPackage(maps);
        if (s != null && !"".equals(s)) {
            redpackageAmount = new BigDecimal(s);
        }
        System.out.println("-------------s-----------------------------------:" + s);
        System.out.println("-------------redpackageAmount-----------------------------------:" + redpackageAmount);
        UserRedpacket userRedpacket = new UserRedpacket();
        userRedpacket.setState(0);
        userRedpacket.setId(redpackageid);
        userRedpacketMapper.updateByPrimaryKeySelective(userRedpacket);

        UserPlayRecord userPlayRecord1 = userPlayRecordMapper.selectByPrimaryKey(playId);
        System.out.println("+++++++++++++++++++++++++:" + playId);
        BigDecimal amount = userPlayRecord1.getAmount();
        //出票失败把钱返回给用户
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        BigDecimal balanceAmount = userStatistics.getBalanceAmount().subtract(redpackageAmount);
        System.out.println("-------------redpackageAmount-----------------------------------:" + redpackageAmount);
        System.out.println("-------------balanceAmount-----------------------------------:" + balanceAmount);
        BigDecimal add = balanceAmount.add(amount);
        userStatistics.setUserId(userid);
        userStatistics.setBalanceAmount(add);
        userStatistics.setUpdateTime(new Date());
        userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);
        //写入日志
        UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
        userStatisticsLog.setUserId(userid);
        userStatisticsLog.setBalanceAmount(userStatistics.getBalanceAmount());
        userStatisticsLog.setWinningBalabceAmount(userStatistics.getWinningBalabceAmount());
        userStatisticsLog.setWinningNum(userStatistics.getWinningNum());
        userStatisticsLog.setTusunNum(userStatistics.getTusunNum());
        userStatisticsLog.setTudiNum(userStatistics.getTudiNum());
        userStatisticsLog.setBettingNum(userStatistics.getBettingNum());
        userStatisticsLog.setCreateTime(new Date());
        userStatisticsLog.setTotalAmount(userStatistics.getTotalAmount());
        userStatisticsLog.setWinningTotalAmount(userStatistics.getWinningTotalAmount());
        userStatisticsLogMapper.insertSelective(userStatisticsLog);

        UserPlayRecord userPlayRecord = new UserPlayRecord();
        userPlayRecord.setUserId(userid);
        userPlayRecord.setPlayId(playId);
        userPlayRecord.setStatus(1);
        userPlayRecordMapper.updateByPrimaryKeySelective(userPlayRecord);

        //System.out.println("****************************************************************");
        //写入账户明细
        userAccount("tuikuan", userid, amount, playId);
        //System.out.println("****************************************************************");

    }

    /**
     * 购买书籍下单
     *
     * @param request
     * @return
     */
    @Override
    public ResultBean BookOrder(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr());
        String userid = request.getParameter("userid");
        String orderid = request.getParameter("orderid");
        String paycode = request.getParameter("paycode");
        System.out.println("-----------------------userid:" + userid);
        System.out.println("-----------------------orderid:" + orderid);
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("orderid", orderid);
        Map<String, Object> map1 = orderInfoMapper.QueryBookOrder(map);
        if (map1 == null || map1.isEmpty()) {
            return new ResultBean("2000", "1|订单不存在！");
        }
        String total = map1.get("total").toString();
        String UnionId = userAuthorizationMapper.queryUnionId(userid);
        String openId = authorizationMapper.queryOpenid(UnionId);
        //支付
        Map<String, Object> map2 = new HashMap<>();
        if ("alipay".equals(paycode)) {
            // 商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = orderid;
            // 订单名称，必填
            String subject = "购买书籍";
            System.out.println(subject);
            // 付款金额，必填
            String total_amount = total;
            // 商品描述，可空
            String body = "";
            // 超时时间 可空
            String timeout_express = "2m";
            String s = GetAlipayH5(AlipayConfig.notify_url, out_trade_no, subject, total_amount, body, timeout_express);
            map2.put("orderstr", s);
        }
        if ("wechatpay".equals(paycode)) {
            map.put("remoteAddr", request.getRemoteAddr());
            map2 = getStringObjectMapMEVB("1", Float.valueOf(total), "购买图书", map, NOTIFY_URL, orderid, openId, "MWEB");
            //map2 = getStringObjectMap("1", Float.valueOf(total), "购买图书", map, NOTIFY_URL, Integer.parseInt(orderid), openId, "APP");
            map2.put("orderstr", "");
        }
        return new ResultBean("1000", "0|下单成功！", map2, "1");
    }

    /**
     * H5的支付宝支付
     *
     * @param out_trade_no
     * @param subject
     * @param total_amount
     * @param body
     * @param timeout_express
     */
    private String GetAlipayH5(String url, String out_trade_no, String subject, String total_amount, String body, String timeout_express) {
        // 销售产品码 必填
        String product_code = "QUICK_WAP_PAY";
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(url);
        // 设置同步地址
        alipay_request.setReturnUrl(AlipayConfig.return_url);
        // form表单生产
        String form = null;
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            System.out.println("-----------");
            System.out.println(form);
            System.out.println("---------------");
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return form;
    }

    /**
     * 保存投注记录
     *
     * @param userid       用户id
     * @param
     * @param userPlayPlan
     * @param planId
     * @param issueNo
     * @param createTime
     */
    private Integer saveUserPlayRecord(Integer printed, Integer caipiaoId, String caipiaoOrderSubCode, String userid, UserPlayPlan userPlayPlan, Integer planId, String issueNo, Date createTime, String caipiaoOrderSubId) {
        UserPlayRecord userPlayRecord = new UserPlayRecord();
        userPlayRecord.setCaipiaoId(caipiaoId);
        userPlayRecord.setUserId(userid);
        userPlayRecord.setPlayType(userPlayPlan.getPlayType());
        userPlayRecord.setStatus(0);
        userPlayRecord.setWinningMoney(new BigDecimal(0));
        userPlayRecord.setDanma(userPlayPlan.getDanma());
        userPlayRecord.setTuoma(userPlayPlan.getTuoma());
        userPlayRecord.setHongqiu(userPlayPlan.getHongqiu());
        userPlayRecord.setLanqiu(userPlayPlan.getLanqiu());
        userPlayRecord.setPlayMethod(userPlayPlan.getPlayMethod());
        userPlayRecord.setNumber(userPlayPlan.getNumber());
        userPlayRecord.setQishu(1);
        Integer beishu = userPlayPlan.getBeishu();
        Integer zhushu = userPlayPlan.getZhushu();
        userPlayRecord.setBeishu(beishu);
        userPlayRecord.setZhushu(zhushu);
        int i1 = beishu * zhushu * 2;
        userPlayRecord.setAmount(new BigDecimal(String.valueOf(i1)));
        userPlayRecord.setCaipiaoOrderSubId(caipiaoOrderSubId);
        userPlayRecord.setOrderTime(new Date());
        userPlayRecord.setPlanId(planId);
        userPlayRecord.setCreateTime(new Date());
        userPlayRecord.setPrizeName("");
        userPlayRecord.setIssueNo(issueNo);
        userPlayRecord.setCaipiaoOrderSubCode(caipiaoOrderSubCode);
        int i = userPlayRecordMapper.insertSelective(userPlayRecord);
        Integer playId = 0;
        if (i > 0) {
            playId = userPlayRecord.getPlayId();
        }
        return playId;
    }

    /**
     * 微信h5快乐十分下单
     * @param caipiaoOrderInfo
     * @param caipiaoOrderId1
     * @param userid
     * @return
     */
    private String wxOrder(int amount, String remoteAddr, CaipiaoOrderInfo caipiaoOrderInfo, String caipiaoOrderId1, String userid) {
        String UnionId = userAuthorizationMapper.queryUnionId(userid);
        String openId = authorizationMapper.queryOpenid(UnionId);
        System.out.println("----------------openId----------------------" + openId);
        Float money = Float.valueOf((float) (amount * 100));
        //Float money = Float.valueOf((float) (amount));
        String total_free = String.valueOf(money.intValue());
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(appid);
        unifiedorder.setMch_id(mch_id);
        unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));

        unifiedorder.setBody("快乐十分");
        unifiedorder.setDevice_info("");
        unifiedorder.setOpenid(openId);
        unifiedorder.setOut_trade_no(caipiaoOrderInfo.getCaipiaoOrderId());
        unifiedorder.setAttach(caipiaoOrderId1);
        unifiedorder.setProduct_id("1");
        unifiedorder.setTotal_fee(total_free);//单位分
        //unifiedorder.setSpbill_create_ip(request.getRemoteAddr());//IP
        unifiedorder.setSpbill_create_ip(remoteAddr);//IP
        unifiedorder.setNotify_url(NOTIFY_URL); //回调地址
        unifiedorder.setTrade_type("JSAPI");//JSAPI，NATIVE，APP，WAP
        ;
        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, PartnerKey);

        String json = PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(), appid, PartnerKey);
        //将json 传到jsp 页面
        //request.setAttribute("wxJsApiParam", json);

        return json;

    }

    @Override
    public ResultBean SSQPurchaseH5(Map<String, Object> map) throws Exception {
        int redlength = 0;//红球数量
        int bluelength = 0;//蓝球数量
        Map<String, Object> maps = new HashMap<>();
        maps.put("lotteryname", "双色球");
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String s1 = userForbiddenMapper.QueryUserForbidden(userid);
        if (s1 != null && !"".equals(s1)) {
            return new ResultBean("2000", "1|非法操作禁止购彩！");
        }
        String type = "";
        String bettingmultiple = map.get("bettingmultiple").toString();
        if (bettingmultiple == null || "".equals(bettingmultiple)) {
            return new ResultBean("2000", "0|倍数为空！");
        }
        String bluenumber = map.get("bluenumber").toString();
        if (bluenumber == null || "".equals(bluenumber)) {
            return new ResultBean("2000", "0|蓝球为空！");
        }
        String rednumber = map.get("rednumber").toString();
        if (rednumber == null || "".equals(rednumber)) {
            return new ResultBean("2000", "0|红球为空！");
        }
        if (Integer.parseInt(bettingmultiple) < 1) {
            return new ResultBean("2000", "1|倍数最小为1！");
        }
        if (Integer.parseInt(bettingmultiple) > 99) {
            return new ResultBean("2000", "1|倍数最大为99！");
        }
        Integer zhushu = 0;

        if (bluenumber == null || "".equals(bluenumber)) {
            return new ResultBean("2000", "0|蓝球为空！");
        }
        if (rednumber == null || "".equals(rednumber)) {
            return new ResultBean("2000", "0|红球为空！");
        }
        redlength = rednumber.split(" ").length;
        bluelength = bluenumber.split(" ").length;
        System.out.println("--------:" + redlength);
        System.out.println("--------:" + bluelength);
        if (redlength < 6 || bluelength < 1) {
            return new ResultBean("2000", "1|请选择足够的蓝球或者红球1111！");
        } else if ((redlength > 6 && bluelength >= 1)||(redlength >= 6 && bluelength > 1)) {
            type = "2";
        } else if (redlength == 6 && bluelength == 1) {
            type = "1";
        } else {
            return new ResultBean("2000", "1|请选择足够的蓝球或者红球2222！");
        }
        zhushu = zhushu + shuagnseqiu.danshizhushu(redlength, bluelength).intValue();

        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
        caipiaoOrderInfo.setCaipiaoOrderId(UUIDUtils.uuid());
        caipiaoOrderInfo.setCaipiaoId(11);
        caipiaoOrderInfo.setCaipiaoOrderCode(com.oruit.app.util.PayUtil.getTradeNo());
        caipiaoOrderInfo.setUserId(userid);
        caipiaoOrderInfo.setState(1);
        caipiaoOrderInfo.setPayType(1);
        caipiaoOrderInfo.setUsedRedpacket(0);
        caipiaoOrderInfo.setRedpacketId(0);
        caipiaoOrderInfo.setRedpacketAmount(new BigDecimal(0));
        caipiaoOrderInfo.setCreateTime(new Date());
        caipiaoOrderInfo.setUpdateTime(new Date());
        caipiaoOrderInfo.setPayTime(ymdhms.parse("1990-01-01"));
        //money  =  注数  *  倍数  * 追的期数  * 2
        int money = zhushu * Integer.parseInt(bettingmultiple) * 2;
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.MAXMONEY_CONFIG);
        String configValue = configInfo.getConfigValue();
        if (new BigDecimal(money).compareTo(new BigDecimal(configValue)) == 1) {
            return new ResultBean("2000", "1|为了您的财产安全，请选择较小的金额！");
        }
        maps.put("money", String.valueOf(money) + ".00");
        caipiaoOrderInfo.setTotal(new BigDecimal(money));
        caipiaoOrderInfo.setAmount(new BigDecimal(money));
        int i = caipiaoOrderInfoMapper.insertSelective(caipiaoOrderInfo);
        String caipiaoOrderId = caipiaoOrderInfo.getCaipiaoOrderId();
        maps.put("orderid", caipiaoOrderId);
        ShuangseqiuOrder shuangseqiuOrder = new ShuangseqiuOrder();
        if ("1".equals(type) || "2".equals(type)) {
            redlength = rednumber.split(" ").length;
            bluelength = bluenumber.split(" ").length;
            zhushu = shuagnseqiu.danshizhushu(redlength, bluelength).intValue();
            if (redlength == 6 && bluelength == 1) {
                shuangseqiuOrder.setPlayType(1);
                shuangseqiuOrder.setDanma("");
                shuangseqiuOrder.setTuoma("");
                shuangseqiuOrder.setHongqiu(rednumber);
                shuangseqiuOrder.setLanqiu(bluenumber);
            }
            if (redlength >= 6 && bluelength > 1 || redlength > 6 && bluelength >= 1) {
                shuangseqiuOrder.setPlayType(2);
                shuangseqiuOrder.setDanma(" ");
                shuangseqiuOrder.setTuoma("");
                shuangseqiuOrder.setHongqiu(rednumber);
                shuangseqiuOrder.setLanqiu(bluenumber);
            }
        }
        shuangseqiuOrder.setAmount(new BigDecimal(money));
        shuangseqiuOrder.setBeishu(Integer.parseInt(bettingmultiple));
        shuangseqiuOrder.setCaipiaoOrderId(caipiaoOrderId);
        shuangseqiuOrder.setCreateTime(new Date());
        shuangseqiuOrder.setQishu(1);
        shuangseqiuOrder.setZhushu(zhushu);
        shuangseqiuOrder.setCaipiaoOrderSubCode(com.oruit.app.util.PayUtil.getTradeNo());
        shuangseqiuOrder.setCaipiaoOrderSubId(UUIDUtils.uuid());
        shuangseqiuOrder.setRedpacketAmount(new BigDecimal(0));
        String s = Issueno.achieveNum();
        maps.put("issueno", s);
        shuangseqiuOrder.setIssueNo(s);
        shuangseqiuOrderMapper.insertSelective(shuangseqiuOrder);
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        BigDecimal balanceAmount = userStatistics.getBalanceAmount();
        BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();
        BigDecimal add = balanceAmount.add(winningBalabceAmount);
        String balance = String.valueOf(add);
        maps.put("balance", balance);
        String remoteAddr = map.get("remoteAddr").toString();
        String s2 = wxOrder(money, remoteAddr, caipiaoOrderInfo, caipiaoOrderInfo.getCaipiaoOrderId(), userid);
        return new ResultBean("1000", "0|待支付", s2, "1");
    }

    /*public static void main(String[] args) {
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid("wxdd7bce23cb41e8f4");
        unifiedorder.setMch_id("1492852902");
        unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));

        unifiedorder.setBody("快乐十分");
        unifiedorder.setDevice_info("");
        unifiedorder.setOpenid("ocenUwRTsps6LtayNjpzCMY_7qGw");
        unifiedorder.setOut_trade_no("123456789");
        unifiedorder.setAttach("111111111111111111111");
        unifiedorder.setProduct_id("1");
        unifiedorder.setTotal_fee("100");//单位分
        //unifiedorder.setSpbill_create_ip(request.getRemoteAddr());//IP
        unifiedorder.setSpbill_create_ip("127.0.0.1");//IP
        unifiedorder.setNotify_url("https://owodream.net/lottery/WXpay/paynotify"); //回调地址
        unifiedorder.setTrade_type("JSAPI");//JSAPI，NATIVE，APP，WAP
        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, "8KHZ2gbm4cVlgnxIvnvoZgHDg2xiF5ht");
        String json = PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(), "wxdd7bce23cb41e8f4", "8KHZ2gbm4cVlgnxIvnvoZgHDg2xiF5ht");
        System.out.println(json);
    }
*/

    /**
     * 浏览器快乐十分下单
     *
     * @param request
     * @return
     */
    @Override
    public ResultBean BrowserOrders(HttpServletRequest request) {

        String userid = request.getParameter("userid");
        String orderid = request.getParameter("orderid");
        String type = request.getParameter("type");
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("orderid", orderid);
        String querycaipiaoordercode = caipiaoOrderInfoMapper.querycaipiaoordercode(map);
        if (querycaipiaoordercode == null || "".equals(querycaipiaoordercode)) {
            return new ResultBean("2000", "1|订单不存在！");
        }
        String total = "1";
        String UnionId = userAuthorizationMapper.queryUnionId(userid);
        String openId = authorizationMapper.queryOpenid(UnionId);
        //支付
        Map<String, Object> map2 = new HashMap<>();
        if ("1".equals(type)) {
            // 商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = orderid;
            // 订单名称，必填
            String subject = "快乐十分";
            System.out.println(subject);
            // 付款金额，必填
            String total_amount = "0.01";
            // 商品描述，可空
            String body = "";
            // 超时时间 可空
            String timeout_express = "2m";

            String s = GetAlipayH5(AlipayConfig.notify_url, out_trade_no, subject, total_amount, body, timeout_express);
            map2.put("result", s);
        } else {
            map.put("remoteAddr", request.getRemoteAddr());
            //map2 = getStringObjectMapMEVB("1", Float.valueOf(total), "快乐十分", map, NOTIFY_URL, Integer.parseInt(orderid), openId, "JSAPI");
            map2 = getStringObjectMapMEVB("1", Float.valueOf(total), "充值", map, NOTIFY_URLAPP, orderid, openId, "MWEB");
        }
        return new ResultBean("1000", "0|下单成功！", map2, "1");
    }

    @Override
    public void chongzhithree(String params) throws Exception {
        CaipiaoOrderInfo caipiaoOrderInfos = caipiaoOrderInfoMapper.selectByPrimaryKey(params);
        Integer state = caipiaoOrderInfos.getState();
        if (state == 1) {
            //根据订单号查询到用户的id
            String userid = caipiaoOrderInfos.getUserId();
            Integer caipiaoid = caipiaoOrderInfos.getCaipiaoId();
            String qishu = "";
            String beishu = "";
            String playmethod = "";
            String number = "";
            String caipiaoordersubid = "";
            String issueno = "";
            int playType = 0;
            String hongqiu = "";
            String zhushu = "";
            String lanqiu = "";
            int amount = 0;//金额
            if (caipiaoid == 114) {
                Map<String, Object> map = caipiaoOrderInfoMapper.queryUseridbyOrder(params);
                issueno = map.get("issueno").toString();
                beishu = map.get("beishu").toString();
                qishu = map.get("qishu").toString();
                zhushu = map.get("zhushu").toString();
                amount = Double.valueOf(map.get("amount").toString()).intValue();
                caipiaoordersubid = map.get("caipiaoordersubid").toString();
                number = map.get("number").toString();
                playmethod = map.get("playmethod").toString();
            }
            if (caipiaoid == 11) {
                Map<String, Object> maps = shuangseqiuOrderMapper.querySSQOrderH5(params);
                issueno = maps.get("issueNo").toString();
                beishu = maps.get("beishu").toString();
                zhushu = maps.get("zhushu").toString();
                qishu = maps.get("qishu").toString();
                amount = Double.valueOf(maps.get("amount").toString()).intValue();
                hongqiu = maps.get("hongqiu").toString();
                lanqiu = maps.get("lanqiu").toString();
                playType = Integer.parseInt(maps.get("playType").toString());
                caipiaoordersubid = maps.get("caipiaoordersubid").toString();
            }
            String queryordernum = caipiaoOrderInfoMapper.Queryordernum(userid);
            int i1 = Integer.parseInt(queryordernum);

            ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.XINREN_CONFIG);
            String configValue = configInfo.getConfigValue();
            System.out.println("--------configValue" + configValue);
            JSONObject jsonObject = JSONObject.parseObject(configValue);
            String xinrenhongbao = jsonObject.get("xinrenhongbao").toString();
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            Redacket redacket = redacketMapper.selectByPrimaryKey(Integer.parseInt(xinrenhongbao));
            Integer validDays = redacket.getValidDays();
            c.add(Calendar.DATE, validDays);
            //积分系统
            pointSystem(userid, new BigDecimal(String.valueOf(amount)));
            if (i1 == 0) {
                UserRedpacket userRedpacket = new UserRedpacket();
                userRedpacket.setUserId(userid);
                userRedpacket.setState(0);
                userRedpacket.setCreateTime(new Date());
                userRedpacket.setRedpacketId(Integer.parseInt(xinrenhongbao));
                userRedpacket.setBeginTime(new Date());
                userRedpacket.setOverTime(c.getTime());
                int redid = userRedpacketMapper.insertSelective(userRedpacket);
            }
            //插入账户明细
            userAccount("goucai", userid, new BigDecimal(String.valueOf(amount)), 0);
            //更新用户信息
            UserStatistics userStatistics = new UserStatistics();
            userStatistics.setUserId(userid);
            userStatistics.setBettingNum(1);
            userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);
            UserPlayPlan userPlayPlan = new UserPlayPlan();
            //插入追号表和投注记录表
            String s = "";//当前期号
            if (caipiaoid == 114) {
                String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
                String klsFissueno = shuagnseqiu.isKLSFissueno();
                if ("false".equals(klsFissueno)) {
                    klsFissueno = shuagnseqiu.KLSFissueno();
                }
                s = yyyyMMdd + klsFissueno;//当期期号
                if ("false".equals(klsFissueno)) {
                    s = yyyyMMdd + shuagnseqiu.KLSFissueno();
                }
                String s2 = s.substring(0, 8) + "0" + s.substring(8, 10);
                Map<String, Object> stringObjectMap = XMLClient.oltpQueryTermInfoServlet("10", s2);
                userPlayPlan.setStatus(2);
                System.out.println("-------issueno-------:" + issueno);
                System.out.println("--------klsFissueno------:" + klsFissueno);
                System.out.println("--------s------:" + s);
                System.out.println("--------stringObjectMap------:" + stringObjectMap);
                if (!"1000".equals(klsFissueno) && Integer.parseInt(issueno.substring(4, 10)) > Integer.parseInt(s.substring(4, 10))) {//数据里面的期数大于当前的期数  为预售
                    userPlayPlan.setType(1);
                    userPlayPlan.setStatus(1);
                } else if ("1000".equals(klsFissueno)) {
                    userPlayPlan.setType(1);
                    userPlayPlan.setStatus(1);
                } else if (stringObjectMap == null) {
                    userPlayPlan.setType(1);
                    userPlayPlan.setStatus(1);
                } else {//立即投注
                    userPlayPlan.setType(0);
                }
                userPlayPlan.setCaipiaoId(114);
                userPlayPlan.setPlayMethod(playmethod);
                userPlayPlan.setDanma("");
                userPlayPlan.setTuoma("");
                userPlayPlan.setHongqiu("");
                userPlayPlan.setLanqiu("");
                userPlayPlan.setNumber(number);
            }

            if (caipiaoid == 11) {
                System.out.println("-------");
                s = Issueno.achieveNumssq();
                userPlayPlan.setCaipiaoId(11);
                userPlayPlan.setStatus(2);
                if ("1000".equals(s)) {//1 为预售
                    userPlayPlan.setType(1);
                    userPlayPlan.setStatus(1);
                } else {//立即投注
                    userPlayPlan.setType(0);
                }
                userPlayPlan.setCaipiaoId(11);
                userPlayPlan.setPlayType(playType);
                userPlayPlan.setDanma("");
                userPlayPlan.setTuoma("");
                userPlayPlan.setHongqiu(hongqiu);
                userPlayPlan.setLanqiu(lanqiu);
                userPlayPlan.setPlayMethod(" ");
                userPlayPlan.setNumber(" ");
            }
            userPlayPlan.setUserId(userid);
            userPlayPlan.setQishu(1);
            userPlayPlan.setBeishu(Integer.parseInt(beishu));
            userPlayPlan.setZhushu(Integer.parseInt(zhushu));
            userPlayPlan.setCreateTime(new Date());
            userPlayPlan.setCaipiaoOrderSubId(caipiaoordersubid);
            //int i = Integer.parseInt(beishu) * 2;
            userPlayPlan.setAmount(new BigDecimal(String.valueOf(amount)));
            System.out.println("---------userPlayPlan-------------:" + userPlayPlan);
            int i2 = userPlayPlanMapper.insertSelective(userPlayPlan);
            Integer planId = userPlayPlan.getPlanId();
            CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
            caipiaoOrderInfo.setState(2);
            caipiaoOrderInfo.setCaipiaoOrderId(params);
            caipiaoOrderInfo.setUpdateTime(new Date());
            caipiaoOrderInfo.setPayTime(new Date());
            caipiaoOrderInfoMapper.updateByPrimaryKeySelective(caipiaoOrderInfo);
            Integer playid = saveUserPlayRecord(0, caipiaoid, " ", userid, userPlayPlan, planId, issueno, new Date(), caipiaoordersubid);
            String amounts = String.valueOf(amount*100);
            if (s.equals(issueno)) {//当前期号和数据库里面的期号作对比相等  掉去投注接口
                String ss = "";//期号
                String caipiaoOrderSubCode = "";//
                String replace = "";//号码
                String game = "0";
                String bettype = "0";
                if (caipiaoid == 114) {
                    replace = number.replace(" ", "") + "R01^";
                    if ("r5".equals(playmethod)) {
                        replace = number.replace(" ", "") + "01^";
                    }
                    ss = issueno.substring(0, 8) + "0" + issueno.substring(8, 10);
                    int i = Integer.parseInt(ss.substring(8));
                    String format1 = String.format("%03d", i);
                    caipiaoOrderSubCode = "klsf" + com.oruit.app.util.PayUtil.getTradeNo() + format1;
                    game = "10";
                    bettype = "0";
                }
                if (caipiaoid == 11) {
                    game = "SSQ";
                    ss = issueno;
                    replace = hongqiu.replace(" ", "");
                    String replace1 = lanqiu.replace(" ", "");
                    String substring = ss.substring(4);
                    caipiaoOrderSubCode = "ssq" + com.oruit.app.util.PayUtil.getTradeNo() + substring;
                    if (playType == 1) {
                        String format1 = String.format("%02d", Integer.parseInt(beishu));
                        replace = replace + replace1 + format1 + "^";
                        bettype = "1";
                    }
                    if (playType == 2) {
                        replace = replace + "|" + replace1 + "^";
                        bettype = "2";
                    }
                }
                if (caipiaoid == 114) {
                    Integer status = userPlayPlan.getStatus();
                    Integer type = userPlayPlan.getType();
                    if (status != 1 && type != 0) {
                        gettouzhu(userid, beishu, playid, amounts, ss, caipiaoOrderSubCode, replace, game, bettype);
                    }
                }
                if (caipiaoid == 11) {
                    gettouzhu(userid, beishu, playid, amounts, ss, caipiaoOrderSubCode, replace, game, bettype);
                }

            }
        }
    }

    private void gettouzhu(String userid, String beishu, Integer playid, String amounts, String ss, String caipiaoOrderSubCode, String replace, String game, String bettype) {
        List<Scheme> schemes = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setSchemeId(caipiaoOrderSubCode);
        scheme.setGame(game);
        scheme.setGameIssue(ss);
        scheme.setBetType(bettype);
        scheme.setBetMulti(beishu);
        scheme.setBetMoney(amounts);
        scheme.setBetCode(replace);
        schemes.add(scheme);
        // Map<String, Object> maps = oltpPrintTicketProxyServlet(schemes);
        String sq = Insertlog(userid, schemes);
        UserPlayRecord userPlayRecord = new UserPlayRecord();
        userPlayRecord.setStatus(5);
        //更新caipiaoOrderSubCode
        userPlayRecord.setPlayId(playid);
        userPlayRecord.setCaipiaoOrderSubCode(caipiaoOrderSubCode);
        int ii = userPlayRecordMapper.updateByPrimaryKeySelective(userPlayRecord);
    }

    @Override
    public ResultBean ClientOrderTwo(HttpServletRequest request) throws ParseException {
        ResultBean resultBean = null;
        String userid = request.getParameter("userid");
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        System.out.println("-----------------userid:" + userid);
        String number = request.getParameter("number");
        if (number == null || "".equals(number)) {
            return new ResultBean("2000", "1|请选择号码！");
        }
        String[] split = number.split(" ");
        int length = split.length;
        if (length < 2) {
            return new ResultBean("2000", "1|请选择足够的号码！");
        }
        String bettingmultiple = request.getParameter("bettingmultiple");
        if (bettingmultiple == null || "".equals(bettingmultiple)) {
            return new ResultBean("2000", "0|倍数为空！");
        }
        if (Integer.parseInt(bettingmultiple) < 1) {
            return new ResultBean("2000", "1|倍数最小为1！");
        }
        if (Integer.parseInt(bettingmultiple) > 99) {
            return new ResultBean("2000", "1|倍数最大为99！");
        }
        int money = Integer.parseInt(bettingmultiple) * 2;
        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
        caipiaoOrderInfo.setCaipiaoOrderId(UUIDUtils.uuid());
        caipiaoOrderInfo.setCaipiaoId(114);
        caipiaoOrderInfo.setCaipiaoOrderCode(com.oruit.app.util.PayUtil.getTradeNo());
        caipiaoOrderInfo.setUserId(userid);
        caipiaoOrderInfo.setState(1);
        caipiaoOrderInfo.setPayType(1);
        caipiaoOrderInfo.setUsedRedpacket(0);
        caipiaoOrderInfo.setRedpacketId(0);
        caipiaoOrderInfo.setRedpacketAmount(new BigDecimal(0));
        caipiaoOrderInfo.setCreateTime(new Date());
        caipiaoOrderInfo.setUpdateTime(ymdhms.parse("1990-01-01"));
        caipiaoOrderInfo.setPayTime(ymdhms.parse("1990-01-01"));
        caipiaoOrderInfo.setTotal(new BigDecimal(money));
        caipiaoOrderInfo.setAmount(new BigDecimal(money));
        int i = caipiaoOrderInfoMapper.insertSelective(caipiaoOrderInfo);
        String caipiaoOrderId = caipiaoOrderInfo.getCaipiaoOrderId();
        if (i <= 0) {
            return new ResultBean("2000", "0|下单失败！！！");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String yyyyMMdd = format.format(new Date());
        String integer1 = shuagnseqiu.isKLSFissueno();
        if ("false".equals(integer1)) {
            integer1 = shuagnseqiu.KLSFissuenoquery();
        }
        System.out.println("-----------------yyyyMMdd------------------:" + yyyyMMdd);
        if (Integer.parseInt(integer1) > 84 && !"1000".equals(integer1)) {
            Calendar nowTime = Calendar.getInstance();
            nowTime.setTime(format.parse(yyyyMMdd));
            nowTime.add(Calendar.DATE, 1);
            Date time = nowTime.getTime();
            yyyyMMdd = format.format(time);
            integer1 = "01";
        }
        if ("1000".equals(integer1)) {
            integer1 = "01";
        }
        System.out.println("-----------------integer1------------------:" + integer1);
        System.out.println("-----------------yyyyMMdd------------------:" + yyyyMMdd);
        String issuno = yyyyMMdd + integer1;
        KuaileshifenOrder kuaileshifenOrder = new KuaileshifenOrder();
        kuaileshifenOrder.setAmount(new BigDecimal(money));
        kuaileshifenOrder.setCaipiaoOrderId(caipiaoOrderId);
        kuaileshifenOrder.setCaipiaoOrderSubCode(com.oruit.app.util.PayUtil.getTradeNo());
        kuaileshifenOrder.setCreateTime(new Date());
        kuaileshifenOrder.setZhushu(1);
        kuaileshifenOrder.setBeishu(Integer.parseInt(bettingmultiple));
        kuaileshifenOrder.setPlayType(1);
        kuaileshifenOrder.setNumber(number);
        kuaileshifenOrder.setIssueNo(issuno);
        kuaileshifenOrder.setQishu(1);
        kuaileshifenOrder.setCaipiaoOrderSubId(UUIDUtils.uuid());
        if (length == 2) {
            kuaileshifenOrder.setPlayMethod("r2");
        } else if (length == 3) {
            kuaileshifenOrder.setPlayMethod("r3");
        } else if (length == 4) {
            kuaileshifenOrder.setPlayMethod("r4");
        } else if (length == 5) {
            kuaileshifenOrder.setPlayMethod("r5");
        } else {
            return new ResultBean("2000", "1|选择球的数量不正确！！！");
        }
        int i1 = kuaileshifenOrderMapper.insertSelective(kuaileshifenOrder);
        String caipiaoOrderId1 = kuaileshifenOrder.getCaipiaoOrderId();
        Map<String, Object> maps = new HashMap<>();
        maps.put("caipiaoorderid", caipiaoOrderId1);
        maps.put("money", money);
        String resultBean1 = "";
        if (i1 > 0) {
            String remoteAddr = request.getRemoteAddr();
            resultBean1 = wxOrder(money, remoteAddr, caipiaoOrderInfo, caipiaoOrderId1, userid);
            resultBean = new ResultBean("1000", "0|成功！", resultBean1, "1");
        } else {
            resultBean = new ResultBean("2000", "1|下单失败！！！");
        }
        return resultBean;
    }

    @Override
    public void Ordertuikuan(HttpServletRequest request) throws Exception {
        String caipiaoid = "11,114";
        String[] split = caipiaoid.split(",");
        Map<String, Object> maps = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String s = "";
        for (int i = 0; i < split.length; i++) {
            caipiaoid = split[i];
            if ("11".equals(caipiaoid)) {
                s = Issueno.achieveNum();
                maps.put("issueno", s);
                maps.put("caipiaoid", Integer.parseInt(caipiaoid));
            }
            if ("114".equals(caipiaoid)) {
                s = shuagnseqiu.KLSFissuenotuikuan();
                s = format.format(new Date()) + s;
                maps.put("issueno", s);
                maps.put("caipiaoid", Integer.parseInt(caipiaoid));
            }
            System.out.println("------------------------maps----------------;" + maps);
            Map<String, Object> params = new HashMap<>();
            List<Map<String, Object>> maps1 = userPlayRecordMapper.QueryUsertuikuan(maps);
            if (maps1.size() > 0) {
                for (Map<String, Object> item : maps1) {
                    String userid = item.get("userid").toString();
                    String playids = item.get("playid").toString();
                    String caipiaoordersubid = item.get("caipiaoordersubid").toString();
                    String cpiaoid = item.get("caipiaoid").toString();
                    params.put("userid", userid);
                    params.put("caipiaoordersubid", caipiaoordersubid);
                    params.put("issueno", s);
                    Integer integer = userPlayRecordMapper.QueryUsertuikuanqishu(params);
                    Integer redpackageid = 0;
                    if (integer <= 0) {
                        if ("114".equals(cpiaoid)) {
                            redpackageid = kuaileshifenOrderMapper.queryredpackageid(params);
                        }
                        if ("11".equals(cpiaoid)) {
                            redpackageid = shuangseqiuOrderMapper.queryredpackageidssq(params);
                        }
                    }
                    int playid = Integer.parseInt(playids);
                    getfail(redpackageid, userid, playid);
                }
            }

        }
    }
}
