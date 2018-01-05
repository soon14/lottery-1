/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.http.util;

import com.oruit.app.common.exception.PcAccessDeniedException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Liuk
 */
public class ExceptionHandle implements HandlerExceptionResolver {

    private final Logger log = Logger.getLogger(ExceptionHandle.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("message", getStackTrace(ex));
        if (ex instanceof PcAccessDeniedException) {   // 没登录
            log.info("-----系统异常-----", ex);
            return new ModelAndView("redirect:/login");
        } else {
            log.info("-----系统异常-----", ex);
            return new ModelAndView("errors/error", model);
        }
    }

    /**
     * 获取日志错误信息
     *
     * @param t
     * @return
     */
    private String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return sw.toString();
        }
    }
}