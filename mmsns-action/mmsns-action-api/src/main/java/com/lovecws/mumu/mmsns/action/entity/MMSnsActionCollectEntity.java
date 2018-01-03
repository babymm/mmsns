package com.lovecws.mumu.mmsns.action.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章收藏
 * @date 2017-12-18 16:25:
 * ma_action_collect
 */
public class MMSnsActionCollectEntity extends PersistentEntity {

    private int collectId;//收藏id
    private String collectStatus;//收藏状态
    private Date collectDate;//收藏时间

    private int collectUserId;//收藏用户id
    private int actionId;//收藏的动弹id
    private int actionUserId;//发布动弹的用户id

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public String getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public int getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(int collectUserId) {
        this.collectUserId = collectUserId;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(int actionUserId) {
        this.actionUserId = actionUserId;
    }

    private String actionType;
    private String actionContent;
    private String wordCount;
    private String collectCount;
    private String voteCount;
    private String reprintCount;
    private String commentCount;
    private String actionDate;

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

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(String collectCount) {
        this.collectCount = collectCount;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getReprintCount() {
        return reprintCount;
    }

    public void setReprintCount(String reprintCount) {
        this.reprintCount = reprintCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }
}
