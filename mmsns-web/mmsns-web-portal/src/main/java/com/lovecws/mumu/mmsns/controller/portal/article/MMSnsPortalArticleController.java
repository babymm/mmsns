package com.lovecws.mumu.mmsns.controller.portal.article;

import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/article")
public class MMSnsPortalArticleController {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public String article(HttpServletRequest request) {
        request.setAttribute("mainModular", "article");
        return "/portal/article/article";
    }

    @RequestMapping(value = "/detail/{articleId}", method = RequestMethod.GET)
    public String detail(@PathVariable int articleId) {
        request.setAttribute("mainModular", "article");

        //根据文章id获取文章详情
        MMSnsArticleEntity articleInfo = articleService.getArticleInfo(String.valueOf(articleId));
        if (articleInfo == null) {
            return "common/error/404";
        }

        //文章阅读量+1
        MMSnsArticleEntity readArticle = new MMSnsArticleEntity();
        readArticle.setArticleId(articleInfo.getArticleId());
        readArticle.setReadCount(1);
        articleService.updateArticle(readArticle);

        articleInfo.setReadCount(articleInfo.getReadCount() + 1);
        request.setAttribute("article", articleInfo);

        //获取文章统计信息
        List<MMSnsArticleEntity> mmSnsArticleEntities = articleService.groupArticleCountByUserId(String.valueOf(articleInfo.getUserId()));
        int articleCount = 0, articleWordCount = 0;
        for (MMSnsArticleEntity articleEntity : mmSnsArticleEntities) {
            if (MMSnsArticleEntity.ARTICLE_TYPE_ORIGINAL.equals(articleEntity.getArticleType())) {
                articleCount = articleEntity.getArticleCount();
                articleWordCount = articleEntity.getWordCount();
                break;
            }
        }
        request.setAttribute("articleCount", articleCount);
        request.setAttribute("articleWordCount", articleWordCount);

        //相关文章
        //热门文章
        //最新文章
        return "/portal/article/detail";
    }
}
