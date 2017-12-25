package com.lovecws.mumu.mmsns.controller.portal.article;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.response.HttpCode;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCommentEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCommentService;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章评论
 * @date 2017-12-25 11:17:
 */
@Controller
@RequestMapping("/article/comment")
public class MMSnsPortalArticleCommentController {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsArticleCommentService articleCommentService;

    /**
     * 发布评论
     *
     * @param articleId      文章id
     * @param commentContent 评论内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ResponseEntity publish(int articleId, String commentContent,
                       @RequestParam(name = "commentType", required = false, defaultValue = "comment") String commentType, String replyCommentId) {
        MMSnsArticleCommentEntity articleCommentEntity = new MMSnsArticleCommentEntity();
        articleCommentEntity.setArticleId(articleId);
        articleCommentEntity.setCommentContent(commentContent);
        articleCommentEntity.setCommentStatus(PublicEnum.NORMAL.value());
        articleCommentEntity.setCommentDate(new Date());
        articleCommentEntity.setCommentType(commentType);
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        articleCommentEntity.setCommentUserId(sessionCommonUser.getUserId());
        articleCommentEntity.setReplyCommentId(replyCommentId != null ? Integer.parseInt(replyCommentId) : 0);
        articleCommentEntity = articleCommentService.publishArticleComment(articleCommentEntity);

        articleCommentEntity.setUserName(sessionCommonUser.getUserName());
        articleCommentEntity.setIndividuation(sessionCommonUser.getIndividuation());
        articleCommentEntity.setAvator(sessionCommonUser.getAvator());
        return new ResponseEntity(articleCommentEntity);
    }

    /**
     * 获取用户评论
     *
     * @param articleId
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity commentData(int articleId,
                            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(name = "limit", required = false, defaultValue = "5") int limit) {
        List<MMSnsArticleCommentEntity> articleComments = articleCommentService.listArticleCommentPage(articleId, page, limit);
        return new ResponseEntity(articleComments);
    }

    @RequestMapping(value = "/reply",method = RequestMethod.GET)
    public String goArticleCommentReplyPage(int commentId,String userName){
        request.setAttribute("commentId",commentId);
        request.setAttribute("userName",userName);
        return "/portal/article/comment_reply";
    }

    @ResponseBody
    @RequestMapping(value = "/reply",method = RequestMethod.POST)
    public ResponseEntity articleCommentReply(int commentId,String replyComment){
        //根据评论id 获取评论详情
        MMSnsArticleCommentEntity articleCommentEntity=articleCommentService.getArticleCommentInfo(commentId);
        if(articleCommentEntity==null){
            return new ResponseEntity(HttpCode.PARAMETER_ERROR);
        }

        MMSnsArticleCommentEntity articleCommentReply=new MMSnsArticleCommentEntity();
        articleCommentReply.setArticleId(articleCommentEntity.getArticleId());
        articleCommentReply.setCommentContent(replyComment);
        articleCommentReply.setCommentStatus(PublicEnum.NORMAL.value());
        articleCommentReply.setCommentDate(new Date());
        articleCommentReply.setCommentType("reply");
        articleCommentReply.setReplyCommentId(commentId);

        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        articleCommentReply.setCommentUserId(sessionCommonUser.getUserId());

        articleCommentReply = articleCommentService.publishArticleComment(articleCommentReply);
        return new ResponseEntity(articleCommentReply);
    }
}
