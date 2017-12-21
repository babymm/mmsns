package com.lovecws.mumu.mmsns.controller.admin.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户设置 文章管理
 * @date 2017-12-15 14:00:
 */
@Controller
@RequestMapping("/admin")
public class MMSnsAdminArticleController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = {"/{individuation}/article/classify"}, method = RequestMethod.GET)
    public String articleClassify(@PathVariable String individuation) {
        request.setAttribute("adminModular", "articleClassify");
        return "/admin/article/classify";
    }

    @RequestMapping(value = {"/{individuation}/article/classify/{operation}"}, method = RequestMethod.GET)
    public String articleClassifyCreate(@PathVariable String individuation, @PathVariable String operation) {
        String articleClassifyTitle = null;
        if ("view".equals(operation)) {
            articleClassifyTitle = "文章分类详情";
        } else if ("edit".equals(operation)) {
            articleClassifyTitle = "编辑文章分类";
        } else if ("create".equals(operation)) {
            articleClassifyTitle = "创建文章分类";
        }
        request.setAttribute("adminModular", "articleClassify");
        request.setAttribute("articleClassifyOperation", operation);
        request.setAttribute("articleClassifyTitle", articleClassifyTitle);
        return "/admin/article/classify_create";
    }

    @RequestMapping(value = {"/{individuation}/article/comment"}, method = RequestMethod.GET)
    public String articleComment(@PathVariable String individuation) {
        request.setAttribute("adminModular", "articleComment");
        return "/admin/article/comments";
    }
}
