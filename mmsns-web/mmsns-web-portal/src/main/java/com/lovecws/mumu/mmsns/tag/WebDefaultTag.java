package com.lovecws.mumu.mmsns.tag;

import com.lovecws.mumu.core.utils.PropertiesLoader;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 服务器上一些默认的设置
 * @date 2018-01-05 16:15:
 */
@DefaultKey("default")
@ValidScope(Scope.APPLICATION)
public class WebDefaultTag {

    PropertiesLoader propertiesLoader = new PropertiesLoader("properties/default.properties");

    /**
     * 获取用户的头像
     *
     * @param userAvator
     * @return
     */
    public String avator(String userAvator) {
        return userAvator != null ? userAvator : propertiesLoader.getProperty("default.useravator");
    }
}
