package com.lovecws.mumu.mmsns.controller.admin.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户设置 动弹管理
 * @date 2017-12-15 14:00:
 */
@Controller
@RequestMapping("/admin")
public class MMSnsAdminActionController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = {"/{individuation}/action/comment"}, method = RequestMethod.GET)
    public String actionComment(@PathVariable String individuation) {
        request.setAttribute("adminModular", "actionComment");
        return "/admin/action/comments";
    }
}
