package com.lovecws.mumu.mmsns.controller.portal.article;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/article")
public class MMSnsPortalArticleController {

    @RequestMapping(method = RequestMethod.GET)
    public String article(HttpServletRequest request) {
        request.setAttribute("mainModular", "article");
        return "/portal/article/article";
    }

    @RequestMapping(value = "/detail/{articleId}", method = RequestMethod.GET)
    public String detail(@PathVariable int articleId, HttpServletRequest request) {
        request.setAttribute("mainModular", "article");
        return "/portal/article/detail";
    }
}
