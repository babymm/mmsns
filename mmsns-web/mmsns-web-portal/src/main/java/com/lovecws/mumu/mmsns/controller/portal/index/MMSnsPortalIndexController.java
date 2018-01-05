package com.lovecws.mumu.mmsns.controller.portal.index;

import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionService;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 主页控制器
 * @date 2017-12-08 20:20:15
 */
@Controller
@RequestMapping
public class MMSnsPortalIndexController {

    @Autowired(required = false)
    private MMSnsArticleService articleService;
    @Autowired(required = false)
    private MMSnsActionService actionService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        request.setAttribute("mainModular", "index");

        //精彩文章 附带图片
        List<MMSnsArticleEntity> articles = articleService.selectArticlePage(null, null, null, null, "read_count", true, 1, 8);
        request.setAttribute("articles", articles);

        //热门动弹
        List<MMSnsActionEntity> actions = actionService.listActionPageWithUserInfo("comment_count", 1, 8);
        request.setAttribute("hotActions", actions);
        return "/portal/index";
    }
}
