package com.oruit.app.util.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.common.exception.PcAccessDeniedException;
import com.oruit.app.common.exception.TnBaseException;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 
 * @author Liuk
 *
 */
public class MappingExceptionResolver extends SimpleMappingExceptionResolver {

    private final Logger logger = Logger.getLogger(MappingExceptionResolver.class);
    private String getErrorMsg(Exception e) {
	String errorMsg = "";
	Throwable cause = e.getCause();
	if (cause != null && StringUtils.isNotBlank(cause.getMessage())) {
	    errorMsg = e.getCause().getMessage();
	} else if (StringUtils.isNotBlank(e.getMessage())) {
	    errorMsg = e.getMessage();
	}
	return errorMsg;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
	    HttpServletResponse response, Object handler, Exception ex) {
	String accept = request.getHeader("accept");
	if (accept.contains("application/json")) {
	    Map<String, String> errors = new HashMap<>();
	    errors.put("errorCode", "-1");
	    errors.put("errorMsg", getErrorMsg(ex));

	    request.setAttribute("errorJson", JSONObject.toJSON(errors)
		    .toString());
	    return super.getModelAndView("errors/ajax/500", ex, request);
	}
	if (ex instanceof PcAccessDeniedException) {
	    response.setStatus(403);
	}
	return super.doResolveException(request, response, handler, ex);
    }

    @Override
    protected ModelAndView getModelAndView(String viewName, Exception ex) {
	if (ex instanceof TnBaseException) {
	    logger.error(MappingExceptionResolver.class, ex);
	} else {
	    logger.error(MappingExceptionResolver.class, ex);
	}
	return super.getModelAndView(viewName, ex);
    }

}
