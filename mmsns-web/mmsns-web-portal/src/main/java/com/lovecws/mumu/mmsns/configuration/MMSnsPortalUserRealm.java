package com.lovecws.mumu.mmsns.configuration;

import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.utils.StringUtil;
import com.lovecws.mumu.core.utils.ValidateUtils;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserService;
import com.lovecws.mumu.security.exception.AccountUnActiveException;
import com.lovecws.mumu.security.realm.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 官网用户认证
 * @date 2017-11-28 10:17
 */
public class MMSnsPortalUserRealm extends UserRealm {

    @Autowired(required = false)
    private MMSnsCommonUserService commonUserService;

    /**
     * 校验用户登录权限
     *
     * @param principals
     * @return
     */
    @Override
    public AuthorizationInfo authorizationInfo(final PrincipalCollection principals) {
        System.out.println(principals);
        return null;
    }

    /**
     * 校验登录用户
     */
    @Override
    public AuthenticationInfo authenticationInfo(final AuthenticationToken token) {
        String loginName = (String) token.getPrincipal();
        if (StringUtil.isEmpty(loginName)) {
            throw new UnknownAccountException();// 没找到帐号
        }
        //根据用户登录信息[用户名、手机号码、用户邮箱] 来获取用户信息
        String userName = null, userPhone = null, userEmail = null;
        if (ValidateUtils.isMobile(loginName)) {
            userPhone = loginName;
        } else if (ValidateUtils.isEmail(loginName)) {
            userEmail = loginName;
        } else {
            userName = loginName;
        }

        List<MMSnsCommonUserEntity> users = commonUserService.getCommonUserByCondition(userName, userPhone, userEmail, null,null);
        //用户不存在
        if (users == null || users.size() == 0 || users.size() > 1) {
            throw new UnknownAccountException();
        }
        //校验 账户状态信息
        MMSnsCommonUserEntity commonUserEntity = users.get(0);
        if (commonUserEntity.getUserStatus().equals(MMSnsCommonUserEntity.USER_STATUS_FORBIT)) {
            throw new LockedAccountException();
        } else if (commonUserEntity.getUserStatus().equals(MMSnsCommonUserEntity.USER_STATUS_DELETE)) {
            throw new DisabledAccountException();
        } else if (commonUserEntity.getUserStatus().equals(MMSnsCommonUserEntity.USER_STATUS_UNACTIVE)) {
            throw new AccountUnActiveException();
        }
        //将用户信息保存到session中
        SecurityUtils.getSubject().getSession().setAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER, commonUserEntity);
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(commonUserEntity.getUserName(), commonUserEntity.getUserPassword(), ByteSource.Util.bytes(commonUserEntity.getUserName() + commonUserEntity.getUserSalt()), getName());
        return authenticationInfo;
    }
}
