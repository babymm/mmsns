package com.lovecws.mumu.mmsns.controller.profile.news;

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
public class MMSnsPortalProfileNewsController {

    @RequestMapping(value = "/{userName}/news", method = RequestMethod.GET)
    public String news(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/news/news";
    }

    @RequestMapping(value = "/{userName}/news/publish", method = RequestMethod.GET)
    public String publish(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/news/publish";
    }
}
