package com.lovecws.mumu.mmsns.action.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.page.PageParam;
import com.lovecws.mumu.mmsns.action.dao.MMSnsActionDao;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionService;
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
 * @Description: 动弹服务接口实现
 * @date 2017-12-28 17:51:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsActionServiceImpl implements MMSnsActionService {

    @Autowired
    private MMSnsActionDao actionDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsActionEntity addAction(MMSnsActionEntity actionEntity) {
        return actionDao.insert(actionEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsActionEntity reprintAction(MMSnsActionEntity actionEntity) {
        actionEntity = addAction(actionEntity);

        //动弹转发量+1
        MMSnsActionEntity reprintAction = new MMSnsActionEntity();
        reprintAction.setActionId(actionEntity.getActionId());
        reprintAction.setReprintCount(1);
        updateAction(reprintAction);
        return actionEntity;
    }

    @Override
    public List<MMSnsActionEntity> listActionPageWithUserInfo(String orderby, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderby", orderby);
        PageParam pageParam = new PageParam(page, limit);
        paramMap.put("beginIndex", pageParam.getBeginIndex());
        paramMap.put("numPerPage", pageParam.getNumPerPage());
        paramMap.put("actionStatus", PublicEnum.NORMAL.value());
        return actionDao.selectList("listActionPageWithUserInfo", paramMap);
    }

    @Override
    public MMSnsActionEntity getArctionInfo(int actionId) {
        return actionDao.getById(String.valueOf(actionId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateAction(MMSnsActionEntity actionEntity) {
        actionDao.update(actionEntity);
    }

    @Override
    public MMSnsActionEntity getActionBaseInfo(int actionId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("actionId", actionId);
        return actionDao.getByColumn(paramMap);
    }

    @Override
    public List<MMSnsActionEntity> groupActionCountByActionType(String sessionUserId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", sessionUserId);
        paramMap.put("actionStatus", PublicEnum.NORMAL.value());
        return actionDao.selectList("groupActionCountByActionType", paramMap);
    }

    @Override
    public PageBean listActionPage(String userId, String actionType, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("actionType", actionType);
        paramMap.put("actionStatus", PublicEnum.NORMAL.value());
        return actionDao.listPage(new PageParam(page, limit), paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteActionById(int actionId) {
        MMSnsActionEntity actionBaseInfo = getActionBaseInfo(actionId);
        //如果该动弹是转载 则将原动弹转载量-1
        if (MMSnsActionEntity.ACTION_TYPE_REPRINT.equals(actionBaseInfo.getActionType())) {
            MMSnsActionEntity reprintAction = new MMSnsActionEntity();
            reprintAction.setActionId(actionBaseInfo.getReprintActionId());
            reprintAction.setReprintCount(-1);
            updateAction(reprintAction);
        }
        //删除该动弹
        MMSnsActionEntity actionEntity = new MMSnsActionEntity();
        actionEntity.setActionId(actionId);
        actionEntity.setActionStatus(PublicEnum.DELETE.value());
        updateAction(actionEntity);
    }
}
