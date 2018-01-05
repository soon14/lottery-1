package com.oruit.weixin.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.oruit.weixin.support.TokenManager;

public class TokenManagerListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//WEB容器 初始化时调用
		TokenManager.init("appid", "secret","code");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//WEB容器  关闭时调用
		TokenManager.destroyed();
	}
}
