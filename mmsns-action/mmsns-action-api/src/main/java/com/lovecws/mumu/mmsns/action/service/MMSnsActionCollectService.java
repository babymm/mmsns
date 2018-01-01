package com.lovecws.mumu.mmsns.action.service;

import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCollectEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹收藏接口
 * @date 2017-12-28 17:39:
 */
public interface MMSnsActionCollectService {
    
    public List<MMSnsActionCollectEntity> getActionCollectByCondition(int actionId, Integer sessionCommonUserUserId);

    public MMSnsActionCollectEntity collectAction(MMSnsActionCollectEntity actionCollect);
}
