package com.lovecws.mumu.mmsns.modular.recommend.config;

import com.lovecws.mumu.mmsns.modular.recommend.task.SqoopJobTask;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 配置文件
 * @date 2018-01-18 12:56
 */
public class PropertyConfig {
    private static final Logger log = Logger.getLogger(SqoopJobTask.class);

    private static final String SQOOP_PROPERTY = "sqoop.properties";
    private static final Map<String, Properties> PROPERTIESMAP = new HashMap<String, Properties>();

    private String propertyName;

    public PropertyConfig() {
        this.propertyName = SQOOP_PROPERTY;
    }

    public PropertyConfig(final String propertyName) {
        this.propertyName = propertyName;
    }

    public synchronized Properties properties() {
        Properties properties = PROPERTIESMAP.get(propertyName);
        if (properties == null) {
            try {
                properties = new Properties();
                properties.load(RecommendConfig.class.getClassLoader().getResourceAsStream(propertyName));
                Set<String> propertyNames = properties.stringPropertyNames();
                log.info("解析配置文件");
                for (String propertyName : propertyNames) {
                    log.info(propertyName + " : " + properties.getProperty(propertyName));
                }
                PROPERTIESMAP.put(propertyName, properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public String getProperty(String propertyKey) {
        Properties properties = properties();
        String property = properties.getProperty(propertyKey);
        return property;
    }
}
