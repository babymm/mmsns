package com.lovecws.mumu.mmsns.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 管理后台首页控制器
 * @date 2017-12-11 10:18:
 */
@Controller
@RequestMapping("/manager")
public class MMSnsManagerIndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "/manager/index";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "/manager/main";
    }
}
