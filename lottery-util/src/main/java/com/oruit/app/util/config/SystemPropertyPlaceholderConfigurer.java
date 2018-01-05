/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * spring 加载properties属性
 * @author Liuk
 */
public class SystemPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    
    private static final Logger log = Logger.getLogger(SystemPropertyPlaceholderConfigurer.class);

    private static Map<String, Object> ctxPropertiesMap;

    public static String getContextProperty(final String key) {
        String result = null;
        if(ctxPropertiesMap.containsKey(key)){
        result =ctxPropertiesMap.get(key).toString();
        }else{
            log.debug("------------properties中不包含key----------------"+key);
        }
        return result;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

}
