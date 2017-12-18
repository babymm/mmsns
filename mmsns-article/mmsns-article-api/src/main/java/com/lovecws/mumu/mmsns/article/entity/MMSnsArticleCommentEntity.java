package com.lovecws.mumu.mmsns.article.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章评论
 * @date 2017-12-18 16:26:
 * mb_article_comment
 */
public class MMSnsArticleCommentEntity extends PersistentEntity {

    private int commentId;//评论id
    private String commentStatus;//评论状态
    private Date commentDate;//评论时间

    private String commentType;//评论类型 comment、reply
    private String commentContent;
    private String commentImg;

    private int commentOrder;//评论排序
    private int articleId;//文章id
    private int replyCommentId;//如果是回复 则是回复的评论id

    private int commentUserId;//评论用户id
    private int articleUserId;//文章用户id
}
