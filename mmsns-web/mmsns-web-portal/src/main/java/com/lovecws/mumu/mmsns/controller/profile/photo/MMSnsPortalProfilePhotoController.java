package com.lovecws.mumu.mmsns.controller.profile.photo;

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
public class MMSnsPortalProfilePhotoController {

    @RequestMapping(value = "/{individuation}/photo", method = RequestMethod.GET)
    public String photo(@PathVariable String individuation, HttpServletRequest request) {
        return "/profile/photo/photo";
    }

    @RequestMapping(value = "/{individuation}/photo/upload", method = RequestMethod.GET)
    public String upload(@PathVariable String individuation, HttpServletRequest request) {
        return "/profile/photo/upload";
    }

    @RequestMapping(value = "/{individuation}/photo/album", method = RequestMethod.GET)
    public String album(@PathVariable String individuation, HttpServletRequest request) {
        return "/profile/photo/album";
    }
}
