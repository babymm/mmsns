package com.lovecws.mumu.mmsns.article.service;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCommentEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章评论服务接口
 * @date 2017-12-18 17:11:
 */
public interface MMSnsArticleCommentService {

    /**
     * 获取文章评论
     *
     * @param articleId
     * @param page
     * @param limit
     * @return
     */
    public List<MMSnsArticleCommentEntity> listArticleCommentPage(int articleId, int page, int limit);

    /**
     * 发布文章评论
     * @param articleCommentEntity
     * @return
     */
    public MMSnsArticleCommentEntity publishArticleComment(MMSnsArticleCommentEntity articleCommentEntity);

    public MMSnsArticleCommentEntity getArticleCommentInfo(int commentId);
}
