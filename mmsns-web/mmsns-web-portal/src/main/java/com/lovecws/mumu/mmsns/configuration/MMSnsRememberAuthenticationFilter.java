package com.lovecws.mumu.mmsns.configuration;

import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ganliang
 * @version 2016年8月29日 上午11:01:00
 * @desc 记住密码 自动登录
 * about:preferences#privacy
 */
public class MMSnsRememberAuthenticationFilter extends UserFilter {

    @Autowired
    private MMSnsCommonUserService commonUserService;

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 是登录页面 则直接返回true
        if (isLoginRequest(request, response)) {
            return true;
        }
        // 获取主题
        Subject subject = getSubject(request, response);
        //如果用户【已认证】 直接跳过
        if (subject.isAuthenticated()) {
            return true;
        }
        // 如果用户【未认证】 但是用户使用【记住我】功能
        if (subject.isRemembered()) {
            // 获取rememberMe的用户名称
            Object principal = subject.getPrincipal();
            if (null != principal) {
                //从session中 获取登录用户信息
                Session session = subject.getSession(true);
                MMSnsCommonUserEntity commonUser = (MMSnsCommonUserEntity) session.getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
                //如果session不保存用户信息；则从数据库中获取
                if (commonUser == null) {
                    List<MMSnsCommonUserEntity> commonUsers = commonUserService.getCommonUserByCondition(principal.toString(), principal.toString(),principal.toString(),null);
                    if (commonUser != null && commonUsers.size() > 0) {
                        session.setAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER, commonUsers.get(0));
                    }
                }
                return true;
            }
        }
        return false;
    }
}