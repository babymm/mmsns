package com.lovecws.mumu.mmsns.portal.controller.group;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 群组主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/group")
public class MMSnsPortalGroupController {

    @RequestMapping(method = RequestMethod.GET)
    public String group(HttpServletRequest request) {
        request.setAttribute("mainModular", "group");
        return "/portal/group/group";
    }

    @RequestMapping(value = "/detail/{groupId}", method = RequestMethod.GET)
    public String detail(@PathVariable int groupId, HttpServletRequest request) {
        request.setAttribute("mainModular", "group");
        return "/portal/group/detail";
    }
}
