package com.lovecws.mumu.mmsns.manager.configuration;

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
 * @Description: mumu日志拦截
 * @date 2017-11-27 10:09
 */
@Component("MMSnsManagerConfiguration")
@Aspect
public class MMSnsManagerConfiguration extends MumuLogComponent {

    private static final Logger log = Logger.getLogger(MMSnsManagerConfiguration.class);

    @Pointcut("execution(* com.lovecws.mumu.mmsns.manager.controller.*.*Controller.*(..))")
    public void MMSnsManagerConfiguration() {

    }

    @Around("MMSnsManagerConfiguration()")
    public Object ElebillManagerConfiguration(ProceedingJoinPoint joinPoint) throws Throwable {
        return log(joinPoint, false);
    }

    @Override
    public void record(final MumuLogEntity mumuLogEntity) {
        log.info(JSON.toJSONString(mumuLogEntity));
    }
}
