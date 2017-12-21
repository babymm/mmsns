package com.lovecws.mumu.mmsns.controller.portal.index;

import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.utils.DateUtils;
import com.lovecws.mumu.core.utils.ValidateUtils;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserService;
import com.lovecws.mumu.security.entity.BaseRealm;
import com.lovecws.mumu.security.utils.PasswordHelper;
import com.lovecws.mumu.sms.exception.SMSException;
import com.lovecws.mumu.sms.service.JPushSMSService;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/8/15/015.
 */
@Controller
@RequestMapping("/register")
public class MMSnsPortalRegisterController {

    @Autowired(required = false)
    private MMSnsCommonUserService commonUserService;
    @Autowired
    private JPushSMSService smsService;

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String register() {
        return "/portal/register";
    }

    /**
     * 跳转到手机注册页面
     *
     * @return
     */
    @RequestMapping(value = "/phone", method = RequestMethod.GET)
    public String registerByPhone() {
        return "/portal/register_phone";
    }

    /**
     * 手机号码注册用户
     *
     * @param username 会员名称
     * @param phone    手机号码
     * @param password 密码
     * @param code     短信验证码
     * @param smsId    短信消息id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/phone", method = RequestMethod.POST)
    public ResponseEntity registerPhone(String username, String phone, String password, String code, String smsId) {
        //检验参数
        if (username == null || username.length() < 6) {
            return new ResponseEntity(400, "会员名称格式错误!", null);
        }
        if (!ValidateUtils.isMobile(phone)) {
            return new ResponseEntity(400, "手机号码格式错误!", null);
        }
        if (password == null || password.length() < 6) {
            return new ResponseEntity(400, "密码格式错误!", null);
        }
        if (code == null || smsId == null) {
            return new ResponseEntity(400, "短信验证码不能为空!", null);
        }
        //验证短信验证码
        try {
            boolean validateMessage = smsService.validateMessage(smsId, code);
            if (!validateMessage) {
                return new ResponseEntity(400, "短信验证码错误!", null);
            }
        } catch (SMSException e) {
            e.printStackTrace();
            return new ResponseEntity(400, "短信验证出现异常!", null);
        }
        //检测用户名称和手机号码是否已经注册
        List<MMSnsCommonUserEntity> commonUsers = commonUserService.getCommonUserByCondition(username, phone, null,null);
        if (commonUsers != null && commonUsers.size() > 0) {
            boolean hasExists = false, usernameExists = false, phoneExists = false;
            for (MMSnsCommonUserEntity commonUser : commonUsers) {
                //如果是未激活的用户
                if (MMSnsCommonUserEntity.USER_STATUS_UNACTIVE.equals(commonUser.getUserStatus())) {
                    //提取未过期的用户
                    if (DateUtils.compareDate(new Date(), commonUser.getCreateDate(), Calendar.HOUR) <= 48) {
                        if (commonUser.getUserName().equals(username)) {
                            hasExists = true;
                            usernameExists = true;
                        }
                        if (commonUser.getUserPhone().equals(phone)) {
                            hasExists = true;
                            phoneExists = true;
                        }
                    }
                }
                //如果是已经删除的用户
                else if (MMSnsCommonUserEntity.USER_STATUS_DELETE.equals(commonUser.getUserStatus())) {
                    continue;
                }
                //如果是 已激活的用户和被禁止登录的用户
                else {
                    if (commonUser.getUserName().equals(username)) {
                        hasExists = true;
                        usernameExists = true;
                    } else if (commonUser.getUserPhone().equals(phone)) {
                        hasExists = true;
                        phoneExists = true;
                    }
                }
            }
            //如果有账号存在  去除已经删除的账号和未激活并且已经过期的账户
            if (hasExists) {
                if (usernameExists) {
                    return new ResponseEntity(400, "会员名称已经被占用!", null);
                } else if (phoneExists) {
                    return new ResponseEntity(400, "手机号码已经被占用!", null);
                }
            }
        }
        //注册用户
        MMSnsCommonUserEntity registerUser = new MMSnsCommonUserEntity();
        registerUser.setUserStatus(MMSnsCommonUserEntity.USER_STATUS_ACTIVE);
        registerUser.setPhoneActive(MMSnsCommonUserEntity.USER_STATUS_ACTIVE);
        registerUser.setUserPhone(phone);
        registerUser.setUserName(username);
        registerUser.setRegisterType("phone");
        BaseRealm baseRealm = PasswordHelper.encryptPassword(new BaseRealm(username, password));
        registerUser.setUserPassword(baseRealm.getPassword());
        registerUser.setUserSalt(baseRealm.getSalt());
        registerUser = commonUserService.addCommonUser(registerUser);
        return new ResponseEntity(registerUser);
    }

    /**
     * 发送短信验证码
     *
     * @param phone   手机号码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ResponseEntity sendMessage(String phone, HttpServletRequest request) {
        if (!ValidateUtils.isMobile(phone)) {
            return new ResponseEntity(400, "fail", "手机号码格式不正确");
        }
        try {
            String smsId = smsService.sendSMS(phone);
            return new ResponseEntity(200, "success", smsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(500, "server error", "短信发送失败");
    }

    /**
     * 跳转到邮箱注册页面
     *
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String registerByEmail() {
        return "/portal/register_email";
    }

    /**
     * 邮箱注册用户
     *
     * @param username 用户名称
     * @param email    邮箱
     * @param password 密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResponseEntity registerEmail(String username, String email, String password) {
        //检验参数
        if (username == null || username.length() < 6) {
            return new ResponseEntity(400, "server error", "会员名称格式错误");
        }
        if (!ValidateUtils.isEmail(email)) {
            return new ResponseEntity(400, "server error", "邮箱格式错误");
        }
        if (password == null || password.length() < 6) {
            return new ResponseEntity(400, "server error", "密码格式错误");
        }
        //检测用户名称和邮箱是否已经注册
        List<MMSnsCommonUserEntity> commonUsers = commonUserService.getCommonUserByCondition(username, null, email,null);
        if (commonUsers != null && commonUsers.size() > 0) {
            boolean hasExists = false, usernameExists = false, emailExists = false;
            for (MMSnsCommonUserEntity commonUser : commonUsers) {
                //如果是未激活的用户
                if (MMSnsCommonUserEntity.USER_STATUS_UNACTIVE.equals(commonUser.getUserStatus())) {
                    //提取未过期的用户
                    if (DateUtils.compareDate(new Date(), commonUser.getCreateDate(), Calendar.HOUR) <= 48) {
                        if (commonUser.getUserName().equals(username)) {
                            hasExists = true;
                            usernameExists = true;
                        } else if (commonUser.getUserEmail().equals(email)) {
                            hasExists = true;
                            emailExists = true;
                        }
                    }
                }
                //如果是已经删除的用户
                else if (MMSnsCommonUserEntity.USER_STATUS_DELETE.equals(commonUser.getUserStatus())) {
                    continue;
                }
                //如果是 已激活的用户和被禁止登录的用户
                else {
                    if (commonUser.getUserName().equals(username)) {
                        hasExists = true;
                        usernameExists = true;
                    } else if (commonUser.getUserEmail().equals(email)) {
                        hasExists = true;
                        emailExists = true;
                    }
                }
            }
            //如果有账号存在  去除已经删除的账号和未激活并且已经过期的账户
            if (hasExists) {
                return new ResponseEntity(400, "server error", "邮箱已经被占用");
            }
        }
        MMSnsCommonUserEntity registerUser = new MMSnsCommonUserEntity();
        registerUser.setUserStatus(MMSnsCommonUserEntity.USER_STATUS_UNACTIVE);
        registerUser.setEmailActive(MMSnsCommonUserEntity.USER_STATUS_UNACTIVE);
        registerUser.setUserEmail(email);
        registerUser.setUserName(username);
        registerUser.setRegisterType("email");
        BaseRealm baseRealm = PasswordHelper.encryptPassword(new BaseRealm(username, password));
        registerUser.setUserPassword(baseRealm.getPassword());
        registerUser.setUserSalt(baseRealm.getSalt());
        //使用pinyin4j将username转化为拼音
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        try {
            String individuation = PinyinHelper.toHanYuPinyinString(username, format, "", true);
            registerUser.setIndividuation(individuation);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        registerUser = commonUserService.addCommonUser(registerUser);
        return new ResponseEntity(registerUser);
    }
}
