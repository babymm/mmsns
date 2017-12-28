package com.lovecws.mumu.mmsns.action.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章实体对象
 * @date 2017-12-18 16:25:
 * ma_action
 */
public class MMSnsActionEntity extends PersistentEntity {

    public static final String ACTION_TYPE_ORIGINAL = "original";
    public static final String ACTION_TYPE_REPRINT = "reprint";

    private int actionId;//动弹id
    private String actionStatus;//动弹状态 PubliEnum
    private Date actionDate;//动弹发布日期
    private String actionType;//原创 original、转发 reprint

    private String actionContent;//动弹内容
    private int wordCount;//动弹字数
    private int userId;//发布动弹的用户id

    private int collectCount;//收藏数量
    private int voteCount;//点赞数量
    private int commentCount;//评论数量
    private int reprintCount;//转发量

    private String reprintActionContent;//转发的动弹内容
    private String reprintActionId;//转发的动弹id
    private String reprintUserId;//转发的动弹作者用户id

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionContent() {
        return actionContent;
    }

    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getReprintCount() {
        return reprintCount;
    }

    public void setReprintCount(int reprintCount) {
        this.reprintCount = reprintCount;
    }

    public String getReprintActionContent() {
        return reprintActionContent;
    }

    public void setReprintActionContent(String reprintActionContent) {
        this.reprintActionContent = reprintActionContent;
    }

    public String getReprintActionId() {
        return reprintActionId;
    }

    public void setReprintActionId(String reprintActionId) {
        this.reprintActionId = reprintActionId;
    }

    public String getReprintUserId() {
        return reprintUserId;
    }

    public void setReprintUserId(String reprintUserId) {
        this.reprintUserId = reprintUserId;
    }
}
