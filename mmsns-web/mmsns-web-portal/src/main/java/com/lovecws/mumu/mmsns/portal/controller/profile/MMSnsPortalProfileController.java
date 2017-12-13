package com.lovecws.mumu.mmsns.portal.controller.profile;

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
public class MMSnsPortalProfileController {

    @RequestMapping(value = "/{userName}/home",method = RequestMethod.GET)
    public String home(@PathVariable String userName,HttpServletRequest request) {
        return "/profile/home";
    }

    @RequestMapping(value = "/{userName}/article", method = RequestMethod.GET)
    public String article(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/article/article";
    }

    @RequestMapping(value = "/{userName}/action", method = RequestMethod.GET)
    public String action(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/action/action";
    }

    @RequestMapping(value = "/{userName}/group", method = RequestMethod.GET)
    public String group(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/group/group";
    }

    @RequestMapping(value = "/{userName}/news", method = RequestMethod.GET)
    public String news(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/news/news";
    }

    @RequestMapping(value = "/{userName}/photo", method = RequestMethod.GET)
    public String photo(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/photo/photo";
    }

    @RequestMapping(value = "/{userName}/doc", method = RequestMethod.GET)
    public String doc(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/doc/doc";
    }
}
