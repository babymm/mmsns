package com.lovecws.mumu.mmsns.common.user.service.impl;

import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserFeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户意见反馈服务实现
 * @date 2017-11-28 10:57
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsCommonUserFeedbackServiceImpl implements MMSnsCommonUserFeedbackService {
}
