package com.oruit.app.util.web;/**
 * Created by wyt on 2017/10/20.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author @wyt
 * @create 2017-10-20 23:37
 */
public class ConfigUtil {
    private static Log logger = LogFactory.getLog(ConfigUtil.class);
    public static Properties getPprVue(String properName) {
        InputStream inputStream
                = ConfigUtil.class.getClassLoader().getResourceAsStream(properName);
        Properties p = new Properties();
        try {
            p.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            logger.error("无法读取数据的配置文件", e);
        }
        return p;
    }
}
