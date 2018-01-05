package com.oruit.app.util.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.SystemPropertyUtils;

public class SystemConfig {
    
     private static Properties props = null;

    /**
     * 解析property的placeholder工具
     */
    private static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
	    SystemPropertyUtils.PLACEHOLDER_PREFIX,
	    SystemPropertyUtils.PLACEHOLDER_SUFFIX,
	    SystemPropertyUtils.VALUE_SEPARATOR, false);

    private static Locale defaultLocale;

    private static HashSet<String> logOperationPermits;

    private static Map<String, String> clientName2Password = null;

    private static Set<String> webServiceAllowedIps = null;

    private static Set<String> httpServiceAllowedIps = null;

    /**
     * 图片上传路径
     * @return 
     */
     public static String getFilePath() {
	return getConfigValueByKey("system.file.img.path");
    }
    /**
     * 图片显示地址
     * @return 
     */
     public static String getImgPath() {
        return getConfigValueByKey("img.path");
    }
     /**
      * 分享地址
      * @return 
      */
     public static String getSharedPath() {
        return getConfigValueByKey("shared.path");
    }
    /**
     * 根据配置的key
     */
    public static String getConfigValueByKey(String configKey) {
	if (props == null) {

	    return null;
	}
	return getProperty(configKey);
    }

    /**
     * 根据给定的key返回对应的配置值
     */
    public static String getConfigValueByKey(String key, String defaultValue) {
	String value = getConfigValueByKey(key);

	if (StringUtils.isEmpty(value)) {
	    value = defaultValue;
	}

	return value;
    }

    public static String getConfigValueByKey(String key, Properties properties) {
	if (props == null) {
	    return null;
	}
	return getProperty(key, properties);
    }

    /**
     * 根据给定的值返回字符串数组 默认是分号(;)
     * 
     * @param key
     *            关键字
     */
    public static String[] getConfigValueArrayByKey(String key) {
	return getConfigValueArrayByKey(key, "\\;");
    }

    /**
     * 根据给定的值返回字符串数组
     * 
     * @param key
     *            关键字
     * @param splitSymbol
     *            拆分字符串的特殊符号
     */
    public static String[] getConfigValueArrayByKey(String key,
	    String splitSymbol) {
	String value = getConfigValueByKey(key);
	String[] valueArray = null;

	if (value != null) {
	    valueArray = value.split(splitSymbol);
	}

	return valueArray;
    }

    /**
     * 根据给定的值返回字符串数组,并由iso8859转换成utf-8
     */
    public static String[] getConfigIsoValue2GbArrayByKey(String key,
	    String splitSymbol) {
	String value = getConfigValueByKey(key);
	String[] valueArray = null;

	if (value != null) {
	    valueArray = value.split(splitSymbol);
	}

	return valueArray;
    }

    /**
     * 根据给定的值返回ArrayList,默认是分号(;)
     * 
     * @param key
     *            关键字
     */
    public static List<String> getConfigValueListByKey(String key) {
	return getConfigValueListByKey(key, "\\;");
    }

    /**
     * 根据给定的值返回ArrayList
     * 
     * @param key
     *            关键字
     * @param splitSymbol
     *            拆分字符串的特殊符号
     */
    public static List<String> getConfigValueListByKey(String key,
	    String splitSymbol) {
	List<String> valueList = new ArrayList<String>();
	String[] strings = getConfigValueArrayByKey(key, splitSymbol);
	if (strings != null) {
	    Collections.addAll(valueList, strings);
	}
	return valueList;
    }

    /**
     * 根据给定的值返回ArrayList,并将其中的iso转换成utf-8
     */
    public static List<String> getConfigIsoValue2GbListByKey(String key,
	    String splitSymbol) {
	ArrayList<String> valueList = new ArrayList<String>();
	Collections.addAll(valueList,
		getConfigIsoValue2GbArrayByKey(key, splitSymbol));

	return valueList;
    }

    private static String getProperty(String key, Properties properties) {
	if (StringUtils.isEmpty(key)) {
	    throw new IllegalArgumentException("key is empty!");
	}
	if (!props.containsKey(key)) {
	    throw new IllegalArgumentException("can't find this key:" + key);
	}
	return helper.replacePlaceholders(props.getProperty(key), properties);
    }

    private static String getProperty(String key) {
	return getProperty(key, props);
    }

   

    /*---------------------------------------------------------------------------------------------------------*/
    public static Locale getDefaultLocale() {
	if (defaultLocale == null) {
	    defaultLocale = Locale.SIMPLIFIED_CHINESE;
	}
	return defaultLocale;
    }

    public void setProps(List<Properties> lists) {
	Properties prop = new Properties();
	for (Properties properties : lists) {
	    prop.putAll(properties);
	}
	SystemConfig.props = prop;
    }
}
