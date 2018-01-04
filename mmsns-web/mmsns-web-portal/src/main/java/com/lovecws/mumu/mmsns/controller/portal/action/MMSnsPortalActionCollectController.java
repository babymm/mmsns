package com.lovecws.mumu.mmsns.controller.portal.action;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionCollectEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionCollectService;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionService;
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
 * @Description: 动弹收藏
 * @date 2017-12-25 11:17:
 */
@Controller
@RequestMapping("/action/collect")
public class MMSnsPortalActionCollectController {

    @Autowired(required = false)
    private MMSnsActionCollectService actionCollectService;
    @Autowired(required = false)
    private MMSnsActionService actionService;

    /**
     * 发布评论
     *
     * @param actionId 动弹id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity actionCollect(int actionId) {
        MMSnsActionEntity actionInfo = actionService.getActionBaseInfo(actionId);
        if (actionInfo == null) {
            return new ResponseEntity(404, "parameter error", "动弹不存在");
        }
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        Integer sessionCommonUserUserId = sessionCommonUser.getUserId();
        if (actionInfo.getUserId() == sessionCommonUserUserId.intValue()) {
            return new ResponseEntity(404, "parameter error", "不能收藏自己的动弹");
        }
        List<MMSnsActionCollectEntity> actionCollects = actionCollectService.getActionCollectByCondition(actionId, sessionCommonUserUserId);
        if (actionCollects != null && actionCollects.size() > 0) {
            return new ResponseEntity(200, "success", "已收藏");
        }
        MMSnsActionCollectEntity actionCollect = new MMSnsActionCollectEntity();
        actionCollect.setActionId(actionId);
        actionCollect.setCollectUserId(sessionCommonUserUserId);
        actionCollect.setCollectStatus(PublicEnum.NORMAL.value());
        actionCollect.setCollectDate(new Date());
        actionCollect = actionCollectService.collectAction(actionCollect);
        return new ResponseEntity(200, "success", "收藏成功");
    }
}
