package com.lovecws.mumu.mmsns.article.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章收藏
 * @date 2017-12-18 16:25:
 * mb_article_collect
 */
public class MMSnsArticleCollectEntity extends PersistentEntity {

    private int collectId;//收藏id
    private String collectStatus;//收藏状态
    private Date collectDate;//收藏时间

    private int collectUserId;//收藏用户id
    private int articleId;//收藏的文章id
    private int articleUserId;//文章用户id
}
