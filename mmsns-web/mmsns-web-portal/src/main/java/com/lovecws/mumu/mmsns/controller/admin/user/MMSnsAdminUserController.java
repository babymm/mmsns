package com.lovecws.mumu.mmsns.controller.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 我的私信
 * @date 2017-12-15 14:00:
 */
@Controller
@RequestMapping("/admin")
public class MMSnsAdminUserController {

    @RequestMapping(value = {"/{userName}/user/inbox"}, method = RequestMethod.GET)
    public String admin(@PathVariable String userName, HttpServletRequest request) {
        return "/admin/user/inbox";
    }

    @RequestMapping(value = {"/{userName}/user/msgs"}, method = RequestMethod.GET)
    public String msgs(@PathVariable String userName, HttpServletRequest request) {
        return "/admin/user/msgs";
    }

    @RequestMapping(value = {"/{userName}/user/chpwd"}, method = RequestMethod.GET)
    public String chpwd(@PathVariable String userName, HttpServletRequest request) {
        return "/admin/user/chpwds";
    }

    @RequestMapping(value = {"/{userName}/user/namespace"}, method = RequestMethod.GET)
    public String namespace(@PathVariable String userName, HttpServletRequest request) {
        return "/admin/user/namespace";
    }

    @RequestMapping(value = {"/{userName}/user/profile"}, method = RequestMethod.GET)
    public String profile(@PathVariable String userName, HttpServletRequest request) {
        return "/admin/user/profile";
    }

    @RequestMapping(value = {"/{userName}/user/chemail"}, method = RequestMethod.GET)
    public String chpemail(@PathVariable String userName, HttpServletRequest request) {
        return "/admin/user/chemail";
    }

    @RequestMapping(value = {"/{userName}/user/chphone"}, method = RequestMethod.GET)
    public String chphone(@PathVariable String userName, HttpServletRequest request) {
        return "/admin/user/chphone";
    }
}
