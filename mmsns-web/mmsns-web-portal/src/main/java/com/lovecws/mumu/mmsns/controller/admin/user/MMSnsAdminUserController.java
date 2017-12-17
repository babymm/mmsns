package com.lovecws.mumu.mmsns.controller.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户设置 用户信息修改
 * @date 2017-12-15 14:00:
 */
@Controller
@RequestMapping("/admin")
public class MMSnsAdminUserController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = {"/{userName}/user/inbox"}, method = RequestMethod.GET)
    public String userInbox(@PathVariable String userName) {
        request.setAttribute("adminModular", "userInbox");
        return "/admin/user/inbox";
    }

    @RequestMapping(value = {"/{userName}/user/msgs"}, method = RequestMethod.GET)
    public String userInboxMsgs(@PathVariable String userName) {
        request.setAttribute("adminModular", "userInbox");
        return "/admin/user/msgs";
    }

    @RequestMapping(value = {"/{userName}/user/chpwd"}, method = RequestMethod.GET)
    public String userChpwd(@PathVariable String userName) {
        request.setAttribute("adminModular", "userChpwd");
        return "/admin/user/chpwds";
    }

    @RequestMapping(value = {"/{userName}/user/namespace"}, method = RequestMethod.GET)
    public String userNamespace(@PathVariable String userName) {
        request.setAttribute("adminModular", "userNamespace");
        return "/admin/user/namespace";
    }

    @RequestMapping(value = {"/{userName}/user/profile"}, method = RequestMethod.GET)
    public String userProfile(@PathVariable String userName) {
        request.setAttribute("adminModular", "userProfile");
        return "/admin/user/profile";
    }

    @RequestMapping(value = {"/{userName}/user/chemail"}, method = RequestMethod.GET)
    public String userChemail(@PathVariable String userName) {
        request.setAttribute("adminModular", "userChemail");
        return "/admin/user/chemail";
    }

    @RequestMapping(value = {"/{userName}/user/chphone"}, method = RequestMethod.GET)
    public String userChphone(@PathVariable String userName) {
        request.setAttribute("adminModular", "userChphone");
        return "/admin/user/chphone";
    }
}
