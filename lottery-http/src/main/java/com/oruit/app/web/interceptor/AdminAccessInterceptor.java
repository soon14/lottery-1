/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.web.interceptor;


import com.oruit.app.util.config.Constants;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.oruit.app.common.exception.PcAccessDeniedException;

public class AdminAccessInterceptor extends HandlerInterceptorAdapter {
	private List<String> excludedUrls = Collections.emptyList();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		String requestUri = request.getRequestURI();
//		for (String url : excludedUrls) {
//			if (requestUri.endsWith(url)) {
//				return true;
//			}
//		}
//
//		Object admin =  request.getSession().getAttribute(
//				Constants.SESSION_ADMIN);
//		if (admin == null) {
//			throw new PcAccessDeniedException("非法访问");
//		}
		return true;
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}
}

