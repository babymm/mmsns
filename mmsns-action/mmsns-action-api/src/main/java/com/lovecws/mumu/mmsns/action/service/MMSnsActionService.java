package com.lovecws.mumu.mmsns.action.service;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹接口
 * @date 2017-12-28 17:39:
 */
public interface MMSnsActionService {

    /**
     * 添加动弹
     *
     * @param actionEntity
     * @return
     */
    public MMSnsActionEntity addAction(MMSnsActionEntity actionEntity);

    /**
     * 分页获取 动弹信息
     *
     * @param orderby
     * @param page
     * @param limit
     * @return
     */
    public List<MMSnsActionEntity> listActionPageWithUserInfo(String orderby, int page, int limit);

    public MMSnsActionEntity getArctionInfo(int actionId);

    public void updateAction(MMSnsActionEntity actionEntity);

    /**
     * 获取动弹基本信息
     *
     * @param actionId
     * @return
     */
    public MMSnsActionEntity getArctionBaseInfo(int actionId);

    public List<MMSnsActionEntity> groupActionCountByActionType(String userId);

    public PageBean listActionPage(String userId, String actionType, int page, int limit);
}
