package com.lovecws.mumu.mmsns.controller.portal.doc;

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
@RequestMapping("/doc")
public class MMSnsPortalDocController {

    @RequestMapping(method = RequestMethod.GET)
    public String photo(HttpServletRequest request) {
        request.setAttribute("mainModular", "doc");
        return "/portal/doc/doc";
    }

    @RequestMapping(value = "/detail/{docId}", method = RequestMethod.GET)
    public String detail(@PathVariable int docId, HttpServletRequest request) {
        request.setAttribute("mainModular", "doc");
        return "/portal/doc/detail";
    }

    @RequestMapping(value = "/view/{docId}", method = RequestMethod.GET)
    public String view(@PathVariable int docId, HttpServletRequest request) {
        request.setAttribute("mainModular", "doc");
        return "/portal/doc/view";
    }

    @RequestMapping(value = {"/list","/list/{docType}"}, method = RequestMethod.GET)
    public String list(String docType,HttpServletRequest request) {
        request.setAttribute("mainModular", "doc");
        request.setAttribute("docType", docType);
        return "/portal/doc/list";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request) {
        request.setAttribute("mainModular", "doc");
        return "/portal/doc/search";
    }
}
