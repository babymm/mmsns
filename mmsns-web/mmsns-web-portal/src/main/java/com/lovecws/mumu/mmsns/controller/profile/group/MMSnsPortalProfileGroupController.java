package com.lovecws.mumu.mmsns.controller.profile.group;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 新闻资讯主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/profile")
public class MMSnsPortalProfileGroupController {

    @RequestMapping(value = "/{userName}/group", method = RequestMethod.GET)
    public String group(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/group/group";
    }

    @RequestMapping(value = "/{userName}/group/create", method = RequestMethod.GET)
    public String create(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/group/create";
    }
}
