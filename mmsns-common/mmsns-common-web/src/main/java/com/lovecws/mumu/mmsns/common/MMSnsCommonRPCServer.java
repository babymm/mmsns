package com.lovecws.mumu.mmsns.common;

import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLProvinceEntity;
import com.lovecws.mumu.mmsns.common.ddl.service.MMSnsCommonDDLProvinceService;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 开启服务
 * @date 2017-12-21 09:31:
 */
public class MMSnsCommonRPCServer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/spring-context.xml");
        applicationContext.start();
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }
}
