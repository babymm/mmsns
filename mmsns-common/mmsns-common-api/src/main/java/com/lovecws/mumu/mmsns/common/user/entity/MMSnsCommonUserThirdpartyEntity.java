package com.lovecws.mumu.mmsns.common.user.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户第三方登录
 * @date 2017-11-24 10:59
 * ec_user_thirdparty
 */
public class MMSnsCommonUserThirdpartyEntity extends PersistentEntity {

    private Integer thirdpartyId;//第三方登录id
    private String thirdpartyStatus;//第三方登录状态
    private Date createDate;//创建时间

    private Integer userId;// 用户id
    private String openId;// 第三方登录令牌
    private String returnValue;// 第三方登录返回值
    private String userRegType;// 第三方登录来源
}
