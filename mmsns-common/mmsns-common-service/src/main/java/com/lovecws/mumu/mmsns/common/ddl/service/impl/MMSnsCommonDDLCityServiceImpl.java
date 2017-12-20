package com.lovecws.mumu.mmsns.common.ddl.service.impl;

import com.lovecws.mumu.mmsns.common.ddl.dao.MMSnsCommonDDLCityDao;
import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLCityEntity;
import com.lovecws.mumu.mmsns.common.ddl.service.MMSnsCommonDDLCityService;
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
public class MMSnsCommonDDLCityServiceImpl implements MMSnsCommonDDLCityService {

    @Autowired
    private MMSnsCommonDDLCityDao ddlCityDao;

    @Override
    public List<MMSnsCommonDDLCityEntity> getAllCityByProvinceCode(String provinceCode) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("provinceCode", provinceCode);
        return ddlCityDao.listByColumn(paramMap);
    }

}
