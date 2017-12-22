package com.lovecws.mumu.mmsns.controller.portal.index;

import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.security.exception.AccountUnActiveException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7/007.
 * 用户登录
 */
@Controller
@RequestMapping
public class MMSnsPortalLoginController {

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        //如果用户已经登录了 则重定向到用户首页
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            MMSnsCommonUserEntity commonUserEntity = (MMSnsCommonUserEntity) subject.getSession(true).getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
            if (commonUserEntity != null) {
                return "redirect:/profile/" + commonUserEntity.getIndividuation() + "/home";
            }
        }
        return "/portal/login";
    }

    /**
     * 用户登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ModelAndView logining(String username, String password, RedirectAttributes model, HttpServletRequest request) {
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "输入错误次数太过，请稍后重试";
        } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
            error = "账户被锁定，请联系管理员";
        } else if (DisabledAccountException.class.getName().equals(exceptionClassName)) {
            error = "账户被删除，请联系管理员";
        } else if (AccountUnActiveException.class.getName().equals(exceptionClassName)) {
            error = "账户未激活，请登录邮箱激活账号！";
        } else if (AuthenticationException.class.getName().equals(exceptionClassName)) {
            error = "未知错误!";
        } else if (exceptionClassName != null) {
            error = "错误提示：" + exceptionClassName;
        }
        if (error != null) {
            model.addFlashAttribute("shiroLoginFailure", error);
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/index");
    }
}
