package com.lovecws.mumu.mmsns.common.ddl.service;

import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLCityEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 城市数据字典接口
 * @date 2017-11-24 14:40
 */
public interface MMSnsCommonDDLCityService {

    /**
     * 根据省份code，获取省下的所有城市
     *
     * @param provinceCode
     * @return
     */
    public List<MMSnsCommonDDLCityEntity> getAllCityByProvinceCode(String provinceCode);
}
