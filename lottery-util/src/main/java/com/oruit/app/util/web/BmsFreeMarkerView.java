package com.oruit.app.util.web;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;


import org.springframework.web.servlet.view.freemarker.FreeMarkerView;


public class BmsFreeMarkerView extends FreeMarkerView {

	public static final String CONTEXT_PATH = "ctx";
	public static final String BASE_PATH = "basePath";
	public static final String SERVER_IP = "serverIp";

	@Override
	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		model.put(CONTEXT_PATH, request.getContextPath());
		model.put(BASE_PATH, RequestUtils.getBasePath(request));
	}

}