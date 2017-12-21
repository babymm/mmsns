package com.lovecws.mumu.mmsns.common.user.service.impl;

import com.lovecws.mumu.core.utils.ValidateUtils;
import com.lovecws.mumu.mmsns.common.user.dao.MMSnsCommonUserDao;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserService;
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
 * @Description: 用户服务实现
 * @date 2017-11-28 10:58
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsCommonUserServiceImpl implements MMSnsCommonUserService {

    @Autowired
    private MMSnsCommonUserDao commonUserDao;

    @Override
    public List<MMSnsCommonUserEntity> getCommonUserByCondition(final String userName, final String userPhone,final String userEmail,final String individuation) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", userName);
        paramMap.put("userPhone", userPhone);
        paramMap.put("userEmail", userEmail);
        paramMap.put("individuation", individuation);
        return commonUserDao.listByColumn(paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsCommonUserEntity addCommonUser(final MMSnsCommonUserEntity commonUserEntity) {
        return commonUserDao.insert(commonUserEntity);
    }

    @Override
    public MMSnsCommonUserEntity getCommonUserById(final int userId) {
        return commonUserDao.getById(String.valueOf(userId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsCommonUserEntity updateCommonUser(final MMSnsCommonUserEntity elebillCommonUserEntity) {
        commonUserDao.update(elebillCommonUserEntity);
        return elebillCommonUserEntity;
    }
}
