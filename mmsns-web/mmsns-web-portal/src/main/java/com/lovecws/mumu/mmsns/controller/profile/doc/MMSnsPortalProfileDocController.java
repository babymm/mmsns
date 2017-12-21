package com.lovecws.mumu.mmsns.controller.profile.doc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 新闻资讯主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/profile")
public class MMSnsPortalProfileDocController {

    @RequestMapping(value = "/{individuation}/doc", method = RequestMethod.GET)
    public String doc(@PathVariable String individuation, HttpServletRequest request) {
        return "/profile/doc/doc";
    }

    @RequestMapping(value = "/{individuation}/doc/upload", method = RequestMethod.GET)
    public String upload(@PathVariable String individuation, HttpServletRequest request) {
        return "/profile/doc/upload";
    }
}
