package com.lovecws.mumu.mmsns.action.dao.impl;

import com.lovecws.mumu.core.dao.impl.BaseDaoImpl;
import com.lovecws.mumu.mmsns.action.dao.MMSnsActionVoteDao;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionVoteEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹点赞数据层实现
 * @date 2017-12-28 17:48:
 */
@Repository
public class MMSnsActionVoteDaoImpl extends BaseDaoImpl<MMSnsActionVoteEntity> implements MMSnsActionVoteDao {
}
