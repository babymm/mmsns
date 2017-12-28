package com.lovecws.mumu.mmsns.action.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章点赞实体对象
 * @date 2017-12-18 16:25:
 * ma_action_vote
 */
public class MMSnsActionVoteEntity extends PersistentEntity {

    private int voteId;//点赞id
    private String voteStatus;//点赞状态
    private Date voteDate;//点赞时间

    private int actionId;//动弹id
    private int actionUserId;//动弹用户id
    private int voteUserId;//点赞用户id

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

    public int getVoteUserId() {
        return voteUserId;
    }

    public void setVoteUserId(int voteUserId) {
        this.voteUserId = voteUserId;
    }
}
