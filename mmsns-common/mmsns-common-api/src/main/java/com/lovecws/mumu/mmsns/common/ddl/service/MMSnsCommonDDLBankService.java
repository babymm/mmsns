package com.lovecws.mumu.mmsns.common.ddl.service;

import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLBankEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 银行数据字典接口
 * @date 2017-11-24 14:40
 */
public interface MMSnsCommonDDLBankService {
    public List<MMSnsCommonDDLBankEntity> getBankList(String isHot);
}
