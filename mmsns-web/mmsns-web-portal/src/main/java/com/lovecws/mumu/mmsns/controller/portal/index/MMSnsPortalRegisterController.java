package com.lovecws.mumu.mmsns.controller.portal.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/8/15/015.
 */
@Controller
@RequestMapping("/register")
public class MMSnsPortalRegisterController {

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
     * 跳转到邮箱注册页面
     *
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String registerByEmail() {
        return "/portal/register_email";
    }

    /**
     * 跳转到邮箱注册页面
     *
     * @return
     */
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String registerSuccess(@ModelAttribute("registerType") String registerType, @ModelAttribute("contact") String contact, @ModelAttribute("code") String code, @ModelAttribute("msg") String msg, Model model) {
        model.addAttribute("registerType", registerType);
        model.addAttribute("contact", contact);
        model.addAttribute("code", code);
        model.addAttribute("msg", msg);
        return "/portal/registerSuccess";
    }
}
