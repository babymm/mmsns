package com.lovecws.mumu.mmsns.portal.controller.photo;

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
@RequestMapping("/photo")
public class MMSnsPortalPhotoController {

    @RequestMapping(method = RequestMethod.GET)
    public String photo(HttpServletRequest request) {
        request.setAttribute("mainModular", "photo");
        return "/portal/photo/photo";
    }

    @RequestMapping(value = "/detail/{newsId}", method = RequestMethod.GET)
    public String detail(@PathVariable int newsId, HttpServletRequest request) {
        request.setAttribute("mainModular", "photo");
        return "/portal/photo/detail";
    }
}
