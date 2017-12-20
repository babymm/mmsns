package com.lovecws.mumu.mmsns.common.ddl.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.mmsns.common.ddl.dao.MMSnsCommonDDLBankDao;
import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLBankEntity;
import com.lovecws.mumu.mmsns.common.ddl.service.MMSnsCommonDDLBankService;
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
 * @Description: 数据字典银行卡管理
 * @date 2017-11-27 10:34
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsCommonDDLBankServiceImpl implements MMSnsCommonDDLBankService {

    @Autowired
    private MMSnsCommonDDLBankDao ddlBankDao;

    @Override
    public List<MMSnsCommonDDLBankEntity> getBankList(String isHot) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bankStatus", PublicEnum.NORMAL.value());
        paramMap.put("isHot", isHot);
        return ddlBankDao.listByColumn(paramMap);
    }
}
