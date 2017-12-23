package com.lovecws.mumu.mmsns.controller.profile.article;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.utils.FilterUtil;
import com.lovecws.mumu.core.utils.ValidateUtils;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCategoryEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCategoryService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCollectService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleVoteService;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 新闻资讯主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/profile")
public class MMSnsPortalProfileArticleController {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsArticleCategoryService articleCategoryService;
    @Autowired(required = false)
    private MMSnsArticleService articleService;
    @Autowired(required = false)
    private MMSnsArticleVoteService articleVoteService;
    @Autowired(required = false)
    private MMSnsArticleCollectService articleCollectService;

    public void initArticleCenter(String sessionUserId) {
        //获取统计信息 我发布的文章的数量、我转发的文章的数量
        List<MMSnsArticleEntity> articleCounts = articleService.groupArticleCountByUserId(sessionUserId);
        int originalArticleCount = 0, reprintArticleCount = 0, voteArticleCount = 0, collectArticleCount = 0;
        for (MMSnsArticleEntity articleEntity : articleCounts) {
            if (articleEntity.getArticleType().equals(MMSnsArticleEntity.ARTICLE_TYPE_ORIGINAL)) {
                originalArticleCount = articleEntity.getArticleCount();
            } else if (articleEntity.getArticleType().equals(MMSnsArticleEntity.ARTICLE_TYPE_REPRINT)) {
                reprintArticleCount = articleEntity.getArticleCount();
            }
        }
        //我点赞的文章数量、我收藏的文章数量 文章分类下的文章数量
        voteArticleCount = articleVoteService.getVoteArticleCountByCondition(sessionUserId);
        collectArticleCount = articleCollectService.getCollectArticleCountByCondition(sessionUserId);
        //获取文章分类
        List<MMSnsArticleEntity> articleCategorys = articleService.groupArticleCountByCategory(sessionUserId, MMSnsArticleEntity.ARTICLE_TYPE_ORIGINAL);

        request.setAttribute("originalArticleCount", originalArticleCount);
        request.setAttribute("reprintArticleCount", reprintArticleCount);
        request.setAttribute("voteArticleCount", voteArticleCount);
        request.setAttribute("collectArticleCount", collectArticleCount);
        request.setAttribute("articleCategorys", articleCategorys);
    }

    @RequestMapping(value = {"/{individuation}/article", "/{individuation}/article/{operation}"}, method = RequestMethod.GET)
    public String article(@PathVariable(name = "individuation", required = true) String individuation,
                          @PathVariable(name = "operation", required = false) String operation,
                          @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(name = "limit", required = false, defaultValue = "5") int limit) {
        MMSnsCommonUserEntity commonUserEntity = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.VISIT_USER);
        String sessionUserId = String.valueOf(commonUserEntity.getUserId());
        initArticleCenter(sessionUserId);

