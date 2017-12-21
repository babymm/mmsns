package com.lovecws.mumu.mmsns.controller.profile.index;


import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import com.lovecws.mumu.mmsns.common.user.service.MMSnsCommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2017-12-21 17:38:
 */
@Component
public class MMSnsVisitUserContainer {

    @Autowired
    private HttpServletRequest request;
    @Autowired(required = false)
    private MMSnsCommonUserService commonUserService;

    public MMSnsCommonUserEntity visitUser(String individuation) {
        Map<String, MMSnsCommonUserEntity> visitMap = (Map<String, MMSnsCommonUserEntity>) request.getSession().getAttribute("VISIT_USERS_MAP");
        //初始化的时候 将登陆用户添加进去
        if (visitMap == null) {
            visitMap = new HashMap<String, MMSnsCommonUserEntity>();
            MMSnsCommonUserEntity loginUser = (MMSnsCommonUserEntity) request.getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
            visitMap.put(loginUser.getIndividuation(), loginUser);
        }
        MMSnsCommonUserEntity commonUserEntity = visitMap.get(individuation);
        if (commonUserEntity == null) {
            List<MMSnsCommonUserEntity> commonUsers = commonUserService.getCommonUserByCondition(null, null, null, individuation);
            commonUserEntity = commonUsers.get(0);
        }
        visitMap.put(individuation, commonUserEntity);
        request.getSession().setAttribute("VISIT_USERS_MAP", visitMap);
        request.setAttribute("VISIT_USER", commonUserEntity);
        return commonUserEntity;
    }
}
