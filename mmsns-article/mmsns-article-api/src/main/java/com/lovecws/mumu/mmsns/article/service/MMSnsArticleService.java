package com.lovecws.mumu.mmsns.article.service;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章服务接口
 * @date 2017-12-18 17:11:
 */
public interface MMSnsArticleService {

    /**
     * 添加文章
     *
     * @param articleEntity
     * @return
     */
    public MMSnsArticleEntity addArticle(MMSnsArticleEntity articleEntity);

    /**
     * 获取用户下的原创文章和转载文章的数量
     *
     * @return
     */
    public List<MMSnsArticleEntity> groupArticleCountByUserId(String userId);

    /**
     * 获取用户原创中 各个分类下的文章数量
     *
     * @param sessionUserId
     * @param articleTypeOriginal
     * @return
     */
    public List<MMSnsArticleEntity> groupArticleCountByCategory(String sessionUserId, String articleTypeOriginal);

    /**
     * 获取文章
     *
     * @param sessionUserId  登陆用户id
     * @param articleType    文章类型
     * @param userCategoryId 文章分类id
     * @param page
     * @param limit
     * @return
     */
    public PageBean<MMSnsArticleEntity> listArticlePage(String sessionUserId, String articleType, String userCategoryId, int page, int limit);

    /**
     * 分页获取文章列表
     *
     * @param sessionUserId
     * @param articleType
     * @param sysCategoryId
     * @param userCategoryId
     * @param orderby
     * @param articleLogo
     * @param page
     * @param limit
     * @return
     */
    public List<MMSnsArticleEntity> selectArticlePage(String sessionUserId, String articleType, String sysCategoryId,String userCategoryId, String orderby, boolean articleLogo, int page, int limit);

    /**
     * 分页获取文章列表 同时附带作者信息
     * @param articleType
     * @param orderby
     * @param articleLogo
     * @param page
     * @param limit
     * @return
     */
    public List<MMSnsArticleEntity> selectArticlePageWithAuthorMessage(String articleType, String sysCategoryId,String orderby, boolean articleLogo,String startDate,String endDate, int page, int limit);

    /**
     * 简单获取文章详情
     *
     * @param articleId
     * @return
     */
    public MMSnsArticleEntity getArticleSimpleInfo(String articleId);

    /**
     * 获取文章详情 同时附带作者信息
     *
     * @param articleId 文章id
     * @return
     */
    public MMSnsArticleEntity getArticleInfo(String articleId);

    /**
     * 更新文章
     *
     * @param articleEntity
     * @return
     */
    public MMSnsArticleEntity updateArticle(MMSnsArticleEntity articleEntity);

    public List<MMSnsArticleEntity> getPopularArticleAuthorMessage(int page, int limit);

    public void updateArticleByCategoryId(String sysCategoryId, String userCategoryId, String articleStatus);

    public void deleteArticle(int articleId);

    public List<MMSnsArticleEntity> getBatchArticleById(long[] recommendArticleIds);
}
