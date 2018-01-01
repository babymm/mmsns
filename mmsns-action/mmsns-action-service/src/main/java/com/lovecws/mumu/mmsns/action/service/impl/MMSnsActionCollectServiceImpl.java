package com.lovecws.mumu.mmsns.action.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.mmsns.action.dao.MMSnsActionCollectDao;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCollectEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionCollectService;
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
 * @Description: 动弹收藏服务接口实现
 * @date 2017-12-28 17:51:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsActionCollectServiceImpl implements MMSnsActionCollectService {

    @Autowired
    private MMSnsActionCollectDao actionCollectDao;
    @Autowired
    private MMSnsActionService actionService;

    @Override
    public List<MMSnsActionCollectEntity> getActionCollectByCondition(int actionId, Integer sessionCommonUserUserId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("actionId", actionId);
        paramMap.put("collectUserId", sessionCommonUserUserId);
        paramMap.put("collectStatus", PublicEnum.NORMAL.value());
        return actionCollectDao.listByColumn(paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsActionCollectEntity collectAction(MMSnsActionCollectEntity actionCollect) {
        actionCollect = actionCollectDao.insert(actionCollect);

        //点赞+1
        MMSnsActionEntity actionEntity = new MMSnsActionEntity();
        actionEntity.setActionId(actionCollect.getActionId());
        actionEntity.setCollectCount(1);
        actionService.updateAction(actionEntity);
        return actionCollect;
    }
}
