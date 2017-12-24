package com.lovecws.mumu.mmsns.controller.portal.article;

import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired(required = false)
    private MMSnsArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public String article(HttpServletRequest request) {
        request.setAttribute("mainModular", "article");
        return "/portal/article/article";
    }

    @RequestMapping(value = "/detail/{articleId}", method = RequestMethod.GET)
    public String detail(@PathVariable int articleId, HttpServletRequest request) {
        request.setAttribute("mainModular", "article");
        //根据文章id获取文章详情
        MMSnsArticleEntity articleInfo = articleService.getArticleInfo(String.valueOf(articleId));
        request.setAttribute("article", articleInfo);
        return "/portal/article/detail";
    }
}
