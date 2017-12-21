package com.lovecws.mumu.mmsns.common;

import com.lovecws.mumu.mmsns.common.ddl.service.MMSnsCommonDDLProvinceService;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.BasicRefererInterfaceConfig;
import com.weibo.api.motan.config.ProtocolConfig;
import com.weibo.api.motan.config.RefererConfig;
import com.weibo.api.motan.config.RegistryConfig;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: rpc客户端调用
 * @date 2017-12-21 10:32:
 */
public class MMSnsRPCClient {

    public static void main(String[] args) {
        MMSnsCommonDDLProvinceService provinceService = getService(MMSnsCommonDDLProvinceService.class);
        System.out.println(provinceService.getAllProvinces());
    }

    public static <T> T getService(Class<T> clazz) {
        //protocol
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setDefault(true);
        protocolConfig.setId("motanProtocol");
        protocolConfig.setName("motan");

        //registry
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("192.168.11.25:2181");
        registryConfig.setRegProtocol("zookeeper");
        registryConfig.setName("zookeeperRegistry");

        //basicReferer
        RefererConfig<T> refererConfig = new RefererConfig<T>();
        refererConfig.setId("motanBasicRefererConfig");
        refererConfig.setGroup("mmsns");
        refererConfig.setModule("mmsns-common");
        refererConfig.setProtocol(protocolConfig);
        refererConfig.setRegistry(registryConfig);
        refererConfig.setShareChannel(true);
        refererConfig.setInterface(clazz);
        return refererConfig.getRef();
    }
}
