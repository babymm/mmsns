package com.lovecws.mumu.mmsns.action.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.page.PageParam;
import com.lovecws.mumu.mmsns.action.dao.MMSnsActionVoteDao;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionVoteEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionService;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹点赞服务接口实现
 * @date 2017-12-28 17:51:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsActionVoteServiceImpl implements MMSnsActionVoteService {

    @Autowired
    private MMSnsActionVoteDao actionVoteDao;
    @Autowired
    private MMSnsActionService actionService;

    @Override
    public List<MMSnsActionVoteEntity> getActionVoteByCondition(int actionId, Integer voteUserId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("actionId", actionId);
        paramMap.put("voteUserId", voteUserId);
        paramMap.put("voteStatus", PublicEnum.NORMAL.value());
        return actionVoteDao.listByColumn(paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsActionVoteEntity voteAction(MMSnsActionVoteEntity actionVoteEntity) {
        //添加动弹点赞
        actionVoteEntity = actionVoteDao.insert(actionVoteEntity);

        //动弹点赞+1
        MMSnsActionEntity actionEntity = new MMSnsActionEntity();
        actionEntity.setActionId(actionVoteEntity.getActionId());
        actionEntity.setVoteCount(1);
        actionService.updateAction(actionEntity);
        return actionVoteEntity;
    }

    @Override
    public int getVoteActionCountByCondition(String sessionUserId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("voteUserId", sessionUserId);
        return actionVoteDao.listPageCount(paramMap);
    }

    @Override
    public PageBean listVoteActionPage(String voteUserId, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("voteUserId", voteUserId);
        paramMap.put("voteStatus", PublicEnum.NORMAL.value());
        return actionVoteDao.listPage(new PageParam(page, limit), paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cancelActionVote(MMSnsActionVoteEntity actionVoteEntity) {
        //删除此点赞信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("actionId", actionVoteEntity.getActionId());
        paramMap.put("voteUserId", actionVoteEntity.getVoteUserId());
        actionVoteDao.delete(paramMap);

        //将动弹点赞数量-1
        MMSnsActionEntity actionEntity = new MMSnsActionEntity();
        actionEntity.setActionId(actionVoteEntity.getActionId());
        actionEntity.setVoteCount(-1);
        actionService.updateAction(actionEntity);
    }
}
