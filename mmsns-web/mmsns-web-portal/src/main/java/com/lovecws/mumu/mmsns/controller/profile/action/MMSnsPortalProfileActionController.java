package com.lovecws.mumu.mmsns.controller.profile.action;

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
public class MMSnsPortalProfileActionController {

    @RequestMapping(value = "/{userName}/action", method = RequestMethod.GET)
    public String action(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/action/action";
    }

    @RequestMapping(value = "/{userName}/action/publish", method = RequestMethod.GET)
    public String publish(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/action/publish";
    }
}
