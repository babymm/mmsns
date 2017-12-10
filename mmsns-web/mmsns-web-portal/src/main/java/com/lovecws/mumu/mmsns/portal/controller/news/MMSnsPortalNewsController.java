package com.lovecws.mumu.mmsns.portal.controller.news;

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
@RequestMapping("/news")
public class MMSnsPortalNewsController {

    @RequestMapping(method = RequestMethod.GET)
    public String news(HttpServletRequest request) {
        request.setAttribute("mainModular", "news");
        return "/portal/news/news";
    }

    @RequestMapping(value = "/detail/{newsId}", method = RequestMethod.GET)
    public String detail(@PathVariable int groupId, HttpServletRequest request) {
        request.setAttribute("mainModular", "news");
        return "/portal/news/detail";
    }
}
