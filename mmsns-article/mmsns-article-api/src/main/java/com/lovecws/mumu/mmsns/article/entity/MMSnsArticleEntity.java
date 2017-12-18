package com.lovecws.mumu.mmsns.article.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章实体对象
 * @date 2017-12-18 16:25:
 * mb_article
 */
public class MMSnsArticleEntity extends PersistentEntity {

    private int articleId;
    private String articleStatus;
    private Date articleDate;

    private String articleTitle;
    private String articleContent;
    private String articleLogo;
    private String articleLabels;
    private int wordCount;

    private int sysCategoryId;
    private int userCategoryId;

    private int readCount;
    private int collectCount;
    private int voteCount;
    private int relayCount;
    private int commentCount;

    private String articleType;//原创、转载
    private int publisherArticleId;//文章发布者的文章id
    private int relayArticleId;//转发文章id

    private int userId;//用户id
}
