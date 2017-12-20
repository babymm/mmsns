package com.lovecws.mumu.mmsns.common.user.service.impl;

import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户操作日志服务实现
 * @date 2017-11-28 10:58
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsCommonUserLogServiceImpl implements MMSnsCommonUserLogService {
}
