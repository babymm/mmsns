package com.lovecws.mumu.mmsns.action.service.impl;

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
    public List<MMSnsActionEntity> listActionPageWithUserInfo(String orderby, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderby", orderby);
        PageParam pageParam = new PageParam(page, limit);
        paramMap.put("beginIndex", pageParam.getBeginIndex());
        paramMap.put("numPerPage", pageParam.getNumPerPage());
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
}
