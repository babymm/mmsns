package com.lovecws.mumu.mmsns.common;

import com.lovecws.mumu.mmsns.common.ddl.service.MMSnsCommonDDLProvinceService;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: rpc客户端调用
 * @date 2017-12-21 10:32:
 */
public class MMSnsRPCSpringClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/spring-motan-client.xml");
        applicationContext.start();

        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        MMSnsCommonDDLProvinceService provinceService = applicationContext.getBean(MMSnsCommonDDLProvinceService.class);
        System.out.println(provinceService.getAllProvinces());
    }
}
