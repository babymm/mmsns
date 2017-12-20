package com.lovecws.mumu.mmsns.common.ddl.service;

import com.lovecws.mumu.mmsns.common.ddl.entity.MMSnsCommonDDLIPAddressEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: ip地址数据字典接口
 * @date 2017-11-24 14:40
 */
public interface MMSnsCommonDDLIPAddressService {

    /**
     * 获取所有的ipaddress
     *
     * @return
     */
    public List<MMSnsCommonDDLIPAddressEntity> getAllIpAddress();

    /**
     * 通过ip查询ip所属地
     *
     * @param ip ip地址
     * @return
     */
    public List<MMSnsCommonDDLIPAddressEntity> getIpAddressByIp(String ip);

    /**
     * 通过地理位置查询ip
     *
     * @param address 地理位置
     * @return
     */
    public List<MMSnsCommonDDLIPAddressEntity> getIpAddressByAddress(String address);

    /**
     * 重新构造ip地址库
     *
     * @param allSystemIpAddress
     */
    public void addBatchIPAddress(List<MMSnsCommonDDLIPAddressEntity> allSystemIpAddress);

}
