package com.lovecws.mumu.mmsns.action.dao.impl;

import com.lovecws.mumu.core.dao.impl.BaseDaoImpl;
import com.lovecws.mumu.mmsns.action.dao.MMSnsActionCommentDao;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCommentEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹评论数据层实现
 * @date 2017-12-28 17:48:
 */
@Repository
public class MMSnsActionCommentDaoImpl extends BaseDaoImpl<MMSnsActionCommentEntity> implements MMSnsActionCommentDao {
}
