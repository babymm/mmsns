package com.lovecws.mumu.mmsns.common.ddl.service.impl;

import com.lovecws.mumu.mmsns.common.ddl.dao.MMSnsCommonDDLProvinceDao;
import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLProvinceEntity;
import com.lovecws.mumu.mmsns.common.ddl.service.MMSnsCommonDDLProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsCommonDDLProvinceServiceImpl implements MMSnsCommonDDLProvinceService {

    @Autowired
    private MMSnsCommonDDLProvinceDao ddlProvinceDao;

    @Override
    public List<MMSnsCommonDDLProvinceEntity> getAllProvinces() {
        return ddlProvinceDao.listByColumn(new HashMap<String, Object>());
    }

}
