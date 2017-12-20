package com.lovecws.mumu.mmsns.common.ddl.service;

import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLAreaEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 区域数据字典
 * @date 2017-11-24 14:40
 */
public interface MMSnsCommonDDLAreaService {

	/**
	 * 根据城市code，获取城市下的所有区
	 * @param cityCode
	 * @return
	 */
	public List<MMSnsCommonDDLAreaEntity> getAllAreaByCityCode(String cityCode);
}
