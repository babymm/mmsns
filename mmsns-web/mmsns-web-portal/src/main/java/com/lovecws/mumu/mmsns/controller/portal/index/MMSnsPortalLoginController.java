package com.lovecws.mumu.mmsns.controller.portal.index;

import com.alibaba.fastjson.JSON;
import com.lovecws.mumu.core.response.HttpCode;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.security.exception.AccountUnActiveException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public String login(HttpServletRequest request, HttpServletResponse response) {
        //如果用户已经登录了 则重定向到用户首页
        boolean authenticated = SecurityUtils.getSubject().isAuthenticated();
        if(authenticated){
            MMSnsCommonUserEntity commonUserEntity = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
            if (commonUserEntity != null) {
                return "redirect:/profile/" + commonUserEntity.getIndividuation() + "/home";
            }
        }
        String requestedWith = request.getHeader("X-Requested-With");
        //如果是ajax异步请求*
        if (requestedWith != null && requestedWith.equals("XMLHttpRequest")) {
            sendError(response, new ResponseEntity(444, "not login", "用户未登录"));
            return null;
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

    public void sendError(HttpServletResponse response, ResponseEntity responseEntity) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            String jsonString = JSON.toJSONString(responseEntity);
            response.getOutputStream().write(jsonString.getBytes("utf-8"));
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
