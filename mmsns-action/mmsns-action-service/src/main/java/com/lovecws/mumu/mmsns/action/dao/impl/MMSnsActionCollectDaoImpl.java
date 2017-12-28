package com.lovecws.mumu.mmsns.action.dao.impl;

import com.lovecws.mumu.core.dao.impl.BaseDaoImpl;
import com.lovecws.mumu.mmsns.action.dao.MMSnsActionCollectDao;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCollectEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹收藏数据层实现
 * @date 2017-12-28 17:46:
 */
@Repository
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = false)
public class MMSnsActionCollectDaoImpl extends BaseDaoImpl<MMSnsActionCollectEntity> implements MMSnsActionCollectDao {
}
