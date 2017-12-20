package com.lovecws.mumu.mmsns.common.ddl.service.impl;

import com.lovecws.mumu.core.utils.IPAddressUtil;
import com.lovecws.mumu.mmsns.common.ddl.dao.MMSnsCommonDDLIPAddressDao;
import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLIPAddressEntity;
import com.lovecws.mumu.mmsns.common.ddl.service.MMSnsCommonDDLIPAddressService;
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
public class MMSnsCommonDDLIPAddressServiceImpl implements MMSnsCommonDDLIPAddressService {

    @Autowired
    private MMSnsCommonDDLIPAddressDao ddlipAddressDao;

    @Override
    /*@ModularCache(prefix="system:ipaddress")*/
    public List<MMSnsCommonDDLIPAddressEntity> getAllIpAddress() {
        return ddlipAddressDao.listBy(new HashMap<String, Object>());
    }

    @Override
    public List<MMSnsCommonDDLIPAddressEntity> getIpAddressByIp(String ip) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ip", IPAddressUtil.transformIpAddressToLong(ip));
        //paramMap.put("ip", ip);
        return ddlipAddressDao.listBy(paramMap);
    }

    @Override
    public List<MMSnsCommonDDLIPAddressEntity> getIpAddressByAddress(String address) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("address", address);
        return ddlipAddressDao.listBy(paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addBatchIPAddress(List<MMSnsCommonDDLIPAddressEntity> allSystemIpAddress) {
        ddlipAddressDao.insert(allSystemIpAddress);
    }

}
