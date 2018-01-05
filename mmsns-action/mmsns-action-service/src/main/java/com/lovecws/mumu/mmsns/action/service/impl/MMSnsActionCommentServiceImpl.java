package com.lovecws.mumu.mmsns.action.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.page.PageParam;
import com.lovecws.mumu.mmsns.action.dao.MMSnsActionCommentDao;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCommentEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionCommentService;
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
 * @Description: 动弹评论服务接口实现
 * @date 2017-12-28 17:51:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsActionCommentServiceImpl implements MMSnsActionCommentService {

    @Autowired
    private MMSnsActionCommentDao actionCommentDao;
    @Autowired
    private MMSnsActionService actionService;

    @Override
    public List<MMSnsActionCommentEntity> listActionCommentPageWithUserInfo(int actionId, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("actionId", actionId);
        PageParam pageParam = new PageParam(page, limit);
        paramMap.put("beginIndex", pageParam.getBeginIndex());
        paramMap.put("numPerPage", pageParam.getNumPerPage());
        paramMap.put("commentStatus", PublicEnum.NORMAL.value());
        return actionCommentDao.selectList("listActionCommentPageWithUserInfo", paramMap);
    }

    @Override
    public PageBean<MMSnsActionCommentEntity> listUserActionCommentPageWithUserInfo(Integer userId, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("actionStatus", PublicEnum.NORMAL.value());
        paramMap.put("commentStatus", PublicEnum.NORMAL.value());
        return actionCommentDao.listPage(new PageParam(page, limit), paramMap);
    }

    @Override
    public MMSnsActionCommentEntity getActionCommentInfo(int commentId) {
        return actionCommentDao.getById(String.valueOf(commentId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsActionCommentEntity publishActionComment(MMSnsActionCommentEntity actionCommentEntity) {
        actionCommentEntity = actionCommentDao.insert(actionCommentEntity);

        //动弹数量+1
        MMSnsActionEntity actionEntity = new MMSnsActionEntity();
        actionEntity.setActionId(actionCommentEntity.getActionId());
        actionEntity.setCommentCount(1);
        actionService.updateAction(actionEntity);

        return actionCommentEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteActionCommentById(int commentId) {
        MMSnsActionCommentEntity articleCommentEntity = new MMSnsActionCommentEntity();
        articleCommentEntity.setCommentId(commentId);
        articleCommentEntity.setCommentStatus(PublicEnum.DELETE.value());
        updateArticleComment(articleCommentEntity);

        MMSnsActionCommentEntity actionCommentInfo = getActionCommentInfo(commentId);
        //动弹评论数量-1
        MMSnsActionEntity actionEntity = new MMSnsActionEntity();
        actionEntity.setActionId(actionCommentInfo.getActionId());
        actionEntity.setCommentCount(-1);
        actionService.updateAction(actionEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateArticleComment(MMSnsActionCommentEntity articleCommentEntity) {
        actionCommentDao.update(articleCommentEntity);
    }
}
