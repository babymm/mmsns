package com.lovecws.mumu.mmsns.common.ddl.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 数据字典 ip地址库
 * @date 2017-11-24 9:27
 * mc_ddl_province
 */
public class MMSnsCommonDDLIPAddressEntity extends PersistentEntity {

    private long sip;//开始ip
    private long eip;//结束ip
    private String startIp;// 起始ip地址
    private String endIp;// 结束ip地址
    private String cityName;// ip城市地名称
    private String companyName;// 运营商公司名称

    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getSip() {
        return sip;
    }

    public void setSip(long sip) {
        this.sip = sip;
    }

    public long getEip() {
        return eip;
    }

    public void setEip(long eip) {
        this.eip = eip;
    }
}
