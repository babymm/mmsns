package com.lovecws.mumu.mmsns.controller.common.email;

import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.utils.ValidateUtils;
import com.lovecws.mumu.email.exception.EmailException;
import com.lovecws.mumu.email.service.SimpleEmailService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 邮件发送控制器
 * @date 2017-12-22 12:30:
 */
@Controller
@RequestMapping("/common/email")
public class CommonEmailController {

    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private SimpleEmailService emailService;

    /**
     * 发送邮箱验证码
     *
     * @param email   邮箱账号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity sendEmail(String email, HttpServletRequest request) {
        if (email == null || !ValidateUtils.isEmail(email)) {
            return new ResponseEntity(400, "error", "邮箱账号错误!");
        }
        //发送注册邮件
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("USERNAME", "baby慕慕");
        modelMap.put("LOGOIMG", basePath + "resources/portal/img/logo.png");
        int verifyCode = RandomUtils.nextInt(100000, 999999);
        request.getSession().setAttribute("VERIFYCODE", String.valueOf(verifyCode));
        modelMap.put("VERIFYCODE", verifyCode);
        modelMap.put("IFORGOTURL", basePath + "iforget");
        modelMap.put("LOGINURL", basePath + "login");
        modelMap.put("OFFICIALURL", basePath);
        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "tpl/verifyCodeEmail.html", "UTF-8", modelMap);
        try {
            boolean sendSuccess = emailService.send(email, null, "baby慕慕开放平台-验证码找回密码", content);
            if (sendSuccess) {
                return new ResponseEntity(200, "success", "验证码发送成功");
            }
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(400, "error", "邮箱发送失败!");
    }
}
