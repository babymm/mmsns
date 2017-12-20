package com.lovecws.mumu.mmsns.common.user.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户
 * @date 2017-11-24 9:27 mc_user
 */
public class MMSnsCommonUserEntity extends PersistentEntity {

    public static final String USER_STATUS_UNACTIVE="unactive";//未激活
    public static final String USER_STATUS_ACTIVE="active";//已激活
    public static final String USER_STATUS_FORBIT="forbit";//被禁止登录
    public static final String USER_STATUS_DELETE="delete";//删除

    public static final String ELEBILL_COMMON_USER="ELEBILL_COMMON_USER";

    private Integer userId;//用户id
    private String userStatus;//用户状态

    private String createIp;// 创建ip
    private Date createDate;//创建时间
    private String createArea;// 创建地址

    private String editIp;// 最后编辑ip
    private Date editDate;//最后编辑时间
    private String editArea;// 最后编辑地址

    private String lastLoginIp;// 最后登录ip
    private Date lastLoginTime;// 最后一次登录时间
    private String lastLoginArea;// 最后登录地址

    private String userName;// 用户昵称
    private String realName;// 用户真实名称
    private String userPassword;// 用户密码
    private String userSalt;//密码加密
    private String userSex;// 用户性别 m男；f女;u 未知
    private Date userBirthday;// 用户生日

    private String userPhone;// 用户手机号码
    private String phoneActive;// 手机号码是否激活
    private String avator;// 用户头像
    private String thumb;// 用户头像缩略图

    private String userEmail;// 用户邮件
    private String emailActive;// 用户邮件是否激活

    private String province;// 省
    private String city;// 市
    private String area;// 区
    private String detailArea;// 用户详细地址

    private String registerType;// 用户注册类型（phone qq、微信、新浪微博）
    private String thirdLoginToken;// 第三方登录凭证

    private String wexin;// 微信
    private String company;// 公司
    private String positional;// 职称

    //个人认证信息
    private String userAuth;//用户认证 认证中 认证成功 认证失败
    private String idcard;//身份证证
    private String idcardImg;//身份证图片信息
    private String idcardbgImg;//身份证图片信息

}
