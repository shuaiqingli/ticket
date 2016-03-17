package com.hengyu.ticket.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置项工具类
 *
 * @author wwang
 */
public class ConfigUtil {
    private static Logger logger = Logger.getLogger(ConfigUtil.class);
    private static Properties properties = null;

    public static Object get(Object key) {
        if (null == properties) {
            init();
        }
        return properties.get(key);
    }

    public static void set(String key, String value) {
        if (null == properties) {
            init();
        }
        properties.setProperty(key, value);
    }

    private synchronized static void init() {
        if (null == properties || properties.isEmpty()) {
            properties = new Properties();
            try {
                properties.load(ConfigUtil.class.getResourceAsStream("/config.properties"));
            } catch (IOException e) {
                logger.error("加载配置文件失败", e);
            }
        }
    }
}
