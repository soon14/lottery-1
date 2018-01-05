package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/9/11.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.dao.model.OrderDetail;
import com.oruit.app.dao.model.OrderInfo;
import com.oruit.app.dao.model.UserInfo;
import com.oruit.app.service.BookService;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.oruit.app.util.MobileUtil.matchesPhoneNumber;

/**
 * @author @wyt
 * @create 2017-09-11 20:39
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    /**
     * 书籍的详情
     * @param request
     * @return
     */
    @Override
    public ResultBean BookDetails(HttpServletRequest request) {
        ResultBean resultBean = null;
        String userid = request.getParameter("userid");
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id 为空！");
        }
        String productid = request.getParameter("productid");
        if(productid == null || "".equals(productid)){
            return new ResultBean("2000", "0|商品为空！");
        }
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.CHONGTI_CONFIG);
        String configValue = configInfo.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        String tushu = jsonObject.get("tushu").toString();
        int productCatalogId = Integer.parseInt(tushu);
        Map<String,Object> maps = new HashMap<>();
        maps.put("productCatalogId",productCatalogId);
        maps.put("productId",Integer.parseInt(productid));
        Map<String, Object> item = productInfoMapper.BookDetails(maps);
        String productcontent = item.get("productcontent").toString().replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll("\"", "").replaceAll("#mod_player,", "");
        item.put("productcontent",productcontent);
        if(item==null || "".equals(item)){
            return new ResultBean("2000", "0|商品不存在！");
        }
        configInfo = configInfoMapper.selectByConnfigCode(Constants.KUAIDI_CONFIG);
        configValue = configInfo.getConfigValue();
        jsonObject = JSONObject.parseObject(configValue);
        String deliveryamount = jsonObject.get("first").toString();
        item.put("deliveryamount",deliveryamount);

        resultBean =  new ResultBean("1000", "0|成功", item, "1");
        System.out.println("--------resultBean----------:"+resultBean);
        return resultBean;
    }

    @Override
    public ResultBean BookOrderInfo(HttpServletRequest request) throws ParseException {
        String userid = request.getParameter("userid");
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id 为空！");
        }
        String productid = request.getParameter("productid");
        if(productid == null || "".equals(productid)){
            return new ResultBean("2000", "0|商品为空！");
        }
        String num = request.getParameter("num");
        if(num == null || "".equals(num)){
            return new ResultBean("2000", "1|商品数量为空！");
        }
        if(Integer.parseInt(num)<1){
            return new ResultBean("2000", "1|商品数量不能为0！");
        }
        String contact = request.getParameter("contact");
        if(contact == null || "".equals(contact)){
            return new ResultBean("2000", "1|联系人为空！");
        }
        String mobile = request.getParameter("mobile");
        if(mobile == null || "".equals(mobile)){
            return new ResultBean("2000", "1|联系电话为空！");
        }
        if(matchesPhoneNumber(mobile)<1){
            return new ResultBean("2000", "1|手机号不正确！");
        }
        String address = request.getParameter("address");
        if(address == null || "".equals(address)){
            return new ResultBean("2000", "1|收货地址为空！");
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        if(userInfo== null || "".equals(userInfo)){
            return new ResultBean("2000", "0|用户不存在！");
        }
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.CHONGTI_CONFIG);
        String configValue = configInfo.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        String tushu = jsonObject.get("tushu").toString();
        int productCatalogId = Integer.parseInt(tushu);
        Map<String,Object> maps = new HashMap<>();
        maps.put("productCatalogId",productCatalogId);
        maps.put("productId",Integer.parseInt(productid));
        Map<String, Object> item = productInfoMapper.BookOrderInfo(maps);
        configInfo = configInfoMapper.selectByConnfigCode(Constants.KUAIDI_CONFIG);
        configValue = configInfo.getConfigValue();
        jsonObject = JSONObject.parseObject(configValue);
        String deliveryamount = jsonObject.get("first").toString();
        String price = item.get("price").toString();
        BigDecimal prices = new BigDecimal(price);
        BigDecimal nums = new BigDecimal(num);
        BigDecimal deliveryamounts = new BigDecimal(deliveryamount);
        BigDecimal amount = prices.multiply(nums).add(deliveryamounts);
        item.put("amount",amount);
        configInfo = configInfoMapper.selectByConnfigCode(Constants.RECHARGE_CONFIG);
         configValue = configInfo.getConfigValue();
         jsonObject = JSONObject.parseObject(configValue);
        JSONArray paytype = jsonObject.getJSONArray("paytype");
        item.put("paytype",paytype);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateTime(new Date());
        orderInfo.setUserId(userid);
        orderInfo.setPayType(Short.parseShort("0"));
        orderInfo.setDeliveryStatus(0);
        orderInfo.setDeliveryAmount(deliveryamounts);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse("1990-01-01");
        orderInfo.setPayTime(parse);
        orderInfo.setTotal(amount);
        orderInfo.setDiscount(new BigDecimal("0"));
        orderInfo.setState(Short.parseShort("1"));
        orderInfo.setDeliveryTime(parse);
        orderInfo.setPayTime(parse);
        orderInfo.setAmount(amount);
        orderInfo.setDeliveryStatus(0);
        orderInfoMapper.insertSelective(orderInfo);

        Integer orderId = orderInfo.getOrderId();
        item.put("orderid",orderId);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        orderDetail.setPrice(amount);
        orderDetail.setProductId(Integer.parseInt(productid));
        orderDetail.setQuantity(Integer.parseInt(num));
        orderDetail.setCreateTime(new Date());
        orderDetail.setContact(contact);
        orderDetail.setMobile(mobile);
        orderDetail.setAddress(address);
        orderDetailMapper.insertSelective(orderDetail);

        return new ResultBean("1000", "0|成功", item, "1");
    }
}
