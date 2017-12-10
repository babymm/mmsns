package com.lovecws.mumu.mmsns.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 主页控制器
 * @date 2017-12-08 20:20:15
 */
@Controller
@RequestMapping("/")
public class MMSnsPortalIndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "/index";
    }
}
