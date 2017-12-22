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
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15/015.
 * 找回密码控制器
 */
@Controller
@RequestMapping("/iforget")
public class MMSnsPortalIforgetController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JPushSMSService smsService;
    @Autowired(required = false)
    private MMSnsCommonUserService commonUserService;

    /**
     * 跳转到找回密码主页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String iforget() {
        return "/portal/iforget";
    }

    /**
     * 跳转到手机号码来找回密码页面
     *
     * @return
     */
    @RequestMapping(value = "/phone", method = RequestMethod.GET)
    public String iforgetByPhone() {
        return "/portal/iforget_phone";
    }

    /**
     * 通过手机号码来找回密码
     *
     * @param phone    手机号码
     * @param password 密码
     * @param code     验证码
     * @param smsId    消息id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/phone", method = RequestMethod.POST)
    public ResponseEntity iforgetPhone(String phone, String password, String code, String smsId) {
        //检验参数
        if (!ValidateUtils.isMobile(phone)) {
            return new ResponseEntity(400, "parameter error", "手机号码格式错误");
        }
        if (password == null || password.length() < 6) {
            return new ResponseEntity(400, "parameter error", "密码格式错误");
        }
        if (code == null || smsId == null) {
            return new ResponseEntity(400, "parameter error", "短信验证码不能为空");
        }
        //验证短信验证码
        try {
            boolean validateMessage = smsService.validateMessage(smsId, code);
            if (!validateMessage) {
                return new ResponseEntity(400, "parameter error", "短信验证码错误");
            }
        } catch (SMSException e) {
            e.printStackTrace();
            return new ResponseEntity(400, "parameter error", "短信验证出现异常");
        }
        //根据手机号码查找用户[手机号码注册之后 立即激活账户]
        List<MMSnsCommonUserEntity> sysUsers = commonUserService.getCommonUserByCondition(null, phone, null, null, MMSnsCommonUserEntity.USER_STATUS_ACTIVE);
        if (sysUsers == null || sysUsers.size() == 0) {
            return new ResponseEntity(400, "parameter error", "手机号码未注册");
        }
        MMSnsCommonUserEntity sysUser = sysUsers.get(0);
        //更新用户密码
        MMSnsCommonUserEntity iforgetUser = new MMSnsCommonUserEntity();
        iforgetUser.setUserId(sysUser.getUserId());
        BaseRealm baseRealm = PasswordHelper.encryptPassword(new BaseRealm(sysUser.getUserName(), password));
        iforgetUser.setUserPassword(baseRealm.getPassword());
        iforgetUser.setUserSalt(baseRealm.getSalt());
        iforgetUser.setEditDate(new Date());
        iforgetUser.setEditIp(SecurityUtils.getSubject().getSession(true).getHost());
        commonUserService.updateCommonUser(iforgetUser);
        return new ResponseEntity(iforgetUser);
    }

    /**
     * 通过邮箱验证码来找回密码
     *
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String iforgetByEmail() {
        return "/portal/iforget_email";
    }

    /**
     * 通过邮箱来找回密码
     *
     * @param email    邮箱
     * @param code     邮箱验证码
     * @param password 新设置的密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResponseEntity iforgetEmail(String email, String code, String password) {
        //参数检验
        if (email == null || !ValidateUtils.isEmail(email)) {
            return new ResponseEntity(400, "parameter error", "邮箱错误");
        }
        if (password == null || password.length() < 6) {
            return new ResponseEntity(400, "parameter error", "密码错误");
        }
        //邮箱验证码校验
        String sessionCode = (String) request.getSession().getAttribute("VERIFYCODE");
        if (code == null || !code.equals(sessionCode)) {
            return new ResponseEntity(400, "parameter error", "邮箱验证码错误");
        }
        //根据邮箱查找用户
        List<MMSnsCommonUserEntity> sysUsers = commonUserService.getCommonUserByCondition(null, null, email, null, MMSnsCommonUserEntity.USER_STATUS_ACTIVE);
        if (sysUsers == null || sysUsers.size() == 0) {
            return new ResponseEntity(400, "parameter error", "邮箱未注册");
        }
        //删除未激活过期用户和标记删除的用户
        Iterator<MMSnsCommonUserEntity> iterator = sysUsers.iterator();
        while (iterator.hasNext()) {
            MMSnsCommonUserEntity sysUser = iterator.next();
            if (MMSnsCommonUserEntity.USER_STATUS_UNACTIVE.equals(sysUser.getUserStatus()) && DateUtils.truncatedCompareTo(new Date(), sysUser.getCreateDate(), Calendar.HOUR) > 48) {
                iterator.remove();
            } else if (MMSnsCommonUserEntity.USER_STATUS_DELETE.equals(sysUser.getUserStatus())) {
                iterator.remove();
            }
        }
        //系统异常
        if (sysUsers.size() > 1) {
            return new ResponseEntity(400, "parameter error", "邮箱注册系统出现异常，请联系管理员！");
        }
        if (sysUsers.size() == 0) {
            return new ResponseEntity(400, "parameter error", "邮箱未注册，或者注册过期，请重新注册！");
        }
        MMSnsCommonUserEntity sysUser = sysUsers.get(0);
        if (MMSnsCommonUserEntity.USER_STATUS_UNACTIVE.equals(sysUser.getUserStatus())) {
            return new ResponseEntity(400, "parameter error", "账户未激活，请登录邮箱进行激活操作。");
        }
        //更新用户密码
        MMSnsCommonUserEntity iforgetUser = new MMSnsCommonUserEntity();
        iforgetUser.setUserId(sysUser.getUserId());
        BaseRealm baseRealm = PasswordHelper.encryptPassword(new BaseRealm(sysUser.getUserName(), password));
        iforgetUser.setUserPassword(baseRealm.getPassword());
        iforgetUser.setUserSalt(baseRealm.getSalt());
        commonUserService.updateCommonUser(iforgetUser);
        return new ResponseEntity(iforgetUser);
    }

    /**
     * 通过人工服务来找回密码
     *
     * @return
     */
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String iforgetByService() {
        return "/portal/iforget_service";
    }

    /**
     * 通过人工服务来找回密码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public ResponseEntity iforgetService(String username, String regType, String contact, String regDate, String password, RedirectAttributes model, HttpServletRequest request) {
        //检验参数
        if (username == null || username.length() < 6) {
            return new ResponseEntity(400, "parameter error", "会员名称错误");
        }
        if (regType == null || contact == null) {
            return new ResponseEntity(400, "parameter error", "注册联系方式不能为空");
        }
        if (password == null || password.length() < 6) {
            return new ResponseEntity(400, "parameter error", "密码错误");
        }
        //根据会员名称查找用户
        List<MMSnsCommonUserEntity> sysUsers = commonUserService.getCommonUserByCondition(username, null, null, null, null);
        if (sysUsers == null || sysUsers.size() == 0) {
            return new ResponseEntity(400, "parameter error", "会员名称未注册");
        }
        //删除未激活过期用户和标记删除的用户
        Iterator<MMSnsCommonUserEntity> iterator = sysUsers.iterator();
        while (iterator.hasNext()) {
            MMSnsCommonUserEntity sysUser = iterator.next();
            if (MMSnsCommonUserEntity.USER_STATUS_UNACTIVE.equals(sysUser.getUserStatus()) && DateUtils.truncatedCompareTo(new Date(), sysUser.getCreateDate(), Calendar.HOUR) > 48) {
                iterator.remove();
            } else if (MMSnsCommonUserEntity.USER_STATUS_DELETE.equals(sysUser.getUserStatus())) {
                iterator.remove();
            }
        }
        //系统异常
        if (sysUsers.size() > 1) {
            return new ResponseEntity(400, "parameter error", "注册系统出现异常，请联系管理员！");
        }
        //注册过期
        if (sysUsers.size() == 0) {
            return new ResponseEntity(400, "parameter error", "用户未注册，或者注册过期，请重新注册！");
        }
        //账户未激活
        MMSnsCommonUserEntity sysUser = sysUsers.get(0);
        if (MMSnsCommonUserEntity.USER_STATUS_UNACTIVE.equals(sysUser.getUserStatus())) {
            return new ResponseEntity(400, "parameter error", "账户未激活，请登录邮箱进行激活操作。");
        }
        //匹配注册时间
        if (!DateUtils.formatDate(sysUser.getCreateDate()).equals(regDate)) {
            return new ResponseEntity(400, "parameter error", "用户信息填写不完整，无法找回密码。");
        }
        //匹配注册方式
        String userRegType = sysUser.getRegisterType();
        if (userRegType == null || ("phone".equalsIgnoreCase(userRegType) && !contact.equals(sysUser.getUserPhone())) || ("email".equalsIgnoreCase(userRegType) && !contact.equals(sysUser.getUserEmail()))) {
            return new ResponseEntity(400, "parameter error", "用户信息填写不完整，无法找回密码。");
        }
        //修改账户密码
        MMSnsCommonUserEntity iforgetUser = new MMSnsCommonUserEntity();
        iforgetUser.setUserId(sysUser.getUserId());
        BaseRealm baseRealm = PasswordHelper.encryptPassword(new BaseRealm(sysUser.getUserName(), password));
        iforgetUser.setUserPassword(baseRealm.getPassword());
        iforgetUser.setUserSalt(baseRealm.getSalt());
        commonUserService.updateCommonUser(iforgetUser);
        return new ResponseEntity(iforgetUser);
    }

}
