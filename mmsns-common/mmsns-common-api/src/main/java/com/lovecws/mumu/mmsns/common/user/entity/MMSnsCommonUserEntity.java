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

    public static final String USER_STATUS_UNACTIVE = "unactive";//未激活
    public static final String USER_STATUS_ACTIVE = "active";//已激活
    public static final String USER_STATUS_FORBIT = "forbit";//被禁止登录
    public static final String USER_STATUS_DELETE = "delete";//删除

    public static final String MMSNS_COMMON_USER = "MMSNS_COMMON_USER";
    public static final String VISIT_USER = "VISIT_USER";

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

    private String individuation;//个性化名称
    private String isindivid;//个性化设置
    private String motto;//人生格言

    //用户信息
    private int userVoteCount;//用户点赞数量
    private int userFansCount;//用户粉丝数量
    private int userAttachCount;//用户关注数量
    private int userScore;//用户积分
    private int userAccessCount;//用户访问量

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateArea() {
        return createArea;
    }

    public void setCreateArea(String createArea) {
        this.createArea = createArea;
    }

    public String getEditIp() {
        return editIp;
    }

    public void setEditIp(String editIp) {
        this.editIp = editIp;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getEditArea() {
        return editArea;
    }

    public void setEditArea(String editArea) {
        this.editArea = editArea;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginArea() {
        return lastLoginArea;
    }

    public void setLastLoginArea(String lastLoginArea) {
        this.lastLoginArea = lastLoginArea;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPhoneActive() {
        return phoneActive;
    }

    public void setPhoneActive(String phoneActive) {
        this.phoneActive = phoneActive;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getEmailActive() {
        return emailActive;
    }

    public void setEmailActive(String emailActive) {
        this.emailActive = emailActive;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailArea() {
        return detailArea;
    }

    public void setDetailArea(String detailArea) {
        this.detailArea = detailArea;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getThirdLoginToken() {
        return thirdLoginToken;
    }

    public void setThirdLoginToken(String thirdLoginToken) {
        this.thirdLoginToken = thirdLoginToken;
    }

    public String getWexin() {
        return wexin;
    }

    public void setWexin(String wexin) {
        this.wexin = wexin;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPositional() {
        return positional;
    }

    public void setPositional(String positional) {
        this.positional = positional;
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardImg() {
        return idcardImg;
    }

    public void setIdcardImg(String idcardImg) {
        this.idcardImg = idcardImg;
    }

    public String getIdcardbgImg() {
        return idcardbgImg;
    }

    public void setIdcardbgImg(String idcardbgImg) {
        this.idcardbgImg = idcardbgImg;
    }

    public String getIndividuation() {
        return individuation;
    }

    public void setIndividuation(String individuation) {
        this.individuation = individuation;
    }

    public void setIsindivid(String isindivid) {
        this.isindivid = isindivid;
    }

    public String getIsindivid() {
        return isindivid;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public int getUserVoteCount() {
        return userVoteCount;
    }

    public void setUserVoteCount(int userVoteCount) {
        this.userVoteCount = userVoteCount;
    }

    public int getUserFansCount() {
        return userFansCount;
    }

    public void setUserFansCount(int userFansCount) {
        this.userFansCount = userFansCount;
    }

    public int getUserAttachCount() {
        return userAttachCount;
    }

    public void setUserAttachCount(int userAttachCount) {
        this.userAttachCount = userAttachCount;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserAccessCount() {
        return userAccessCount;
    }

    public void setUserAccessCount(int userAccessCount) {
        this.userAccessCount = userAccessCount;
    }

}
