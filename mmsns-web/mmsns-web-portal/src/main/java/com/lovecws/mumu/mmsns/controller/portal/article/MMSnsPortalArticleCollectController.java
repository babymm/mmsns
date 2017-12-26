package com.lovecws.mumu.mmsns.controller.portal.article;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCollectEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCollectService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章收藏
 * @date 2017-12-25 11:17:
 */
@Controller
@RequestMapping("/article/collect")
public class MMSnsPortalArticleCollectController {

    @Autowired(required = false)
    private MMSnsArticleCollectService articleCollectService;
    @Autowired(required = false)
    private MMSnsArticleService articleService;

    /**
     * 发布评论
     *
     * @param articleId 文章id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity articleCollect(int articleId) {
        MMSnsArticleEntity articleInfo = articleService.getArticleSimpleInfo(String.valueOf(articleId));
        if (articleInfo == null) {
            return new ResponseEntity(404, "parameter error", "文章不存在");
        }
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        Integer sessionCommonUserUserId = sessionCommonUser.getUserId();
        if (articleInfo.getUserId() == sessionCommonUserUserId.intValue()) {
            return new ResponseEntity(404, "parameter error", "不能收藏自己的文章");
        }
        List<MMSnsArticleCollectEntity> articleCollects = articleCollectService.getArticleCollectByCondition(articleId, sessionCommonUserUserId);
        if (articleCollects != null && articleCollects.size() > 0) {
            return new ResponseEntity(404, "success", "已收藏");
        }
        MMSnsArticleCollectEntity articleCollect = new MMSnsArticleCollectEntity();
        articleCollect.setArticleId(articleId);
        articleCollect.setCollectUserId(sessionCommonUserUserId);
        articleCollect.setCollectStatus(PublicEnum.NORMAL.value());
        articleCollect.setCollectDate(new Date());
        articleCollect = articleCollectService.collectArticle(articleCollect);
        return new ResponseEntity(200, "success", "收藏成功");
    }
}
