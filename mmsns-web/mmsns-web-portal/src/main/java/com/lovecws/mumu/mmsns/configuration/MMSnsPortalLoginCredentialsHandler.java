package com.lovecws.mumu.mmsns.configuration;

import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserService;
import com.lovecws.mumu.security.credentials.LoginCredentialsHandler;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户扥估成功之后修改用户登录信息
 * @date 2017-12-22 10:27:
 */
@Component
public class MMSnsPortalLoginCredentialsHandler implements LoginCredentialsHandler {

    @Autowired(required = false)
    private MMSnsCommonUserService commonUserService;

    @Override
    public void before() {

    }

    @Override
    public void after() {
        MMSnsCommonUserEntity commonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        MMSnsCommonUserEntity loginUser = new MMSnsCommonUserEntity();
        loginUser.setUserId(commonUser.getUserId());
        loginUser.setLastLoginTime(new Date());
        loginUser.setLastLoginIp(SecurityUtils.getSubject().getSession().getHost());
        commonUserService.updateCommonUser(loginUser);

        commonUser.setLastLoginTime(loginUser.getLastLoginTime());
        commonUser.setLastLoginIp(loginUser.getLastLoginIp());
        SecurityUtils.getSubject().getSession().setAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER, commonUser);
    }
}
