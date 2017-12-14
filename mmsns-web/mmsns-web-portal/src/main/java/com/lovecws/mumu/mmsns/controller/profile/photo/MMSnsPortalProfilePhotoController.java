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

    @RequestMapping(value = "/{userName}/photo", method = RequestMethod.GET)
    public String photo(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/photo/photo";
    }

    @RequestMapping(value = "/{userName}/photo/upload", method = RequestMethod.GET)
    public String upload(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/photo/upload";
    }

    @RequestMapping(value = "/{userName}/photo/album", method = RequestMethod.GET)
    public String album(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/photo/album";
    }
}
