package com.lovecws.mumu.mmsns.controller.common.sms;

import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.utils.ValidateUtils;
import com.lovecws.mumu.sms.service.JPushSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 短信验证码控制器
 * @date 2017-12-22 12:28:
 */
@Controller
@RequestMapping("/common/sms")
public class CommonSMSController {

    @Autowired
    private JPushSMSService smsService;

    /**
     * 发送短信验证码
     *
     * @param phone   手机号码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.POST)
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
}
