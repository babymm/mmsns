package com.lovecws.mumu.mmsns.action.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章评论
 * @date 2017-12-18 16:26:
 * ma_action_comment
 */
public class MMSnsActionCommentEntity extends PersistentEntity {

    public static final String ACTION_COMMENT_TYPE_COMMENT = "comment";
    public static final String ACTION_COMMENT_TYPE_REPLY = "reply";

    private int commentId;//评论id
    private String commentStatus;//评论状态
    private Date commentDate;//评论时间

    private String commentType;//评论类型 comment、reply
    private String commentContent;

    private int commentUserId;//评论用户id
    private int actionId;//动弹id
    private int actionUserId;//动弹用户id

    private int replyCommentId;//如果是回复 则是回复的评论id
    private int replyUserId;//回复评论id
    private String replyCommentContent;//回复的评论内容

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
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

    public int getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(int replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public int getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyCommentContent() {
        return replyCommentContent;
    }

    public void setReplyCommentContent(String replyCommentContent) {
        this.replyCommentContent = replyCommentContent;
    }

    private String userName;
    private String individuation;
    private String avator;
    private String replyUserName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIndividuation() {
        return individuation;
    }

    public void setIndividuation(String individuation) {
        this.individuation = individuation;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }
}
