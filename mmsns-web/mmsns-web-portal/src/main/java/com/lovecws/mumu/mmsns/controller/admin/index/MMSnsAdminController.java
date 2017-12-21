package com.lovecws.mumu.mmsns.controller.admin.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 后台设置
 * @date 2017-12-15 12:48:
 */
@Controller
@RequestMapping("/admin")
public class MMSnsAdminController {

    @RequestMapping(value = {"/{individuation}/index", "/{individuation}"}, method = RequestMethod.GET)
    public String admin(@PathVariable String individuation, HttpServletRequest request) {
        return "/admin/index";
    }
}
