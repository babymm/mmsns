package com.lovecws.mumu.mmsns.controller.admin.article;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCategoryEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCategoryService;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户设置 文章管理
 * @date 2017-12-15 14:00:
 */
@Controller
@RequestMapping("/admin")
public class MMSnsAdminArticleController {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsArticleCategoryService articleCategoryService;

    /**
     * 跳转到文章分类页面
     *
     * @param individuation
     * @return
     */
    @RequestMapping(value = {"/{individuation}/article/classify"}, method = RequestMethod.GET)
    public String articleClassify(@PathVariable String individuation) {
        request.setAttribute("adminModular", "articleClassify");
        return "/admin/article/classify";
    }

    /**
     * 获取到文章分类数据
     *
     * @param individuation
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{individuation}/article/classify/data", method = RequestMethod.GET)
    public Object scoreData(@PathVariable String individuation,
                            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession(true).getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        PageBean<MMSnsArticleCategoryEntity> pageBean = articleCategoryService.getArticleClassifyByCondition(MMSnsArticleCategoryEntity.ARTICLE_CATEGORY_TYPE_USER, String.valueOf(sessionCommonUser.getUserId()), null, page, limit);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data", pageBean.getRecordList());
        resultMap.put("code", 0);
        resultMap.put("msg", "success");
        resultMap.put("count", pageBean.getTotalCount());
        return resultMap;
    }

    /**
     * 跳转到文章分类 创建、编辑、详情页面
     *
     * @param individuation
     * @param operation
     * @return
     */
    @RequestMapping(value = {"/{individuation}/article/classify/{operation}"}, method = RequestMethod.GET)
    public String articleClassifyCreate(@PathVariable String individuation, @PathVariable String operation, String categoryId) {
        String articleClassifyTitle = null;
        if ("view".equals(operation)) {
            articleClassifyTitle = "文章分类详情";
            request.setAttribute("articleCategory", articleCategoryService.getArticleCategoryInfo(categoryId));
        } else if ("edit".equals(operation)) {
            articleClassifyTitle = "编辑文章分类";
            request.setAttribute("articleCategory", articleCategoryService.getArticleCategoryInfo(categoryId));
        } else if ("create".equals(operation)) {
            articleClassifyTitle = "创建文章分类";
        }
        request.setAttribute("adminModular", "articleClassify");
        request.setAttribute("articleClassifyOperation", operation);
        request.setAttribute("articleClassifyTitle", articleClassifyTitle);
        return "/admin/article/classify_create";
    }

    /**
     * 文章创建
     *
     * @param individuation
     * @param categoryName  分类名称
     * @param categoryIcon  分类icon
     * @param categoryOrder 分类排序
     * @param categoryDesc  分类描述
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/{individuation}/article/classify/create"}, method = RequestMethod.POST)
    public ResponseEntity addArticleClassify(@PathVariable String individuation, String categoryName, String categoryIcon, Integer categoryOrder, String categoryDesc) {
        request.setAttribute("adminModular", "articleClassify");
        MMSnsArticleCategoryEntity articleCategoryEntity = new MMSnsArticleCategoryEntity();
        articleCategoryEntity.setCategoryStatus(PublicEnum.NORMAL.value());
        articleCategoryEntity.setCategoryDate(new Date());
        articleCategoryEntity.setCategoryType(MMSnsArticleCategoryEntity.ARTICLE_CATEGORY_TYPE_USER);
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession(true).getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        articleCategoryEntity.setUserId(sessionCommonUser.getUserId());
        articleCategoryEntity.setCategoryName(categoryName);
        articleCategoryEntity.setCategoryIcon(categoryIcon);
        articleCategoryEntity.setCategoryDesc(categoryDesc);
        articleCategoryEntity.setCategoryOrder(categoryOrder);
        articleCategoryEntity = articleCategoryService.addArticleCategory(articleCategoryEntity);
        return new ResponseEntity(articleCategoryEntity);
    }

    /**
     * 文章编辑
     *
     * @param individuation
     * @param categoryId    分类id
     * @param categoryName  分类名称
     * @param categoryIcon  分类icon
     * @param categoryOrder 分类排序
     * @param categoryDesc  分类描述
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/{individuation}/article/classify/edit"}, method = RequestMethod.POST)
    public ResponseEntity editArticleClassify(@PathVariable String individuation, int categoryId, String categoryName, String categoryIcon, Integer categoryOrder, String categoryDesc) {
        request.setAttribute("adminModular", "articleClassify");
        MMSnsArticleCategoryEntity articleCategoryEntity = new MMSnsArticleCategoryEntity();
        articleCategoryEntity.setCategoryId(categoryId);
        articleCategoryEntity.setCategoryName(categoryName);
        articleCategoryEntity.setCategoryIcon(categoryIcon);
        articleCategoryEntity.setCategoryDesc(categoryDesc);
        articleCategoryEntity.setCategoryOrder(categoryOrder);
        articleCategoryEntity = articleCategoryService.updateArticleCategory(articleCategoryEntity);
        return new ResponseEntity(articleCategoryEntity);
    }

    /**
     * 文章分类删除
     *
     * @param individuation
     * @param categoryId    分类id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/{individuation}/article/classify/delete"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteArticleClassify(@PathVariable String individuation, String categoryId) {
        articleCategoryService.deleteArticleCategory(categoryId);
        return new ResponseEntity();
    }

    @RequestMapping(value = {"/{individuation}/article/comment"}, method = RequestMethod.GET)
    public String articleComment(@PathVariable String individuation) {
        request.setAttribute("adminModular", "articleComment");
        return "/admin/article/comments";
    }
}
