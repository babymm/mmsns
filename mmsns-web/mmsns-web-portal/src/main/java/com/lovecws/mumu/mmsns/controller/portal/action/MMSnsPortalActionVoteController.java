package com.lovecws.mumu.mmsns.controller.portal.action;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionVoteEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionService;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionVoteService;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹点赞
 * @date 2017-12-25 11:17:
 */
@Controller
@RequestMapping("/action/vote")
public class MMSnsPortalActionVoteController {

    @Autowired(required = false)
    private MMSnsActionVoteService actionVoteService;
    @Autowired(required = false)
    private MMSnsActionService actionService;

    /**
     * 用户对动弹进行点赞
     *
     * @param actionId 动弹id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity articleVote(int actionId) {
        MMSnsActionEntity actionInfo = actionService.getArctionBaseInfo(actionId);
        if (actionInfo == null) {
            return new ResponseEntity(404, "parameter error", "动弹不存在");
        }
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        Integer sessionCommonUserUserId = sessionCommonUser.getUserId();
        if (actionInfo.getUserId() == sessionCommonUserUserId.intValue()) {
            return new ResponseEntity(404, "parameter error", "不能点赞自己的动弹");
        }
        List<MMSnsActionVoteEntity> articleVotes = actionVoteService.getActionVoteByCondition(actionId, sessionCommonUserUserId);
        if (articleVotes != null && articleVotes.size() > 0) {
            return new ResponseEntity(200, "success", "已点赞");
        }
        MMSnsActionVoteEntity actionVoteEntity = new MMSnsActionVoteEntity();
        actionVoteEntity.setActionId(actionId);
        actionVoteEntity.setVoteUserId(sessionCommonUserUserId);
        actionVoteEntity.setVoteStatus(PublicEnum.NORMAL.value());
        actionVoteEntity.setVoteDate(new Date());
        actionVoteEntity.setActionUserId(actionInfo.getUserId());
        actionVoteEntity = actionVoteService.voteAction(actionVoteEntity);
        return new ResponseEntity(200, "success", "点赞成功");
    }
}
