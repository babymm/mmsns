package com.lovecws.mumu.mmsns.common.ddl.service.impl;

import com.lovecws.mumu.mmsns.common.ddl.dao.MMSnsCommonDDLAreaDao;
import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLAreaEntity;
import com.lovecws.mumu.mmsns.common.ddl.service.MMSnsCommonDDLAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsCommonDDLAreaServiceImpl implements MMSnsCommonDDLAreaService {

    @Autowired
    private MMSnsCommonDDLAreaDao ddlAreaDao;

    @Override
    public List<MMSnsCommonDDLAreaEntity> getAllAreaByCityCode(String cityCode) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cityCode", cityCode);
        return ddlAreaDao.listByColumn(paramMap);
    }

}
