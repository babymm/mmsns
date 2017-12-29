package com.lovecws.mumu.mmsns.action.service;

import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCommentEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹评论接口
 * @date 2017-12-28 17:39:
 */
public interface MMSnsActionCommentService {
    public List<MMSnsActionCommentEntity> listActionCommentPageWithUserInfo(int actionId, int page, int limit);

    public MMSnsActionCommentEntity getActionCommentInfo(int commentId);

    public MMSnsActionCommentEntity publishActionComment(MMSnsActionCommentEntity actionCommentReply);

    public void deleteArticleCommentById(String commentId);

    public void updateArticleComment(MMSnsActionCommentEntity articleCommentEntity);
}
