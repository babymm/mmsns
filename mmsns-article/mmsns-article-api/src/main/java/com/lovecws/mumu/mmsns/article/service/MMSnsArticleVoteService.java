package com.lovecws.mumu.mmsns.article.service;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCollectEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleVoteEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章点赞服务接口
 * @date 2017-12-18 17:11:
 */
public interface MMSnsArticleVoteService {

    /**
     * 获取点赞文章数量
     *
     * @param sessionUserId
     * @return
     */
    public int getVoteArticleCountByCondition(String sessionUserId);

    /**
     * 获取我发布的文章
     *
     * @param sessionUserId
     * @param page
     * @param limit
     * @return
     */
    public PageBean<MMSnsArticleVoteEntity> listVoteArticlePage(String sessionUserId, int page, int limit);

    public List<MMSnsArticleVoteEntity> getArticleVoteByCondition(int articleId, Integer userId);

    public MMSnsArticleVoteEntity voteArticle(MMSnsArticleVoteEntity articleVoteEntity);
}
