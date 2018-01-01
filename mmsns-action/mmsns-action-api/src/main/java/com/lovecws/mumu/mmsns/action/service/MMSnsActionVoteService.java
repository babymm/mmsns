package com.lovecws.mumu.mmsns.action.service;

import com.lovecws.mumu.mmsns.action.entity.MMSnsActionVoteEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹点赞接口
 * @date 2017-12-28 17:40:
 */
public interface MMSnsActionVoteService {

    public List<MMSnsActionVoteEntity> getActionVoteByCondition(int actionId, Integer sessionCommonUserUserId);

    public MMSnsActionVoteEntity voteAction(MMSnsActionVoteEntity actionVoteEntity);
}
