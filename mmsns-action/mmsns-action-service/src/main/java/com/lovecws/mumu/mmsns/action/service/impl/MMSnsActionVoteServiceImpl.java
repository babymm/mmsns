package com.lovecws.mumu.mmsns.action.service.impl;

import com.lovecws.mumu.mmsns.action.service.MMSnsActionVoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹点赞服务接口实现
 * @date 2017-12-28 17:51:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsActionVoteServiceImpl implements MMSnsActionVoteService {
}
