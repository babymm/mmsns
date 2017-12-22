package com.lovecws.mumu.mmsns.controller.portal.index;

import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.serialize.JavaSerializeUtil;
import com.lovecws.mumu.core.utils.*;
import com.lovecws.mumu.email.exception.EmailException;
import com.lovecws.mumu.email.service.SimpleEmailService;
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
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/8/15/015.
 */
@Controller
@RequestMapping("/register")
public class MMSnsPortalRegisterController {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsCommonUserService commonUserService;
    @Autowired
    private JPushSMSService smsService;
    @Autowired
    private SimpleEmailService emailService;
    @Autowired
    private VelocityEngine velocityEngine;

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
            return new ResponseEntity(400, "parameter error", "会员名称格式错误!");
        }
        if (!ValidateUtils.isMobile(phone)) {
            return new ResponseEntity(400, "parameter error", "手机号码格式错误!");
        }
        if (password == null || password.length() < 6) {
            return new ResponseEntity(400, "parameter error", "密码格式错误!");
        }
        if (code == null || smsId == null) {
            return new ResponseEntity(400, "parameter error", "短信验证码不能为空!");
        }
        //验证短信验证码
        try {
            boolean validateMessage = smsService.validateMessage(smsId, code);
            if (!validateMessage) {
                return new ResponseEntity(400, "parameter error", "短信验证码错误!");
            }
        } catch (SMSException e) {
            e.printStackTrace();
            return new ResponseEntity(400, "parameter error", "短信验证出现异常!");
        }
        //检测用户名称和手机号码是否已经注册
        List<MMSnsCommonUserEntity> commonUsers = commonUserService.getCommonUserByCondition(username, phone, null, null,null);
        if (commonUsers != null && commonUsers.size() > 0) {
            MMSnsCommonUserEntity commonUserEntity = commonUsers.get(0);
            if (commonUserEntity.getUserStatus().equals(MMSnsCommonUserEntity.USER_STATUS_FORBIT)) {
                return new ResponseEntity(400, "server error", "账号被禁用,请联系管理员");
            } else if (commonUserEntity.getUserStatus().equals(MMSnsCommonUserEntity.USER_STATUS_ACTIVE)) {
                return new ResponseEntity(401, "server error", "手机号码已经注册,请直接登录");
            } else if (commonUserEntity.getUserStatus().equals(MMSnsCommonUserEntity.USER_STATUS_DELETE)) {
                return new ResponseEntity(402, "server error", "账号已经被删除,请联系管理员");
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
        registerUser.setCreateDate(new Date());
        registerUser.setCreateIp(WebUtil.getRemoteIP(request));
        registerUser = commonUserService.addCommonUser(registerUser);
        return new ResponseEntity(registerUser);
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
        List<MMSnsCommonUserEntity> commonUsers = commonUserService.getCommonUserByCondition(username, null, email, null,null);
        if (commonUsers != null && commonUsers.size() > 0) {
            boolean hasExists = false, usernameExists = false, emailExists = false;
            for (MMSnsCommonUserEntity sysUser : commonUsers) {
                //如果是未激活的用户
                if (MMSnsCommonUserEntity.USER_STATUS_UNACTIVE.equals(sysUser.getUserStatus())) {
                    //提取未过期的用户
                    if (DateUtils.compareDate(new Date(), sysUser.getCreateDate(), Calendar.HOUR) <= 48) {
                        if (sysUser.getUserName().equals(username)) {
                            hasExists = true;
                            usernameExists = true;
                        } else if (sysUser.getUserEmail().equals(email)) {
                            hasExists = true;
                            emailExists = true;
                        }
                    }
                }
                //如果是已经删除的用户
                else if (MMSnsCommonUserEntity.USER_STATUS_DELETE.equals(sysUser.getUserStatus())) {
                    continue;
                }
                //如果是 已激活的用户和被禁止登录的用户
                else {
                    if (sysUser.getUserName().equals(username)) {
                        hasExists = true;
                        usernameExists = true;
                    } else if (sysUser.getUserEmail().equals(email)) {
                        hasExists = true;
                        emailExists = true;
                    }
                }
            }
            //如果有账号存在  去除已经删除的账号和未激活并且已经过期的账户
            if (hasExists) {
                if (usernameExists) {
                    return new ResponseEntity(403, "server error", "会员名称已经注册，请更换会员名称");
                }
                if (emailExists) {
                    return new ResponseEntity(403, "server error", "邮箱已经注册，请更换邮箱地址");
                }
            }
        }
        //发送注册邮件
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("USERNAME", username);
        modelMap.put("LOGOIMG", basePath + "resources/portal/img/logo.png");

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("email", email);
            paramMap.put("date", new Date());
            //生成验证码
            String checkcode = SecurityUtil.encryptBASE64(JavaSerializeUtil.serialize(paramMap));
            modelMap.put("VERIFYURL", basePath + "register/mailprove?checkcode=" + checkcode);
            modelMap.put("LOGINURL", basePath + "login");
            modelMap.put("OFFICIALURL", basePath);
            String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "tpl/regEmail.html", "UTF-8", modelMap);
            boolean sendSuccess = emailService.send(email, null, "baby慕慕开放平台-开发者注册认证", content);
            if (sendSuccess) {
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
                registerUser.setCreateDate(new Date());
                registerUser.setCreateIp(WebUtil.getRemoteIP(request));
                registerUser = commonUserService.addCommonUser(registerUser);
                return new ResponseEntity(registerUser);
            }
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(400, "server error", "邮箱注册失败");
    }

    /**
     * 验证邮箱
     *
     * @param checkcode 验证码
     * @return
     */
    @RequestMapping(value = "/mailprove", method = RequestMethod.GET)
    public String mailprove(String checkcode, Model model) {
        model.addAttribute("registerType", "email");
        //参数检测
        if (checkcode == null || "".equals(checkcode)) {
            model.addAttribute("code", 500);
            model.addAttribute("msg", "验证码不能为空!");
            return "/portal/mailprove";
        }
        //获取校验参数
        byte[] bytes = SecurityUtil.decryptBASE64(checkcode);
        Map<String, Object> paramMap = (Map<String, Object>) JavaSerializeUtil.deserialize(bytes);
        String email = (String) paramMap.get("email");
        Date date = (Date) paramMap.get("date");
        model.addAttribute("contact", email);
        if (email == null || date == null || DateUtils.compareDate(new Date(), date, Calendar.HOUR) > 48) {
            model.addAttribute("code", 500);
            model.addAttribute("msg", "注册链接已过期,请重新注册!");
            return "/portal/mailprove";
        }
        //根据邮箱账号查找用户
        List<MMSnsCommonUserEntity> sysUsers = commonUserService.getCommonUserByCondition(null, null, email, null,null);
        if (sysUsers == null || sysUsers.size() != 1) {
            model.addAttribute("code", 500);
            model.addAttribute("msg", "邮箱激活出现异常,请联系管理员!");
            return "/portal/mailprove";
        }
        //检测用户是否已经激活
        MMSnsCommonUserEntity sysUser = sysUsers.get(0);
        if (MMSnsCommonUserEntity.USER_STATUS_ACTIVE.equals(sysUser.getUserStatus())) {
            model.addAttribute("code", 500);
            model.addAttribute("msg", "邮箱已经激活,请登录!");
            return "/portal/mailprove";
        }
        //激活用户
        MMSnsCommonUserEntity registerUser = new MMSnsCommonUserEntity();
        registerUser.setUserId(sysUser.getUserId());
        registerUser.setUserStatus(MMSnsCommonUserEntity.USER_STATUS_ACTIVE);
        registerUser.setEmailActive(MMSnsCommonUserEntity.USER_STATUS_ACTIVE);
        commonUserService.updateCommonUser(registerUser);

        model.addAttribute("code", 200);
        model.addAttribute("msg", "邮箱激活成功！");
        return "/portal/mailprove";
    }
}
