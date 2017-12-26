package com.lovecws.mumu.mmsns.article.service;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCollectEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章收藏服务接口
 * @date 2017-12-18 17:11:
 */
public interface MMSnsArticleCollectService {

    /**
     * 获取收藏文章数量
     *
     * @param sessionUserId
     * @return
     */
    public int getCollectArticleCountByCondition(String sessionUserId);

    /**
     * 获取我
     *
     * @param sessionUserId
     * @param page
     * @param limit
     * @return
     */
    public PageBean<MMSnsArticleCollectEntity> listCollectArticlePage(String sessionUserId, int page, int limit);

    /**
     * 获取用户收藏的文章
     * @param articleId
     * @param userId
     * @return
     */
    public List<MMSnsArticleCollectEntity> getArticleCollectByCondition(Integer articleId, Integer userId);

    /**
     * 用户收藏文章
     * @param articleCollect
     * @return
     */
    public MMSnsArticleCollectEntity collectArticle(MMSnsArticleCollectEntity articleCollect);
}
