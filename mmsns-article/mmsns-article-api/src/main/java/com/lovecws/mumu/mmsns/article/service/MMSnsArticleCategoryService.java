package com.lovecws.mumu.mmsns.article.service;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCategoryEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章分类服务接口
 * @date 2017-12-18 17:10:
 */
public interface MMSnsArticleCategoryService {

    /**
     * 获取
     *
     * @param categoryType 文章分类类型
     * @param userId       用户id
     * @param systemUserId 系统用户id
     * @param page         当前页面
     * @param limit        一页大小
     * @return
     */
    public PageBean<MMSnsArticleCategoryEntity> getArticleClassifyByCondition(String categoryType, String userId, String systemUserId, int page, int limit);

    /**
     * 添加文章
     *
     * @param articleCategoryEntity
     * @return
     */
    public MMSnsArticleCategoryEntity addArticleCategory(MMSnsArticleCategoryEntity articleCategoryEntity);

    /**
     * 更新文章
     *
     * @param articleCategoryEntity
     * @return
     */
    public MMSnsArticleCategoryEntity updateArticleCategory(MMSnsArticleCategoryEntity articleCategoryEntity);

    /**
     * 获取文章详情
     *
     * @param categoryId 文章分类id
     * @return
     */
    public MMSnsArticleCategoryEntity getArticleCategoryInfo(String categoryId);

    /**
     * 删除文章分类
     * @param categoryId
     */
    public void deleteArticleCategory(String categoryId);
}
