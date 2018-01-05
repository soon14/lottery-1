package com.oruit.app.dao;

import com.oruit.app.dao.model.ProductInfo;
import com.oruit.app.util.web.ResultBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    /**
     * 充值列表金额
     * @return
     */
    List<Map<String,Object>> GetRechargeProductList(Map<String,Object> map);

    /**
     * 查询商品的价格
     * @param map
     * @return
     */
    Map<String,Object> queryPrice(Map<String,Object> map);
    /**
     * 查询商品的详细信息
     * @param map
     * @return
     */
    Map<String,Object> BookDetails(Map<String,Object> map);
    /**
     * 查询选购的详细信息
     * @param map
     * @return
     */
    Map<String,Object> BookOrderInfo(Map<String,Object> map);
}