package com.lovecws.mumu.mmsns.action.service;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionVoteEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹点赞接口
 * @date 2017-12-28 17:40:
 */
public interface MMSnsActionVoteService {

    public List<MMSnsActionVoteEntity> getActionVoteByCondition(int actionId, Integer voteUserId);

    public MMSnsActionVoteEntity voteAction(MMSnsActionVoteEntity actionVoteEntity);

    public int getVoteActionCountByCondition(String sessionUserId);

    public PageBean listVoteActionPage(String voteUserId, int page, int limit);
}
