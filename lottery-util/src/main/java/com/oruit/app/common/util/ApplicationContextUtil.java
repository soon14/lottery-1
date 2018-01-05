package com.oruit.app.common.util;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author yaofang
 * @version $Id: ApplicationContextUtil.java, v 0.1 2014年9月18日 上午11:38:06 yaofang Exp $
 */
public class ApplicationContextUtil {

    public static ApplicationContext applicationContext;

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

}
