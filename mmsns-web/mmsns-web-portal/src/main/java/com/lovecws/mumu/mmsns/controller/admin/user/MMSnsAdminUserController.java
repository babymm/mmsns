package com.lovecws.mumu.mmsns.controller.admin.user;

import com.lovecws.mumu.core.response.HttpCode;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.utils.WebUtil;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserService;
import com.lovecws.mumu.security.entity.BaseRealm;
import com.lovecws.mumu.security.utils.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户设置 用户信息修改
 * @date 2017-12-15 14:00:
 */
@Controller
@RequestMapping("/admin")
public class MMSnsAdminUserController {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsCommonUserService commonUserService;

    @RequestMapping(value = {"/{individuation}/user/inbox"}, method = RequestMethod.GET)
    public String userInbox(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userInbox");
        return "/admin/user/inbox";
    }

    @RequestMapping(value = {"/{individuation}/user/msgs"}, method = RequestMethod.GET)
    public String userInboxMsgs(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userInbox");
        return "/admin/user/msgs";
    }

    /**
     * 进入到修改登录密码的页面
     *
     * @param individuation
     * @return
     */
    @RequestMapping(value = {"/{individuation}/user/chpwd"}, method = RequestMethod.GET)
    public String userChpwd(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userChpwd");
        return "/admin/user/chpwds";
    }

    /**
     * 修改用户登录密码
     *
     * @param individuation
     * @param password
     * @param newpassword
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/{individuation}/user/chpwd"}, method = RequestMethod.PUT)
    public ResponseEntity changeUserPassword(@PathVariable String individuation, String password, String newpassword) {
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        MMSnsCommonUserEntity commonUser = commonUserService.getCommonUserById(sessionCommonUser.getUserId());
        //比对原来的密码
        String pwd = PasswordHelper.getPwd(password, commonUser.getUserName(), commonUser.getUserSalt());
        if (!pwd.equals(commonUser.getUserPassword())) {
            return new ResponseEntity(HttpCode.PARAMETER_ERROR, "用户密码输入错误");
        }
        //重置新密码
        BaseRealm baseRealm = PasswordHelper.encryptPassword(new BaseRealm(commonUser.getUserName(), newpassword));
        MMSnsCommonUserEntity chpwdUser = new MMSnsCommonUserEntity();
        chpwdUser.setUserId(commonUser.getUserId());
        chpwdUser.setUserPassword(baseRealm.getPassword());
        chpwdUser.setUserSalt(baseRealm.getSalt());
        chpwdUser.setEditDate(new Date());
        chpwdUser.setEditIp(WebUtil.getRemoteIP(request));
        commonUserService.updateCommonUser(chpwdUser);
        return new ResponseEntity();
    }

    @RequestMapping(value = {"/{individuation}/user/namespace"}, method = RequestMethod.GET)
    public String individuationspace(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userNamespace");
        return "/admin/user/namespace";
    }

    @RequestMapping(value = {"/{individuation}/user/profile"}, method = RequestMethod.GET)
    public String userProfile(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userProfile");
        return "/admin/user/profile";
    }

    @RequestMapping(value = {"/{individuation}/user/chemail"}, method = RequestMethod.GET)
    public String userChemail(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userChemail");
        return "/admin/user/chemail";
    }

    @RequestMapping(value = {"/{individuation}/user/chphone"}, method = RequestMethod.GET)
    public String userChphone(@PathVariable String individuation) {
        request.setAttribute("adminModular", "userChphone");
        return "/admin/user/chphone";
    }
}
