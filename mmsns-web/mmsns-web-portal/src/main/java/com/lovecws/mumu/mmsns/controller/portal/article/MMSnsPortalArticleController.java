package com.lovecws.mumu.mmsns.controller.portal.article;

import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.utils.DateUtils;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCategoryEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCategoryService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import com.lovecws.mumu.mmsns.modular.recommend.entity.RecommendTypeEnum;
import com.lovecws.mumu.mmsns.modular.recommend.service.ModularRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
    @Autowired(required = false)
    private MMSnsArticleCategoryService articleCategoryService;
    @Autowired(required = false)
    private ModularRecommendService recommendService;

    /**
     * 进入到文章列表页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String goArticleListPage(String sysCategoryId, String articleType) {
        request.setAttribute("mainModular", "article");
        request.setAttribute("sysCategoryId", sysCategoryId);
        //精彩文章 附带图片
        List<MMSnsArticleEntity> articles = articleService.selectArticlePage(null, null, sysCategoryId, null, "read_count", true, 1, 4);
        request.setAttribute("articles", articles);
        //文章系统分类
        List<MMSnsArticleCategoryEntity> articleCategorys = articleCategoryService.getArticleCategoryList(MMSnsArticleCategoryEntity.ARTICLE_CATEGORY_TYPE_SYSTEM, null, null);
        MMSnsArticleCategoryEntity allArticleCategory = new MMSnsArticleCategoryEntity();
        allArticleCategory.setCategoryId(0);
        allArticleCategory.setCategoryName("全部");
        allArticleCategory.setCategoryIcon("fa fa-bars");
        articleCategorys.add(0, allArticleCategory);
        request.setAttribute("articleCategorys", articleCategorys);
        //最受欢迎 的文章作者
        List<MMSnsArticleEntity> popularArticleAuthors = articleService.getPopularArticleAuthorMessage(1, 5);
        request.setAttribute("popularArticleAuthors", popularArticleAuthors);
        //推荐用户 TODO[准备使用大数据相关内容 进行作者推荐] 1、使用sqoop将文章信息导入到hadoop大数据。2、将用户点击的文章信息保存到redis缓存中。3、使用hive或者mahout做响应的推荐。
        //List<MMSnsArticleEntity> recommendArticleAuthors = articleService.getRecommendArticleAuthorMessage(1, 5);
        //request.setAttribute("recommendArticleAuthors", recommendArticleAuthors);
        return "/portal/article/article";
    }

    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity articleData(String articleType, String sysCategoryId,
                                      @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(name = "limit", required = false, defaultValue = "5") int limit) {
        String orderby = "read_count", startDate = null, endDate = null;
        if ("articleRecommend".equalsIgnoreCase(articleType)) {
            orderby = "read_count";
        } else if ("articleToday".equalsIgnoreCase(articleType)) {
            orderby = "read_count";
            startDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
            endDate = DateUtils.formatDate(DateUtils.addDay(new Date(), 1), "yyyy-MM-dd");
        } else if ("articleWeek".equalsIgnoreCase(articleType)) {
            orderby = "read_count";
            startDate = DateUtils.formatDate(DateUtils.getWeekStart(new Date()), "yyyy-MM-dd");
            endDate = DateUtils.formatDate(DateUtils.getWeekEnd(new Date()), "yyyy-MM-dd");
        } else if ("articleVote".equalsIgnoreCase(articleType)) {
            orderby = "vote_count";
        } else if ("articleNewst".equalsIgnoreCase(articleType)) {
            orderby = "article_date";
        }
        List<MMSnsArticleEntity> articles = articleService.selectArticlePageWithAuthorMessage(null, sysCategoryId, orderby, false, startDate, endDate, page, limit);
        return new ResponseEntity(articles);
    }

    /**
     * 进入到文章详情页面
     *
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/detail/{articleId}", method = RequestMethod.GET)
    public String goArticleDetailPage(@PathVariable int articleId) {
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

        //相关文章 文章作者发布的其他文章
        List<MMSnsArticleEntity> relationArticles = articleService.selectArticlePage(String.valueOf(articleInfo.getUserId()), null, null, null, null, false, 1, 5);
        request.setAttribute("relationArticles", relationArticles);
        //热门文章
        List<MMSnsArticleEntity> hotArticles = articleService.selectArticlePage(null, null, null, null, "read_count", false, 1, 5);
        request.setAttribute("hotArticles", hotArticles);

        //最新推荐 TODO
        long[] recommendArticleIds = recommendService.recommend(RecommendTypeEnum.ARTICLE, articleId, 5f);
        List<MMSnsArticleEntity> recommendArticles = articleService.getBatchArticleById(recommendArticleIds);
        request.setAttribute("newsArticles", recommendArticles);
        return "/portal/article/detail";
    }
}
