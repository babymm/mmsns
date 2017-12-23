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

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public String getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(String voteStatus) {
        this.voteStatus = voteStatus;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getArticleUserId() {
        return articleUserId;
    }

    public void setArticleUserId(int articleUserId) {
        this.articleUserId = articleUserId;
    }

    public int getVoteUserId() {
        return voteUserId;
    }

    public void setVoteUserId(int voteUserId) {
        this.voteUserId = voteUserId;
    }

    private Date articleDate;
    private String articleTitle;
    private String articleLogo;
    private int readCount;
    private int collectCount;
    private int voteCount;
    private int relayCount;
    private int commentCount;

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleLogo() {
        return articleLogo;
    }

    public void setArticleLogo(String articleLogo) {
        this.articleLogo = articleLogo;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getRelayCount() {
        return relayCount;
    }

    public void setRelayCount(int relayCount) {
        this.relayCount = relayCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
