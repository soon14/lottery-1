package com.oruit.app.http.action;/**
 * Created by wyt on 2017/9/12.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.BookService;
import com.oruit.app.service.OrderService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author @wyt
 * @create 2017-09-12 9:12
 */
@Controller
public class BookAction {
    @Autowired
    private BookService bookService;
    @Autowired
    private OrderService orderService;
    @RequestMapping("BookDetails")
    public void BookDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResultBean result  = bookService.BookDetails(request);
        ResponseUtils.writeResult(request, response, result);
    }
    @RequestMapping("BookOrderInfo")
    public void BookOrderInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        ResultBean result = bookService.BookOrderInfo(request);

        ResponseUtils.writeResult(request, response, result);
    }
    @RequestMapping("BookOrder")
    public void BookOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = orderService.BookOrder(request);
        ResponseUtils.writeResult(request, response, result);
    }
}
