package com.lovecws.mumu.mmsns.common.ddl.service;

import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLProvinceEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 省份数据字典接口
 * @date 2017-11-24 14:40
 */
public interface MMSnsCommonDDLProvinceService {

    /**
     * 获取所有的省
     *
     * @return
     */
    public List<MMSnsCommonDDLProvinceEntity> getAllProvinces();
}
