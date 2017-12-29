package com.lovecws.mumu.mmsns.controller.portal.action;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.response.HttpCode;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCommentEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionCommentService;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹评论
 * @date 2017-12-29 14:52:
 */
@Controller
@RequestMapping("/action/comment")
public class MMSnsPortalActionCommentController {
    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsActionCommentService actionCommentService;

    /**
     * 分页获取动弹数据
     *
     * @param actionId
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity actionData(int actionId,
                                     @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "limit", required = false, defaultValue = "5") int limit) {
        List<MMSnsActionCommentEntity> actionComments = actionCommentService.listActionCommentPageWithUserInfo(actionId, page, limit);
        return new ResponseEntity(actionComments);
    }

    /**
     * 发布动弹评论
     *
     * @param actionId       动弹id
     * @param commentContent 评论内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ResponseEntity publish(int actionId, String commentContent) {
        MMSnsActionCommentEntity actionCommentEntity = new MMSnsActionCommentEntity();
        actionCommentEntity.setActionId(actionId);
        actionCommentEntity.setCommentContent(commentContent);
        actionCommentEntity.setCommentStatus(PublicEnum.NORMAL.value());
        actionCommentEntity.setCommentDate(new Date());
        actionCommentEntity.setCommentType(MMSnsActionCommentEntity.ACTION_COMMENT_TYPE_COMMENT);
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        actionCommentEntity.setCommentUserId(sessionCommonUser.getUserId());
        actionCommentEntity = actionCommentService.publishActionComment(actionCommentEntity);

        actionCommentEntity.setUserName(sessionCommonUser.getUserName());
        actionCommentEntity.setIndividuation(sessionCommonUser.getIndividuation());
        actionCommentEntity.setAvator(sessionCommonUser.getAvator());
        return new ResponseEntity(actionCommentEntity);
    }

    @RequestMapping(value = "/comment_reply", method = RequestMethod.GET)
    public String goActionCommentReplyPage(int commentId, String userName) {
        request.setAttribute("commentId", commentId);
        request.setAttribute("userName", userName);
        return "/portal/action/comment_reply";
    }

    @ResponseBody
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public ResponseEntity actionCommentReply(int commentId, String replyComment, String userName) {
        //根据评论id 获取评论详情
        MMSnsActionCommentEntity actionCommentEntity = actionCommentService.getActionCommentInfo(commentId);
        if (actionCommentEntity == null) {
            return new ResponseEntity(HttpCode.PARAMETER_ERROR);
        }

        MMSnsActionCommentEntity actionCommentReply = new MMSnsActionCommentEntity();
        actionCommentReply.setActionId(actionCommentEntity.getActionId());
        actionCommentReply.setCommentContent(replyComment);
        actionCommentReply.setCommentStatus(PublicEnum.NORMAL.value());
        actionCommentReply.setCommentDate(new Date());
        actionCommentReply.setCommentType(MMSnsActionCommentEntity.ACTION_COMMENT_TYPE_REPLY);
        actionCommentReply.setReplyCommentId(commentId);
        actionCommentReply.setReplyUserId(actionCommentEntity.getCommentUserId());
        actionCommentReply.setReplyCommentContent(actionCommentEntity.getCommentContent());
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        actionCommentReply.setCommentUserId(sessionCommonUser.getUserId());
        actionCommentReply = actionCommentService.publishActionComment(actionCommentReply);

        actionCommentReply.setUserName(sessionCommonUser.getUserName());
        actionCommentReply.setIndividuation(sessionCommonUser.getIndividuation());
        actionCommentReply.setAvator(sessionCommonUser.getAvator());
        actionCommentReply.setReplyUserName(userName);
        return new ResponseEntity(actionCommentReply);
    }
}
