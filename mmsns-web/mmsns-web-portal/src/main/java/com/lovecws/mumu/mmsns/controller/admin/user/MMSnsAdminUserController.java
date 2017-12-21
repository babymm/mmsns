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

    @RequestMapping(value = {"/{individuation}/user/inbox"}, method = RequestMethod.GET)
    public String userInbox(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userInbox");
        return "/admin/user/inbox";
    }

    @RequestMapping(value = {"/{individuation}/user/msgs"}, method = RequestMethod.GET)
    public String userInboxMsgs(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userInbox");
        return "/admin/user/msgs";
    }

    @RequestMapping(value = {"/{individuation}/user/chpwd"}, method = RequestMethod.GET)
    public String userChpwd(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userChpwd");
        return "/admin/user/chpwds";
    }

    @RequestMapping(value = {"/{individuation}/user/namespace"}, method = RequestMethod.GET)
    public String individuationspace(@PathVariable String individuation) {
        request.setAttribute("adminModular", "individuationspace");
        return "/admin/user/namespace";
    }

    @RequestMapping(value = {"/{individuation}/user/profile"}, method = RequestMethod.GET)
    public String userProfile(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userProfile");
        return "/admin/user/profile";
    }

    @RequestMapping(value = {"/{individuation}/user/chemail"}, method = RequestMethod.GET)
    public String userChemail(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userChemail");
        return "/admin/user/chemail";
    }

    @RequestMapping(value = {"/{individuation}/user/chphone"}, method = RequestMethod.GET)
    public String userChphone(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userChphone");
        return "/admin/user/chphone";
    }
}
