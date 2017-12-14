package com.lovecws.mumu.mmsns.configuration;

import com.alibaba.fastjson.JSON;
import com.lovecws.mumu.core.log.MumuLogComponent;
import com.lovecws.mumu.core.log.MumuLogEntity;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 日志拦截器
 * @date 2017-12-08 21:50
 */
@Aspect
@Component
public class MMSnsPortalLogComponent extends MumuLogComponent {

    private static final Logger log = Logger.getLogger(MMSnsPortalLogComponent.class);

    @Pointcut("execution(* com.lovecws.mumu.mmsns.controller.*.*.*Controller.*(..))")
    public void MMSnsPortalLogComponent() {
    }

    @Override
    public void record(MumuLogEntity mumuLogEntity) {
        log.info(JSON.toJSONString(mumuLogEntity));
    }

    @Around("MMSnsPortalLogComponent()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        return log(joinPoint, false);
    }
}
