package com.lovecws.mumu.mmsns.controller.portal.action;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.response.HttpCode;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.action.entity.MMSnsActionEntity;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionCommentService;
import com.lovecws.mumu.mmsns.action.service.MMSnsActionService;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.util.MMSnsContentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 动弹主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/action")
public class MMSnsPortalActionController {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsActionService actionService;

    @RequestMapping(method = RequestMethod.GET)
    public String action() {
        request.setAttribute("mainModular", "action");
        //获取热门动弹
        List<MMSnsActionEntity> actions = actionService.listActionPageWithUserInfo("comment_count", 1, 10);
        request.setAttribute("hotActions", actions);
        return "/portal/action/action";
    }

    /**
     * 分页获取动弹数据
     *
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity actionData(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "limit", required = false, defaultValue = "5") int limit) {
        List<MMSnsActionEntity> actions = actionService.listActionPageWithUserInfo("action_date", page, limit);
        return new ResponseEntity(actions);
    }

    /**
     * 动弹发布
     *
     * @param actionContent
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ResponseEntity publishAction(String actionContent) {
        if (actionContent == null || "".equals(actionContent)) {
            return new ResponseEntity(HttpCode.PARAMETER_ERROR);
        }
        MMSnsActionEntity actionEntity = new MMSnsActionEntity();
        actionEntity.setActionStatus(PublicEnum.NORMAL.value());
        actionEntity.setActionDate(new Date());
        actionEntity.setActionType(MMSnsActionEntity.ACTION_TYPE_ORIGINAL);
        actionEntity.setActionContent(actionContent);
        actionEntity.setWordCount(MMSnsContentUtil.wordCount(actionContent));
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        actionEntity.setUserId(sessionCommonUser.getUserId());
        actionEntity = actionService.addAction(actionEntity);

        actionEntity.setAvator(sessionCommonUser.getAvator());
        actionEntity.setUserName(sessionCommonUser.getUserName());
        actionEntity.setIndividuation(sessionCommonUser.getIndividuation());
        actionEntity.setCompany(sessionCommonUser.getCompany());
        actionEntity.setPositional(sessionCommonUser.getPositional());
        return new ResponseEntity(actionEntity);
    }

    /**
     * 跳转到转载页面
     *
     * @param actionId
     * @return
     */
    @RequestMapping(value = "/reprint/{actionId}", method = RequestMethod.GET)
    public String reprint(@PathVariable int actionId) {
        return "/portal/action/reprint";
    }

    /**
     * 动弹转载
     *
     * @param actionId      动弹id
     * @param actionContent 动弹转载内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reprint", method = RequestMethod.POST)
    public ResponseEntity reprintAction(int actionId, String actionContent) {
        //获取动弹详情
        MMSnsActionEntity actionBaseInfo = actionService.getActionBaseInfo(actionId);
        if (actionBaseInfo == null) {
            return new ResponseEntity(HttpCode.PARAMETER_ERROR);
        }
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        if (actionBaseInfo.getUserId() == sessionCommonUser.getUserId().intValue()) {
            return new ResponseEntity(404, "parameter error", "不能转载自己的动弹");
        }
        MMSnsActionEntity actionEntity = new MMSnsActionEntity();
        actionEntity.setActionStatus(PublicEnum.NORMAL.value());
        actionEntity.setActionDate(new Date());
        actionEntity.setActionType(MMSnsActionEntity.ACTION_TYPE_REPRINT);
        actionEntity.setActionContent(actionContent);
        actionEntity.setWordCount(MMSnsContentUtil.wordCount(actionContent));
        actionEntity.setUserId(sessionCommonUser.getUserId());
        actionEntity.setReprintActionContent(actionBaseInfo.getActionContent());
        actionEntity.setReprintActionId(actionBaseInfo.getActionId());
        actionEntity.setReprintUserId(actionBaseInfo.getUserId());
        actionEntity = actionService.reprintAction(actionEntity);

        actionEntity.setAvator(sessionCommonUser.getAvator());
        actionEntity.setUserName(sessionCommonUser.getUserName());
        actionEntity.setIndividuation(sessionCommonUser.getIndividuation());
        actionEntity.setCompany(sessionCommonUser.getCompany());
        actionEntity.setPositional(sessionCommonUser.getPositional());
        return new ResponseEntity(actionEntity);
    }

    @RequestMapping(value = "/detail/{actionId}", method = RequestMethod.GET)
    public String detail(@PathVariable int actionId) {
        request.setAttribute("mainModular", "action");

        //获取动弹详情
        MMSnsActionEntity actionEntity = actionService.getArctionInfo(actionId);
        request.setAttribute("action", actionEntity);

        //获取热门动弹
        List<MMSnsActionEntity> actions = actionService.listActionPageWithUserInfo("comment_count", 0, 5);
        request.setAttribute("hotActions", actions);
        return "/portal/action/detail";
    }
}