        PageBean pageBean = null;
        if (ValidateUtils.isEmpty(operation)) {
            pageBean = articleService.listArticlePage(sessionUserId, null, null, page, limit);
        } else if (MMSnsArticleEntity.ARTICLE_TYPE_ORIGINAL.equals(operation)) {
            pageBean = articleService.listArticlePage(sessionUserId, MMSnsArticleEntity.ARTICLE_TYPE_ORIGINAL, null, page, limit);
        } else if (MMSnsArticleEntity.ARTICLE_TYPE_REPRINT.equals(operation)) {
            pageBean = articleService.listArticlePage(sessionUserId, MMSnsArticleEntity.ARTICLE_TYPE_REPRINT, null, page, limit);
        } else if ("vote".equals(operation)) {
            pageBean = articleVoteService.listVoteArticlePage(sessionUserId, page, limit);
        } else if ("collect".equals(operation)) {
            pageBean = articleCollectService.listCollectArticlePage(sessionUserId, page, limit);
        } else {
            pageBean = articleService.listArticlePage(sessionUserId, MMSnsArticleEntity.ARTICLE_TYPE_ORIGINAL, operation, page, limit);
        }
        request.setAttribute("articlePageBean", pageBean);
        return "/profile/article/article";
    }

    /**
     * 跳转到[创建|编辑]文章页面
     *
     * @param individuation
     * @param articleId     文章id
     * @return
     */
    @RequestMapping(value = {"/{individuation}/article/create", "/{individuation}/article/edit/{articleId}"}, method = RequestMethod.GET)
    public String goCreateArticlePage(@PathVariable String individuation,
                                      @PathVariable(name = "articleId", required = false) String articleId) {
        MMSnsCommonUserEntity commonUserEntity = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.VISIT_USER);
        String sessionUserId = String.valueOf(commonUserEntity.getUserId());
        initArticleCenter(sessionUserId);

        //获取用户文章分类
        List<MMSnsArticleCategoryEntity> userArticleCategorys = articleCategoryService.getArticleCategoryList(MMSnsArticleCategoryEntity.ARTICLE_CATEGORY_TYPE_USER, String.valueOf(commonUserEntity.getUserId()), null);
        request.setAttribute("userArticleCategorys", userArticleCategorys);
        //获取文章系统分类
        List<MMSnsArticleCategoryEntity> systemArticleCategorys = articleCategoryService.getArticleCategoryList(MMSnsArticleCategoryEntity.ARTICLE_CATEGORY_TYPE_SYSTEM, null, null);
        request.setAttribute("systemArticleCategorys", systemArticleCategorys);

        String pageArticleTitle = "", pageArticleType = "";
        //文章创建
        if (articleId == null) {
            pageArticleTitle = "编写文章";
            pageArticleType = "create";
        }
        //文章编辑
        else {
            pageArticleTitle = "编辑文章";
            pageArticleType = "edit";
            MMSnsArticleEntity articleEntity = articleService.getArticleInfo(articleId);
            request.setAttribute("articleEntity", articleEntity);
        }
        request.setAttribute("pageArticleTitle", pageArticleTitle);
        request.setAttribute("pageArticleType", pageArticleType);
        return "/profile/article/create";
    }

    /**
     * 添加文章
     *
     * @param individuation
     * @param articleEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{individuation}/article/create", method = RequestMethod.POST)
    public ResponseEntity addArticle(@PathVariable String individuation, MMSnsArticleEntity articleEntity) {
        articleEntity.setArticleStatus(PublicEnum.NORMAL.value());
        articleEntity.setArticleDate(new Date());
        String articleContent = articleEntity.getArticleContent();
        articleEntity.setArticleLogo(getFacadeImageFromContent(articleContent));
        articleEntity.setWordCount(articleContent.length());
        articleEntity.setArticleType(MMSnsArticleEntity.ARTICLE_TYPE_ORIGINAL);
        MMSnsCommonUserEntity commonUserEntity = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.VISIT_USER);
        articleEntity.setUserId(commonUserEntity.getUserId());
        articleEntity = articleService.addArticle(articleEntity);
        return new ResponseEntity(articleEntity);
    }

    /**
     * 更新文章
     *
     * @param individuation
     * @param articleEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{individuation}/article/edit", method = RequestMethod.PUT)
    public ResponseEntity updateArticle(@PathVariable String individuation, MMSnsArticleEntity articleEntity) {
        String articleContent = articleEntity.getArticleContent();
        articleEntity.setArticleLogo(getFacadeImageFromContent(articleContent));
        articleEntity.setWordCount(articleContent.length());
        articleEntity = articleService.updateArticle(articleEntity);
        return new ResponseEntity(articleEntity);
    }

    /**
     * 删除文章
     *
     * @param individuation
     * @param articleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{individuation}/article/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteArticle(@PathVariable String individuation, String articleId) {
        //articleService.deleteArticle(articleId);
        return new ResponseEntity();
    }

    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http[s]{0,1}:\"?(.*?)(\"|>|\\s+)";

    /**
     * 从字符串中获取首页图片url
     *
     * @param content 字符串内容
     * @return
     */
    public static String getFacadeImageFromContent(String content) {
        if (content == null) {
            return null;
        }
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(content);
        if (matcher.find()) {
            String group = matcher.group();
            Matcher urlMatcher = Pattern.compile(IMGSRC_REG).matcher(group);
            while (urlMatcher.find()) {
                String url = urlMatcher.group();
                if(url==null||url.contains("/layui")||url.contains("/icon")){
                    continue;
                }
                url = url.substring(0, url.length() - 1);
                return url;
            }
        }
        return null;
    }
}
