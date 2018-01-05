package com.oruit.app.service;/**
 * Created by wyt on 2017/9/11.
 */

import com.oruit.app.util.web.ResultBean;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

/**
 * 购买书籍相关
 * @author wyt
 * @create 2017-09-11 20:35
 */
public interface BookService {

    /**
     * 书籍的详情
     * @param request
     * @return
     */
    ResultBean BookDetails(HttpServletRequest request);

    /**
     * 订单的详细信息
     * @param request
     * @return
     */
    ResultBean BookOrderInfo(HttpServletRequest request) throws ParseException;
}
