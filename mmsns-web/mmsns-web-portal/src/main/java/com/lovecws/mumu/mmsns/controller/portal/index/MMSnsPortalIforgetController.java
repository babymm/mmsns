package com.lovecws.mumu.mmsns.controller.portal.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/8/15/015.
 * 找回密码控制器
 */
@Controller
@RequestMapping("/iforget")
public class MMSnsPortalIforgetController {

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
     * 通过邮箱验证码来找回密码
     *
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String iforgetByEmail() {
        return "/portal/iforget_email";
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

}
