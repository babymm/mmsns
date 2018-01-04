package com.lovecws.mumu.mmsns.controller.profile.action;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.core.utils.ValidateUtils;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCollectEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionVoteEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionCollectService;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionService;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionVoteService;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 新闻资讯主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/profile")
public class MMSnsPortalProfileActionController {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsActionService actionService;
    @Autowired(required = false)
    private MMSnsActionVoteService actionVoteService;
    @Autowired(required = false)
    private MMSnsActionCollectService actionCollectService;

    public void initActionCenter(String sessionUserId) {
        //获取统计信息 我发布的文章的数量、我转发的文章的数量
        List<MMSnsActionEntity> actionCounts = actionService.groupActionCountByActionType(sessionUserId);
        int originalActionCount = 0, reprintActionCount = 0, actionWordCount = 0, voteActionCount = 0, collectActionCount = 0;
        for (MMSnsActionEntity actionEntity : actionCounts) {
            if (actionEntity.getActionType().equals(MMSnsActionEntity.ACTION_TYPE_ORIGINAL)) {
                originalActionCount = actionEntity.getActionCount();
                actionWordCount = actionEntity.getWordCount();
            } else if (actionEntity.getActionType().equals(MMSnsActionEntity.ACTION_TYPE_REPRINT)) {
                reprintActionCount = actionEntity.getActionCount();
            }
        }
        //我点赞的文章数量、我收藏的文章数量 文章分类下的文章数量
        voteActionCount = actionVoteService.getVoteActionCountByCondition(sessionUserId);
        collectActionCount = actionCollectService.getCollectActionCountByCondition(sessionUserId);

        request.setAttribute("actionWordCount", actionWordCount);
        request.setAttribute("originalActionCount", originalActionCount);
        request.setAttribute("reprintActionCount", reprintActionCount);
        request.setAttribute("voteActionCount", voteActionCount);
        request.setAttribute("collectActionCount", collectActionCount);
    }

    @RequestMapping(value = {"/{individuation}/action", "/{individuation}/action/{operation}"}, method = RequestMethod.GET)
    public String action(@PathVariable(name = "individuation", required = true) String individuation,
                         @PathVariable(name = "operation", required = false) String operation,
                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(name = "limit", required = false, defaultValue = "5") int limit) {
        MMSnsCommonUserEntity commonUserEntity = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.VISIT_USER);
        String sessionUserId = String.valueOf(commonUserEntity.getUserId());
        initActionCenter(sessionUserId);

        PageBean pageBean = null;
        if ("vote".equals(operation)) {
            pageBean = actionVoteService.listVoteActionPage(sessionUserId, page, limit);
        } else if ("collect".equals(operation)) {
            pageBean = actionCollectService.listCollectActionPage(sessionUserId, page, limit);
        } else {
            pageBean = actionService.listActionPage(sessionUserId, operation, page, limit);
        }
        request.setAttribute("actionPageBean", pageBean);
        return "/profile/action/action";
    }

    @RequestMapping(value = "/{individuation}/action/publish", method = RequestMethod.GET)
    public String publish(@PathVariable String individuation) {
        return "/profile/action/publish";
    }

    /**
     * 取消动弹点赞
     *
     * @param individuation
     * @param actionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{individuation}/action/cancleVote", method = RequestMethod.DELETE)
    public ResponseEntity cancleActionVote(@PathVariable String individuation, int actionId) {
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        MMSnsActionVoteEntity actionVoteEntity = new MMSnsActionVoteEntity();
        actionVoteEntity.setVoteUserId(sessionCommonUser.getUserId());
        actionVoteEntity.setActionId(actionId);
        actionVoteService.cancelActionVote(actionVoteEntity);
        return new ResponseEntity();
    }

    /**
     * 取消动弹收藏
     *
     * @param individuation
     * @param actionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{individuation}/action/cancleCollect", method = RequestMethod.DELETE)
    public ResponseEntity cancleActionCollect(@PathVariable String individuation, int actionId) {
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        MMSnsActionCollectEntity actionCollectEntity = new MMSnsActionCollectEntity();
        actionCollectEntity.setCollectUserId(sessionCommonUser.getUserId());
        actionCollectEntity.setActionId(actionId);
        actionCollectService.cancelActionCollect(actionCollectEntity);
        return new ResponseEntity();
    }

    /**
     * 删除动弹
     *
     * @param individuation
     * @param actionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{individuation}/action/delete", method = RequestMethod.DELETE)
    public ResponseEntity actionDelete(@PathVariable String individuation, int actionId) {
        actionService.deleteActionById(actionId);
        return new ResponseEntity();
    }
}
