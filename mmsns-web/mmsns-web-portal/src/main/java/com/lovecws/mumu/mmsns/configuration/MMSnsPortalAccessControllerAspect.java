package com.lovecws.mumu.mmsns.configuration;

import com.lovecws.mumu.core.log.MumuLogComponent;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserService;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogRecord;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户访问profile下面的页面 必须这个用户要存在,并且将这个用户的个人信息保存起来，方便页面进行访问;
 * 用户访问admin下面的页面 必须要当前登录用户进行访问 如果其他用户访问 则跳转到当前用户首页
 * @date 2017-12-22 09:33
 */
@Aspect
@Component
public class MMSnsPortalAccessControllerAspect {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsCommonUserService commonUserService;

    public static final String VISIT_USERS_MAP = "VISIT_USERS_MAP";

    private static final Logger log = Logger.getLogger(MMSnsPortalLogComponent.class);

    @Pointcut("execution(* com.lovecws.mumu.mmsns.controller.profile.*.*Controller.*(..)) || " +
            "execution(* com.lovecws.mumu.mmsns.controller.admin.*.*Controller.*(..))")
    public void MMSnsPortalProfileAspect() {
    }

    @Around("MMSnsPortalProfileAspect()")
    public Object accessController(ProceedingJoinPoint joinPoint) throws Throwable {
        Method requestMethod = getMethod(joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs());
        String servletPath = request.getServletPath();
        String individuation = getIndividuation(servletPath);
        //判断该方法是 返回页面还是返回数据;如果是返回数据 则直接放行
        ResponseBody responseBody = requestMethod.getAnnotation(ResponseBody.class);
        if (responseBody != null) {
            Object proceed = joinPoint.proceed();
            //后台接口 修改了用户信息 需要重置缓存信息
            UserInfoUpdate userInfoUpdate = requestMethod.getAnnotation(UserInfoUpdate.class);
            if (userInfoUpdate != null && userInfoUpdate.required()) {
                MMSnsCommonUserEntity commonUserEntity = getCommonUser(individuation);
                MMSnsCommonUserEntity loginUser = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
                Map<String, MMSnsCommonUserEntity> visitMap = (Map<String, MMSnsCommonUserEntity>) request.getSession().getAttribute(VISIT_USERS_MAP);
                visitMap.put(loginUser.getIndividuation(), commonUserEntity);
                visitMap.put(individuation, commonUserEntity);
                request.getSession().setAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER, commonUserEntity);
                request.getSession().setAttribute(VISIT_USERS_MAP, visitMap);
            }
            return proceed;
        }

        if (servletPath.startsWith("/profile")) {
            return handleProfilePage(individuation, joinPoint);
        } else if (servletPath.startsWith("/admin")) {
            return handleAdminPage(individuation, joinPoint);
        }
        return null;
    }

    /**
     * 获取请求的方法
     *
     * @param clazz      字节码
     * @param methodName 方法名称
     * @param args       方法参数数组
     * @return
     */
    public Method getMethod(Class<? extends Object> clazz, String methodName, Object[] args) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            method.setAccessible(true);
            if (method.getName().equals(methodName) && method.getParameters().length == args.length) {
                return method;
            }
        }
        return null;
    }

    /**
     * 获取空间 个性化名称
     *
     * @param servletPath
     * @return
     */
    private String getIndividuation(String servletPath) {
        int beginIndex = servletPath.indexOf("/", 1);
        int endIndex = servletPath.indexOf("/", beginIndex + 1);
        if (endIndex >= 0) {
            return servletPath.substring(beginIndex + 1, endIndex);
        }
        return servletPath.substring(beginIndex + 1);
    }

    private MMSnsCommonUserEntity getCommonUser(String individuation) {
        List<MMSnsCommonUserEntity> commonUsers = commonUserService.getCommonUserByCondition(null, null, null, individuation, null);
        if (commonUsers != null && commonUsers.size() > 0) {
            return commonUsers.get(0);
        }
        return null;
    }

    /**
     * 处理profile下面的页面
     *
     * @param individuation 个性化名称
     * @param joinPoint
     * @return
     */
    private Object handleProfilePage(String individuation, ProceedingJoinPoint joinPoint) throws Throwable {
        if (individuation == null) {
            return "/common/error/404";
        }
        //缓存用户访问的用户列表
        Map<String, MMSnsCommonUserEntity> visitMap = (Map<String, MMSnsCommonUserEntity>) request.getSession().getAttribute(VISIT_USERS_MAP);
        //初始化的时候 将登陆用户添加进去
        if (visitMap == null) {
            MMSnsCommonUserEntity loginUser = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
            visitMap = new HashMap<String, MMSnsCommonUserEntity>();
            visitMap.put(loginUser.getIndividuation(), loginUser);
        }
        MMSnsCommonUserEntity commonUserEntity = visitMap.get(individuation);
        if (commonUserEntity == null) {
            commonUserEntity = getCommonUser(individuation);
        }
        visitMap.put(individuation, commonUserEntity);
        request.getSession().setAttribute(VISIT_USERS_MAP, visitMap);
        request.getSession().setAttribute(MMSnsCommonUserEntity.VISIT_USER, commonUserEntity);
        //如果用户不存在 则跳转到404页面
        if (commonUserEntity == null) {
            return "/common/error/404";
        }
        return joinPoint.proceed();
    }

    /**
     * 处理admin下面的页面
     *
     * @param individuation 个性化名称
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    private Object handleAdminPage(String individuation, ProceedingJoinPoint joinPoint) throws Throwable {
        MMSnsCommonUserEntity loginUser = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        if (individuation == null) {
            return "redirect:/profile/" + loginUser.getIndividuation() + "/home";
        }
        if (!individuation.equals(loginUser.getIndividuation())) {
            return "redirect:/profile/" + loginUser.getIndividuation() + "/home";
        }
        return joinPoint.proceed();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface UserInfoUpdate {
        String name() default "";

        boolean required() default true;
    }
}
