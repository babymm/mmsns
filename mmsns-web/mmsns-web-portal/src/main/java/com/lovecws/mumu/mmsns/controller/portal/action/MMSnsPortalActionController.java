package com.lovecws.mumu.mmsns.controller.portal.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/action")
public class MMSnsPortalActionController {

    @RequestMapping(method = RequestMethod.GET)
    public String action(HttpServletRequest request) {
        request.setAttribute("mainModular", "action");
        return "/portal/action/action";
    }

    @RequestMapping(value = "/detail/{actionId}", method = RequestMethod.GET)
    public String detail(@PathVariable int actionId, HttpServletRequest request) {
        request.setAttribute("mainModular", "action");
        return "/portal/action/detail";
    }
}
