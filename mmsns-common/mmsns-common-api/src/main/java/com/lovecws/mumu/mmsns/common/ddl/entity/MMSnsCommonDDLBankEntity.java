package com.lovecws.mumu.mmsns.common.ddl.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户
 * @date 2017-11-24 9:27
 * mc_ddl_bank
 */
public class MMSnsCommonDDLBankEntity extends PersistentEntity {

    private Integer bankId;// 银行id
    private Integer bankStatus;// 银行状态
    private String bankName;// 银行名称
    private String logo;// 银行logo
    private String backgroundImage;// 背景图片
    private String initial;// 首字母
    private Integer isHot;// 是否热门（0：不热门，1：热门）

    public Integer getBankId() {
        return bankId;
    }

    public Integer getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(final Integer bankStatus) {
        this.bankStatus = bankStatus;
    }

    public void setBankId(final Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(final String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(final String initial) {
        this.initial = initial;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(final Integer isHot) {
        this.isHot = isHot;
    }
}
