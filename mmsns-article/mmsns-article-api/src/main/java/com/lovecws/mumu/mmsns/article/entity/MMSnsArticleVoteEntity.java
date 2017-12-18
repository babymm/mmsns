package com.lovecws.mumu.mmsns.article.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章点赞实体对象
 * @date 2017-12-18 16:25:
 * mb_article_vote
 */
public class MMSnsArticleVoteEntity extends PersistentEntity {

    private int voteId;//收藏id
    private String voteStatus;//收藏状态
    private Date voteDate;//收藏时间

    private int articleId;//收藏的文章id
    private int articleUserId;//文章用户id

    private int voteUserId;//收藏用户id

}
