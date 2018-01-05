package com.lovecws.mumu.mmsns.controller.admin.action;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCommentEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionCommentService;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCommentEntity;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private MMSnsActionCommentService actionCommentService;

    @RequestMapping(value = {"/{individuation}/action/comment"}, method = RequestMethod.GET)
    public String actionComment(@PathVariable String individuation,
                                @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(name = "limit", required = false, defaultValue = "7") int limit) {
        request.setAttribute("adminModular", "actionComment");

        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        PageBean<MMSnsActionCommentEntity> pageBean = actionCommentService.listUserActionCommentPageWithUserInfo(sessionCommonUser.getUserId(), page, limit);
        request.setAttribute("actionCommentPageBean", pageBean);
        return "/admin/action/comments";
    }

    @ResponseBody
    @RequestMapping(value = {"/{individuation}/action/comment/delete"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteActionComment(@PathVariable String individuation, int commentId) {
        actionCommentService.deleteActionCommentById(commentId);
        return new ResponseEntity();
    }
}
