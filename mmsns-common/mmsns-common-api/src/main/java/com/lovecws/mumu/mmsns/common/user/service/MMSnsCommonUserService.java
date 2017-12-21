package com.lovecws.mumu.mmsns.common.user.service;

import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;

import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户服务接口
 * @date 2017-11-28 10:46
 */
public interface MMSnsCommonUserService {
    /**
     * 获取用户列表
     *
     * @param userPhone 用户手机号码
     * @param userName  用户名称
     * @param userEmail  用户邮箱
     * @param individuation  用户个性化空间名称
     * @return
     */
    public List<MMSnsCommonUserEntity> getCommonUserByCondition(String userName,String userPhone, String userEmail,String individuation);

    /**
     * 添加用户
     *
     * @param commonUserEntity 用户实体
     * @return
     */
    public MMSnsCommonUserEntity addCommonUser(MMSnsCommonUserEntity commonUserEntity);

    /**
     * 获取用户详情
     *
     * @param userId 用户id
     * @return
     */
    public MMSnsCommonUserEntity getCommonUserById(int userId);

    /**
     * 更新用户信息
     *
     * @param elebillCommonUserEntity 用户实体
     * @return
     */
    public MMSnsCommonUserEntity updateCommonUser(MMSnsCommonUserEntity elebillCommonUserEntity);
}
